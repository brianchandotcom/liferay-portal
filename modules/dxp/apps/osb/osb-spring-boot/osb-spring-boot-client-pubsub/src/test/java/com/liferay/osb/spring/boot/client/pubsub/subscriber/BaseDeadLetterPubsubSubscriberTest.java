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
public class BaseDeadLetterPubsubSubscriberTest {

	@Test
	public void testDeliveryAttemptValue() throws Exception {
		TestDeadLetterPubsubSubscriber testDeadLetterPubsubSubscriber =
			new TestDeadLetterPubsubSubscriber();

		testDeadLetterPubsubSubscriber.receive(
			new Message(
				Collections.singletonMap(
					BaseDeadLetterPubsubSubscriber.
						SOURCE_DELIVERY_COUNT_ATTRIBUTE_NAME,
					"7"),
				RandomStringUtils.randomAlphanumeric(10),
				RandomStringUtils.randomAlphabetic(6)));

		Assert.assertEquals(
			7, testDeadLetterPubsubSubscriber.getDeliveryAttempt());
	}

	private static class TestDeadLetterPubsubSubscriber
		extends BaseDeadLetterPubsubSubscriber {

		public int getDeliveryAttempt() {
			return _deliveryAttempt;
		}

		@Override
		protected String getProjectId() {
			return "test-project";
		}

		@Override
		protected String getTopic() {
			return "test-topic";
		}

		@Override
		protected void onDeadLetter(
			Message message, String sourceSubscriptionName,
			int deliveryAttempt) {

			_deliveryAttempt = deliveryAttempt;
		}

		private int _deliveryAttempt;

	}

}