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
 * @author Iván Zaera
 */
public class AnnotatedSettingsDescriptorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetAllKeys() {
		Set<String> allKeys1 = _annotatedSettingsDescriptor.getAllKeys();

		Collection<String> expectedAllKeys = Arrays.asList(
			"boolean", "long", "string", "stringArray1", "stringArray2",
			"unrenamedProperty");

		Assert.assertEquals(
			allKeys1.toString(), expectedAllKeys.size(), allKeys1.size());
		Assert.assertTrue(allKeys1.containsAll(expectedAllKeys));

		allKeys1.remove("long");

		Set<String> allKeys2 = _annotatedSettingsDescriptor.getAllKeys();

		Assert.assertTrue(allKeys2.containsAll(expectedAllKeys));
	}

	@Test
	public void testGetMultiValuedKeys() {
		Set<String> multiValuedKeys1 =
			_annotatedSettingsDescriptor.getMultiValuedKeys();

		Collection<String> expectedMultiValuedKeys = Arrays.asList(
			"stringArray1", "stringArray2");

		Assert.assertEquals(
			multiValuedKeys1.toString(), expectedMultiValuedKeys.size(),
			multiValuedKeys1.size());
		Assert.assertTrue(
			multiValuedKeys1.containsAll(expectedMultiValuedKeys));

		multiValuedKeys1.remove("stringArray1");

		Set<String> multiValuedKeys2 =
			_annotatedSettingsDescriptor.getMultiValuedKeys();

		Assert.assertTrue(
			multiValuedKeys2.containsAll(expectedMultiValuedKeys));
	}

	@Settings.Config
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