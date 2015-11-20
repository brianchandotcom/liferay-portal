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

package com.liferay.application.list;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;

import java.io.IOException;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public interface PanelApp extends PanelEntry {

	public int getNotificationsCount(User user);

	public Portlet getPortlet();

	public String getPortletId();

	public PortletURL getPortletURL(HttpServletRequest request)
		throws PortalException;

	public boolean include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException;

	public void setPortlet(Portlet portlet);

}