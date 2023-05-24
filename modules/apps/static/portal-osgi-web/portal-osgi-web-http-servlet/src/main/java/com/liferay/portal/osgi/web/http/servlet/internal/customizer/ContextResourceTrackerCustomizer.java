/*******************************************************************************
 * Copyright (c) 2014 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.customizer;

import com.liferay.portal.osgi.web.http.servlet.internal.HttpServiceRuntimeImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.error.HttpWhiteboardFailureException;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.ResourceRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.util.StringPlus;

import java.util.concurrent.atomic.AtomicReference;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.runtime.dto.DTOConstants;
import org.osgi.service.http.runtime.dto.FailedResourceDTO;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Raymond Augé
 */
public class ContextResourceTrackerCustomizer
	extends RegistrationServiceTrackerCustomizer
		<Object, AtomicReference<ResourceRegistration>> {

	public ContextResourceTrackerCustomizer(
		BundleContext bundleContext,
		HttpServiceRuntimeImpl httpServiceRuntimeImpl,
		ContextController contextController) {

		super(bundleContext, httpServiceRuntimeImpl);

		_contextController = contextController;
	}

	@Override
	public AtomicReference<ResourceRegistration> addingService(
		ServiceReference<Object> serviceReference) {

		Object resourcePrefix = serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX);

		Object resourcePattern = serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN);

		if (((resourcePrefix == null) && (resourcePattern == null)) ||
			!_contextController.matches(serviceReference)) {

			return null;
		}

		if (!httpServiceRuntimeImpl.matches(serviceReference)) {
			return null;
		}

		AtomicReference<ResourceRegistration> result = new AtomicReference<>();

		try {
			result.set(
				_contextController.addResourceRegistration(serviceReference));
		}
		catch (HttpWhiteboardFailureException httpWhiteboardFailureException) {
			httpServiceRuntimeImpl.log(
				httpWhiteboardFailureException.getMessage(),
				httpWhiteboardFailureException);

			_recordFailedResourceDTO(
				serviceReference,
				httpWhiteboardFailureException.getFailureReason());
		}
		catch (Exception exception) {
			httpServiceRuntimeImpl.log(exception.getMessage(), exception);

			_recordFailedResourceDTO(
				serviceReference,
				DTOConstants.FAILURE_REASON_EXCEPTION_ON_INIT);
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

		ResourceRegistration resourceRegistration = resourceReference.get();

		if (resourceRegistration != null) {
			resourceRegistration.destroy();
		}

		httpServiceRuntimeImpl.removeFailedResourceDTO(serviceReference);
	}

	private void _recordFailedResourceDTO(
		ServiceReference<Object> serviceReference, int failureReason) {

		FailedResourceDTO failedResourceDTO = new FailedResourceDTO();

		failedResourceDTO.failureReason = failureReason;
		failedResourceDTO.patterns = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN));
		failedResourceDTO.prefix = (String)serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX);
		failedResourceDTO.serviceId = (Long)serviceReference.getProperty(
			Constants.SERVICE_ID);
		failedResourceDTO.servletContextId = _contextController.getServiceId();

		httpServiceRuntimeImpl.recordFailedResourceDTO(
			serviceReference, failedResourceDTO);
	}

	private final ContextController _contextController;

}