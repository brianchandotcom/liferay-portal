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

package com.liferay.portal.kernel.display.context.util;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public abstract class BaseRequestHelper {

	public BaseRequestHelper(HttpServletRequest request) {
		_request = request;
	}

	public long getCompanyId() {
		if (_companyId == null) {
			_companyId = getThemeDisplay().getCompanyId();
		}

		return _companyId;
	}

	public Layout getLayout() {
		if (_layout == null) {
			_layout = getThemeDisplay().getLayout();
		}

		return _layout;
	}

	public Locale getLocale() {
		if (_locale == null) {
			_locale = getThemeDisplay().getLocale();
		}

		return _locale;
	}

	public PermissionChecker getPermissionChecker() {
		if (_permissionChecker == null) {
			_permissionChecker = getThemeDisplay().getPermissionChecker();
		}

		return _permissionChecker;
	}

	public PortletDisplay getPortletDisplay() {
		if (_portletDisplay == null) {
			_portletDisplay = getThemeDisplay().getPortletDisplay();
		}

		return _portletDisplay;
	}

	public String getPortletId() {
		if (_portletId == null) {
			_portletId = getPortletDisplay().getId();
		}

		return _portletId;
	}

	public String getPortletName() {
		if (_portletName == null) {
			_portletName = getPortletDisplay().getPortletName();
		}

		return _portletName;
	}

	public String getPortletResource() {
		if (_portletResource == null) {
			_portletResource = getPortletDisplay().getPortletResource();
		}

		return _portletResource;
	}

	public HttpServletRequest getRequest() {
		return _request;
	}

	public String getResourcePortletId() {
		if (_resourcePortletId == null) {
			if (getPortletId().equals(PortletKeys.PORTLET_CONFIGURATION)) {
				_resourcePortletId = getPortletResource();
			}
			else {
				_resourcePortletId = getPortletId();
			}
		}

		return _resourcePortletId;
	}

	public String getResourcePortletName() {
		if (_resourcePortletName == null) {
			if (getPortletId().equals(PortletKeys.PORTLET_CONFIGURATION)) {
				_resourcePortletName = getPortletResource();
			}
			else {
				_resourcePortletName = getPortletName();
			}
		}

		return _resourcePortletName;
	}

	public long getScopeGroupId() {
		if (_scopeGroupId == null) {
			_scopeGroupId = getThemeDisplay().getScopeGroupId();
		}

		return _scopeGroupId;
	}

	public ThemeDisplay getThemeDisplay() {
		if (_themeDisplay == null) {
			_themeDisplay = (ThemeDisplay) _request.getAttribute(
				WebKeys.THEME_DISPLAY);
		}

		return _themeDisplay;
	}

	private Long _companyId;
	private Layout _layout;
	private Locale _locale;
	private PermissionChecker _permissionChecker;
	private PortletDisplay _portletDisplay;
	private String _portletId;
	private String _portletName;
	private String _portletResource;
	private final HttpServletRequest _request;
	private String _resourcePortletId;
	private String _resourcePortletName;
	private Long _scopeGroupId;
	private ThemeDisplay _themeDisplay;

}