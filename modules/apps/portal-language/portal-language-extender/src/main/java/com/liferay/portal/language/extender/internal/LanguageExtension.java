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
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.ServiceTracker;

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

	public void start() {
		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		for (BundleCapability bundleCapability : _bundleCapabilities) {
			ResourceBundleLoader resourceBundleLoader = null;

			Dictionary<String, Object> attributes = new HashMapDictionary<>(
				bundleCapability.getAttributes());

			Object aggregate = attributes.get("resource.bundle.aggregate");

			String bundleSymbolicName = null;

			Object bundleSymbolicNameObject = attributes.get(
				"bundle.symbolic.name");

			if (Validator.isNull(bundleSymbolicNameObject)) {
				bundleSymbolicName = _bundle.getSymbolicName();
			}
			else {
				bundleSymbolicName = bundleSymbolicNameObject.toString();
			}

			Object baseName = attributes.get("resource.bundle.base.name");

			if (aggregate instanceof String) {
				int serviceRanking = GetterUtil.getInteger(
					attributes.get("service.ranking"), Integer.MIN_VALUE);

				resourceBundleLoader = processAggregate(
					(String)aggregate, bundleSymbolicName, (String)baseName,
					serviceRanking);
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

	protected ResourceBundleLoader processAggregate(
		String aggregate, String bundleSymbolicName, String baseName,
		int limit) {

		List<String> filterStrings = StringUtil.split(aggregate);

		List<ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>>
			serviceTrackers = new ArrayList<>(filterStrings.size());

		for (String filterString : filterStrings) {
			Filter filter = null;

			filterString = StringBundler.concat(
				"(&(objectClass=", ResourceBundleLoader.class.getName(), ")",
				filterString, ")");

			try {
				filter = _bundleContext.createFilter(filterString);
			}
			catch (InvalidSyntaxException ise) {
				throw new IllegalArgumentException(ise);
			}

			ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>
				serviceTracker = new PredicateServiceTracker(
					_bundleContext, filter,
					new ResourceBundleLoaderPredicate(
						bundleSymbolicName, baseName, limit));

			serviceTracker.open();

			serviceTrackers.add(serviceTracker);
		}

		ServiceTrackerResourceBundleLoader serviceTrackerResourceBundleLoader =
			new ServiceTrackerResourceBundleLoader(serviceTrackers);

		_serviceTrackerResourceBundleLoaders.add(
			serviceTrackerResourceBundleLoader);

		return serviceTrackerResourceBundleLoader;
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

	private final Bundle _bundle;
	private final List<BundleCapability> _bundleCapabilities;
	private final BundleContext _bundleContext;
	private final Collection<ServiceRegistration<ResourceBundleLoader>>
		_serviceRegistrations = new ArrayList<>();
	private final List<ServiceTrackerResourceBundleLoader>
		_serviceTrackerResourceBundleLoaders = new ArrayList<>();

	private static class ServiceTrackerResourceBundleLoader
		implements ResourceBundleLoader {

		public ServiceTrackerResourceBundleLoader(
			List<ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>>
				serviceTrackers) {

			_serviceTrackers = serviceTrackers;
		}

		public void close() {
			for (ServiceTracker<?, ?> serviceTracker : _serviceTrackers) {
				serviceTracker.close();
			}
		}

		@Override
		public ResourceBundle loadResourceBundle(Locale locale) {
			List<ResourceBundle> resourceBundles = new ArrayList<>();

			for (ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>
					serviceTracker : _serviceTrackers) {

				ResourceBundleLoader resourceBundleLoader =
					serviceTracker.getService();

				if (resourceBundleLoader != null) {
					ResourceBundle resourceBundle =
						resourceBundleLoader.loadResourceBundle(locale);

					if (resourceBundle != null) {
						resourceBundles.add(resourceBundle);
					}
				}
			}

			if (resourceBundles.isEmpty()) {
				return null;
			}

			if (resourceBundles.size() == 1) {
				return resourceBundles.get(0);
			}

			return new AggregateResourceBundle(
				resourceBundles.toArray(new ResourceBundle[0]));
		}

		private final List
			<ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>>
				_serviceTrackers;

	}

	private static class PredicateServiceTracker
		extends ServiceTracker<ResourceBundleLoader, ResourceBundleLoader> {

		public PredicateServiceTracker(
			BundleContext bundleContext, Filter filter,
			Predicate<ServiceReference<ResourceBundleLoader>> predicate) {

			super(bundleContext, filter, null);

			_predicate = predicate;
		}

		@Override
		public ResourceBundleLoader addingService(
			ServiceReference<ResourceBundleLoader> serviceReference) {

			if (_predicate.test(serviceReference)) {
				return context.getService(serviceReference);
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<ResourceBundleLoader> serviceReference,
			ResourceBundleLoader resourceBundleLoader) {

			if (!_predicate.test(serviceReference)) {
				context.ungetService(serviceReference);

				remove(serviceReference);
			}

			super.modifiedService(serviceReference, resourceBundleLoader);
		}

		private final Predicate<ServiceReference<ResourceBundleLoader>>
			_predicate;

	}

	private static class ResourceBundleLoaderPredicate
		implements Predicate<ServiceReference<ResourceBundleLoader>> {

		public ResourceBundleLoaderPredicate(
			String bundleSymbolicName, String baseName, int limit) {

			_bundleSymbolicName = bundleSymbolicName;
			_baseName = baseName;
			_limit = limit;
		}

		@Override
		public boolean test(
			ServiceReference<ResourceBundleLoader> serviceReference) {

			String bundleSymbolicName = null;

			Object bundleSymbolicNameObject = serviceReference.getProperty(
				"bundle.symbolic.name");

			if (bundleSymbolicNameObject == null) {
				Bundle bundle = serviceReference.getBundle();

				bundleSymbolicName = bundle.getSymbolicName();
			}
			else {
				bundleSymbolicName = bundleSymbolicNameObject.toString();
			}

			String bundleBaseName = null;

			Object bundleBaseNameObject = serviceReference.getProperty(
				"resource.bundle.base.name");

			if (bundleBaseNameObject == null) {
				bundleBaseName = "content.Language";
			}
			else {
				bundleBaseName = bundleBaseNameObject.toString();
			}

			if (_bundleSymbolicName.equals(bundleSymbolicName) &&
				_baseName.equals(bundleBaseName)) {

				int serviceRanking = GetterUtil.getInteger(
					serviceReference.getProperty("service.ranking"),
					Integer.MIN_VALUE);

				if (_limit <= serviceRanking) {
					return false;
				}
			}

			return true;
		}

		private final String _baseName;
		private final String _bundleSymbolicName;
		private final int _limit;

	}

}