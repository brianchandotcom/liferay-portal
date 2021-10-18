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

package com.liferay.release.feature.flag.web.internal.upgrade.util;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.release.feature.flag.ReleaseFeatureFlag;
import com.liferay.release.feature.flag.ReleaseFeatureFlagManagerUtil;

/**
 * @author Alejandro Tardín
 */
public class UpgradeReleaseFeatureFlag implements UpgradeStep {

	public UpgradeReleaseFeatureFlag(ReleaseFeatureFlag releaseFeatureFlag) {
		_releaseFeatureFlag = releaseFeatureFlag;
	}

	@Override
	public void upgrade(DBProcessContext dbProcessContext) {
		if (!StartupHelperUtil.isDBNew() && StartupHelperUtil.isUpgrading()) {
			ReleaseFeatureFlagManagerUtil.setEnabled(
				_releaseFeatureFlag, false);
		}
	}

	private final ReleaseFeatureFlag _releaseFeatureFlag;

}