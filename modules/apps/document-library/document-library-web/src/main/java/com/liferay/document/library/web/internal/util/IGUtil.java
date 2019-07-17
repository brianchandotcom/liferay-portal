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

package com.liferay.document.library.web.internal.util;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class IGUtil {

	public static void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception {

		String mvcRenderCommandName = ParamUtil.getString(
			httpServletRequest, "mvcRenderCommandName");

		PortletURL portletURL = renderResponse.createRenderURL();

		if (mvcRenderCommandName.equals("/document_library/select_folder")) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			portletURL.setParameter(
				"mvcRenderCommandName", mvcRenderCommandName);
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, themeDisplay.translate("home"),
				portletURL.toString());
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/image_gallery_display/view");
		}

		long rootFolderId = getRootFolderId(httpServletRequest);

		List<Folder> ancestorFolders = Collections.emptyList();

		if ((folder != null) && (folder.getFolderId() != rootFolderId)) {
			ancestorFolders = folder.getAncestors();

			int indexOfRootFolder = -1;

			for (int i = 0; i < ancestorFolders.size(); i++) {
				Folder ancestorFolder = ancestorFolders.get(i);

				if (rootFolderId == ancestorFolder.getFolderId()) {
					indexOfRootFolder = i;
				}
			}

			if (indexOfRootFolder > -1) {
				ancestorFolders = ancestorFolders.subList(0, indexOfRootFolder);
			}
		}

		Collections.reverse(ancestorFolders);

		for (Folder ancestorFolder : ancestorFolders) {
			portletURL.setParameter(
				"folderId", String.valueOf(ancestorFolder.getFolderId()));

			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, ancestorFolder.getName(),
				portletURL.toString());
		}

		portletURL.setParameter(
			"folderId", String.valueOf(folder.getFolderId()));

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, folder.getName(), portletURL.toString());
	}

	public static void addPortletBreadcrumbEntries(
			long folderId, HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		addPortletBreadcrumbEntries(
			DLAppLocalServiceUtil.getFolder(folderId), httpServletRequest,
			renderResponse);
	}

	protected static long getRootFolderId(HttpServletRequest httpServletRequest)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getPortletPreferences(
				httpServletRequest,
				PortalUtil.getPortletId(httpServletRequest));

		return GetterUtil.getLong(
			portletPreferences.getValue(
				"rootFolderId",
				String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)));
	}

}