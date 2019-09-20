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

package com.liferay.push.notifications.sender.android.internal;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.push.notifications.constants.PushNotificationsConstants;
import com.liferay.push.notifications.constants.PushNotificationsDestinationNames;
import com.liferay.push.notifications.exception.PushNotificationsException;
import com.liferay.push.notifications.sender.PushNotificationsSender;
import com.liferay.push.notifications.sender.android.internal.configuration.AndroidPushNotificationsSenderConfiguration;
import com.liferay.push.notifications.service.PushNotificationsDeviceLocalService;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Silvio Santos
 * @author Bruno Farache
 */
@Component(
	configurationPid = "com.liferay.push.notifications.sender.android.internal.configuration.AndroidPushNotificationsSenderConfiguration",
	immediate = true,
	property = "platform=" + AndroidPushNotificationsSender.PLATFORM,
	service = PushNotificationsSender.class
)
public class AndroidPushNotificationsSender implements PushNotificationsSender {

	public static final String PLATFORM = "android";

	@Override
	public void send(List<String> tokens, JSONObject payloadJSONObject)
		throws Exception {

		if (_sender == null) {
			throw new PushNotificationsException(
				"Android push notifications sender is not configured properly");
		}

		Message message = buildMessage(payloadJSONObject);

		MulticastResult multicastResult = _sender.send(
			message, tokens,
			_androidPushNotificationsSenderConfiguration.retries());

		validateMulticastResult(tokens, payloadJSONObject, multicastResult);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_androidPushNotificationsSenderConfiguration =
			ConfigurableUtil.createConfigurable(
				AndroidPushNotificationsSenderConfiguration.class, properties);

		String apiKey = _androidPushNotificationsSenderConfiguration.apiKey();

		if (Validator.isNull(apiKey)) {
			_sender = null;

			return;
		}

		_sender = new Sender(apiKey);
	}

	protected Message buildMessage(JSONObject payloadJSONObject) {
		Message.Builder builder = new Message.Builder();

		builder.addData(
			PushNotificationsConstants.KEY_PAYLOAD,
			payloadJSONObject.toString());

		return builder.build();
	}

	@Deactivate
	protected void deactivate() {
		_sender = null;
	}

	@Reference(unbind = "-")
	protected void setPushNotificationsDeviceLocalService(
		PushNotificationsDeviceLocalService
			pushNotificationsDeviceLocalService) {

		_pushNotificationsDeviceLocalService =
			pushNotificationsDeviceLocalService;
	}

	protected void validateMulticastResult(
		List<String> tokens, JSONObject payloadJSONObject,
		MulticastResult multicastResult) {

		List<Result> results = multicastResult.getResults();

		for (int i = 0; i < results.size(); i++) {
			Result result = results.get(i);
			String token = tokens.get(i);

			com.liferay.portal.kernel.messaging.Message message =
				new com.liferay.portal.kernel.messaging.Message();

			message.setPayload(
				new AndroidResponse(result, token, payloadJSONObject));

			_messageBus.sendMessage(
				PushNotificationsDestinationNames.PUSH_NOTIFICATION_RESPONSE,
				message);

			if ((multicastResult.getCanonicalIds() == 0) &&
				(multicastResult.getFailure() == 0)) {

				continue;
			}

			String canonicalRegistrationId =
				result.getCanonicalRegistrationId();

			if (Validator.isNotNull(canonicalRegistrationId) &&
				Validator.isNotNull(result.getMessageId())) {

				try {
					_pushNotificationsDeviceLocalService.updateToken(
						token, canonicalRegistrationId);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to update token " + token);
					}
				}
			}

			String errorCodeName = result.getErrorCodeName();

			if (Validator.isNotNull(errorCodeName) &&
				(errorCodeName.equals(Constants.ERROR_INVALID_REGISTRATION) ||
				 errorCodeName.equals(Constants.ERROR_MISMATCH_SENDER_ID) ||
				 errorCodeName.equals(Constants.ERROR_NOT_REGISTERED))) {

				try {
					_pushNotificationsDeviceLocalService.
						deletePushNotificationsDevice(token);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to delete invalid token " + token);
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AndroidPushNotificationsSender.class);

	private volatile AndroidPushNotificationsSenderConfiguration
		_androidPushNotificationsSenderConfiguration;

	@Reference
	private MessageBus _messageBus;

	private PushNotificationsDeviceLocalService
		_pushNotificationsDeviceLocalService;
	private volatile Sender _sender;

}