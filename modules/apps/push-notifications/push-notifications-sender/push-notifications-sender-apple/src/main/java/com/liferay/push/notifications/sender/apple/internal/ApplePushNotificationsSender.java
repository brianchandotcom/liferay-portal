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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.push.notifications.constants.PushNotificationsConstants;
import com.liferay.push.notifications.exception.PushNotificationsException;
import com.liferay.push.notifications.sender.PushNotificationsSender;
import com.liferay.push.notifications.sender.apple.internal.configuration.ApplePushNotificationsSenderConfiguration;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.apns.PayloadBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
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
	configurationPid = "com.liferay.push.notifications.sender.apple.internal.configuration.ApplePushNotificationsSenderConfiguration",
	immediate = true,
	property = "platform=" + ApplePushNotificationsSender.PLATFORM,
	service = PushNotificationsSender.class
)
public class ApplePushNotificationsSender implements PushNotificationsSender {

	public static final String PLATFORM = "apple";

	@Override
	public void send(List<String> tokens, JSONObject payloadJSONObject)
		throws Exception {

		if (_apnsService == null) {
			throw new PushNotificationsException(
				"Apple push notifications sender is not configured properly");
		}

		String payload = buildPayload(payloadJSONObject);

		_apnsService.push(tokens, payload);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
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

			_apnsService = null;

			return;
		}

		ApnsServiceBuilder appleServiceBuilder = APNS.newService();

		try (InputStream inputStream = _getCertificateInputStream(
				certificatePath)) {

			if (inputStream == null) {
				throw new IllegalArgumentException(
					"Unable to find Apple certificate at " + certificatePath);
			}

			appleServiceBuilder.withCert(inputStream, certificatePassword);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}
		}

		appleServiceBuilder.withDelegate(new AppleDelegate(_messageBus));

		if (applePushNotificationsSenderConfiguration.sandbox()) {
			appleServiceBuilder.withSandboxDestination();
		}
		else {
			appleServiceBuilder.withProductionDestination();
		}

		_apnsService = appleServiceBuilder.build();
	}

	protected String buildPayload(JSONObject payloadJSONObject) {
		PayloadBuilder builder = PayloadBuilder.newPayload();

		String title = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_TITLE);

		if (Validator.isNotNull(title)) {
			builder.alertTitle(title);
		}

		if (payloadJSONObject.has(PushNotificationsConstants.KEY_BADGE)) {
			builder.badge(
				payloadJSONObject.getInt(PushNotificationsConstants.KEY_BADGE));
		}

		String body = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_BODY);

		if (Validator.isNotNull(body)) {
			builder.alertBody(body);
		}

		String bodyLocalizedKey = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_BODY_LOCALIZED);

		if (Validator.isNotNull(bodyLocalizedKey)) {
			builder.localizedKey(bodyLocalizedKey);
		}

		JSONArray bodyLocalizedArgumentsJSONArray =
			payloadJSONObject.getJSONArray(
				PushNotificationsConstants.KEY_BODY_LOCALIZED_ARGUMENTS);

		if (bodyLocalizedArgumentsJSONArray != null) {
			List<String> localizedArguments = new ArrayList<>();

			for (int i = 0; i < bodyLocalizedArgumentsJSONArray.length(); i++) {
				localizedArguments.add(
					bodyLocalizedArgumentsJSONArray.getString(i));
			}

			builder.localizedArguments(localizedArguments);
		}

		boolean silent = payloadJSONObject.getBoolean(
			PushNotificationsConstants.KEY_SILENT);

		if (silent) {
			builder.instantDeliveryOrSilentNotification();
		}

		String sound = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_SOUND);

		if (Validator.isNotNull(sound)) {
			builder.sound(sound);
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

			builder.localizedTitleArguments(localizedArguments);
		}

		String titleLocalizedKey = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_TITLE_LOCALIZED);

		if (Validator.isNotNull(titleLocalizedKey)) {
			builder.localizedTitleKey(titleLocalizedKey);
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

		builder.customField(
			PushNotificationsConstants.KEY_PAYLOAD,
			newPayloadJSONObject.toString());

		return builder.build();
	}

	@Deactivate
	protected void deactivate() {
		_apnsService = null;
	}

	private InputStream _getCertificateInputStream(String certificatePath) {
		try {
			return new FileInputStream(certificatePath);
		}
		catch (FileNotFoundException fnfe) {
			ClassLoader classLoader =
				ApplePushNotificationsSender.class.getClassLoader();

			return classLoader.getResourceAsStream(certificatePath);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ApplePushNotificationsSender.class);

	private volatile ApnsService _apnsService;

	@Reference
	private MessageBus _messageBus;

}