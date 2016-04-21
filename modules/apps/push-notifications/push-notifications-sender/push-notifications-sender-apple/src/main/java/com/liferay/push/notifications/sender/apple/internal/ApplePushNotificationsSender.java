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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StreamUtil;
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
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Silvio Santos
 * @author Bruno Farache
 */
@Component(
	configurationPid = "com.liferay.push.notifications.sender.apple.internal.configuration.ApplePushNotificationsSenderConfiguration",
	immediate = true,
	property = {"platform=" + ApplePushNotificationsSender.PLATFORM}
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

		InputStream inputStream = null;

		try {
			try {
				inputStream = new FileInputStream(certificatePath);
			}
			catch (FileNotFoundException fnfe) {
				ClassLoader classLoader =
					ApplePushNotificationsSender.class.getClassLoader();

				inputStream = classLoader.getResourceAsStream(certificatePath);
			}

			if (inputStream == null) {
				throw new IllegalArgumentException(
					"Unable to find Apple certificate at " + certificatePath);
			}

			appleServiceBuilder.withCert(inputStream, certificatePassword);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}

		appleServiceBuilder.withDelegate(new AppleDelegate());

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

		builder.customField(
			PushNotificationsConstants.KEY_PAYLOAD,
			payloadJSONObject.toString());

		return builder.build();
	}

	@Deactivate
	protected void deactivate() {
		_apnsService = null;
	}

	private volatile ApnsService _apnsService;

}