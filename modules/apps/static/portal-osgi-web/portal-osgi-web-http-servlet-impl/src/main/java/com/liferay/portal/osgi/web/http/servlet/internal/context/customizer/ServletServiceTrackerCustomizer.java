/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.context.customizer;

import com.liferay.portal.osgi.web.http.servlet.internal.context.LiferayContextController;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import jakarta.servlet.Servlet;

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.registration.ServletRegistration;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Dante Wang
 */
public class ServletServiceTrackerCustomizer
	extends BaseServiceTrackerCustomizer
		<Servlet, AtomicReference<ServletRegistration>> {

	public ServletServiceTrackerCustomizer(
		BundleContext bundleContext,
		HttpServletEndpointController httpServletEndpointController,
		LiferayContextController liferayContextController) {

		super(
			bundleContext, httpServletEndpointController,
			liferayContextController);
	}

	@Override
	public AtomicReference<ServletRegistration> addingService(
		ServiceReference<Servlet> serviceReference) {

		if (Objects.isNull(
				serviceReference.getProperty(
					HttpWhiteboardConstants.
						HTTP_WHITEBOARD_SERVLET_ERROR_PAGE)) &&
			Objects.isNull(
				serviceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME)) &&
			Objects.isNull(
				serviceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN))) {

			return null;
		}

		if (!liferayContextController.matches(serviceReference) ||
			!httpServletEndpointController.matches(serviceReference)) {

			return null;
		}

		AtomicReference<ServletRegistration> result = new AtomicReference<>();

		try {
			result.set(
				liferayContextController.addServletRegistration(
					serviceReference));
		}
		catch (Exception exception) {
			httpServletEndpointController.log(
				exception.getMessage(), exception);
		}

		return result;
	}

	@Override
	public void modifiedService(
		ServiceReference<Servlet> serviceReference,
		AtomicReference<ServletRegistration> servletReference) {

		removedService(serviceReference, servletReference);

		AtomicReference<ServletRegistration> added = addingService(
			serviceReference);

		servletReference.set(added.get());
	}

	@Override
	public void removedService(
		ServiceReference<Servlet> serviceReference,
		AtomicReference<ServletRegistration> servletReference) {

		ServletRegistration registration = servletReference.get();

		if (registration != null) {
			registration.destroy();
		}
	}

}