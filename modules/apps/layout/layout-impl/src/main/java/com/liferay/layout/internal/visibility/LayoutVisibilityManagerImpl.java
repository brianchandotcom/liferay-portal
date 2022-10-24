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