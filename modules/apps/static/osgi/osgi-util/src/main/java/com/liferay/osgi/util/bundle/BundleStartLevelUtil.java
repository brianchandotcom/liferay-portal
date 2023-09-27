/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.util.bundle;

import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.string.StringPool;

import java.util.Collections;
import java.util.Dictionary;

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