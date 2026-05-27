/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.broker;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.NotFoundException;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import com.liferay.osb.spring.boot.client.pubsub.Message;
import com.liferay.osb.spring.boot.client.pubsub.configuration.PubsubProperties;
import com.liferay.osb.spring.boot.client.pubsub.credentials.ServiceAccountCredentialsProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
public abstract class BaseMessageBroker implements MessageBroker {

	@Override
	public synchronized void publish(String topic, Message message)
		throws Exception {

		Publisher publisher = _publisherMap.get(topic);

		if (publisher == null) {
			Publisher.Builder builder = Publisher.newBuilder(
				TopicName.ofProjectTopicName(
					_pubsubProperties.getProjectId(),
					_pubsubProperties.getNamespace() + topic)
			).setCredentialsProvider(
				getCredentialsProvider()
			).setEnableMessageOrdering(
				true
			);

			RetrySettings retrySettings = buildRetrySettings();

			if (retrySettings != null) {
				builder.setRetrySettings(retrySettings);
			}

			publisher = builder.build();

			_publisherMap.put(topic, publisher);
		}

		PubsubMessage pubsubMessage = PubsubMessage.newBuilder(
		).putAllAttributes(
			message.getStringAttributes()
		).setData(
			ByteString.copyFromUtf8((String)message.getPayload())
		).setOrderingKey(
			getOrderingKey(message)
		).build();

		ApiFuture<String> apiFuture = publisher.publish(pubsubMessage);

		ApiFutures.addCallback(
			apiFuture,
			new ApiFutureCallback<String>() {

				public void onFailure(Throwable throwable) {
					_log.error("Failed to publish message", throwable);
				}

				public void onSuccess(String messageId) {
					if (_log.isDebugEnabled()) {
						_log.debug("Published message: " + messageId);
					}
				}

			},
			MoreExecutors.directExecutor());
	}

	@PreDestroy
	public void shutdown() {
		try {
			for (Publisher publisher : _publisherMap.values()) {
				publisher.shutdown();

				publisher.awaitTermination(1, TimeUnit.MINUTES);
			}
		}
		catch (Exception exception) {
			_log.error("Failed to shut down publishers", exception);
		}
	}

	@PostConstruct
	public void start() throws Exception {
		if (!isAutoCreate()) {
			return;
		}

		TopicAdminSettings topicAdminSettings = TopicAdminSettings.newBuilder(
		).setCredentialsProvider(
			getCredentialsProvider()
		).build();

		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(
				topicAdminSettings)) {

			PubsubProperties.Broker broker = _pubsubProperties.getBroker();
			String namespace = _pubsubProperties.getNamespace();

			for (String topic : getDeclaredTopics()) {
				_createIfMissing(topicAdminClient, namespace + topic);

				if (broker.isDeadLetterEnabled()) {
					_createIfMissing(
						topicAdminClient, namespace + topic + "-dlq");
				}
			}
		}
	}

	protected RetrySettings buildRetrySettings() {
		return null;
	}

	protected CredentialsProvider getCredentialsProvider() throws Exception {
		ServiceAccountCredentialsProvider serviceAccountCredentialsProvider =
			getServiceAccountCredentialsProvider();

		return serviceAccountCredentialsProvider.getCredentialsProvider();
	}

	protected abstract Set<String> getDeclaredTopics();

	protected String getOrderingKey(Message message) {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	protected abstract ServiceAccountCredentialsProvider
			getServiceAccountCredentialsProvider()
		throws Exception;

	protected boolean isAutoCreate() {
		PubsubProperties.Broker broker = _pubsubProperties.getBroker();

		return broker.isAutoCreate();
	}

	private void _createIfMissing(
		TopicAdminClient topicAdminClient, String topicName) {

		TopicName fullTopicName = TopicName.ofProjectTopicName(
			_pubsubProperties.getProjectId(), topicName);

		try {
			topicAdminClient.getTopic(fullTopicName);
		}
		catch (NotFoundException notFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Topic not found, creating", notFoundException);
			}

			topicAdminClient.createTopic(fullTopicName);

			if (_log.isInfoEnabled()) {
				_log.info("Created topic " + fullTopicName);
			}
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		BaseMessageBroker.class);

	private final Map<String, Publisher> _publisherMap = new HashMap<>();

	@Autowired
	private PubsubProperties _pubsubProperties;

}