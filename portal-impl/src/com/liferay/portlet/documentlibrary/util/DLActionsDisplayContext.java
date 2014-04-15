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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.trash.util.TrashUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
public class DLActionsDisplayContext {

	public DLActionsDisplayContext(HttpServletRequest request) {
		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_portletDisplay = themeDisplay.getPortletDisplay();
		_scopeGroupId = themeDisplay.getScopeGroupId();
	}

	public boolean isIEOnWin32() {
		if (_ieOnWin32 == null) {
			_ieOnWin32 = BrowserSnifferUtil.isIeOnWin32(_request);
		}

		return _ieOnWin32;
	}

	public boolean isShowActions() throws PortalException, SystemException {
		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			new DLPortletInstanceSettings(
				_portletDisplay.getPortletInstanceSettings());

		return dlPortletInstanceSettings.getShowActions();
	}

	public boolean isShowMinimalActionsButton() {
		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return ParamUtil.getBoolean(_request, "showMinimalActionButtons");
	}

	public boolean isAddFolderButtonVisible() {
		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return false;
	}

	public boolean isFolderMenuVisible()
		throws PortalException, SystemException {

		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			new DLPortletInstanceSettings(
				_portletDisplay.getPortletInstanceSettings());

		return dlPortletInstanceSettings.getShowFolderMenu();
	}

	public boolean isShowTabs() throws PortalException, SystemException {
		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			new DLPortletInstanceSettings(
				_portletDisplay.getPortletInstanceSettings());

		return dlPortletInstanceSettings.getShowTabs();
	}

	public boolean isShowWhenSingleIconActionButton() {
		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return false;
	}

	public boolean isTrashEnabled() throws PortalException, SystemException {
		if (_trashEnabled == null) {
			_trashEnabled = TrashUtil.isTrashEnabled(_scopeGroupId);
		}

		return _trashEnabled;
	}

	public boolean isWebDAVEnabled() {
		return _portletDisplay.isWebDAVEnabled();
	}

	private Boolean _ieOnWin32;
	private PortletDisplay _portletDisplay;
	private HttpServletRequest _request;
	private long _scopeGroupId;
	private Boolean _trashEnabled;

}