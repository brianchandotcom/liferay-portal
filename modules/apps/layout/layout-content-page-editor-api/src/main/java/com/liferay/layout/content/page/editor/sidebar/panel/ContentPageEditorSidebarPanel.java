/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.sidebar.panel;

import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public interface ContentPageEditorSidebarPanel {

	public String getIcon();

	public String getId();

	public String getLabel(Locale locale);

	public default String getURL(HttpServletRequest httpServletRequest) {
		return null;
	}

	public default boolean includeSeparator() {
		return false;
	}

	public default boolean isLink() {
		return false;
	}

	public default boolean isVisible(
		PermissionChecker permissionChecker, long plid, int layoutType) {

		return true;
	}

}