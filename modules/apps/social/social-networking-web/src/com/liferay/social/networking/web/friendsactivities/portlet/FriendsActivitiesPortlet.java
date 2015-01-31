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

package com.liferay.social.networking.web.friendsactivities.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.social",
		"com.liferay.portlet.friendly-url-mapping=friends_activities",
		"com.liferay.portlet.friendly-url-routes=com/liferay/portal/kernel/portlet/rss-friendly-url-routes.xml",
		"com.liferay.portlet.css-class-wrapper=social-networking-portlet-friends-activities",
		"javax.portlet.display-name=Friends' Activities",
		"javax.portlet.init-param.config-template=/friends_activities/configuration.jsp",
		"javax.portlet.init-param.view-template=/friends_activities/view.jsp",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.supports.mime-type=text/html",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.info.title=Friends' Activities",
		"javax.portlet.info.short-title=Friends' Activities",
		"javax.portlet.info.keywords=Friends' activities",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class FriendsActivitiesPortlet extends MVCPortlet {
}