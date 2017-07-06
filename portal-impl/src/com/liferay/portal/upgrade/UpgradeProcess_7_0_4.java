package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;

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
	}

}