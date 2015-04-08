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

package com.liferay.portal.settings;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.kernel.settings.GroupServiceSettings;
import com.liferay.portal.kernel.settings.definition.SettingsDefinition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import org.testng.Assert;

/**
 * @author Iván Zaera
 */
public class SettingsDefinitionSettingsDescriptorTest {

	@Before
	public void setUp() {
		SettingsDefinition<MockSettings, MockConfiguration> settingsDefinition =
			new SettingsDefinition<MockSettings, MockConfiguration>() {

				@Override
				public Class<MockSettings> getSettingsClass() {
					return MockSettings.class;
				}

				@Override
				public Class<MockConfiguration> getConfigurationBeanClass() {
					return MockConfiguration.class;
				}

				@Override
				public Class<?> getSettingsExtraClass() {
					return null;
				}

				@Override
				public String[] getSettingsIds() {
					return _SETTINGS_IDS;
				}

			};

		_settingsDefinitionSettingsDescriptor =
			new SettingsDefinitionSettingsDescriptor(settingsDefinition, null);
	}

	@Test
	public void testGetAllKeys() {
		Set<String> allKeys =
			_settingsDefinitionSettingsDescriptor.getAllKeys();

		Collection<String> expectedAllKeys = Arrays.asList(
			"booleanValue", "longValue", "stringValue", "stringArrayValue1",
			"stringArrayValue2");

		Assert.assertEquals(allKeys.size(), expectedAllKeys.size());
		Assert.assertTrue(allKeys.containsAll(expectedAllKeys));
	}

	@Test
	public void testGetMultiValuedKeys() {
		Set<String> multiValuedKeys =
			_settingsDefinitionSettingsDescriptor.getMultiValuedKeys();

		Collection<String> expectedMultiValuedKeys = Arrays.asList(
			"stringArrayValue1", "stringArrayValue2");

		Assert.assertEquals(
			multiValuedKeys.size(), expectedMultiValuedKeys.size());
		Assert.assertTrue(multiValuedKeys.containsAll(expectedMultiValuedKeys));
	}

	@Test
	public void testGetSettingsIds() {
		Set<String> settingsIds =
			_settingsDefinitionSettingsDescriptor.getSettingsIds();

		Collection<String> expectedSettingsIds = Arrays.asList(
			"settingsId.1", "settingsId.2");

		Assert.assertEquals(settingsIds.size(), expectedSettingsIds.size());
		Assert.assertTrue(settingsIds.containsAll(expectedSettingsIds));
	}

	@Meta.OCD(
		id = "com.liferay.portal.settings.SettingsDefinitionSettingsDescriptorTest.MockConfiguration"
	)
	public interface MockConfiguration {

		@Meta.AD(
			deflt = "false", required = false
		)
		public boolean booleanValue();

		@Meta.AD(
			deflt = "0", required = false
		)
		public long longValue();

		@Meta.AD(
			deflt = "", required = false
		)
		public String[] stringArrayValue1();

		@Meta.AD(
			deflt = "", required = false
		)
		public String[] stringArrayValue2();

		@Meta.AD(
			deflt = "", required = false
		)
		public String stringValue();

	}

	public interface MockSettings
		extends GroupServiceSettings, MockConfiguration {
	}

	private static final String[] _SETTINGS_IDS = {
		"settingsId.1", "settingsId.2"};

	private SettingsDefinitionSettingsDescriptor
		_settingsDefinitionSettingsDescriptor;

}