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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.security.permission.resource.DLFolderPermission;
import com.liferay.document.library.web.internal.security.permission.resource.DLPermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class FolderActionDisplayContext {

	public FolderActionDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;

		_dlRequestHelper = new DLRequestHelper(httpServletRequest);
	}

	public Folder getFolder() {
		if (_folder != null) {
			return _folder;
		}

		ResultRow row = (ResultRow)_httpServletRequest.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		if (row == null) {
			_folder = (Folder)_httpServletRequest.getAttribute(
				"info_panel.jsp-folder");
		}
		else {
			if (row.getObject() instanceof Folder) {
				_folder = (Folder)row.getObject();
			}
		}

		return _folder;
	}

	public String getModelResource() {
		Folder folder = getFolder();

		if (folder != null) {
			return DLFolderConstants.getClassName();
		}

		return "com.liferay.document.library";
	}

	public String getModelResourceDescription() throws PortalException {
		Folder folder = getFolder();

		if (folder != null) {
			return folder.getName();
		}

		ThemeDisplay themeDisplay = _dlRequestHelper.getThemeDisplay();

		return themeDisplay.getScopeGroupName();
	}

	public String getRandomNamespace() {
		if (_randomNamespace != null) {
			return _randomNamespace;
		}

		String portletName = _dlRequestHelper.getPortletName();

		if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			_randomNamespace =
				PortalUtil.generateRandomKey(
					_httpServletRequest,
					"portlet_document_library_folder_action") +
						StringPool.UNDERLINE;
		}
		else {
			_randomNamespace =
				PortalUtil.generateRandomKey(
					_httpServletRequest,
					"portlet_image_gallery_display_folder_action") +
						StringPool.UNDERLINE;
		}

		return _randomNamespace;
	}

	public String getRedirect() {
		return _dlRequestHelper.getCurrentURL();
	}

	public long getRepositoryId() {
		if (_repositoryId != null) {
			return _repositoryId;
		}

		Folder folder = getFolder();

		if (folder != null) {
			_repositoryId = folder.getRepositoryId();
		}
		else {
			_repositoryId = GetterUtil.getLong(
				(String)_httpServletRequest.getAttribute(
					"view.jsp-repositoryId"));
		}

		return _repositoryId;
	}

	public long getResourcePrimKey() {
		Folder folder = getFolder();

		if (folder != null) {
			return folder.getFolderId();
		}

		return _dlRequestHelper.getScopeGroupId();
	}

	public int getStatus() {
		if (_status != null) {
			return _status;
		}

		ThemeDisplay themeDisplay = _dlRequestHelper.getThemeDisplay();

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isContentReviewer(
				_dlRequestHelper.getCompanyId(),
				_dlRequestHelper.getScopeGroupId())) {

			_status = WorkflowConstants.STATUS_ANY;
		}
		else {
			_status = WorkflowConstants.STATUS_APPROVED;
		}

		return _status;
	}

	public boolean isFolderSelected() {
		if (_folderSelected != null) {
			return _folderSelected;
		}

		_folderSelected = GetterUtil.getBoolean(
			(String)_httpServletRequest.getAttribute(
				"view_entries.jsp-folderSelected"));

		return _folderSelected;
	}

	public boolean isShowPermissionsURL() throws PortalException {
		Folder folder = getFolder();

		if (folder != null) {
			return DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(), folder,
				ActionKeys.PERMISSIONS);
		}

		return DLPermission.contains(
			_dlRequestHelper.getPermissionChecker(),
			_dlRequestHelper.getScopeGroupId(), ActionKeys.PERMISSIONS);
	}

	public boolean isView() {
		if (_view != null) {
			return _view;
		}

		ResultRow row = (ResultRow)_httpServletRequest.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		String portletName = _dlRequestHelper.getPortletName();

		if ((row == null) &&
			portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {

			_view = true;
		}
		else {
			_view = false;
		}

		return _view;
	}

	private final DLRequestHelper _dlRequestHelper;
	private Folder _folder;
	private Boolean _folderSelected;
	private final HttpServletRequest _httpServletRequest;
	private String _randomNamespace;
	private Long _repositoryId;
	private Integer _status;
	private Boolean _view;

}