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

package com.liferay.document.library.web.internal.portlet.toolbar.contributor.helper;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeServiceUtil;
import com.liferay.document.library.web.internal.security.permission.resource.DLFolderPermission;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Adolfo Pérez
 */
public class MenuItemProvider {

	public List<MenuItem> getAddDocumentTypesMenuItems(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		long folderId = _getFolderId(folder);

		if (!_hasPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_DOCUMENT)) {

			return Collections.emptyList();
		}

		List<MenuItem> menuItems = new ArrayList<>();

		long repositoryId = _getRepositoryId(folder, themeDisplay);

		if (themeDisplay.getScopeGroupId() == repositoryId) {
			menuItems.addAll(
				_getPortletTitleAddDocumentTypeMenuItems(
					folder, themeDisplay, portletRequest));
		}

		return menuItems;
	}

	public MenuItem getAddFileMenuItem(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		long folderId = _getFolderId(folder);

		if (!_hasPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_DOCUMENT)) {

			return null;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("upload");
		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest),
				"file-upload"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter(Constants.CMD, Constants.ADD);
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"repositoryId",
			String.valueOf(_getRepositoryId(folder, themeDisplay)));
		portletURL.setParameter("folderId", String.valueOf(folderId));

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	public MenuItem getAddFolderMenuItem(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		long folderId = _getFolderId(folder);

		if (!_hasPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_FOLDER)) {

			return null;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("folder");
		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest), "folder"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_folder");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"repositoryId",
			String.valueOf(_getRepositoryId(folder, themeDisplay)));
		portletURL.setParameter("parentFolderId", String.valueOf(folderId));
		portletURL.setParameter("ignoreRootFolder", Boolean.TRUE.toString());

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	public MenuItem getAddMultipleFilesMenuItem(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		if ((folder != null) && !folder.isSupportsMultipleUpload()) {
			return null;
		}

		long folderId = _getFolderId(folder);

		if (!_hasPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_DOCUMENT)) {

			return null;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("upload-multiple");
		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest),
				"multiple-files-upload"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/document_library/upload_multiple_file_entries");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"repositoryId",
			String.valueOf(_getRepositoryId(folder, themeDisplay)));
		portletURL.setParameter("folderId", String.valueOf(folderId));

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	public URLMenuItem getAddRepositoryMenuItem(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		if (folder != null) {
			return null;
		}

		if (!_hasPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ActionKeys.ADD_REPOSITORY)) {

			return null;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("repository");
		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest),
				"repository"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_repository");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	public URLMenuItem getAddShortcutMenuItem(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		if ((folder != null) && !folder.isSupportsShortcuts()) {
			return null;
		}

		long folderId = _getFolderId(folder);

		if (!_hasPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_SHORTCUT)) {

			return null;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("shortcut");
		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest), "shortcut"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_shortcut");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"repositoryId",
			String.valueOf(_getRepositoryId(folder, themeDisplay)));
		portletURL.setParameter("folderId", String.valueOf(folderId));

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	private MenuItem _getFileEntryTypeMenuItem(
			Folder folder, List<DLFileEntryType> fileEntryTypes,
			DLFileEntryType fileEntryType, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws PortalException {

		URLMenuItem urlMenuItem = new URLMenuItem();

		String label = LanguageUtil.get(
			PortalUtil.getHttpServletRequest(portletRequest),
			fileEntryType.getUnambiguousName(
				fileEntryTypes, themeDisplay.getScopeGroupId(),
				themeDisplay.getLocale()));

		urlMenuItem.setLabel(label);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter(Constants.CMD, Constants.ADD);
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"repositoryId",
			String.valueOf(_getRepositoryId(folder, themeDisplay)));
		portletURL.setParameter(
			"folderId", String.valueOf(_getFolderId(folder)));
		portletURL.setParameter(
			"fileEntryTypeId",
			String.valueOf(fileEntryType.getFileEntryTypeId()));

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	private List<DLFileEntryType> _getFileEntryTypes(
		long groupId, Folder folder) {

		if ((folder != null) && !folder.isSupportsMetadata()) {
			return Collections.emptyList();
		}

		long folderId = _getFolderId(folder);

		boolean inherited = true;

		if ((folder != null) && (folder.getModel() instanceof DLFolder)) {
			DLFolder dlFolder = (DLFolder)folder.getModel();

			if (dlFolder.getRestrictionType() ==
					DLFolderConstants.
						RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW) {

				inherited = false;
			}
		}

		try {
			return DLFileEntryTypeServiceUtil.getFolderFileEntryTypes(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId), folderId,
				inherited);
		}
		catch (PortalException pe) {
			_log.error(
				StringBundler.concat(
					"Unable to get file entry types for group ", groupId,
					" and folder ", folderId),
				pe);

			return Collections.emptyList();
		}
	}

	private long _getFolderId(Folder folder) {
		if (folder == null) {
			return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		return folder.getFolderId();
	}

	private List<MenuItem> _getPortletTitleAddDocumentTypeMenuItems(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		List<MenuItem> menuItems = new ArrayList<>();

		List<DLFileEntryType> fileEntryTypes = _getFileEntryTypes(
			themeDisplay.getScopeGroupId(), folder);

		for (DLFileEntryType fileEntryType : fileEntryTypes) {
			try {
				if (fileEntryType.getFileEntryTypeId() !=
						DLFileEntryTypeConstants.COMPANY_ID_BASIC_DOCUMENT) {

					MenuItem urlMenuItem = _getFileEntryTypeMenuItem(
						folder, fileEntryTypes, fileEntryType, themeDisplay,
						portletRequest);

					menuItems.add(urlMenuItem);
				}
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to add menu item for file entry type " +
						fileEntryType.getName(),
					pe);
			}
		}

		return menuItems;
	}

	private long _getRepositoryId(Folder folder, ThemeDisplay themeDisplay) {
		if (folder == null) {
			return themeDisplay.getScopeGroupId();
		}

		return folder.getRepositoryId();
	}

	private boolean _hasPermission(
		PermissionChecker permissionChecker, long groupId, long folderId,
		String actionId) {

		try {
			return DLFolderPermission.contains(
				permissionChecker, groupId, folderId, actionId);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MenuItemProvider.class);

}