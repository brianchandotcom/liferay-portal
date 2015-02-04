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

package com.liferay.social.networking.web.membersactivities.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.social",
		"com.liferay.portlet.friendly-url-mapping=activities",
		"com.liferay.portlet.friendly-url-routes=com/liferay/social/networking/web/membersactivities/portlet/route/membersactivities-friendly-url-routes.xml",
		"com.liferay.portlet.css-class-wrapper=social-networking-portlet-members",
		"javax.portlet.display-name=Members' Activities",
		"javax.portlet.init-param.config-template=/members_activities/configuration.jsp",
		"javax.portlet.init-param.view-template=/members_activities/view.jsp",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.supports.mime-type=text/html",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.info.title=Members' Activities",
		"javax.portlet.info.short-title=Members' Activities",
		"javax.portlet.info.keywords=Members' Activities",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class MembersActivitiesPortlet extends MVCPortlet {
}