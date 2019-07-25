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

package com.liferay.portal.cache.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = {})
public class PortalCacheExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<Void>(
			bundleContext, Bundle.ACTIVE, null) {

			@Override
			public Void addingBundle(Bundle bundle, BundleEvent bundleEvent) {
				BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

				ClassLoader classLoader = bundleWiring.getClassLoader();

				_configure(
					classLoader, _multiVMPortalCacheManager,
					PropsKeys.EHCACHE_MULTI_VM_CONFIG_LOCATION,
					"/META-INF/module-multi-vm.xml");
				_configure(
					classLoader, _singleVMPortalCacheManager,
					PropsKeys.EHCACHE_SINGLE_VM_CONFIG_LOCATION,
					"/META-INF/module-single-vm.xml");

				return null;
			}

		};

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private void _configure(
		ClassLoader classLoader, PortalCacheManager<?, ?> portalCacheManager,
		String propertyKey, String defaultConfigurationFile) {

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

		URL configurationURL = classLoader.getResource(configurationFile);

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
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalCacheExtender.class);

	private BundleTracker<Void> _bundleTracker;

	@Reference(
		target = "(portal.cache.manager.name=" + PortalCacheManagerNames.MULTI_VM + ")"
	)
	private PortalCacheManager<? extends Serializable, ? extends Serializable>
		_multiVMPortalCacheManager;

	@Reference(
		target = "(portal.cache.manager.name=" + PortalCacheManagerNames.SINGLE_VM + ")"
	)
	private PortalCacheManager<? extends Serializable, ?>
		_singleVMPortalCacheManager;

}