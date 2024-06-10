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
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.push.notifications.constants.PushNotificationsConstants;
import com.liferay.push.notifications.exception.PushNotificationsException;
import com.liferay.push.notifications.sender.PushNotificationsSender;
import com.liferay.push.notifications.sender.firebase.internal.configuration.FirebasePushNotificationsSenderConfiguration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	public static final String BASE_GOOGLE_NOTIFICATIONS_API =
		"https://fcm.googleapis.com/fcm/notification";

	public static final String GOOGLE_GROUP_ID = StringUtil.randomString();

	public static final int OK_CODE = 200;

	public static final String PLATFORM = "firebase";

	@Override
	public void send(List<String> tokens, JSONObject payloadJSONObject)
		throws IOException, JSONException, PushNotificationsException {

		if (_googleCredentials == null) {
			throw new PushNotificationsException(
				"Firebase push notifications sender is not configured " +
					"properly");
		}

		String notificationKey = tokens.get(0);
		String accessToken = _getAccessToken();

		if (tokens.size() > 1) {
			notificationKey = _getNotificationKey(accessToken);

			if (Validator.isNull(notificationKey)) {
				notificationKey = _createNotificationGroup(accessToken, tokens);
			}
		}

		_send(accessToken, buildMessage(payloadJSONObject, notificationKey));

		if (tokens.size() > 1) {
			_removeNotificationGroup(accessToken, tokens, notificationKey);
		}
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

	protected JSONObject buildMessage(
		JSONObject payloadJSONObject, String notificationKey) {

		JSONObject messageContent = JSONUtil.put(
			"android", _buildAndroidData(payloadJSONObject)
		).put(
			"data", _buildMessagePayload(payloadJSONObject)
		).put(
			"token", notificationKey
		);

		return JSONUtil.put("message", messageContent);
	}

	@Deactivate
	protected void deactivate() {
		_googleCredentials = null;
	}

	private JSONObject _buildAndroidData(JSONObject payloadJSONObject) {
		return JSONUtil.put(
			"notification",
			JSONUtil.put(
				"body",
				payloadJSONObject.getString(PushNotificationsConstants.KEY_BODY)
			).put(
				"body_loc_args",
				payloadJSONObject.getJSONArray(
					PushNotificationsConstants.KEY_BODY_LOCALIZED_ARGUMENTS)
			).put(
				"body_loc_key",
				payloadJSONObject.getString(
					PushNotificationsConstants.KEY_BODY_LOCALIZED)
			).put(
				"notification_count",
				payloadJSONObject.getInt(PushNotificationsConstants.KEY_BADGE)
			).put(
				"sound",
				payloadJSONObject.getString(
					PushNotificationsConstants.KEY_SOUND)
			).put(
				"title",
				payloadJSONObject.getString(
					PushNotificationsConstants.KEY_TITLE)
			).put(
				"title_loc_args",
				payloadJSONObject.getJSONArray(
					PushNotificationsConstants.KEY_TITLE_LOCALIZED_ARGUMENTS)
			).put(
				"title_loc_key",
				payloadJSONObject.getString(
					PushNotificationsConstants.KEY_TITLE_LOCALIZED)
			));
	}

	private String _buildMessagePayload(JSONObject payloadJSONObject) {
		Iterator<String> keys = payloadJSONObject.keys();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		while (keys.hasNext()) {
			String key = keys.next();

			if (!_notificationKeys.contains(key)) {
				jsonObject.put(key, payloadJSONObject.get(key));
			}
		}

		return JSONUtil.put(
			"payload", jsonObject
		).toString();
	}

	private String _createNotificationGroup(
			String authorizationToken, List<String> tokens)
		throws IOException, JSONException, PushNotificationsException {

		Http.Options options = new Http.Options();

		options.addHeader("access_token_auth", "true");
		options.addHeader("project_id", _projectNumber);
		options.addHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + authorizationToken);
		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		options.setBody(
			JSONUtil.put(
				"notification_key_name", GOOGLE_GROUP_ID
			).put(
				"operation", "create"
			).put(
				"registration_ids", tokens
			).toString(),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation(BASE_GOOGLE_NOTIFICATIONS_API);
		options.setPost(true);

		String responseString = _httpUtil.URLtoString(options);

		Http.Response optionsResponse = options.getResponse();

		if (optionsResponse.getResponseCode() != OK_CODE) {
			throw new PushNotificationsException(
				"Unable to create a notification group");
		}

		JSONObject response = JSONFactoryUtil.createJSONObject(responseString);

		return response.getString("notification_key");
	}

	private String _getAccessToken() throws IOException {
		_googleCredentials.refresh();

		return _googleCredentials.getAccessToken(
		).getTokenValue();
	}

	private String _getNotificationKey(String authorizationToken)
		throws IOException, JSONException {

		Http.Options options = new Http.Options();

		options.addHeader("access_token_auth", "true");
		options.addHeader("project_id", _projectNumber);
		options.addHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + authorizationToken);
		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.setPost(false);
		options.setLocation(
			StringBundler.concat(
				BASE_GOOGLE_NOTIFICATIONS_API, "?notification_key_name=",
				GOOGLE_GROUP_ID));

		String responseString = _httpUtil.URLtoString(options);

		JSONObject response = JSONFactoryUtil.createJSONObject(responseString);

		return response.getString("notification_key");
	}

	private String _getProjectId() {
		ServiceAccountCredentials serviceAccountCredentials =
			(ServiceAccountCredentials)_googleCredentials;

		return serviceAccountCredentials.getProjectId();
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

	private void _removeNotificationGroup(
			String authorizationToken, List<String> tokens,
			String notificationKey)
		throws IOException, PushNotificationsException {

		Http.Options options = new Http.Options();

		options.addHeader("access_token_auth", "true");
		options.addHeader("project_id", _projectNumber);
		options.addHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + authorizationToken);
		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		options.setBody(
			JSONUtil.put(
				"notification_key", notificationKey
			).put(
				"notification_key_name", GOOGLE_GROUP_ID
			).put(
				"operation", "remove"
			).put(
				"registration_ids", tokens
			).toString(),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(BASE_GOOGLE_NOTIFICATIONS_API);
		options.setPost(true);

		_httpUtil.URLtoString(options);

		Http.Response optionsResponse = options.getResponse();

		if (optionsResponse.getResponseCode() != OK_CODE) {
			throw new PushNotificationsException(
				"Unable to remove notification group");
		}
	}

	private void _send(String accessToken, JSONObject message)
		throws IOException, PushNotificationsException {

		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		options.setBody(
			message.toString(), ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation(
			"https://fcm.googleapis.com/v1/projects/" + _getProjectId() +
				"/messages:send");
		options.setPost(true);

		_httpUtil.URLtoString(options);

		Http.Response optionsResponse = options.getResponse();

		if (optionsResponse.getResponseCode() != OK_CODE) {
			throw new PushNotificationsException(
				"Unable to send the push notification");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FirebasePushNotificationsSender.class);

	private static final Set<String> _notificationKeys = SetUtil.fromArray(
		PushNotificationsConstants.KEY_BADGE,
		PushNotificationsConstants.KEY_BODY,
		PushNotificationsConstants.KEY_BODY_LOCALIZED,
		PushNotificationsConstants.KEY_BODY_LOCALIZED_ARGUMENTS,
		PushNotificationsConstants.KEY_SOUND,
		PushNotificationsConstants.KEY_SILENT);

	private volatile FirebasePushNotificationsSenderConfiguration
		_firebasePushNotificationsSenderConfiguration;
	private GoogleCredentials _googleCredentials;

	@Reference
	private HttpUtil _httpUtil;

	private String _projectNumber;

}