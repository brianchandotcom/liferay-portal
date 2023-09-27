/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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