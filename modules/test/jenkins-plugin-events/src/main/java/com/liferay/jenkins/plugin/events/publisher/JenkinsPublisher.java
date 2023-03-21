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
import com.liferay.jenkins.plugin.events.listener.JMSMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsPublisher {

	public JenkinsPublisher(JSONObject jsonObject) {
		_jmsRequest = jsonObject.has("jmsRequest");

		if (_jmsRequest) {
			JSONObject jmsRequestJSONObject = jsonObject.getJSONObject(
				"jmsRequest");

			_inboundJMSQueueName = jmsRequestJSONObject.optString(
				"inboundJMSQueueName");
			_outboundJMSQueueName = jmsRequestJSONObject.optString(
				"outboundJMSQueueName");
		}
		else {
			_inboundJMSQueueName = null;
			_outboundJMSQueueName = null;
		}

		_name = jsonObject.getString("name");
		_url = jsonObject.getString("url");
		_userName = jsonObject.getString("userName");
		_userPassword = jsonObject.getString("userPassword");

		_setEventTrigger(
			jsonObject.getBoolean("buildCompleted"),
			EventTrigger.BUILD_COMPLETED);
		_setEventTrigger(
			jsonObject.getBoolean("buildStarted"), EventTrigger.BUILD_STARTED);
		_setEventTrigger(
			jsonObject.getBoolean("computerBusy"), EventTrigger.COMPUTER_BUSY);
		_setEventTrigger(
			jsonObject.getBoolean("computerIdle"), EventTrigger.COMPUTER_IDLE);
		_setEventTrigger(
			jsonObject.getBoolean("computerOffline"),
			EventTrigger.COMPUTER_OFFLINE);
		_setEventTrigger(
			jsonObject.getBoolean("computerOnline"),
			EventTrigger.COMPUTER_ONLINE);
		_setEventTrigger(
			jsonObject.getBoolean("computerTemporarilyOffline"),
			EventTrigger.COMPUTER_TEMPORARILY_OFFLINE);
		_setEventTrigger(
			jsonObject.getBoolean("computerTemporarilyOnline"),
			EventTrigger.COMPUTER_TEMPORARILY_ONLINE);
		_setEventTrigger(
			jsonObject.getBoolean("queueItemEnterBlocked"),
			EventTrigger.QUEUE_ITEM_ENTER_BLOCKED);
		_setEventTrigger(
			jsonObject.getBoolean("queueItemEnterBuildable"),
			EventTrigger.QUEUE_ITEM_ENTER_BUILDABLE);
		_setEventTrigger(
			jsonObject.getBoolean("queueItemEnterWaiting"),
			EventTrigger.QUEUE_ITEM_ENTER_WAITING);
		_setEventTrigger(
			jsonObject.getBoolean("queueItemLeaveBlocked"),
			EventTrigger.QUEUE_ITEM_LEAVE_BLOCKED);
		_setEventTrigger(
			jsonObject.getBoolean("queueItemLeaveBuildable"),
			EventTrigger.QUEUE_ITEM_LEAVE_BUILDABLE);
		_setEventTrigger(
			jsonObject.getBoolean("queueItemLeaveWaiting"),
			EventTrigger.QUEUE_ITEM_LEAVE_WAITING);
		_setEventTrigger(
			jsonObject.getBoolean("queueItemLeft"),
			EventTrigger.QUEUE_ITEM_LEFT);

		subscribe();
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

	public boolean containsEventTrigger(String eventTriggerString) {
		for (EventTrigger eventTrigger : EventTrigger.values()) {
			if (Objects.equals(eventTriggerString, eventTrigger.toString())) {
				return containsEventTrigger(
					EventTrigger.valueOf(eventTriggerString));
			}
		}

		return false;
	}

	public List<EventTrigger> getEventTriggers() {
		return _eventTriggers;
	}

	public String getInboundJMSQueueName() {
		return _inboundJMSQueueName;
	}

	public boolean getJmsRequest() {
		return _jmsRequest;
	}

	public String getName() {
		return _name;
	}

	public String getOutboundJMSQueueName() {
		return _outboundJMSQueueName;
	}

	public String getUrl() {
		return _url;
	}

	public String getUserName() {
		return _userName;
	}

	public String getUserPassword() {
		return _userPassword;
	}

	public void publish(String payload, EventTrigger eventTrigger) {
		if (!_eventTriggers.contains(eventTrigger)) {
			return;
		}

		if (_jmsRequest) {
			_publishJMS(payload, eventTrigger);

			return;
		}

		_publishHttp("body=" + payload, eventTrigger);
	}

	public void subscribe() {
		if ((_inboundJMSQueueName == null) || !_jmsRequest) {
			return;
		}

		JMSConnection jmsConnection = JMSFactory.newJMSConnection(_url);

		JMSQueue jmsQueue = JMSFactory.newJMSQueue(
			jmsConnection, _inboundJMSQueueName);

		jmsQueue.subscribe(new JMSMessageListener());
	}

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

		String userNamePassword = getUserName() + ":" + getUserPassword();

		Base64.Encoder base64Encoder = Base64.getEncoder();

		sb.append(base64Encoder.encodeToString(userNamePassword.getBytes()));

		return sb.toString();
	}

	private void _publishHttp(String payload, EventTrigger eventTrigger) {
		if (!containsEventTrigger(eventTrigger)) {
			return;
		}

		HttpURLConnection httpURLConnection = null;

		try {
			URL url = new URL(getUrl());

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
		String outboundJMSQueueName = getOutboundJMSQueueName();

		if (!containsEventTrigger(eventTrigger) ||
			(outboundJMSQueueName == null) || outboundJMSQueueName.isEmpty()) {

			return;
		}

		JMSConnection jmsConnection = JMSFactory.newJMSConnection(getUrl());

		JMSQueue jmsQueue = JMSFactory.newJMSQueue(
			jmsConnection, outboundJMSQueueName);

		jmsQueue.publish(payload);
	}

	private void _setEventTrigger(
		boolean enableEventTrigger, EventTrigger eventTrigger) {

		List<EventTrigger> eventTriggers = getEventTriggers();

		eventTriggers.remove(eventTrigger);

		if (enableEventTrigger) {
			eventTriggers.add(eventTrigger);
		}
	}

	private final List<EventTrigger> _eventTriggers = new ArrayList<>();
	private final String _inboundJMSQueueName;
	private final boolean _jmsRequest;
	private final String _name;
	private final String _outboundJMSQueueName;
	private final String _url;
	private final String _userName;
	private final String _userPassword;

}