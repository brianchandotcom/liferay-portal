/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.visibility;

import com.liferay.layout.admin.kernel.visibility.LayoutVisibilityManager;
import com.liferay.release.feature.flag.ReleaseFeatureFlag;
import com.liferay.release.feature.flag.ReleaseFeatureFlagManagerUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutVisibilityManager.class)
public class LayoutVisibilityManagerImpl implements LayoutVisibilityManager {

	@Override
	public boolean isPrivateLayoutsEnabled() {
		if (ReleaseFeatureFlagManagerUtil.isEnabled(
				ReleaseFeatureFlag.DISABLE_PRIVATE_LAYOUTS)) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isPrivateLayoutsEnabled(long groupId) {
		if (ReleaseFeatureFlagManagerUtil.isEnabled(
				ReleaseFeatureFlag.DISABLE_PRIVATE_LAYOUTS)) {

			return false;
		}

		return true;
	}

}