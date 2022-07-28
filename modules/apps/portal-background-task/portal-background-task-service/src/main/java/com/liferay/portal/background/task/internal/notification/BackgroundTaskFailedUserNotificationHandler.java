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

package com.liferay.portal.background.task.internal.notification;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dante Wang
 */
@Component(
	immediate = true, property = "javax.portlet.name=BackgroundTask",
	service = UserNotificationHandler.class
)
public class BackgroundTaskFailedUserNotificationHandler
	extends BaseUserNotificationHandler {

	public BackgroundTaskFailedUserNotificationHandler() {
		setPortletId("BackgroundTask");
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$TITLE$]", "[$BODY$]"},
			new String[] {
				_language.get(serviceContext.getLocale(), "background-task"),
				_language.format(
					serviceContext.getLocale(), "background-task-has-failed",
					new String[] {
						jsonObject.getString("name"),
						jsonObject.getString("taskExecutorClassName")
					})
			});
	}

	@Reference
	private Language _language;

}