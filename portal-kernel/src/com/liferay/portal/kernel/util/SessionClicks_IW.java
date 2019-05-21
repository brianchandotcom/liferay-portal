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

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public class SessionClicks_IW {
	public static SessionClicks_IW getInstance() {
		return _instance;
	}

	public java.lang.String get(
		javax.servlet.http.HttpServletRequest httpServletRequest,
		java.lang.String key, java.lang.String defaultValue) {
		return SessionClicks.get(httpServletRequest, key, defaultValue);
	}

	public java.lang.String get(
		javax.servlet.http.HttpServletRequest httpServletRequest,
		java.lang.String namespace, java.lang.String key,
		java.lang.String defaultValue) {
		return SessionClicks.get(httpServletRequest, namespace, key,
			defaultValue);
	}

	public java.lang.String get(javax.servlet.http.HttpSession session,
		java.lang.String key, java.lang.String defaultValue) {
		return SessionClicks.get(session, key, defaultValue);
	}

	public java.lang.String get(javax.servlet.http.HttpSession session,
		java.lang.String namespace, java.lang.String key,
		java.lang.String defaultValue) {
		return SessionClicks.get(session, namespace, key, defaultValue);
	}

	public void put(javax.servlet.http.HttpServletRequest httpServletRequest,
		java.lang.String key, java.lang.String value) {
		SessionClicks.put(httpServletRequest, key, value);
	}

	public void put(javax.servlet.http.HttpServletRequest httpServletRequest,
		java.lang.String namespace, java.lang.String key, java.lang.String value) {
		SessionClicks.put(httpServletRequest, namespace, key, value);
	}

	public void put(javax.servlet.http.HttpSession session,
		java.lang.String key, java.lang.String value) {
		SessionClicks.put(session, key, value);
	}

	public void put(javax.servlet.http.HttpSession session,
		java.lang.String namespace, java.lang.String key, java.lang.String value) {
		SessionClicks.put(session, namespace, key, value);
	}

	private SessionClicks_IW() {
	}

	private static SessionClicks_IW _instance = new SessionClicks_IW();
}