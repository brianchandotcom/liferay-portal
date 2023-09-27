/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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