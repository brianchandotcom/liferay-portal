/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.sender.firebase.internal;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.push.notifications.exception.PushNotificationsException;
import com.liferay.push.notifications.sender.PushNotificationsSender;
import com.liferay.push.notifications.sender.firebase.internal.configuration.FirebasePushNotificationsSenderConfiguration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 */
@Component(
	configurationPid = "com.liferay.push.notifications.sender.firebase.internal.configuration.FirebasePushNotificationsSenderConfiguration",
	property = "platform=" + FirebasePushNotificationsSender.PLATFORM,
	service = PushNotificationsSender.class
)
public class FirebasePushNotificationsSender
	implements PushNotificationsSender {

	public static final String AUTHORIZATION = "Authorization";

	public static final String BASE_GOOGLE_NOTIFICATIONS_API =
		"https://fcm.googleapis.com/fcm/notification";

	public static final String GOOGLE_GROUP_ID = StringUtil.randomString();

	public static final int OK_CODE = 200;

	public static final String PLATFORM = "firebase";

	@Override
	public void send(List<String> tokens, JSONObject payloadJSONObject)
		throws Exception {

		if (_sender == null) {
			throw new PushNotificationsException(
				"Firebase push notifications sender is not configured " +
					"properly");
		}

		_sender.send(_buildMessage(tokens, payloadJSONObject));
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties)
		throws PortalException {

		_firebasePushNotificationsSenderConfiguration =
			ConfigurableUtil.createConfigurable(
				FirebasePushNotificationsSenderConfiguration.class, properties);

		_projectNumber =
			_firebasePushNotificationsSenderConfiguration.projectNumber();

		_initGoogleCloudServices();
	}

			return;
		}

	@Deactivate
	protected void deactivate() {
		_googleCredentials = null;
	}

	@Deactivate
	protected void deactivate() {
		_sender = null;
	}

	private Message _buildMessage(
		List<String> tokens, JSONObject payloadJSONObject) {

		Message.Builder builder = new Message.Builder();

		boolean silent = payloadJSONObject.getBoolean(
			PushNotificationsConstants.KEY_SILENT);

		if (silent) {
			builder.contentAvailable(silent);
		}

		builder.notification(_buildNotification(payloadJSONObject));
		builder.to(tokens);

		JSONObject newPayloadJSONObject = _jsonFactory.createJSONObject();

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
			builder.data(
				HashMapBuilder.put(
					PushNotificationsConstants.KEY_PAYLOAD,
					newPayloadJSONObject.toString()
				).build());
		}

		return builder.build();
	}

	private Notification _buildNotification(JSONObject payloadJSONObject) {
		Notification.Builder builder = new Notification.Builder();

		if (payloadJSONObject.has(PushNotificationsConstants.KEY_BADGE)) {
			builder.badge(
				payloadJSONObject.getInt(PushNotificationsConstants.KEY_BADGE));
		}

	private void _initGoogleCloudServices() throws PortalException {
		String serviceAccountKey =
			_firebasePushNotificationsSenderConfiguration.serviceAccountKey();

		try {
			if (Validator.isBlank(serviceAccountKey)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Using application default credentials because " +
							"service account key was not set");
				}

				_googleCredentials =
					ServiceAccountCredentials.getApplicationDefault();
			}
			else {
				_googleCredentials = ServiceAccountCredentials.fromStream(
					new ByteArrayInputStream(serviceAccountKey.getBytes())
				).createScoped(
					Arrays.asList(
						"https://www.googleapis.com/auth/firebase.messaging")
				);
			}
		}
		catch (IOException ioException) {
			throw new PortalException(
				"Unable to authenticate with GCS", ioException);
		}
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

	private static final Log _log = LogFactoryUtil.getLog(
		FirebasePushNotificationsSender.class);

	private volatile FirebasePushNotificationsSenderConfiguration
		_firebasePushNotificationsSenderConfiguration;
	private GoogleCredentials _googleCredentials;

	@Reference
	private HttpUtil _httpUtil;

	private String _projectNumber;

}