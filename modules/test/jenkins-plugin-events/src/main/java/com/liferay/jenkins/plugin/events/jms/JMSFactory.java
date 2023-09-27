/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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