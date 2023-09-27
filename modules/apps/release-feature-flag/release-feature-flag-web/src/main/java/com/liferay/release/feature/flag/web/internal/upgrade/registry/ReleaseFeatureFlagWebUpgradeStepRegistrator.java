/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.feature.flag.web.internal.upgrade.registry;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.release.feature.flag.ReleaseFeatureFlag;
import com.liferay.release.feature.flag.ReleaseFeatureFlagManagerUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = UpgradeStepRegistrator.class)
public class ReleaseFeatureFlagWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {

		// See https://tinyurl.com/yuzbmuzp on how to use this

		registry.register(
			"0.0.0", "1.0.0",
			new ReleaseFeatureFlagUpgradeStep(
				ReleaseFeatureFlag.DISABLE_PRIVATE_LAYOUTS));
	}

	public class ReleaseFeatureFlagUpgradeStep implements UpgradeStep {

		public ReleaseFeatureFlagUpgradeStep(
			ReleaseFeatureFlag releaseFeatureFlag) {

			_releaseFeatureFlag = releaseFeatureFlag;
		}

		@Override
		public void upgrade() {
			if (ReleaseInfo.isDXP() && !StartupHelperUtil.isDBNew() &&
				StartupHelperUtil.isUpgrading()) {

				ReleaseFeatureFlagManagerUtil.setEnabled(
					_releaseFeatureFlag, false);
			}
		}

		private final ReleaseFeatureFlag _releaseFeatureFlag;

	}

}