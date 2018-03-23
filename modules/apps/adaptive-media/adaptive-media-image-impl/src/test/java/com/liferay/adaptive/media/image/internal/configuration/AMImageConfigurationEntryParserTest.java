/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.URLCodec;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adolfo Pérez
 */
@PrepareForTest(URLCodec.class)
@RunWith(PowerMockRunner.class)
public class AMImageConfigurationEntryParserTest extends PowerMockito {

	@Before
	public void setUp() {
		_http = mock(Http.class);

		_amImageConfigurationEntryParser = new AMImageConfigurationEntryParser(
			_http);

		PowerMockito.mockStatic(URLCodec.class);

		when(
			URLCodec.encodeURL(Mockito.eq("desc"))
		).thenReturn(
			"desc"
		);

		when(
			_http.decodeURL(Mockito.eq("desc"))
		).thenReturn(
			"desc"
		);

		when(
			URLCodec.encodeURL(Mockito.eq("test"))
		).thenReturn(
			"test"
		);

		when(
			_http.decodeURL(Mockito.eq("test"))
		).thenReturn(
			"test"
		);
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
		when(
			URLCodec.encodeURL(Mockito.eq("desc:;"))
		).thenReturn(
			"desc%3A%3B"
		);

		when(
			_http.decodeURL(Mockito.eq("desc%3A%3B"))
		).thenReturn(
			"desc:;"
		);

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
		when(
			URLCodec.encodeURL(Mockito.eq("test:;"))
		).thenReturn(
			"test%3A%3B"
		);

		when(
			_http.decodeURL(Mockito.eq("test%3A%3B"))
		).thenReturn(
			"test:;"
		);

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
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		String configurationString =
			_amImageConfigurationEntryParser.getConfigurationString(
				amImageConfigurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-height=100:enabled=true", configurationString);
	}

	@Test
	public void testGetConfigurationStringWithMaxHeightAndMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "200");

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		String configurationString =
			_amImageConfigurationEntryParser.getConfigurationString(
				amImageConfigurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-height=100;max-width=200:enabled=true",
			configurationString);
	}

	@Test
	public void testGetConfigurationStringWithMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "200");

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		String configurationString =
			_amImageConfigurationEntryParser.getConfigurationString(
				amImageConfigurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-width=200:enabled=true", configurationString);
	}

	@Test
	public void testGetConfigurationStringWithNoProperties() {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", Collections.emptyMap(), true);

		String configurationString =
			_amImageConfigurationEntryParser.getConfigurationString(
				amImageConfigurationEntry);

		Assert.assertEquals(
			"test:desc:12345::enabled=true", configurationString);
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxHeight() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, false);

		String configurationString =
			_amImageConfigurationEntryParser.getConfigurationString(
				amImageConfigurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-height=100:enabled=false",
			configurationString);
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxHeightAndMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "200");

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, false);

		String configurationString =
			_amImageConfigurationEntryParser.getConfigurationString(
				amImageConfigurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-height=100;max-width=200:enabled=false",
			configurationString);
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "200");

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, false);

		String configurationString =
			_amImageConfigurationEntryParser.getConfigurationString(
				amImageConfigurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-width=200:enabled=false", configurationString);
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
	private Http _http;

}