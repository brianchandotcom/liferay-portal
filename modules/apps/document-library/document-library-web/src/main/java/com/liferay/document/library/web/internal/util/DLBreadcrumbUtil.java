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
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.WindowStateException;

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

		PortletURL portletURL = renderResponse.createRenderURL();

		FileEntry unescapedFileEntry = fileEntry.toUnescapedModel();

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/view_file_entry");
		portletURL.setParameter(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, unescapedFileEntry.getTitle(),
			portletURL.toString());
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

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/view_file_entry");
		portletURL.setParameter(
			"fileEntryId", String.valueOf(fileShortcut.getToFileEntryId()));

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, unescapedDLFileShortcut.getToTitle(),
			portletURL.toString());
	}

	public static void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest httpServletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/view");

		Map<String, Object> data = new HashMap<>();

		data.put("direction-right", Boolean.TRUE.toString());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		DLPortletInstanceSettings dlPortletInstanceSettings =
			DLPortletInstanceSettings.getInstance(
				themeDisplay.getLayout(), portletDisplay.getId());

		data.put("folder-id", dlPortletInstanceSettings.getRootFolderId());

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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long rootFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		boolean ignoreRootFolder = ParamUtil.getBoolean(
			httpServletRequest, "ignoreRootFolder");

		if (!ignoreRootFolder) {
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

			Map<String, Object> data = new HashMap<>();

			data.put("direction-right", Boolean.TRUE.toString());
			data.put("folder-id", ancestorFolder.getFolderId());

			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, ancestorFolder.getName(),
				portletURL.toString(), data);
		}

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (folder != null) {
			folderId = folder.getFolderId();
		}

		portletURL.setParameter("folderId", String.valueOf(folderId));

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId != rootFolderId)) {

			Folder unescapedFolder = folder.toUnescapedModel();

			Map<String, Object> data = new HashMap<>();

			data.put("direction-right", Boolean.TRUE.toString());
			data.put("folder-id", folderId);

			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, unescapedFolder.getName(),
				portletURL.toString(), data);
		}
	}

	public static void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception {

		String mvcRenderCommandName = ParamUtil.getString(
			httpServletRequest, "mvcRenderCommandName");

		PortletURL portletURL = renderResponse.createRenderURL();

		if (mvcRenderCommandName.equals(
				"/document_library/select_file_entry") ||
			mvcRenderCommandName.equals("/document_library/select_folder")) {

			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
			boolean ignoreRootFolder = ParamUtil.getBoolean(
				httpServletRequest, "ignoreRootFolder");

			_addPortletBreadcrumbEntry(
				httpServletRequest, "mvcRenderCommandName",
				mvcRenderCommandName, groupId, ignoreRootFolder, portletURL);
		}
		else {
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

	private static void _addPortletBreadcrumbEntry(
			HttpServletRequest httpServletRequest, String parameterName,
			String parameterValue, long groupId, boolean ignoreRootFolder,
			PortletURL portletURL)
		throws WindowStateException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		portletURL.setParameter(parameterName, parameterValue);
		portletURL.setParameter("groupId", String.valueOf(groupId));
		portletURL.setParameter(
			"ignoreRootFolder", String.valueOf(ignoreRootFolder));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, themeDisplay.translate("home"),
			portletURL.toString());
	}

}