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

package com.liferay.portal.cache.extender.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Tina Tian
 */
public class PortalCacheExtenderUtil {

	public static BundleTracker<Void> createBundleTracker(
		BundleContext bundleContext,
		PortalCacheManager<?, ?> portalCacheManager, String propertyKey,
		String defaultConfigurationFile) {

		return new BundleTracker<Void>(bundleContext, Bundle.ACTIVE, null) {

			@Override
			public Void addingBundle(Bundle bundle, BundleEvent bundleEvent) {
				BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

				ClassLoader classLoader = bundleWiring.getClassLoader();

				String configurationFile = null;

				if (classLoader.getResource("portlet.properties") != null) {
					Configuration configuration =
						ConfigurationFactoryUtil.getConfiguration(
							classLoader, "portlet");

					configurationFile = configuration.get(propertyKey);
				}

				if (Validator.isNull(configurationFile)) {
					configurationFile = defaultConfigurationFile;
				}

				URL configurationURL = classLoader.getResource(
					configurationFile);

				if (configurationURL != null) {
					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Reconfiguring caches in cache manager ",
								portalCacheManager.getPortalCacheManagerName(),
								" using ", configurationURL));
					}

					portalCacheManager.reconfigurePortalCaches(
						configurationURL, classLoader);
				}

				return null;
			}

		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalCacheExtenderUtil.class);

}