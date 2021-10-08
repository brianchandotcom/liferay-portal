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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alejandro Tardín
 */
public class ReleaseFeatureFlagManagerUtil {

	public static boolean isEnabled(ReleaseFeatureFlag releaseFeatureFlag) {
		ReleaseFeatureFlagManager releaseFeatureFlagManager =
			_serviceTracker.getService();

		return releaseFeatureFlagManager.isEnabled(releaseFeatureFlag);
	}

	public static void setEnabled(
		ReleaseFeatureFlag releaseFeatureFlag, boolean enabled) {

		ReleaseFeatureFlagManager releaseFeatureFlagManager =
			_serviceTracker.getService();

		releaseFeatureFlagManager.setEnabled(releaseFeatureFlag, enabled);
	}

	private static final ServiceTracker
		<ReleaseFeatureFlagManager, ReleaseFeatureFlagManager> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ReleaseFeatureFlagManagerUtil.class);

		ServiceTracker<ReleaseFeatureFlagManager, ReleaseFeatureFlagManager>
			serviceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), ReleaseFeatureFlagManager.class,
				null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}