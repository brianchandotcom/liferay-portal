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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

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

	public String getDownloadEntryURL() {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setParameter(
			"folderId", String.valueOf(_dlAdminDisplayContext.getFolderId()));
		resourceURL.setResourceID("/document_library/download_entry");

		return resourceURL.toString();
	}

	public String getEditEntryURL() {
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

	public String getRestoreTrashEntriesURL() {
		ActionURL actionURL = _renderResponse.createActionURL();

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

	public String getUploadURL() {
		ActionURL actionURL = _renderResponse.createActionURL();

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
	private final HttpServletRequest _httpServletRequest;
	private String _navigation;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}