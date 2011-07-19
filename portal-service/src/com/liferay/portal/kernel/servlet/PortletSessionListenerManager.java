/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdHttpSession;
import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdSplitterUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * <p>
 * See http://issues.liferay.com/browse/LEP-2299.
 * </p>
 *
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 */
public class PortletSessionListenerManager implements HttpSessionListener {

	public static void addHttpSessionListener(
		HttpSessionListener httpSessionListener) {

		_httpSessionListeners.add(httpSessionListener);
	}

	public static void removeHttpSessionListener(
		HttpSessionListener httpSessionListener) {

		_httpSessionListeners.remove(httpSessionListener);
	}

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		httpSessionEvent = getHttpSessionEvent(httpSessionEvent);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			for (HttpSessionListener httpSessionListener :
					_httpSessionListeners) {

				Class<?> clazz = httpSessionListener.getClass();

				ClassLoader classLoader = clazz.getClassLoader();

				currentThread.setContextClassLoader(classLoader);

				httpSessionListener.sessionCreated(httpSessionEvent);
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		httpSessionEvent = getHttpSessionEvent(httpSessionEvent);

		for (HttpSessionListener httpSessionListener : _httpSessionListeners) {
			httpSessionListener.sessionDestroyed(httpSessionEvent);
		}
	}

	protected HttpSessionEvent getHttpSessionEvent(
		HttpSessionEvent httpSessionEvent) {

		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
			CompoundSessionIdHttpSession compoundSessionIdHttpSession =
				new CompoundSessionIdHttpSession(httpSessionEvent.getSession());

			httpSessionEvent = new HttpSessionEvent(
				compoundSessionIdHttpSession);
		}

		return httpSessionEvent;
	}

	private static List<HttpSessionListener> _httpSessionListeners =
		new ArrayList<HttpSessionListener>();

}