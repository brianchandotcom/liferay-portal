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

package com.liferay.frontend.js.top.head.extender.internal;

import com.liferay.frontend.js.top.head.extender.TopHeadResources;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletContext;

import org.apache.felix.utils.extender.Extension;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class TopHeadExtension implements Extension {

	public TopHeadExtension(
		Bundle bundle, TopHeadResourcesImpl topHeadResourcesImpl, int weight) {

		_bundle = bundle;
		_topHeadResourcesImpl = topHeadResourcesImpl;
		_weight = weight;
	}

	@Override
	public void destroy() throws Exception {
		_serviceTracker.close();
	}

	@Override
	public void start() throws Exception {
		final BundleContext bundleContext = _bundle.getBundleContext();

		String filterString = StringBundler.concat(
			"(&(objectClass=", ServletContext.class.getName(),
			")(osgi.web.symbolicname=", _bundle.getSymbolicName(), "))");

		final Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("service.ranking", _weight);

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, filterString,
			new ServiceTrackerCustomizer
				<ServletContext, Collection<ServiceRegistration<?>>>() {

				@Override
				public Collection<ServiceRegistration<?>> addingService(
					ServiceReference<ServletContext> serviceReference) {

					Collection<ServiceRegistration<?>> serviceRegistrations =
						new ArrayList<>();

					ServletContext servletContext = bundleContext.getService(
						serviceReference);

					_topHeadResourcesImpl.setServletContextPath(
						servletContext.getContextPath());

					serviceRegistrations.add(
						bundleContext.registerService(
							TopHeadResources.class, _topHeadResourcesImpl,
							properties));

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
	}

	private final Bundle _bundle;
	private ServiceTracker<ServletContext, Collection<ServiceRegistration<?>>>
		_serviceTracker;
	private final TopHeadResourcesImpl _topHeadResourcesImpl;
	private final int _weight;

}