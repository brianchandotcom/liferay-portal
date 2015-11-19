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

package com.liferay.wiki.navigation.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.wiki.navigation.web.constants.WikiNavigationPortletKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=wiki-navigation-portlet-page-menu",
		"com.liferay.portlet.display-category=category.wiki",
		"com.liferay.portlet.header-portlet-css=/page_menu/css/main.css",
		"com.liferay.portlet.icon=/page_menu/icons/page_menu.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Page Menu",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/page_menu/view.jsp",
		"javax.portlet.name=" + WikiNavigationPortletKeys.PAGE_MENU,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=nodeId;http://www.liferay.com/public-render-parameters/wiki",
		"javax.portlet.supported-public-render-parameter=title;http://www.liferay.com/public-render-parameters/wiki",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class WikiNavigationPageMenuPortlet extends MVCPortlet {
}