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

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.context.ServletContextHelperController;
import org.eclipse.equinox.http.servlet.internal.error.HttpWhiteboardFailureException;
import org.eclipse.equinox.http.servlet.internal.registration.ListenerRegistration;
import org.osgi.framework.*;
import org.osgi.service.http.runtime.dto.DTOConstants;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Raymond Augé
 */
public class ContextListenerTrackerCustomizer
	extends RegistrationServiceTrackerCustomizer<EventListener,  AtomicReference<ListenerRegistration>> {

	public ContextListenerTrackerCustomizer(
		BundleContext bundleContext, HttpServletEndpointController httpServletEndpointController,
		ServletContextHelperController servletContextHelperController) {

		super(bundleContext, httpServletEndpointController);

		this.servletContextHelperController = servletContextHelperController;
	}

	@Override
	public AtomicReference<ListenerRegistration> addingService(
		ServiceReference<EventListener> serviceReference) {

		if (serviceReference.getProperty(HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER) == null) {
			return null;
		}

		if (!servletContextHelperController.matches(serviceReference)) {
			return null;
		}

		if (!httpServletEndpointController.matches(serviceReference)) {
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

			result.set(servletContextHelperController.addListenerRegistration(serviceReference));
		}
		catch (HttpWhiteboardFailureException hwfe) {
			httpServletEndpointController.log(hwfe.getMessage(), hwfe);
		}
		catch (Exception e) {
			httpServletEndpointController.log(e.getMessage(), e);
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
	}

	private ServletContextHelperController servletContextHelperController;

}
/* @generated */