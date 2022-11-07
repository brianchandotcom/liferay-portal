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
 * panel application. Applications are included within application categories
 * defined by {@link PanelCategory} implementations.
 *
 * @author Adolfo Pérez
 * @see    PanelEntry
 */
public interface PanelApp extends PanelEntry {

	/**
	 * Returns the number of notifications for the user.
	 *
	 * @param  user the user from which notifications are retrieved
	 * @return the number of notifications for the user
	 */
	public int getNotificationsCount(User user);

	/**
	 * Returns the portlet associated with the application.
	 *
	 * @return the portlet associated with the application
	 */
	public Portlet getPortlet();

	/**
	 * Returns the portlet's ID associated with the application.
	 *
	 * @return the portlet's ID associated with the application
	 */
	public String getPortletId();

	/**
	 * Returns the URL used to render a portlet based on the servlet request
	 * attributes.
	 *
	 * @param  httpServletRequest the servlet request used to create a portlet's
	 *         URL
	 * @return the portlet's URL used to render a target portlet
	 * @throws PortalException if a portal exception occurred
	 */
	public PortletURL getPortletURL(HttpServletRequest httpServletRequest)
		throws PortalException;

	/**
	 * Returns <code>true</code> if the application successfully renders.
	 *
	 * @param  httpServletRequest the servlet request used in the rendering
	 *         process
	 * @param  httpServletResponse the servlet response used in the rendering
	 *         process
	 * @return <code>true</code> if the application successfully renders;
	 *         <code>false</code> otherwise
	 * @throws IOException if an IO exception occurred
	 */
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException;

	/**
	 * Sets the {@link GroupProvider} associated with the application.
	 *
	 * @param groupProvider the group provider associated with the application
	 */
	public void setGroupProvider(GroupProvider groupProvider);

	/**
	 * Sets the portlet associated with the application.
	 *
	 * @param portlet the portlet associated with the application
	 */
	public void setPortlet(Portlet portlet);

}