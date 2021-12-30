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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.workflow.kaleo.definition.NotificationReceptionType;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.internal.helper.NotificationMessageHelper;
import com.liferay.portal.workflow.kaleo.runtime.notification.BaseNotificationSender;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationRecipient;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationSender;
import com.liferay.push.notifications.constants.PushNotificationsConstants;
import com.liferay.push.notifications.constants.PushNotificationsDestinationNames;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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

	@Override
	protected void doSendNotification(
			Map<NotificationReceptionType, Set<NotificationRecipient>>
				notificationRecipientsMap,
			String defaultSubject, String notificationMessage,
			ExecutionContext executionContext)
		throws Exception {

		Collection<Set<NotificationRecipient>>
			notificationRecipientsCollection =
				notificationRecipientsMap.values();

		Iterator<Set<NotificationRecipient>> iterator =
			notificationRecipientsCollection.iterator();

		List<NotificationRecipient> notificationRecipients = new ArrayList<>(
			getDeliverableNotificationRecipients(
				iterator.next(), UserNotificationDeliveryConstants.TYPE_PUSH));

		Message message = _createMessage(
			notificationRecipients, notificationMessage, executionContext);

		messageBus.sendMessage(
			PushNotificationsDestinationNames.PUSH_NOTIFICATION, message);
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected MessageBus messageBus;

	@Reference
	protected NotificationMessageHelper notificationMessageHelper;

	private Message _createMessage(
		List<NotificationRecipient> notificationRecipients,
		String notificationMessage, ExecutionContext executionContext) {

		Message message = new Message();

		JSONObject payloadJSONObject = _createPayloadJSONObject(
			notificationRecipients, notificationMessage, executionContext);

		message.setPayload(payloadJSONObject);

		return message;
	}

	private JSONObject _createPayloadJSONObject(
		List<NotificationRecipient> notificationRecipients,
		String notificationMessage, ExecutionContext executionContext) {

		JSONObject jsonObject =
			notificationMessageHelper.createMessageJSONObject(
				notificationMessage, executionContext);

		jsonObject.put(
			PushNotificationsConstants.KEY_BODY, notificationMessage
		).put(
			PushNotificationsConstants.KEY_FROM, _fromName
		).put(
			PushNotificationsConstants.KEY_TO_USER_IDS,
			_createUserIdsRecipientsJSONArray(notificationRecipients)
		);

		return jsonObject;
	}

	private JSONArray _createUserIdsRecipientsJSONArray(
		List<NotificationRecipient> notificationRecipients) {

		JSONArray jsonArray = jsonFactory.createJSONArray();

		Stream<NotificationRecipient> stream = notificationRecipients.stream();

		stream.filter(
			notificationRecipient -> notificationRecipient.getUserId() > 0
		).forEach(
			notificationRecipient -> jsonArray.put(
				notificationRecipient.getUserId())
		);

		return jsonArray;
	}

	private String _fromName;

}