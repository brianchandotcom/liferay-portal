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

package com.liferay.portal.language.extender.internal;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.CacheResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.felix.utils.extender.Extension;
import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
public class LanguageExtension implements Extension {

	public LanguageExtension(
		BundleContext bundleContext, Bundle bundle,
		List<BundleCapability> bundleCapabilities, Logger logger) {

		_bundleContext = bundleContext;
		_bundle = bundle;
		_bundleCapabilities = bundleCapabilities;
		_logger = logger;
	}

	@Override
	public void destroy() throws Exception {
		for (ServiceRegistration<ResourceBundleLoader> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Override
	public void start() throws Exception {
		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		for (BundleCapability capability : _bundleCapabilities) {
			Map<String, Object> attributes = capability.getAttributes();

			Object aggregate = attributes.get("resource.bundle.aggregate");
			Object baseName = attributes.get("resource.bundle.base.name");

			ResourceBundleLoader resourceBundleLoader = null;

			if (aggregate instanceof String) {
				resourceBundleLoader = processAggregate((String)aggregate);
			}
			else if (baseName instanceof String) {
				resourceBundleLoader = processBaseName(
					bundleWiring.getClassLoader(), (String)baseName);
			}

			if (resourceBundleLoader != null) {
				registerResourceBundleLoader(attributes, resourceBundleLoader);
			}
			else {
				_logger.log(
					Logger.LOG_WARNING,
					"Unable to handle " + capability + " in " +
						_bundle.getSymbolicName());
			}
		}
	}

	protected ResourceBundleLoader processAggregate(String aggregate) {
		String[] filterStrings = aggregate.split(",");

		List<ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>>
			serviceTrackers = new ArrayList<>(filterStrings.length);

		for (String filterString : filterStrings) {
			filterString =
				"(&(objectClass=" + ResourceBundleLoader.class.getName() + ")" +
					filterString + ")";

			ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>
				serviceTracker = ServiceTrackerFactory.open(
					_bundleContext, filterString);

			serviceTrackers.add(serviceTracker);
		}

		return new ServiceTrackerResourceBundleLoader(serviceTrackers);
	}

	protected ResourceBundleLoader processBaseName(
		ClassLoader classLoader, String baseName) {

		return new CacheResourceBundleLoader(
			ResourceBundleUtil.getResourceBundleLoader(baseName, classLoader));
	}

	protected void registerResourceBundleLoader(
		Map<String, Object> attributes,
		ResourceBundleLoader resourceBundleLoader) {

		Dictionary<String, Object> properties = new Hashtable<>(attributes);

		if (Validator.isNull(properties.get("bundle.symbolic.name"))) {
			properties.put("bundle.symbolic.name", _bundle.getSymbolicName());
		}

		if (Validator.isNull(properties.get("service.ranking"))) {
			properties.put("service.ranking", Integer.MIN_VALUE);
		}

		_serviceRegistrations.add(
			_bundleContext.registerService(
				ResourceBundleLoader.class, resourceBundleLoader, properties));
	}

	private final Bundle _bundle;
	private final List<BundleCapability> _bundleCapabilities;
	private final BundleContext _bundleContext;
	private final Logger _logger;
	private final Collection<ServiceRegistration<ResourceBundleLoader>>
		_serviceRegistrations = new ArrayList<>();

	private static class ServiceTrackerResourceBundleLoader
		implements ResourceBundleLoader {

		public ServiceTrackerResourceBundleLoader(
			List<ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>>
				serviceTrackers) {

			_serviceTrackers = serviceTrackers;
		}

		@Override
		public ResourceBundle loadResourceBundle(String languageId) {
			List<ResourceBundle> resourceBundles = new ArrayList<>();

			for (ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>
					serviceTracker : _serviceTrackers) {

				ResourceBundleLoader resourceBundleLoader =
					serviceTracker.getService();

				ResourceBundle resourceBundle =
					resourceBundleLoader.loadResourceBundle(languageId);

				if (resourceBundle != null) {
					resourceBundles.add(resourceBundle);
				}
			}

			if (resourceBundles.isEmpty()) {
				return null;
			}

			if (resourceBundles.size() == 1) {
				return resourceBundles.get(0);
			}

			return new AggregateResourceBundle(
				resourceBundles.toArray(
					new ResourceBundle[resourceBundles.size()]));
		}

		private final
			List<ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>>
				_serviceTrackers;

	}

}