/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.context.customizer;

import com.liferay.portal.osgi.web.http.servlet.internal.context.LiferayContextController;

import jakarta.servlet.Filter;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.registration.FilterRegistration;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Raymond Augé
 */
public class FilterServiceTrackerCustomizer
	extends BaseServiceTrackerCustomizer
		<Filter, AtomicReference<FilterRegistration>> {

	public FilterServiceTrackerCustomizer(
		BundleContext bundleContext,
		HttpServletEndpointController httpServletEndpointController,
		LiferayContextController liferayContextController) {

		super(
			bundleContext, httpServletEndpointController,
			liferayContextController);
	}

	@Override
	public AtomicReference<FilterRegistration> addingService(
		ServiceReference<Filter> serviceReference) {

		if (Objects.isNull(
				serviceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN)) &&
			Objects.isNull(
				serviceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_REGEX)) &&
			Objects.isNull(
				serviceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET))) {

			return null;
		}

		if (!liferayContextController.matches(serviceReference) ||
			!httpServletEndpointController.matches(serviceReference)) {

			return null;
		}

		AtomicReference<FilterRegistration> filterRegistrationAtomicReference =
			new AtomicReference<>();

		try {
			filterRegistrationAtomicReference.set(
				liferayContextController.addFilterRegistration(
					serviceReference));
		}
		catch (Exception exception) {
			httpServletEndpointController.log(
				exception.getMessage(), exception);
		}

		return filterRegistrationAtomicReference;
	}

	@Override
	public void modifiedService(
		ServiceReference<Filter> serviceReference,
		AtomicReference<FilterRegistration> filterRegistrationAtomicReference) {

		removedService(serviceReference, filterRegistrationAtomicReference);

		AtomicReference<FilterRegistration>
			newFilterRegistrationAtomicReference = addingService(
				serviceReference);

		filterRegistrationAtomicReference.set(
			newFilterRegistrationAtomicReference.get());
	}

	@Override
	public void removedService(
		ServiceReference<Filter> serviceReference,
		AtomicReference<FilterRegistration> filterRegistrationAtomicReference) {

		FilterRegistration filterRegistration =
			filterRegistrationAtomicReference.get();

		if (filterRegistration != null) {
			filterRegistration.destroy();
		}
	}

}