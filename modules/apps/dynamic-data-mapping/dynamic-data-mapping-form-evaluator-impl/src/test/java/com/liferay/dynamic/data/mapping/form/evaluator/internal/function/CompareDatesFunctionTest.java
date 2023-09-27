/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.DateFormatFactoryImpl;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Selton Guedes
 */
@RunWith(Parameterized.class)
public class CompareDatesFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(name = "date1={0}, date2={1}, expectedResult={2}")
	public static List<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"2022-04-03", "2022-04-03", true},
				{"2021-04-03", "2022-04-03", false},
				{"2022-04-03 10:13:00", "2022-04-03", true},
				{"2022-04-03 10:13:00", "2022-04-03 10:13:00", true},
				{"2021-04-03 10:13:00", "2022-04-02", false},
				{"2021-04", "2022-04-02", false}
			});
	}

	@Before
	public void setUp() throws Exception {
		_setUpDateFormatFactoryUtil();
	}

	@Test
	public void testApply() {
		Assert.assertEquals(
			expectedResult, _compareDatesFunction.apply(date1, date2));
	}

	@Parameterized.Parameter
	public Object date1;

	@Parameterized.Parameter(1)
	public Object date2;

	@Parameterized.Parameter(2)
	public boolean expectedResult;

	private void _setUpDateFormatFactoryUtil() {
		DateFormatFactoryUtil dateFormatFactoryUtil =
			new DateFormatFactoryUtil();

		dateFormatFactoryUtil.setDateFormatFactory(new DateFormatFactoryImpl());
	}

	private final CompareDatesFunction _compareDatesFunction =
		new CompareDatesFunction();

}