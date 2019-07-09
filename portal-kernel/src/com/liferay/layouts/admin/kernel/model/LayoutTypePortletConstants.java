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
 * @deprecated As of Mueller (7.2.x), replaced by {@link com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants}
 */
@Deprecated
public class LayoutTypePortletConstants
	extends com.liferay.portal.kernel.model.LayoutTypePortletConstants {

	public static final String ARTICLE_ID = "article-id";

	public static final String CUSTOMIZABLE_LAYOUT = "CUSTOMIZABLE_LAYOUT";

	public static final String CUSTOMIZABLE_SUFFIX = "-customizable";

	public static final String EMBEDDED_LAYOUT_URL = "embeddedLayoutURL";

	public static final String FULL_PAGE_APPLICATION_PORTLET =
		"fullPageApplicationPortlet";

	public static final String GROUP_ID = "groupId";

	public static final String JAVASCRIPT = "javascript";

	public static final String LAST_IMPORT_DATE = "last-import-date";

	public static final String LAST_IMPORT_LAYOUT_BRANCH_ID =
		"last-import-layout-branch-id";

	public static final String LAST_IMPORT_LAYOUT_BRANCH_NAME =
		"last-import-layout-branch-name";

	public static final String LAST_IMPORT_LAYOUT_REVISION_ID =
		"last-import-layout-revision-id";

	public static final String LAST_IMPORT_LAYOUT_SET_BRANCH_ID =
		"last-import-layout-set-branch-id";

	public static final String LAST_IMPORT_LAYOUT_SET_BRANCH_NAME =
		"last-import-layout-set-branch-name";

	public static final String LAST_IMPORT_USER_NAME = "last-import-user-name";

	public static final String LAST_IMPORT_USER_UUID = "last-import-user-uuid";

	public static final String LAST_MERGE_TIME = "last-merge-time";

	public static final String LAYOUT_UPDATEABLE = "layoutUpdateable";

	public static final String LINK_TO_LAYOUT_ID = "linkToLayoutId";

	public static final String MERGE_FAIL_COUNT = "merge-fail-count";

	public static final String MODIFIED_DATE = "modifiedDate";

	public static final String PANEL_LAYOUT_DESCRIPTION =
		"panelLayoutDescription";

	public static final String PANEL_SELECTED_PORTLETS =
		"panelSelectedPortlets";

	public static final String PRIVATE_LAYOUT = "privateLayout";

	public static final String PROPERTY_NAMESPACE = "lfr-theme:";

	public static final String QUERY_STRING = "query-string";

	public static final String SITEMAP_CHANGEFREQ = "sitemap-changefreq";

	public static final String SITEMAP_INCLUDE = "sitemap-include";

	public static final String SITEMAP_PRIORITY = "sitemap-priority";

	public static final String TARGET = "target";

	public static final String URL = "url";

	public static boolean hasPortletIds(String typeSettingId) {
		if (isLayoutTemplateColumnName(typeSettingId) ||
			DEFAULT_ASSET_PUBLISHER_PORTLET_ID.equals(typeSettingId) ||
			PANEL_SELECTED_PORTLETS.equals(typeSettingId)) {

			return true;
		}

		return false;
	}

	public static boolean isLayoutTemplateColumnName(String typeSettingId) {
		if (_typeSettingsIds.contains(typeSettingId) ||
			typeSettingId.startsWith(PROPERTY_NAMESPACE) ||
			typeSettingId.endsWith(CUSTOMIZABLE_SUFFIX)) {

			return false;
		}

		return true;
	}

	private static final Set<String> _typeSettingsIds = SetUtil.fromArray(
		new String[] {
			ARTICLE_ID, CUSTOMIZABLE_LAYOUT, DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
			EMBEDDED_LAYOUT_URL, FULL_PAGE_APPLICATION_PORTLET, GROUP_ID,
			JAVASCRIPT, LAST_IMPORT_DATE, LAST_IMPORT_LAYOUT_BRANCH_ID,
			LAST_IMPORT_LAYOUT_BRANCH_NAME, LAST_IMPORT_LAYOUT_REVISION_ID,
			LAST_IMPORT_LAYOUT_SET_BRANCH_ID,
			LAST_IMPORT_LAYOUT_SET_BRANCH_NAME, LAST_IMPORT_USER_NAME,
			LAST_IMPORT_USER_UUID, LAST_MERGE_TIME, LAYOUT_TEMPLATE_ID,
			LAYOUT_UPDATEABLE, LINK_TO_LAYOUT_ID, MERGE_FAIL_COUNT, MODE_ABOUT,
			MODE_CONFIG, MODE_EDIT, MODE_EDIT_DEFAULTS, MODE_EDIT_GUEST,
			MODE_HELP, MODE_PREVIEW, MODE_PRINT, MODIFIED_DATE,
			NESTED_COLUMN_IDS, PANEL_LAYOUT_DESCRIPTION,
			PANEL_SELECTED_PORTLETS, PRIVATE_LAYOUT, QUERY_STRING,
			SITEMAP_CHANGEFREQ, SITEMAP_INCLUDE, SITEMAP_PRIORITY, STATE_MAX,
			STATE_MIN, STATIC_PORTLET_ORGANIZATION_SELECTOR,
			STATIC_PORTLET_REGULAR_SITE_SELECTOR, STATIC_PORTLET_USER_SELECTOR,
			TARGET, URL
		});

}