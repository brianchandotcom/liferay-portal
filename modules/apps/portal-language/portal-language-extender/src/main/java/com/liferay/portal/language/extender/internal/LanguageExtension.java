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
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.CacheResourceBundleLoader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
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

import org.apache.felix.utils.extender.Extension;
import org.apache.felix.utils.log.Logger;

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
		for (Runnable closingRunnable : _closingRunnables) {
			closingRunnable.run();
		}

		for (ServiceRegistration<ResourceBundleLoader> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Override
	public void start() throws Exception {
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
				registerResourceBundleLoader(attributes, resourceBundleLoader);
			}
			else {
				_logger.log(
					Logger.LOG_WARNING,
					StringBundler.concat(
						"Unable to handle ", bundleCapability, " in ",
						_bundle.getSymbolicName()));
			}
		}
	}

	protected ResourceBundleLoader processAggregate(
		String aggregate, final String bundleSymbolicName, String baseName,
		final int limit) {

		String[] filterStrings = aggregate.split(",");

		List<ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>>
			serviceTrackers = new ArrayList<>(filterStrings.length);

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
					filter,
					new ResourceBundleLoaderPredicate(
						bundleSymbolicName, baseName, limit));

			serviceTracker.open();

			_closingRunnables.add(serviceTracker::close);

			serviceTrackers.add(serviceTracker);
		}

		return new ServiceTrackerResourceBundleLoader(serviceTrackers);
	}

	protected ResourceBundleLoader processBaseName(
		ClassLoader classLoader, String baseName,
		boolean excludePortalResource) {

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleUtil.getResourceBundleLoader(baseName, classLoader);

		if (excludePortalResource) {
			return new CacheResourceBundleLoader(resourceBundleLoader);
		}
		else {
			AggregateResourceBundleLoader aggregateResourceBundleLoader =
				new AggregateResourceBundleLoader(
					resourceBundleLoader,
					ResourceBundleLoaderUtil.getPortalResourceBundleLoader());

			return new CacheResourceBundleLoader(aggregateResourceBundleLoader);
		}
	}

	protected void registerResourceBundleLoader(
		Dictionary<String, Object> attributes,
		ResourceBundleLoader resourceBundleLoader) {

		if (Validator.isNull(attributes.get("bundle.symbolic.name"))) {
			attributes.put("bundle.symbolic.name", _bundle.getSymbolicName());
		}

		if (Validator.isNull(attributes.get("service.ranking"))) {
			attributes.put("service.ranking", Integer.MIN_VALUE);
		}

		_serviceRegistrations.add(
			_bundleContext.registerService(
				ResourceBundleLoader.class, resourceBundleLoader, attributes));
	}

	private final Bundle _bundle;
	private final List<BundleCapability> _bundleCapabilities;
	private final BundleContext _bundleContext;
	private final List<Runnable> _closingRunnables = new ArrayList<>();
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
				resourceBundles.toArray(
					new ResourceBundle[resourceBundles.size()]));
		}

		/**
		 * @deprecated As of Judson (7.1.x), replaced by {@link
		 *             #loadResourceBundle(Locale)}
		 */
		@Deprecated
		public ResourceBundle loadResourceBundle(String languageId) {
			return loadResourceBundle(LocaleUtil.fromLanguageId(languageId));
		}

		private final
			List<ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>>
				_serviceTrackers;

	}

	private class PredicateServiceTracker
		extends ServiceTracker<ResourceBundleLoader, ResourceBundleLoader> {

		public PredicateServiceTracker(
			Filter filter,
			Predicate<ServiceReference<ResourceBundleLoader>> predicate) {

			super(_bundleContext, filter, null);

			_predicate = predicate;
		}

		@Override
		public ResourceBundleLoader addingService(
			ServiceReference<ResourceBundleLoader> serviceReference) {

			if (_predicate.test(serviceReference)) {
				return _bundleContext.getService(serviceReference);
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<ResourceBundleLoader> serviceReference,
			ResourceBundleLoader resourceBundleLoader) {

			if (!_predicate.test(serviceReference)) {
				_bundleContext.ungetService(serviceReference);

				remove(serviceReference);
			}

			super.modifiedService(serviceReference, resourceBundleLoader);
		}

		private final Predicate<ServiceReference<ResourceBundleLoader>>
			_predicate;

	}

	private class ResourceBundleLoaderPredicate
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