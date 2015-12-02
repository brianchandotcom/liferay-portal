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

package com.liferay.notifications.web.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.notifications.web.constants.NotificationsPortletKeys;
import com.liferay.portal.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.USER_MY_ACCOUNT,
		"service.ranking:Integer=400"
	},
	service = PanelApp.class
)
public class NotificationsPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return NotificationsPortletKeys.NOTIFICATIONS;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + NotificationsPortletKeys.NOTIFICATIONS + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}