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

package com.liferay.dynamic.data.mapping.storage.constants;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Guilherme Camacho
 */
public class FieldConstantsTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetSerializableReturnDoubleWithLocaleBR() {
		Serializable serializable = FieldConstants.getSerializable(
			LocaleUtil.BRAZIL, LocaleUtil.BRAZIL, FieldConstants.DOUBLE, "3,0");

		Assert.assertEquals(Double.valueOf(3.0), (Double)serializable);
	}

	@Test
	public void testGetSerializableReturnDoubleWithLocaleUS() {
		Serializable serializable = FieldConstants.getSerializable(
			LocaleUtil.US, LocaleUtil.US, FieldConstants.DOUBLE, "3.0");

		Assert.assertEquals(Double.valueOf(3.0), (Double)serializable);
	}

	@Test
	public void testGetSerializableReturnIntegerWithLocaleBR() {
		Serializable serializable = FieldConstants.getSerializable(
			LocaleUtil.BRAZIL, LocaleUtil.BRAZIL, FieldConstants.INTEGER, "3");

		Assert.assertEquals(Integer.valueOf(3), (Integer)serializable);
	}

	@Test
	public void testGetSerializableReturnIntegerWithLocaleUS() {
		Serializable serializable = FieldConstants.getSerializable(
			LocaleUtil.US, LocaleUtil.US, FieldConstants.INTEGER, "3");

		Assert.assertEquals(Integer.valueOf(3), (Integer)serializable);
	}

	@Test
	public void testGetSerializableReturnNumberArray() {
		List<Serializable> values = Arrays.asList(
			"1", 1.0, 1000, "10000000000");

		Serializable serializable = FieldConstants.getSerializable(
			FieldConstants.DOUBLE, values);

		Number[] numbers = (Number[])serializable;

		Assert.assertEquals(new BigDecimal("1"), numbers[0]);
		Assert.assertEquals(1.0, numbers[1]);
		Assert.assertEquals(1000, numbers[2]);
		Assert.assertEquals(new BigDecimal("10000000000"), numbers[3]);

		serializable = FieldConstants.getSerializable(
			FieldConstants.INTEGER, values);

		numbers = (Number[])serializable;

		Assert.assertEquals(new BigDecimal("1"), numbers[0]);
		Assert.assertEquals(1.0, numbers[1]);
		Assert.assertEquals(1000, numbers[2]);
		Assert.assertEquals(new BigDecimal("10000000000"), numbers[3]);
	}

}