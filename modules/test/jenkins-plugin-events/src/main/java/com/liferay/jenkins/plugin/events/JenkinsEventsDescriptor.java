/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.events;

import com.liferay.jenkins.plugin.events.jms.JMSConnection;
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

	public void addEventTrigger(EventTrigger eventTrigger) {
		_eventTriggers.add(eventTrigger);
	}

	public void clearEventTriggers() {
		_eventTriggers.clear();
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

	@Override
	public Descriptor<JenkinsEventsDescriptor> getDescriptor() {
		return this;
	}

	public List<EventTrigger> getEventTriggers() {
		return _eventTriggers;
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

	public void publish(String payload, EventTrigger eventTrigger) {
		String outboundQueueName = getOutboundQueueName();

		if (!containsEventTrigger(eventTrigger) ||
			(outboundQueueName == null) || outboundQueueName.isEmpty()) {

			return;
		}

		JMSConnection jmsConnection = JMSFactory.newJMSConnection(getUrl());

		JMSQueue jmsQueue = JMSFactory.newJMSQueue(
			jmsConnection, outboundQueueName);

		jmsQueue.publish(payload);
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
		if (((_inboundQueueName == null) || _inboundQueueName.isEmpty()) &&
			((_url == null) || _url.isEmpty())) {

			return;
		}

		if ((_url == null) || _url.isEmpty()) {
			return;
		}

		JMSConnection jmsConnection = JMSFactory.newJMSConnection(_url);

		JMSQueue jmsQueue = JMSFactory.newJMSQueue(
			jmsConnection, _inboundQueueName);

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

	private final List<EventTrigger> _eventTriggers = new ArrayList<>();
	private String _inboundQueueName;
	private String _outboundQueueName;
	private String _url;
	private String _userName;
	private String _userPassword;

}