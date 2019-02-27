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

package com.liferay.product.navigation.user.personal.menu;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides an interface that defines the entries to be used by a
 * <code>liferay-product-navigation:user-personal-menu</code> tag instance to
 * render a new user personal menu entry.
 *
 * <p>
 * Implementations must be registered in the OSGi Registry. The order of the
 * user personal menu entry is determined by the
 * <code>product.navigation.user.personal.menu.entry.order</code> property value.
 * The section where the entry goes in the menu is determined by the
 * <code>product.navigation.user.personal.menu.group</code> property value.
 * </p>
 *
 * @author Pei-Jung Lan
 * @review
 */
public interface UserPersonalMenuEntry {

	/**
	 * Returns the label that is displayed in the user personal menu.
	 *
	 * @param  locale the label's retrieved locale
	 * @return the label of the user personal menu entry
	 * @review
	 */
	public String getLabel(Locale locale);

	/**
	 * Returns the URL used to render a portlet based on the servlet request
	 * attributes.
	 *
	 * @param  request the servlet request used to create a portlet's URL
	 * @return the portlet's URL used to render a target portlet
	 * @throws PortalException if a portal exception occurred
	 * @review
	 */
	public String getPortletURL(HttpServletRequest request) throws Exception;

	/**
	 * Returns <code>true</code> if the entry should be displayed in the
	 * user personal menu.
	 *
	 * @param  permissionChecker the permission checker
	 * @return <code>true</code> if the entry should be displayed in the user
	 *         personal menu; <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @review
	 */
	public default boolean isShow(PermissionChecker permissionChecker)
		throws PortalException {

		return true;
	}

}