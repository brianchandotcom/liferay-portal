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

package com.liferay.powwow.provider.zoom;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.PowwowMeetingConstants;
import com.liferay.powwow.model.PowwowParticipantConstants;
import com.liferay.powwow.model.PowwowServer;
import com.liferay.powwow.provider.BasePowwowServiceProvider;
import com.liferay.powwow.service.PowwowMeetingLocalServiceUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

/**
 * @author Marco Calderon
 */
public class ZoomPowwowServiceProvider extends BasePowwowServiceProvider {

	@Override
	public int getAddPowwowMeetingStrategy() {
		return ADD_POWWOW_MEETING_STRATEGY_EAGER;
	}

	@Override
	public List<String> getBrandingFeatures() {
		if (_brandingFeatures != null) {
			return _brandingFeatures;
		}

		List<String> brandingFeatures = new ArrayList<>();

		brandingFeatures.add("fastest-performance");
		brandingFeatures.add("highest-quality");
		brandingFeatures.add("trusted-security");
		brandingFeatures.add("supports-windows-and-mac");
		brandingFeatures.add(
			"includes-audio-video-chat-screen-sharing-and-native-ios-android-" +
				"support");

		_brandingFeatures = brandingFeatures;

		return _brandingFeatures;
	}

	@Override
	public String getBrandingLabel() {
		return "preferred-solution";
	}

	@Override
	public Map<String, String> getIndexFields(PowwowMeeting powwowMeeting) {
		Map<String, Serializable> providerTypeMetadata =
			powwowMeeting.getProviderTypeMetadataMap();

		return HashMapBuilder.put(
			"zoomHostId", String.valueOf(providerTypeMetadata.get("host_id"))
		).put(
			"zoomMeetingId", String.valueOf(providerTypeMetadata.get("id"))
		).build();
	}

	@Override
	public String getJoinByPhoneAccessCodeLabel() {
		return "meeting-id";
	}

	@Override
	public List<String> getJoinByPhoneDefaultNumbers() {
		if (_joinByPhoneDefaultNumbers.isEmpty()) {
			getJoinByPhoneNumbers();
		}

		return _joinByPhoneDefaultNumbers;
	}

	@Override
	public Map<String, List<String>> getJoinByPhoneInternationalNumbers() {
		if (_joinByPhoneInternationalNumbers.isEmpty()) {
			getJoinByPhoneNumbers();
		}

		return _joinByPhoneInternationalNumbers;
	}

	@Override
	public String getPowwowServiceProviderKey() {
		return "zoom";
	}

	@Override
	public String getPowwowServiceProviderName() {
		return "Zoom";
	}

	@Override
	public boolean isFieldAPIKeyRequired() {
		return true;
	}

	@Override
	public boolean isFieldSecretRequired() {
		return true;
	}

