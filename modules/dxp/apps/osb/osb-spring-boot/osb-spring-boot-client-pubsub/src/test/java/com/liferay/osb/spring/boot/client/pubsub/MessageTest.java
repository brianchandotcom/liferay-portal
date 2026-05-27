/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Bischof
 */
public class MessageTest {

	@Test
	public void testAttributesAreObjectTyped() {
		String key1 = RandomStringUtils.randomAlphabetic(8);
		String key2 = RandomStringUtils.randomAlphabetic(8);
		String stringValue = RandomStringUtils.randomAlphanumeric(10);
		long longValue = ThreadLocalRandom.current(
		).nextLong();

		Map<String, Object> attributes = new HashMap<>();

		attributes.put(key1, longValue);
		attributes.put(key2, stringValue);

		Message message = new Message(
			attributes, RandomStringUtils.randomAlphanumeric(16));

		Map<String, Object> retrieved = message.getAttributes();

		Assert.assertEquals(longValue, retrieved.get(key1));
		Assert.assertEquals(stringValue, retrieved.get(key2));
	}

	@Test
	public void testConstructorWithAllFields() {
		String topic = RandomStringUtils.randomAlphabetic(10);
		String attrKey = RandomStringUtils.randomAlphabetic(6);
		String attrValue = RandomStringUtils.randomAlphanumeric(8);
		String payload = RandomStringUtils.randomAlphanumeric(12);

		Map<String, Object> attributes = new HashMap<>();

		attributes.put(attrKey, attrValue);

		Message message = new Message(topic, attributes, payload);

		Assert.assertEquals(topic, message.getTopic());
		Assert.assertEquals(payload, message.getPayload());
		Assert.assertEquals(attributes, message.getAttributes());
	}

	@Test
	public void testDestinationNameAliasesTopic() {
		String topic1 = RandomStringUtils.randomAlphabetic(8);
		String topic2 = RandomStringUtils.randomAlphabetic(8);

		Message message = new Message();

		message.setTopic(topic1);

		Assert.assertEquals(topic1, message.getDestinationName());

		message.setDestinationName(topic2);

		Assert.assertEquals(topic2, message.getTopic());
	}

	@Test
	public void testGetReturnsNullForMissingKey() {
		Message message = new Message();

		Assert.assertNull(message.get(RandomStringUtils.randomAlphabetic(8)));
	}

	@Test
	public void testPutLazilyInitializesAttributes() {
		String key = RandomStringUtils.randomAlphabetic(6);
		String value = RandomStringUtils.randomAlphanumeric(8);

		Message message = new Message();

		message.put(key, value);

		Assert.assertEquals(value, message.get(key));
	}

	@Test
	public void testStringAttributesConvertValues() {
		String textValue = RandomStringUtils.randomAlphanumeric(10);
		int numberValue = ThreadLocalRandom.current(
		).nextInt(
			1, 10000
		);

		Map<String, Object> attributes = new HashMap<>();

		attributes.put("bool", true);
		attributes.put("number", numberValue);
		attributes.put("text", textValue);

		Message message = new Message(
			attributes, RandomStringUtils.randomAlphanumeric(12));

		Map<String, String> stringAttributes = message.getStringAttributes();

		Assert.assertEquals(
			String.valueOf(numberValue), stringAttributes.get("number"));
		Assert.assertEquals("true", stringAttributes.get("bool"));
		Assert.assertEquals(textValue, stringAttributes.get("text"));
	}

	@Test
	public void testToString() {
		String topic = "my-topic";
		String payload = "my-payload";
		String attrKey = RandomStringUtils.randomAlphabetic(4);
		String attrValue = RandomStringUtils.randomAlphanumeric(6);

		Map<String, Object> attributes = new HashMap<>();

		attributes.put(attrKey, attrValue);

		Message message = new Message(topic, attributes, payload);

		Assert.assertTrue(
			message.toString(
			).contains(
				"topic=my-topic"
			));
		Assert.assertTrue(
			message.toString(
			).contains(
				"payload=my-payload"
			));
		Assert.assertTrue(
			message.toString(
			).contains(
				"attributes="
			));
	}

}