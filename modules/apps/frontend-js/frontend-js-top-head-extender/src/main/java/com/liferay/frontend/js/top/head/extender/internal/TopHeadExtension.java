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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Dictionary;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class TopHeadExtension {

	public TopHeadExtension(
		Bundle bundle, TopHeadResourcesImpl topHeadResourcesImpl, int weight) {

		_bundle = bundle;
		_topHeadResourcesImpl = topHeadResourcesImpl;
		_weight = weight;
	}

	public void destroy() {
		_serviceTracker.close();
	}

	public void start() {
		BundleContext bundleContext = _bundle.getBundleContext();

		String filterString = StringBundler.concat(
			"(&(objectClass=", ServletContext.class.getName(),
			")(osgi.web.symbolicname=", _bundle.getSymbolicName(), "))");

		Dictionary<String, Object> properties = MapUtil.singletonDictionary(
			"service.ranking", _weight);

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, filterString,
			new ServiceTrackerCustomizer
				<ServletContext, ServiceRegistration<?>>() {

				@Override
				public ServiceRegistration<?> addingService(
					ServiceReference<ServletContext> serviceReference) {

					ServletContext servletContext = bundleContext.getService(
						serviceReference);

					_topHeadResourcesImpl.setServletContextPath(
						servletContext.getContextPath());

					return bundleContext.registerService(
						TopHeadResources.class, _topHeadResourcesImpl,
						properties);
				}

				@Override
				public void modifiedService(
					ServiceReference<ServletContext> serviceReference,
					ServiceRegistration<?> serviceRegistration) {
				}

				@Override
				public void removedService(
					ServiceReference<ServletContext> serviceReference,
					ServiceRegistration<?> serviceRegistration) {

					serviceRegistration.unregister();

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	private final Bundle _bundle;
	private ServiceTracker<ServletContext, ServiceRegistration<?>>
		_serviceTracker;
	private final TopHeadResourcesImpl _topHeadResourcesImpl;
	private final int _weight;

}