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

package com.liferay.document.library.item.selector.web.internal.util;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DLBreadcrumbUtil {

	public static void addPortletBreadcrumbEntries(
			Folder folder, String displayStyle,
			HttpServletRequest httpServletRequest, PortletURL portletURL)
		throws Exception {

		portletURL.setParameter("displayStyle", displayStyle);

		_addPortletBreadcrumbEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, httpServletRequest,
			LanguageUtil.get(httpServletRequest, "home"), portletURL);

		if (folder != null) {
			List<Folder> ancestorFolders = folder.getAncestors();

			Collections.reverse(ancestorFolders);

			for (Folder ancestorFolder : ancestorFolders) {
				_addPortletBreadcrumbEntry(
					ancestorFolder.getFolderId(), httpServletRequest,
					ancestorFolder.getName(), portletURL);
			}

			_addPortletBreadcrumbEntry(
				folder.getFolderId(), httpServletRequest, folder.getName(),
				portletURL);
		}
	}

	private static void _addPortletBreadcrumbEntry(
		long folderId, HttpServletRequest httpServletRequest, String title,
		PortletURL portletURL) {

		portletURL.setParameter("folderId", String.valueOf(folderId));

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, title, portletURL.toString());
	}

}