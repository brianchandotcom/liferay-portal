/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.events.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Michael Hashimoto
 */
public class JMSConnection {

	public JMSConnection(String jmsBrokerURL) {
		_jmsBrokerURL = jmsBrokerURL;

		connect();
	}

	public void connect() {
		synchronized (_log) {
			if (_connection != null) {
				return;
			}

			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				_jmsBrokerURL);

			try {
				_connection = connectionFactory.createConnection();

				_connection.start();

				if (_log.isDebugEnabled()) {
					_log.debug("Created JMS connection to " + _jmsBrokerURL);
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
		}
	}

	public void disconnect() {
		synchronized (_log) {
			try {
				if (_connection != null) {
					_connection.close();
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
			finally {
				_connection = null;
			}
		}
	}

	public Connection getConnection() {
		synchronized (_log) {
			connect();

			return _connection;
		}
	}

	private static final Log _log = LogFactory.getLog(JMSConnection.class);

	private Connection _connection;
	private final String _jmsBrokerURL;

}