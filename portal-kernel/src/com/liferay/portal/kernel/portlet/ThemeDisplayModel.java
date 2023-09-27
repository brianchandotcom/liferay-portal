/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;

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
		_portalURL = HttpComponentsUtil.removeProtocol(
			themeDisplay.getPortalURL());
		_realUserId = themeDisplay.getRealUserId();
		_scopeGroupId = themeDisplay.getScopeGroupId();
		_secure = themeDisplay.isSecure();
		_serverName = themeDisplay.getServerName();
		_serverPort = themeDisplay.getServerPort();
		_timeZone = themeDisplay.getTimeZone();
		_urlPortal = HttpComponentsUtil.removeProtocol(
			themeDisplay.getURLPortal());
		_userId = themeDisplay.getUserId();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (portletDisplay != null) {
			_portletDisplayModel = new PortletDisplayModel(portletDisplay);
		}
		else {
			_portletDisplayModel = null;
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

	private final String _cdnHost;
	private final long _companyId;
	private final String _doAsUserId;
	private final String _i18nLanguageId;
	private final String _i18nPath;
	private final String _languageId;
	private final Locale _locale;
	private final String _pathContext;
	private final String _pathFriendlyURLPrivateGroup;
	private final String _pathFriendlyURLPrivateUser;
	private final String _pathFriendlyURLPublic;
	private final String _pathImage;
	private final String _pathMain;
	private final String _pathThemeImages;
	private final long _plid;
	private final String _portalURL;
	private final PortletDisplayModel _portletDisplayModel;
	private final long _realUserId;
	private final long _scopeGroupId;
	private final boolean _secure;
	private final String _serverName;
	private final int _serverPort;
	private final TimeZone _timeZone;
	private final String _urlPortal;
	private final long _userId;

}