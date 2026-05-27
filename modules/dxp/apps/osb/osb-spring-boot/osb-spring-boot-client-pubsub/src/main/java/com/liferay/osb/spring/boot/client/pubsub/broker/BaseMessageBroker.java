/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.distributed.messaging.google.pubsub.connector.broker;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.NotFoundException;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import com.liferay.osb.distributed.messaging.Message;
import com.liferay.osb.distributed.messaging.google.pubsub.connector.ServiceAccountCredentialsProvider;
import com.liferay.osb.distributed.messaging.publishing.broker.MessageBroker;
import com.liferay.osgi.util.StringPlus;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Amos Fong
 */
public abstract class BaseMessageBroker implements MessageBroker {

	@Override
	public synchronized void publish(String topic, Message message)
		throws Exception {

		Publisher publisher = _publisherMap.get(topic);

		if (publisher == null) {
			publisher = Publisher.newBuilder(
				TopicName.ofProjectTopicName(_projectId, _namespace + topic)
			).setCredentialsProvider(
				getCredentialsProvider()
			).setEnableMessageOrdering(
				true
			).build();

			_publisherMap.put(topic, publisher);
		}

		ByteString byteString = ByteString.copyFromUtf8(
			(String)message.getPayload());

		PubsubMessage pubsubMessage = PubsubMessage.newBuilder(
		).putAllAttributes(
			message.getStringAttributes()
		).setData(
			byteString
		).setOrderingKey(
			getClass().getName()
		).build();

		ApiFuture<String> apiFuture = publisher.publish(pubsubMessage);

		ApiFutures.addCallback(
			apiFuture,
			new ApiFutureCallback<String>() {

				public void onFailure(Throwable t) {
					_log.error("Failed to publish message", t);
				}

				public void onSuccess(String messageId) {
					if (_log.isDebugEnabled()) {
						_log.debug("Published message: " + messageId);
					}
				}

			},
			MoreExecutors.directExecutor());
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		_namespace = GetterUtil.getString(properties.get("namespace"));
		_projectId = GetterUtil.getString(properties.get("projectId"));

		boolean publishingTopicCreate = GetterUtil.getBoolean(
			properties.get("publishing.topic.create"));

		if (!publishingTopicCreate) {
			return;
		}

		TopicAdminSettings topicAdminSettings = TopicAdminSettings.newBuilder(
		).setCredentialsProvider(
			getCredentialsProvider()
		).build();

		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(
				topicAdminSettings)) {

			List<String> publishingTopicPatterns = StringPlus.asList(
				properties.get("publishing.topic.pattern"));

			for (String publishingTopicPattern : publishingTopicPatterns) {
				TopicName topicName = TopicName.ofProjectTopicName(
					_projectId, _namespace + publishingTopicPattern);

				try {
					topicAdminClient.getTopic(topicName);
				}
				catch (NotFoundException notFoundException) {
					topicAdminClient.createTopic(topicName);

					if (_log.isInfoEnabled()) {
						_log.info("Created topic " + topicName);
					}
				}
			}
		}
	}

	@Deactivate
	protected void deactivate() {
		try {
			for (Publisher publisher : _publisherMap.values()) {
				publisher.shutdown();

				publisher.awaitTermination(1, TimeUnit.MINUTES);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	protected CredentialsProvider getCredentialsProvider() throws Exception {
		ServiceAccountCredentialsProvider serviceAccountCredentialsProvider =
			getServiceAccountCredentialsProvider();

		return serviceAccountCredentialsProvider.getCredentialsProvider();
	}

	protected abstract ServiceAccountCredentialsProvider
			getServiceAccountCredentialsProvider()
		throws Exception;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseMessageBroker.class);

	private String _namespace;
	private String _projectId;
	private final Map<String, Publisher> _publisherMap = new HashMap<>();

}