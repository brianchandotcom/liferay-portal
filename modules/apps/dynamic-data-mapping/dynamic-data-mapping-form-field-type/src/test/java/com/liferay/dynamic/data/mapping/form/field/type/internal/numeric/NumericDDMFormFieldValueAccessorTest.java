/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 */
public class NumericDDMFormFieldValueAccessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetValueForEvaluation1() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Numeric",
				DDMFormValuesTestUtil.createLocalizedValue(
					"2.5", "1,5", LocaleUtil.US));

		Assert.assertEquals(
			new BigDecimal(1.5),
			_numericDDMFormFieldValueAccessor.getValueForEvaluation(
				ddmFormFieldValue, LocaleUtil.BRAZIL));
		Assert.assertEquals(
			new BigDecimal(2.5),
			_numericDDMFormFieldValueAccessor.getValueForEvaluation(
				ddmFormFieldValue, LocaleUtil.US));
	}

	@Test
	public void testGetValueForEvaluation2() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Numeric",
				DDMFormValuesTestUtil.createLocalizedValue(
					"2,5", "1.5", LocaleUtil.US));

		Assert.assertEquals(
			new BigDecimal(1.5),
			_numericDDMFormFieldValueAccessor.getValueForEvaluation(
				ddmFormFieldValue, LocaleUtil.BRAZIL));
		Assert.assertEquals(
			new BigDecimal(2.5),
			_numericDDMFormFieldValueAccessor.getValueForEvaluation(
				ddmFormFieldValue, LocaleUtil.US));
	}

	private final NumericDDMFormFieldValueAccessor
		_numericDDMFormFieldValueAccessor =
			new NumericDDMFormFieldValueAccessor();

}