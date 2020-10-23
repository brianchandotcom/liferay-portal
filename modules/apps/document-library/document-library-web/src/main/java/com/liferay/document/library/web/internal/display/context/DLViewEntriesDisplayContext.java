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

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.document.library.web.internal.search.EntriesChecker;
import com.liferay.document.library.web.internal.search.EntriesMover;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

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
	}

	public String getDisplayStyle() {
		return _dlAdminDisplayContext.getDisplayStyle();
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public SearchContainer<Object> getSearchContainer() throws PortalException {
		SearchContainer<Object> searchContainer =
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

	public boolean isDescriptiveDisplayStyle() {
		if (Objects.equals(getDisplayStyle(), "descriptive")) {
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

			return true;
		}

		return false;
	}

	private long _getRepositoryId() {
		return GetterUtil.getLong(
			_liferayPortletRequest.getAttribute("view.jsp-repositoryId"));
	}

	private final DLAdminDisplayContext _dlAdminDisplayContext;
	private final DLTrashHelper _dlTrashHelper;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private final ThemeDisplay _themeDisplay;

}