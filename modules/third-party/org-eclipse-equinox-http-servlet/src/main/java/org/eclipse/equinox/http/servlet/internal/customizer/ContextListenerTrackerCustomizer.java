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

import java.util.EventListener;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.equinox.http.servlet.internal.HttpServiceRuntimeImpl;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.error.HttpWhiteboardFailureException;
import org.eclipse.equinox.http.servlet.internal.registration.ListenerRegistration;
import org.eclipse.equinox.http.servlet.internal.util.StringPlus;
import org.osgi.framework.*;
import org.osgi.service.http.runtime.dto.DTOConstants;
import org.osgi.service.http.runtime.dto.FailedListenerDTO;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Raymond Augé
 */
public class ContextListenerTrackerCustomizer
	extends RegistrationServiceTrackerCustomizer<EventListener,  AtomicReference<ListenerRegistration>> {

	public ContextListenerTrackerCustomizer(
		BundleContext bundleContext, HttpServiceRuntimeImpl httpServiceRuntime,
		ContextController contextController) {

		super(bundleContext, httpServiceRuntime);

		this.contextController = contextController;
	}

	@Override
	public AtomicReference<ListenerRegistration> addingService(
		ServiceReference<EventListener> serviceReference) {

		if (serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER) == null) {
			return null;
		}

		if (!contextController.matches(serviceReference)) {
			return null;
		}

		if (!httpServiceRuntime.matches(serviceReference)) {
			return null;
		}

		AtomicReference<ListenerRegistration> result = new AtomicReference<ListenerRegistration>();

		try {
			Object listenerObj = serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER);

			if (!Boolean.class.isInstance(listenerObj) &&
				!String.class.isInstance(listenerObj)) {

				throw new HttpWhiteboardFailureException(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER + "=" + listenerObj + " is not a valid option. Ignoring!", //$NON-NLS-1$ //$NON-NLS-2$
					DTOConstants.FAILURE_REASON_VALIDATION_FAILED);
			}

			Boolean listener = (listenerObj instanceof Boolean) ? (Boolean)listenerObj : Boolean.parseBoolean((String)listenerObj);

			if (!listener.booleanValue()) {
				return result;
			}

			result.set(contextController.addListenerRegistration(serviceReference));
		}
		catch (HttpWhiteboardFailureException hwfe) {
			httpServiceRuntime.log(hwfe.getMessage(), hwfe);

			recordFailedListenerDTO(serviceReference, hwfe.getFailureReason());
		}
		catch (Exception e) {
			httpServiceRuntime.log(e.getMessage(), e);

			recordFailedListenerDTO(serviceReference, DTOConstants.FAILURE_REASON_EXCEPTION_ON_INIT);
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
			// Destroy now ungets the object we are using
			listenerRegistration.destroy();
		}

		contextController.getHttpServiceRuntime().removeFailedListenerDTO(serviceReference);
	}

	private void recordFailedListenerDTO(
		ServiceReference<EventListener> serviceReference, int failureReason) {

		FailedListenerDTO failedListenerDTO = new FailedListenerDTO();

		failedListenerDTO.failureReason = failureReason;
		failedListenerDTO.serviceId = (Long)serviceReference.getProperty(Constants.SERVICE_ID);
		failedListenerDTO.servletContextId = contextController.getServiceId();
		failedListenerDTO.types = StringPlus.from(
			serviceReference.getProperty(Constants.OBJECTCLASS)).toArray(new String[0]);

		contextController.getHttpServiceRuntime().recordFailedListenerDTO(serviceReference, failedListenerDTO);
	}

	private ContextController contextController;

}
