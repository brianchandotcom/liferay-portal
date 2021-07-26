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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.DateFormatFactoryImpl;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 */
public class PastDatesFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_pastDatesFunction.setDDMExpressionParameterAccessor(
			new DefaultDDMExpressionParameterAccessor());

		_setUpDateFormatFactoryUtil();
	}

	@Test
	public void testApplyFalse1() {
		LocalDate todayLocalDate = LocalDate.now(ZoneId.of("UTC"));

		LocalDate tomorrowLocalDate = todayLocalDate.plusDays(1);

		Assert.assertFalse(
			_pastDatesFunction.apply(
				tomorrowLocalDate.toString(),
				JSONUtil.put(
					"endsOn", JSONUtil.put("type", "responseDate")
				).toString()));
	}

	@Test
	public void testApplyFalse2() {
		Assert.assertFalse(
			_pastDatesFunction.apply(
				null,
				JSONUtil.put(
					"endsOn", JSONUtil.put("type", "responseDate")
				).toString()));
	}

	@Test
	public void testApplyTrue() {
		LocalDate todayLocalDate = LocalDate.now(ZoneId.of("UTC"));

		LocalDate yesterdayLocalDate = todayLocalDate.minusDays(1);

		Assert.assertTrue(
			_pastDatesFunction.apply(
				yesterdayLocalDate.toString(),
				JSONUtil.put(
					"endsOn", JSONUtil.put("type", "responseDate")
				).toString()));
	}

	private void _setUpDateFormatFactoryUtil() {
		DateFormatFactoryUtil dateFormatFactoryUtil =
			new DateFormatFactoryUtil();

		dateFormatFactoryUtil.setDateFormatFactory(new DateFormatFactoryImpl());
	}

	private final PastDatesFunction _pastDatesFunction =
		new PastDatesFunction();

}