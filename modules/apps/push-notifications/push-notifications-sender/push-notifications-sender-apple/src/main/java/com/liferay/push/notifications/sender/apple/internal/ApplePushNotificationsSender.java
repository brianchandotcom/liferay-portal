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

package com.liferay.push.notifications.sender.apple.internal;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.ApnsClientBuilder;
import com.eatthepath.pushy.apns.PushNotificationResponse;
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.push.notifications.constants.PushNotificationsConstants;
import com.liferay.push.notifications.constants.PushNotificationsDestinationNames;
import com.liferay.push.notifications.exception.PushNotificationsException;
import com.liferay.push.notifications.sender.PushNotificationsSender;
import com.liferay.push.notifications.sender.apple.internal.configuration.ApplePushNotificationsSenderConfiguration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.time.Instant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import javax.net.ssl.SSLException;

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
	configurationPid = "com.liferay.push.notifications.sender.apple.internal.configuration.ApplePushNotificationsSenderConfiguration",
	immediate = true,
	property = "platform=" + ApplePushNotificationsSender.PLATFORM,
	service = PushNotificationsSender.class
)
public class ApplePushNotificationsSender implements PushNotificationsSender {

	public static final String PLATFORM = "apple";

	public static final String TOPIC = "com.liferay.demopush1";

