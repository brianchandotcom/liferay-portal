/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0.util;

/**
 *
 * @author Miguel Pastor
 */
public class UserGroupTemplateInfo {

	public String getColorSchemeId() {
		return _colorSchemeId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getCss() {
		return _css;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getLayoutSetId() {
		return _layoutSetId;
	}

	public long getLayoutSetPrototypeId() {
		return _layoutSetPrototypeId;
	}

	public short getLogo() {
		return _logo;
	}

	public long getLogoId() {
		return _logoId;
	}

	public long getPageCount() {
		return _pageCount;
	}

	public String getSettings() {
		return _settings;
	}

	public long getUserGroupId() {
		return _userGroupId;
	}

	public String getThemeId() {
		return _themeId;
	}

	public String getWapThemeId() {
		return _wapThemeId;
	}

	public String getWapColorSchemeId() {
		return _wapColorSchemeId;
	}

	public void setColorSchemeId(String colorSchemeId) {
		_colorSchemeId = colorSchemeId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCss(String css) {
		_css = css;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setLayoutSetPrototypeId(long layoutSetPrototypeId) {
		_layoutSetPrototypeId = layoutSetPrototypeId;
	}

	public void setLogo(short logo) {
		_logo = logo;
	}

	public void setLogoId(long logoId) {
		_logoId = logoId;
	}

	public void setPageCount(long pageCount) {
		_pageCount = pageCount;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public void setSettings(String settings) {
		_settings = settings;
	}

	public void setUserGroupId(long userGroupId) {
		_userGroupId = userGroupId;
	}

	public void setLayoutSetId(long layoutSetId) {
		_layoutSetId = layoutSetId;
	}

	public void setThemeId(String themeId) {
		_themeId = themeId;
	}

	public void setWapThemeId(String wapThemeId) {
		_wapThemeId = wapThemeId;
	}

	public void setWapColorSchemeId(String wapColorSchemeId) {
		_wapColorSchemeId = wapColorSchemeId;
	}

	private String _colorSchemeId;
	private long _companyId;
	private String _css;
	private long _groupId;
	private long _layoutSetId;
	private long _layoutSetPrototypeId;
	private short _logo;
	private long _logoId;
	private long _pageCount;
	private boolean _privateLayout;
	private String _settings;
	private long _userGroupId;
	private String _themeId;
	private String _wapThemeId;
	private String _wapColorSchemeId;

}