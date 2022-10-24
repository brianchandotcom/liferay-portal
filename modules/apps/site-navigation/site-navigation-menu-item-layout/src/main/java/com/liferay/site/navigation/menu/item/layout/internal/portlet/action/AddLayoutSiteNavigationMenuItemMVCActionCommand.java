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

package com.liferay.site.navigation.menu.item.layout.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.exception.SiteNavigationMenuItemNameException;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
		"mvc.command.name=/navigation_menu/add_layout_site_navigation_menu_item"
	},
	service = MVCActionCommand.class
)
public class AddLayoutSiteNavigationMenuItemMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long siteNavigationMenuId = ParamUtil.getLong(
			actionRequest, "siteNavigationMenuId");
		String siteNavigationMenuItemType = ParamUtil.getString(
			actionRequest, "siteNavigationMenuItemType");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		Map<Long, SiteNavigationMenuItem> layoutSiteNavigationMenuItemMap =
			new HashMap<>();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
				ParamUtil.getString(actionRequest, "items"));

			Iterator<JSONObject> iterator = jsonArray.iterator();

			while (iterator.hasNext()) {
				JSONObject itemJSONObject = iterator.next();

				String layoutUuid = itemJSONObject.getString("id");
				long groupId = itemJSONObject.getLong("groupId");
				boolean privateLayout = itemJSONObject.getBoolean(
					"privateLayout");

				Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
					layoutUuid, groupId, privateLayout);

				if (layout == null) {
					continue;
				}

				SiteNavigationMenuItem siteNavigationMenuItem =
					_siteNavigationMenuItemService.addSiteNavigationMenuItem(
						themeDisplay.getScopeGroupId(), siteNavigationMenuId, 0,
						siteNavigationMenuItemType,
						UnicodePropertiesBuilder.create(
							true
						).put(
							"groupId", String.valueOf(groupId)
						).put(
							"layoutUuid", layoutUuid
						).put(
							"privateLayout", String.valueOf(privateLayout)
						).put(
							"title", layout.getName(themeDisplay.getLocale())
						).buildString(),
						serviceContext);

				layoutSiteNavigationMenuItemMap.put(
					layout.getPlid(), siteNavigationMenuItem);
			}

			for (Map.Entry<Long, SiteNavigationMenuItem> entry :
					layoutSiteNavigationMenuItemMap.entrySet()) {

				Layout layout = _layoutLocalService.fetchLayout(entry.getKey());

				if (layout.getParentPlid() <= 0) {
					continue;
				}

				SiteNavigationMenuItem parentSiteNavigationMenuItem =
					layoutSiteNavigationMenuItemMap.get(layout.getParentPlid());

				if (parentSiteNavigationMenuItem == null) {
					continue;
				}

				SiteNavigationMenuItem siteNavigationMenuItem =
					entry.getValue();

				_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
					siteNavigationMenuItem.getSiteNavigationMenuItemId(),
					parentSiteNavigationMenuItem.getSiteNavigationMenuItemId(),
					layout.getPriority());
			}

			if (MapUtil.isEmpty(layoutSiteNavigationMenuItemMap)) {
				jsonObject.put(
					"errorMessage",
					_language.get(
						_portal.getHttpServletRequest(actionRequest),
						"please-choose-at-least-one-page"));
			}
			else {
				jsonObject.put(
					"siteNavigationMenuItemId",
					layoutSiteNavigationMenuItemMap);
			}
		}
		catch (SiteNavigationMenuItemNameException
					siteNavigationMenuItemNameException) {

			if (_log.isDebugEnabled()) {
				_log.debug(siteNavigationMenuItemNameException);
			}

			jsonObject.put(
				"errorMessage",
				_language.get(
					_portal.getHttpServletRequest(actionRequest),
					"an-unexpected-error-occurred"));
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddLayoutSiteNavigationMenuItemMVCActionCommand.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

}