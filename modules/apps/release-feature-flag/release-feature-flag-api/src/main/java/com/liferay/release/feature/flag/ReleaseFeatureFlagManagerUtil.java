/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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