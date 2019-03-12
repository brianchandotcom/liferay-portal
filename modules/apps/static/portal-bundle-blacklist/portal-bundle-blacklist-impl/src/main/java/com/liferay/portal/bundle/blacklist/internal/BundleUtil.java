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

package com.liferay.portal.bundle.blacklist.internal;

import com.liferay.osgi.util.bundle.BundleStartLevelUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;

import java.io.File;

import java.net.URI;

import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.wiring.FrameworkWiring;

/**
 * @author Matthew Tambara
 */
public class BundleUtil {

	public static void refreshBundles(
		FrameworkWiring frameworkWiring, List<Bundle> refreshBundles) {

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

	public static void reinstallBundle(
			FrameworkWiring frameworkWiring,
			UninstalledBundleData uninstalledBundleData,
			BundleContext bundleContext, LPKGDeployer lpkgDeployer)
		throws Exception {

		Bundle bundle = null;

		String location = uninstalledBundleData.getLocation();

		Map<String, String[]> parameters = ParamUtil.getParameterMap(location);

		String[] lpkgPath = parameters.get("lpkgPath");
		String[] protocol = parameters.get("protocol");
		String[] webContextPath = parameters.get("Web-ContextPath");

		if (parameters.isEmpty() && location.endsWith(".lpkg")) {
			URI uri = new URI(location);

			uri = uri.normalize();

			bundle = bundleContext.installBundle(
				location, lpkgDeployer.toBundle(new File(uri.getPath())));
		}
		else if (ArrayUtil.isNotEmpty(lpkgPath)) {
			bundle = bundleContext.getBundle(lpkgPath[0]);

			refreshBundles(
				frameworkWiring, Collections.<Bundle>singletonList(bundle));

			return;
		}
		else if (ArrayUtil.isNotEmpty(protocol) && protocol[0].equals("lpkg") &&
				 ArrayUtil.isNotEmpty(webContextPath)) {

			String contextName = webContextPath[0].substring(1);

			for (Bundle installedBundle : bundleContext.getBundles()) {
				Dictionary<String, String> headers = installedBundle.getHeaders(
					StringPool.BLANK);

				if (contextName.equals(
						headers.get("Liferay-WAB-Context-Name"))) {

					refreshBundles(
						frameworkWiring,
						Collections.<Bundle>singletonList(installedBundle));
				}
			}

			return;
		}
		else if (location.startsWith("webbundle:")) {
			WebBundleInstaller webBundleInstaller = new WebBundleInstaller(
				bundleContext, location, uninstalledBundleData.getStartLevel());

			webBundleInstaller.open();

			return;
		}
		else {
			bundle = bundleContext.installBundle(location);
		}

		BundleStartLevelUtil.setStartLevelAndStart(
			bundle, uninstalledBundleData.getStartLevel(), bundleContext);
	}

}