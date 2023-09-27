/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Deepak Gothe
 */
public interface ServletObjectsFactory {

	public ServletConfig getServletConfig(
		PortletConfig portletConfig, PortletRequest portletRequest);

	public HttpServletRequest getServletRequest(PortletRequest portletRequest);

	public HttpServletResponse getServletResponse(
		PortletRequest portletRequest, PortletResponse portletResponse);

}