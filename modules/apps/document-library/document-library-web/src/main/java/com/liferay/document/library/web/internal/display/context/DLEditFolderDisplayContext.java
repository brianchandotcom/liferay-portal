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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.web.internal.security.permission.resource.DLFolderPermission;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DLEditFolderDisplayContext {

	public DLEditFolderDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Folder getFolder() {
		return (Folder)_httpServletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FOLDER);
	}

	public long getFolderId() {
		return BeanParamUtil.getLong(
			getFolder(), _httpServletRequest, "folderId");
	}

	public String getHeaderTitle() {
		Folder folder = getFolder();

		if (folder != null) {
			return folder.getName();
		}

		if (isRootFolder()) {
			return LanguageUtil.get(_httpServletRequest, "home");
		}

		return LanguageUtil.get(_httpServletRequest, "new-folder");
	}

	public String getLanguageId() {
		return LanguageUtil.getLanguageId(_httpServletRequest);
	}

	public Folder getParentFolder() throws PortalException {
		try {
			if (getParentFolderId() ==
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				return null;
			}

			return DLAppServiceUtil.getFolder(getParentFolderId());
		}
		catch (Exception exception) {
			return null;
		}
	}

	public long getParentFolderId() {
		return BeanParamUtil.getLong(
			getFolder(), _httpServletRequest, "parentFolderId",
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public String getParentFolderName() throws PortalException {
		Folder folder = getParentFolder();

		if (folder == null) {
			return LanguageUtil.get(_httpServletRequest, "home");
		}

		return folder.getName();
	}

	public String getRedirect() {
		return ParamUtil.getString(_httpServletRequest, "redirect");
	}

	public long getRepositoryId() {
		return BeanParamUtil.getLong(
			getFolder(), _httpServletRequest, "repositoryId");
	}

	public List<WorkflowDefinition> getWorkflowDefinitions()
		throws PortalException {

		if (!isWorkflowEnabled()) {
			return null;
		}

		return WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(
			_themeDisplay.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	public boolean isRootFolder() {
		return ParamUtil.getBoolean(_httpServletRequest, "rootFolder");
	}

	public boolean isWorkflowEnabled() throws PortalException {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		WorkflowHandler<DLFileEntry> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				DLFileEntry.class.getName());

		if (WorkflowEngineManagerUtil.isDeployed() &&
			(workflowHandler != null) &&
			DLFolderPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), getFolderId(),
				ActionKeys.UPDATE) &&
			!scopeGroup.isLayoutSetPrototype()) {

			return true;
		}

		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}