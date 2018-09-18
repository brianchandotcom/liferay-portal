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

package com.liferay.frontend.css.rtl.servlet.internal;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;

import java.util.Hashtable;

import javax.servlet.Servlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
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
@Component(immediate = true, service = {})
public class RTLServletTracker {

	@Activate
	protected void activate(final BundleContext bundleContext) {
		String filterString = StringBundler.concat(
			"(&(objectClass=", ServletContextHelper.class.getName(), ")",
			"(rtl.required=true))");

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, filterString,
			new ServiceTrackerCustomizer
				<ServletContextHelper, ServiceRegistration<Servlet>>() {

				@Override
				public ServiceRegistration<Servlet> addingService(
					ServiceReference<ServletContextHelper> serviceReference) {

					ServletContextHelper servletContextHelper =
						bundleContext.getService(serviceReference);

					Servlet servlet = new RTLServlet(
						serviceReference.getBundle(), servletContextHelper);

					return bundleContext.registerService(
						Servlet.class, servlet,
						_buildProperties(serviceReference));
				}

				@Override
				public void modifiedService(
					ServiceReference<ServletContextHelper> serviceReference,
					ServiceRegistration<Servlet> serviceRegistration) {

					serviceRegistration.setProperties(
						_buildProperties(serviceReference));
				}

				@Override
				public void removedService(
					ServiceReference<ServletContextHelper> serviceReference,
					ServiceRegistration<Servlet> serviceRegistration) {

					serviceRegistration.unregister();

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private Hashtable<String, Object> _buildProperties(
		ServiceReference<ServletContextHelper> serviceReference) {

		Hashtable<String, Object> properties = new Hashtable<>();

		Object contextName = serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME);

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			contextName);

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			RTLServlet.class.getName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "*.css");

		return properties;
	}

	private ServiceTracker<ServletContextHelper, ServiceRegistration<Servlet>>
		_serviceTracker;

}