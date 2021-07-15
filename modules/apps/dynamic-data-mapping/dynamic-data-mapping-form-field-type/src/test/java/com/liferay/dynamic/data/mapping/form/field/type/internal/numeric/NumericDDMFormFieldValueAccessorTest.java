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