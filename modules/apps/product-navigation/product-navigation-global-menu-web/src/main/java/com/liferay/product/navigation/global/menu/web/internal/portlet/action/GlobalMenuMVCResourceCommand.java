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

package com.liferay.product.navigation.global.menu.web.internal.portlet.action;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.global.menu.web.internal.constants.ProductNavigationGlobalMenuPortletKeys;
import com.liferay.site.util.GroupURLProvider;
import com.liferay.site.util.RecentGroupManager;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ProductNavigationGlobalMenuPortletKeys.PRODUCT_NAVIGATION_GLOBAL_MENU,
		"mvc.command.name=/global_menu/panel_apps"
	},
	service = MVCResourceCommand.class
)
public class GlobalMenuMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			_getGlobalMenuContextJSONObject(resourceRequest));
	}

	private JSONArray _getChildPanelCategoriesJSONArray(
			HttpServletRequest httpServletRequest, String key,
			ThemeDisplay themeDisplay)
		throws PortalException {

		JSONArray childPanelCategoriesJSONArray =
			JSONFactoryUtil.createJSONArray();

		List<PanelCategory> childPanelCategories =
			_panelCategoryRegistry.getChildPanelCategories(
				key, themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup());

		for (PanelCategory childPanelCategory : childPanelCategories) {
			JSONArray panelAppsJSONArray = _getPanelAppsJSONArray(
				httpServletRequest, childPanelCategory.getKey(), themeDisplay);

			if ((panelAppsJSONArray == null) ||
				(panelAppsJSONArray.length() <= 0)) {

				continue;
			}

			childPanelCategoriesJSONArray.put(
				JSONUtil.put(
					"key", childPanelCategory.getKey()
				).put(
					"label",
					childPanelCategory.getLabel(themeDisplay.getLocale())
				).put(
					"panelApps", panelAppsJSONArray
				));
		}

		return childPanelCategoriesJSONArray;
	}

	private JSONObject _getGlobalMenuContextJSONObject(
			ResourceRequest resourceRequest)
		throws PortalException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return JSONUtil.put(
			"items",
			_getPanelCategoriesJSONArray(httpServletRequest, themeDisplay)
		).put(
			"recentSites",
			_getRecentSitesJSONArray(
				httpServletRequest, resourceRequest, themeDisplay)
		);
	}

	private JSONArray _getPanelAppsJSONArray(
			HttpServletRequest httpServletRequest, String key,
			ThemeDisplay themeDisplay)
		throws PortalException {

		JSONArray panelAppsJSONArray = JSONFactoryUtil.createJSONArray();

		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(key);

		for (PanelApp panelApp : panelApps) {
			Portlet portlet = _portletLocalService.getPortletById(
				themeDisplay.getCompanyId(), panelApp.getPortletId());

			panelAppsJSONArray.put(
				JSONUtil.put(
					"label",
					_portal.getPortletTitle(portlet, themeDisplay.getLocale())
				).put(
					"portletId", panelApp.getPortletId()
				).put(
					"url", panelApp.getPortletURL(httpServletRequest)
				));
		}

		return panelAppsJSONArray;
	}

	private JSONArray _getPanelCategoriesJSONArray(
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay)
		throws PortalException {

		JSONArray panelCategoriesJSONArray = JSONFactoryUtil.createJSONArray();

		List<PanelCategory> globalMenuPanelCategories =
			_panelCategoryRegistry.getChildPanelCategories(
				PanelCategoryKeys.GLOBAL_MENU,
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup());

		for (PanelCategory panelCategory : globalMenuPanelCategories) {
			JSONArray childPanelCategoriesJSONArray =
				_getChildPanelCategoriesJSONArray(
					httpServletRequest, panelCategory.getKey(), themeDisplay);

			if ((childPanelCategoriesJSONArray == null) ||
				(childPanelCategoriesJSONArray.length() <= 0)) {

				continue;
			}

			panelCategoriesJSONArray.put(
				JSONUtil.put(
					"childCategories", childPanelCategoriesJSONArray
				).put(
					"key", panelCategory.getKey()
				).put(
					"label", panelCategory.getLabel(themeDisplay.getLocale())
				));
		}

		return panelCategoriesJSONArray;
	}

	private JSONArray _getRecentSitesJSONArray(
		HttpServletRequest httpServletRequest, ResourceRequest resourceRequest,
		ThemeDisplay themeDisplay) {

		JSONArray recentSitesJSONArray = JSONFactoryUtil.createJSONArray();

		List<Group> recentSites = _recentGroupManager.getRecentGroups(
			httpServletRequest);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(), getClass());

		for (Group group : recentSites) {
			recentSitesJSONArray.put(
				JSONUtil.put(
					"key", group.getGroupKey()
				).put(
					"label",
					LanguageUtil.get(
						resourceBundle, group.getScopeLabel(themeDisplay))
				).put(
					"logoURL", group.getLogoURL(themeDisplay, true)
				).put(
					"url", _groupURLProvider.getGroupURL(group, resourceRequest)
				));
		}

		return recentSitesJSONArray;
	}

	@Reference
	private GroupURLProvider _groupURLProvider;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private RecentGroupManager _recentGroupManager;

}