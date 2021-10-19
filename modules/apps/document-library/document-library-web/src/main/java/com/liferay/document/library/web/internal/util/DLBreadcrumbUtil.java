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
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class DLBreadcrumbUtil {

	public static void addPortletBreadcrumbEntries(
			FileEntry fileEntry, HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception {

		Folder folder = fileEntry.getFolder();

		if (folder.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			addPortletBreadcrumbEntries(
				folder, httpServletRequest, renderResponse);
		}

		FileEntry unescapedFileEntry = fileEntry.toUnescapedModel();

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, unescapedFileEntry.getTitle(),
			PortletURLBuilder.createRenderURL(
				renderResponse
			).setMVCRenderCommandName(
				"/document_library/view_file_entry"
			).setParameter(
				"fileEntryId", fileEntry.getFileEntryId()
			).buildString());
	}

	public static void addPortletBreadcrumbEntries(
			FileShortcut fileShortcut, HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception {

		Folder folder = fileShortcut.getFolder();

		if (folder.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			addPortletBreadcrumbEntries(
				folder, httpServletRequest, renderResponse);
		}

		FileShortcut unescapedDLFileShortcut = fileShortcut.toUnescapedModel();

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, unescapedDLFileShortcut.getToTitle(),
			PortletURLBuilder.createRenderURL(
				renderResponse
			).setMVCRenderCommandName(
				"/document_library/view_file_entry"
			).setParameter(
				"fileEntryId", fileShortcut.getToFileEntryId()
			).buildString());
	}

	public static void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest httpServletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			"/document_library/view"
		).buildPortletURL();

		Map<String, Object> data = HashMapBuilder.<String, Object>put(
			"direction-right", Boolean.TRUE.toString()
		).put(
			"folder-id",
			() -> {
				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				DLPortletInstanceSettings dlPortletInstanceSettings =
					DLPortletInstanceSettings.getInstance(
						themeDisplay.getLayout(), portletDisplay.getId());

				return dlPortletInstanceSettings.getRootFolderId();
			}
		).build();

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, themeDisplay.translate("home"),
			portletURL.toString(), data);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/view_folder");

		addPortletBreadcrumbEntries(folder, httpServletRequest, portletURL);
	}

	public static void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest httpServletRequest,
			PortletURL portletURL)
		throws Exception {

		long rootFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		boolean ignoreRootFolder = ParamUtil.getBoolean(
			httpServletRequest, "ignoreRootFolder");

		if (!ignoreRootFolder) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			DLPortletInstanceSettings dlPortletInstanceSettings =
				DLPortletInstanceSettings.getInstance(
					themeDisplay.getLayout(), portletDisplay.getId());

			rootFolderId = dlPortletInstanceSettings.getRootFolderId();
		}

		List<Folder> ancestorFolders = Collections.emptyList();

		if ((folder != null) && (folder.getFolderId() != rootFolderId) &&
			!folder.isRoot()) {

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
				portletURL.toString(),
				HashMapBuilder.<String, Object>put(
					"direction-right", Boolean.TRUE.toString()
				).put(
					"folder-id", ancestorFolder.getFolderId()
				).build());
		}

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (folder != null) {
			folderId = folder.getFolderId();
		}

		portletURL.setParameter("folderId", String.valueOf(folderId));

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId != rootFolderId)) {

			Folder unescapedFolder = folder.toUnescapedModel();

			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, unescapedFolder.getName(),
				portletURL.toString(),
				HashMapBuilder.<String, Object>put(
					"direction-right", Boolean.TRUE.toString()
				).put(
					"folder-id", folderId
				).build());
		}
	}

	public static void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception {

		PortletURL portletURL = renderResponse.createRenderURL();

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (folder != null) {
			folderId = folder.getFolderId();
		}

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view");
		}

		addPortletBreadcrumbEntries(folder, httpServletRequest, portletURL);
	}

	public static void addPortletBreadcrumbEntries(
			long folderId, HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception {

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

			if (folder.getFolderId() !=
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				addPortletBreadcrumbEntries(
					folder, httpServletRequest, renderResponse);
			}
		}
	}

}