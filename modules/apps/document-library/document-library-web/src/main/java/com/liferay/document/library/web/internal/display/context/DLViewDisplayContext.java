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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
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

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;
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
		RenderURL renderURL = _renderResponse.createRenderURL();

		renderURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		renderURL.setParameter(Constants.CMD, Constants.ADD);
		renderURL.setParameter("redirect", _getRedirect());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		renderURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));

		renderURL.setParameter(
			"repositoryId",
			String.valueOf(_dlAdminDisplayContext.getRepositoryId()));
		renderURL.setParameter(
			"folderId", String.valueOf(_dlAdminDisplayContext.getFolderId()));

		return renderURL.toString();
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

		ActionURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/edit_entry");

		return actionURL.toString();
	}

	public String getEditFileEntryURL() {
		RenderURL renderURL = _renderResponse.createRenderURL();

		renderURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");

		return renderURL.toString();
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
		ActionURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/edit_entry");
		actionURL.setParameter(Constants.CMD, Constants.RESTORE);

		return actionURL.toString();
	}

	public String getSelectCategoriesURL()
		throws PortalException, WindowStateException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			_httpServletRequest, AssetCategory.class.getName(),
			PortletProvider.Action.BROWSE);

		portletURL.setParameter(
			"eventName", _renderResponse.getNamespace() + "selectCategories");
		portletURL.setParameter("selectedCategories", "{selectedCategories}");
		portletURL.setParameter("singleSelect", "{singleSelect}");
		portletURL.setParameter("vocabularyIds", "{vocabularyIds}");
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public String getSelectFileEntryTypeURL() throws WindowStateException {
		RenderURL renderURL = _renderResponse.createRenderURL();

		renderURL.setParameter(
			"mvcPath", "/document_library/select_file_entry_type.jsp");
		renderURL.setParameter(
			"fileEntryTypeId", String.valueOf(_getFileEntryTypeId()));
		renderURL.setWindowState(LiferayWindowState.POP_UP);

		return renderURL.toString();
	}

	public String getSelectFolderURL() throws WindowStateException {
		RenderURL renderURL = _renderResponse.createRenderURL();

		renderURL.setParameter(
			"mvcRenderCommandName", "/document_library/select_folder");
		renderURL.setParameter(
			"folderId", String.valueOf(_dlAdminDisplayContext.getFolderId()));
		renderURL.setWindowState(LiferayWindowState.POP_UP);

		return renderURL.toString();
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

		ActionURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/edit_file_entry");
		actionURL.setParameter(Constants.CMD, Constants.ADD_DYNAMIC);
		actionURL.setParameter("folderId", "{folderId}");
		actionURL.setParameter(
			"repositoryId",
			String.valueOf(_dlAdminDisplayContext.getRepositoryId()));

		return actionURL.toString();
	}

	public String getViewFileEntryTypeURL() throws PortletException {
		PortletURL portletURL = PortletURLUtil.clone(
			_getCurrentPortletURL(), _renderResponse);

		portletURL.setParameter("browseBy", "file-entry-type");
		portletURL.setParameter("fileEntryTypeId", (String)null);

		return portletURL.toString();
	}

	public String getViewFileEntryURL() {
		RenderURL renderURL = _renderResponse.createRenderURL();

		renderURL.setParameter(
			"mvcRenderCommandName", "/document_library/view_file_entry");
		renderURL.setParameter("redirect", _getRedirect());

		return renderURL.toString();
	}

	public String getViewMoreFileEntryTypesURL() throws WindowStateException {
		RenderURL renderURL = _renderResponse.createRenderURL();

		renderURL.setParameter(
			"mvcPath", "/document_library/view_more_menu_items.jsp");
		renderURL.setParameter(
			"folderId", String.valueOf(_dlAdminDisplayContext.getFolderId()));
		renderURL.setParameter(
			"eventName", _renderResponse.getNamespace() + "selectAddMenuItem");
		renderURL.setWindowState(LiferayWindowState.POP_UP);

		return renderURL.toString();
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