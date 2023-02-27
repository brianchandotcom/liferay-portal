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

package com.liferay.jenkins.plugin.events;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsWebHook {

	public JenkinsWebHook() {
		_initializeEventTypes();
	}

	public JenkinsWebHook(JSONObject jsonObject) {
		buildCompleted = jsonObject.getBoolean("buildCompleted");
		buildStarted = jsonObject.getBoolean("buildStarted");
		computerBusy = jsonObject.getBoolean("computerBusy");
		computerIdle = jsonObject.getBoolean("computerIdle");
		computerOffline = jsonObject.getBoolean("computerOffline");
		computerOnline = jsonObject.getBoolean("computerOnline");
		computerTemporarilyOffline = jsonObject.getBoolean(
			"computerTemporarilyOffline");
		computerTemporarilyOnline = jsonObject.getBoolean(
			"computerTemporarilyOnline");
		url = jsonObject.getString("url");

		_initializeEventTypes();
	}

	public boolean containsEventTrigger(EventTrigger eventTrigger) {
		if (_eventTriggers.contains(eventTrigger)) {
			return true;
		}

		return false;
	}

	public URL getURL() {
		try {
			return new URL(url);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	public void publish(String payload, EventTrigger eventTrigger) {
		if (!_eventTriggers.contains(eventTrigger)) {
			return;
		}

		HttpURLConnection httpURLConnection = null;

		try {
			URL url = getURL();

			httpURLConnection = (HttpURLConnection)url.openConnection();

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty(
				"Authorization", _getAuthorizationHeader());

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				httpURLConnection.getOutputStream());

			outputStreamWriter.write(payload);

			outputStreamWriter.flush();
			outputStreamWriter.close();

			try (InputStream errorInputStream =
					httpURLConnection.getErrorStream()) {

				if (errorInputStream != null) {
					ByteArrayOutputStream byteArrayOutputStream =
						new ByteArrayOutputStream();

					byte[] bytes = new byte[1024];

					int b;

					while ((b = errorInputStream.read(bytes)) != -1) {
						byteArrayOutputStream.write(bytes, 0, b);
					}

					throw new RuntimeException(
						byteArrayOutputStream.toString("UTF-8"));
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			try (InputStream inputStream = httpURLConnection.getInputStream()) {
				if (inputStream != null) {
					ByteArrayOutputStream byteArrayOutputStream =
						new ByteArrayOutputStream();

					byte[] bytes = new byte[1024];

					int b;

					while ((b = inputStream.read(bytes)) != -1) {
						byteArrayOutputStream.write(bytes, 0, b);
					}
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
	}

	public boolean buildCompleted;
	public boolean buildStarted;
	public boolean computerBusy;
	public boolean computerIdle;
	public boolean computerOffline;
	public boolean computerOnline;
	public boolean computerTemporarilyOffline;
	public boolean computerTemporarilyOnline;
	public String url;

	public enum EventTrigger {

		BUILD_COMPLETED, BUILD_STARTED, COMPUTER_BUSY, COMPUTER_IDLE,
		COMPUTER_OFFLINE, COMPUTER_ONLINE, COMPUTER_TEMPORARILY_OFFLINE,
		COMPUTER_TEMPORARILY_ONLINE

	}

	private String _getAuthorizationHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("Basic ");

		String userNamePassword = "admin:admin";

		Base64.Encoder base64Encoder = Base64.getEncoder();

		sb.append(base64Encoder.encodeToString(userNamePassword.getBytes()));

		return sb.toString();
	}

	private void _initializeEventTypes() {
		_eventTriggers.clear();

		if (buildCompleted) {
			_eventTriggers.add(EventTrigger.BUILD_COMPLETED);
		}

		if (buildStarted) {
			_eventTriggers.add(EventTrigger.BUILD_STARTED);
		}

		if (computerBusy) {
			_eventTriggers.add(EventTrigger.COMPUTER_BUSY);
		}

		if (computerIdle) {
			_eventTriggers.add(EventTrigger.COMPUTER_IDLE);
		}

		if (computerOffline) {
			_eventTriggers.add(EventTrigger.COMPUTER_OFFLINE);
		}

		if (computerOnline) {
			_eventTriggers.add(EventTrigger.COMPUTER_ONLINE);
		}

		if (computerTemporarilyOffline) {
			_eventTriggers.add(EventTrigger.COMPUTER_TEMPORARILY_OFFLINE);
		}

		if (computerTemporarilyOnline) {
			_eventTriggers.add(EventTrigger.COMPUTER_TEMPORARILY_ONLINE);
		}
	}

	private final List<EventTrigger> _eventTriggers = new ArrayList<>();

}