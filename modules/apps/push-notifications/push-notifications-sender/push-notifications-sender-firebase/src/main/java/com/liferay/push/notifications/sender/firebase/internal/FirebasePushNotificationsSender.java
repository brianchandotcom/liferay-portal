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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.push.notifications.constants.PushNotificationsConstants;
import com.liferay.push.notifications.exception.PushNotificationsException;
import com.liferay.push.notifications.sender.PushNotificationsSender;
import com.liferay.push.notifications.sender.firebase.internal.configuration.FirebasePushNotificationsSenderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	property = "platform=" + FirebasePushNotificationsSender.PLATFORM,
	service = PushNotificationsSender.class
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

		boolean silent = payloadJSONObject.getBoolean(
			PushNotificationsConstants.KEY_SILENT);

		if (silent) {
			builder.contentAvailable(silent);
		}

		builder.notification(buildNotification(payloadJSONObject));
		builder.to(tokens);

		JSONObject newPayloadJSONObject = JSONFactoryUtil.createJSONObject();

		Iterator<String> iterator = payloadJSONObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (!key.equals(PushNotificationsConstants.KEY_BADGE) &&
				!key.equals(PushNotificationsConstants.KEY_BODY) &&
				!key.equals(PushNotificationsConstants.KEY_BODY_LOCALIZED) &&
				!key.equals(
					PushNotificationsConstants.KEY_BODY_LOCALIZED_ARGUMENTS) &&
				!key.equals(PushNotificationsConstants.KEY_SOUND) &&
				!key.equals(PushNotificationsConstants.KEY_SILENT)) {

				newPayloadJSONObject.put(key, payloadJSONObject.get(key));
			}
		}

		if (newPayloadJSONObject.length() > 0) {
			Map<String, String> data = new HashMap<>();

			data.put(
				PushNotificationsConstants.KEY_PAYLOAD,
				newPayloadJSONObject.toString());

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

		String title = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_TITLE);

		if (Validator.isNotNull(title)) {
			builder.title(title);
		}

		JSONArray titleLocalizedArgumentsJSONArray =
			payloadJSONObject.getJSONArray(
				PushNotificationsConstants.KEY_TITLE_LOCALIZED_ARGUMENTS);

		if (titleLocalizedArgumentsJSONArray != null) {
			List<String> localizedArguments = new ArrayList<>();

			for (int i = 0; i < titleLocalizedArgumentsJSONArray.length();
				 i++) {

				localizedArguments.add(
					titleLocalizedArgumentsJSONArray.getString(i));
			}

			builder.titleLocalizationArguments(localizedArguments);
		}

		String titleLocalizedKey = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_TITLE_LOCALIZED);

		if (Validator.isNotNull(titleLocalizedKey)) {
			builder.titleLocalizationKey(titleLocalizedKey);
		}

		return builder.build();
	}

	@Deactivate
	protected void deactivate() {
		_sender = null;
	}

	private volatile FirebasePushNotificationsSenderConfiguration
		_firebasePushNotificationsSenderConfiguration;
	private volatile Sender _sender;

}