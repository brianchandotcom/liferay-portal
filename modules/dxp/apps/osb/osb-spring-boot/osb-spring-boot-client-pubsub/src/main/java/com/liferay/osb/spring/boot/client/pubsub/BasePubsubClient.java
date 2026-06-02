/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.AlreadyExistsException;
import com.google.api.gax.rpc.NotFoundException;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.TopicName;

import com.liferay.osb.spring.boot.client.pubsub.credentials.ServiceAccountCredentialsProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Kyle Bischof
 */
public abstract class BasePubsubClient {

	protected void ensureTopicExists(String topic) throws Exception {
		TopicAdminSettings topicAdminSettings = TopicAdminSettings.newBuilder(
		).setCredentialsProvider(
			getCredentialsProvider()
		).build();

		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(
				topicAdminSettings)) {

			_ensureTopicExists(topicAdminClient, topic);

			if (isDeadLetterTopicEnabled()) {
				_ensureTopicExists(topicAdminClient, getDeadLetterTopic(topic));
			}
		}
	}

	protected CredentialsProvider getCredentialsProvider() throws Exception {
		return _serviceAccountCredentialsProvider.getCredentialsProvider();
	}

	protected String getDeadLetterTopic(String topic) {
		return "dead-letter";
	}

	protected String getNamespace() {
		return "";
	}

	protected abstract String getProjectId();

	protected boolean isAutoCreateTopic() {
		return true;
	}

	protected boolean isDeadLetterTopicEnabled() {
		return true;
	}

	private void _ensureTopicExists(
		TopicAdminClient topicAdminClient, String name) {

		TopicName topicName = TopicName.ofProjectTopicName(
			getProjectId(), getNamespace() + name);

		try {
			topicAdminClient.getTopic(topicName);
		}
		catch (NotFoundException notFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find topic. Creating topic " + topicName,
					notFoundException);
			}

			try {
				topicAdminClient.createTopic(topicName);
			}
			catch (AlreadyExistsException alreadyExistsException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Topic already exists " + topicName,
						alreadyExistsException);
				}
			}
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		BasePubsubClient.class);

	@Autowired
	private ServiceAccountCredentialsProvider
		_serviceAccountCredentialsProvider;

}