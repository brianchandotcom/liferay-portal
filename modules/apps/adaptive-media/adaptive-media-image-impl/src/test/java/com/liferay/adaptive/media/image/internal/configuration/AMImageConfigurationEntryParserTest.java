/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AMImageConfigurationEntryParserTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_amImageConfigurationEntryParser =
			new AMImageConfigurationEntryParser();
	}

	@Test
	public void testDisabledValidString() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			_amImageConfigurationEntryParser.parse(
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
		_amImageConfigurationEntryParser.parse("test:desc:12345:");
	}

	@Test
	public void testEmptyDescription() {
		_amImageConfigurationEntryParser.parse(
			"test::12345:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyName() {
		_amImageConfigurationEntryParser.parse(
			":desc:12345:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyString() {
		_amImageConfigurationEntryParser.parse("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyUUID() {
		_amImageConfigurationEntryParser.parse(
			"test:desc::max-height=100;max-width=200");
	}

	@Test
	public void testEncodedDescription() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			_amImageConfigurationEntryParser.parse(
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
			_amImageConfigurationEntryParser.parse(
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
			_amImageConfigurationEntryParser.getConfigurationString(
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
			_amImageConfigurationEntryParser.getConfigurationString(
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
			_amImageConfigurationEntryParser.getConfigurationString(
				amImageConfigurationEntry));
	}

	@Test
	public void testGetConfigurationStringWithNoProperties() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", Collections.emptyMap(), true);

		Assert.assertEquals(
			"test:desc:12345::enabled=true",
			_amImageConfigurationEntryParser.getConfigurationString(
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
			_amImageConfigurationEntryParser.getConfigurationString(
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
			_amImageConfigurationEntryParser.getConfigurationString(
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
			_amImageConfigurationEntryParser.getConfigurationString(
				amImageConfigurationEntry));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidEnabledAttribute() {
		_amImageConfigurationEntryParser.parse(
			"test:desc:12345:max-height=100;max-width=200:disabled=true");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingAttributesField() {
		_amImageConfigurationEntryParser.parse("test:desc:12345");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingDescription() {
		_amImageConfigurationEntryParser.parse(
			"12345:max-height=100;max-width=200");
	}

	@Test
	public void testMissingEnabledAttributeDefaultsEnabled() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			_amImageConfigurationEntryParser.parse(
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
		_amImageConfigurationEntryParser.parse(
			"12345:desc:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingUUID() {
		_amImageConfigurationEntryParser.parse(
			"test:desc:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullString() {
		_amImageConfigurationEntryParser.parse(null);
	}

	@Test
	public void testValidString() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			_amImageConfigurationEntryParser.parse(
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

	private AMImageConfigurationEntryParser _amImageConfigurationEntryParser;

}