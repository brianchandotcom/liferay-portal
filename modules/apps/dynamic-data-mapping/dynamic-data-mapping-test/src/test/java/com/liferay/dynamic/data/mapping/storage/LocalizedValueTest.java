/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class LocalizedValueTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEqualsWithDifferentDefaultLocaleAndSameValuesMap() {
		Value value1 = new LocalizedValue(LocaleUtil.US);
		Value value2 = new LocalizedValue(LocaleUtil.BRAZIL);

		Assert.assertNotEquals(value1, value2);
	}

	@Test
	public void testEqualsWithSameDefaultLocaleAndDifferentValuesMap() {
		Value value1 = new LocalizedValue(LocaleUtil.US);

		value1.addString(LocaleUtil.US, "Test");
		value1.addString(LocaleUtil.BRAZIL, "Teste");

		Value value2 = new LocalizedValue(LocaleUtil.US);

		value2.addString(LocaleUtil.US, "Different Test");
		value2.addString(LocaleUtil.BRAZIL, "Teste");

		Assert.assertNotEquals(value1, value2);
	}

	@Test
	public void testEqualsWithSameDefaultLocaleAndSameValuesMap() {
		Value value1 = new LocalizedValue(LocaleUtil.US);

		value1.addString(LocaleUtil.US, "Test");
		value1.addString(LocaleUtil.BRAZIL, "Teste");

		Value value2 = new LocalizedValue(LocaleUtil.US);

		value2.addString(LocaleUtil.US, "Test");
		value2.addString(LocaleUtil.BRAZIL, "Teste");

		Assert.assertEquals(value1, value2);
	}

}