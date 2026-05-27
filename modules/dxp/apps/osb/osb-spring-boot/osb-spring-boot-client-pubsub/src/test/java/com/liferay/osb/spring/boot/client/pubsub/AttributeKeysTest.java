/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Bischof
 */
public class AttributeKeysTest {

	@Test
	public void testAllLegacyKeysPresent() {
		Assert.assertEquals("appId", AttributeKeys.APP_ID);
		Assert.assertEquals("clusterId", AttributeKeys.CLUSTER_ID);
		Assert.assertEquals("contentEncoding", AttributeKeys.CONTENT_ENCODING);
		Assert.assertEquals("contentType", AttributeKeys.CONTENT_TYPE);
		Assert.assertEquals("correlationId", AttributeKeys.CORRELATION_ID);
		Assert.assertEquals("deliveryMode", AttributeKeys.DELIVERY_MODE);
		Assert.assertEquals("expiration", AttributeKeys.EXPIRATION);
		Assert.assertEquals("headers", AttributeKeys.HEADERS);
		Assert.assertEquals("messageId", AttributeKeys.MESSAGE_ID);
		Assert.assertEquals("priority", AttributeKeys.PRIORITY);
		Assert.assertEquals("replyTo", AttributeKeys.REPLY_TO);
		Assert.assertEquals("timestamp", AttributeKeys.TIMESTAMP);
		Assert.assertEquals("type", AttributeKeys.TYPE);
		Assert.assertEquals("userId", AttributeKeys.USER_ID);
	}

}