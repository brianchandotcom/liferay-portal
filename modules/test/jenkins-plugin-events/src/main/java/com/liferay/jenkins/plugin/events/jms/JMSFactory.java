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

package com.liferay.jenkins.plugin.events.jms;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class JMSFactory {

	public static JMSConnection newJMSConnection(String jmsBrokerURL) {
		JMSConnection jmsConnection = _jmsConnections.get(jmsBrokerURL);

		if (jmsConnection == null) {
			jmsConnection = new JMSConnection(jmsBrokerURL);

			_jmsConnections.put(jmsBrokerURL, jmsConnection);
		}

		return jmsConnection;
	}

	public static JMSQueue newJMSQueue(
		JMSConnection jmsConnection, String queueName) {

		JMSQueue jmsQueue = _jmsQueues.get(queueName);

		if (jmsQueue == null) {
			jmsQueue = new JMSQueue(jmsConnection.getConnection(), queueName);

			_jmsQueues.put(queueName, jmsQueue);
		}

		return jmsQueue;
	}

	public static JMSTopic newJMSTopic(
		JMSConnection jmsConnection, String topicName) {

		JMSTopic jmsTopic = _jmsTopics.get(topicName);

		if (jmsTopic == null) {
			jmsTopic = new JMSTopic(jmsConnection.getConnection(), topicName);

			_jmsTopics.put(topicName, jmsTopic);
		}

		return jmsTopic;
	}

	private static final Map<String, JMSConnection> _jmsConnections =
		new HashMap<>();
	private static final Map<String, JMSQueue> _jmsQueues = new HashMap<>();
	private static final Map<String, JMSTopic> _jmsTopics = new HashMap<>();

}