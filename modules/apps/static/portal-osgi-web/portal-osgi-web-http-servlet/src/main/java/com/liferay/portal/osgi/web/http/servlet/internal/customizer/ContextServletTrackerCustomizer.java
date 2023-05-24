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
import com.liferay.portal.osgi.web.http.servlet.internal.registration.ServletRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.util.BooleanPlus;
import com.liferay.portal.osgi.web.http.servlet.internal.util.Const;
import com.liferay.portal.osgi.web.http.servlet.internal.util.ServiceProperties;
import com.liferay.portal.osgi.web.http.servlet.internal.util.StringPlus;

import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.Servlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.runtime.dto.DTOConstants;
import org.osgi.service.http.runtime.dto.FailedServletDTO;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Raymond Augé
 */
public class ContextServletTrackerCustomizer
	extends RegistrationServiceTrackerCustomizer
		<Servlet, AtomicReference<ServletRegistration>> {

	public ContextServletTrackerCustomizer(
		BundleContext bundleContext,
		HttpServiceRuntimeImpl httpServiceRuntimeImpl,
		ContextController contextController) {

		super(bundleContext, httpServiceRuntimeImpl);

		_contextController = contextController;
	}

	@Override
	public AtomicReference<ServletRegistration> addingService(
		ServiceReference<Servlet> serviceReference) {

		Object servletErrorPage = serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_ERROR_PAGE);

		Object servletName = serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME);

		Object servletPattern = serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN);

		if ((servletErrorPage == null) && (servletName == null) &&
			(servletPattern == null)) {

			return null;
		}

		if (!_contextController.matches(serviceReference) ||
			!httpServiceRuntimeImpl.matches(serviceReference)) {

			return null;
		}

		AtomicReference<ServletRegistration> result = new AtomicReference<>();

		try {
			result.set(
				_contextController.addServletRegistration(serviceReference));
		}
		catch (HttpWhiteboardFailureException httpWhiteboardFailureException) {
			httpServiceRuntimeImpl.log(
				httpWhiteboardFailureException.getMessage(),
				httpWhiteboardFailureException);

			_recordFailedServletDTO(
				serviceReference,
				httpWhiteboardFailureException.getFailureReason());
		}
		catch (Exception exception) {
			httpServiceRuntimeImpl.log(exception.getMessage(), exception);

			_recordFailedServletDTO(
				serviceReference,
				DTOConstants.FAILURE_REASON_EXCEPTION_ON_INIT);
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

		httpServiceRuntimeImpl.removeFailedServletDTOs(serviceReference);
	}

	private void _recordFailedServletDTO(
		ServiceReference<Servlet> serviceReference, int failureReason) {

		FailedServletDTO failedServletDTO = new FailedServletDTO();

		failedServletDTO.asyncSupported = BooleanPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_SERVLET_ASYNC_SUPPORTED),
			false);
		failedServletDTO.failureReason = failureReason;
		failedServletDTO.initParams = ServiceProperties.parseInitParams(
			serviceReference,
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX);
		failedServletDTO.name = (String)serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME);
		failedServletDTO.patterns = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN));
		failedServletDTO.serviceId = (Long)serviceReference.getProperty(
			Constants.SERVICE_ID);
		failedServletDTO.servletContextId = _contextController.getServiceId();
		failedServletDTO.servletInfo = Const.BLANK;

		httpServiceRuntimeImpl.recordFailedServletDTO(
			serviceReference, failedServletDTO);
	}

	private final ContextController _contextController;

}