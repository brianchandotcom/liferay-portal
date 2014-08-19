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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalSessionContext {

	public static int count() {
		return _instance._count();
	}

	public static HttpSession get(String sessionId) {
		return _instance._get(sessionId);
	}

	public static void put(String sessionId, HttpSession session) {
		_instance._put(sessionId, session);
	}

	public static HttpSession remove(String sessionId) {
		return _instance._remove(sessionId);
	}

	public static Collection<HttpSession> values() {
		return _instance._values();
	}

	protected PortalSessionContext() {
		_sessionPool = new ConcurrentHashMap<String, HttpSession>();
	}

	private int _count() {
		return _sessionPool.size();
	}

	private HttpSession _get(String sessionId) {
		if (Validator.isNull(sessionId)) {
			return null;
		}

		return _sessionPool.get(sessionId);
	}

	private void _put(String sessionId, HttpSession session) {
		_sessionPool.put(sessionId, session);
	}

	private HttpSession _remove(String sessionId) {
		return _sessionPool.remove(sessionId);
	}

	private Collection<HttpSession> _values() {
		return _sessionPool.values();
	}

	private static final PortalSessionContext _instance =
		new PortalSessionContext();

	private final Map<String, HttpSession> _sessionPool;

}