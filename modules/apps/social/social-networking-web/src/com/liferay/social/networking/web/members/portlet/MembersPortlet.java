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

package com.liferay.social.networking.web.members.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"com.liferay.portlet.css-class-wrapper=social-networking-portlet-members",
		"com.liferay.portlet.display-category=category.social",
		"com.liferay.portlet.header-portlet-css=/members/css/main.css",
		"com.liferay.portlet.icon=/icons/members.png",
		"javax.portlet.display-name=Members",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=Members",
		"javax.portlet.info.short-title=Members",
		"javax.portlet.info.title=Members",
		"javax.portlet.init-param.view-template=/members/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref.role-name=administrator",
		"javax.portlet.security-role-ref.role-name=guest",
		"javax.portlet.security-role-ref.role-name=power-user",
		"javax.portlet.security-role-ref.role-name=user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class MembersPortlet extends MVCPortlet {
}