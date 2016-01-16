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
import com.liferay.portlet.documentlibrary.exception.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.exception.NoSuchFolderException;
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
 * @author Roberto Díaz
 */
public class ActionUtil {

	public static List<FileEntry> getFileEntries(HttpServletRequest request)
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

		return fileEntries;
	}

	public static List<FileEntry> getFileEntries(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getFileEntries(request);
	}

	public static FileEntry getFileEntry(HttpServletRequest request)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(request, "fileEntryId");

		FileEntry fileEntry = null;

		if (fileEntryId > 0) {
			fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);
		}

		if (fileEntry == null) {
			return null;
		}

		String cmd = ParamUtil.getString(request, Constants.CMD);

		if (fileEntry.isInTrash() && !cmd.equals(Constants.MOVE_FROM_TRASH)) {
			throw new NoSuchFileEntryException(
				"{fileEntryId=" + fileEntryId + "}");
		}

		return fileEntry;
	}

	public static FileEntry getFileEntry(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getFileEntry(request);
	}

	public static FileShortcut getFileShortcut(HttpServletRequest request)
		throws Exception {

		long fileShortcutId = ParamUtil.getLong(request, "fileShortcutId");

		FileShortcut fileShortcut = null;

		if (fileShortcutId > 0) {
			fileShortcut = DLAppServiceUtil.getFileShortcut(fileShortcutId);
		}

		return fileShortcut;
	}

	public static FileShortcut getFileShortcut(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getFileShortcut(request);
	}

	public static List<FileShortcut> getFileShortcuts(
			HttpServletRequest request)
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

		return fileShortcuts;
	}

	public static List<FileShortcut> getFileShortcuts(
			PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getFileShortcuts(request);
	}

	public static FileVersion getFileVersion(
			HttpServletRequest request, FileEntry fileEntry)
		throws Exception {

		if (fileEntry == null) {
			return null;
		}

		FileVersion fileVersion = null;

		String version = ParamUtil.getString(request, "version");

		if (Validator.isNotNull(version)) {
			fileVersion = fileEntry.getFileVersion(version);
		}
		else {
			fileVersion = fileEntry.getFileVersion();
		}

		if (RawMetadataProcessorUtil.isSupported(fileVersion)) {
			RawMetadataProcessorUtil.generateMetadata(fileVersion);
		}

		return fileVersion;
	}

	public static FileVersion getFileVersion(
			PortletRequest portletRequest, FileEntry fileEntry)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getFileVersion(request, fileEntry);
	}

	public static Folder getFolder(HttpServletRequest request)
		throws Exception {

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

		return folder;
	}

	public static Folder getFolder(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getFolder(request);
	}

	public static List<Folder> getFolders(HttpServletRequest request)
		throws Exception {

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

		return folders;
	}

	public static List<Folder> getFolders(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getFolders(request);
	}

	public static Repository getRepository(HttpServletRequest request)
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

		return repository;
	}

	public static Repository getRepository(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getRepository(request);
	}

	private static final Log _log = LogFactoryUtil.getLog(ActionUtil.class);

}