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

package com.liferay.osgi.util.bundle;

import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;
import org.osgi.framework.wiring.FrameworkWiring;

/**
 * @author Matthew Tambara
 */
public class BundleStartLevelUtil {

	public static void setStartLevelAndStart(
			Bundle bundle, int startLevel, BundleContext bundleContext)
		throws Exception {

		Bundle systemBundle = bundleContext.getBundle(0);

		FrameworkStartLevel frameworkStartLevel = systemBundle.adapt(
			FrameworkStartLevel.class);

		BundleStartLevel bundleStartLevel = bundle.adapt(
			BundleStartLevel.class);

		if (frameworkStartLevel.getStartLevel() >= startLevel) {
			_startBundle(bundle, bundleContext);

			bundleStartLevel.setStartLevel(startLevel);
		}
		else {
			bundleStartLevel.setStartLevel(startLevel);

			_startBundle(bundle, bundleContext);
		}
	}

	public static void setStartLevelAndStart(
			Map<Bundle, Integer> installedBundles, BundleContext bundleContext)
		throws Exception {

		_refreshBundles(installedBundles.keySet(), bundleContext);

		for (Map.Entry<Bundle, Integer> entry : installedBundles.entrySet()) {
			setStartLevelAndStart(
				entry.getKey(), entry.getValue(), bundleContext);
		}
	}

	private static void _refreshBundles(
		Collection<Bundle> refreshBundles, BundleContext bundleContext) {

		Bundle systemBundle = bundleContext.getBundle(0);

		FrameworkWiring frameworkWiring = systemBundle.adapt(
			FrameworkWiring.class);

		final DefaultNoticeableFuture<FrameworkEvent> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		frameworkWiring.refreshBundles(
			refreshBundles,
			new FrameworkListener() {

				@Override
				public void frameworkEvent(FrameworkEvent frameworkEvent) {
					defaultNoticeableFuture.set(frameworkEvent);
				}

			});

		try {
			FrameworkEvent frameworkEvent = defaultNoticeableFuture.get();

			if (frameworkEvent.getType() != FrameworkEvent.PACKAGES_REFRESHED) {
				throw frameworkEvent.getThrowable();
			}
		}
		catch (Throwable t) {
			ReflectionUtil.throwException(t);
		}
	}

	private static void _startBundle(Bundle bundle, BundleContext bundleContext)
		throws Exception {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String fragmentHost = headers.get(Constants.FRAGMENT_HOST);

		if (fragmentHost == null) {
			bundle.start();
		}
		else {
			for (Bundle installedBundle : bundleContext.getBundles()) {
				if (!fragmentHost.equals(installedBundle.getSymbolicName())) {
					continue;
				}

				Bundle systemBundle = bundleContext.getBundle(0);

				FrameworkWiring frameworkWiring = systemBundle.adapt(
					FrameworkWiring.class);

				final DefaultNoticeableFuture<FrameworkEvent>
					defaultNoticeableFuture = new DefaultNoticeableFuture<>();

				frameworkWiring.refreshBundles(
					Collections.singletonList(installedBundle),
					new FrameworkListener() {

						@Override
						public void frameworkEvent(
							FrameworkEvent frameworkEvent) {

							defaultNoticeableFuture.set(frameworkEvent);
						}

					});

				defaultNoticeableFuture.get();

				break;
			}
		}
	}

}