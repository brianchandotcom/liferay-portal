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

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.document.library.web.settings.internal.DLPortletInstanceSettings;
import com.liferay.portal.kernel.bean.BeanParamUtil;
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
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.render.command.name=-",
		"mvc.render.command.name=/image_gallery_display/view"
	},
	service = {PortletToolbarContributor.class}
)
public class IGPortletToolbarContributor extends BasePortletToolbarContributor {

	protected void addPortletTitleAddFileEntryMenuItem(
			List<MenuItem> menuItems, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws PortalException {

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest),
				"add-file-entry"));

		Folder folder = _getFolder(themeDisplay, portletRequest);

		if ((folder != null) && !folder.isMountPoint() &&
			containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folder.getFolderId(),
				ActionKeys.ADD_DOCUMENT)) {

			PortletURL portletURL = _getAddFileEntryPortletURL(
				themeDisplay, portletRequest, folder.getFolderId(),
				folder.getRepositoryId());

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
		}
		else if ((folder == null) &&
				 containsPermission(
					 themeDisplay.getPermissionChecker(),
					 themeDisplay.getScopeGroupId(),
					 DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					 ActionKeys.ADD_DOCUMENT)) {

			PortletURL portletURL = _getAddFileEntryPortletURL(
				themeDisplay, portletRequest,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				themeDisplay.getScopeGroupId());

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
		}
	}

	protected void addPortletTitleAddFolderMenuItem(
			List<MenuItem> menuItems, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws PortalException {

		URLMenuItem urlMenuItem = new URLMenuItem();

		Folder folder = _getFolder(themeDisplay, portletRequest);

		if ((folder != null) &&
			containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folder.getFolderId(),
				ActionKeys.ADD_FOLDER) &&
			!folder.isMountPoint()) {

			urlMenuItem.setLabel(
				LanguageUtil.get(
					PortalUtil.getHttpServletRequest(portletRequest),
					"add-subfolder"));

			PortletURL portletURL = _getAddFolderPortletURL(
				themeDisplay, portletRequest, folder.getFolderId(),
				folder.getRepositoryId());

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
		}
		else if ((folder == null) &&
				 containsPermission(
					 themeDisplay.getPermissionChecker(),
					 themeDisplay.getScopeGroupId(),
					 DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					 ActionKeys.ADD_FOLDER)) {

			urlMenuItem.setLabel(
				LanguageUtil.get(
					PortalUtil.getHttpServletRequest(portletRequest),
					"add-folder"));

			PortletURL portletURL = _getAddFolderPortletURL(
				themeDisplay, portletRequest,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				themeDisplay.getScopeGroupId());

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
		}
	}

	protected void addPortletTitleAddMulpleFileEntriesMenuItem(
		List<MenuItem> menuItems, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest),
				"multiple-media"));

		Folder folder = _getFolder(themeDisplay, portletRequest);

		if ((folder == null) &&
			containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ActionKeys.ADD_DOCUMENT)) {

			PortletURL portletURL = _getAddMultipleFileEntriesPortletURL(
				themeDisplay, portletRequest,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				themeDisplay.getScopeGroupId());

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
		}
		else if (folder.isSupportsMultipleUpload() &&
				 containsPermission(
					 themeDisplay.getPermissionChecker(),
					 themeDisplay.getScopeGroupId(), folder.getFolderId(),
					 ActionKeys.ADD_DOCUMENT)) {

			PortletURL portletURL = _getAddMultipleFileEntriesPortletURL(
				themeDisplay, portletRequest, folder.getFolderId(),
				folder.getRepositoryId());

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
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

	@Override
	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<MenuItem> menuItems = new ArrayList<>();

		try {
			addPortletTitleAddFolderMenuItem(
				menuItems, themeDisplay, portletRequest);
		}
		catch (PortalException pe) {
			_log.error("Unable to add folder menu item", pe);
		}

		try {
			addPortletTitleAddFileEntryMenuItem(
				menuItems, themeDisplay, portletRequest);
		}
		catch (PortalException pe) {
			_log.error("Unable to add file entry menu item", pe);
		}

		addPortletTitleAddMulpleFileEntriesMenuItem(
			menuItems, themeDisplay, portletRequest);

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
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	private PortletURL _getAddFileEntryPortletURL(
		ThemeDisplay themeDisplay, PortletRequest portletRequest, long folderId,
		long repositoryId) {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setParameter("backURL", themeDisplay.getURLCurrent());
		portletURL.setParameter("parentFolderId", String.valueOf(folderId));
		portletURL.setParameter("repositoryId", String.valueOf(repositoryId));

		return portletURL;
	}

	private PortletURL _getAddFolderPortletURL(
		ThemeDisplay themeDisplay, PortletRequest portletRequest, long folderId,
		long repositoryId) {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_folder");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setParameter("repositoryId", String.valueOf(repositoryId));
		portletURL.setParameter("parentFolderId", String.valueOf(folderId));
		portletURL.setParameter("ignoreRootFolder", Boolean.TRUE.toString());

		return portletURL;
	}

	private PortletURL _getAddMultipleFileEntriesPortletURL(
		ThemeDisplay themeDisplay, PortletRequest portletRequest, long folderId,
		long repositoryId) {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/document_library/upload_multiple_file_entries");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setParameter("backURL", themeDisplay.getURLCurrent());
		portletURL.setParameter("repositoryId", String.valueOf(repositoryId));
		portletURL.setParameter("parentFolderId", String.valueOf(folderId));

		return portletURL;
	}

	private Folder _getFolder(
		ThemeDisplay themeDisplay, PortletRequest portletRequest) {

		Folder folder = (Folder)portletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FOLDER);

		if (folder != null) {
			return folder;
		}

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

		long folderId = BeanParamUtil.getLong(
			folder, portletRequest, "folderId", rootFolderId);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		IGPortletToolbarContributor.class);

	private BaseModelPermissionChecker _baseModelPermissionChecker;
	private DLAppLocalService _dlAppLocalService;

}