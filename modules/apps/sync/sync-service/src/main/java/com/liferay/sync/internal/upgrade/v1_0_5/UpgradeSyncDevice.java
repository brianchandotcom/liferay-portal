package com.liferay.sync.internal.upgrade.v1_0_5;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.sync.internal.upgrade.v1_0_5.util.SyncDeviceTable;

/**
 * @author Jose A. Jimenez Campoy
 */
public class UpgradeSyncDevice extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			SyncDeviceTable.class,
			new AlterColumnType("hostname", "VARCHAR(200) null"));
	}

}