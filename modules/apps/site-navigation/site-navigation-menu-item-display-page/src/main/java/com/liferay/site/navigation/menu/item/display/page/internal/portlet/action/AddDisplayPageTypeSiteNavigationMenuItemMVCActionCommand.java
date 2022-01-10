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

package com.liferay.site.navigation.menu.item.display.page.internal.portlet.action;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.exception.SiteNavigationMenuItemNameException;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
		"mvc.command.name=/navigation_menu/add_display_page_type_site_navigation_menu_item"
	},
	service = MVCActionCommand.class
)
public class AddDisplayPageTypeSiteNavigationMenuItemMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		long siteNavigationMenuId = ParamUtil.getLong(
			actionRequest, "siteNavigationMenuId");
		String siteNavigationMenuItemType = ParamUtil.getString(
			actionRequest, "siteNavigationMenuItemType");

		if ((classNameId > 0) && (classPK > 0) && (siteNavigationMenuId > 0) &&
			Validator.isNotNull(siteNavigationMenuItemType)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			try {
				_siteNavigationMenuItemService.addSiteNavigationMenuItem(
					themeDisplay.getScopeGroupId(), siteNavigationMenuId, 0,
					siteNavigationMenuItemType,
					UnicodePropertiesBuilder.create(
						true
					).put(
						"className", siteNavigationMenuItemType
					).put(
						"classNameId", String.valueOf(classNameId)
					).put(
						"classPK", String.valueOf(classPK)
					).put(
						"classTypeId",
						String.valueOf(
							ParamUtil.getLong(actionRequest, "classTypeId"))
					).put(
						"title", ParamUtil.getString(actionRequest, "title")
					).put(
						"type", ParamUtil.getString(actionRequest, "type")
					).buildString(),
					serviceContext);
			}
			catch (SiteNavigationMenuItemNameException
						siteNavigationMenuItemNameException) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						siteNavigationMenuItemNameException,
						siteNavigationMenuItemNameException);
				}

				jsonObject.put(
					"errorMessage",
					LanguageUtil.get(
						_portal.getHttpServletRequest(actionRequest),
						"an-unexpected-error-occurred"));
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to add SiteNavigationMenuItem for classNameId ",
						classNameId, ", classPK ", classPK,
						" siteNavigationMenuId ", siteNavigationMenuId,
						" and type ", siteNavigationMenuItemType));
			}

			jsonObject.put(
				"errorMessage",
				LanguageUtil.get(
					_portal.getHttpServletRequest(actionRequest),
					"an-unexpected-error-occurred"));
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddDisplayPageTypeSiteNavigationMenuItemMVCActionCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

}