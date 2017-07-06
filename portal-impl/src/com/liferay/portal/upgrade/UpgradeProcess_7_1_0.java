package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v7_1_0.UpgradeSchema;

/**
 * @author Alberto Chaparro
 */
public class UpgradeProcess_7_1_0 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_7_1_0_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);
	}

}