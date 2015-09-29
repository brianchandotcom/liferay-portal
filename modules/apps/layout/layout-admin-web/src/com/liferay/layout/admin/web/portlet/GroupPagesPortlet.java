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

package com.liferay.layout.admin.web.portlet;

import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-layouts-admin",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/group_pages.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Group Pages",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class}
)
public class GroupPagesPortlet extends LayoutAdminPortlet {

	@Override
	protected Group getGroup(PortletRequest portletRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getSiteGroup();

		portletRequest.setAttribute(WebKeys.GROUP, group);

		return group;
	}

}