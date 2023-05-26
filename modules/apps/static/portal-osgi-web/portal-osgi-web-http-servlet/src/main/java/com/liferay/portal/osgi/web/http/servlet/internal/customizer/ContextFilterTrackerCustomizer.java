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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.HttpServiceRuntimeImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.error.HttpWhiteboardFailureException;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.FilterRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.util.ServiceProperties;
import com.liferay.portal.osgi.web.http.servlet.internal.util.StringPlus;

import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.Filter;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.runtime.dto.DTOConstants;
import org.osgi.service.http.runtime.dto.FailedFilterDTO;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Raymond Augé
 */
public class ContextFilterTrackerCustomizer
	extends RegistrationServiceTrackerCustomizer
		<Filter, AtomicReference<FilterRegistration>> {

	public ContextFilterTrackerCustomizer(
		BundleContext bundleContext,
		HttpServiceRuntimeImpl httpServiceRuntimeImpl,
		ContextController contextController) {

		super(bundleContext, httpServiceRuntimeImpl);

		_contextController = contextController;
	}

	@Override
	public AtomicReference<FilterRegistration> addingService(
		ServiceReference<Filter> serviceReference) {

		Object filterPattern = serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN);

		Object filterRegex = serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_REGEX);

		Object filterServlet = serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET);

		if ((filterPattern == null) && (filterRegex == null) &&
			(filterServlet == null)) {

			return null;
		}

		if (!_contextController.matches(serviceReference) ||
			!httpServiceRuntimeImpl.matches(serviceReference)) {

			return null;
		}

		AtomicReference<FilterRegistration> result = new AtomicReference<>();

		try {
			result.set(
				_contextController.addFilterRegistration(serviceReference));
		}
		catch (HttpWhiteboardFailureException httpWhiteboardFailureException) {
			httpServiceRuntimeImpl.log(
				httpWhiteboardFailureException.getMessage(),
				httpWhiteboardFailureException);

			_recordFailedFilterDTO(
				serviceReference,
				httpWhiteboardFailureException.getFailureReason());
		}
		catch (Exception exception) {
			httpServiceRuntimeImpl.log(exception.getMessage(), exception);

			_recordFailedFilterDTO(
				serviceReference,
				DTOConstants.FAILURE_REASON_EXCEPTION_ON_INIT);
		}

		return result;
	}

	@Override
	public void modifiedService(
		ServiceReference<Filter> serviceReference,
		AtomicReference<FilterRegistration> filterReference) {

		removedService(serviceReference, filterReference);

		AtomicReference<FilterRegistration> added = addingService(
			serviceReference);

		filterReference.set(added.get());
	}

	@Override
	public void removedService(
		ServiceReference<Filter> serviceReference,
		AtomicReference<FilterRegistration> filterReference) {

		FilterRegistration registration = filterReference.get();

		if (registration != null) {
			registration.destroy();
		}

		httpServiceRuntimeImpl.removeFailedFilterDTO(serviceReference);
	}

	private void _recordFailedFilterDTO(
		ServiceReference<Filter> serviceReference, int failureReason) {

		FailedFilterDTO failedFilterDTO = new FailedFilterDTO();

		failedFilterDTO.asyncSupported = GetterUtil.getBoolean(
			serviceReference.getProperty(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_FILTER_ASYNC_SUPPORTED));
		failedFilterDTO.dispatcher = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_DISPATCHER));
		failedFilterDTO.failureReason = failureReason;
		failedFilterDTO.initParams = ServiceProperties.parseInitParams(
			serviceReference,
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_INIT_PARAM_PREFIX);
		failedFilterDTO.name = (String)serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME);

		failedFilterDTO.patterns = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN));

		failedFilterDTO.regexs = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_REGEX));

		failedFilterDTO.serviceId = (Long)serviceReference.getProperty(
			Constants.SERVICE_ID);

		failedFilterDTO.servletContextId = _contextController.getServiceId();

		failedFilterDTO.servletNames = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET));

		httpServiceRuntimeImpl.recordFailedFilterDTO(
			serviceReference, failedFilterDTO);
	}

	private final ContextController _contextController;

}