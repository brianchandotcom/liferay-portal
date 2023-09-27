/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.events.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Michael Hashimoto
 */
public class JMSQueue {

	public String getQueueName() {
		return _queueName;
	}

	public void publish(String message) {
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
		synchronized (_log) {
			if (_messageConsumer != null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"[" + _queueName + "] Already subscribed to queue");
				}

				return;
			}

			try {
				_messageConsumer = _session.createConsumer(_queue);

				_messageConsumer.setMessageListener(messageListener);

				if (_log.isDebugEnabled()) {
					_log.debug("[" + _queueName + "] Subscribed to queue");
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

				if (_log.isDebugEnabled()) {
					_log.debug("[" + _queueName + "] Unsubscribed to queue");
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
		}
	}

	protected JMSQueue(Connection connection, String queueName) {
		_queueName = queueName;

		try {
			_session = connection.createSession(
				false, Session.AUTO_ACKNOWLEDGE);

			_queue = _session.createQueue(_queueName);
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}
	}

	private static final Log _log = LogFactory.getLog(JMSQueue.class);

	private MessageConsumer _messageConsumer;
	private final Queue _queue;
	private final String _queueName;
	private final Session _session;

}