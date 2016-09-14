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
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;

import java.io.IOException;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides an interface that defines applications to be used by a
 * <code>liferay-application-list:panel-app</code> tag instance to render a new
 * panel application. Applications included within application categories
 * defined by {@link PanelCategory} implementations.
 *
 * @see PanelEntry
 *
 * @author Adolfo Pérez
 */
public interface PanelApp extends PanelEntry {

	/**
	 * Returns the number of notifications for the user.
	 *
	 * @param user the user to get notifications count
	 * @return notifications count for the user
	 */
	public int getNotificationsCount(User user);

	/**
	 * Returns the portlet associated with the application
	 *
	 * @return the portlet associated with the application
	 */
	public Portlet getPortlet();

	/**
	 * Returns the portlet's id associated with the application
	 *
	 * @return the portlet's id associated with the application
	 */
	public String getPortletId();

	/**
	 * Returns the URL used to render an associated portlet based on the
	 * servlet request attributes
	 *
	 * @param request the servlet request to create a portlet's URL
	 * @return the portlet's URL to be used to render a target portlet
	 *
	 * @throws PortalException if an Portal exception occurred
	 */
	public PortletURL getPortletURL(HttpServletRequest request)
		throws PortalException;

	/**
	 * Renders the application view.
	 *
	 * @param request the servlet request to be used in rendering process
	 * @param response the servlet response to be used in rendering process
	 * @return <code>true</code> in case of successful application rendering,
	 * 		   <code>false</code> otherwise.
	 *
	 * @throws IOException if an IO exception occurred
	 */
	public boolean include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException;

	/**
	 * Sets {@link GroupProvider} associated with the application
	 *
	 * @param groupProvider the group provider associated with the application
	 */
	public void setGroupProvider(GroupProvider groupProvider);

	/**
	 * Sets the portlet associated with the application
	 *
	 * @param portlet the portlet associated with the application
	 */
	public void setPortlet(Portlet portlet);

}