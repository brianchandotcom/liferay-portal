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

package com.liferay.portal.workflow.kaleo.runtime.internal.notification;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.workflow.constants.MyWorkflowTasksConstants;
import com.liferay.portal.workflow.kaleo.definition.NotificationReceptionType;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.internal.util.NotificationMessageHelper;
import com.liferay.portal.workflow.kaleo.runtime.notification.BaseNotificationSender;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationRecipient;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationSender;

import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = "notification.type=user-notification",
	service = NotificationSender.class
)
public class UserNotificationMessageSender
	extends BaseNotificationSender implements NotificationSender {

	@Override
	protected void doSendNotification(
			Map<NotificationReceptionType, Set<NotificationRecipient>>
				notificationRecipients,
			String defaultSubject, String notificationMessage,
			ExecutionContext executionContext)
		throws Exception {

		JSONObject jsonObject =
			_notificationMessageHelper.createMessageJSONObject(
				notificationMessage, executionContext);

		for (Map.Entry<NotificationReceptionType, Set<NotificationRecipient>>
				entry : notificationRecipients.entrySet()) {

			for (NotificationRecipient notificationRecipient :
					entry.getValue()) {

				if (notificationRecipient.getUserId() <= 0) {
					continue;
				}

				if (UserNotificationManagerUtil.isDeliver(
						notificationRecipient.getUserId(),
						PortletKeys.MY_WORKFLOW_TASK, 0,
						MyWorkflowTasksConstants.
							NOTIFICATION_TYPE_MY_WORKFLOW_TASKS,
						UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

					_userNotificationEventLocalService.
						sendUserNotificationEvents(
							notificationRecipient.getUserId(),
							PortletKeys.MY_WORKFLOW_TASK,
							UserNotificationDeliveryConstants.TYPE_WEBSITE,
							jsonObject);
				}
			}
		}
	}

	@Reference
	private NotificationMessageHelper _notificationMessageHelper;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}