/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.HttpSessionWrapper;

import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.session.AbstractSession;
import org.eclipse.jetty.server.session.AbstractSessionManager;

/**
 * @author Brian Wing Shun Chan
 */
public class JettyHttpSessionWrapper
	extends HttpSessionWrapper implements AbstractSessionManager.SessionIf {

	public JettyHttpSessionWrapper(HttpSession session) {
		super(session);

		_session = session;
	}

	@Override
	public AbstractSession getSession() {
		HttpSessionWrapper sessionWrapper = (HttpSessionWrapper)_session;

		JettySharedSessionWrapper jettySharedSessionWrapper =
			(JettySharedSessionWrapper)sessionWrapper.getWrappedSession();

		return jettySharedSessionWrapper.getSession();
	}

	private HttpSession _session;

}