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

package com.liferay.message.boards.web.internal.notifications;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS},
	service = UserNotificationHandler.class
)
public class MBUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public MBUserNotificationHandler() {
		setPortletId(MBPortletKeys.MESSAGE_BOARDS);
	}

	@Override
	protected String getFormattedMessage(
		JSONObject jsonObject, ServiceContext serviceContext, String message,
		String typeName) {

		return LanguageUtil.format(
			serviceContext.getLocale(), message,
			new String[] {
				HtmlUtil.escape(jsonObject.getString("fullName")),
				StringUtil.toLowerCase(HtmlUtil.escape(typeName))
			},
			false);
	}

}