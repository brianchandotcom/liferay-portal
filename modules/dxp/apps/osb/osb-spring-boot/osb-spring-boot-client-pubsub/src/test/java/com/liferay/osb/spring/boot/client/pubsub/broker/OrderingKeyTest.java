/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.broker;

import com.liferay.osb.spring.boot.client.pubsub.Message;
import com.liferay.osb.spring.boot.client.pubsub.credentials.ServiceAccountCredentialsProvider;

import java.util.Collections;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Bischof
 */
public class OrderingKeyTest {

	@Test
	public void testDefaultOrderingKeyIsBrokerClassFqn() {
		TestBroker broker = new TestBroker();

		String orderingKey = broker.callGetOrderingKey(new Message("payload"));

		Assert.assertEquals(TestBroker.class.getName(), orderingKey);
	}

	@Test
	public void testOverriddenOrderingKey() {
		OverridingBroker broker = new OverridingBroker();

		String orderingKey = broker.callGetOrderingKey(new Message("payload"));

		Assert.assertEquals("custom-key", orderingKey);
	}

	private static class OverridingBroker extends TestBroker {

		@Override
		protected String getOrderingKey(Message message) {
			return "custom-key";
		}

	}

	private static class TestBroker extends BaseMessageBroker {

		public String callGetOrderingKey(Message message) {
			return getOrderingKey(message);
		}

		@Override
		protected Set<String> getDeclaredTopics() {
			return Collections.emptySet();
		}

		@Override
		protected ServiceAccountCredentialsProvider
			getServiceAccountCredentialsProvider() {

			return null;
		}

	}

}