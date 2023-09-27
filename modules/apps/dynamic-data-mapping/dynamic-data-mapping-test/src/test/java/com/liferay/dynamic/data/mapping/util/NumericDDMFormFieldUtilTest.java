/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mariano Álvaro Sáiz
 */
@NewEnv(type = NewEnv.Type.JVM)
@NewEnv.JVMArgsLine("-Djava.locale.providers=JRE,COMPAT,CLDR")
public class NumericDDMFormFieldUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetDecimalFormatWithDecimalArabicSeparator()
		throws Exception {

		DecimalFormat decimalFormat = NumericDDMFormFieldUtil.getDecimalFormat(
			new Locale("ar", "SA"));

		Assert.assertEquals("1٫2345678", decimalFormat.format(1.2345678));
		Assert.assertEquals(
			BigDecimal.valueOf(1.2345678), decimalFormat.parse("1٫2345678"));
	}

	@Test
	public void testGetDecimalFormatWithDecimalComma() throws Exception {
		DecimalFormat decimalFormat = NumericDDMFormFieldUtil.getDecimalFormat(
			LocaleUtil.PORTUGAL);

		Assert.assertEquals("1,2345678", decimalFormat.format(1.2345678));
		Assert.assertEquals(
			BigDecimal.valueOf(1.2345678), decimalFormat.parse("1,2345678"));
	}

	@Test
	public void testGetDecimalFormatWithDecimalPoint() throws Exception {
		DecimalFormat decimalFormat = NumericDDMFormFieldUtil.getDecimalFormat(
			LocaleUtil.US);

		Assert.assertEquals("1.2345678", decimalFormat.format(1.2345678));
		Assert.assertEquals(
			BigDecimal.valueOf(1.2345678), decimalFormat.parse("1.2345678"));
	}

}