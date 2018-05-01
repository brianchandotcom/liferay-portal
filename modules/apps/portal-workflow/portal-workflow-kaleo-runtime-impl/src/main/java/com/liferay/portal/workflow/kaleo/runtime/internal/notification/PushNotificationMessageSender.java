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

import static com.liferay.push.notifications.constants.PushNotificationsDestinationNames.PUSH_NOTIFICATION;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.workflow.kaleo.definition.NotificationReceptionType;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.internal.util.NotificationMessageHelper;
import com.liferay.portal.workflow.kaleo.runtime.notification.BaseNotificationSender;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationRecipient;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationSender;
import com.liferay.push.notifications.constants.PushNotificationsConstants;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"fromName=Liferay Portal Workflow Notifications",
		"notification.type=push-notification"
	},
	service = NotificationSender.class
)
public class PushNotificationMessageSender
	extends BaseNotificationSender implements NotificationSender {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_fromName = (String)properties.get("fromName");
	}

	protected Message createMessage(
		Collection<Set<NotificationRecipient>> notificationRecipients,
		String notificationMessage, ExecutionContext executionContext) {

		Message message = new Message();

		JSONObject payloadJSONObject = createPayloadJSONObject(
			notificationRecipients, notificationMessage, executionContext);

		message.setPayload(payloadJSONObject);

		return message;
	}

	protected JSONObject createPayloadJSONObject(
		Collection<Set<NotificationRecipient>> notificationRecipients,
		String notificationMessage, ExecutionContext executionContext) {

		JSONObject jsonObject =
			notificationMessageHelper.createMessageJSONObject(
				notificationMessage, executionContext);

		jsonObject.put(
			PushNotificationsConstants.KEY_BODY, notificationMessage);
		jsonObject.put(PushNotificationsConstants.KEY_FROM, _fromName);
		jsonObject.put(
			PushNotificationsConstants.KEY_TO_USER_IDS,
			createUserIdsRecipientsJSONArray(notificationRecipients.stream()));

		return jsonObject;
	}

	protected JSONArray createUserIdsRecipientsJSONArray(
		Stream<Set<NotificationRecipient>> notificationRecipientsSetStream) {

		JSONArray jsonArray = jsonFactory.createJSONArray();

		Stream<NotificationRecipient> notificationRecipientStream =
			notificationRecipientsSetStream.flatMap(
				notificationRecipientSet -> notificationRecipientSet.stream());

		notificationRecipientStream.filter(
			notificationRecipient -> notificationRecipient.getUserId() > 0
		).forEach(
			notificationRecipient ->
				jsonArray.put(notificationRecipient.getUserId())
		);

		return jsonArray;
	}

	@Override
	protected void doSendNotification(
			Map<NotificationReceptionType, Set<NotificationRecipient>>
				notificationRecipients,
			String defaultSubject, String notificationMessage,
			ExecutionContext executionContext)
		throws Exception {

		Message message = createMessage(
			notificationRecipients.values(), notificationMessage,
			executionContext);

		messageBus.sendMessage(PUSH_NOTIFICATION, message);
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected MessageBus messageBus;

	@Reference
	protected NotificationMessageHelper notificationMessageHelper;

	private String _fromName;

}