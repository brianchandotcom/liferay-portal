/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.tags.constants.AssetTagsAdminPortletKeys;
import com.liferay.asset.util.AssetHelper;
import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSiteInitializerFDSNames;

import jakarta.portlet.PortletRequest;

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
			JSONUtil.put(JSONUtil.put(
				"href",
				_getControlPanelPortletURL(
					AssetCategoriesAdminPortletKeys.
						ASSET_CATEGORIES_ADMIN)
			).put(
				"label",
				LanguageUtil.get(
					_httpServletRequest,
					"export-import-vocabularies")
			)).put(JSONUtil.put(
				"href",
				_getControlPanelPortletURL(
					AssetTagsAdminPortletKeys.ASSET_TAGS_ADMIN)
			).put(
				"label",
				LanguageUtil.get(
					_httpServletRequest, "export-import-tags")
			))
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

	private String _getControlPanelPortletURL(String portletId)
		throws Exception {

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_httpServletRequest,
				_groupService.getGroup(_themeDisplay.getScopeGroupId()),
				ExportImportPortletKeys.EXPORT_IMPORT, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/export_import/export_import"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).setPortletResource(
			portletId
		).setParameter(
			"returnToFullPageURL", PortalUtil.getCurrentURL(_httpServletRequest)
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private final GroupService _groupService;
	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}