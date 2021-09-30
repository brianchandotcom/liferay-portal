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

package com.liferay.shielded.container.internal.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Shuyang Zhou
 */
public class ShieldedContainerHttpSessionListener
	implements HttpSessionListener {

	public ShieldedContainerHttpSessionListener(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();

		httpSession.setAttribute(
			ShieldedContainerHttpSessionActivationListener.NAME,
			new ShieldedContainerHttpSessionActivationListener());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();

		_servletContext.removeAttribute(httpSession.getId());
	}

	private final ServletContext _servletContext;

}