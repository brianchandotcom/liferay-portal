/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Bischof
 */
public class BasePubsubClientTest {

	@Test
	public void testGetDeadLetterTopicDefault() {
		TestClient testClient = new TestClient();

		Assert.assertEquals(
			"dead-letter",
			testClient.getDeadLetterTopic(
				RandomStringUtils.randomAlphabetic(6)));
	}

	@Test
	public void testIsAutoCreateTopicDefaultsToTrue() {
		TestClient testClient = new TestClient();

		Assert.assertTrue(testClient.isAutoCreateTopic());
	}

	@Test
	public void testIsDeadLetterEnabledDefaultsToTrue() {
		TestClient testClient = new TestClient();

		Assert.assertTrue(testClient.isDeadLetterEnabled());
	}

	private static class TestClient extends BasePubsubClient {

		@Override
		protected String getProjectId() {
			return "test-project";
		}

	}

}