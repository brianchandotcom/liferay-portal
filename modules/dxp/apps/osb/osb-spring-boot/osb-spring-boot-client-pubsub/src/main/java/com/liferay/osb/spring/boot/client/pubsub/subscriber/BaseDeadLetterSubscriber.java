/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.subscriber;

import com.liferay.osb.spring.boot.client.pubsub.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kyle Bischof
 */
public abstract class BaseDeadLetterSubscriber extends BasePubsubSubscriber {

	public static final String SOURCE_DELIVERY_COUNT_ATTRIBUTE_NAME =
		"CloudPubSubDeadLetterSourceDeliveryCount";

	public static final String SOURCE_SUBSCRIPTION_ATTRIBUTE_NAME =
		"CloudPubSubDeadLetterSourceSubscription";

	@Override
	protected final boolean isDeadLetterEnabled() {
		return false;
	}

	protected abstract void onDeadLetter(
			Message message, String sourceSubscription, int deliveryAttempt)
		throws Exception;

	@Override
	protected final void receive(Message message) throws Exception {
		onDeadLetter(
			message, message.get(SOURCE_SUBSCRIPTION_ATTRIBUTE_NAME),
			_getDeliveryAttempt(message));
	}

	private int _getDeliveryAttempt(Message message) {
		String deliveryAttempt = message.get(
			SOURCE_DELIVERY_COUNT_ATTRIBUTE_NAME);

		if ((deliveryAttempt == null) || deliveryAttempt.isEmpty()) {
			return 0;
		}

		try {
			return Integer.parseInt(deliveryAttempt);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to parse delivery attempt " + deliveryAttempt,
					numberFormatException);
			}

			return 0;
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		BaseDeadLetterSubscriber.class);

}