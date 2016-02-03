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

package com.liferay.portal.language.servlet.filter;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true)
public class LanguageFilterTracker {

	@Activate
	protected void activate(final BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTracker = new ServiceTracker<>(
			bundleContext, ServletContextHelper.class,
			new ServletContextHelperServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<ServletContextHelper, ServletContextHelperTracked>
		_serviceTracker;

	private static class ServiceTrackerResourceBundleLoader
		implements ResourceBundleLoader {

		public ServiceTrackerResourceBundleLoader(
			ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>
				serviceTracker) {

			_serviceTracker = serviceTracker;
		}

		@Override
		public ResourceBundle loadResourceBundle(String languageId) {
			ResourceBundleLoader resourceBundleLoader =
				_serviceTracker.getService();

			if (resourceBundleLoader != null) {
				return resourceBundleLoader.loadResourceBundle(languageId);
			}

			return null;
		}

		private final ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>
			_serviceTracker;

	}

	private class ServletContextHelperServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ServletContextHelper, ServletContextHelperTracked> {

		public ServletContextHelperServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ServletContextHelperTracked addingService(
			ServiceReference<ServletContextHelper> serviceReference) {

			Bundle bundle = serviceReference.getBundle();

			try {
				String bundleSymbolicName = bundle.getSymbolicName();

				List<ServiceRegistration<?>> serviceRegistrations =
					new ArrayList<>();

				Hashtable<String, Object> properties = new Hashtable<>();

				properties.put("bundle.symbolic.name", bundleSymbolicName);
				properties.put("service.ranking", Integer.MIN_VALUE);

				BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

				ClassLoader classLoader = bundleWiring.getClassLoader();

				ResourceBundleLoader resourceBundleLoader =
					new AggregateResourceBundleLoader(
						ResourceBundleUtil.getResourceBundleLoader(
							"content.Language", classLoader),
						LanguageResources.RESOURCE_BUNDLE_LOADER);

				serviceRegistrations.add(
					_bundleContext.registerService(
						ResourceBundleLoader.class, resourceBundleLoader,
						properties));

				String filterString =
					"(&(objectClass=" +
						ResourceBundleLoader.class.getName() + ")" +
							"(bundle.symbolic.name=" +
								bundleSymbolicName + "))";

				final ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>
					serviceTracker = ServiceTrackerFactory.open(
						_bundleContext, filterString);

				Filter filter = new LanguageFilter(
					new ServiceTrackerResourceBundleLoader(serviceTracker));

				properties = new Hashtable<>();

				Object contextName = serviceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME);

				properties.put(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
					contextName);

				properties.put(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_DISPATCHER,
					new String[] {
						DispatcherType.ASYNC.toString(),
						DispatcherType.FORWARD.toString(),
						DispatcherType.INCLUDE.toString(),
						DispatcherType.REQUEST.toString()
					});
				properties.put(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME,
					"Language Filter");
				properties.put(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN,
					new String[] {"*.css", "*.js"});

				serviceRegistrations.add(
					_bundleContext.registerService(
						Filter.class, filter, properties));

				return new ServletContextHelperTracked(
					serviceTracker, serviceRegistrations);
			}
			catch (InvalidSyntaxException ise) {
				throw new RuntimeException(ise);
			}
		}

		@Override
		public void modifiedService(
			ServiceReference<ServletContextHelper> serviceReference,
			ServletContextHelperTracked serviceRegistration) {

			removedService(serviceReference, serviceRegistration);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<ServletContextHelper> serviceReference,
			ServletContextHelperTracked servletContextHelperTracked) {

			servletContextHelperTracked.clean();

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

	private class ServletContextHelperTracked {

		public ServletContextHelperTracked(
			ServiceTracker<?, ?> serviceTracker,
			List<ServiceRegistration<?>> serviceRegistrations) {

			_serviceTracker = serviceTracker;
			_serviceRegistrations = serviceRegistrations;
		}

		public void clean() {
			_serviceTracker.close();

			for (ServiceRegistration<?> serviceRegistration :
					_serviceRegistrations) {

				serviceRegistration.unregister();
			}
		}

		private final List<ServiceRegistration<?>> _serviceRegistrations;
		private ServiceTracker<?, ?> _serviceTracker;

	}

}