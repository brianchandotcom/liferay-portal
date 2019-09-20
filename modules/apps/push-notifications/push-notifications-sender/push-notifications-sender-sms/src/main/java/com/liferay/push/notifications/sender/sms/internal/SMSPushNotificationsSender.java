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

package com.liferay.push.notifications.sender.sms.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.push.notifications.constants.PushNotificationsConstants;
import com.liferay.push.notifications.constants.PushNotificationsDestinationNames;
import com.liferay.push.notifications.exception.PushNotificationsException;
import com.liferay.push.notifications.sender.PushNotificationsSender;
import com.liferay.push.notifications.sender.Response;
import com.liferay.push.notifications.sender.sms.internal.configuration.SMSPushNotificationsSenderConfiguration;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 */
@Component(
	configurationPid = "com.liferay.push.notifications.sender.sms.internal.configuration.SMSPushNotificationsSenderConfiguration",
	immediate = true,
	property = "platform=" + SMSPushNotificationsSender.PLATFORM,
	service = PushNotificationsSender.class
)
public class SMSPushNotificationsSender implements PushNotificationsSender {

	public static final String PLATFORM = "sms";

	@Override
	public void send(List<String> numbers, JSONObject payloadJSONObject)
		throws Exception {

		if (_twilioRestClient == null) {
			throw new PushNotificationsException(
				"SMS push notifications sender is not configured properly");
		}

		Account account = _twilioRestClient.getAccount();

		SmsFactory smsFactory = account.getSmsFactory();

		String body = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_BODY);

		String from = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_FROM);

		if (Validator.isNull(from)) {
			from = _smsPushNotificationsSenderConfiguration.number();
		}

		for (String number : numbers) {
			Map<String, String> params = new HashMap<>();

			params.put("Body", body);
			params.put("From", from);

			String statusCallback =
				_smsPushNotificationsSenderConfiguration.statusCallback();

			if (Validator.isNotNull(statusCallback)) {
				params.put("StatusCallback", statusCallback);
			}

			params.put("To", number);

			Response response = new SMSResponse(
				smsFactory.create(params), payloadJSONObject);

			Message message = new Message();

			message.setPayload(response);

			_messageBus.sendMessage(
				PushNotificationsDestinationNames.PUSH_NOTIFICATION_RESPONSE,
				message);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_smsPushNotificationsSenderConfiguration =
			ConfigurableUtil.createConfigurable(
				SMSPushNotificationsSenderConfiguration.class, properties);

		String accountSID =
			_smsPushNotificationsSenderConfiguration.accountSID();
		String authToken = _smsPushNotificationsSenderConfiguration.authToken();

		if (Validator.isNull(accountSID) || Validator.isNull(authToken)) {
			_twilioRestClient = null;

			return;
		}

		_twilioRestClient = new TwilioRestClient(accountSID, authToken);
	}

	@Reference
	private MessageBus _messageBus;

	private volatile SMSPushNotificationsSenderConfiguration
		_smsPushNotificationsSenderConfiguration;
	private volatile TwilioRestClient _twilioRestClient;

}