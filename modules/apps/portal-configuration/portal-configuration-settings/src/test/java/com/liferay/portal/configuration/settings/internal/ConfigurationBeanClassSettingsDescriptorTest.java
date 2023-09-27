/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Settings.Config
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