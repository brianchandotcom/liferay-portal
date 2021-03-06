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

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Péter Borkuti
 */
public class DurationScaleTest {

	public static final String[] SCALES = {
		"second", "millisecond", "minute", "hour", "day", "week", "month",
		"year"
	};

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test(
		expected = KaleoDefinitionValidationException.InvalidDurationScale.class
	)
	public void testParseInvalidScale() throws Exception {
		DurationScale.parse("random text");
	}

	@Test
	public void testParseValidScales() throws Exception {
		for (String scale : SCALES) {
			DurationScale durationScale = DurationScale.parse(scale);

			Assert.assertEquals(scale, durationScale.getValue());
		}
	}

	@Test
	public void testScaleNum() throws Exception {
		DurationScale[] values = DurationScale.values();

		Assert.assertEquals(Arrays.toString(values), 8, values.length);
	}

}