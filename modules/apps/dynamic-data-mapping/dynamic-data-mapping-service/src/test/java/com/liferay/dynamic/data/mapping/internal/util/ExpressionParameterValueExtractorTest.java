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

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcos Martins
 */
public class ExpressionParameterValueExtractorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testExtractParameterValues() {
		_assertParameterValueArray(
			_extractParameterValues("equals(Country, \"US\")"),
			Arrays.asList("Country", "\"US\""));

		_assertParameterValueArray(
			_extractParameterValues("equals(sum(\"1\",1), 2)"),
			Arrays.asList("\"1\"", "1", "2"));
	}

	private void _assertParameterValueArray(
		List<String> actualParameterValues,
		List<String> expectedParameterValues) {

		Assert.assertEquals(
			Arrays.deepToString(expectedParameterValues.toArray()),
			Arrays.deepToString(actualParameterValues.toArray()));
	}

	private List<String> _extractParameterValues(String visibilityExpression) {
		return ExpressionParameterValueExtractor.extractParameterValues(
			visibilityExpression);
	}

}