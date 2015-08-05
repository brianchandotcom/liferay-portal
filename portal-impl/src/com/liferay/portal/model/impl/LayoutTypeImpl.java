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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.LayoutTypeAccessPolicy;
import com.liferay.portal.model.LayoutTypeController;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutTypeImpl implements LayoutType {

	public LayoutTypeImpl(
		Layout layout, LayoutTypeController layoutTypeController,
		LayoutTypeAccessPolicy layoutTypeAccessPolicy) {

		_layout = layout;
		_layoutTypeController = layoutTypeController;
		_layoutTypeAccessPolicy = layoutTypeAccessPolicy;
	}

	@Override
	public String[] getConfigurationActionDelete() {
		return _layoutTypeController.getConfigurationActionDelete();
	}

	@Override
	public String[] getConfigurationActionUpdate() {
		return _layoutTypeController.getConfigurationActionUpdate();
	}

	@Override
	public Layout getLayout() {
		return _layout;
	}

	@Override
	public LayoutTypeAccessPolicy getLayoutTypeAccessPolicy() {
		return _layoutTypeAccessPolicy;
	}

	@Override
	public LayoutTypeController getLayoutTypeController() {
		return _layoutTypeController;
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		return _layout.getTypeSettingsProperties();
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		return getTypeSettingsProperty(key, null);
	}

	@Override
	public String getTypeSettingsProperty(String key, String defaultValue) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key, defaultValue);
	}

	@Override
	public String getURL(Map<String, String> variables) {
		String url = _layoutTypeController.getURL();

		if (Validator.isNull(url)) {
			url = getDefaultURL();
		}

		return replaceVariables(url, variables);
	}

	@Override
	public boolean isBrowsable() {
		return _layoutTypeController.isBrowsable();
	}

	@Override
	public boolean isFirstPageable() {
		return _layoutTypeController.isFirstPageable();
	}

	@Override
	public boolean isParentable() {
		return _layoutTypeController.isParentable();
	}

	@Override
	public boolean isSitemapable() {
		return _layoutTypeController.isSitemapable();
	}

	@Override
	public boolean isURLFriendliable() {
		return _layoutTypeController.isURLFriendliable();
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void setLayout(Layout layout) {
	}

	@Override
	public void setTypeSettingsProperty(String key, String value) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		typeSettingsProperties.setProperty(key, value);
	}

	protected String getDefaultURL() {
		return _URL;
	}

	protected String replaceVariables(
		String url, Map<String, String> variables) {

		return StringUtil.replace(
			url, StringPool.DOLLAR_AND_OPEN_CURLY_BRACE,
			StringPool.CLOSE_CURLY_BRACE, variables);
	}

	private static final String _URL =
		"${liferay:mainPath}/portal/layout?p_l_id=${liferay:plid}&" +
			"p_v_l_s_g_id=${liferay:pvlsgid}";

	private final Layout _layout;
	private final LayoutTypeAccessPolicy _layoutTypeAccessPolicy;
	private final LayoutTypeController _layoutTypeController;

}