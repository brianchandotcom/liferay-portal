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
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.exception.SiteNavigationMenuItemNameException;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
		"mvc.command.name=/navigation_menu/add_display_page_site_navigation_menu_item"
	},
	service = MVCActionCommand.class
)
public class AddDisplayPageSiteNavigationMenuItemMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		if ((classNameId > 0) && (classPK > 0)) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long classTypeId = ParamUtil.getLong(actionRequest, "classTypeId");

			long siteNavigationMenuId = ParamUtil.getLong(
				actionRequest, "siteNavigationMenuId");
			String title = ParamUtil.getString(actionRequest, "title");
			String type = ParamUtil.getString(actionRequest, "type");

			UnicodeProperties curTypeSettingsUnicodeProperties =
				new UnicodeProperties(true);

			curTypeSettingsUnicodeProperties.setProperty(
				"classNameId", String.valueOf(classNameId));
			curTypeSettingsUnicodeProperties.setProperty(
				"classTypeId", String.valueOf(classTypeId));
			curTypeSettingsUnicodeProperties.setProperty(
				"classPK", String.valueOf(classPK));
			curTypeSettingsUnicodeProperties.setProperty("title", title);
			curTypeSettingsUnicodeProperties.setProperty("type", type);

			try {
				_siteNavigationMenuItemService.addSiteNavigationMenuItem(
					themeDisplay.getScopeGroupId(), siteNavigationMenuId, 0,
					SiteNavigationMenuItemTypeConstants.DISPLAY_PAGE,
					curTypeSettingsUnicodeProperties.toString(),
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

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddDisplayPageSiteNavigationMenuItemMVCActionCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

}