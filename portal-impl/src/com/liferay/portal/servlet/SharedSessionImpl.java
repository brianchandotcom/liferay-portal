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

import com.liferay.portal.kernel.servlet.SharedSession;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.resiliency.spi.agent.SPIAgentRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Raymond Augé
 */
public class SharedSessionImpl implements SharedSession {

	@Override
	public HttpSession getSharedSessionWrapper(
		HttpSession portalSession, HttpServletRequest request) {

		HttpSession portletSession = request.getSession();

		SPIAgentRequest.populatePortletSessionAttributes(
			request, portalSession);

		if (ServerDetector.isJetty()) {
			return new JettySharedSessionWrapper(portalSession, portletSession);
		}

		return new SharedSessionWrapper(portalSession, portletSession);
	}

}