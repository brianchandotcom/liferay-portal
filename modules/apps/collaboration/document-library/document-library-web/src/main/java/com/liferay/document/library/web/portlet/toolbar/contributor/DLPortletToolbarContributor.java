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

package com.liferay.document.library.web.portlet.toolbar.contributor;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.document.library.web.portlet.toolbar.contributor.helper.DLPortletToolbarContributorHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.BasePortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"mvc.render.command.name=-",
		"mvc.render.command.name=/document_library/view",
		"mvc.render.command.name=/document_library/view_folder"
	},
	service = {DLPortletToolbarContributor.class, PortletToolbarContributor.class}
)
public class DLPortletToolbarContributor extends BasePortletToolbarContributor {

	public MenuItem getFileEntryTypeMenuItem(
			PortletRequest portletRequest, Folder folder,
			List<DLFileEntryType> fileEntryTypes, DLFileEntryType fileEntryType,
			ThemeDisplay themeDisplay)
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
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter(Constants.CMD, Constants.ADD);
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"repositoryId",
			String.valueOf(_getRepositoryId(themeDisplay, folder)));
		portletURL.setParameter(
			"folderId", String.valueOf(_getFolderId(folder)));
		portletURL.setParameter(
			"fileEntryTypeId",
			String.valueOf(fileEntryType.getFileEntryTypeId()));

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	public List<MenuItem> getPortletTitleAddDocumentMenuItems(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		long folderId = _getFolderId(folder);

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_DOCUMENT)) {

			return Collections.emptyList();
		}

		List<MenuItem> menuItems = new ArrayList<>();

		long repositoryId = _getRepositoryId(themeDisplay, folder);

		if (themeDisplay.getScopeGroupId() != repositoryId) {
			menuItems.add(
				_getPortletTitleAddBasicDocumentMenuItem(
					folder, themeDisplay, portletRequest));
		}
		else {
			menuItems.addAll(
				_getPortletTitleAddDocumentTypeMenuItems(
					folder, themeDisplay, portletRequest));
		}

		return menuItems;
	}

	public MenuItem getPortletTitleAddFolderMenuItem(
		ThemeDisplay themeDisplay, PortletRequest portletRequest,
		Folder folder) {

		long folderId = _getFolderId(folder);

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_FOLDER)) {

			return null;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest),
				(folder != null) ? "subfolder" : "folder"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_folder");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"repositoryId",
			String.valueOf(_getRepositoryId(themeDisplay, folder)));
		portletURL.setParameter("parentFolderId", String.valueOf(folderId));
		portletURL.setParameter("ignoreRootFolder", Boolean.TRUE.toString());

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	public MenuItem getPortletTitleAddMultipleDocumentsMenuItem(
		ThemeDisplay themeDisplay, PortletRequest portletRequest,
		Folder folder) {

		if ((folder != null) && !folder.isSupportsMultipleUpload()) {
			return null;
		}

		List<DLFileEntryType> fileEntryTypes = getFileEntryTypes(
			themeDisplay.getScopeGroupId(), folder);

		if (fileEntryTypes.isEmpty()) {
			return null;
		}

		long folderId = _getFolderId(folder);

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_DOCUMENT)) {

			return null;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest),
				"multiple-documents"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/document_library/upload_multiple_file_entries");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"repositoryId",
			String.valueOf(_getRepositoryId(themeDisplay, folder)));
		portletURL.setParameter("folderId", String.valueOf(folderId));

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	public URLMenuItem getPortletTitleAddRepositoryMenuItem(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		if (folder != null) {
			return null;
		}

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ActionKeys.ADD_REPOSITORY)) {

			return null;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest),
				"repository"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_repository");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	public URLMenuItem getPortletTitleAddShortcutMenuItem(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		if ((folder != null) && !folder.isSupportsShortcuts()) {
			return null;
		}

		long folderId = _getFolderId(folder);

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_SHORTCUT)) {

			return null;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest), "shortcut"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_shortcut");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"repositoryId",
			String.valueOf(_getRepositoryId(themeDisplay, folder)));
		portletURL.setParameter("folderId", String.valueOf(folderId));

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	protected void addPortletTitleAddDocumentMenuItems(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		menuItems.addAll(
			getPortletTitleAddDocumentMenuItems(
				folder, themeDisplay, portletRequest));
	}

	protected void addPortletTitleAddFolderMenuItem(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		MenuItem portletTitleAddFolderMenuItem =
			getPortletTitleAddFolderMenuItem(
				themeDisplay, portletRequest, folder);

		if (portletTitleAddFolderMenuItem != null) {
			menuItems.add(portletTitleAddFolderMenuItem);
		}
	}

	protected void addPortletTitleAddMultipleDocumentsMenuItem(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		MenuItem portletTitleAddMultipleDocumentsMenuItem =
			getPortletTitleAddMultipleDocumentsMenuItem(
				themeDisplay, portletRequest, folder);

		if (portletTitleAddMultipleDocumentsMenuItem != null) {
			menuItems.add(portletTitleAddMultipleDocumentsMenuItem);
		}
	}

	protected void addPortletTitleAddRepositoryMenuItem(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		URLMenuItem portletTitleAddRepositoryMenuItem =
			getPortletTitleAddRepositoryMenuItem(
				folder, themeDisplay, portletRequest);

		if (portletTitleAddRepositoryMenuItem != null) {
			menuItems.add(portletTitleAddRepositoryMenuItem);
		}
	}

	protected void addPortletTitleAddShortcutMenuItem(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		URLMenuItem portletTitleAddShortcutMenuItem =
			getPortletTitleAddShortcutMenuItem(
				folder, themeDisplay, portletRequest);

		if (portletTitleAddShortcutMenuItem != null) {
			menuItems.add(portletTitleAddShortcutMenuItem);
		}
	}

	protected boolean containsPermission(
		PermissionChecker permissionChecker, long groupId, long folderId,
		String actionId) {

		try {
			_baseModelPermissionChecker.checkBaseModel(
				permissionChecker, groupId, folderId, actionId);
		}
		catch (PortalException pe) {
			return false;
		}

		return true;
	}

	protected List<DLFileEntryType> getFileEntryTypes(
		long groupId, Folder folder) {

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

		List<DLFileEntryType> fileEntryTypes = Collections.emptyList();

		if ((folder == null) || folder.isSupportsMetadata()) {
			try {
				fileEntryTypes =
					_dlFileEntryTypeService.getFolderFileEntryTypes(
						PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
						folderId, inherited);
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to get file entry types for group " + groupId +
						" and folder " + folderId,
					pe);
			}
		}

		return fileEntryTypes;
	}

	@Override
	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Folder folder = _dlPortletToolbarContributorHelper.getFolder(
			themeDisplay, portletRequest);

		List<MenuItem> menuItems = new ArrayList<>();

		addPortletTitleAddFolderMenuItem(
			menuItems, folder, themeDisplay, portletRequest);

		addPortletTitleAddShortcutMenuItem(
			menuItems, folder, themeDisplay, portletRequest);

		addPortletTitleAddRepositoryMenuItem(
			menuItems, folder, themeDisplay, portletRequest);

		addPortletTitleAddMultipleDocumentsMenuItem(
			menuItems, folder, themeDisplay, portletRequest);

		addPortletTitleAddDocumentMenuItems(
			menuItems, folder, themeDisplay, portletRequest);

		return menuItems;
	}

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFolder)",
		unbind = "-"
	)
	protected void setBaseModelPermissionChecker(
		BaseModelPermissionChecker baseModelPermissionChecker) {

		_baseModelPermissionChecker = baseModelPermissionChecker;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeService(
		DLFileEntryTypeService dlFileEntryTypeService) {

		_dlFileEntryTypeService = dlFileEntryTypeService;
	}

	@Reference(unbind = "-")
	protected void setDLPortletToolbarContributorHelper(
		DLPortletToolbarContributorHelper dlPortletToolbarContributorHelper) {

		_dlPortletToolbarContributorHelper = dlPortletToolbarContributorHelper;
	}

	private long _getFolderId(Folder folder) {
		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (folder != null) {
			folderId = folder.getFolderId();
		}

		return folderId;
	}

	private MenuItem _getPortletTitleAddBasicDocumentMenuItem(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		long folderId = _getFolderId(folder);

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest),
				"basic-document"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter(Constants.CMD, Constants.ADD);
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"repositoryId",
			String.valueOf(_getRepositoryId(themeDisplay, folder)));
		portletURL.setParameter("folderId", String.valueOf(folderId));

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	private List<MenuItem> _getPortletTitleAddDocumentTypeMenuItems(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		List<MenuItem> menuItems = new ArrayList<>();

		List<DLFileEntryType> fileEntryTypes = getFileEntryTypes(
			themeDisplay.getScopeGroupId(), folder);

		for (DLFileEntryType fileEntryType : fileEntryTypes) {
			try {
				MenuItem urlMenuItem = getFileEntryTypeMenuItem(
					portletRequest, folder, fileEntryTypes, fileEntryType,
					themeDisplay);

				menuItems.add(urlMenuItem);
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

	private long _getRepositoryId(ThemeDisplay themeDisplay, Folder folder) {
		long repositoryId = themeDisplay.getScopeGroupId();

		if (folder != null) {
			repositoryId = folder.getRepositoryId();
		}

		return repositoryId;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLPortletToolbarContributor.class);

	private BaseModelPermissionChecker _baseModelPermissionChecker;
	private DLFileEntryTypeService _dlFileEntryTypeService;
	private DLPortletToolbarContributorHelper
		_dlPortletToolbarContributorHelper;

}