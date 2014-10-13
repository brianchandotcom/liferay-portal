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

import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdSplitterUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * See https://issues.liferay.com/browse/LEP-1466.
 * </p>
 *
 * @author Rudy Hilado
 * @author Shuyang Zhou
 */
public class PortletSessionTracker {

	public static void add(HttpSession session) {
		String sessionId = session.getId();

		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
			sessionId = CompoundSessionIdSplitterUtil.parseSessionId(sessionId);
		}

		Map<String, HttpSession> sessions = _sessions.get(sessionId);

		if (sessions == null) {
			sessions = new ConcurrentHashMap<String, HttpSession>();

			Map<String, HttpSession> previousSessions = _sessions.putIfAbsent(
				sessionId, sessions);

			if (previousSessions != null) {
				sessions = previousSessions;
			}
		}

		ServletContext servletContext = session.getServletContext();

		String contextPath = servletContext.getContextPath();

		// ConcurrentHashMap's read is faster than its write. This check is
		// logically unnecessary, but is a performance improvement.

		if (!sessions.containsKey(contextPath)) {
			sessions.put(contextPath, session);
		}
	}

	public static void invalidate(String sessionId) {
		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
			sessionId = CompoundSessionIdSplitterUtil.parseSessionId(sessionId);
		}

		Map<String, HttpSession> sessions = _sessions.remove(sessionId);

		if (sessions == null) {
			return;
		}

		for (HttpSession session : sessions.values()) {
			try {
				session.invalidate();
			}
			catch (Exception e) {
			}
		}
	}

	private static final ConcurrentMap<String, Map<String, HttpSession>>
		_sessions = new ConcurrentHashMap<String, Map<String, HttpSession>>();

}