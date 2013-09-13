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

package com.liferay.portal.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Layout;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class LayoutSettings {

	public static LayoutSettings getInstance(Layout layout) {
		return getInstance(layout.getType());
	}

	public static LayoutSettings getInstance(String type) {
		return _layoutSettingsMap.get(type);
	}

	public static Map<String, LayoutSettings> getCurrentLayoutSettings(){
		return _layoutSettingsMap;
	}

	public static void setLayoutSetting(String type) {
		new LayoutSettings(type);
	}

	public String[] getConfigurationActionDelete() {
		return _configurationActionDelete;
	}

	public String[] getConfigurationActionUpdate() {
		return _configurationActionUpdate;
	}

	public String getEditPage() {
		return _editPage;
	}

	public String getType() {
		return _type;
	}

	public String getURL() {
		return _url;
	}

	public String getURL(Map<String, String> variables) {
		return StringUtil.replace(
			_url, StringPool.DOLLAR_AND_OPEN_CURLY_BRACE,
			StringPool.CLOSE_CURLY_BRACE, variables);
	}

	public String getViewPage() {
		return _viewPage;
	}

	public boolean isFirstPageable() {
		return _firstPageable;
	}

	public boolean isParentable() {
		return _parentable;
	}

	public boolean isSitemapable() {
		return _sitemapable;
	}

	public boolean isURLFriendliable() {
		return _urlFriendliable;
	}

	private LayoutSettings(String type) {
		_type = type;

		Filter filter = new Filter(type);

		_configurationActionDelete = StringUtil.split(
			GetterUtil.getString(
				PropsUtil.get(
					PropsKeys.LAYOUT_CONFIGURATION_ACTION_DELETE, filter)));
		_configurationActionUpdate = StringUtil.split(
			GetterUtil.getString(
				PropsUtil.get(
					PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE, filter)));
		_editPage = GetterUtil.getString(
			PropsUtil.get(PropsKeys.LAYOUT_EDIT_PAGE, filter));
		_firstPageable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_FIRST_PAGEABLE, filter));
		_parentable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_PARENTABLE, filter), true);
		_sitemapable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_SITEMAPABLE, filter), true);
		_url = GetterUtil.getString(
			PropsUtil.get(PropsKeys.LAYOUT_URL, filter));
		_urlFriendliable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_URL_FRIENDLIABLE, filter), true);
		_viewPage = GetterUtil.getString(
			PropsUtil.get(PropsKeys.LAYOUT_VIEW_PAGE, filter));

		_layoutSettingsMap.put(type, this);
	}

	private static Map<String, LayoutSettings> _layoutSettingsMap =
		new HashMap<String, LayoutSettings>();

	static {
		new LayoutSettings("control_panel");

		for (String type : PropsValues.LAYOUT_TYPES) {
			new LayoutSettings(type);
		}
	}

	private String[] _configurationActionDelete;
	private String[] _configurationActionUpdate;
	private String _editPage;
	private boolean _firstPageable;
	private boolean _parentable;
	private boolean _sitemapable;
	private String _type;
	private String _url;
	private boolean _urlFriendliable;
	private String _viewPage;

}