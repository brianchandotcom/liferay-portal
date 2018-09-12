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

package com.liferay.portal.workflow.task.web.internal.notifications;

import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.workflow.constants.MyWorkflowTasksConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sarai Díaz
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + PortletKeys.MY_WORKFLOW_TASK,
	service = UserNotificationDefinition.class
)
public class MyWorkflowTasksWorkflowUserNotificationDefinition
	extends UserNotificationDefinition {

	public MyWorkflowTasksWorkflowUserNotificationDefinition() {
		super(
			PortletKeys.MY_WORKFLOW_TASK, 0,
			MyWorkflowTasksConstants.NOTIFICATION_TYPE_MY_WORKFLOW_TASKS,
			"receive-a-notification-when-someone-interacts-with-a-workflow");

		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"email", UserNotificationDeliveryConstants.TYPE_EMAIL, true,
				true));
		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"mobile", UserNotificationDeliveryConstants.TYPE_PUSH, true,
				true));
		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"website", UserNotificationDeliveryConstants.TYPE_WEBSITE, true,
				true));
	}

}