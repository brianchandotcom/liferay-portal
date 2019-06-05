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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.CacheResourceBundleLoader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Carlos Sierra Andrés
 */
public class LanguageExtension {

	public LanguageExtension(
		BundleContext bundleContext, Bundle bundle,
		List<BundleCapability> bundleCapabilities) {

		_bundleContext = bundleContext;
		_bundle = bundle;
		_bundleCapabilities = bundleCapabilities;
	}

	public void destroy() {
		for (ServiceTrackerResourceBundleLoader
				serviceTrackerResourceBundleLoader :
					_serviceTrackerResourceBundleLoaders) {

			serviceTrackerResourceBundleLoader.close();
		}

		for (ServiceRegistration<ResourceBundleLoader> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	public void start() throws InvalidSyntaxException {
		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		for (BundleCapability bundleCapability : _bundleCapabilities) {
			ResourceBundleLoader resourceBundleLoader = null;

			Dictionary<String, Object> attributes = new HashMapDictionary<>(
				bundleCapability.getAttributes());

			Object aggregate = attributes.get("resource.bundle.aggregate");

			Object baseName = attributes.get("resource.bundle.base.name");

			if (aggregate instanceof String) {
				int aggregateId = _atomicInteger.incrementAndGet();

				ServiceTrackerResourceBundleLoader
					serviceTrackerResourceBundleLoader =
						new ServiceTrackerResourceBundleLoader(
							_bundleContext, (String)aggregate, aggregateId);

				attributes.put("aggregateId", String.valueOf(aggregateId));

				_serviceTrackerResourceBundleLoaders.add(
					serviceTrackerResourceBundleLoader);

				resourceBundleLoader = serviceTrackerResourceBundleLoader;
			}
			else if (baseName instanceof String) {
				Object excludePortalResources = attributes.get(
					"exclude.portal.resources");

				if (excludePortalResources == null) {
					excludePortalResources = StringPool.FALSE;
				}

				resourceBundleLoader = processBaseName(
					bundleWiring.getClassLoader(), (String)baseName,
					GetterUtil.getBoolean(excludePortalResources));
			}

			Object serviceRanking = attributes.get(Constants.SERVICE_RANKING);

			if (Validator.isNotNull(serviceRanking)) {
				attributes.put(
					Constants.SERVICE_RANKING,
					GetterUtil.getInteger(serviceRanking));
			}

			if (resourceBundleLoader != null) {
				if (Validator.isNull(attributes.get("bundle.symbolic.name"))) {
					attributes.put(
						"bundle.symbolic.name", _bundle.getSymbolicName());
				}

				if (Validator.isNull(attributes.get("service.ranking"))) {
					attributes.put("service.ranking", Integer.MIN_VALUE);
				}

				_serviceRegistrations.add(
					_bundleContext.registerService(
						ResourceBundleLoader.class, resourceBundleLoader,
						attributes));
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to handle ", bundleCapability, " in ",
						_bundle.getSymbolicName()));
			}
		}
	}

	protected ResourceBundleLoader processBaseName(
		ClassLoader classLoader, String baseName,
		boolean excludePortalResource) {

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleUtil.getResourceBundleLoader(baseName, classLoader);

		if (excludePortalResource) {
			return new CacheResourceBundleLoader(resourceBundleLoader);
		}

		AggregateResourceBundleLoader aggregateResourceBundleLoader =
			new AggregateResourceBundleLoader(
				resourceBundleLoader,
				ResourceBundleLoaderUtil.getPortalResourceBundleLoader());

		return new CacheResourceBundleLoader(aggregateResourceBundleLoader);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LanguageExtension.class);

	private static final AtomicInteger _atomicInteger = new AtomicInteger();

	private final Bundle _bundle;
	private final List<BundleCapability> _bundleCapabilities;
	private final BundleContext _bundleContext;
	private final Collection<ServiceRegistration<ResourceBundleLoader>>
		_serviceRegistrations = new ArrayList<>();
	private final List<ServiceTrackerResourceBundleLoader>
		_serviceTrackerResourceBundleLoaders = new ArrayList<>();

}