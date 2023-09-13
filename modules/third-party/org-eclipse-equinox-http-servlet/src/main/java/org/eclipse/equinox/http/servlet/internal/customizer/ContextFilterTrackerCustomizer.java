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

package org.eclipse.equinox.http.servlet.internal.customizer;

import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.Filter;
import org.eclipse.equinox.http.servlet.internal.HttpServiceRuntimeImpl;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.error.HttpWhiteboardFailureException;
import org.eclipse.equinox.http.servlet.internal.registration.FilterRegistration;
import org.eclipse.equinox.http.servlet.internal.util.*;
import org.osgi.framework.*;
import org.osgi.service.http.runtime.dto.DTOConstants;
import org.osgi.service.http.runtime.dto.FailedFilterDTO;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Raymond Augé
 */
public class ContextFilterTrackerCustomizer
	extends RegistrationServiceTrackerCustomizer<Filter, AtomicReference<FilterRegistration>> {

	public ContextFilterTrackerCustomizer(
		BundleContext bundleContext, HttpServiceRuntimeImpl httpServiceRuntime,
		ContextController contextController) {

		super(bundleContext, httpServiceRuntime);

		this.contextController = contextController;
	}

	@Override
	public AtomicReference<FilterRegistration> addingService(
		ServiceReference<Filter> serviceReference) {

		if ((serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN) == null) &&
			(serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_REGEX) == null) &&
			(serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET) == null)) {

			return null;
		}

		if (!contextController.matches(serviceReference)) {
			return null;
		}

		if (!httpServiceRuntime.matches(serviceReference)) {
			return null;
		}

		AtomicReference<FilterRegistration> result = new AtomicReference<FilterRegistration>();

		try {
			result.set(contextController.addFilterRegistration(serviceReference));
		}
		catch (HttpWhiteboardFailureException hwfe) {
			httpServiceRuntime.log(hwfe.getMessage(), hwfe);

			recordFailedFilterDTO(serviceReference, hwfe.getFailureReason());
		}
		catch (Exception e) {
			httpServiceRuntime.log(e.getMessage(), e);

			recordFailedFilterDTO(serviceReference, DTOConstants.FAILURE_REASON_EXCEPTION_ON_INIT);
		}

		return result;
	}

	@Override
	public void modifiedService(
		ServiceReference<Filter> serviceReference,
		AtomicReference<FilterRegistration> filterReference) {

		removedService(serviceReference, filterReference);
		AtomicReference<FilterRegistration> added = addingService(serviceReference);
		filterReference.set(added.get());
	}

	@Override
	public void removedService(
		ServiceReference<Filter> serviceReference,
		AtomicReference<FilterRegistration> filterReference) {
		FilterRegistration registration = filterReference.get();
		if (registration != null) {
			// Destroy now ungets the object we are using
			registration.destroy();
		}

		contextController.getHttpServiceRuntime().removeFailedFilterDTO(serviceReference);
	}

	private void recordFailedFilterDTO(
		ServiceReference<Filter> serviceReference, int failureReason) {

		FailedFilterDTO failedFilterDTO = new FailedFilterDTO();

		failedFilterDTO.asyncSupported = BooleanPlus.from(
			serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_ASYNC_SUPPORTED), false);
		failedFilterDTO.dispatcher = StringPlus.from(
			serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_DISPATCHER)).toArray(new String[0]);
		failedFilterDTO.failureReason = failureReason;
		failedFilterDTO.initParams = ServiceProperties.parseInitParams(
			serviceReference, HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_INIT_PARAM_PREFIX);
		failedFilterDTO.name = (String)serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME);
		failedFilterDTO.patterns = StringPlus.from(
			serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN)).toArray(new String[0]);
		failedFilterDTO.regexs = StringPlus.from(
			serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_REGEX)).toArray(new String[0]);
		failedFilterDTO.serviceId = (Long)serviceReference.getProperty(Constants.SERVICE_ID);
		failedFilterDTO.servletContextId = contextController.getServiceId();
		failedFilterDTO.servletNames = StringPlus.from(
			serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET)).toArray(new String[0]);

		contextController.getHttpServiceRuntime().recordFailedFilterDTO(serviceReference, failedFilterDTO);
	}

	private ContextController contextController;

}
