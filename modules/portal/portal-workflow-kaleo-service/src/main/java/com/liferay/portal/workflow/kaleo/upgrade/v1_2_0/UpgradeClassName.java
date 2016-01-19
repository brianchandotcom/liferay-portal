package com.liferay.portal.workflow.kaleo.upgrade.v1_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

public class UpgradeClassName extends UpgradeProcess{

	@Override
	protected void doUpgrade() throws Exception {
		updateTableClassName("KaleoInstance");
		updateTableClassName("KaleoInstanceToken");
		updateTableClassName("KaleoTaskInstanceToken");
		
		updateWorkflowContextClassName("KaleoInstance");
		updateWorkflowContextClassName("KaleoLog");
		updateWorkflowContextClassName("KaleoTaskInstanceToken");
	}

	protected void updateTableClassName(String tableName)
		throws Exception {
				
		StringBundler sb = new StringBundler();
		sb.append("update "+ tableName +" set ");
		sb.append("className = \"com.liferay.journal.model.JournalArticle\"");
		sb.append("where className = \"com.liferay.portlet.journal.model.JournalArticle\"");
		String sql = sb.toString();
		runSQL(sql);
		
	}
	
	protected void updateWorkflowContextClassName(String tableName)
			throws Exception {
		
		StringBundler sb = new StringBundler();
		sb.append("update " + tableName +" set ");
		sb.append("workflowContext = ");
		sb.append("REPLACE( workflowContext,");
		sb.append("'com.liferay.portlet.journal.model.JournalArticle',"); 
		sb.append("'com.liferay.journal.model.JournalArticle' )");
		String sql = sb.toString();
		runSQL(sql);
		
	}
	
}
