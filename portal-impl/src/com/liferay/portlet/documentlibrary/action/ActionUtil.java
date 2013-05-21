/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Repository;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.documentlibrary.util.RawMetadataProcessorUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 */
public class ActionUtil {

	public static void getFileEntries(HttpServletRequest request)
		throws Exception {

		List<FileEntry> fileEntries = new ArrayList<FileEntry>();

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(request, "fileEntryIds"), 0L);

		for (long fileEntryId : fileEntryIds) {
			try {
				FileEntry fileEntry = DLAppServiceUtil.getFileEntry(
					fileEntryId);

				fileEntries.add(fileEntry);
			}
			catch (NoSuchFileEntryException nsfee) {
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

		RawMetadataProcessorUtil.generateMetadata(fileVersion);

		String cmd = ParamUtil.getString(request, Constants.CMD);

		if ((fileVersion.isInTrash() || fileVersion.isInTrashContainer()) &&
			!cmd.equals(Constants.MOVE_FROM_TRASH)) {

			throw new NoSuchFileEntryException();
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

		DLFileShortcut fileShortcut = null;

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

		long[] fileShortcutIds = StringUtil.split(
			ParamUtil.getString(request, "fileShortcutIds"), 0L);

		List<DLFileShortcut> fileShortcuts = new ArrayList<DLFileShortcut>();

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

		Folder folder = null;

		if ((folderId > 0) &&
			(folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			folder = DLAppServiceUtil.getFolder(folderId);

			if (folder.getModel() instanceof DLFolder) {
				DLFolder dlFolder = (DLFolder)folder.getModel();

				if (dlFolder.isInTrash() || dlFolder.isInTrashContainer()) {
					throw new NoSuchFolderException();
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
		long[] folderIds = StringUtil.split(
			ParamUtil.getString(request, "folderIds"), 0L);

		List<Folder> folders = new ArrayList<Folder>();

		for (long folderId : folderIds) {
			try {
				Folder folder = DLAppServiceUtil.getFolder(folderId);

				folders.add(folder);
			}
			catch (NoSuchFolderException nsfee) {
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

}