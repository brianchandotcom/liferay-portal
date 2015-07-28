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

package com.liferay.document.library.web.asset;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseJSPAssetRenderer;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Chow
 */
public class DLFolderAssetRenderer
	extends BaseJSPAssetRenderer<Folder> implements TrashRenderer {

	public static final String TYPE = "folder";

	public DLFolderAssetRenderer(Folder folder) {
		_folder = folder;
	}

	@Override
	public Folder getAssetObject() {
		return _folder;
	}

	@Override
	public String getClassName() {
		return DLFolder.class.getName();
	}

	@Override
	public long getClassPK() {
		return _folder.getPrimaryKey();
	}

	@Override
	public Date getDisplayDate() {
		return _folder.getModifiedDate();
	}

	@Override
	public long getGroupId() {
		return _folder.getGroupId();
	}

	@Override
	public String getIconCssClass() throws PortalException {
		try {
			if (_folder.isMountPoint()) {
				return "icon-drive";
			}

			if (!PropsValues.DL_FOLDER_ICON_CHECK_COUNT) {
				return "icon-folder-open";
			}

			List<Long> subfolderIds = DLAppServiceUtil.getSubfolderIds(
				_folder.getRepositoryId(), _folder.getFolderId(), false);

			if (!subfolderIds.isEmpty()) {
				return "icon-folder-open";
			}

			int count = DLAppServiceUtil.getFoldersFileEntriesCount(
				_folder.getRepositoryId(),
				ListUtil.fromArray(new Long[] {_folder.getFolderId()}),
				WorkflowConstants.STATUS_APPROVED);

			if (count > 0) {
				return "icon-folder-open";
			}
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return "icon-remove";
		}
		catch (RepositoryException re) {
			if (_log.isDebugEnabled()) {
				_log.debug(re, re);
			}

			return "icon-remove";
		}

		return super.getIconCssClass();
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		try {
			if (PropsValues.DL_FOLDER_ICON_CHECK_COUNT &&
				DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
					_folder.getRepositoryId(), _folder.getFolderId(),
					WorkflowConstants.STATUS_APPROVED, true) > 0) {

				return themeDisplay.getPathThemeImages() +
					"/common/folder_full_document.png";
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return themeDisplay.getPathThemeImages() + "/common/folder_empty.png";
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/document_library/asset/folder_" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return _folder.getDescription();
	}

	@Override
	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!PropsValues.DL_FOLDER_ICON_CHECK_COUNT) {
			return themeDisplay.getPathThemeImages() +
				"/file_system/large/folder_empty_document.png";
		}

		int foldersCount = DLAppServiceUtil.getFoldersCount(
			_folder.getRepositoryId(), _folder.getFolderId());
		int entriesCount = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(
			_folder.getRepositoryId(), _folder.getFolderId(),
			WorkflowConstants.STATUS_APPROVED);

		if ((entriesCount > 0) || (foldersCount > 0)) {
			return themeDisplay.getPathThemeImages() +
				"/file_system/large/folder_full_document.png";
		}

		return themeDisplay.getPathThemeImages() +
			"/file_system/large/folder_empty_document.png";
	}

	@Override
	public String getTitle(Locale locale) {
		return TrashUtil.getOriginalTitle(_folder.getName());
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_folder");
		portletURL.setParameter(
			"folderId", String.valueOf(_folder.getFolderId()));

		return portletURL;
	}

	@Override
	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		PortletURL portletURL = assetRendererFactory.getURLView(
			liferayPortletResponse, windowState);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/view");
		portletURL.setParameter(
			"folderId", String.valueOf(_folder.getFolderId()));
		portletURL.setWindowState(windowState);

		return portletURL;
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return getURLViewInContext(
			liferayPortletRequest, noSuchEntryRedirect,
			"/document_library/find_folder", "folderId", _folder.getFolderId());
	}

	@Override
	public long getUserId() {
		return _folder.getUserId();
	}

	@Override
	public String getUserName() {
		return _folder.getUserName();
	}

	@Override
	public String getUuid() {
		return _folder.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return DLFolderPermission.contains(
			permissionChecker, _folder, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return DLFolderPermission.contains(
			permissionChecker, _folder, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER, _folder);

		return super.include(request, response, template);
	}

	@Override
	public boolean isDisplayable() {
		if (_folder.isMountPoint()) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFolderAssetRenderer.class);

	private final Folder _folder;

}