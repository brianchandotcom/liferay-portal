/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.subscriber;

import com.liferay.osb.spring.boot.client.pubsub.Message;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Bischof
 */
public class BasePubsubSubscriberTest {

	@Test
	public void testGetAckDeadlineSecondsDefault() {
		TestSubscriber testSubscriber = new TestSubscriber();

		Assert.assertEquals(30, testSubscriber.getAckDeadlineSeconds());
	}

	@Test
	public void testGetMaxDeliveryAttemptsDefault() {
		TestSubscriber testSubscriber = new TestSubscriber();

		Assert.assertEquals(5, testSubscriber.getMaxDeliveryAttempts());
	}

	private static class TestSubscriber extends BasePubsubSubscriber {

		@Override
		protected String getProjectId() {
			return "test-project";
		}

		@Override
		protected void receive(Message message) {
		}

	}

}