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
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bruno Oliveira
 * @author Carolina Barbosa
 */
public class FutureDatesFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApplyFalse1() {
		FutureDatesFunction futureDatesFunction = new FutureDatesFunction();

		LocalDate todayLocalDate = LocalDate.now();

		LocalDate yesterdayLocalDate = todayLocalDate.minusDays(1);

		Assert.assertFalse(
			futureDatesFunction.apply(
				yesterdayLocalDate.toString(),
				JSONUtil.put(
					"startsFrom", JSONUtil.put("type", "responseDate")
				).toString()));
	}

	@Test
	public void testApplyFalse2() {
		FutureDatesFunction futureDatesFunction = new FutureDatesFunction();

		Assert.assertFalse(
			futureDatesFunction.apply(
				null,
				JSONUtil.put(
					"startsFrom", JSONUtil.put("type", "responseDate")
				).toString()));
	}

	@Test
	public void testApplyTrue() {
		FutureDatesFunction futureDatesFunction = new FutureDatesFunction();

		LocalDate todayLocalDate = LocalDate.now();

		LocalDate tomorrowLocalDate = todayLocalDate.plusDays(1);

		Assert.assertTrue(
			futureDatesFunction.apply(
				tomorrowLocalDate.toString(),
				JSONUtil.put(
					"startsFrom", JSONUtil.put("type", "responseDate")
				).toString()));
	}

}