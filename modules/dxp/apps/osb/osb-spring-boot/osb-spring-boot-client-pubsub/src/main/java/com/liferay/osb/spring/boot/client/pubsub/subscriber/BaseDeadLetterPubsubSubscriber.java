/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.subscriber;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.TopicName;

import com.liferay.osb.spring.boot.client.pubsub.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kyle Bischof
 */
public abstract class BaseDeadLetterPubsubSubscriber
	extends BasePubsubSubscriber {

	public static final String SOURCE_DELIVERY_COUNT_ATTRIBUTE_NAME =
		"CloudPubSubDeadLetterSourceDeliveryCount";

	public static final String SOURCE_SUBSCRIPTION_ATTRIBUTE_NAME =
		"CloudPubSubDeadLetterSourceSubscription";

	protected String getSourceTopic(String sourceSubscriptionName)
		throws Exception {

		String sourceTopic = _topics.get(sourceSubscriptionName);

		if (sourceTopic != null) {
			return sourceTopic;
		}

		SubscriptionAdminSettings subscriptionAdminSettings =
			SubscriptionAdminSettings.newBuilder(
			).setCredentialsProvider(
				getCredentialsProvider()
			).build();

		try (SubscriptionAdminClient subscriptionAdminClient =
				SubscriptionAdminClient.create(subscriptionAdminSettings)) {

			Subscription subscription = subscriptionAdminClient.getSubscription(
				ProjectSubscriptionName.of(
					getProjectId(), sourceSubscriptionName));

			TopicName topicName = TopicName.parse(subscription.getTopic());

			_topics.put(sourceSubscriptionName, topicName.getTopic());

			return topicName.getTopic();
		}
	}

	@Override
	protected final boolean isDeadLetterTopicEnabled() {
		return false;
	}

	protected abstract void onDeadLetter(
			Message message, String sourceSubscriptionName, int deliveryAttempt)
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
		BaseDeadLetterPubsubSubscriber.class);

	private final Map<String, String> _topics = new ConcurrentHashMap<>();

}