/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.context.customizer;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;
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
		ContextController contextController) {

		super(bundleContext, contextController, httpServletEndpointController);
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

		if (!contextController.matches(serviceReference) ||
			!httpServletEndpointController.matches(serviceReference)) {

			return null;
		}

		AtomicReference<ResourceRegistration> result = new AtomicReference<>();

		try {
			result.set(
				contextController.addResourceRegistration(serviceReference));
		}
		catch (Exception exception) {
			httpServletEndpointController.log(
				exception.getMessage(), exception);
		}

		return result;
	}

	@Override
	public void modifiedService(
		ServiceReference<Object> serviceReference,
		AtomicReference<ResourceRegistration> resourceReference) {

		removedService(serviceReference, resourceReference);

		AtomicReference<ResourceRegistration> added = addingService(
			serviceReference);

		resourceReference.set(added.get());
	}

	@Override
	public void removedService(
		ServiceReference<Object> serviceReference,
		AtomicReference<ResourceRegistration> resourceReference) {

		ResourceRegistration registration = resourceReference.get();

		if (registration != null) {
			registration.destroy();
		}
	}

}