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

package com.liferay.portal.kernel.settings;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * @author Iván Zaera
 */
public class FallbackSettingsTest {

	public FallbackSettingsTest() {
		_settings = Mockito.mock(Settings.class);

		_fallbackKeys.add("key1", "key2", "key3");
		_fallbackKeys.add("key2", "key7");
		_fallbackKeys.add("key3", "key5");

		_fallbackSettings = new FallbackSettings(_settings, _fallbackKeys);
	}

	@Test
	public void testGetValuesWhenConfigured() {
		String[] defaultValues = {"default"};

		String[] mockValues = {"value"};

		Mockito.when(
			_settings.getValues("key2", null)
		).thenReturn(
			mockValues
		);

		Assert.assertArrayEquals(
			mockValues, _fallbackSettings.getValues("key1", defaultValues));

		verifyGetValues("key1", "key2");
	}

	@Test
	public void testGetValuesWhenUnconfigured() {
		String[] defaultValues = {"default"};

		Assert.assertArrayEquals(
			defaultValues, _fallbackSettings.getValues("key1", defaultValues));

		verifyGetValues("key1", "key2", "key3");
	}

	@Test
	public void testGetValueWhenConfigured() {
		Mockito.when(
			_settings.getValue("key2", null)
		).thenReturn(
			"value"
		);

		Assert.assertEquals(
			"value", _fallbackSettings.getValue("key1", "default"));

		verifyGetValue("key1", "key2");
	}

	@Test
	public void testGetValueWhenUnconfigured() {
		Assert.assertEquals(
			"default", _fallbackSettings.getValue("key1", "default"));

		verifyGetValue("key1", "key2", "key3");
	}

	protected void verifyGetValue(String... keys) {
		InOrder inOrder = Mockito.inOrder(_settings);

		for (String key : keys) {
			inOrder.verify(
				_settings
			).getValue(
				key, null
			);
		}
	}

	protected void verifyGetValues(String... keys) {
		InOrder inOrder = Mockito.inOrder(_settings);

		for (String key : keys) {
			inOrder.verify(
				_settings
			).getValues(
				key, null
			);
		}
	}

	private final FallbackKeys _fallbackKeys = new FallbackKeys();
	private final FallbackSettings _fallbackSettings;
	private final Settings _settings;

}