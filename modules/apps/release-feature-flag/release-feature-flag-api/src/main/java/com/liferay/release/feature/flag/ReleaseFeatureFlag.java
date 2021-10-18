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

package com.liferay.release.feature.flag;

/**
 * @author Alejandro Tardín
 *
 * Release feature flags are meant to reduce the impact of new changes to
 * existing DXP customers. During the upgrade process, all the new release
 * feature flags will be automatically disabled. Administrators will then be
 * able to manually enable those they consider useful.
 *
 * This enum will hold all the release feature flags. To add one follow these
 * steps:
 *
 * 1. Add a constant here.
 *
 * 2. Add an upgrade step to {@link com.liferay.release.feature.flag.web.internal.upgrade.ReleaseFeatureFlagUpgrade }:
 * <code>
 *     registry.register(
 *     	"0.0.0", "1.0.0",
 *     	new UpgradeReleaseFeatureFlag(ReleaseFeatureFlag.FEATURE_FLAG_NAME))
 * </code>
 * This will ensure the feature flag is disabled for existing DXP customers.
 *
 * 3. Use your feature flag in your code like this:
 * <code>
 * 	if (ReleaseFeatureFlagManagerUtil.isEnabled(ReleaseFeatureFlag.FEATURE_FLAG_NAME)) {
 *		// Code for the new behaviour
 *	}
 *	else {
 *		// Code for the old behaviour
 *	}
 * 	</code>
 */
public enum ReleaseFeatureFlag {
}