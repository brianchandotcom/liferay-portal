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
 * Provides an interface that defines applications category to be used by a
 * <code>liferay-application-list:panel-category</code> tag instance to render
 * a new panel application category. Applications categories include
 * applications defined by {@link PanelApp} implementations.
 *
 * @see PanelEntry
 *
 * @author Adolfo Pérez
 */
public interface PanelCategory extends PanelEntry {

	/**
	 * Returns number of notifications for the user on this application
	 * category.
	 *
	 * @param panelCategoryHelper the {@link PanelCategoryHelper} to facilitate
	 *        method's implementation
	 * @param permissionChecker the {@link PermissionChecker} to be able to
	 *         check user's permissions
	 * @param group the group instance to check for notifications
	 * @param user the user to get notifications count
	 * @return number of notifications for the user on this panel category
	 */
	public int getNotificationsCount(
		PanelCategoryHelper panelCategoryHelper,
		PermissionChecker permissionChecker, Group group, User user);

	/**
	 * Renders application category body view.
	 *
	 * @param request the servlet request to be used in rendering process
	 * @param response the servlet response to be used in rendering process
	 * @return <code>true</code> in case of successful category body rendering,
	 * 		   <code>false</code> otherwise.
	 *
	 * @throws IOException if an IOException occured
	 */
	public boolean include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException;

	/**
	 * Renders application category header view.
	 *
	 * @param request the servlet request to be used in rendering process
	 * @param response the servlet response to be used in rendering process
	 * @return <code>true</code> in case of successful header rendering,
	 * 		   <code>false</code> otherwise.
	 *
	 * @throws IOException if an IOException occured
	 */
	public boolean includeHeader(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException;

	/**
	 * Checks if the application category is active.
	 *
	 * @param request the servlet request
	 * @param panelCategoryHelper the {@link PanelCategoryHelper} to facilitate
	 *        method's implementation
	 * @param group the group instance to check the state of the application
	 *        category
	 * @return <code>true</code> if the category is active; <code>false</code>
	 *         otherwise;
	 */
	public boolean isActive(
		HttpServletRequest request, PanelCategoryHelper panelCategoryHelper,
		Group group);

	/**
	 * Checks if the state of the category is persisted.
	 *
	 * @return <code>true</code> if the state of the category is persisted;
	 * 		   <code>false</code> otherwise;
	 */
	public boolean isPersistState();

}