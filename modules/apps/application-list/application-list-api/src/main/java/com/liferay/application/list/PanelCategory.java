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

import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides an interface that defines application categories to be used by a
 * <code>liferay-application-list:panel-category</code> tag instance to render a
 * new panel application category. Application categories include applications
 * defined by {@link PanelApp} implementations.
 *
 * @author Adolfo Pérez
 * @see    PanelEntry
 */
public interface PanelCategory extends PanelEntry {

	/**
	 * Returns the number of notifications for the user in this application
	 * category.
	 *
	 * @param  panelCategoryHelper the {@link PanelCategoryHelper} to facilitate
	 *         the method's implementation
	 * @param  permissionChecker the <code>PermissionChecker</code> (in
	 *         <code>portal-kernel</code>) used to check the user's permissions
	 * @param  group the group for which notifications are checked
	 * @param  user the user from which notifications are retrieved
	 * @return the number of notifications for the user in the application
	 *         category
	 */
	public int getNotificationsCount(
		PanelCategoryHelper panelCategoryHelper,
		PermissionChecker permissionChecker, Group group, User user);

	/**
	 * Returns <code>true</code> if the category body renders successfully.
	 *
	 * @param  httpServletRequest the servlet request used in the rendering
	 *         process
	 * @param  httpServletResponse the servlet response used in the rendering
	 *         process
	 * @return <code>true</code> if the category body renders successfully;
	 *         <code>false</code> otherwise
	 * @throws IOException if an IO exception occurred
	 */
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException;

	/**
	 * Returns <code>true</code> if the category header renders successfully.
	 *
	 * @param  httpServletRequest the servlet request used in the rendering
	 *         process
	 * @param  httpServletResponse the servlet response used in the rendering
	 *         process
	 * @return <code>true</code> if the category header renders successfully;
	 *         <code>false</code> otherwise
	 * @throws IOException if an IO exception occurred
	 */
	public boolean includeHeader(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException;

	/**
	 * Returns <code>true</code> if the application category is active.
	 *
	 * @param  httpServletRequest the servlet request
	 * @param  panelCategoryHelper the {@link PanelCategoryHelper} to facilitate
	 *         the method's implementation
	 * @param  group the group for which the state of the application category
	 *         is checked
	 * @return <code>true</code> if the application category is active;
	 *         <code>false</code> otherwise
	 */
	public boolean isActive(
		HttpServletRequest httpServletRequest,
		PanelCategoryHelper panelCategoryHelper, Group group);

	/**
	 * Returns <code>true</code> if the state of the category is persisted.
	 *
	 * @return <code>true</code> if the state of the category is persisted;
	 *         <code>false</code> otherwise
	 */
	public boolean isPersistState();

}