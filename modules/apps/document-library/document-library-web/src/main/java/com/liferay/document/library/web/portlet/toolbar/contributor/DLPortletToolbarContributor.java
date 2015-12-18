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

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.document.library.web.settings.internal.DLPortletInstanceSettings;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"mvc.render.command.name=-",
		"mvc.render.command.name=/document_library/view"
	},
	service = {DLPortletToolbarContributor.class, PortletToolbarContributor.class}
)
public class DLPortletToolbarContributor implements PortletToolbarContributor {

	@Override
	public List<Menu> getPortletTitleMenus(PortletRequest portletRequest) {
		List<Menu> menus = new ArrayList<>();

		Menu menu = new Menu();

		menu.setDirection("down");
		menu.setExtended(false);
		menu.setIcon("../aui/plus-sign-2");
		menu.setMenuItems(getPortletTitleMenuItems(portletRequest));
		menu.setShowArrow(false);

		menus.add(menu);

		return menus;
	}

	protected void addPortletTitleAddDocumentMenuItems(
			List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws PortalException {

		long folderId = _getFolderId(folder);

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_DOCUMENT)) {

			return;
		}

		long repositoryId = _getRepositoryId(themeDisplay, folder);

		if (themeDisplay.getScopeGroupId() != repositoryId) {
			addPortletTitleAddBasicDocumentMenuItem(
				menuItems, folder, themeDisplay, portletRequest);
		}
		else {
			addPortletTitleAddDocumentTypeMenuItems(
				menuItems, folder, themeDisplay, portletRequest);
		}
	}

	protected void addPortletTitleAddDocumentTypeMenuItems(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		List<DLFileEntryType> fileEntryTypes = getFileEntryTypes(
			themeDisplay.getScopeGroupId(), folder);

		for (DLFileEntryType fileEntryType : fileEntryTypes) {
			try {
				URLMenuItem urlMenuItem = getFileEntryTypeMenuItem(
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
	}

	protected void addPortletTitleAddFolderMenuItem(
			List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws PortalException {

		long folderId = _getFolderId(folder);

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_FOLDER)) {

			return;
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

		menuItems.add(urlMenuItem);
	}

	protected void addPortletTitleAddMultipleDocumentsMenuItem(
			List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws PortalException {

		if ((folder != null) && !folder.isSupportsMultipleUpload()) {
			return;
		}

		List<DLFileEntryType> fileEntryTypes = getFileEntryTypes(
			themeDisplay.getScopeGroupId(), folder);

		if (fileEntryTypes.isEmpty()) {
			return;
		}

		long folderId = _getFolderId(folder);

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_DOCUMENT)) {

			return;
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
			"mvcPath", "/document_library/upload_multiple_file_entries.jsp");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"repositoryId",
			String.valueOf(_getRepositoryId(themeDisplay, folder)));
		portletURL.setParameter("folderId", String.valueOf(folderId));

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	protected void addPortletTitleAddRepositoryMenuItem(
			List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws PortalException {

		if (folder != null) {
			return;
		}

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ActionKeys.ADD_REPOSITORY)) {

			return;
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

		menuItems.add(urlMenuItem);
	}

	protected void addPortletTitleAddShortcutMenuItem(
			List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws PortalException {

		if ((folder != null) && !folder.isSupportsShortcuts()) {
			return;
		}

		long folderId = _getFolderId(folder);

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_SHORTCUT)) {

			return;
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

		menuItems.add(urlMenuItem);
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

	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Folder folder = _getFolder(themeDisplay, portletRequest);

		List<MenuItem> menuItems = new ArrayList<>();

		try {
			addPortletTitleAddFolderMenuItem(
				menuItems, folder, themeDisplay, portletRequest);
		}
		catch (PortalException pe) {
			_log.error("Unable to add folder menu item", pe);
		}

		try {
			addPortletTitleAddShortcutMenuItem(
				menuItems, folder, themeDisplay, portletRequest);
		}
		catch (PortalException pe) {
			_log.error("Unable to add shortcut menu item", pe);
		}

		try {
			addPortletTitleAddRepositoryMenuItem(
				menuItems, folder, themeDisplay, portletRequest);
		}
		catch (PortalException pe) {
			_log.error("Unable to add repository menu item", pe);
		}

		try {
			addPortletTitleAddMultipleDocumentsMenuItem(
				menuItems, folder, themeDisplay, portletRequest);
		}
		catch (PortalException pe) {
			_log.error("Unable to add multiple documents menu item", pe);
		}

		try {
			addPortletTitleAddDocumentMenuItems(
				menuItems, folder, themeDisplay, portletRequest);
		}
		catch (PortalException pe) {
			_log.error("Unable to add add document menu item", pe);
		}

		return menuItems;
	}

	@Reference(
		target = "(model.class.name=com.liferay.portlet.documentlibrary.model.DLFolder)",
		unbind = "-"
	)
	protected void setBaseModelPermissionChecker(
		BaseModelPermissionChecker baseModelPermissionChecker) {

		_baseModelPermissionChecker = baseModelPermissionChecker;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeService(
		DLFileEntryTypeService dlFileEntryTypeService) {

		_dlFileEntryTypeService = dlFileEntryTypeService;
	}

	private Folder _getFolder(
		ThemeDisplay themeDisplay, PortletRequest portletRequest) {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		long rootFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		try {
			DLPortletInstanceSettings dlPortletInstanceSettings =
				DLPortletInstanceSettings.getInstance(
					themeDisplay.getLayout(), portletDisplay.getId());

			rootFolderId = dlPortletInstanceSettings.getRootFolderId();
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		Folder folder = (Folder)portletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FOLDER);

		long folderId = BeanParamUtil.getLong(
			folder, portletRequest, "folderId", rootFolderId);

		if ((folder == null) &&
			(folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			try {
				folder = _dlAppLocalService.getFolder(folderId);
			}
			catch (NoSuchFolderException nsfe) {
				folder = null;
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return folder;
	}

	private long _getFolderId(Folder folder) {
		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (folder != null) {
			folderId = folder.getFolderId();
		}

		return folderId;
	}

	private long _getRepositoryId(ThemeDisplay themeDisplay, Folder folder) {
		long repositoryId = themeDisplay.getScopeGroupId();

		if (folder != null) {
			repositoryId = folder.getRepositoryId();
		}

		return repositoryId;
	}

	private void addPortletTitleAddBasicDocumentMenuItem(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
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

		menuItems.add(urlMenuItem);
	}

	private URLMenuItem getFileEntryTypeMenuItem(
			PortletRequest portletRequest, Folder folder,
			List<DLFileEntryType> fileEntryTypes, DLFileEntryType fileEntryType,
			ThemeDisplay themeDisplay)
		throws PortalException {

		URLMenuItem urlMenuItem = new URLMenuItem();

		String label = LanguageUtil.get(
			PortalUtil.getHttpServletRequest(portletRequest),
			HtmlUtil.escape(
				fileEntryType.getUnambiguousName(
					fileEntryTypes, themeDisplay.getScopeGroupId(),
					themeDisplay.getLocale())));

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

	private static final Log _log = LogFactoryUtil.getLog(
		DLPortletToolbarContributor.class);

	private volatile BaseModelPermissionChecker _baseModelPermissionChecker;
	private volatile DLAppLocalService _dlAppLocalService;
	private volatile DLFileEntryTypeService _dlFileEntryTypeService;

}