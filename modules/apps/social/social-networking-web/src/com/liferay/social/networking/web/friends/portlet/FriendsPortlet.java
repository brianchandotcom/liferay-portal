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

package com.liferay.social.networking.web.friends.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.social",
		"com.liferay.portlet.icon=/icons/friends.png",
		"com.liferay.portlet.header-portlet-css=/friends/css/main.css",
		"com.liferay.portlet.css-class-wrapper=social-networking-portlet-friends",
		"javax.portlet.display-name=Friends",
		"javax.portlet.init-param.view-template=/friends/view.jsp",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.supports.mime-type=text/html",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.info.title=Friends",
		"javax.portlet.info.short-title=Friends",
		"javax.portlet.info.keywords=Friends",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class FriendsPortlet extends MVCPortlet {
}