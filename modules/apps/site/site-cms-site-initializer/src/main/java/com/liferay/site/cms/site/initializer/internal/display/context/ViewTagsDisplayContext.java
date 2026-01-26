/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.tags.constants.AssetTagsAdminPortletKeys;
import com.liferay.asset.util.AssetHelper;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSiteInitializerFDSNames;
import com.liferay.site.cms.site.initializer.internal.util.ExportImportUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Noor Najjar
 */
public class ViewTagsDisplayContext {

	public ViewTagsDisplayContext(
		GroupService groupService, HttpServletRequest httpServletRequest,
		ThemeDisplay themeDisplay) {

		_groupService = groupService;
		_httpServletRequest = httpServletRequest;
		_themeDisplay = themeDisplay;
	}

	public Map<String, Object> getReactData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"actionItems",
			JSONUtil.putAll(
				ExportImportUtil.getActionItemJSONObject(
					_httpServletRequest, "export-import-vocabularies",
					AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN,
					_themeDisplay),
				ExportImportUtil.getActionItemJSONObject(
					_httpServletRequest, "export-import-tags",
					AssetTagsAdminPortletKeys.ASSET_TAGS_ADMIN, _themeDisplay))
		).put(
			"cmsGroupId", _themeDisplay.getScopeGroupId()
		).put(
			"dataSetId", CMSSiteInitializerFDSNames.CATEGORIZATION_TAGS
		).put(
			"invalidTagCharacters",
			String.valueOf(AssetHelper.INVALID_CHARACTERS)
		).put(
			"tagsURL",
			PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayoutByFriendlyURL(
					_themeDisplay.getScopeGroupId(), false,
					"/categorization/view-tags"),
				_themeDisplay)
		).put(
			"tagUsagesURL",
			PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayoutByFriendlyURL(
					_themeDisplay.getScopeGroupId(), false,
					"/categorization/view-tag-usages"),
				_themeDisplay)
		).put(
			"vocabulariesURL",
			PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayoutByFriendlyURL(
					_themeDisplay.getScopeGroupId(), false,
					"/categorization/view-vocabularies"),
				_themeDisplay)
		).build();
	}

	private final GroupService _groupService;
	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}