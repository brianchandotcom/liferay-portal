/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.taglib.servlet.taglib;

import com.liferay.data.engine.taglib.servlet.taglib.DataLayoutBuilderTag.DataLayoutDDMFormAdapter;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Igor Franca
 */
public class DataLayoutBuilderTagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_dataLayoutDDMFormAdapter = new DataLayoutDDMFormAdapter(
			Collections.singleton(LocaleUtil.US), null, null, null, null);
	}

	@Test
	public void testCreateDDMFormFieldValue() throws Exception {
		String propertyValue = RandomTestUtil.randomString();

		_testCreateDDMFormFieldValue(propertyValue, propertyValue);

		_testCreateDDMFormFieldValue(null, StringPool.BLANK);
	}

	private void _testCreateDDMFormFieldValue(
			String actualPropertyValue, String expectedPropertyValue)
		throws Exception {

		DDMFormField ddmFormField = Mockito.mock(DDMFormField.class);

		Value value = _dataLayoutDDMFormAdapter.createDDMFormFieldValue(
			Collections.singleton(LocaleUtil.US), ddmFormField,
			actualPropertyValue);

		Assert.assertTrue(value instanceof UnlocalizedValue);
		Assert.assertEquals(
			expectedPropertyValue, value.getString(LocaleUtil.US));
	}

	private DataLayoutDDMFormAdapter _dataLayoutDDMFormAdapter;

}