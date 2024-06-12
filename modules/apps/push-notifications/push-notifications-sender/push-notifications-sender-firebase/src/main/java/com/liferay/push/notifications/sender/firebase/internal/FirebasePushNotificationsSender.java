/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.sender.firebase.internal;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.push.notifications.constants.PushNotificationsConstants;
import com.liferay.push.notifications.exception.PushNotificationsException;
import com.liferay.push.notifications.sender.PushNotificationsSender;
import com.liferay.push.notifications.sender.firebase.internal.configuration.FirebasePushNotificationsSenderConfiguration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.ArrayList;
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

	public static final int OK_CODE = 200;

	public static final String PLATFORM = "firebase";

	@Override
	public void send(List<String> tokens, JSONObject payloadJSONObject)
		throws Exception {

		if (_googleCredentials == null) {
			throw new PushNotificationsException(
				"Firebase push notifications sender is not configured " +
					"properly");
		}

		String accessToken = _getAccessToken();

		if (tokens.size() > 1) {
			DeviceGroup deviceGroup = _createDeviceGroup(accessToken, tokens);

			boolean success = false;

			try {
				_send(
					accessToken,
					_buildMessage(deviceGroup.getId(), payloadJSONObject));

				success = true;
			}
			finally {
				_removeDeviceGroup(accessToken, deviceGroup, success, tokens);
			}
		}
		else {
			_send(accessToken, _buildMessage(tokens.get(0), payloadJSONObject));
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties)
		throws PortalException {

		_firebasePushNotificationsSenderConfiguration =
			ConfigurableUtil.createConfigurable(
				FirebasePushNotificationsSenderConfiguration.class, properties);

		if (Validator.isNull(
				_firebasePushNotificationsSenderConfiguration.
					projectNumber())) {

			return;
		}

		_initGoogleCloudServices();
	}

	@Deactivate
	protected void deactivate() {
		_googleCredentials = null;
	}

	private JSONObject _buildAndroidData(JSONObject payloadJSONObject) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String body = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_BODY);

		if (Validator.isNotNull(body)) {
			jsonObject.put("body", body);
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

			jsonObject.put("body_loc_args", bodyLocalizedArguments);
		}

		String bodyLocalizedKey = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_BODY_LOCALIZED);

		if (Validator.isNotNull(bodyLocalizedKey)) {
			jsonObject.put("body_loc_key", bodyLocalizedKey);
		}

		if (payloadJSONObject.has(PushNotificationsConstants.KEY_BADGE)) {
			jsonObject.put(
				"notification_count",
				payloadJSONObject.getInt(PushNotificationsConstants.KEY_BADGE));
		}

		String sound = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_SOUND);

		if (Validator.isNotNull(sound)) {
			jsonObject.put("sound", sound);
		}

		String title = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_TITLE);

		if (Validator.isNotNull(title)) {
			jsonObject.put("title", title);
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

			jsonObject.put("title_loc_args", localizedArguments);
		}

		String titleLocalizedKey = payloadJSONObject.getString(
			PushNotificationsConstants.KEY_TITLE_LOCALIZED);

		if (Validator.isNotNull(titleLocalizedKey)) {
			jsonObject.put("title_loc_key", titleLocalizedKey);
		}

		return JSONUtil.put("notification", jsonObject);
	}

	private JSONObject _buildMessage(
		String notificationKey, JSONObject payloadJSONObject) {

		JSONObject messageContent = JSONUtil.put(
			"android", _buildAndroidData(payloadJSONObject)
		).put(
			"data", _buildMessagePayload(payloadJSONObject)
		).put(
			"token", notificationKey
		);

		return JSONUtil.put("message", messageContent);
	}

	private JSONObject _buildMessagePayload(JSONObject payloadJSONObject) {
		Iterator<String> keys = payloadJSONObject.keys();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		while (keys.hasNext()) {
			String key = keys.next();

			if (!_notificationKeys.contains(key)) {
				jsonObject.put(key, payloadJSONObject.get(key));
			}
		}

		return JSONUtil.put("payload", jsonObject.toString());
	}

	private DeviceGroup _createDeviceGroup(
			String authorizationToken, List<String> tokens)
		throws Exception {

		String name = StringUtil.randomString();

		Http.Options options = new Http.Options();

		options.addHeader("access_token_auth", "true");
		options.addHeader("project_id", _projectNumber);
		options.addHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + authorizationToken);
		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		options.setBody(
			JSONUtil.put(
				"notification_key_name", name
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

		return new DeviceGroup(response.getString("notification_key"), name);
	}

	private String _getAccessToken() throws Exception {
		_googleCredentials.refresh();

		return _googleCredentials.getAccessToken(
		).getTokenValue();
	}

	private String _getProjectId() {
		ServiceAccountCredentials serviceAccountCredentials =
			(ServiceAccountCredentials)_googleCredentials;

		return serviceAccountCredentials.getProjectId();
	}

	private void _initGoogleCloudServices() throws PortalException {
		_projectNumber =
			_firebasePushNotificationsSenderConfiguration.projectNumber();

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
				"Unable to authenticate with Firebase", ioException);
		}
	}

	private void _removeDeviceGroup(
			String authorizationToken, DeviceGroup deviceGroup,
			boolean throwException, List<String> tokens)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader("access_token_auth", "true");
		options.addHeader("project_id", _projectNumber);
		options.addHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + authorizationToken);
		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		options.setBody(
			JSONUtil.put(
				"notification_key", deviceGroup.getId()
			).put(
				"notification_key_name", deviceGroup.getName()
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
			String errorMessage = StringBundler.concat(
				"Unable to remove notification group with notification_key: ",
				deviceGroup.getId(), " and notification_key_name: ",
				deviceGroup.getName());

			if (throwException) {
				throw new PushNotificationsException(errorMessage);
			}

			_log.error(errorMessage);
		}
	}

	private void _send(String accessToken, JSONObject message)
		throws Exception {

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

	private class DeviceGroup {

		public DeviceGroup(String id, String name) {
			_id = id;
			_name = name;
		}

		public String getId() {
			return _id;
		}

		public String getName() {
			return _name;
		}

		private final String _id;
		private final String _name;

	}

}