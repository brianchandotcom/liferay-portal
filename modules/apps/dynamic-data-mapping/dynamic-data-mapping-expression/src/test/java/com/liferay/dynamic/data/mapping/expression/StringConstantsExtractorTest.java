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

package com.liferay.dynamic.data.mapping.expression;

import com.liferay.dynamic.data.mapping.expression.internal.StringConstantsExtractor;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class StringConstantsExtractorTest {

	@Test
	public void testExpressionWithMultipleStringConstants() {
		String expressionString =
			"equals(Country, \"Brazil\") || equals(Country, \"US\")";

		List<String> variableNames = _stringConstantsExtractor.extract(
			expressionString);

		Assert.assertEquals("Brazil", variableNames.get(0));
		Assert.assertEquals("US", variableNames.get(1));
	}

	@Test
	public void testExpressionWithNoStringConstants() {
		String expressionString = "2 * sum(5, 6)";

		List<String> variableNames = _stringConstantsExtractor.extract(
			expressionString);

		Assert.assertTrue(variableNames.isEmpty());
	}

	private final StringConstantsExtractor _stringConstantsExtractor =
		new StringConstantsExtractor();

}