package com.liferay.message.boards.internal.upgrade.v6_3_1;


import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.v7_3_x.UpgradeSchema;

/**
 * @author Felipe Veloso
 */
public class MBSuspiciousActivityUpgradeProcess extends UpgradeProcess {


	@Override
	protected void doUpgrade() throws Exception {

		String template = StringUtil.read(
			UpgradeSchema.class.getResourceAsStream("dependencies/update.sql"));

		runSQLTemplateString(template, false);

//		upgrade(UpgradeMVCCVersion.class);

		alterTableDropColumn("MBSuspiciousActivity","description");
		alterColumnName("MBSuspiciousActivity","type","reason VARCHAR(255) null");
	}
}
