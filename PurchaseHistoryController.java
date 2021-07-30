package jp.co.internous.rainbow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.rainbow.model.mapper.TblPurchaseHistoryMapper;
import jp.co.internous.rainbow.model.session.LoginSession;
import jp.co.internous.rainbow.model.domain.dto.PurchaseHistoryDto;

@Controller
@RequestMapping("/rainbow/history")
public class PurchaseHistoryController {
	@Autowired
	private LoginSession loginSession;
	
	@Autowired
	private TblPurchaseHistoryMapper purchaseHistoryMapper;
	
	/**
	 * "/"を受け取った際に決済情報を取得し、
	 * purchase_history.htmlに表示させる関数
	 * @param m　画面に渡すデータ
	 * @return　"purchase"に遷移　
	 */
	
	@RequestMapping("/")
	public String index(Model m) {
		int userId = loginSession.getUserId();
		List<PurchaseHistoryDto> historyList = purchaseHistoryMapper.findByUserId(userId);
		
		m.addAttribute("historyList", historyList);
		// page_header.htmlでsessionの変数を表示させているため、loginSessionも画面に送る。
		m.addAttribute("loginSession",loginSession);
						
		return "purchase_history";
	}
	
	/**
	 * 削除ボタンが押下された場合に作用する関数
	 * 購入履歴をDBの購入履歴テーブルから
	 * 論理削除(tbl_purchase_history.statusを0に更新)する
	 * 削除成功した場合、商品購入画面から購入履歴を削除する
	 * @return "購入履歴はありません"と表示
	 */
	
	@RequestMapping("/delete")
	@ResponseBody
	public boolean delete() {
		int userId = loginSession.getUserId();
		int result = purchaseHistoryMapper.logicalDeleteByUserId(userId);
		
		return result > 0;
	}
}
