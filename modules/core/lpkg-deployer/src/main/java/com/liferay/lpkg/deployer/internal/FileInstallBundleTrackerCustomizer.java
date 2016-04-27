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

package com.liferay.lpkg.deployer.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
public class FileInstallBundleTrackerCustomizer
	implements BundleTrackerCustomizer<Void> {

	public FileInstallBundleTrackerCustomizer(
		BundleTracker<List<Bundle>> lpkgBundleTracker) {

		_lpkgBundleTracker = lpkgBundleTracker;
	}

	@Override
	public Void addingBundle(Bundle bundle, BundleEvent bundleEvent) {
		if (!"org.apache.felix.fileinstall".equals(bundle.getSymbolicName())) {
			return null;
		}

		Map<Bundle, List<Bundle>> tracked = _lpkgBundleTracker.getTracked();

		for (Map.Entry<Bundle, List<Bundle>> entry : tracked.entrySet()) {
			Bundle lpkgBundle = entry.getKey();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Post startup processing for lpkg bundle " + lpkgBundle);
			}

			for (Bundle newBundle : entry.getValue()) {
				if (newBundle.getState() < Bundle.ACTIVE) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Starting " + newBundle + " from " + lpkgBundle);
					}

					try {
						newBundle.start();
					}
					catch (BundleException be) {
						_log.error(
							"Unable to start " + newBundle + " from " +
								lpkgBundle,
							be);
					}

					if (_log.isDebugEnabled()) {
						_log.debug(
							newBundle + " from " + lpkgBundle + "started");
					}
				}
				else if (_log.isDebugEnabled()) {
					_log.debug(
						newBundle + " from " + lpkgBundle +
							" is already started");
				}
			}
		}

		return null;
	}

	@Override
	public void modifiedBundle(Bundle bundle, BundleEvent bundleEvent, Void v) {
	}

	@Override
	public void removedBundle(Bundle bundle, BundleEvent bundleEvent, Void v) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileInstallBundleTrackerCustomizer.class);

	private final BundleTracker<List<Bundle>> _lpkgBundleTracker;

}