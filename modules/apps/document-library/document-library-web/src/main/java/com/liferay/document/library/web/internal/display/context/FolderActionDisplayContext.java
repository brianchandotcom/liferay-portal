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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.web.internal.display.context.logic.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.security.permission.resource.DLFolderPermission;
import com.liferay.document.library.web.internal.security.permission.resource.DLPermission;
import com.liferay.document.library.web.internal.util.DLTrashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.util.RepositoryUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class FolderActionDisplayContext {

	public FolderActionDisplayContext(
		HttpServletRequest httpServletRequest, DLTrashUtil dlTrashUtil) {

		_httpServletRequest = httpServletRequest;
		_dlTrashUtil = dlTrashUtil;

		_dlRequestHelper = new DLRequestHelper(httpServletRequest);
	}

	public String getAddFileShortcutURL() {
		ThemeDisplay themeDisplay = _dlRequestHelper.getThemeDisplay();

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			_httpServletRequest, themeDisplay.getScopeGroup(),
			DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_shortcut");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"repositoryId", String.valueOf(getRepositoryId()));
		portletURL.setParameter("folderId", String.valueOf(_getFolderId()));

		return portletURL.toString();
	}

	public String getAddFolderURL() {
		ThemeDisplay themeDisplay = _dlRequestHelper.getThemeDisplay();

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			_httpServletRequest, themeDisplay.getScopeGroup(),
			DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_folder");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"repositoryId", String.valueOf(getRepositoryId()));
		portletURL.setParameter(
			"parentFolderId", String.valueOf(_getFolderId()));
		portletURL.setParameter("ignoreRootFolder", Boolean.TRUE.toString());

		return portletURL.toString();
	}

	public String getAddMediaURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"repositoryId", String.valueOf(getRepositoryId()));
		portletURL.setParameter("folderId", String.valueOf(_getFolderId()));

		return portletURL.toString();
	}

	public String getAddMultipleMediaURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/document_library/upload_multiple_file_entries");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter("backURL", getRedirect());
		portletURL.setParameter(
			"repositoryId", String.valueOf(getRepositoryId()));
		portletURL.setParameter("folderId", String.valueOf(_getFolderId()));

		return portletURL.toString();
	}

	public String getAddRepositoryURL() {
		ThemeDisplay themeDisplay = _dlRequestHelper.getThemeDisplay();

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			_httpServletRequest, themeDisplay.getScopeGroup(),
			DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_repository");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter("folderId", String.valueOf(_getFolderId()));

		return portletURL.toString();
	}

	public String getDeleteExpiredTemporaryFileEntriesURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/edit_folder");
		portletURL.setParameter(
			Constants.CMD, "deleteExpiredTemporaryFileEntries");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"repositoryId", String.valueOf(getRepositoryId()));

		return portletURL.toString();
	}

	public String getDeleteFolderURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		Folder folder = getFolder();

		if (!folder.isMountPoint() && !folder.isRoot()) {
			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/document_library/edit_folder");
		}
		else {
			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/document_library/edit_repository");
		}

		portletURL.setParameter(Constants.CMD, _getDeleteFolderCommand());
		portletURL.setParameter("redirect", _getParentFolderURL());

		if (!folder.isMountPoint() && !folder.isRoot()) {
			portletURL.setParameter("folderId", String.valueOf(_getFolderId()));
		}
		else {
			portletURL.setParameter(
				"repositoryId", String.valueOf(getRepositoryId()));
		}

		return portletURL.toString();
	}

	public String getDownloadFolderURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		ResourceURL resourceURL = liferayPortletResponse.createResourceURL();

		resourceURL.setParameter("folderId", String.valueOf(_getFolderId()));
		resourceURL.setParameter(
			"repositoryId", String.valueOf(getRepositoryId()));
		resourceURL.setResourceID("/document_library/download_folder");

		return resourceURL.toString();
	}

	public String getEditFolderURL() {
		Folder folder = getFolder();

		if (folder == null) {
			ThemeDisplay themeDisplay = _dlRequestHelper.getThemeDisplay();

			PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
				_httpServletRequest, themeDisplay.getScopeGroup(),
				DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0, 0,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/edit_folder");
			portletURL.setParameter("redirect", getRedirect());
			portletURL.setParameter("folderId", String.valueOf(_getFolderId()));
			portletURL.setParameter(
				"repositoryId", String.valueOf(getRepositoryId()));
			portletURL.setParameter("rootFolder", Boolean.TRUE.toString());

			return portletURL.toString();
		}

		if (!folder.isMountPoint() && !folder.isRoot()) {
			ThemeDisplay themeDisplay = _dlRequestHelper.getThemeDisplay();

			PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
				_httpServletRequest, themeDisplay.getScopeGroup(),
				DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0, 0,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/edit_folder");
			portletURL.setParameter("redirect", getRedirect());
			portletURL.setParameter("folderId", String.valueOf(_getFolderId()));
			portletURL.setParameter(
				"repositoryId", String.valueOf(getRepositoryId()));

			return portletURL.toString();
		}

		ThemeDisplay themeDisplay = _dlRequestHelper.getThemeDisplay();

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			_httpServletRequest, themeDisplay.getScopeGroup(),
			DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_repository");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter("folderId", String.valueOf(_getFolderId()));
		portletURL.setParameter(
			"repositoryId", String.valueOf(getRepositoryId()));

		return portletURL.toString();
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

	public String getMoveFolderURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		return StringBundler.concat(
			"javascript:", liferayPortletResponse.getNamespace(),
			"move(1, 'rowIdsFolder', ", _getFolderId(), ");");
	}

	public String getPublishFolderURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/publish_folder");
		portletURL.setParameter("backURL", getRedirect());
		portletURL.setParameter("folderId", String.valueOf(_getFolderId()));

		return portletURL.toString();
	}

	public String getRandomNamespace() {
		if (_randomNamespace != null) {
			return _randomNamespace;
		}

		String portletName = _dlRequestHelper.getPortletName();

		if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			String randomKey = PortalUtil.generateRandomKey(
				_httpServletRequest, "portlet_document_library_folder_action");

			_randomNamespace = randomKey + StringPool.UNDERLINE;
		}
		else {
			String randomKey = PortalUtil.generateRandomKey(
				_httpServletRequest,
				"portlet_image_gallery_display_folder_action");

			_randomNamespace = randomKey + StringPool.UNDERLINE;
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

	public String getViewSlideShowURL() throws WindowStateException {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/image_gallery_display/view_slide_show");
		portletURL.setParameter("folderId", String.valueOf(_getFolderId()));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private boolean _hasViewPermission() throws PortalException {
		return DLFolderPermission.contains(
			_dlRequestHelper.getPermissionChecker(),
			_dlRequestHelper.getScopeGroupId(), _getFolderId(),
			ActionKeys.VIEW);
	}

	public boolean isAccessFromDesktopActionVisible() throws PortalException {
		PortletDisplay portletDisplay = _dlRequestHelper.getPortletDisplay();

		Folder folder = getFolder();

		if (_hasViewPermission() && portletDisplay.isWebDAVEnabled() &&
			((folder == null) ||
			 (folder.getRepositoryId() ==
				 _dlRequestHelper.getScopeGroupId()))) {

			return true;
		}

		return false;
	}

	public boolean isAddFileShortcutActionVisible() throws PortalException {
		String portletName = _dlRequestHelper.getPortletName();

		if (!portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
			return false;
		}

		Folder folder = getFolder();

		if (((folder == null) ||
			 (!folder.isMountPoint() && folder.isSupportsShortcuts())) &&
			DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(), _getFolderId(),
				ActionKeys.ADD_SHORTCUT)) {

			return true;
		}

		return false;
	}

	public boolean isAddFolderActionVisible() throws PortalException {
		if (DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(), _getFolderId(),
				ActionKeys.ADD_FOLDER)) {

			return true;
		}

		return false;
	}

	public boolean isAddMediaActionVisible() throws PortalException {
		String portletName = _dlRequestHelper.getPortletName();

		if (!portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
			return false;
		}

		Folder folder = getFolder();

		if (((folder == null) || !folder.isMountPoint()) &&
			DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(), _getFolderId(),
				ActionKeys.ADD_DOCUMENT)) {

			return true;
		}

		return false;
	}

	public boolean isAddRepositoryActionVisible() throws PortalException {
		Folder folder = getFolder();

		if (folder != null) {
			return false;
		}

		if (DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(), _getFolderId(),
				ActionKeys.ADD_REPOSITORY)) {

			return true;
		}

		return false;
	}

	public boolean isDeleteExpiredTemporaryFileEntriesActionVisible() {
		Folder folder = getFolder();

		if ((folder != null) && folder.isMountPoint() &&
			folder.isRepositoryCapabilityProvided(
				TemporaryFileEntriesCapability.class)) {

			return true;
		}

		return false;
	}

	public boolean isDeleteFolderActionVisible() throws PortalException {
		Folder folder = getFolder();

		if ((folder != null) &&
			DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(), _getFolderId(),
				ActionKeys.DELETE)) {

			return true;
		}

		return false;
	}

	public boolean isDownloadFolderActionVisible() throws PortalException {
		Folder folder = getFolder();

		if (_hasViewPermission() &&
			((folder != null) ||
			 !RepositoryUtil.isExternalRepository(_getFolderId()))) {

			return true;
		}

		return false;
	}

	public boolean isEditFolderActionVisible() throws PortalException {
		if (_isWorkflowEnabled() &&
			DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(), _getFolderId(),
				ActionKeys.UPDATE)) {

			return true;
		}

		return false;
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

	public boolean isMoveFolderActionVisible() throws PortalException {
		Folder folder = getFolder();

		if (DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(), _getFolderId(),
				ActionKeys.UPDATE) &&
			(folder != null) &&
			!(folder.isMountPoint() ||
			  (RepositoryUtil.isExternalRepository(getRepositoryId()) &&
			   folder.isRoot()))) {

			return true;
		}

		return false;
	}

	public boolean isMultipleUploadSupported() {
		Folder folder = getFolder();

		if ((folder == null) || folder.isSupportsMultipleUpload()) {
			return true;
		}

		return false;
	}

	public boolean isPermissionsActionVisible() throws PortalException {
		Folder folder = getFolder();

		if (_hasPermissionsPermission() &&
			!(folder.isMountPoint() ||
			  (RepositoryUtil.isExternalRepository(getRepositoryId()) &&
			   folder.isRoot()))) {

			return true;
		}

		return false;
	}

	public boolean isPublishFolderActionVisible() throws PortalException {
		Folder folder = getFolder();

		String portletName = _dlRequestHelper.getPortletName();

		boolean documentLibraryAdmin = portletName.equals(
			DLPortletKeys.DOCUMENT_LIBRARY_ADMIN);

		boolean hasExportImportPortletInfoPermission =
			GroupPermissionUtil.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(),
				ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		boolean inStagingGroup = stagingGroupHelper.isStagingGroup(
			_dlRequestHelper.getScopeGroupId());

		boolean portletStaged = stagingGroupHelper.isStagedPortlet(
			_dlRequestHelper.getScopeGroupId(), DLPortletKeys.DOCUMENT_LIBRARY);

		if ((folder != null) && documentLibraryAdmin &&
			hasExportImportPortletInfoPermission && inStagingGroup &&
			portletStaged) {

			return true;
		}

		return false;
	}

	public boolean isShowActions() {
		DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper =
			new DLPortletInstanceSettingsHelper(_dlRequestHelper);

		if (dlPortletInstanceSettingsHelper.isShowActions()) {
			return true;
		}

		return false;
	}

	private boolean _hasPermissionsPermission() throws PortalException {
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

	public boolean isTrashEnabled() throws PortalException {
		Folder folder = getFolder();

		if (((folder == null) ||
			 folder.isRepositoryCapabilityProvided(TrashCapability.class)) &&
			_dlTrashUtil.isTrashEnabled(
				_dlRequestHelper.getScopeGroupId(), getRepositoryId())) {

			return true;
		}

		return false;
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

	public boolean isViewSlideShowActionVisible() throws PortalException {
		String portletName = _dlRequestHelper.getPortletName();

		if (!portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
			return false;
		}

		if (!_hasViewPermission()) {
			return false;
		}

		int fileEntriesAndFileShortcutsCount =
			DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(
				getRepositoryId(), _getFolderId(), getStatus());

		if (fileEntriesAndFileShortcutsCount == 0) {
			return false;
		}

		return true;
	}

	private String _getDeleteFolderCommand() throws PortalException {
		Folder folder = getFolder();

		if (folder.isMountPoint() || folder.isRoot()) {
			return Constants.DELETE;
		}

		if (isTrashEnabled()) {
			return Constants.MOVE_TO_TRASH;
		}

		return Constants.DELETE;
	}

	private long _getFolderId() {
		Folder folder = getFolder();

		if (folder == null) {
			return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		return folder.getFolderId();
	}

	private String _getParentFolderURL() {
		if (!isView() && !isFolderSelected()) {
			return getRedirect();
		}

		String portletName = _dlRequestHelper.getPortletName();

		String mvcRenderCommandName = "/image_gallery_display/view";

		if (!portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
			mvcRenderCommandName = "/document_library/view";

			Folder folder = getFolder();

			if ((folder != null) &&
				(folder.getParentFolderId() !=
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

				mvcRenderCommandName = "/document_library/view_folder";
			}
		}

		Folder folder = getFolder();

		if (folder == null) {
			return StringPool.BLANK;
		}

		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);

		if (folder.isMountPoint() || folder.isRoot()) {
			portletURL.setParameter(
				"folderId",
				String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));
		}
		else {
			portletURL.setParameter(
				"folderId", String.valueOf(folder.getParentFolderId()));
		}

		return portletURL.toString();
	}

	private boolean _isWorkflowEnabled() {
		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return false;
		}

		WorkflowHandler<Object> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				DLFileEntry.class.getName());

		if (workflowHandler == null) {
			return false;
		}

		return true;
	}

	private final DLRequestHelper _dlRequestHelper;
	private final DLTrashUtil _dlTrashUtil;
	private Folder _folder;
	private Boolean _folderSelected;
	private final HttpServletRequest _httpServletRequest;
	private String _randomNamespace;
	private Long _repositoryId;
	private Integer _status;
	private Boolean _view;

}