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

package com.liferay.document.library.portlet.toolbar.contributor;

import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Adolfo Pérez
 */
public interface DLPortletToolbarContributor extends PortletToolbarContributor {

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public default List<MenuItem> getPortletTitleAddDocumentMenuItems(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		return Collections.emptyList();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public default MenuItem getPortletTitleAddFolderMenuItem(
		ThemeDisplay themeDisplay, PortletRequest portletRequest,
		Folder folder) {

		return null;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public default MenuItem getPortletTitleAddMultipleDocumentsMenuItem(
		ThemeDisplay themeDisplay, PortletRequest portletRequest,
		Folder folder) {

		return null;
	}

}