/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Bischof
 */
public class MessageTest {

	@Test
	public void testConstructorDefensiveCopiesAttributes() {
		String key = RandomStringUtils.randomAlphabetic(8);
		String value = RandomStringUtils.randomAlphanumeric(10);

		Map<String, String> attributes = new HashMap<>(
			Collections.singletonMap(key, value));

		Message message = new Message(
			attributes, RandomStringUtils.randomAlphanumeric(12),
			RandomStringUtils.randomAlphabetic(6));

		attributes.put(
			RandomStringUtils.randomAlphabetic(8),
			RandomStringUtils.randomAlphanumeric(10));

		Assert.assertEquals(value, message.get(key));

		Map<String, String> messageAttributes = message.getAttributes();

		Assert.assertEquals(
			messageAttributes.toString(), 1, messageAttributes.size());
	}

	@Test
	public void testGetAttributesIsUnmodifiable() {
		String key = RandomStringUtils.randomAlphabetic(8);
		String value = RandomStringUtils.randomAlphanumeric(10);

		Message message = new Message(
			Collections.singletonMap(key, value),
			RandomStringUtils.randomAlphanumeric(12),
			RandomStringUtils.randomAlphabetic(6));

		Map<String, String> messageAttributes = message.getAttributes();

		Assert.assertThrows(
			UnsupportedOperationException.class,
			() -> messageAttributes.put(
				RandomStringUtils.randomAlphabetic(8),
				RandomStringUtils.randomAlphanumeric(10)));

		Assert.assertEquals(value, message.get(key));
		Assert.assertEquals(
			messageAttributes.toString(), 1, messageAttributes.size());
	}

	@Test
	public void testGetAttributesReturnsEmptyMapWhenNull() {
		Message message = new Message(
			null, RandomStringUtils.randomAlphanumeric(12),
			RandomStringUtils.randomAlphabetic(6));

		Map<String, String> messageAttributes = message.getAttributes();

		Assert.assertTrue(
			messageAttributes.toString(), messageAttributes.isEmpty());
	}

	@Test
	public void testGetReturnsNullWhenAttributesNull() {
		Message message = new Message(
			null, RandomStringUtils.randomAlphanumeric(12),
			RandomStringUtils.randomAlphabetic(6));

		Assert.assertNull(message.get(RandomStringUtils.randomAlphabetic(8)));
	}

	@Test
	public void testPutLazilyInitializesAttributes() {
		String key = RandomStringUtils.randomAlphabetic(8);
		String value = RandomStringUtils.randomAlphanumeric(10);

		Message message = new Message(
			null, RandomStringUtils.randomAlphanumeric(12),
			RandomStringUtils.randomAlphabetic(6));

		message.put(key, value);

		Assert.assertEquals(value, message.get(key));

		Map<String, String> messageAttributes = message.getAttributes();

		Assert.assertEquals(value, messageAttributes.get(key));
	}

	@Test
	public void testSetAttributesDefensiveCopy() {
		String key = RandomStringUtils.randomAlphabetic(8);
		String value = RandomStringUtils.randomAlphanumeric(10);

		Map<String, String> attributes = new HashMap<>(
			Collections.singletonMap(key, value));

		Message message = new Message(
			null, RandomStringUtils.randomAlphanumeric(12),
			RandomStringUtils.randomAlphabetic(6));

		message.setAttributes(attributes);

		attributes.put(
			RandomStringUtils.randomAlphabetic(8),
			RandomStringUtils.randomAlphanumeric(10));

		Assert.assertEquals(value, message.get(key));

		Map<String, String> messageAttributes = message.getAttributes();

		Assert.assertEquals(
			messageAttributes.toString(), 1, messageAttributes.size());
	}

	@Test
	public void testSetAttributesNullNullsMap() {
		String key = RandomStringUtils.randomAlphabetic(8);

		Map<String, String> attributes = Collections.singletonMap(
			key, RandomStringUtils.randomAlphanumeric(10));

		Message message = new Message(
			attributes, RandomStringUtils.randomAlphanumeric(12),
			RandomStringUtils.randomAlphabetic(6));

		message.setAttributes(null);

		Assert.assertNull(message.get(key));

		Map<String, String> messageAttributes = message.getAttributes();

		Assert.assertTrue(
			messageAttributes.toString(), messageAttributes.isEmpty());
	}

	@Test
	public void testToStringFormat() {
		String key = RandomStringUtils.randomAlphabetic(8);
		String value = RandomStringUtils.randomAlphanumeric(10);
		String payload = RandomStringUtils.randomAlphanumeric(12);
		String topic = RandomStringUtils.randomAlphabetic(6);

		Map<String, String> attributes = Collections.singletonMap(key, value);

		Message message = new Message(attributes, payload, topic);

		Assert.assertEquals(
			"{topic=" + topic + ", attributes={" + key + "=" + value +
				"}, payload=" + payload + "}",
			message.toString());
	}

	@Test
	public void testToStringNullAttributes() {
		Message message = new Message(
			null, RandomStringUtils.randomAlphanumeric(12),
			RandomStringUtils.randomAlphabetic(6));

		Assert.assertTrue(
			message.toString(),
			message.toString(
			).contains(
				"attributes=null"
			));
	}

}