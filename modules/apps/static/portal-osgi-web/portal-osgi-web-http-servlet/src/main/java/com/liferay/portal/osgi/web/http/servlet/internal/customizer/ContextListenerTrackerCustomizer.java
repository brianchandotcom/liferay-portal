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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.HttpServiceRuntimeImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.error.HttpWhiteboardFailureException;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.ListenerRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.util.StringPlus;

import java.util.EventListener;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.runtime.dto.DTOConstants;
import org.osgi.service.http.runtime.dto.FailedListenerDTO;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Raymond Augé
 */
public class ContextListenerTrackerCustomizer
	extends RegistrationServiceTrackerCustomizer
		<EventListener, AtomicReference<ListenerRegistration>> {

	public ContextListenerTrackerCustomizer(
		BundleContext bundleContext,
		HttpServiceRuntimeImpl httpServiceRuntimeImpl,
		ContextController contextController) {

		super(bundleContext, httpServiceRuntimeImpl);

		_contextController = contextController;
	}

	@Override
	public AtomicReference<ListenerRegistration> addingService(
		ServiceReference<EventListener> serviceReference) {

		Object listenerObject = serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER);

		if ((listenerObject == null) ||
			!_contextController.matches(serviceReference) ||
			!httpServiceRuntimeImpl.matches(serviceReference)) {

			return null;
		}

		AtomicReference<ListenerRegistration> result = new AtomicReference<>();

		try {
			if (!(listenerObject instanceof Boolean) &&
				!(listenerObject instanceof String)) {

				throw new HttpWhiteboardFailureException(
					StringBundler.concat(
						HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER, "=",
						listenerObject, " is not a valid option. Ignoring!"),
					DTOConstants.FAILURE_REASON_VALIDATION_FAILED);
			}

			boolean listener = Boolean.parseBoolean(
				String.valueOf(listenerObject));

			if (!listener) {
				return result;
			}

			result.set(
				_contextController.addListenerRegistration(serviceReference));
		}
		catch (HttpWhiteboardFailureException httpWhiteboardFailureException) {
			_log.error(httpWhiteboardFailureException);

			_recordFailedListenerDTO(
				serviceReference,
				httpWhiteboardFailureException.getFailureReason());
		}
		catch (Exception exception) {
			_log.error(exception);

			_recordFailedListenerDTO(
				serviceReference,
				DTOConstants.FAILURE_REASON_EXCEPTION_ON_INIT);
		}

		return result;
	}

	@Override
	public void modifiedService(
		ServiceReference<EventListener> serviceReference,
		AtomicReference<ListenerRegistration> listenerRegistration) {

		removedService(serviceReference, listenerRegistration);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<EventListener> serviceReference,
		AtomicReference<ListenerRegistration> listenerReference) {

		ListenerRegistration listenerRegistration = listenerReference.get();

		if (listenerRegistration != null) {
			listenerRegistration.destroy();
		}

		httpServiceRuntimeImpl.removeFailedListenerDTO(serviceReference);
	}

	private void _recordFailedListenerDTO(
		ServiceReference<EventListener> serviceReference, int failureReason) {

		FailedListenerDTO failedListenerDTO = new FailedListenerDTO();

		failedListenerDTO.failureReason = failureReason;
		failedListenerDTO.serviceId = (Long)serviceReference.getProperty(
			Constants.SERVICE_ID);
		failedListenerDTO.servletContextId = _contextController.getServiceId();
		failedListenerDTO.types = StringPlus.from(
			serviceReference.getProperty(Constants.OBJECTCLASS));

		httpServiceRuntimeImpl.recordFailedListenerDTO(
			serviceReference, failedListenerDTO);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContextListenerTrackerCustomizer.class.getName());

	private final ContextController _contextController;

}