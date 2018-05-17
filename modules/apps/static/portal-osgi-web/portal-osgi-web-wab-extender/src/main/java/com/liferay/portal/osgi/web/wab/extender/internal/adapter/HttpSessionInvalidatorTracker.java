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

package com.liferay.portal.osgi.web.wab.extender.internal.adapter;

import com.liferay.portal.kernel.servlet.PortletSessionListenerManager;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.eclipse.equinox.http.servlet.session.HttpSessionInvalidator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
@Component(immediate = true)
public class HttpSessionInvalidatorTracker {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, HttpSessionInvalidator.class,
			new ServiceTrackerCustomizer
				<HttpSessionInvalidator, HttpSessionListener>() {

				@Override
				public HttpSessionListener addingService(
					ServiceReference<HttpSessionInvalidator> serviceReference) {

					HttpSessionInvalidator httpSessionInvalidator =
						bundleContext.getService(serviceReference);

					HttpSessionListener httpSessionListener =
						new HttpSessionListener() {

							@Override
							public void sessionCreated(
								HttpSessionEvent httpSessionEvent) {
							}

							@Override
							public void sessionDestroyed(
								HttpSessionEvent httpSessionEvent) {

								HttpSession httpSession =
									httpSessionEvent.getSession();

								httpSessionInvalidator.invalidate(
									httpSession.getId(), false);
							}

						};

					PortletSessionListenerManager.addHttpSessionListener(
						httpSessionListener);

					return httpSessionListener;
				}

				@Override
				public void modifiedService(
					ServiceReference<HttpSessionInvalidator> serviceReference,
					HttpSessionListener httpSessionListener) {
				}

				@Override
				public void removedService(
					ServiceReference<HttpSessionInvalidator> serviceReference,
					HttpSessionListener httpSessionListener) {

					PortletSessionListenerManager.removeHttpSessionListener(
						httpSessionListener);

					bundleContext.ungetService(serviceReference);
				}

			});

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<HttpSessionInvalidator, HttpSessionListener>
		_serviceTracker;

}