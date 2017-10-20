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

package com.liferay.site.navigation.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.admin.web.internal.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
		"mvc.command.name=/navigation_menu/add_site_navigation_menu"
	},
	service = MVCActionCommand.class
)
public class AddSiteNavigationMenuMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String selectedItemType = ParamUtil.getString(
			actionRequest, "selectedItemType");

		String name = ParamUtil.getString(actionRequest, "name");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuService.addSiteNavigationMenu(
				themeDisplay.getScopeGroupId(), name, serviceContext);

		hideDefaultSuccessMessage(actionRequest);

		PortletURL redirectURL = PortletURLFactoryUtil.create(
			actionRequest, SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
			themeDisplay.getPlid(), ActionRequest.RENDER_PHASE);

		PortletURL viewSiteNavigationMenusURL = PortletURLFactoryUtil.create(
			actionRequest, SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
			themeDisplay.getPlid(), ActionRequest.RENDER_PHASE);

		viewSiteNavigationMenusURL.setParameter("mvcPath", "/view.jsp");

		redirectURL.setParameter("mvcPath", "/edit_site_navigation_menu.jsp");
		redirectURL.setParameter(
			"redirect", viewSiteNavigationMenusURL.toString());
		redirectURL.setParameter(
			"siteNavigationMenuId",
			String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()));
		redirectURL.setParameter("selectedItemType", selectedItemType);

		actionRequest.setAttribute(WebKeys.REDIRECT, redirectURL.toString());
	}

	@Reference
	private SiteNavigationMenuService _siteNavigationMenuService;

}