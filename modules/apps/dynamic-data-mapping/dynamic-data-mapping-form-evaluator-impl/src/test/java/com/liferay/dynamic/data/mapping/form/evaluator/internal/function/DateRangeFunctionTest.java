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

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Carolina Barbosa
 */
public class DateRangeFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_dateRangeFunction.setDDMExpressionParameterAccessor(
			new DefaultDDMExpressionParameterAccessor());

		_setUpDateFormatFactoryUtil();
		_setUpFutureDatesFunction();
		_setUpPastDatesFunction();
	}

	@Test
	public void testApplyFalse1() {
		LocalDate yesterdayLocalDate = _todayLocalDate.minusDays(1);

		Assert.assertFalse(
			_dateRangeFunction.apply(
				yesterdayLocalDate.toString(),
				JSONUtil.put(
					"endsOn", JSONUtil.put("type", "responseDate")
				).put(
					"startsFrom", JSONUtil.put("type", "responseDate")
				).toString()));
	}

	@Test
	public void testApplyFalse2() {
		LocalDate tomorrowLocalDate = _todayLocalDate.plusDays(1);

		Assert.assertFalse(
			_dateRangeFunction.apply(
				tomorrowLocalDate.toString(),
				JSONUtil.put(
					"endsOn", JSONUtil.put("type", "responseDate")
				).put(
					"startsFrom", JSONUtil.put("type", "responseDate")
				).toString()));
	}

	@Test
	public void testApplyFalse3() {
		Assert.assertFalse(
			_dateRangeFunction.apply(
				null,
				JSONUtil.put(
					"endsOn",
					JSONUtil.put(
						"date", "responseDate"
					).put(
						"quantity", -12
					).put(
						"type", "customDate"
					).put(
						"unit", "days"
					)
				).put(
					"startsFrom",
					JSONUtil.put(
						"date", "responseDate"
					).put(
						"quantity", -24
					).put(
						"type", "customDate"
					).put(
						"unit", "days"
					)
				)));
	}

	@Test
	public void testApplyFalseCustomDays() {
		Assert.assertFalse(
			_apply(_todayLocalDate.minusDays(6), "days", -24, -12));
		Assert.assertFalse(
			_apply(_todayLocalDate.minusDays(30), "days", -24, -12));
		Assert.assertFalse(_apply(_todayLocalDate.plusDays(6), "days", 12, 24));
		Assert.assertFalse(
			_apply(_todayLocalDate.plusDays(30), "days", 12, 24));
	}

	@Test
	public void testApplyFalseCustomMonths() {
		Assert.assertFalse(
			_apply(_todayLocalDate.minusMonths(6), "months", -24, -12));
		Assert.assertFalse(
			_apply(_todayLocalDate.minusMonths(30), "months", -24, -12));
		Assert.assertFalse(
			_apply(_todayLocalDate.plusMonths(6), "months", 12, 24));
		Assert.assertFalse(
			_apply(_todayLocalDate.plusMonths(30), "months", 12, 24));
	}

	@Test
	public void testApplyFalseCustomYears() {
		Assert.assertFalse(
			_apply(_todayLocalDate.minusYears(6), "years", -24, -12));
		Assert.assertFalse(
			_apply(_todayLocalDate.minusYears(30), "years", -24, -12));
		Assert.assertFalse(
			_apply(_todayLocalDate.plusYears(6), "years", 12, 24));
		Assert.assertFalse(
			_apply(_todayLocalDate.plusYears(30), "years", 12, 24));
	}

	@Test
	public void testApplyTrue() {
		Assert.assertTrue(
			_dateRangeFunction.apply(
				LocalDate.now(ZoneId.of("UTC")),
				JSONUtil.put(
					"endsOn", JSONUtil.put("type", "responseDate")
				).put(
					"startsFrom", JSONUtil.put("type", "responseDate")
				).toString()));
	}

	@Test
	public void testApplyTrueCustomDays() {
		Assert.assertTrue(
			_apply(_todayLocalDate.minusDays(20), "days", -24, -12));
		Assert.assertTrue(_apply(_todayLocalDate.plusDays(20), "days", 12, 24));
	}

	@Test
	public void testApplyTrueCustomMonths() {
		Assert.assertTrue(
			_apply(_todayLocalDate.minusMonths(20), "months", -24, -12));
		Assert.assertTrue(
			_apply(_todayLocalDate.plusMonths(20), "months", 12, 24));
	}

	@Test
	public void testApplyTrueCustomYears() {
		Assert.assertTrue(
			_apply(_todayLocalDate.minusYears(20), "years", -24, -12));
		Assert.assertTrue(
			_apply(_todayLocalDate.plusYears(20), "years", 12, 24));
	}

	private Boolean _apply(
		LocalDate localDate, String unit, int startQuantity, int endQuantity) {

		return _dateRangeFunction.apply(
			localDate.toString(),
			JSONUtil.put(
				"endsOn",
				JSONUtil.put(
					"date", "responseDate"
				).put(
					"quantity", endQuantity
				).put(
					"type", "customDate"
				).put(
					"unit", unit
				)
			).put(
				"startsFrom",
				JSONUtil.put(
					"date", "responseDate"
				).put(
					"quantity", startQuantity
				).put(
					"type", "customDate"
				).put(
					"unit", unit
				)
			).toString());
	}

	private void _setUpDateFormatFactoryUtil() {
		DateFormatFactoryUtil dateFormatFactoryUtil =
			new DateFormatFactoryUtil();

		dateFormatFactoryUtil.setDateFormatFactory(new DateFormatFactoryImpl());
	}

	private void _setUpFutureDatesFunction() throws Exception {
		PowerMockito.field(
			DateRangeFunction.class, "_futureDatesFunction"
		).set(
			_dateRangeFunction, new FutureDatesFunction()
		);
	}

	private void _setUpPastDatesFunction() throws Exception {
		PowerMockito.field(
			DateRangeFunction.class, "_pastDatesFunction"
		).set(
			_dateRangeFunction, new PastDatesFunction()
		);
	}

	private final DateRangeFunction _dateRangeFunction =
		new DateRangeFunction();
	private final LocalDate _todayLocalDate = LocalDate.now(ZoneId.of("UTC"));

}