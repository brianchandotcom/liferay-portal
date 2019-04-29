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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.admin.web.internal.handler.SiteNavigationMenuExceptionRequestHandler;
import com.liferay.site.navigation.service.SiteNavigationMenuService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
		"mvc.command.name=/navigation_menu/update_site_navigation_menu"
	},
	service = MVCActionCommand.class
)
public class UpdateSiteNavigationMEnuMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long siteNavigationMenuId = ParamUtil.getLong(
			actionRequest, "siteNavigationMenuId");

		String name = ParamUtil.getString(actionRequest, "name");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			_siteNavigationMenuService.updateSiteNavigationMenu(
				siteNavigationMenuId, name, serviceContext);

			JSONObject jsonObject = JSONUtil.put(
				"redirectURL", getRedirectURL(actionResponse));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException pe) {
			hideDefaultSuccessMessage(actionRequest);

			_siteNavigationMenuExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, pe);
		}
	}

	protected String getRedirectURL(ActionResponse actionResponse) {
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");

		return portletURL.toString();
	}

	@Reference
	private Portal _portal;

	@Reference
	private SiteNavigationMenuExceptionRequestHandler
		_siteNavigationMenuExceptionRequestHandler;

	@Reference
	private SiteNavigationMenuService _siteNavigationMenuService;

}