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

package com.liferay.portal.web.internal.session.replication;

import com.liferay.portal.kernel.servlet.PersistentHttpServletRequestWrapper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Dante Wang
 */
public class SessionReplicationHttpServletRequest
	extends PersistentHttpServletRequestWrapper {

	public SessionReplicationHttpServletRequest(
		HttpServletRequest httpServletRequest) {

		super(httpServletRequest);
	}

	@Override
	public HttpSession getSession() {
		HttpSession httpSession = super.getSession();

		if (httpSession == null) {
			return null;
		}

		httpSession = new SessionReplicationHttpSessionWrapper(httpSession);

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)getRequest();

		ServletContext servletContext = httpServletRequest.getServletContext();

		servletContext.setAttribute(httpSession.getId(), httpSession);

		return httpSession;
	}

	@Override
	public HttpSession getSession(boolean create) {
		HttpSession httpSession = super.getSession(create);

		if (httpSession == null) {
			return null;
		}

		return new SessionReplicationHttpSessionWrapper(httpSession);
	}

}