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

package com.liferay.jenkins.plugin.events.publisher;

import com.liferay.jenkins.plugin.events.jms.JMSConnection;
import com.liferay.jenkins.plugin.events.jms.JMSFactory;
import com.liferay.jenkins.plugin.events.jms.JMSQueue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsPublisher {

	public JenkinsPublisher() {
		_initializeEventTypes();
	}

	public JenkinsPublisher(JSONObject jsonObject) {
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

		jmsRequest = jsonObject.has("jmsRequest");

		if (jmsRequest) {
			JSONObject jmsRequestJSONObject = jsonObject.getJSONObject(
				"jmsRequest");

			inboundJMSQueueName = jmsRequestJSONObject.optString(
				"inboundJMSQueueName");
			outboundJMSQueueName = jmsRequestJSONObject.optString(
				"outboundJMSQueueName");
		}

		queueItemEnterBlocked = jsonObject.getBoolean("queueItemEnterBlocked");
		queueItemEnterBuildable = jsonObject.getBoolean(
			"queueItemEnterBuildable");
		queueItemEnterWaiting = jsonObject.getBoolean("queueItemEnterWaiting");
		queueItemLeaveBlocked = jsonObject.getBoolean("queueItemLeaveBlocked");
		queueItemLeaveBuildable = jsonObject.getBoolean(
			"queueItemLeaveBuildable");
		queueItemLeaveWaiting = jsonObject.getBoolean("queueItemLeaveWaiting");
		queueItemLeft = jsonObject.getBoolean("queueItemLeft");
		url = jsonObject.getString("url");
		userName = jsonObject.getString("userName");
		userPassword = jsonObject.getString("userPassword");

		_initializeEventTypes();
	}

	public boolean containsEventTrigger(EventTrigger eventTrigger) {
		if (eventTrigger == null) {
			return false;
		}

		if (_eventTriggers.contains(eventTrigger)) {
			return true;
		}

		return false;
	}

	public void publish(String payload, EventTrigger eventTrigger) {
		if (!_eventTriggers.contains(eventTrigger)) {
			return;
		}

		if (jmsRequest) {
			_publishJMS(payload, eventTrigger);

			return;
		}

		_publishHttp(payload, eventTrigger);
	}

	public boolean buildCompleted;
	public boolean buildStarted;
	public boolean computerBusy;
	public boolean computerIdle;
	public boolean computerOffline;
	public boolean computerOnline;
	public boolean computerTemporarilyOffline;
	public boolean computerTemporarilyOnline;
	public String inboundJMSQueueName;
	public boolean jmsRequest;
	public String outboundJMSQueueName;
	public boolean queueItemEnterBlocked;
	public boolean queueItemEnterBuildable;
	public boolean queueItemEnterWaiting;
	public boolean queueItemLeaveBlocked;
	public boolean queueItemLeaveBuildable;
	public boolean queueItemLeaveWaiting;
	public boolean queueItemLeft;
	public String url;
	public String userName;
	public String userPassword;

	public enum EventTrigger {

		BUILD_COMPLETED, BUILD_STARTED, COMPUTER_BUSY, COMPUTER_IDLE,
		COMPUTER_OFFLINE, COMPUTER_ONLINE, COMPUTER_TEMPORARILY_OFFLINE,
		COMPUTER_TEMPORARILY_ONLINE, QUEUE_ITEM_ENTER_BLOCKED,
		QUEUE_ITEM_ENTER_BUILDABLE, QUEUE_ITEM_ENTER_WAITING,
		QUEUE_ITEM_LEAVE_BLOCKED, QUEUE_ITEM_LEAVE_BUILDABLE,
		QUEUE_ITEM_LEAVE_WAITING, QUEUE_ITEM_LEFT

	}

	private String _getAuthorizationHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("Basic ");

		String userNamePassword = userName + ":" + userPassword;

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

		if (queueItemEnterBlocked) {
			_eventTriggers.add(EventTrigger.QUEUE_ITEM_ENTER_BLOCKED);
		}

		if (queueItemEnterBuildable) {
			_eventTriggers.add(EventTrigger.QUEUE_ITEM_ENTER_BUILDABLE);
		}

		if (queueItemEnterWaiting) {
			_eventTriggers.add(EventTrigger.QUEUE_ITEM_ENTER_WAITING);
		}

		if (queueItemLeaveBlocked) {
			_eventTriggers.add(EventTrigger.QUEUE_ITEM_LEAVE_BLOCKED);
		}

		if (queueItemLeaveBuildable) {
			_eventTriggers.add(EventTrigger.QUEUE_ITEM_LEAVE_BUILDABLE);
		}

		if (queueItemLeaveWaiting) {
			_eventTriggers.add(EventTrigger.QUEUE_ITEM_LEAVE_WAITING);
		}

		if (queueItemLeft) {
			_eventTriggers.add(EventTrigger.QUEUE_ITEM_LEFT);
		}
	}

	private void _publishHttp(String payload, EventTrigger eventTrigger) {
		if (!_eventTriggers.contains(eventTrigger)) {
			return;
		}

		HttpURLConnection httpURLConnection = null;

		try {
			URL url = new URL(this.url);

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

					int b;
					byte[] bytes = new byte[1024];

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

					int b;
					byte[] bytes = new byte[1024];

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

	private void _publishJMS(String payload, EventTrigger eventTrigger) {
		if (!_eventTriggers.contains(eventTrigger) ||
			(outboundJMSQueueName == null) || outboundJMSQueueName.isEmpty()) {

			return;
		}

		JMSConnection jmsConnection = JMSFactory.newJMSConnection(url);

		JMSQueue jmsQueue = JMSFactory.newJMSQueue(
			jmsConnection, outboundJMSQueueName);

		jmsQueue.publish(payload);
	}

	private final List<EventTrigger> _eventTriggers = new ArrayList<>();

}