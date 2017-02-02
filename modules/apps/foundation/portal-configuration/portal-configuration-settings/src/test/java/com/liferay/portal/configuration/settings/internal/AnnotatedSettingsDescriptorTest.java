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

package com.liferay.portal.configuration.settings.internal;

import com.liferay.portal.kernel.settings.Settings;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class AnnotatedSettingsDescriptorTest {

	@Test
	public void testGetAllKeys() {
		Set<String> allKeys = _annotatedSettingsDescriptor.getAllKeys();

		Collection<String> expectedAllKeys = Arrays.asList(
			"boolean", "long", "string", "stringArray1", "stringArray2",
			"unrenamedProperty");

		Assert.assertEquals(
			allKeys.toString(), expectedAllKeys.size(), allKeys.size());
		Assert.assertTrue(allKeys.containsAll(expectedAllKeys));
	}

	@Test
	public void testGetMultiValuedKeys() {
		Set<String> multiValuedKeys =
			_annotatedSettingsDescriptor.getMultiValuedKeys();

		Collection<String> expectedMultiValuedKeys = Arrays.asList(
			"stringArray1", "stringArray2");

		Assert.assertEquals(
			multiValuedKeys.toString(), expectedMultiValuedKeys.size(),
			multiValuedKeys.size());
		Assert.assertTrue(multiValuedKeys.containsAll(expectedMultiValuedKeys));
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

		@Settings.Property(name = "unrenamedProperty")
		public String getRenamedProperty() {
			return "";
		}

		public String getString() {
			return "";
		}

		public String[] getStringArray1() {
			return new String[0];
		}

		public String[] getStringArray2() {
			return new String[0];
		}

	}

	private final AnnotatedSettingsDescriptor _annotatedSettingsDescriptor =
		new AnnotatedSettingsDescriptor(MockSettings.class);

}