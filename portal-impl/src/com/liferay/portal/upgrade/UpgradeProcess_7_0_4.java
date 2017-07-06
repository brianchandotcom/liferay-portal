package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v7_0_4.UpgradeContact;
import com.liferay.portal.upgrade.v7_0_4.UpgradeEmailAddress;
import com.liferay.portal.upgrade.v7_0_4.UpgradeExpando;
import com.liferay.portal.upgrade.v7_0_4.UpgradeMBMailingList;
import com.liferay.portal.upgrade.v7_0_4.UpgradeUser;

/**
 * @author Alberto Chaparro
 */
public class UpgradeProcess_7_0_4 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_7_0_4_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeContact.class);
		upgrade(UpgradeEmailAddress.class);
		upgrade(UpgradeExpando.class);
		upgrade(UpgradeMBMailingList.class);
		upgrade(UpgradeUser.class);

		clearIndexesCache();
	}

}