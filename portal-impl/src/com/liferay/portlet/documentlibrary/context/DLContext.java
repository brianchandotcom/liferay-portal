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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.trash.util.TrashUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class DLContext {

	public DLContext(HttpServletRequest request) {
		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_portletDisplay = themeDisplay.getPortletDisplay();
		_scopeGroupId = themeDisplay.getScopeGroupId();
	}

	// view_file_entry.jsp

	public boolean isIEOnWin32() {
		if (_ieOnWin32 == null) {
			_ieOnWin32 = BrowserSnifferUtil.isIeOnWin32(_request);
		}

		return _ieOnWin32;
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