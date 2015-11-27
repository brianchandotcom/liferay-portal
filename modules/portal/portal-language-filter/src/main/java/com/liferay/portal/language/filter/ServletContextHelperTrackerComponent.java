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

package com.liferay.portal.language.filter;

import java.util.Hashtable;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
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
@Component(immediate = true)
public class ServletContextHelperTrackerComponent {

	@Activate
	protected void activate(final BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTracker = new ServiceTracker<>(
			bundleContext, ServletContextHelper.class,
			new ServiceTrackerCustomizer
				<ServletContextHelper, ServiceRegistration<Filter>>() {

				@Override
				public ServiceRegistration<Filter> addingService(
					ServiceReference<ServletContextHelper> serviceReference) {

					ServletContextHelper servletContextHelper =
						bundleContext.getService(serviceReference);

					Filter languageFilter = new LanguageFilter(
						servletContextHelper);

					Hashtable<String, Object> properties = new Hashtable<>();

					Object contextName = serviceReference.getProperty(
						HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME);

					properties.put(
						HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
						contextName);
					properties.put(
						HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN,
							new String[] {"*.css", "*.js"});
					properties.put(
						HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME,
						"LanguageFilter");
					properties.put(
						HttpWhiteboardConstants.
							HTTP_WHITEBOARD_FILTER_DISPATCHER,
							new String[] {
								DispatcherType.ASYNC.toString(),
								DispatcherType.FORWARD.toString(),
								DispatcherType.INCLUDE.toString(),
								DispatcherType.REQUEST.toString()
							});

					return bundleContext.registerService(
						Filter.class, languageFilter, properties);
				}

				@Override
				public void modifiedService(
					ServiceReference<ServletContextHelper> reference,
					ServiceRegistration<Filter> serviceRegistration) {

					removedService(reference, serviceRegistration);
					addingService(reference);
				}

				@Override
				public void removedService(
					ServiceReference<ServletContextHelper> reference,
					ServiceRegistration<Filter> serviceRegistration) {

					serviceRegistration.unregister();

					bundleContext.ungetService(reference);
				}
			});

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<ServletContextHelper, ServiceRegistration<Filter>>
		_serviceTracker;

}