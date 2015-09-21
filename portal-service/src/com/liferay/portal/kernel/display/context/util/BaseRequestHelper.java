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

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public abstract class BaseRequestHelper {

	public BaseRequestHelper(HttpServletRequest request) {
		_request = request;
	}

	public Company getCompany() {
		if (_company == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_company = themeDisplay.getCompany();
		}

		return _company;
	}

	public long getCompanyId() {
		if (_companyId == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_companyId = themeDisplay.getCompanyId();
		}

		return _companyId;
	}

	public String getCurrentURL() {
		if (_currentURL == null) {
			PortletURL portletURL = PortletURLUtil.getCurrent(
				getLiferayPortletRequest(), getLiferayPortletResponse());

			_currentURL = portletURL.toString();
		}

		return _currentURL;
	}

	public Layout getLayout() {
		if (_layout == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_layout = themeDisplay.getLayout();
		}

		return _layout;
	}

	public LiferayPortletRequest getLiferayPortletRequest() {
		if (_liferayPortletRequest == null) {
			PortletRequest portletRequest =
				(PortletRequest)_request.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			_liferayPortletRequest = PortalUtil.getLiferayPortletRequest(
				portletRequest);
		}

		return _liferayPortletRequest;
	}

	public LiferayPortletResponse getLiferayPortletResponse() {
		if (_liferayPortletResponse == null) {
			PortletResponse portletResponse =
				(PortletResponse)_request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
				portletResponse);
		}

		return _liferayPortletResponse;
	}

	public Locale getLocale() {
		if (_locale == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_locale = themeDisplay.getLocale();
		}

		return _locale;
	}

	public PermissionChecker getPermissionChecker() {
		if (_permissionChecker == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_permissionChecker = themeDisplay.getPermissionChecker();
		}

		return _permissionChecker;
	}

	public PortletDisplay getPortletDisplay() {
		if (_portletDisplay == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_portletDisplay = themeDisplay.getPortletDisplay();
		}

		return _portletDisplay;
	}

	public String getPortletId() {
		if (_portletId == null) {
			PortletDisplay portletDisplay = getPortletDisplay();

			_portletId = portletDisplay.getId();
		}

		return _portletId;
	}

	public String getPortletName() {
		if (_portletName == null) {
			PortletDisplay portletDisplay = getPortletDisplay();

			_portletName = portletDisplay.getPortletName();
		}

		return _portletName;
	}

	public String getPortletResource() {
		if (_portletResource == null) {
			PortletDisplay portletDisplay = getPortletDisplay();

			_portletResource = portletDisplay.getPortletResource();
		}

		return _portletResource;
	}

	public String getPortletTitle() {
		if (_portletTitle == null) {
			PortletDisplay portletDisplay = getPortletDisplay();

			_portletTitle = portletDisplay.getTitle();
		}

		return _portletTitle;
	}

	public HttpServletRequest getRequest() {
		return _request;
	}

	public String getResourcePortletId() {
		if (_resourcePortletId == null) {
			String portletResource = getPortletResource();

			if (Validator.isNotNull(portletResource)) {
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
			String portletResource = getPortletResource();

			if (Validator.isNotNull(portletResource)) {
				_resourcePortletName = portletResource;
			}
			else {
				_resourcePortletName = getPortletName();
			}
		}

		return _resourcePortletName;
	}

	public long getScopeGroupId() {
		if (_scopeGroupId == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_scopeGroupId = themeDisplay.getScopeGroupId();
		}

		return _scopeGroupId;
	}

	public long getSiteGroupId() {
		if (_siteGroupId == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_siteGroupId = themeDisplay.getSiteGroupId();
		}

		return _siteGroupId;
	}

	public ThemeDisplay getThemeDisplay() {
		if (_themeDisplay == null) {
			_themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);
		}

		return _themeDisplay;
	}

	public User getUser() {
		if (_user == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_user = themeDisplay.getUser();
		}

		return _user;
	}

	public long getUserId() {
		if (_user == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_user = themeDisplay.getUser();
		}

		return _user.getUserId();
	}

	private Company _company;
	private Long _companyId;
	private String _currentURL;
	private Layout _layout;
	private LiferayPortletRequest _liferayPortletRequest;
	private LiferayPortletResponse _liferayPortletResponse;
	private Locale _locale;
	private PermissionChecker _permissionChecker;
	private PortletDisplay _portletDisplay;
	private String _portletId;
	private String _portletName;
	private String _portletResource;
	private String _portletTitle;
	private final HttpServletRequest _request;
	private String _resourcePortletId;
	private String _resourcePortletName;
	private Long _scopeGroupId;
	private Long _siteGroupId;
	private ThemeDisplay _themeDisplay;
	private User _user;

}