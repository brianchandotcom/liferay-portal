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

package com.liferay.portlet.documentlibrary.context;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
public class DLActionsDisplayContext {

	public DLActionsDisplayContext(
		HttpServletRequest request,
		DLPortletInstanceSettings dlPortletInstanceSettings) {

		_request = request;
		_dlPortletInstanceSettings = dlPortletInstanceSettings;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_portletDisplay = themeDisplay.getPortletDisplay();
	}

	public boolean isAddFolderButtonVisible() {
		String portletName = _portletDisplay.getPortletName();

		if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return false;
	}

	public boolean isFolderMenuVisible() {
		String portletName = _portletDisplay.getPortletName();

		if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return _dlPortletInstanceSettings.isShowFolderMenu();
	}

	public boolean isShowActions() {
		String portletName = _portletDisplay.getPortletName();
		String portletResource = _portletDisplay.getPortletResource();

		if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN) ||
			portletResource.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletResource.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return _dlPortletInstanceSettings.isShowActions();
	}

	public boolean isShowMinimalActionsButton() {
		String portletName = _portletDisplay.getPortletName();

		if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return ParamUtil.getBoolean(_request, "showMinimalActionButtons");
	}

	public boolean isShowTabs() {
		String portletName = _portletDisplay.getPortletName();

		if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return _dlPortletInstanceSettings.isShowTabs();
	}

	public boolean isShowWhenSingleIconActionButton() {
		String portletName = _portletDisplay.getPortletName();

		if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return false;
	}

	private final DLPortletInstanceSettings _dlPortletInstanceSettings;
	private final PortletDisplay _portletDisplay;
	private final HttpServletRequest _request;

}