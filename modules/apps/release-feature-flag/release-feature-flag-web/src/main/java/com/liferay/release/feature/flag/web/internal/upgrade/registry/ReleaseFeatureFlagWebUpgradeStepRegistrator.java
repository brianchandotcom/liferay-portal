/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.release.feature.flag.web.internal.upgrade.registry;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
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
		public void upgrade(DBProcessContext dbProcessContext) {
			if (ReleaseInfo.isDXP() && !StartupHelperUtil.isDBNew() &&
				StartupHelperUtil.isUpgrading()) {

				ReleaseFeatureFlagManagerUtil.setEnabled(
					_releaseFeatureFlag, false);
			}
		}

		private final ReleaseFeatureFlag _releaseFeatureFlag;

	}

}