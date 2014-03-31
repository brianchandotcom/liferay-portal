/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Shuyang Zhou
 */
public class ThemeDisplayModel {

	public ThemeDisplayModel(ThemeDisplay themeDisplay) {
		_cdnHost = themeDisplay.getCDNHost();
		_companyId = themeDisplay.getCompanyId();
		_doAsUserId = themeDisplay.getDoAsUserId();
		_i18nLanguageId = themeDisplay.getI18nLanguageId();
		_i18nPath = themeDisplay.getI18nPath();
		_languageId = themeDisplay.getLanguageId();
		_locale = themeDisplay.getLocale();
		_pathContext = themeDisplay.getPathContext();
		_pathFriendlyURLPrivateGroup =
			themeDisplay.getPathFriendlyURLPrivateGroup();
		_pathFriendlyURLPrivateUser =
			themeDisplay.getPathFriendlyURLPrivateUser();
		_pathFriendlyURLPublic = themeDisplay.getPathFriendlyURLPublic();
		_pathImage = themeDisplay.getPathImage();
		_pathMain = themeDisplay.getPathMain();
		_pathThemeImages = themeDisplay.getPathThemeImages();
		_plid = themeDisplay.getPlid();
		_portalURL = HttpUtil.removeProtocol(themeDisplay.getPortalURL());
		_realUserId = themeDisplay.getRealUserId();
		_scopeGroupId = themeDisplay.getScopeGroupId();
		_secure = themeDisplay.isSecure();
		_serverName = themeDisplay.getServerName();
		_serverPort = themeDisplay.getServerPort();
		_timeZone = themeDisplay.getTimeZone();
		_urlPortal = HttpUtil.removeProtocol(themeDisplay.getURLPortal());
		_userId = themeDisplay.getUserId();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (portletDisplay != null) {
			_portletDisplayModel = new PortletDisplayModel(portletDisplay);
		}
	}

	public String getCdnHost() {
		return _cdnHost;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getDoAsUserId() {
		return _doAsUserId;
	}

	public String getI18nLanguageId() {
		return _i18nLanguageId;
	}

	public String getI18nPath() {
		return _i18nPath;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public Locale getLocale() {
		return _locale;
	}

	public String getPathContext() {
		return _pathContext;
	}

	public String getPathFriendlyURLPrivateGroup() {
		return _pathFriendlyURLPrivateGroup;
	}

	public String getPathFriendlyURLPrivateUser() {
		return _pathFriendlyURLPrivateUser;
	}

	public String getPathFriendlyURLPublic() {
		return _pathFriendlyURLPublic;
	}

	public String getPathImage() {
		return _pathImage;
	}

	public String getPathMain() {
		return _pathMain;
	}

	public String getPathThemeImages() {
		return _pathThemeImages;
	}

	public long getPlid() {
		return _plid;
	}

	public String getPortalURL() {
		return _portalURL;
	}

	public PortletDisplayModel getPortletDisplayModel() {
		return _portletDisplayModel;
	}

	public long getRealUserId() {
		return _realUserId;
	}

	public long getScopeGroupId() {
		return _scopeGroupId;
	}

	public String getServerName() {
		return _serverName;
	}

	public int getServerPort() {
		return _serverPort;
	}

	public TimeZone getTimeZone() {
		return _timeZone;
	}

	public String getURLPortal() {
		return _urlPortal;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isSecure() {
		return _secure;
	}

	private String _cdnHost;
	private long _companyId;
	private String _doAsUserId;
	private String _i18nLanguageId;
	private String _i18nPath;
	private String _languageId;
	private Locale _locale;
	private String _pathContext;
	private String _pathFriendlyURLPrivateGroup;
	private String _pathFriendlyURLPrivateUser;
	private String _pathFriendlyURLPublic;
	private String _pathImage;
	private String _pathMain;
	private String _pathThemeImages;
	private long _plid;
	private String _portalURL;
	private PortletDisplayModel _portletDisplayModel;
	private long _realUserId;
	private long _scopeGroupId;
	private boolean _secure;
	private String _serverName;
	private int _serverPort;
	private TimeZone _timeZone;
	private String _urlPortal;
	private long _userId;

}