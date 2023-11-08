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

	public static JMSQueue newJMSQueue(
		String jmsBrokerURL, String queueName, String userName,
		String userPassword) {

		String key = jmsBrokerURL + "/" + queueName;

		JMSQueue jmsQueue = _jmsQueues.get(key);

		if (jmsQueue == null) {
			jmsQueue = new JMSQueue(jmsBrokerURL, queueName);

			_jmsQueues.put(key, jmsQueue);
		}

		jmsQueue.setUserName(userName);
		jmsQueue.setUserPassword(userPassword);

		return _jmsQueues.get(key);
	}

	private static final Map<String, JMSQueue> _jmsQueues = new HashMap<>();

}