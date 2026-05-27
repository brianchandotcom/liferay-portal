/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.publisher;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
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
import com.liferay.osb.spring.boot.client.pubsub.credentials.ServiceAccountCredentialsProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
public abstract class BasePubsubPublisher {

	public synchronized void publish(Message message) throws Exception {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Publishing message " + message.toString());
			}

			doPublish(message);
		}
		catch (Exception exception) {
			handleError(message, exception);
		}
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

	protected RetrySettings buildRetrySettings() {
		return null;
	}

	protected void doPublish(Message message) throws Exception {
		Publisher publisher = _publisherMap.get(message.getTopic());

		if (publisher == null) {
			publisher = _createPublisher(message.getTopic());

			_publisherMap.put(message.getTopic(), publisher);
		}

		PubsubMessage pubsubMessage = PubsubMessage.newBuilder(
		).putAllAttributes(
			message.getAttributes()
		).setData(
			ByteString.copyFromUtf8(message.getPayload())
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

	protected String getNamespace() {
		return "";
	}

	protected String getOrderingKey(Message message) {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	protected abstract String getProjectId();

	protected void handleError(Message message, Exception exception)
		throws Exception {

		_log.error(
			"Failed to publish message " + message.toString(), exception);

		throw exception;
	}

	protected boolean isAutoCreateTopic() {
		return true;
	}

	protected boolean isDeadLetterEnabled() {
		return true;
	}

	private Publisher _createPublisher(String topic) throws Exception {
		if (isAutoCreateTopic()) {
			_ensureTopicExists(topic);
		}

		Publisher.Builder builder = Publisher.newBuilder(
			TopicName.ofProjectTopicName(getProjectId(), getNamespace() + topic)
		).setCredentialsProvider(
			_serviceAccountCredentialsProvider.getCredentialsProvider()
		).setEnableMessageOrdering(
			true
		);

		RetrySettings retrySettings = buildRetrySettings();

		if (retrySettings != null) {
			builder.setRetrySettings(retrySettings);
		}

		return builder.build();
	}

	private void _ensureTopicExists(String topic) throws Exception {
		TopicAdminSettings topicAdminSettings = TopicAdminSettings.newBuilder(
		).setCredentialsProvider(
			_serviceAccountCredentialsProvider.getCredentialsProvider()
		).build();

		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(
				topicAdminSettings)) {

			_ensureTopicExists(topicAdminClient, getNamespace() + topic);

			if (isDeadLetterEnabled()) {
				_ensureTopicExists(
					topicAdminClient, getNamespace() + topic + "-dlq");
			}
		}
	}

	private void _ensureTopicExists(
		TopicAdminClient topicAdminClient, String topic) {

		TopicName topicName = TopicName.ofProjectTopicName(
			getProjectId(), topic);

		try {
			topicAdminClient.getTopic(topicName);
		}
		catch (NotFoundException notFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find topic. Creating topic " + topicName,
					notFoundException);
			}

			topicAdminClient.createTopic(topicName);
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		BasePubsubPublisher.class);

	private final Map<String, Publisher> _publisherMap = new HashMap<>();

	@Autowired
	private ServiceAccountCredentialsProvider
		_serviceAccountCredentialsProvider;

}