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

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.display.context.logic.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.document.library.web.internal.search.EntriesChecker;
import com.liferay.document.library.web.internal.search.EntriesMover;
import com.liferay.document.library.web.internal.security.permission.resource.DLFileEntryPermission;
import com.liferay.document.library.web.internal.security.permission.resource.DLFolderPermission;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.RepositoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DLViewEntriesDisplayContext {

	public DLViewEntriesDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_dlAdminDisplayContext =
			(DLAdminDisplayContext)liferayPortletRequest.getAttribute(
				DLAdminDisplayContext.class.getName());
		_dlTrashHelper = (DLTrashHelper)liferayPortletRequest.getAttribute(
			DLWebKeys.DOCUMENT_LIBRARY_TRASH_HELPER);
		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_dlRequestHelper = new DLRequestHelper(_httpServletRequest);
	}

	public List<String> getAvailableActions(FileEntry fileEntry)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		if (DLFileEntryPermission.contains(
				permissionChecker, fileEntry, ActionKeys.DELETE)) {

			availableActions.add("deleteEntries");
		}

		if (DLFileEntryPermission.contains(
				permissionChecker, fileEntry, ActionKeys.UPDATE)) {

			availableActions.add("collectDigitalSignature");

			availableActions.add("move");

			if (fileEntry.isCheckedOut()) {
				availableActions.add("checkin");
			}
			else {
				availableActions.add("checkout");
			}

			if (!RepositoryUtil.isExternalRepository(
					fileEntry.getRepositoryId()) &&
				!_hasWorkflowDefinitionLink(fileEntry) &&
				!_isCheckedOutByAnotherUser(fileEntry)) {

				if (_hasValidAssetVocabularies(
						_themeDisplay.getScopeGroupId())) {

					availableActions.add("editCategories");
				}

				availableActions.add("editTags");
			}
		}

		if (DLFileEntryPermission.contains(
				permissionChecker, fileEntry, ActionKeys.VIEW)) {

			availableActions.add("download");
		}

		return availableActions;
	}

	public List<String> getAvailableActions(Folder folder)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		if (DLFolderPermission.contains(
				permissionChecker, folder, ActionKeys.DELETE)) {

			availableActions.add("deleteEntries");
		}

		if (DLFolderPermission.contains(
				permissionChecker, folder, ActionKeys.UPDATE) &&
			!folder.isMountPoint()) {

			availableActions.add("move");
		}

		if (DLFolderPermission.contains(
				permissionChecker, folder, ActionKeys.VIEW) &&
			!RepositoryUtil.isExternalRepository(folder.getRepositoryId())) {

			availableActions.add("download");
		}

		return availableActions;
	}

	public String getDisplayStyle() {
		return _dlAdminDisplayContext.getDisplayStyle();
	}

	public String[] getEntryColumns() {
		DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper =
			new DLPortletInstanceSettingsHelper(_dlRequestHelper);

		return dlPortletInstanceSettingsHelper.getEntryColumns();
	}

	public FileVersion getLatestFileVersion(FileEntry fileEntry)
		throws PortalException {

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		User user = _themeDisplay.getUser();

		if ((_themeDisplay.getUserId() == fileEntry.getUserId()) ||
			permissionChecker.isContentReviewer(
				user.getCompanyId(), _themeDisplay.getScopeGroupId()) ||
			DLFileEntryPermission.contains(
				permissionChecker, fileEntry, ActionKeys.UPDATE)) {

			FileVersion fileVersion = fileEntry.getLatestFileVersion();

			return fileVersion.toEscapedModel();
		}

		FileVersion fileVersion = fileEntry.getFileVersion();

		return fileVersion.toEscapedModel();
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public SearchContainer<RepositoryEntry> getSearchContainer()
		throws PortalException {

		SearchContainer<RepositoryEntry> searchContainer =
			_dlAdminDisplayContext.getSearchContainer();

		EntriesChecker entriesChecker = new EntriesChecker(
			_liferayPortletRequest, _liferayPortletResponse);

		entriesChecker.setCssClass("entry-selector");

		entriesChecker.setRememberCheckBoxStateURLRegex(
			_dlAdminDisplayContext.getRememberCheckBoxStateURLRegex());

		searchContainer.setRowChecker(entriesChecker);

		if (!BrowserSnifferUtil.isMobile(_httpServletRequest)) {
			EntriesMover entriesMover = new EntriesMover(
				_dlTrashHelper.isTrashEnabled(
					_themeDisplay.getScopeGroupId(), _getRepositoryId()));

			searchContainer.setRowMover(entriesMover);
		}

		return searchContainer;
	}

	public String getThumbnailSrc(FileVersion fileVersion) throws Exception {
		return DLURLHelperUtil.getThumbnailSrc(
			fileVersion.getFileEntry(), fileVersion, _themeDisplay);
	}

	public String getViewFileEntryURL(FileEntry fileEntry) {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/document_library/view_file_entry"
		).setRedirect(
			HttpUtil.removeParameter(
				_dlRequestHelper.getCurrentURL(),
				_liferayPortletResponse.getNamespace() + "ajax")
		).setParameter(
			"fileEntryId", fileEntry.getFileEntryId()
		).buildString();
	}

	public boolean isDescriptiveDisplayStyle() {
		if (Objects.equals(getDisplayStyle(), "descriptive")) {
			return true;
		}

		return false;
	}

	public boolean isDraggable(FileEntry fileEntry) throws PortalException {
		if (!BrowserSnifferUtil.isMobile(_httpServletRequest) &&
			(DLFileEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), fileEntry,
				ActionKeys.DELETE) ||
			 DLFileEntryPermission.contains(
				 _themeDisplay.getPermissionChecker(), fileEntry,
				 ActionKeys.UPDATE))) {

			return true;
		}

		return false;
	}

	public boolean isDraggable(Folder folder) throws PortalException {
		if (!BrowserSnifferUtil.isMobile(_httpServletRequest) &&
			(DLFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), folder,
				ActionKeys.DELETE) ||
			 DLFolderPermission.contains(
				 _themeDisplay.getPermissionChecker(), folder,
				 ActionKeys.UPDATE))) {

			return true;
		}

		return false;
	}

	public boolean isIconDisplayStyle() {
		if (Objects.equals(getDisplayStyle(), "icon")) {
			return true;
		}

		return false;
	}

	public boolean isRootFolder() {
		long folderId = _dlAdminDisplayContext.getFolderId();

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId != _dlAdminDisplayContext.getRootFolderId())) {

			return false;
		}

		return true;
	}

	public boolean isVersioningStrategyOverridable() {
		return _dlAdminDisplayContext.isVersioningStrategyOverridable();
	}

	private long _getRepositoryId() {
		return GetterUtil.getLong(
			_liferayPortletRequest.getAttribute("view.jsp-repositoryId"));
	}

	private boolean _hasValidAssetVocabularies(long scopeGroupId)
		throws PortalException {

		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId));

		Stream<AssetVocabulary> stream = assetVocabularies.stream();

		return stream.anyMatch(
			assetVocabulary -> {
				if (!assetVocabulary.isAssociatedToClassNameId(
						ClassNameLocalServiceUtil.getClassNameId(
							DLFileEntry.class.getName()))) {

					return false;
				}

				int count =
					AssetCategoryServiceUtil.getVocabularyCategoriesCount(
						assetVocabulary.getGroupId(),
						assetVocabulary.getVocabularyId());

				if (count > 0) {
					return true;
				}

				return false;
			});
	}

	private boolean _hasWorkflowDefinitionLink(FileEntry fileEntry) {
		if (!(fileEntry.getModel() instanceof DLFileEntry)) {
			return false;
		}

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		if (_hasWorkflowDefinitionLink(
				dlFileEntry.getFolderId(), dlFileEntry.getFileEntryTypeId())) {

			return true;
		}

		return false;
	}

	private boolean _hasWorkflowDefinitionLink(
		long folderId, long fileEntryTypeId) {

		return DLUtil.hasWorkflowDefinitionLink(
			_themeDisplay.getCompanyId(), _themeDisplay.getScopeGroupId(),
			folderId, fileEntryTypeId);
	}

	private boolean _isCheckedOutByAnotherUser(FileEntry fileEntry) {
		if (fileEntry.isCheckedOut() && !fileEntry.hasLock()) {
			return true;
		}

		return false;
	}

	private final DLAdminDisplayContext _dlAdminDisplayContext;
	private final DLRequestHelper _dlRequestHelper;
	private final DLTrashHelper _dlTrashHelper;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private final ThemeDisplay _themeDisplay;

}