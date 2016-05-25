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
import com.liferay.social.networking.service.MeetupsEntryLocalService;
import com.liferay.social.networking.service.MeetupsRegistrationLocalService;
import com.liferay.social.networking.service.WallEntryLocalService;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"com.liferay.portlet.css-class-wrapper=social-networking-portlet-friends-activities",
		"com.liferay.portlet.display-category=category.social",
		"javax.portlet.display-name=Friends' Activities",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=Friends' activities",
		"javax.portlet.info.short-title=Friends' Activities",
		"javax.portlet.info.title=Friends' Activities",
		"javax.portlet.init-param.view-template=/friends_activities/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class FriendsActivitiesPortlet extends MVCPortlet {

	@Reference(unbind = "-")
	protected void setMeetupsEntryLocalService(
		MeetupsEntryLocalService meetupsEntryLocalService) {
	}

	@Reference(unbind = "-")
	protected void setMeetupsRegistrationLocalService(
		MeetupsRegistrationLocalService meetupsRegistrationLocalService) {
	}

	@Reference(unbind = "-")
	protected void setWallEntryLocalService(
		WallEntryLocalService wallEntryLocalService) {
	}

}