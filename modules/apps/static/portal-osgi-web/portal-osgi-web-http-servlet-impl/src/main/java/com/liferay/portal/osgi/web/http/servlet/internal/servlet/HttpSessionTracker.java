/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.util.EventListeners;

/**
 * @author Dante Wang
 */
public class HttpSessionTracker {

	public static void addHttpSessionWrapper(
		HttpSessionWrapper httpSessionWrapper) {

		String sessionId = httpSessionWrapper.getId();

		Set<HttpSessionWrapper> httpSessionWrappers =
			_httpSessionWrappersMap.computeIfAbsent(
				sessionId,
				key -> Collections.newSetFromMap(new ConcurrentHashMap<>()));

		httpSessionWrappers.add(httpSessionWrapper);
	}

	public static void clearHttpSessionWrappers(String sessionId) {
		Set<HttpSessionWrapper> httpSessionWrappers =
			_httpSessionWrappersMap.remove(sessionId);

		if (httpSessionWrappers == null) {
			return;
		}

		for (HttpSessionWrapper httpSessionWrapper : httpSessionWrappers) {
			ContextController contextController =
				httpSessionWrapper.getContextController();

			EventListeners eventListeners =
				contextController.getEventListeners();

			List<HttpSessionListener> httpSessionListeners = eventListeners.get(
				HttpSessionListener.class);

			if (!httpSessionListeners.isEmpty()) {
				HttpSessionEvent httpSessionEvent = new HttpSessionEvent(
					httpSessionWrapper);

				for (HttpSessionListener listener : httpSessionListeners) {
					try {
						listener.sessionDestroyed(httpSessionEvent);
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
					httpSessionWrapper.getAttributeNames();

				while (enumeration.hasMoreElements()) {
					HttpSessionBindingEvent httpSessionBindingEvent =
						new HttpSessionBindingEvent(
							httpSessionWrapper, enumeration.nextElement());

					for (HttpSessionAttributeListener
							httpSessionAttributeListener :
								httpSessionAttributeListeners) {

						httpSessionAttributeListener.attributeRemoved(
							httpSessionBindingEvent);
					}
				}
			}

			contextController.removeActiveSession(httpSessionWrapper.getId());
		}
	}

	public static boolean removeHttpSessionWrapper(
		HttpSessionWrapper httpSessionWrapper) {

		Set<HttpSessionWrapper> httpSessionWrappers =
			_httpSessionWrappersMap.get(httpSessionWrapper.getId());

		if ((httpSessionWrappers != null) &&
			httpSessionWrappers.remove(httpSessionWrapper)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HttpSessionTracker.class);

	private static final ConcurrentMap<String, Set<HttpSessionWrapper>>
		_httpSessionWrappersMap = new ConcurrentHashMap<>();

}