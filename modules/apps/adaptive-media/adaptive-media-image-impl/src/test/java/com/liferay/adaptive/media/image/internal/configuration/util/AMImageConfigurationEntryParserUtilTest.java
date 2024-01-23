/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.configuration.util;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.internal.configuration.AMImageConfigurationEntryImpl;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AMImageConfigurationEntryParserUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDisabledValidString() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			AMImageConfigurationEntryParserUtil.parse(
				"test:desc:12345:max-height=100;max-width=200:enabled=false");

		Assert.assertEquals("test", amImageConfigurationEntry.getName());
		Assert.assertEquals("desc", amImageConfigurationEntry.getDescription());
		Assert.assertEquals("12345", amImageConfigurationEntry.getUUID());
		Assert.assertFalse(amImageConfigurationEntry.isEnabled());

		Map<String, String> properties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyAttributes() {
		AMImageConfigurationEntryParserUtil.parse("test:desc:12345:");
	}

	@Test
	public void testEmptyDescription() {
		AMImageConfigurationEntryParserUtil.parse(
			"test::12345:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyName() {
		AMImageConfigurationEntryParserUtil.parse(
			":desc:12345:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyString() {
		AMImageConfigurationEntryParserUtil.parse("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyUUID() {
		AMImageConfigurationEntryParserUtil.parse(
			"test:desc::max-height=100;max-width=200");
	}

	@Test
	public void testEncodedDescription() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			AMImageConfigurationEntryParserUtil.parse(
				"test:desc%3A%3B:12345:max-height=100;max-width=200");

		Assert.assertEquals("test", amImageConfigurationEntry.getName());
		Assert.assertEquals(
			"desc:;", amImageConfigurationEntry.getDescription());
		Assert.assertEquals("12345", amImageConfigurationEntry.getUUID());

		Map<String, String> properties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
	}

	@Test
	public void testEncodedName() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			AMImageConfigurationEntryParserUtil.parse(
				"test%3A%3B:desc:12345:max-height=100;max-width=200");

		Assert.assertEquals("test:;", amImageConfigurationEntry.getName());
		Assert.assertEquals("desc", amImageConfigurationEntry.getDescription());
		Assert.assertEquals("12345", amImageConfigurationEntry.getUUID());

		Map<String, String> properties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
	}

	@Test
	public void testGetConfigurationStringWithMaxHeight() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345",
				HashMapBuilder.put(
					"max-height", "100"
				).build(),
				true);

		Assert.assertEquals(
			"test:desc:12345:max-height=100:enabled=true",
			AMImageConfigurationEntryParserUtil.getConfigurationString(
				amImageConfigurationEntry));
	}

	@Test
	public void testGetConfigurationStringWithMaxHeightAndMaxWidth() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "200"
				).build(),
				true);

		Assert.assertEquals(
			"test:desc:12345:max-height=100;max-width=200:enabled=true",
			AMImageConfigurationEntryParserUtil.getConfigurationString(
				amImageConfigurationEntry));
	}

	@Test
	public void testGetConfigurationStringWithMaxWidth() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345",
				HashMapBuilder.put(
					"max-width", "200"
				).build(),
				true);

		Assert.assertEquals(
			"test:desc:12345:max-width=200:enabled=true",
			AMImageConfigurationEntryParserUtil.getConfigurationString(
				amImageConfigurationEntry));
	}

	@Test
	public void testGetConfigurationStringWithNoProperties() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", Collections.emptyMap(), true);

		Assert.assertEquals(
			"test:desc:12345::enabled=true",
			AMImageConfigurationEntryParserUtil.getConfigurationString(
				amImageConfigurationEntry));
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxHeight() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345",
				HashMapBuilder.put(
					"max-height", "100"
				).build(),
				false);

		Assert.assertEquals(
			"test:desc:12345:max-height=100:enabled=false",
			AMImageConfigurationEntryParserUtil.getConfigurationString(
				amImageConfigurationEntry));
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxHeightAndMaxWidth() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "200"
				).build(),
				false);

		Assert.assertEquals(
			"test:desc:12345:max-height=100;max-width=200:enabled=false",
			AMImageConfigurationEntryParserUtil.getConfigurationString(
				amImageConfigurationEntry));
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxWidth() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345",
				HashMapBuilder.put(
					"max-width", "200"
				).build(),
				false);

		Assert.assertEquals(
			"test:desc:12345:max-width=200:enabled=false",
			AMImageConfigurationEntryParserUtil.getConfigurationString(
				amImageConfigurationEntry));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidEnabledAttribute() {
		AMImageConfigurationEntryParserUtil.parse(
			"test:desc:12345:max-height=100;max-width=200:disabled=true");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingAttributesField() {
		AMImageConfigurationEntryParserUtil.parse("test:desc:12345");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingDescription() {
		AMImageConfigurationEntryParserUtil.parse(
			"12345:max-height=100;max-width=200");
	}

	@Test
	public void testMissingEnabledAttributeDefaultsEnabled() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			AMImageConfigurationEntryParserUtil.parse(
				"test:desc:12345:max-height=100;max-width=200");

		Assert.assertEquals("test", amImageConfigurationEntry.getName());
		Assert.assertEquals("12345", amImageConfigurationEntry.getUUID());
		Assert.assertEquals("desc", amImageConfigurationEntry.getDescription());
		Assert.assertTrue(amImageConfigurationEntry.isEnabled());

		Map<String, String> properties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingName() {
		AMImageConfigurationEntryParserUtil.parse(
			"12345:desc:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingUUID() {
		AMImageConfigurationEntryParserUtil.parse(
			"test:desc:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullString() {
		AMImageConfigurationEntryParserUtil.parse(null);
	}

	@Test
	public void testValidString() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			AMImageConfigurationEntryParserUtil.parse(
				"test:desc:12345:max-height=100;max-width=200:enabled=true");

		Assert.assertEquals("test", amImageConfigurationEntry.getName());
		Assert.assertEquals("desc", amImageConfigurationEntry.getDescription());
		Assert.assertEquals("12345", amImageConfigurationEntry.getUUID());
		Assert.assertTrue(amImageConfigurationEntry.isEnabled());

		Map<String, String> properties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
	}

}