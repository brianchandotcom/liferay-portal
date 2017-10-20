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

package com.liferay.layouts.admin.kernel.model;

import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

/**
 * @author Michael Bowerman
 */
public class LayoutTypePortletConstants
	extends com.liferay.portal.kernel.model.LayoutTypePortletConstants {

	public static final String LINK_TO_LAYOUT_ID = "linkToLayoutId";

	public static boolean isLayoutTemplateColumnName(String typeSettingId) {
		return !_typeSettingsIds.contains(typeSettingId);
	}

	private static final Set<String> _typeSettingsIds = SetUtil.fromArray(
		new String[] {
			DEFAULT_ASSET_PUBLISHER_PORTLET_ID, LAYOUT_TEMPLATE_ID,
			LINK_TO_LAYOUT_ID, MODE_ABOUT, MODE_CONFIG, MODE_EDIT,
			MODE_EDIT_DEFAULTS, MODE_EDIT_GUEST, MODE_HELP, MODE_PREVIEW,
			MODE_PRINT, NESTED_COLUMN_IDS, STATE_MAX, STATE_MIN,
			STATIC_PORTLET_ORGANIZATION_SELECTOR,
			STATIC_PORTLET_REGULAR_SITE_SELECTOR, STATIC_PORTLET_USER_SELECTOR
		});

}