/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.events.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Michael Hashimoto
 */
public class JMSQueue {

	public String getQueueName() {
		return _queueName;
	}

	public void setBrokerURL(String jmsBrokerURL) {
		_jmsBrokerURL = jmsBrokerURL;
	}

	public void setQueueName(String queueName) {
		_queueName = queueName;
	}

	public void publish(String message) {
		connect();

		try {
			MessageProducer messageProducer = _session.createProducer(_queue);

			TextMessage textMessage = _session.createTextMessage();

			textMessage.setText(message);

			messageProducer.send(textMessage);
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}
	}

	public void subscribe(MessageListener messageListener) {
		connect();

		synchronized (_log) {
			try {
				if (_messageConsumer != null) {
					_messageConsumer.close();
				}

				_messageConsumer = _session.createConsumer(_queue);

				_messageConsumer.setMessageListener(messageListener);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Subscribed to " + _jmsBrokerURL + " at " + _queueName);
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
		}
	}

	public void unsubscribe() {
		synchronized (_log) {
			if (_messageConsumer == null) {
				return;
			}

			try {
				_messageConsumer.close();

				_messageConsumer = null;

				if (_log.isInfoEnabled()) {
					_log.info(
						"Unsubscribed from " + _jmsBrokerURL + " at " + _queueName);
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
		}
	}

	protected JMSQueue(String jmsBrokerURL, String queueName) {
		_jmsBrokerURL = jmsBrokerURL;
		_queueName = queueName;
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

				_session = _connection.createSession(
					false, Session.AUTO_ACKNOWLEDGE);

				_queue = _session.createQueue(_queueName);

				if (_log.isInfoEnabled()) {
					_log.info("Connected to " + _jmsBrokerURL + " at " + _queueName);
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
				if (_messageConsumer != null) {
					_messageConsumer.close();
				}

				if (_connection != null) {
					_connection.close();
				}

				if (_log.isInfoEnabled()) {
					_log.info("Disconnected from " + _jmsBrokerURL + " at " + _queueName);
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
			finally {
				_connection = null;
				_messageConsumer = null;
				_queue = null;
				_session = null;
			}
		}
	}

	private static final Log _log = LogFactory.getLog(JMSQueue.class);

	private String _jmsBrokerURL;
	private MessageConsumer _messageConsumer;
	private Queue _queue;
	private String _queueName;
	private Session _session;
	private Connection _connection;

}