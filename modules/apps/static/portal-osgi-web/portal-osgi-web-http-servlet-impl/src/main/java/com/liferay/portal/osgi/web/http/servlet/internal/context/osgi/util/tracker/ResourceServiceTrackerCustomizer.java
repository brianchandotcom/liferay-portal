/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.context.osgi.util.tracker;

import com.liferay.portal.osgi.web.http.servlet.internal.context.LiferayContextController;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.registration.ResourceRegistration;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Dante Wang
 */
public class ResourceServiceTrackerCustomizer
	extends BaseServiceTrackerCustomizer
		<Object, AtomicReference<ResourceRegistration>> {

	public ResourceServiceTrackerCustomizer(
		BundleContext bundleContext,
		HttpServletEndpointController httpServletEndpointController,
		LiferayContextController liferayContextController) {

		super(
			bundleContext, httpServletEndpointController,
			liferayContextController);
	}

	@Override
	public AtomicReference<ResourceRegistration> addingService(
		ServiceReference<Object> serviceReference) {

		if (Objects.isNull(
				serviceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX)) &&
			Objects.isNull(
				serviceReference.getProperty(
					HttpWhiteboardConstants.
						HTTP_WHITEBOARD_RESOURCE_PATTERN))) {

			return null;
		}

		if (!liferayContextController.matches(serviceReference) ||
			!httpServletEndpointController.matches(serviceReference)) {

			return null;
		}

		AtomicReference<ResourceRegistration>
			resourceRegistrationAtomicReference = new AtomicReference<>();

		try {
			resourceRegistrationAtomicReference.set(
				liferayContextController.addResourceRegistration(
					serviceReference));
		}
		catch (Exception exception) {
			httpServletEndpointController.log(
				exception.getMessage(), exception);
		}

		return resourceRegistrationAtomicReference;
	}

}