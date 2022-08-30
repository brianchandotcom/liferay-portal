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