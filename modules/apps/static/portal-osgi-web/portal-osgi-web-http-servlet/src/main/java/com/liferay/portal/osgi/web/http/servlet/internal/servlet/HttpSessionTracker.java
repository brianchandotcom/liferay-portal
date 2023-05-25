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

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.util.EventListeners;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Shuyang Zhou
 */
public class HttpSessionTracker {

	public static void addHttpSessionAdaptor(
		HttpSessionAdaptor httpSessionAdaptor) {

		String sessionId = httpSessionAdaptor.getId();

		Set<HttpSessionAdaptor> httpSessionAdaptors =
			_httpSessionAdaptorsMap.get(sessionId);

		if (httpSessionAdaptors == null) {
			httpSessionAdaptors = Collections.newSetFromMap(
				new ConcurrentHashMap<>());

			Set<HttpSessionAdaptor> previousHttpSessionAdaptors =
				_httpSessionAdaptorsMap.putIfAbsent(
					sessionId, httpSessionAdaptors);

			if (previousHttpSessionAdaptors != null) {
				httpSessionAdaptors = previousHttpSessionAdaptors;
			}
		}

		httpSessionAdaptors.add(httpSessionAdaptor);
	}

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

				for (HttpSessionListener httpSessionListener :
						httpSessionListeners) {

					try {
						httpSessionListener.sessionDestroyed(httpSessionEvent);
					}
					catch (IllegalStateException illegalStateException) {
						if (_log.isDebugEnabled()) {
							_log.debug(illegalStateException);
						}
					}
				}

				List<HttpSessionAttributeListener>
					httpSessionAttributeListeners = eventListeners.get(
						HttpSessionAttributeListener.class);

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

	public static void removeHttpSessionAdaptor(
		HttpSessionAdaptor httpSessionAdaptor) {

		Set<HttpSessionAdaptor> httpSessionAdaptors =
			_httpSessionAdaptorsMap.get(httpSessionAdaptor.getId());

		if (httpSessionAdaptors == null) {
			return;
		}

		httpSessionAdaptors.remove(httpSessionAdaptor);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HttpSessionTracker.class.getName());

	private static final ConcurrentMap<String, Set<HttpSessionAdaptor>>
		_httpSessionAdaptorsMap = new ConcurrentHashMap<>();

}