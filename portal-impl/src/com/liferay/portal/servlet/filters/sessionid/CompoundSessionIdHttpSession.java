/*
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

package com.liferay.portal.servlet.filters.sessionid;

import com.liferay.portal.kernel.servlet.HttpSessionWrapper;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpSession;

/**
 * @author Michael C. Han
 */
public class CompoundSessionIdHttpSession extends HttpSessionWrapper {

	public CompoundSessionIdHttpSession(HttpSession session) {
		this(session, PropsValues.SESSION_ID_DELIMITER);
	}

	public CompoundSessionIdHttpSession(
		HttpSession session,
		String sessionIdDelimiter) {

		super(session);

		_sessionIdDelimiter = sessionIdDelimiter;
	}

	@Override
	public String getId() {
		String sessionId = super.getId();

		if (_sessionIdDelimiter == null) {
			return sessionId;
		}
		else {
			int index = sessionId.indexOf(_sessionIdDelimiter);
			if (index == -1) {
				return sessionId;
			}
			else {
				return sessionId.substring(0, index - 1);
			}
		}
	}

	private String _sessionIdDelimiter;
}
