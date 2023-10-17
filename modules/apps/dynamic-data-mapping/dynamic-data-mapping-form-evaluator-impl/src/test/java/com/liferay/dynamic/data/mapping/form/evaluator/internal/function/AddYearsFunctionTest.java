/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Murilo Stodolni
 */
public class AddYearsFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApply() {
		Assert.assertEquals(
			_todayLocalDate.plusYears(-1),
			_addYearsFunction.apply(_todayLocalDate.toString(), -1));
		Assert.assertEquals(
			_todayLocalDate,
			_addYearsFunction.apply(_todayLocalDate.toString(), 0));
		Assert.assertEquals(
			_todayLocalDate.plusYears(1),
			_addYearsFunction.apply(_todayLocalDate.toString(), 1));

		Assert.assertNull(_addYearsFunction.apply(null, 1));
		Assert.assertNull(
			_addYearsFunction.apply(_todayLocalDate.toString(), null));
	}

	private final AddYearsFunction _addYearsFunction = new AddYearsFunction();
	private final LocalDate _todayLocalDate = LocalDate.now(ZoneId.of("UTC"));

}