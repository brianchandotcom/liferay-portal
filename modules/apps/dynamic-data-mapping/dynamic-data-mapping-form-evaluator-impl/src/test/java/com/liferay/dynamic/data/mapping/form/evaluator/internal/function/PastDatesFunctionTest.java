/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.Assert;
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

	@Test
	public void testApplyFalse() {
		LocalDate tomorrowLocalDate = _todayLocalDate.plusDays(1);

		Assert.assertFalse(
			_pastDatesFunction.apply(
				tomorrowLocalDate.toString(), _todayLocalDate.toString()));
	}

	@Test
	public void testApplyTrue() {
		LocalDate yesterdayLocalDate = _todayLocalDate.minusDays(1);
		LocalDateTime yesterdayLocalDateTime = _todayLocalDateTime.minusDays(1);

		Assert.assertTrue(
			_pastDatesFunction.apply(
				yesterdayLocalDate.toString(), _todayLocalDate.toString()));

		Assert.assertTrue(
			_pastDatesFunction.apply(
				yesterdayLocalDate.toString(), _todayLocalDateTime.toString()));

		Assert.assertTrue(
			_pastDatesFunction.apply(
				yesterdayLocalDateTime.toString(), _todayLocalDate.toString()));

		Assert.assertTrue(
			_pastDatesFunction.apply(
				yesterdayLocalDateTime.toString(),
				_todayLocalDateTime.toString()));

		Assert.assertTrue(
			_pastDatesFunction.apply(
				_todayLocalDate.toString(), _todayLocalDate.toString()));
	}

	@Test
	public void testApplyWithoutValues() {
		Assert.assertFalse(
			_pastDatesFunction.apply(null, _todayLocalDate.toString()));
		Assert.assertFalse(
			_pastDatesFunction.apply(_todayLocalDate.toString(), null));
	}

	private final PastDatesFunction _pastDatesFunction =
		new PastDatesFunction();
	private final LocalDate _todayLocalDate = LocalDate.now(ZoneId.of("UTC"));
	private final LocalDateTime _todayLocalDateTime = LocalDateTime.now(
		ZoneId.of("UTC"));

}