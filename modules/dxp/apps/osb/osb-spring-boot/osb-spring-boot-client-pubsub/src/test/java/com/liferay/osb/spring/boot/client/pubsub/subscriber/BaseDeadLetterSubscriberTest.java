/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.subscriber;

import com.liferay.osb.spring.boot.client.pubsub.Message;

import java.util.Collections;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Bischof
 */
public class BaseDeadLetterSubscriberTest {

	@Test
	public void testDeliveryAttemptEmptyDefaultsToZero() throws Exception {
		RecordingDeadLetterSubscriber recordingDeadLetterSubscriber =
			new RecordingDeadLetterSubscriber();

		recordingDeadLetterSubscriber.receive(
			new Message(
				Collections.singletonMap(
					BaseDeadLetterSubscriber.
						SOURCE_DELIVERY_COUNT_ATTRIBUTE_NAME,
					""),
				RandomStringUtils.randomAlphanumeric(10),
				RandomStringUtils.randomAlphabetic(6)));

		Assert.assertEquals(
			0, recordingDeadLetterSubscriber.getDeliveryAttempt());
	}

	@Test
	public void testDeliveryAttemptInvalidDefaultsToZero() throws Exception {
		RecordingDeadLetterSubscriber recordingDeadLetterSubscriber =
			new RecordingDeadLetterSubscriber();

		recordingDeadLetterSubscriber.receive(
			new Message(
				Collections.singletonMap(
					BaseDeadLetterSubscriber.
						SOURCE_DELIVERY_COUNT_ATTRIBUTE_NAME,
					RandomStringUtils.randomAlphabetic(5)),
				RandomStringUtils.randomAlphanumeric(10),
				RandomStringUtils.randomAlphabetic(6)));

		Assert.assertEquals(
			0, recordingDeadLetterSubscriber.getDeliveryAttempt());
	}

	@Test
	public void testDeliveryAttemptNullDefaultsToZero() throws Exception {
		RecordingDeadLetterSubscriber recordingDeadLetterSubscriber =
			new RecordingDeadLetterSubscriber();

		recordingDeadLetterSubscriber.receive(
			new Message(
				null, RandomStringUtils.randomAlphanumeric(10),
				RandomStringUtils.randomAlphabetic(6)));

		Assert.assertEquals(
			0, recordingDeadLetterSubscriber.getDeliveryAttempt());
	}

	@Test
	public void testDeliveryAttemptValidIsParsed() throws Exception {
		RecordingDeadLetterSubscriber recordingDeadLetterSubscriber =
			new RecordingDeadLetterSubscriber();

		recordingDeadLetterSubscriber.receive(
			new Message(
				Collections.singletonMap(
					BaseDeadLetterSubscriber.
						SOURCE_DELIVERY_COUNT_ATTRIBUTE_NAME,
					"7"),
				RandomStringUtils.randomAlphanumeric(10),
				RandomStringUtils.randomAlphabetic(6)));

		Assert.assertEquals(
			7, recordingDeadLetterSubscriber.getDeliveryAttempt());
	}

	@Test
	public void testIsDeadLetterEnabledFalse() {
		RecordingDeadLetterSubscriber recordingDeadLetterSubscriber =
			new RecordingDeadLetterSubscriber();

		Assert.assertFalse(recordingDeadLetterSubscriber.isDeadLetterEnabled());
	}

	@Test
	public void testReceivePassesSourceSubscriptionAndMessage()
		throws Exception {

		String sourceSubscription = RandomStringUtils.randomAlphanumeric(12);

		Message message = new Message(
			Collections.singletonMap(
				BaseDeadLetterSubscriber.SOURCE_SUBSCRIPTION_ATTRIBUTE_NAME,
				sourceSubscription),
			RandomStringUtils.randomAlphanumeric(10),
			RandomStringUtils.randomAlphabetic(6));

		RecordingDeadLetterSubscriber recordingDeadLetterSubscriber =
			new RecordingDeadLetterSubscriber();

		recordingDeadLetterSubscriber.receive(message);

		Assert.assertSame(message, recordingDeadLetterSubscriber.getMessage());
		Assert.assertEquals(
			sourceSubscription,
			recordingDeadLetterSubscriber.getSourceSubscription());
	}

	private static class RecordingDeadLetterSubscriber
		extends BaseDeadLetterSubscriber {

		public int getDeliveryAttempt() {
			return _deliveryAttempt;
		}

		public Message getMessage() {
			return _message;
		}

		public String getSourceSubscription() {
			return _sourceSubscription;
		}

		@Override
		protected String getProjectId() {
			return "test-project";
		}

		@Override
		protected void onDeadLetter(
			Message message, String sourceSubscription, int deliveryAttempt) {

			_message = message;
			_sourceSubscription = sourceSubscription;
			_deliveryAttempt = deliveryAttempt;
		}

		private int _deliveryAttempt;
		private Message _message;
		private String _sourceSubscription;

	}

}