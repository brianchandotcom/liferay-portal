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

package com.liferay.announcements.web.display.context.util;

import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class AnnouncementsRequestHelper extends BaseRequestHelper {

	public AnnouncementsRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public PortletPreferences getPortletPreferences() {
		if (_portletPreferences != null) {
			return _portletPreferences;
		}

		HttpServletRequest request = getRequest();

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		_portletPreferences = portletRequest.getPreferences();

		return _portletPreferences;
	}

	public Group getScopeGroup() {
		if (_scopeGroup != null) {
			return _scopeGroup;
		}

		ThemeDisplay themeDisplay = getThemeDisplay();

		_scopeGroup = themeDisplay.getScopeGroup();

		return _scopeGroup;
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(getRequest(), "tabs1", "entries");

		return _tabs1;
	}

	private PortletPreferences _portletPreferences;
	private Group _scopeGroup;
	private String _tabs1;

}