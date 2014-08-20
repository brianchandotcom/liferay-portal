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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.TransientValue;

import java.io.Serializable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

/**
 * @author Alexander Chow
 */
public class PortalSessionActivationListener
	implements HttpSessionActivationListener, Serializable {

	public static PortalSessionActivationListener getInstance() {
		return _instance;
	}

	public static PortalSessionActivationListener getInstance(
		HttpSession session) {

		TransientValue<PortalSessionActivationListener> transientValue =
			(TransientValue<PortalSessionActivationListener>)
				session.getAttribute(
					PortalSessionActivationListener.class.getName());

		PortalSessionActivationListener portalSessionActivationListener = null;

		if (transientValue != null) {
			portalSessionActivationListener = transientValue.getValue();
		}

		return portalSessionActivationListener;
	}

	public static void setInstance(HttpSession session) {
		TransientValue<PortalSessionActivationListener> transientValue =
			new TransientValue<PortalSessionActivationListener>(
				PortalSessionActivationListener.getInstance());

		session.setAttribute(
			PortalSessionActivationListener.class.getName(), transientValue);
	}

	@Override
	public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
		new PortalSessionCreator(httpSessionEvent);
	}

	@Override
	public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
	}

	private static final PortalSessionActivationListener _instance =
		new PortalSessionActivationListener();

}