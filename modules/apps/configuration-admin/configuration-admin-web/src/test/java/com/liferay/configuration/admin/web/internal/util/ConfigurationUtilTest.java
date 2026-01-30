/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.exception.ConfigurationValidationException;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Thiago Buarque
 */
public class ConfigurationUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidateProperties() {
		MockedStatic<LanguageUtil> languageUtilMockedStatic =
			Mockito.mockStatic(LanguageUtil.class);

		languageUtilMockedStatic.when(
			() -> LanguageUtil.get(
				Mockito.any(Locale.class), Mockito.anyString())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		try {
			ConfigurationUtil.validateProperties(
				TestConfiguration.class, LocaleUtil.US,
				HashMapDictionaryBuilder.<String, Object>put(
					"maxMinKey", "6"
				).put(
					"requiredKey", RandomTestUtil.randomString()
				).build());

			Assert.fail();
		}
		catch (ConfigurationValidationException
					configurationValidationException) {

			Assert.assertEquals(
				"The maximum value for property \"maxMinKey\" is 5",
				configurationValidationException.getMessage());
		}

		try {
			ConfigurationUtil.validateProperties(
				TestConfiguration.class, LocaleUtil.US,
				HashMapDictionaryBuilder.<String, Object>put(
					"maxMinKey", "0"
				).put(
					"requiredKey", RandomTestUtil.randomString()
				).build());

			Assert.fail();
		}
		catch (ConfigurationValidationException
					configurationValidationException) {

			Assert.assertEquals(
				"The minimum value for property \"maxMinKey\" is 1",
				configurationValidationException.getMessage());
		}

		try {
			ConfigurationUtil.validateProperties(
				TestConfiguration.class, LocaleUtil.US,
				new HashMapDictionary<>());

			Assert.fail();
		}
		catch (ConfigurationValidationException
					configurationValidationException) {

			Assert.assertEquals(
				"Property \"requiredKey\" is required",
				configurationValidationException.getMessage());
		}
	}

	@ExtendedObjectClassDefinition(
		scope = ExtendedObjectClassDefinition.Scope.GROUP
	)
	@Meta.OCD(
		id = "com.liferay.configuration.admin.web.internal.util.ConfigurationUtilTest"
	)
	private interface TestConfiguration {

		@Meta.AD(max = "5", min = "1", required = false)
		public int maxMinKey();

		@Meta.AD
		public String requiredKey();

	}

}