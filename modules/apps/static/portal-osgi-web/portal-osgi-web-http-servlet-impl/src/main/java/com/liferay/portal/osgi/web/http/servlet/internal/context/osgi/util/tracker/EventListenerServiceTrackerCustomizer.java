/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.context.osgi.util.tracker;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.context.LiferayContextController;

import java.util.EventListener;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.error.HttpWhiteboardFailureException;
import org.eclipse.equinox.http.servlet.internal.registration.ListenerRegistration;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.runtime.dto.DTOConstants;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Dante Wang
 */
public class EventListenerServiceTrackerCustomizer
	extends BaseServiceTrackerCustomizer
		<EventListener, AtomicReference<ListenerRegistration>> {

	public EventListenerServiceTrackerCustomizer(
		BundleContext bundleContext,
		HttpServletEndpointController httpServletEndpointController,
		LiferayContextController liferayContextController) {

		super(
			bundleContext, httpServletEndpointController,
			liferayContextController);
	}

	@Override
	public AtomicReference<ListenerRegistration> addingService(
		ServiceReference<EventListener> serviceReference) {

		Object listenerObject = serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER);

		if ((listenerObject == null) ||
			!liferayContextController.matches(serviceReference) ||
			!httpServletEndpointController.matches(serviceReference)) {

			return null;
		}

		AtomicReference<ListenerRegistration>
			listenerRegistrationAtomicReference = new AtomicReference<>();

		try {
			if (!(listenerObject instanceof Boolean) &&
				!(listenerObject instanceof String)) {

				throw new HttpWhiteboardFailureException(
					StringBundler.concat(
						HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER, "=",
						listenerObject, " is not valid"),
					DTOConstants.FAILURE_REASON_VALIDATION_FAILED);
			}

			if (!GetterUtil.getBoolean(listenerObject)) {
				return listenerRegistrationAtomicReference;
			}

			listenerRegistrationAtomicReference.set(
				liferayContextController.addListenerRegistration(
					serviceReference));
		}
		catch (Exception exception) {
			httpServletEndpointController.log(
				exception.getMessage(), exception);
		}

		return listenerRegistrationAtomicReference;
	}

}