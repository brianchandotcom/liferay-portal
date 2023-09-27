/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcos Martins
 */
public class NumericDDMFormFieldValueLocalizerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testLocalize() {
		String localizedValue = _numericDDMFormFieldValueLocalizer.localize(
			"1000.95", LocaleUtil.US);

		Assert.assertEquals("1000.95", localizedValue);
	}

	@Test
	public void testLocalizeWithEditingValueEndingWithPeriod() {
		_numericDDMFormFieldValueLocalizer.setEditingFieldValue(true);

		String localizedValue = _numericDDMFormFieldValueLocalizer.localize(
			"1000.", LocaleUtil.US);

		Assert.assertEquals("1000.", localizedValue);
	}

	@Test
	public void testLocalizeWithNotEditingValueEndingWithPeriod() {
		_numericDDMFormFieldValueLocalizer.setEditingFieldValue(false);

		String localizedValue = _numericDDMFormFieldValueLocalizer.localize(
			"1000.", LocaleUtil.US);

		Assert.assertEquals("1000", localizedValue);
	}

	private final NumericDDMFormFieldValueLocalizer
		_numericDDMFormFieldValueLocalizer =
			new NumericDDMFormFieldValueLocalizer();

}