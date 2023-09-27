/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.internal.util;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class ServicesProvider {

	public static AbsolutePortalURLBuilderFactory
		getAbsolutePortalURLBuilderFactory() {

		return _absolutePortalURLBuilderFactorySnapshot.get();
	}

	public static Map<String, Bundle> getBundleMap() {
		return _bundleMapDCLSingleton.getSingleton(
			() -> {
				Map<String, Bundle> bundleMap = new ConcurrentHashMap<>();

				Bundle bundle = FrameworkUtil.getBundle(ServicesProvider.class);

				BundleTracker<String> bundleTracker = new BundleTracker<>(
					bundle.getBundleContext(), Bundle.ACTIVE,
					new BundleTrackerCustomizer<String>() {

						@Override
						public String addingBundle(
							Bundle bundle, BundleEvent bundleEvent) {

							bundleMap.put(bundle.getSymbolicName(), bundle);

							return bundle.getSymbolicName();
						}

						@Override
						public void modifiedBundle(
							Bundle bundle, BundleEvent bundleEvent,
							String symbolicName) {
						}

						@Override
						public void removedBundle(
							Bundle bundle, BundleEvent bundleEvent,
							String symbolicName) {

							bundleMap.remove(symbolicName);
						}

					});

				bundleTracker.open();

				return bundleMap;
			});
	}

	private static final Snapshot<AbsolutePortalURLBuilderFactory>
		_absolutePortalURLBuilderFactorySnapshot = new Snapshot<>(
			ServicesProvider.class, AbsolutePortalURLBuilderFactory.class);
	private static final DCLSingleton<Map<String, Bundle>>
		_bundleMapDCLSingleton = new DCLSingleton<>();

}