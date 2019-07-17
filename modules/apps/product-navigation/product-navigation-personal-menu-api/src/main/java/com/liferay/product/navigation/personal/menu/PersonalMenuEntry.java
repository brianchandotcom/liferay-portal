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

package com.liferay.product.navigation.personal.menu;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides an interface that defines the entries to be used by a {@code
 * liferay-product-navigation:user-personal-menu} tag instance to render a new
 * user personal menu entry.
 *
 * <p>
 * Implementations must be registered in the OSGi Registry. The user personal
 * menu entry order is determined by the {@code
 * product.navigation.personal.menu.entry.order} property value. The entry's
 * section placement in the menu is determined by the {@code
 * product.navigation.personal.menu.group} property value.
 * </p>
 *
 * @author Pei-Jung Lan
 */
public interface PersonalMenuEntry {

	/**
	 * Returns the icon name to display in the entry.
	 *
	 * @param  portletRequest the portlet request
	 * @return the icon name to display in the entry
	 */
	public default String getIcon(PortletRequest portletRequest) {
		return StringPool.BLANK;
	}

	/**
	 * Returns the label that is displayed in the user personal menu.
	 *
	 * @param  locale the label's retrieved locale
	 * @return the label of the user personal menu entry
	 */
	public String getLabel(Locale locale);

	/**
	 * Returns the URL used to render a portlet based on the servlet request
	 * attributes.
	 *
	 * @param  httpServletRequest the servlet request used to create a portlet's
	 *         URL
	 * @return the portlet's URL used to render a portlet
	 * @throws PortalException if a portal exception occurred
	 */
	public String getPortletURL(HttpServletRequest httpServletRequest)
		throws PortalException;

	/**
	 * Returns {@code true} if the entry is the current active entry.
	 *
	 * @param  portletRequest the portlet request
	 * @param  portletId the portlet's ID
	 * @return {@code true} if the entry is the current active entry; {@code
	 *         false} otherwise
	 */
	public default boolean isActive(
			PortletRequest portletRequest, String portletId)
		throws PortalException {

		return false;
	}

	/**
	 * Returns {@code true} if the entry should be displayed in the user
	 * personal menu.
	 *
	 * @param  portletRequest the portlet request
	 * @param  permissionChecker the permission checker
	 * @return {@code true} if the entry should be displayed in the user
	 *         personal menu; {@code false} otherwise
	 */
	public default boolean isShow(
			PortletRequest portletRequest, PermissionChecker permissionChecker)
		throws PortalException {

		return true;
	}

}