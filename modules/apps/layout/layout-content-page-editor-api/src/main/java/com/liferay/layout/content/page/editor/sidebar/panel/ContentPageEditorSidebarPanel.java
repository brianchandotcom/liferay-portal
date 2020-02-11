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

	public default boolean isVisible(boolean pageIsDisplayPage) {
		return true;
	}

	public default boolean isVisible(
		PermissionChecker permissionChecker, long plid,
		boolean pageIsDisplayPage) {

		return true;
	}

}