	@Override
	public boolean isServerActive(PowwowServer powwowServer) {
		try {
			JSONArray jsonArray = getUsersJSONArray(powwowServer);

			if (jsonArray != null) {
				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	public boolean isSupportsJoinByPhone() {
		return true;
	}

	@Override
	public boolean isSupportsOptionAutoStartVideo() {
		return true;
	}

	@Override
	public boolean isSupportsOptionPassword() {
		return true;
	}

	@Override
	protected Map<String, Serializable> addPowwowMeeting(
		User user, PowwowServer powwowServer, long powwowMeetingId, String name,
		Map<String, String> options) {

		String hostId = getHostId(user, powwowServer);

		Map<String, String> parameterMap = HashMapBuilder.put(
			"host_id", hostId
		).put(
			"option_host_video",
			options.get(PowwowMeetingConstants.OPTION_AUTO_START_VIDEO)
		).put(
			"option_participants_video",
			options.get(PowwowMeetingConstants.OPTION_AUTO_START_VIDEO)
		).build();

		String password = options.get(PowwowMeetingConstants.OPTION_PASSWORD);

		if (Validator.isNotNull(password)) {
			parameterMap.put("password", password);
		}

		parameterMap.put("topic", name);
		parameterMap.put("type", _MEETING_TYPE_RECURRING);

		Map<String, Serializable> providerTypeMetadataMap =
			HashMapBuilder.<String, Serializable>put(
				"host_id", hostId
			).put(
				"id",
				() -> {
					JSONObject responseJSONObject = execute(
						powwowServer, "meeting", "create", parameterMap);

					return responseJSONObject.getString("id");
				}
			).put(
				"option_host_video",
				options.get(PowwowMeetingConstants.OPTION_AUTO_START_VIDEO)
			).put(
				"option_participants_video",
				options.get(PowwowMeetingConstants.OPTION_AUTO_START_VIDEO)
			).build();

		if (Validator.isNotNull(password)) {
			providerTypeMetadataMap.put(
				"password",
				options.get(PowwowMeetingConstants.OPTION_PASSWORD));
		}

		return providerTypeMetadataMap;
	}

	protected void cleanUpZoomHosts(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting) {

		int powwowMeetingsCount =
			PowwowMeetingLocalServiceUtil.getUserPowwowMeetingsCount(
				powwowMeeting.getUserId(),
				PowwowMeetingConstants.STATUS_IN_PROGRESS);

		if (powwowMeetingsCount <= 1) {
			Map<String, Serializable> providerTypeMetadataMap =
				powwowMeeting.getProviderTypeMetadataMap();

			String hostId = String.valueOf(
				providerTypeMetadataMap.get("host_id"));

			deleteZoomHost(powwowServer, hostId);
		}
	}

	protected String createZoomHost(User user, PowwowServer powwowServer) {
		Map<String, String> parameterMap = HashMapBuilder.put(
			"dept", _DEPT_API
		).put(
			"email", user.getEmailAddress()
		).put(
			"first_name", user.getFirstName()
		).put(
			"last_name", user.getLastName()
		).put(
			"type", String.valueOf(_USER_TYPE_PRO)
		).build();

		JSONObject responseJSONObject = execute(
			powwowServer, "user", "custcreate", parameterMap);

		return responseJSONObject.getString("id");
	}

	@Override
	protected boolean deletePowwowMeeting(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting) {

		JSONObject responseJSONObject = execute(
			powwowServer, "meeting", "delete", getParameterMap(powwowMeeting));

		String deletedAt = responseJSONObject.getString("deleted_at");

		if (deletedAt.equals(StringPool.BLANK)) {
			return false;
		}

		cleanUpZoomHosts(powwowServer, powwowMeeting);

		return true;
	}

	protected void deleteZoomHost(PowwowServer powwowServer, String hostId) {
		Map<String, String> params = HashMapBuilder.put(
			"id", hostId
		).build();

		JSONObject responseJSONObject = execute(
			powwowServer, "user", "get", params);

		String dept = responseJSONObject.getString("dept");

		if (!dept.equals(_DEPT_API)) {
			return;
		}

		responseJSONObject = execute(powwowServer, "user", "delete", params);

		String deletedAt = responseJSONObject.getString("deleted_at");

		if (deletedAt.equals(StringPool.BLANK)) {
			throw new SystemException();
		}
	}

	@Override
	protected boolean endPowwowMeeting(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting) {

		JSONObject responseJSONObject = execute(
			powwowServer, "meeting", "end", getParameterMap(powwowMeeting));

		String endedAt = responseJSONObject.getString("ended_at");

		if (endedAt.equals(StringPool.BLANK)) {
			return false;
		}

		return true;
	}

	protected JSONObject execute(
		PowwowServer powwowServer, String resource, String action,
		Map<String, String> parameterMap) {

		return execute(powwowServer, resource, action, parameterMap, true);
	}

	protected JSONObject execute(
		PowwowServer powwowServer, String resource, String action,
		Map<String, String> parameterMap, boolean throwError) {

		Http.Options options = new Http.Options();

		StringBundler sb = new StringBundler(4);

		sb.append("https://api.zoom.us/v1/");
		sb.append(resource);
		sb.append(StringPool.SLASH);
		sb.append(action);

		String location = sb.toString();

		options.setLocation(location);

		Map<String, String> parts = new HashMap<>();

		if (parameterMap != null) {
			parts.putAll(parameterMap);
		}

		parts.put("api_key", powwowServer.getApiKey());
		parts.put("api_secret", powwowServer.getSecret());

		options.setParts(parts);

		options.setPost(true);

		try {
			long elapsedTime = System.currentTimeMillis() - _lastAPICallTime;

			if (elapsedTime < Time.SECOND) {
				if (_apiCallCount >= 10) {
					try {
						Thread.sleep(Time.SECOND + 1 - elapsedTime);

						_apiCallCount = 1;
					}
					catch (InterruptedException ie) {
					}
				}

				_apiCallCount++;
			}
			else {
				_apiCallCount = 1;
			}

			if (_apiCallCount == 1) {
				_lastAPICallTime = System.currentTimeMillis();
			}

			String response = sendRequest(options);

			JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
				response);

			JSONObject errorJSONObject = responseJSONObject.getJSONObject(
				"error");

			if (throwError && (errorJSONObject != null)) {
				throw new SystemException(
					"Unable to complete request: " + errorJSONObject);
			}

			return responseJSONObject;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected String getHostId(User user, PowwowServer powwowServer) {
		JSONArray usersJSONArray = getUsersJSONArray(powwowServer);

		if (usersJSONArray == null) {
			return null;
		}

		for (int i = 0; i < usersJSONArray.length(); i++) {
			JSONObject userJSONObject = usersJSONArray.getJSONObject(i);

			String emailAddress = user.getEmailAddress();

			if (emailAddress.equals(userJSONObject.getString("email"))) {
				return userJSONObject.getString("id");
			}
		}

		return createZoomHost(user, powwowServer);
	}

	@Override
	protected long getJoinByPhoneAccessCode(PowwowMeeting powwowMeeting) {
		Map<String, Serializable> providerTypeMetadataMap =
			powwowMeeting.getProviderTypeMetadataMap();

		String accessCode = String.valueOf(providerTypeMetadataMap.get("id"));

		return GetterUtil.getLong(accessCode);
	}

	protected void getJoinByPhoneNumbers() {
		try {
			Source source = new Source(
				HttpUtil.URLtoString("http://zoom.us/zoomconference"));

			List<Element> countryElements = source.getAllElementsByClass(
				"country");
			List<Element> numberElements = source.getAllElementsByClass(
				"vcl_number");

			if (countryElements.size() != numberElements.size()) {
				return;
			}

			for (int i = 0; i < countryElements.size(); i++) {
				Element countryElement = countryElements.get(i);

				String country = HtmlUtil.extractText(
					countryElement.toString());

				Element numberElement = numberElements.get(i);

				String number = HtmlUtil.extractText(numberElement.toString());

				if (Objects.equals(country, "United States")) {
					_joinByPhoneDefaultNumbers.add(number);
				}

				List<String> numbers = _joinByPhoneInternationalNumbers.get(
					country);

				if (numbers == null) {
					numbers = new ArrayList<>();
				}

				numbers.add(number);

				_joinByPhoneInternationalNumbers.put(country, numbers);
			}
		}
		catch (Exception e) {
		}
	}

	@Override
	protected String getJoinPowwowMeetingURL(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting, String name,
		int type) {

		JSONObject responseJSONObject = execute(
			powwowServer, "meeting", "get", getParameterMap(powwowMeeting));

		String joinPowwowMeetingURL = responseJSONObject.getString("join_url");

		if (type == PowwowParticipantConstants.TYPE_HOST) {
			joinPowwowMeetingURL = responseJSONObject.getString("start_url");
		}

		return joinPowwowMeetingURL;
	}

	@Override
	protected boolean getOptionAutoStartVideo(PowwowMeeting powwowMeeting) {
		Map<String, Serializable> providerTypeMetadataMap =
			powwowMeeting.getProviderTypeMetadataMap();

		return GetterUtil.getBoolean(
			providerTypeMetadataMap.get("option_host_video"));
	}

	@Override
	protected String getOptionPassword(PowwowMeeting powwowMeeting) {
		Map<String, Serializable> providerTypeMetadataMap =
			powwowMeeting.getProviderTypeMetadataMap();

		return GetterUtil.getString(providerTypeMetadataMap.get("password"));
	}

	protected Map<String, String> getParameterMap(PowwowMeeting powwowMeeting) {
		Map<String, Serializable> providerTypeMetadataMap =
			powwowMeeting.getProviderTypeMetadataMap();

		return HashMapBuilder.put(
			"host_id", String.valueOf(providerTypeMetadataMap.get("host_id"))
		).put(
			"id", String.valueOf(providerTypeMetadataMap.get("id"))
		).build();
	}

	protected JSONArray getUsersJSONArray(PowwowServer powwowServer) {
		JSONObject responseJSONObject = execute(
			powwowServer, "user", "list", null);

		return responseJSONObject.getJSONArray("users");
	}

	protected JSONObject getZoomMeetingJSONObject(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting) {

		JSONObject responseJSONObject = execute(
			powwowServer, "meeting", "get", getParameterMap(powwowMeeting),
			false);

		JSONObject errorJSONObject = responseJSONObject.getJSONObject("error");

		if (errorJSONObject != null) {
			int code = errorJSONObject.getInt("code");

			if (code == _ERROR_CODE_MEETING_NOT_FOUND) {
				return null;
			}

			throw new SystemException(
				"Unable to retrieve Zoom meeting: " + errorJSONObject);
		}

		if (!responseJSONObject.has("created_at")) {
			throw new SystemException(
				"Invalid response from Zoom server: " + responseJSONObject);
		}

		String createdAt = responseJSONObject.getString("created_at");

		if (createdAt.equals(StringPool.BLANK)) {
			return null;
		}

		return responseJSONObject;
	}

	@Override
	protected boolean isPowwowMeetingCreated(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting) {

		JSONObject zoomMeetingJSONObject = getZoomMeetingJSONObject(
			powwowServer, powwowMeeting);

		if (zoomMeetingJSONObject == null) {
			return false;
		}

		return true;
	}

	@Override
	protected boolean isPowwowMeetingRunning(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting) {

		try {
			JSONObject zoomMeetingJSONObject = getZoomMeetingJSONObject(
				powwowServer, powwowMeeting);

			if (zoomMeetingJSONObject == null) {
				return false;
			}

			int status = zoomMeetingJSONObject.getInt("status");

			if (status == _MEETING_STATUS_IN_PROGRESS) {
				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	protected Map<String, Serializable> updatePowwowMeeting(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting, String name,
		User user, Map<String, String> options) {

		Map<String, Serializable> providerTypeMetadataMap =
			powwowMeeting.getProviderTypeMetadataMap();

		Map<String, String> parameterMap = HashMapBuilder.put(
			"host_id", getHostId(user, powwowServer)
		).put(
			"id", String.valueOf(providerTypeMetadataMap.get("id"))
		).put(
			"option_host_video",
			options.get(PowwowMeetingConstants.OPTION_AUTO_START_VIDEO)
		).put(
			"option_participants_video",
			options.get(PowwowMeetingConstants.OPTION_AUTO_START_VIDEO)
		).build();

		String password = options.get(PowwowMeetingConstants.OPTION_PASSWORD);

		if (Validator.isNotNull(password)) {
			parameterMap.put("password", password);
		}

		parameterMap.put("topic", name);
		parameterMap.put("type", String.valueOf(_MEETING_TYPE_RECURRING));

		execute(powwowServer, "meeting", "update", parameterMap);

		providerTypeMetadataMap.put(
			"option_host_video",
			options.get(PowwowMeetingConstants.OPTION_AUTO_START_VIDEO));
		providerTypeMetadataMap.put(
			"option_participants_video",
			options.get(PowwowMeetingConstants.OPTION_AUTO_START_VIDEO));

		if (Validator.isNull(password)) {
			providerTypeMetadataMap.remove("password");
		}
		else {
			providerTypeMetadataMap.put(
				"password",
				options.get(PowwowMeetingConstants.OPTION_PASSWORD));
		}

		return providerTypeMetadataMap;
	}

	private static final String _DEPT_API = "API";

	private static final int _ERROR_CODE_MEETING_NOT_FOUND = 3001;

	private static final int _MEETING_STATUS_IN_PROGRESS = 1;

	private static final String _MEETING_TYPE_RECURRING = "3";

	private static final int _USER_TYPE_PRO = 2;

	private static final Log _log = LogFactoryUtil.getLog(
		ZoomPowwowServiceProvider.class);

	private static int _apiCallCount;
	private static long _lastAPICallTime = System.currentTimeMillis();

	private List<String> _brandingFeatures;
	private final List<String> _joinByPhoneDefaultNumbers = new ArrayList<>();
	private final Map<String, List<String>> _joinByPhoneInternationalNumbers =
		new TreeMap<>();

}