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
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.web.internal.security.permission.resource.DLFolderPermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.util.RepositoryUtil;

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

	public String getCmd() {
		if (isRootFolder()) {
			return "updateWorkflowDefinitions";
		}

		Folder folder = getFolder();

		if (folder == null) {
			return Constants.ADD;
		}

		return Constants.UPDATE;
	}

	public String getFileEntryTypeRestrictionsHelpMessage() {
		if (isRootFolder()) {
			return StringPool.BLANK;
		}

		return "document-type-restrictions-help";
	}

	public String getFileEntryTypeRestrictionsLabel() throws PortalException {
		if (isRootFolder()) {
			return StringPool.BLANK;
		}

		if (isWorkflowEnabled()) {
			return "document-type-restrictions-and-workflow";
		}

		return "document-type-restrictions";
	}

	public Folder getFolder() {
		if (_folder != null) {
			return _folder;
		}

		_folder = (Folder)_httpServletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FOLDER);

		return _folder;
	}

	public long getFolderId() {
		if (_folderId != null) {
			return _folderId;
		}

		_folderId = BeanParamUtil.getLong(
			getFolder(), _httpServletRequest, "folderId");

		return _folderId;
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

	public Folder getParentFolder() {
		try {
			if (_parentFolder != null) {
				return _parentFolder;
			}

			if (getParentFolderId() ==
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				return null;
			}

			_parentFolder = DLAppServiceUtil.getFolder(getParentFolderId());

			return _parentFolder;
		}
		catch (Exception exception) {
			return null;
		}
	}

	public long getParentFolderId() {
		if (_parentFolderId != null) {
			return _parentFolderId;
		}

		_parentFolderId = BeanParamUtil.getLong(
			getFolder(), _httpServletRequest, "parentFolderId",
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _parentFolderId;
	}

	public String getParentFolderName() {
		Folder folder = getParentFolder();

		if (folder == null) {
			return LanguageUtil.get(_httpServletRequest, "home");
		}

		return folder.getName();
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public long getRepositoryId() {
		if (_repositoryId != null) {
			return _repositoryId;
		}

		_repositoryId = BeanParamUtil.getLong(
			getFolder(), _httpServletRequest, "repositoryId");

		return _repositoryId;
	}

	public List<WorkflowDefinition> getWorkflowDefinitions()
		throws PortalException {

		if (_workflowDefinitions != null) {
			return _workflowDefinitions;
		}

		if (!isWorkflowEnabled()) {
			return null;
		}

		_workflowDefinitions =
			WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(
				_themeDisplay.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		return _workflowDefinitions;
	}

	public boolean isFileEntryTypeSupported() {
		Folder folder = getFolder();

		if (isRootFolder() ||
			((folder != null) && (folder.getModel() instanceof DLFolder))) {

			return true;
		}

		return false;
	}

	public boolean isRootFolder() {
		return ParamUtil.getBoolean(_httpServletRequest, "rootFolder");
	}

	public boolean isShowDescription() {
		Folder parentFolder = getParentFolder();

		if ((parentFolder == null) || parentFolder.isSupportsMetadata()) {
			return true;
		}

		return false;
	}

	public boolean isSupportsMetadata() {
		Folder parentFolder = getParentFolder();

		if ((parentFolder == null) || parentFolder.isSupportsMetadata()) {
			return true;
		}

		return false;
	}

	public boolean isSupportsPermissions() {
		Folder folder = getFolder();

		if ((folder == null) &&
			!RepositoryUtil.isExternalRepository(getRepositoryId())) {

			return true;
		}

		return false;
	}

	public boolean isWorkflowEnabled() throws PortalException {
		if (_workflowEnabled != null) {
			return _workflowEnabled;
		}

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

			_workflowEnabled = true;
		}
		else {
			_workflowEnabled = false;
		}

		return _workflowEnabled;
	}

	private Folder _folder;
	private Long _folderId;
	private final HttpServletRequest _httpServletRequest;
	private Folder _parentFolder;
	private Long _parentFolderId;
	private String _redirect;
	private Long _repositoryId;
	private final ThemeDisplay _themeDisplay;
	private List<WorkflowDefinition> _workflowDefinitions;
	private Boolean _workflowEnabled;

}