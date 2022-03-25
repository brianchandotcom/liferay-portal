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

package com.liferay.batch.engine.internal.notification;

import com.liferay.batch.engine.constants.BatchEnginePortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + BatchEnginePortletKeys.BATCH_ENGINE,
	service = UserNotificationHandler.class
)
public class BatchEngineUserNotificationHandler
	extends BaseUserNotificationHandler {

	public BatchEngineUserNotificationHandler() {
		setPortletId(BatchEnginePortletKeys.BATCH_ENGINE);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		String status = serviceContext.translate(
			jsonObject.getString("status"));
		String taskType = serviceContext.translate(
			jsonObject.getString("taskType"));

		return String.format(
			"<h2 class=\"title\">%s</h2><div class=\"body\">%s</div>",
			serviceContext.translate("x-batch-engine-task-x", taskType, status),
			serviceContext.translate(
				"x-batch-engine-task-for-x-x", taskType,
				jsonObject.getString("className"), status));
	}

}