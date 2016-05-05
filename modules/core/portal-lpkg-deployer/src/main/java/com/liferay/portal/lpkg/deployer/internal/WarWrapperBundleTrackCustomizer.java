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

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.lpkg.deployer.LPKGWarBundleRegistry;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
public class WarWrapperBundleTrackCustomizer
	implements BundleTrackerCustomizer<Bundle> {

	public WarWrapperBundleTrackCustomizer(
		LPKGWarBundleRegistry lpkgWarBundleRegistry) {

		_lpkgWarBundleRegistry = lpkgWarBundleRegistry;
	}

	@Override
	public Bundle addingBundle(Bundle bundle, BundleEvent bundleEvent) {
		return bundle;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent, Bundle trackedBundle) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent, Bundle trackedBundle) {

		if (bundle.getState() != Bundle.UNINSTALLED) {
			return;
		}

		// Uninstall registered war bundle when its wrapper bundle has been
		// uninstalled.

		Bundle warBundle = _lpkgWarBundleRegistry.unregister(bundle);

		if (warBundle != null) {
			try {
				warBundle.uninstall();
			}
			catch (BundleException be) {
				_log.error(
					"Unable to unregister war bundle " + warBundle +
						", wrapped by " + bundle,
					be);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WarWrapperBundleTrackCustomizer.class);

	private final LPKGWarBundleRegistry _lpkgWarBundleRegistry;

}