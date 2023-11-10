/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.events;

import com.liferay.jenkins.plugin.events.jms.JMSFactory;
import com.liferay.jenkins.plugin.events.jms.JMSQueue;
import com.liferay.jenkins.plugin.events.listener.JMSMessageListener;

import hudson.Extension;

import hudson.model.Describable;
import hudson.model.Descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Michael Hashimoto
 */
@Extension
public class JenkinsEventsDescriptor
	extends Descriptor<JenkinsEventsDescriptor>
	implements Describable<JenkinsEventsDescriptor> {

	public JenkinsEventsDescriptor() {
		super(JenkinsEventsDescriptor.class);

		load();

		subscribe();

		JenkinsEventsUtil.setJenkinsEventsDescriptor(this);
	}

	public void addEventType(EventType eventType) {
		_eventTypes.add(eventType);
	}

	public void clearEventTypes() {
		_eventTypes.clear();
	}

	public boolean containsEventType(EventType eventType) {
		if (eventType == null) {
			return false;
		}

		if (_eventTypes.contains(eventType)) {
			return true;
		}

		return false;
	}

	public boolean containsEventType(String eventTypeString) {
		for (EventType eventType : EventType.values()) {
			if (Objects.equals(eventTypeString, eventType.toString())) {
				return containsEventType(EventType.valueOf(eventTypeString));
			}
		}

		return false;
	}

	@Override
	public Descriptor<JenkinsEventsDescriptor> getDescriptor() {
		return this;
	}

	public List<EventType> getEventTypes() {
		return _eventTypes;
	}

	public String getInboundQueueName() {
		return _inboundQueueName;
	}

	public String getOutboundQueueName() {
		return _outboundQueueName;
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

	public void publish(String payload, EventType eventType) {
		String outboundQueueName = getOutboundQueueName();

		if (!containsEventType(eventType) || (outboundQueueName == null) ||
			outboundQueueName.isEmpty()) {

			return;
		}

		if (_outboundJMSQueue == null) {
			_outboundJMSQueue = JMSFactory.newJMSQueue(
				getUrl(), outboundQueueName, _userName, _userPassword);

			_outboundJMSQueue.connect();
		}

		_outboundJMSQueue.publish(payload);
	}

	public void setInboundQueueName(String inboundQueueName) {
		_inboundQueueName = inboundQueueName;
	}

	public void setOutboundQueueName(String outboundQueueName) {
		_outboundQueueName = outboundQueueName;
	}

	public void setUrl(String url) {
		if (!url.matches("tcp://.*")) {
			throw new RuntimeException("Invalid URL");
		}

		_url = url;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public void setUserPassword(String userPassword) {
		_userPassword = userPassword;
	}

	public void subscribe() {
		if ((_url == null) || _url.isEmpty()) {
			return;
		}

		if ((_inboundQueueName != null) && !_inboundQueueName.isEmpty()) {
			if (_inboundJMSQueue == null) {
				_inboundJMSQueue = JMSFactory.newJMSQueue(
					_url, _inboundQueueName, _userName, _userPassword);
			}
			else {
				_inboundJMSQueue.unsubscribe();

				_inboundJMSQueue.disconnect();

				_inboundJMSQueue.setBrokerURL(_url);
				_inboundJMSQueue.setQueueName(_inboundQueueName);
			}

			_inboundJMSQueue.connect();

			_inboundJMSQueue.subscribe(new JMSMessageListener());
		}
		else {
			_inboundJMSQueue = null;
		}

		if ((_outboundQueueName != null) && !_outboundQueueName.isEmpty()) {
			if (_outboundJMSQueue == null) {
				_outboundJMSQueue = JMSFactory.newJMSQueue(
					_url, _outboundQueueName, _userName, _userPassword);

				_outboundJMSQueue.connect();
			}
			else {
				_outboundJMSQueue.disconnect();

				_outboundJMSQueue.setBrokerURL(_url);
				_outboundJMSQueue.setQueueName(_outboundQueueName);

				_outboundJMSQueue.connect();
			}
		}
		else {
			_outboundJMSQueue = null;
		}
	}

	public enum EventType {

		BUILD_COMPLETED, BUILD_STARTED, COMPUTER_BUSY, COMPUTER_IDLE,
		COMPUTER_OFFLINE, COMPUTER_ONLINE, COMPUTER_TEMPORARILY_OFFLINE,
		COMPUTER_TEMPORARILY_ONLINE, QUEUE_ITEM_ENTER_BLOCKED,
		QUEUE_ITEM_ENTER_BUILDABLE, QUEUE_ITEM_ENTER_WAITING,
		QUEUE_ITEM_LEAVE_BLOCKED, QUEUE_ITEM_LEAVE_BUILDABLE,
		QUEUE_ITEM_LEAVE_WAITING, QUEUE_ITEM_LEFT

	}

	private final List<EventType> _eventTypes = new ArrayList<>();
	private transient JMSQueue _inboundJMSQueue;
	private String _inboundQueueName;
	private transient JMSQueue _outboundJMSQueue;
	private String _outboundQueueName;
	private String _url;
	private String _userName;
	private String _userPassword;

}