	@Override
	public void send(List<String> tokens, JSONObject payloadJSONObject)
		throws Exception {

		if (_apnsClient == null) {
			throw new PushNotificationsException(
				"Apple push notifications sender is not configured properly");
		}

		String payload = buildPayload(payloadJSONObject);

		Stream<String> tokensStream = tokens.stream();

		tokensStream.map(
			t -> new SimpleApnsPushNotification(
				t, ApplePushNotificationsSender.TOPIC, payload)
		).forEach(
			notification -> _handleNotificationResponse(
				_apnsClient.sendNotification(notification))
		);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties)
		throws SSLException {

		ApplePushNotificationsSenderConfiguration
			applePushNotificationsSenderConfiguration =
				ConfigurableUtil.createConfigurable(
					ApplePushNotificationsSenderConfiguration.class,
					properties);

		String certificatePath =
			applePushNotificationsSenderConfiguration.certificatePath();
		String certificatePassword =
			applePushNotificationsSenderConfiguration.certificatePassword();

		if (Validator.isNull(certificatePath) ||
			Validator.isNull(certificatePassword)) {

			_apnsClient = null;

			return;
		}

		ApnsClientBuilder apnsClientBuilder = new ApnsClientBuilder();

		try (InputStream inputStream = _getCertificateInputStream(
				certificatePath)) {

			if (inputStream == null) {
				throw new IllegalArgumentException(
					"Unable to find Apple certificate at " + certificatePath);
			}

			apnsClientBuilder.setClientCredentials(
				inputStream, certificatePassword);
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioException, ioException);
			}
		}

		if (applePushNotificationsSenderConfiguration.sandbox()) {
			apnsClientBuilder.setApnsServer(
				ApnsClientBuilder.DEVELOPMENT_APNS_HOST);
		}
		else {
			apnsClientBuilder.setApnsServer(
				ApnsClientBuilder.PRODUCTION_APNS_HOST);
		}

		_apnsClient = apnsClientBuilder.build();
	}

	protected String buildPayload(JSONObject payloadJSONObject) {
		SimpleApnsPayloadBuilder builder = new SimpleApnsPayloadBuilder();

		String title = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_TITLE);

		if (Validator.isNotNull(title)) {
			builder.setAlertTitle(title);
		}

		if (payloadJSONObject.has(PushNotificationsConstants.KEY_BADGE)) {
			builder.setBadgeNumber(
				payloadJSONObject.getInt(PushNotificationsConstants.KEY_BADGE));
		}

		String body = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_BODY);

		if (Validator.isNotNull(body)) {
			builder.setAlertBody(body);
		}

		String bodyLocalizedKey = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_BODY_LOCALIZED);

		JSONArray bodyLocalizedArgumentsJSONArray =
			payloadJSONObject.getJSONArray(
				PushNotificationsConstants.KEY_BODY_LOCALIZED_ARGUMENTS);

		if (Validator.isNotNull(bodyLocalizedKey)) {
			if (bodyLocalizedArgumentsJSONArray != null) {
				List<String> localizedArguments = new ArrayList<>();

				for (int i = 0; i < bodyLocalizedArgumentsJSONArray.length();
					 i++) {

					localizedArguments.add(
						bodyLocalizedArgumentsJSONArray.getString(i));
				}

				builder.setLocalizedAlertMessage(
					bodyLocalizedKey,
					localizedArguments.toArray(new String[0]));
			}
			else {
				builder.setLocalizedAlertMessage(bodyLocalizedKey);
			}
		}

		boolean silent = payloadJSONObject.getBoolean(
			PushNotificationsConstants.KEY_SILENT);

		if (silent) {
			builder.setSound(null);
		}

		String sound = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_SOUND);

		if (Validator.isNotNull(sound)) {
			builder.setSound(sound);
		}

		String titleLocalizedKey = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_TITLE_LOCALIZED);

		JSONArray titleLocalizedArgumentsJSONArray =
			payloadJSONObject.getJSONArray(
				PushNotificationsConstants.KEY_TITLE_LOCALIZED_ARGUMENTS);

		if (Validator.isNotNull(titleLocalizedKey)) {
			if (titleLocalizedArgumentsJSONArray != null) {
				List<String> localizedArguments = new ArrayList<>();

				for (int i = 0; i < titleLocalizedArgumentsJSONArray.length();
					 i++) {

					localizedArguments.add(
						titleLocalizedArgumentsJSONArray.getString(i));
				}

				builder.setLocalizedAlertTitle(
					titleLocalizedKey,
					localizedArguments.toArray(new String[0]));
			}
			else {
				builder.setLocalizedAlertTitle(titleLocalizedKey);
			}
		}

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
				!key.equals(PushNotificationsConstants.KEY_SILENT) &&
				!key.equals(PushNotificationsConstants.KEY_TITLE) &&
				!key.equals(PushNotificationsConstants.KEY_TITLE_LOCALIZED) &&
				!key.equals(
					PushNotificationsConstants.KEY_TITLE_LOCALIZED_ARGUMENTS)) {

				newPayloadJSONObject.put(key, payloadJSONObject.get(key));
			}
		}

		builder.addCustomProperty(
			PushNotificationsConstants.KEY_PAYLOAD,
			newPayloadJSONObject.toString());

		return builder.build();
	}

	@Deactivate
	protected void deactivate() {
		_apnsClient.close();
	}

	protected void sendResponse(AppleResponse appleResponse) {
		Message message = new Message();

		message.setPayload(appleResponse);

		_messageBus.sendMessage(
			PushNotificationsDestinationNames.PUSH_NOTIFICATION_RESPONSE,
			message);
	}

	private InputStream _getCertificateInputStream(String certificatePath) {
		try {
			return new FileInputStream(certificatePath);
		}
		catch (FileNotFoundException fileNotFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(fileNotFoundException, fileNotFoundException);
			}

			ClassLoader classLoader =
				ApplePushNotificationsSender.class.getClassLoader();

			return classLoader.getResourceAsStream(certificatePath);
		}
	}

	private void _handleNotificationResponse(
		CompletableFuture<PushNotificationResponse<SimpleApnsPushNotification>>
			notificationResponse) {

		notificationResponse.whenComplete(
			(response, cause) -> {
				if (response != null) {
					if (!response.isAccepted() && _log.isWarnEnabled()) {
						String timestamp = String.valueOf(
							response.getTokenInvalidationTimestamp(
							).orElse(
								Instant.parse("")
							));

						_log.warn(
							StringBundler.concat(
								"Notification rejected by the APNs ",
								"gateway: ", response.getRejectionReason(),
								"\t…and the token is invalid as of ",
								timestamp));
					}

					sendResponse(
						new AppleResponse(response.getPushNotification(),
							false));
				}
				else {
					sendResponse(new AppleResponse(null, cause));
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ApplePushNotificationsSender.class);

	private volatile ApnsClient _apnsClient;

	@Reference
	private MessageBus _messageBus;

}