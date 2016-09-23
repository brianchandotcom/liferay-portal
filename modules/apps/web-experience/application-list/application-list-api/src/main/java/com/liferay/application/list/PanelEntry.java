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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Locale;

/**
 * Provides a basic interface for panel categories and implementations. To
 * create a new {@link PanelCategory} or {@link PanelApp} implementation, it is
 * necessary to implement its corresponding interface. Never implement this
 * interface directly.
 *
 * @author Adolfo Pérez
 * @see    PanelApp
 * @see    PanelCategory
 */
public interface PanelEntry {

	/**
	 * Returns the panel entry's key.
	 *
	 * @return the panel entry's key
	 */
	public String getKey();

	/**
	 * Returns the label that is displayed in the user interface when the panel
	 * entry is included.
	 *
	 * @param  locale the label's retrieved locale
	 * @return the label of the panel entry
	 */
	public String getLabel(Locale locale);

	/**
	 * Returns <code>true</code> if the panel entry should be displayed in the
	 * group's context.
	 *
	 * @param  permissionChecker the permission checker
	 * @param  group the group for which permissions are checked
	 * @return <code>true</code> if the Control Menu entry should be displayed
	 *         in the request's context; <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException;

}