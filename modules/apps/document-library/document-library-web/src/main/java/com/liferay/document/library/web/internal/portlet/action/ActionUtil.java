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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLFileVersionPreviewConstants;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileShortcutException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.util.RawMetadataProcessorUtil;
import com.liferay.document.library.service.DLFileVersionPreviewLocalServiceUtil;
import com.liferay.document.library.web.internal.security.permission.resource.DLPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.RepositoryServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

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

	public static List<FileEntry> getFileEntries(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		List<FileEntry> fileEntries = new ArrayList<>();

		long[] fileEntryIds = ParamUtil.getLongValues(
			httpServletRequest, "rowIdsFileEntry");

		for (long fileEntryId : fileEntryIds) {
			try {
				fileEntries.add(DLAppServiceUtil.getFileEntry(fileEntryId));
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
		throws PortalException {

		return getFileEntries(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static FileEntry getFileEntry(HttpServletRequest httpServletRequest)
		throws PortalException {

		long fileEntryId = ParamUtil.getLong(httpServletRequest, "fileEntryId");

		if (fileEntryId <= 0) {
			return null;
		}

		FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);

		String cmd = ParamUtil.getString(httpServletRequest, Constants.CMD);

		if (fileEntry.isInTrash() && !cmd.equals(Constants.MOVE_FROM_TRASH)) {
			throw new NoSuchFileEntryException(
				"{fileEntryId=" + fileEntryId + "}");
		}

		return fileEntry;
	}

	public static FileEntry getFileEntry(PortletRequest portletRequest)
		throws PortalException {

		return getFileEntry(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static FileShortcut getFileShortcut(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		long fileShortcutId = ParamUtil.getLong(
			httpServletRequest, "fileShortcutId");

		if (fileShortcutId <= 0) {
			return null;
		}

		return DLAppServiceUtil.getFileShortcut(fileShortcutId);
	}

	public static FileShortcut getFileShortcut(PortletRequest portletRequest)
		throws PortalException {

		return getFileShortcut(
			PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static List<FileShortcut> getFileShortcuts(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		long[] fileShortcutIds = ParamUtil.getLongValues(
			httpServletRequest, "rowIdsDLFileShortcut");

		List<FileShortcut> fileShortcuts = new ArrayList<>();

		for (long fileShortcutId : fileShortcutIds) {
			try {
				fileShortcuts.add(
					DLAppServiceUtil.getFileShortcut(fileShortcutId));
			}
			catch (NoSuchFileShortcutException nsfse) {
				if (_log.isDebugEnabled()) {
					_log.debug(nsfse, nsfse);
				}
			}
		}

		return fileShortcuts;
	}

	public static List<FileShortcut> getFileShortcuts(
			PortletRequest portletRequest)
		throws PortalException {

		return getFileShortcuts(
			PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static FileVersion getFileVersion(
			HttpServletRequest httpServletRequest, FileEntry fileEntry)
		throws PortalException {

		if (fileEntry == null) {
			return null;
		}

		FileVersion fileVersion = null;

		String version = ParamUtil.getString(httpServletRequest, "version");

		if (Validator.isNotNull(version)) {
			fileVersion = fileEntry.getFileVersion(version);
		}
		else {
			fileVersion = fileEntry.getFileVersion();
		}

		if (RawMetadataProcessorUtil.isSupported(fileVersion) &&
			!DLFileVersionPreviewLocalServiceUtil.hasDLFileVersionPreview(
				fileEntry.getFileEntryId(), fileVersion.getFileVersionId(),
				DLFileVersionPreviewConstants.STATUS_FAILURE)) {

			RawMetadataProcessorUtil.generateMetadata(fileVersion);
		}

		return fileVersion;
	}

	public static FileVersion getFileVersion(
			PortletRequest portletRequest, FileEntry fileEntry)
		throws PortalException {

		return getFileVersion(
			PortalUtil.getHttpServletRequest(portletRequest), fileEntry);
	}

	public static Folder getFolder(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(httpServletRequest, "folderId");

		boolean ignoreRootFolder = ParamUtil.getBoolean(
			httpServletRequest, "ignoreRootFolder");

		if ((folderId <= 0) && !ignoreRootFolder) {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			String portletId = portletDisplay.getId();

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletPreferences(
					httpServletRequest, portletId);

			folderId = GetterUtil.getLong(
				portletPreferences.getValue("rootFolderId", null));
		}

		if (folderId <= 0) {
			DLPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.VIEW);

			return null;
		}

		Folder folder = DLAppServiceUtil.getFolder(folderId);

		if (folder.isMountPoint()) {
			com.liferay.portal.kernel.repository.Repository repository =
				RepositoryProviderUtil.getRepository(folder.getRepositoryId());

			folder = repository.getFolder(folder.getFolderId());
		}

		if (!folder.isRepositoryCapabilityProvided(TrashCapability.class)) {
			return folder;
		}

		TrashCapability trashCapability = folder.getRepositoryCapability(
			TrashCapability.class);

		if (trashCapability.isInTrash(folder)) {
			throw new NoSuchFolderException("{folderId=" + folderId + "}");
		}

		return folder;
	}

	public static Folder getFolder(PortletRequest portletRequest)
		throws PortalException {

		return getFolder(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static List<Folder> getFolders(HttpServletRequest httpServletRequest)
		throws PortalException {

		long[] folderIds = ParamUtil.getLongValues(
			httpServletRequest, "rowIdsFolder");

		List<Folder> folders = new ArrayList<>();

		for (long folderId : folderIds) {
			try {
				folders.add(DLAppServiceUtil.getFolder(folderId));
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
		throws PortalException {

		return getFolders(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static Repository getRepository(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long repositoryId = ParamUtil.getLong(
			httpServletRequest, "repositoryId");

		if (repositoryId > 0) {
			return RepositoryServiceUtil.getRepository(repositoryId);
		}

		DLPermission.check(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			ActionKeys.VIEW);

		return null;
	}

	public static Repository getRepository(PortletRequest portletRequest)
		throws PortalException {

		return getRepository(PortalUtil.getHttpServletRequest(portletRequest));
	}

	private static final Log _log = LogFactoryUtil.getLog(ActionUtil.class);

}