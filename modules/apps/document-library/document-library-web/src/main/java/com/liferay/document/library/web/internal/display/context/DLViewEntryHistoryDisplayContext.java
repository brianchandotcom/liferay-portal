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

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mikel Lorza
 */
public class DLViewEntryHistoryDisplayContext {

	public DLViewEntryHistoryDisplayContext(
		DLAppLocalService dlAppLocalService, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_dlAppLocalService = dlAppLocalService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getBackURL() {
		if (Validator.isNotNull(_backURL)) {
			return _backURL;
		}

		_backURL = ParamUtil.getString(_renderRequest, "backURL");

		return _backURL;
	}

	public FileEntry getFileEntry() {
		if (_fileEntry != null) {
			return _fileEntry;
		}

		_fileEntry = (FileEntry)_renderRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

		return _fileEntry;
	}

	public SearchContainer<FileVersion> getFileVersionSearchContainer() {
		SearchContainer<FileVersion> fileVersionSearchContainer =
			new SearchContainer(_renderRequest, getPortletURL(), null, null);

		int status = _getFileEntryStatus();

		fileVersionSearchContainer.setResultsAndTotal(
			() -> _fileEntry.getFileVersions(status),
			_fileEntry.getFileVersionsCount(status));

		fileVersionSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		return fileVersionSearchContainer;
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "versions"));
			}
		).build();
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/document_library/view_file_entry_history"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			getBackURL()
		).setParameter(
			"fileEntryId", _fileEntry.getFileEntryId()
		).setParameter(
			"groupId", _fileEntry.getGroupId()
		).setParameter(
			"referringPortletResource", getReferringPortletResource()
		).buildPortletURL();
	}

	public String getReferringPortletResource() {
		if (Validator.isNotNull(_referringPortletResource)) {
			return _referringPortletResource;
		}

		_referringPortletResource = ParamUtil.getString(
			_renderRequest, "referringPortletResource");

		return _referringPortletResource;
	}

	private int _getFileEntryStatus() {
		int status = WorkflowConstants.STATUS_APPROVED;

		User user = _themeDisplay.getUser();

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		if ((user.getUserId() == _fileEntry.getUserId()) ||
			permissionChecker.isContentReviewer(
				_themeDisplay.getCompanyId(),
				_themeDisplay.getScopeGroupId())) {

			status = WorkflowConstants.STATUS_ANY;
		}

		return status;
	}

	private String _getRedirect() {
		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		_redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(_renderRequest, "redirect"));

		return _redirect;
	}

	private String _backURL;
	private final DLAppLocalService _dlAppLocalService;
	private FileEntry _fileEntry;
	private final HttpServletRequest _httpServletRequest;
	private String _redirect;
	private String _referringPortletResource;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}