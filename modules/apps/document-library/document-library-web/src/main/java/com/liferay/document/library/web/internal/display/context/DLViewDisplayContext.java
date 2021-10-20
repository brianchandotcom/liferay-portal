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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.web.internal.display.context.logic.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.security.permission.resource.DLFolderPermission;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.FolderItemSelectorReturnType;
import com.liferay.item.selector.criteria.folder.criterion.FolderItemSelectorCriterion;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.comparator.AssetVocabularyGroupLocalizedTitleComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DLViewDisplayContext {

	public DLViewDisplayContext(
		DLAdminDisplayContext dlAdminDisplayContext,
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_dlAdminDisplayContext = dlAdminDisplayContext;
		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(
			new DLRequestHelper(httpServletRequest));
	}

	public String getAddFileEntryURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/document_library/edit_file_entry"
		).setCMD(
			Constants.ADD
		).setRedirect(
			_getRedirect()
		).setParameter(
			"folderId", _dlAdminDisplayContext.getFolderId()
		).setParameter(
			"groupId",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return themeDisplay.getScopeGroupId();
			}
		).setParameter(
			"repositoryId", _dlAdminDisplayContext.getRepositoryId()
		).buildString();
	}

	public String getColumnNames() {
		return Stream.of(
			_dlPortletInstanceSettingsHelper.getEntryColumns()
		).map(
			HtmlUtil::escapeJS
		).collect(
			Collectors.joining("','")
		);
	}

	public String getDownloadEntryURL() {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setParameter(
			"folderId", String.valueOf(_dlAdminDisplayContext.getFolderId()));
		resourceURL.setResourceID("/document_library/download_entry");

		return resourceURL.toString();
	}

	public String getEditEntryURL() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return StringPool.BLANK;
		}

		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/document_library/edit_entry"
		).buildString();
	}

	public String getEditFileEntryURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/document_library/edit_file_entry"
		).buildString();
	}

	public Folder getFolder() {
		return _dlAdminDisplayContext.getFolder();
	}

	public long getFolderId() {
		return _dlAdminDisplayContext.getFolderId();
	}

	public long getRepositoryId() {
		return _dlAdminDisplayContext.getRepositoryId();
	}

	public String getRestoreTrashEntriesURL() {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/document_library/edit_entry"
		).setCMD(
			Constants.RESTORE
		).buildString();
	}

	public String getSelectCategoriesURL()
		throws PortalException, WindowStateException {

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				_httpServletRequest, AssetCategory.class.getName(),
				PortletProvider.Action.BROWSE)
		).setParameter(
			"eventName", _renderResponse.getNamespace() + "selectCategories"
		).setParameter(
			"selectedCategories", "{selectedCategories}"
		).setParameter(
			"singleSelect", "{singleSelect}"
		).setParameter(
			"vocabularyIds", "{vocabularyIds}"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getSelectFileEntryTypeURL() throws WindowStateException {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/document_library/select_file_entry_type.jsp"
		).setParameter(
			"fileEntryTypeId", _getFileEntryTypeId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getSelectFolderURL() throws WindowStateException {
		ItemSelector itemSelector =
			(ItemSelector)_httpServletRequest.getAttribute(
				ItemSelector.class.getName());

		FolderItemSelectorCriterion folderItemSelectorCriterion =
			new FolderItemSelectorCriterion();

		folderItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FolderItemSelectorReturnType());
		folderItemSelectorCriterion.setFolderId(getFolderId());
		folderItemSelectorCriterion.setSelectedFolderId(getFolderId());

		PortletURL portletURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_renderRequest),
			"itemSelected", folderItemSelectorCriterion);

		return portletURL.toString();
	}

	public String getSidebarPanelURL() {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setParameter(
			"folderId", String.valueOf(_dlAdminDisplayContext.getFolderId()));
		resourceURL.setParameter(
			"repositoryId",
			String.valueOf(_dlAdminDisplayContext.getRepositoryId()));
		resourceURL.setResourceID("/document_library/info_panel");

		return resourceURL.toString();
	}

	public String getUploadURL() throws PortalException {
		if (!isUploadable()) {
			return StringPool.BLANK;
		}

		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/document_library/edit_file_entry"
		).setCMD(
			Constants.ADD_DYNAMIC
		).setParameter(
			"folderId", "{folderId}"
		).setParameter(
			"repositoryId", _dlAdminDisplayContext.getRepositoryId()
		).buildString();
	}

	public String getViewFileEntryTypeURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(_getCurrentPortletURL(), _renderResponse)
		).setParameter(
			"browseBy", "file-entry-type"
		).setParameter(
			"fileEntryTypeId", (String)null
		).buildString();
	}

	public String getViewFileEntryURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/document_library/view_file_entry"
		).setRedirect(
			_getRedirect()
		).buildString();
	}

	public String getViewMoreFileEntryTypesURL() throws WindowStateException {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/document_library/view_more_menu_items.jsp"
		).setParameter(
			"eventName", _renderResponse.getNamespace() + "selectAddMenuItem"
		).setParameter(
			"folderId", _dlAdminDisplayContext.getFolderId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public boolean isFileEntryMetadataSetsNavigation() {
		if (Objects.equals(_getNavigation(), "file_entry_metadata_sets")) {
			return true;
		}

		return false;
	}

	public boolean isFileEntryTypesNavigation() {
		if (Objects.equals(_getNavigation(), "file_entry_types")) {
			return true;
		}

		return false;
	}

	public boolean isOpenInMSOfficeEnabled() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (portletDisplay.isWebDAVEnabled() &&
			BrowserSnifferUtil.isIeOnWin32(_httpServletRequest)) {

			return true;
		}

		return false;
	}

	public boolean isSearch() {
		return _dlAdminDisplayContext.isSearch();
	}

	public boolean isShowFolderDescription() {
		if (_dlAdminDisplayContext.isDefaultFolderView()) {
			return false;
		}

		Folder folder = _dlAdminDisplayContext.getFolder();

		if (folder == null) {
			return false;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return false;
	}

	public boolean isUploadable() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!DLFolderPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				_dlAdminDisplayContext.getFolderId(),
				ActionKeys.ADD_DOCUMENT)) {

			return false;
		}

		List<AssetVocabulary> assetVocabularies = new ArrayList<>(
			AssetVocabularyServiceUtil.getGroupVocabularies(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					themeDisplay.getScopeGroupId())));

		assetVocabularies.sort(
			new AssetVocabularyGroupLocalizedTitleComparator(
				themeDisplay.getScopeGroupId(), themeDisplay.getLocale(),
				true));

		long classNameId = ClassNameLocalServiceUtil.getClassNameId(
			DLFileEntryConstants.getClassName());

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			if (assetVocabulary.isRequired(
					classNameId,
					DLFileEntryTypeConstants.
						FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)) {

				return false;
			}
		}

		return true;
	}

	private PortletURL _getCurrentPortletURL() {
		return PortletURLUtil.getCurrent(_renderRequest, _renderResponse);
	}

	private long _getFileEntryTypeId() {
		return ParamUtil.getLong(_httpServletRequest, "fileEntryTypeId", -1);
	}

	private String _getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_httpServletRequest, "navigation");

		return _navigation;
	}

	private String _getRedirect() {
		PortletURL portletURL = _getCurrentPortletURL();

		return portletURL.toString();
	}

	private final DLAdminDisplayContext _dlAdminDisplayContext;
	private final DLPortletInstanceSettingsHelper
		_dlPortletInstanceSettingsHelper;
	private final HttpServletRequest _httpServletRequest;
	private String _navigation;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}