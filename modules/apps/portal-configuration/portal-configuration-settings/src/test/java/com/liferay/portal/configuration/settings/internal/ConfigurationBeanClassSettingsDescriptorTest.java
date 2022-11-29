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
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Riccardo Ferrari
 */
public class ConfigurationBeanClassSettingsDescriptorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetAllKeys() {
		Set<String> allKeys1 =
			_configurationBeanClassSettingsDescriptor.getAllKeys();

		Collection<String> expectedAllKeys = Arrays.asList(
			"getBoolean", "getLong", "getString", "getStringArray1",
			"getStringArray2", "toRemove");

		Assert.assertEquals(
			allKeys1.toString(), expectedAllKeys.size(), allKeys1.size());
		Assert.assertTrue(allKeys1.containsAll(expectedAllKeys));

		allKeys1.remove("toRemove");

		Set<String> allKeys2 =
			_configurationBeanClassSettingsDescriptor.getAllKeys();

		Assert.assertTrue(allKeys2.containsAll(expectedAllKeys));
	}

	@Test
	public void testGetMultiValuedKeys() {
		Set<String> multiValuedKeys1 =
			_configurationBeanClassSettingsDescriptor.getMultiValuedKeys();

		Collection<String> expectedMultiValuedKeys = Arrays.asList(
			"getStringArray1", "getStringArray2");

		Assert.assertEquals(
			multiValuedKeys1.toString(), expectedMultiValuedKeys.size(),
			multiValuedKeys1.size());
		Assert.assertTrue(
			multiValuedKeys1.containsAll(expectedMultiValuedKeys));

		multiValuedKeys1.remove("getStringArray1");

		Set<String> multiValuedKeys2 =
			_configurationBeanClassSettingsDescriptor.getMultiValuedKeys();

		Assert.assertTrue(
			multiValuedKeys2.containsAll(expectedMultiValuedKeys));
	}

	@Settings.Config(settingsIds = "settingsId.1")
	public interface MockSettings {

		public boolean getBoolean();

		public long getLong();

		public String getString();

		public String[] getStringArray1();

		public String[] getStringArray2();

		public String toRemove();

	}

	private final ConfigurationBeanClassSettingsDescriptor
		_configurationBeanClassSettingsDescriptor =
			new ConfigurationBeanClassSettingsDescriptor(
				ConfigurationBeanClassSettingsDescriptorTest.MockSettings.
					class);

}