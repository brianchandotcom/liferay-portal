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
import com.liferay.portal.util.PropsValues;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
public class LPKGBundleTrackerCustomizer
	implements BundleTrackerCustomizer<List<Bundle>> {

	public LPKGBundleTrackerCustomizer(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public List<Bundle> addingBundle(Bundle bundle, BundleEvent bundleEvent) {
		URL url = bundle.getEntry("liferay-marketplace.properties");

		if (url == null) {
			return null;
		}

		List<Bundle> bundles = new ArrayList<>();

		try {
			Enumeration<URL> enumeration = bundle.findEntries(
				"/", "*.jar", false);

			if (enumeration != null) {
				while (enumeration.hasMoreElements()) {
					url = enumeration.nextElement();

					Bundle newBundle = _bundleContext.installBundle(
						url.getPath(), url.openStream());

					BundleStartLevel bundleStartLevel = newBundle.adapt(
						BundleStartLevel.class);

					bundleStartLevel.setStartLevel(
						PropsValues.
							MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL);

					bundles.add(newBundle);
				}
			}

			enumeration = bundle.findEntries("/", "*.war", false);

			if (enumeration != null) {
				while (enumeration.hasMoreElements()) {
					url = enumeration.nextElement();

					String pathString = url.getPath();

					String contextName = pathString.substring(
						pathString.lastIndexOf('/') + 1,
						pathString.lastIndexOf(".war"));

					int index = contextName.lastIndexOf('-');

					if (index >= 0) {
						contextName = contextName.substring(0, index);
					}

					Path filePath = Files.createTempFile(
						"lpkg-web-" + contextName, ".war");

					try {
						Files.copy(url.openStream(), filePath);

						Bundle newBundle = _bundleContext.installBundle(
							"webbundle:file://" + filePath.toString() +
								"?Web-ContextPath=/" + contextName);

						BundleStartLevel bundleStartLevel = newBundle.adapt(
							BundleStartLevel.class);

						bundleStartLevel.setStartLevel(
							PropsValues.
								MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL);

						bundles.add(newBundle);
					}
					finally {
						Files.delete(filePath);
					}
				}
			}
		}
		catch (Exception e) {
			_log.error("Rollback bundle installation for " + bundles, e);

			for (Bundle newBundle : bundles) {
				try {
					newBundle.uninstall();
				}
				catch (BundleException be) {
					_log.error("Unable to uninstall bundle " + newBundle, be);
				}
			}

			return null;
		}

		return bundles;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent, List<Bundle> bundles) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent, List<Bundle> bundles) {

		if (bundle.getState() != Bundle.UNINSTALLED) {
			return;
		}

		for (Bundle newBundle : bundles) {
			try {
				newBundle.uninstall();
			}
			catch (BundleException be) {
				_log.error(
					"Unable to uninstall " + newBundle +
						" in response to uninstallation of " + bundle,
					be);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LPKGBundleTrackerCustomizer.class);

	private final BundleContext _bundleContext;

}