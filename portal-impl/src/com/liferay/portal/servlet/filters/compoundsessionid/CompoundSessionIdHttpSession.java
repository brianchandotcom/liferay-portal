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

package com.liferay.portal.servlet.filters.compoundsessionid;

import com.liferay.portal.kernel.servlet.HttpSessionWrapper;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpSession;

/**
 * @author Michael C. Han
 */
public class CompoundSessionIdHttpSession extends HttpSessionWrapper {

	public CompoundSessionIdHttpSession(HttpSession session) {
		this(session, null);
	}

	public CompoundSessionIdHttpSession(
		HttpSession session, String sessionIdDelimiter) {

		super(session);

		if (sessionIdDelimiter == null) {
			_sessionIdDelimiter =
				CompoundSessionIdFilter.getSessionIdDelimiter();
		}
		else {
			_sessionIdDelimiter = sessionIdDelimiter;
		}
	}

	@Override
	public String getId() {
		String sessionId = super.getId();

		if (Validator.isNull(_sessionIdDelimiter)) {
			return sessionId;
		}

		int pos = sessionId.indexOf(_sessionIdDelimiter);

		if (pos == -1) {
			return sessionId;
		}

		return sessionId.substring(0, pos - 1);
	}

	private String _sessionIdDelimiter;

}