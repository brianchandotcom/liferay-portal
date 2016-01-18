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

package com.liferay.portal.theme.contributor.extender.internal;

import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResources;
import com.liferay.portal.theme.contributor.extender.BundleWebResources;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;

import org.apache.felix.utils.extender.Extension;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 * @author Michael Bradford
 */
public class ThemeContributorExtension implements Extension {

	public ThemeContributorExtension(
		Bundle bundle, BundleWebResourcesImpl bundleWebResources) {

		_bundle = bundle;
		_bundleWebResources = bundleWebResources;
	}

	@Override
	public void destroy() throws Exception {
		_serviceTracker.close();
	}

	@Override
	public void start() throws Exception {
		final BundleContext bundleContext = _bundle.getBundleContext();

		Filter filter = bundleContext.createFilter(
			"(&(objectClass=" + ServletContext.class.getName() +
				")(osgi.web.symbolicname=" + _bundle.getSymbolicName() + "))");

		_serviceTracker = new ServiceTracker<>(
			bundleContext, filter,
			new ServiceTrackerCustomizer
				<ServletContext, Collection<ServiceRegistration<?>>>() {

				@Override
				public Collection<ServiceRegistration<?>> addingService(
					ServiceReference<ServletContext> serviceReference) {

					Collection<ServiceRegistration<?>> serviceRegistrations =
						new ArrayList<>();

					ServletContext servletContext = bundleContext.getService(
						serviceReference);

					serviceRegistrations.add(
						bundleContext.registerService(
							PortalWebResources.class.getName(),
							new ThemeContributorPortalWebResources(
								servletContext),
							null));

					String contextPath = servletContext.getContextPath();

					_bundleWebResources.setServletContextPath(contextPath);

					serviceRegistrations.add(
						bundleContext.registerService(
							BundleWebResources.class, _bundleWebResources,
							null));

					return serviceRegistrations;
				}

				@Override
				public void modifiedService(
					ServiceReference<ServletContext> serviceReference,
					Collection<ServiceRegistration<?>> service) {

					removedService(serviceReference, service);

					addingService(serviceReference);
				}

				@Override
				public void removedService(
					ServiceReference<ServletContext> serviceReference,
					Collection<ServiceRegistration<?>> serviceRegistrations) {

					for (ServiceRegistration<?> serviceRegistration :
							serviceRegistrations) {

						serviceRegistration.unregister();
					}

					bundleContext.ungetService(serviceReference);
				}

			});

		_serviceTracker.open();
	}

	private final Bundle _bundle;
	private final BundleWebResourcesImpl _bundleWebResources;
	private ServiceTracker<ServletContext, Collection<ServiceRegistration<?>>>
		_serviceTracker;

	private class ThemeContributorPortalWebResources
		implements PortalWebResources {

		public ThemeContributorPortalWebResources(
			ServletContext servletContext) {

			_servletContext = servletContext;
		}

		@Override
		public String getContextPath() {
			return _servletContext.getContextPath();
		}

		@Override
		public long getLastModified() {
			return _bundle.getLastModified();
		}

		@Override
		public String getResourceType() {
			return PortalWebResourceConstants.RESOURCE_TYPE_THEME_CONTRIBUTOR;
		}

		@Override
		public ServletContext getServletContext() {
			return _servletContext;
		}

		protected void setServletContext(ServletContext servletContext) {
			_servletContext = servletContext;
		}

		private volatile ServletContext _servletContext;

	}

}