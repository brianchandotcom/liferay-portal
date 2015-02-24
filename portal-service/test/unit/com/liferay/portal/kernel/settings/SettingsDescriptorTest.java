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

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import org.testng.Assert;

/**
 * @author Iván Zaera
 */
public class SettingsDescriptorTest {

	@Test
	public void testGetAllKeys() {
		Set<String> allKeys = _settingsDescriptor.getAllKeys();

		Collection<String> expectedAllKeys = Arrays.asList(
			"boolean", "long", "string", "stringArray", "stringArray2",
			"nonRenamedProperty");

		Assert.assertEquals(allKeys.size(), expectedAllKeys.size());
		Assert.assertTrue(allKeys.containsAll(expectedAllKeys));
	}

	@Test
	public void testGetMultiValuedKeys() {
		Set<String> multiValuedKeys = _settingsDescriptor.getMultiValuedKeys();

		Collection<String> expectedMultiValuedKeys = Arrays.asList(
			"stringArray", "stringArray2");

		Assert.assertEquals(
			multiValuedKeys.size(), expectedMultiValuedKeys.size());
		Assert.assertTrue(multiValuedKeys.containsAll(expectedMultiValuedKeys));
	}

	@Test
	public void testGetSettingsIds() {
		Set<String> settingsIds = _settingsDescriptor.getSettingsIds();

		Collection<String> expectedSettingsIds = Arrays.asList(
			"settingsId.1", "settingsId.2");

		Assert.assertEquals(settingsIds.size(), expectedSettingsIds.size());
		Assert.assertTrue(settingsIds.containsAll(expectedSettingsIds));
	}

	@Settings.Config(settingsIds = {"settingsId.1", "settingsId.2"})
	public class MockSettings {

		public boolean getBoolean() {
			return false;
		}

		@Settings.Property(ignore = true)
		public String getIgnoredProperty() {
			return "";
		}

		public long getLong() {
			return 0;
		}

		@Settings.Property(name = "nonRenamedProperty")
		public String getRenamedProperty() {
			return "";
		}

		public String getString() {
			return "";
		}

		public String[] getStringArray() {
			return new String[0];
		}

		public String[] getStringArray2() {
			return new String[0];
		}

	}

	private final SettingsDescriptor<MockSettings> _settingsDescriptor =
		new SettingsDescriptor<>(MockSettings.class);

}