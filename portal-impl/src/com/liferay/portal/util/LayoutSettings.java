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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class LayoutSettings {

	public static void addLayoutSetting(String type) {
		_layoutSettingsMap.put(type, new LayoutSettings(type));
	}

	public static LayoutSettings getInstance(Layout layout) {
		return getInstance(layout.getType());
	}

	public static LayoutSettings getInstance(String type) {
		return _layoutSettingsMap.get(type);
	}

	public static Map<String, LayoutSettings> getLayoutSettingsMap() {
		return _layoutSettingsMap;
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
		long plid = GetterUtil.getLong(variables.get("liferay:plid"));

		String url = getDefaultURL();

		try {
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (hasViewPermission(layout)) {
				url = getURL();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return replaceVariables(url, variables);
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

	protected boolean hasViewPermission(Layout layout) {
		if (layout.isTypeControlPanel()) {
			return false;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			return false;
		}

		try {
			return LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.VIEW);
		}
		catch (Exception e) {
			_log.error(e);

			return false;
		}
	}

	protected String replaceVariables(
		String url, Map<String, String> variables) {

		return StringUtil.replace(
			url, StringPool.DOLLAR_AND_OPEN_CURLY_BRACE,
			StringPool.CLOSE_CURLY_BRACE, variables);
	}

	protected String getDefaultURL() {
		return _URL;
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
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutSettings.class);

	private static final String _URL =
		"${liferay:mainPath}/portal/layout?p_l_id=${liferay:plid}&" +
			"p_v_l_s_g_id=${liferay:pvlsgid}";

	private static Map<String, LayoutSettings> _layoutSettingsMap =
		new HashMap<String, LayoutSettings>();

	static {
		_layoutSettingsMap.put(
			LayoutConstants.TYPE_CONTROL_PANEL,
			new LayoutSettings(LayoutConstants.TYPE_CONTROL_PANEL));

		for (String type : PropsValues.LAYOUT_TYPES) {
			_layoutSettingsMap.put(type, new LayoutSettings(type));
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