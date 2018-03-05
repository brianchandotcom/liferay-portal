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

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
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

		String type = ParamUtil.getString(actionRequest, "type");

		UnicodeProperties typeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		List<String> layoutUUIDs = StringUtil.split(
			typeSettingsProperties.getProperty("layoutUuid"));

		for (String layoutUuid : layoutUUIDs) {
			UnicodeProperties curTypeSettingsProperties = new UnicodeProperties(
				true);

			long groupId = GetterUtil.getLong(
				typeSettingsProperties.get("groupId"));

			boolean privateLayout = GetterUtil.getBoolean(
				typeSettingsProperties.get("privateLayout"));

			Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				layoutUuid, groupId, privateLayout);

			if (layout == null) {
				continue;
			}

			curTypeSettingsProperties.setProperty(
				"groupId", String.valueOf(groupId));
			curTypeSettingsProperties.setProperty("layoutUuid", layoutUuid);
			curTypeSettingsProperties.setProperty(
				"privateLayout", String.valueOf(privateLayout));
			curTypeSettingsProperties.setProperty(
				"title", layout.getName(themeDisplay.getLocale()));

			_siteNavigationMenuItemService.addSiteNavigationMenuItem(
				themeDisplay.getScopeGroupId(), siteNavigationMenuId, 0, type,
				curTypeSettingsProperties.toString(), serviceContext);
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

}