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

package com.liferay.document.library.web.portlet.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Repository;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.documentlibrary.util.RawMetadataProcessorUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 */
public class ActionUtil {

	public static void getFileEntries(HttpServletRequest request)
		throws Exception {

		List<FileEntry> fileEntries = new ArrayList<>();

		long[] fileEntryIds = ParamUtil.getLongValues(
			request, "rowIdsFileEntry");

		for (long fileEntryId : fileEntryIds) {
			try {
				FileEntry fileEntry = DLAppServiceUtil.getFileEntry(
					fileEntryId);

				fileEntries.add(fileEntry);
			}
			catch (NoSuchFileEntryException nsfee) {
				if (_log.isDebugEnabled()) {
					_log.debug(nsfee, nsfee);
				}
			}
		}

		request.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_ENTRIES, fileEntries);
	}

	public static void getFileEntries(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFileEntries(request);
	}

	public static void getFileEntry(HttpServletRequest request)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(request, "fileEntryId");

		FileEntry fileEntry = null;

		if (fileEntryId > 0) {
			fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);
		}

		request.setAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, fileEntry);

		String version = ParamUtil.getString(request, "version");

		if (fileEntry == null) {
			return;
		}

		FileVersion fileVersion = null;

		if (Validator.isNotNull(version)) {
			fileVersion = fileEntry.getFileVersion(version);

			request.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, fileVersion);
		}
		else {
			fileVersion = fileEntry.getFileVersion();
		}

		if (RawMetadataProcessorUtil.isSupported(fileVersion)) {
			RawMetadataProcessorUtil.generateMetadata(fileVersion);
		}

		String cmd = ParamUtil.getString(request, Constants.CMD);

		if (fileEntry.isInTrash() && !cmd.equals(Constants.MOVE_FROM_TRASH)) {
			throw new NoSuchFileEntryException(
				"{fileEntryId=" + fileEntryId + "}");
		}
	}

	public static void getFileEntry(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFileEntry(request);
	}

	public static void getFileShortcut(HttpServletRequest request)
		throws Exception {

		long fileShortcutId = ParamUtil.getLong(request, "fileShortcutId");

		FileShortcut fileShortcut = null;

		if (fileShortcutId > 0) {
			fileShortcut = DLAppServiceUtil.getFileShortcut(fileShortcutId);
		}

		request.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT, fileShortcut);
	}

	public static void getFileShortcut(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFileShortcut(request);
	}

	public static void getFileShortcuts(HttpServletRequest request)
		throws Exception {

		long[] fileShortcutIds = ParamUtil.getLongValues(
			request, "rowIdsDLFileShortcut");

		List<FileShortcut> fileShortcuts = new ArrayList<>();

		for (long fileShortcutId : fileShortcutIds) {
			if (fileShortcutId > 0) {
				fileShortcuts.add(
					DLAppServiceUtil.getFileShortcut(fileShortcutId));
			}
		}

		request.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUTS, fileShortcuts);
	}

	public static void getFileShortcuts(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFileShortcuts(request);
	}

	public static void getFolder(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(request, "folderId");

		boolean ignoreRootFolder = ParamUtil.getBoolean(
			request, "ignoreRootFolder");

		if ((folderId <= 0) && !ignoreRootFolder) {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			String portletId = portletDisplay.getId();

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletPreferences(
					request, portletId);

			folderId = GetterUtil.getLong(
				portletPreferences.getValue("rootFolderId", null));
		}

		Folder folder = null;

		if (folderId > 0) {
			folder = DLAppServiceUtil.getFolder(folderId);

			if (folder.getModel() instanceof DLFolder) {
				DLFolder dlFolder = (DLFolder)folder.getModel();

				if (dlFolder.isInTrash()) {
					throw new NoSuchFolderException(
						"{folderId=" + folderId + "}");
				}
			}
		}
		else {
			DLPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.VIEW);
		}

		request.setAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER, folder);
	}

	public static void getFolder(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFolder(request);
	}

	public static void getFolders(HttpServletRequest request) throws Exception {
		long[] folderIds = ParamUtil.getLongValues(request, "rowIdsFolder");

		List<Folder> folders = new ArrayList<>();

		for (long folderId : folderIds) {
			try {
				Folder folder = DLAppServiceUtil.getFolder(folderId);

				folders.add(folder);
			}
			catch (NoSuchFolderException nsfe) {
				if (_log.isDebugEnabled()) {
					_log.debug(nsfe, nsfe);
				}
			}
		}

		request.setAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDERS, folders);
	}

	public static void getFolders(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFolders(request);
	}

	public static void getRepository(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long repositoryId = ParamUtil.getLong(request, "repositoryId");

		Repository repository = null;

		if (repositoryId > 0) {
			repository = RepositoryServiceUtil.getRepository(repositoryId);
		}
		else {
			DLPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.VIEW);
		}

		request.setAttribute(WebKeys.DOCUMENT_LIBRARY_REPOSITORY, repository);
	}

	public static void getRepository(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getRepository(request);
	}

	private static final Log _log = LogFactoryUtil.getLog(ActionUtil.class);

}