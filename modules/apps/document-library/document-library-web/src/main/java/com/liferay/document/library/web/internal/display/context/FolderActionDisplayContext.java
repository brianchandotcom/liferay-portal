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
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.document.library.web.internal.security.permission.resource.DLFolderPermission;
import com.liferay.document.library.web.internal.security.permission.resource.DLPermission;
import com.liferay.document.library.web.internal.util.DLFolderUtil;
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
		HttpServletRequest httpServletRequest, DLTrashHelper dlTrashHelper) {

		_httpServletRequest = httpServletRequest;
		_dlTrashHelper = dlTrashHelper;

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
		portletURL.setParameter("redirect", _dlRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"repositoryId", String.valueOf(_getRepositoryId()));
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
		portletURL.setParameter("redirect", _dlRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"repositoryId", String.valueOf(_getRepositoryId()));
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
		portletURL.setParameter("redirect", _dlRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"repositoryId", String.valueOf(_getRepositoryId()));
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
		portletURL.setParameter("redirect", _dlRequestHelper.getCurrentURL());
		portletURL.setParameter("backURL", _dlRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"repositoryId", String.valueOf(_getRepositoryId()));
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
		portletURL.setParameter("redirect", _dlRequestHelper.getCurrentURL());
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
		portletURL.setParameter("redirect", _dlRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"repositoryId", String.valueOf(_getRepositoryId()));

		return portletURL.toString();
	}

	public String getDeleteFolderURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		Folder folder = _getFolder();

		if (!DLFolderUtil.isRepositoryRoot(folder)) {
			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/document_library/edit_folder");
		}
		else {
			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/document_library/edit_repository");
		}

		portletURL.setParameter(Constants.CMD, _getDeleteFolderCommand());
		portletURL.setParameter("redirect", _getParentFolderURL());

		if (!DLFolderUtil.isRepositoryRoot(folder)) {
			portletURL.setParameter("folderId", String.valueOf(_getFolderId()));
		}
		else {
			portletURL.setParameter(
				"repositoryId", String.valueOf(_getRepositoryId()));
		}

		return portletURL.toString();
	}

	public String getDownloadFolderURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		ResourceURL resourceURL = liferayPortletResponse.createResourceURL();

		resourceURL.setParameter("folderId", String.valueOf(_getFolderId()));
		resourceURL.setParameter(
			"repositoryId", String.valueOf(_getRepositoryId()));
		resourceURL.setResourceID("/document_library/download_folder");

		return resourceURL.toString();
	}

	public String getEditFolderURL() {
		Folder folder = _getFolder();

		ThemeDisplay themeDisplay = _dlRequestHelper.getThemeDisplay();

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			_httpServletRequest, themeDisplay.getScopeGroup(),
			DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0, 0,
			PortletRequest.RENDER_PHASE);

		if ((folder == null) || !DLFolderUtil.isRepositoryRoot(folder)) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/edit_folder");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/edit_repository");
		}

		portletURL.setParameter("redirect", _dlRequestHelper.getCurrentURL());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletURL.setParameter("portletResource", portletDisplay.getId());

		portletURL.setParameter("folderId", String.valueOf(_getFolderId()));
		portletURL.setParameter(
			"repositoryId", String.valueOf(_getRepositoryId()));

		if (folder == null) {
			portletURL.setParameter("rootFolder", Boolean.TRUE.toString());
		}

		return portletURL.toString();
	}

	public String getModelResource() {
		Folder folder = _getFolder();

		if (folder != null) {
			return DLFolderConstants.getClassName();
		}

		return "com.liferay.document.library";
	}

	public String getModelResourceDescription() throws PortalException {
		Folder folder = _getFolder();

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
		portletURL.setParameter("backURL", _dlRequestHelper.getCurrentURL());
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

	public long getResourcePrimKey() {
		Folder folder = _getFolder();

		if (folder != null) {
			return folder.getFolderId();
		}

		return _dlRequestHelper.getScopeGroupId();
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

	public boolean isAccessFromDesktopActionVisible() throws PortalException {
		PortletDisplay portletDisplay = _dlRequestHelper.getPortletDisplay();

		if (!_hasViewPermission() || !portletDisplay.isWebDAVEnabled()) {
			return false;
		}

		Folder folder = _getFolder();

		if ((folder == null) ||
			(folder.getRepositoryId() == _dlRequestHelper.getScopeGroupId())) {

			return true;
		}

		return false;
	}

	public boolean isAddFileShortcutActionVisible() throws PortalException {
		String portletName = _dlRequestHelper.getPortletName();

		if (!portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
			return false;
		}

		Folder folder = _getFolder();

		if ((folder != null) &&
			(folder.isMountPoint() || !folder.isSupportsShortcuts())) {

			return false;
		}

		if (DLFolderPermission.contains(
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

		Folder folder = _getFolder();

		if ((folder != null) && DLFolderUtil.isRepositoryRoot(folder)) {
			return false;
		}

		if (DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(), _getFolderId(),
				ActionKeys.ADD_DOCUMENT)) {

			return true;
		}

		return false;
	}

	public boolean isAddRepositoryActionVisible() throws PortalException {
		Folder folder = _getFolder();

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
		Folder folder = _getFolder();

		if (folder == null) {
			return false;
		}

		if (DLFolderUtil.isRepositoryRoot(folder) &&
			folder.isRepositoryCapabilityProvided(
				TemporaryFileEntriesCapability.class)) {

			return true;
		}

		return false;
	}

	public boolean isDeleteFolderActionVisible() throws PortalException {
		Folder folder = _getFolder();

		if (folder == null) {
			return false;
		}

		if (DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(), _getFolderId(),
				ActionKeys.DELETE)) {

			return true;
		}

		return false;
	}

	public boolean isDownloadFolderActionVisible() throws PortalException {
		Folder folder = _getFolder();

		if ((folder == null) ||
			RepositoryUtil.isExternalRepository(_getRepositoryId())) {

			return false;
		}

		if (_hasViewPermission()) {
			return true;
		}

		return false;
	}

	public boolean isEditFolderActionVisible() throws PortalException {
		if (!_isWorkflowEnabled()) {
			return false;
		}

		if (DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(), _getFolderId(),
				ActionKeys.UPDATE)) {

			return true;
		}

		return false;
	}

	public boolean isMoveFolderActionVisible() throws PortalException {
		Folder folder = _getFolder();

		if ((folder == null) || DLFolderUtil.isRepositoryRoot(folder)) {
			return false;
		}

		if (DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(), _getFolderId(),
				ActionKeys.UPDATE)) {

			return true;
		}

		return false;
	}

	public boolean isMultipleUploadSupported() {
		Folder folder = _getFolder();

		if ((folder == null) || folder.isSupportsMultipleUpload()) {
			return true;
		}

		return false;
	}

	public boolean isPermissionsActionVisible() throws PortalException {
		if (!_hasPermissionsPermission()) {
			return false;
		}

		Folder folder = _getFolder();

		if (folder == null) {
			return true;
		}

		if (!DLFolderUtil.isRepositoryRoot(folder)) {
			return true;
		}

		return false;
	}

	public boolean isPublishFolderActionVisible() throws PortalException {
		Folder folder = _getFolder();

		if (folder == null) {
			return false;
		}

		String portletName = _dlRequestHelper.getPortletName();

		if (!portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
			return false;
		}

		if (!GroupPermissionUtil.contains(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(),
				ActionKeys.EXPORT_IMPORT_PORTLET_INFO)) {

			return false;
		}

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (!stagingGroupHelper.isStagingGroup(
				_dlRequestHelper.getScopeGroupId())) {

			return false;
		}

		if (!stagingGroupHelper.isStagedPortlet(
				_dlRequestHelper.getScopeGroupId(),
				DLPortletKeys.DOCUMENT_LIBRARY)) {

			return false;
		}

		return true;
	}

	public boolean isShowActions() {
		DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper =
			new DLPortletInstanceSettingsHelper(_dlRequestHelper);

		if (dlPortletInstanceSettingsHelper.isShowActions()) {
			return true;
		}

		return false;
	}

	public boolean isTrashEnabled() throws PortalException {
		Folder folder = _getFolder();

		if (((folder == null) ||
			 folder.isRepositoryCapabilityProvided(TrashCapability.class)) &&
			_dlTrashHelper.isTrashEnabled(
				_dlRequestHelper.getScopeGroupId(), _getRepositoryId())) {

			return true;
		}

		return false;
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
				_getRepositoryId(), _getFolderId(), _getStatus());

		if (fileEntriesAndFileShortcutsCount == 0) {
			return false;
		}

		return true;
	}

	private String _getDeleteFolderCommand() throws PortalException {
		Folder folder = _getFolder();

		if (DLFolderUtil.isRepositoryRoot(folder)) {
			return Constants.DELETE;
		}

		if (isTrashEnabled()) {
			return Constants.MOVE_TO_TRASH;
		}

		return Constants.DELETE;
	}

	private Folder _getFolder() {
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

	private long _getFolderId() {
		Folder folder = _getFolder();

		if (folder == null) {
			return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		return folder.getFolderId();
	}

	private String _getParentFolderURL() {
		if (!_isView()) {
			return _dlRequestHelper.getCurrentURL();
		}

		String portletName = _dlRequestHelper.getPortletName();

		String mvcRenderCommandName = "/image_gallery_display/view";

		if (!portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
			mvcRenderCommandName = "/document_library/view";

			Folder folder = _getFolder();

			if ((folder != null) && !DLFolderUtil.isRepositoryRoot(folder) &&
				(folder.getParentFolderId() !=
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

				mvcRenderCommandName = "/document_library/view_folder";
			}
		}

		Folder folder = _getFolder();

		if (folder == null) {
			return StringPool.BLANK;
		}

		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);

		if (DLFolderUtil.isRepositoryRoot(folder)) {
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

	private long _getRepositoryId() {
		if (_repositoryId != null) {
			return _repositoryId;
		}

		Folder folder = _getFolder();

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

	private int _getStatus() {
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

	private boolean _hasPermissionsPermission() throws PortalException {
		Folder folder = _getFolder();

		if (folder != null) {
			return DLFolderPermission.contains(
				_dlRequestHelper.getPermissionChecker(), folder,
				ActionKeys.PERMISSIONS);
		}

		return DLPermission.contains(
			_dlRequestHelper.getPermissionChecker(),
			_dlRequestHelper.getScopeGroupId(), ActionKeys.PERMISSIONS);
	}

	private boolean _hasViewPermission() throws PortalException {
		return DLFolderPermission.contains(
			_dlRequestHelper.getPermissionChecker(),
			_dlRequestHelper.getScopeGroupId(), _getFolderId(),
			ActionKeys.VIEW);
	}

	private boolean _isView() {
		if (_view != null) {
			return _view;
		}

		Folder folder = (Folder)_httpServletRequest.getAttribute(
			"info_panel.jsp-folder");

		if ((folder != null) && (folder.getFolderId() == _getFolderId())) {
			return true;
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
	private final DLTrashHelper _dlTrashHelper;
	private Folder _folder;
	private final HttpServletRequest _httpServletRequest;
	private String _randomNamespace;
	private Long _repositoryId;
	private Integer _status;
	private Boolean _view;

}