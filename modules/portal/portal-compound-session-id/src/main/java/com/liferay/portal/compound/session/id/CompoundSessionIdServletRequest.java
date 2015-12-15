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

package com.liferay.portal.compound.session.id;

import com.liferay.portal.kernel.servlet.PersistentHttpServletRequestWrapper;
import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdHttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Michael C. Han
 */
public class CompoundSessionIdServletRequest
	extends PersistentHttpServletRequestWrapper {

	public CompoundSessionIdServletRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public HttpSession getSession() {
		HttpSession session = super.getSession();

		return new CompoundSessionIdHttpSession(session);
	}

	@Override
	public HttpSession getSession(boolean create) {
		HttpSession session = super.getSession(create);

		if (session == null) {
			return session;
		}

		return new CompoundSessionIdHttpSession(session);
	}

}