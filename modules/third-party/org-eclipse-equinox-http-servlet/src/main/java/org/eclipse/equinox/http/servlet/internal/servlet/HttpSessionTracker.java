/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.eclipse.equinox.http.servlet.internal.servlet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.http.*;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.util.EventListeners;

/**
 * @author Shuyang Zhou
 */
public class HttpSessionTracker {

	public static void clearHttpSessionAdaptors(String sessionId) {
		Set<HttpSessionAdaptor> httpSessionAdaptors =
			_httpSessionAdaptorsMap.remove(sessionId);

		if (httpSessionAdaptors == null) {
			return;
		}

		for (HttpSessionAdaptor httpSessionAdaptor : httpSessionAdaptors) {
			ContextController contextController =
				httpSessionAdaptor.getController();

			EventListeners eventListeners =
				contextController.getEventListeners();

			List<HttpSessionListener> httpSessionListeners = eventListeners.get(
				HttpSessionListener.class);

			if (!httpSessionListeners.isEmpty()) {
				HttpSessionEvent httpSessionEvent = new HttpSessionEvent(
					httpSessionAdaptor);

				for (HttpSessionListener listener : httpSessionListeners) {
					try {
						listener.sessionDestroyed(httpSessionEvent);
					}
					catch (IllegalStateException ise) {
						// outer session is already invalidated
					}
				}
			}

			List<HttpSessionAttributeListener> httpSessionAttributeListeners =
				eventListeners.get(HttpSessionAttributeListener.class);

			if (!httpSessionListeners.isEmpty()) {
				Enumeration<String> enumeration =
					httpSessionAdaptor.getAttributeNames();

				while (enumeration.hasMoreElements()) {
					HttpSessionBindingEvent httpSessionBindingEvent =
						new HttpSessionBindingEvent(
							httpSessionAdaptor, enumeration.nextElement());

					for (HttpSessionAttributeListener
							httpSessionAttributeListener :
								httpSessionAttributeListeners) {

						httpSessionAttributeListener.attributeRemoved(
							httpSessionBindingEvent);
					}
				}
			}

			contextController.removeActiveSession(httpSessionAdaptor.getId());
		}
	}

	public static void addHttpSessionAdaptor(
		HttpSessionAdaptor httpSessionAdaptor) {

		String sessionId = httpSessionAdaptor.getId();

		Set<HttpSessionAdaptor> httpSessionAdaptors =
			_httpSessionAdaptorsMap.get(sessionId);

		if (httpSessionAdaptors == null) {
			httpSessionAdaptors = Collections.newSetFromMap(
				new ConcurrentHashMap<HttpSessionAdaptor, Boolean>());

			Set<HttpSessionAdaptor> previousHttpSessionAdaptors =
				_httpSessionAdaptorsMap.putIfAbsent(
					sessionId, httpSessionAdaptors);

			if (previousHttpSessionAdaptors != null) {
				httpSessionAdaptors = previousHttpSessionAdaptors;
			}
		}

		httpSessionAdaptors.add(httpSessionAdaptor);
	}

	public static boolean removeHttpSessionAdaptor(
		HttpSessionAdaptor httpSessionAdaptor) {

		Set<HttpSessionAdaptor> httpSessionAdaptors =
			_httpSessionAdaptorsMap.get(httpSessionAdaptor.getId());

		if (httpSessionAdaptors == null) {
			return false;
		}

		return httpSessionAdaptors.remove(httpSessionAdaptor);
	}

	private static final ConcurrentMap<String, Set<HttpSessionAdaptor>>
		_httpSessionAdaptorsMap =
			new ConcurrentHashMap<String, Set<HttpSessionAdaptor>>();

}