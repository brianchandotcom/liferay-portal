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

package com.liferay.push.notifications.sender.firebase.internal;

import com.liferay.mobile.fcm.Message;
import com.liferay.mobile.fcm.Notification;
import com.liferay.mobile.fcm.Sender;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.push.notifications.constants.PushNotificationsConstants;
import com.liferay.push.notifications.exception.PushNotificationsException;
import com.liferay.push.notifications.sender.PushNotificationsSender;
import com.liferay.push.notifications.sender.firebase.internal.configuration.FirebasePushNotificationsSenderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bruno Farache
 */
@Component(
	configurationPid = "com.liferay.push.notifications.sender.firebase.internal.configuration.FirebasePushNotificationsSenderConfiguration",
	immediate = true,
	property = {"platform=" + FirebasePushNotificationsSender.PLATFORM}
)
public class FirebasePushNotificationsSender
	implements PushNotificationsSender {

	public static final String PLATFORM = "firebase";

	@Override
	public void send(List<String> tokens, JSONObject payloadJSONObject)
		throws Exception {

		if (_sender == null) {
			throw new PushNotificationsException(
				"Firebase push notifications sender is not configured " +
					"properly");
		}

		_sender.send(buildMessage(tokens, payloadJSONObject));
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_firebasePushNotificationsSenderConfiguration =
			ConfigurableUtil.createConfigurable(
				FirebasePushNotificationsSenderConfiguration.class, properties);

		String apiKey = _firebasePushNotificationsSenderConfiguration.apiKey();

		if (Validator.isNull(apiKey)) {
			_sender = null;

			return;
		}

		_sender = new Sender(apiKey);
	}

	protected Message buildMessage(
		List<String> tokens, JSONObject payloadJSONObject) {

		Message.Builder builder = new Message.Builder();

		builder.to(tokens);
		builder.notification(buildNotification(payloadJSONObject));

		boolean silent = payloadJSONObject.getBoolean(
			PushNotificationsConstants.KEY_SILENT);

		if (silent) {
			builder.contentAvailable(silent);
		}

		payloadJSONObject.remove(PushNotificationsConstants.KEY_SILENT);

		if (payloadJSONObject.length() > 0) {
			Map<String, String> data = new HashMap<>();

			data.put(
				PushNotificationsConstants.KEY_PAYLOAD,
				payloadJSONObject.toString());

			builder.data(data);
		}

		return builder.build();
	}

	protected Notification buildNotification(JSONObject payloadJSONObject) {
		Notification.Builder builder = new Notification.Builder();

		if (payloadJSONObject.has(PushNotificationsConstants.KEY_BADGE)) {
			builder.badge(
				payloadJSONObject.getInt(PushNotificationsConstants.KEY_BADGE));
		}

		String body = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_BODY);

		if (Validator.isNotNull(body)) {
			builder.body(body);
		}

		String bodyLocalizedKey = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_BODY_LOCALIZED);

		if (Validator.isNotNull(bodyLocalizedKey)) {
			builder.bodyLocalizationKey(bodyLocalizedKey);
		}

		JSONArray bodyLocalizedArgumentsJSONArray =
			payloadJSONObject.getJSONArray(
				PushNotificationsConstants.KEY_BODY_LOCALIZED_ARGUMENTS);

		if (bodyLocalizedArgumentsJSONArray != null) {
			List<String> bodyLocalizedArguments = new ArrayList<>();

			for (int i = 0; i < bodyLocalizedArgumentsJSONArray.length(); i++) {
				bodyLocalizedArguments.add(
					bodyLocalizedArgumentsJSONArray.getString(i));
			}

			builder.bodyLocalizationArguments(bodyLocalizedArguments);
		}

		String sound = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_SOUND);

		if (Validator.isNotNull(sound)) {
			builder.sound(sound);
		}

		payloadJSONObject.remove(PushNotificationsConstants.KEY_BADGE);
		payloadJSONObject.remove(PushNotificationsConstants.KEY_BODY);
		payloadJSONObject.remove(PushNotificationsConstants.KEY_BODY_LOCALIZED);
		payloadJSONObject.remove(
			PushNotificationsConstants.KEY_BODY_LOCALIZED_ARGUMENTS);
		payloadJSONObject.remove(PushNotificationsConstants.KEY_SOUND);

		return builder.build();
	}

	@Deactivate
	protected void deactivate() {
		_sender = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FirebasePushNotificationsSender.class);

	private volatile FirebasePushNotificationsSenderConfiguration
		_firebasePushNotificationsSenderConfiguration;
	private volatile Sender _sender;

}