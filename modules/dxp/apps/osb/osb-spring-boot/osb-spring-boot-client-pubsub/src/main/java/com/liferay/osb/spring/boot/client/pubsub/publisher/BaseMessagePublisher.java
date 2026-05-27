/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.publisher;

import com.liferay.osb.spring.boot.client.pubsub.Message;
import com.liferay.osb.spring.boot.client.pubsub.broker.MessageBroker;
import com.liferay.petra.string.StringBundler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
public abstract class BaseMessagePublisher implements MessagePublisher {

	@Override
	public synchronized void publish(String topic, Message message)
		throws Exception {

		try {
			if (_log.isDebugEnabled()) {
				Class<?> messageBrokerClass = messageBroker.getClass();

				_log.debug(
					StringBundler.concat(
						"Publishing message for topic ", topic, " to ",
						messageBrokerClass.getName()));

				_log.debug("Message: " + message.toString());
			}

			messageBroker.publish(topic, message);
		}
		catch (Exception exception) {
			handleError(topic, message, exception);
		}
	}

	protected void handleError(
			String topic, Message message, Exception exception)
		throws Exception {

		_log.error("Failed to publish message to topic " + topic, exception);

		throw exception;
	}

	@Autowired
	protected MessageBroker messageBroker;

	private static final Logger _log = LoggerFactory.getLogger(
		BaseMessagePublisher.class);

}