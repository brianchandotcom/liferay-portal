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

import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.taglib.servlet.taglib.NavigationMenuTag;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sandro Chinea
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
		"mvc.command.name=/site_navigation_admin/get_site_navigation_menu_preview"
	},
	service = MVCResourceCommand.class
)
public class GetSiteNavigationMenuPreviewMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		long siteNavigationMenuId = ParamUtil.getLong(
			resourceRequest, "siteNavigationMenuId");

		String ddmTemplateKey = ParamUtil.getString(
			resourceRequest, "ddmTemplateKey");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			httpServletResponse, unsyncStringWriter);

		NavigationMenuTag navigationMenuTag = new NavigationMenuTag();

		navigationMenuTag.setSiteNavigationMenuId(siteNavigationMenuId);
		navigationMenuTag.setDdmTemplateKey(ddmTemplateKey);
		navigationMenuTag.setRootItemLevel(0);

		navigationMenuTag.doTag(httpServletRequest, pipingServletResponse);

		ServletResponseUtil.write(
			httpServletResponse, unsyncStringWriter.toString());
	}

	@Reference
	private Portal _portal;

}