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

package com.liferay.portal.search.test.util.query;

import com.liferay.portal.search.internal.query.function.score.FieldValueFactorScoreFunctionImpl;
import com.liferay.portal.search.query.function.score.FieldValueFactorScoreFunction;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public abstract class BaseScoreFunctionTranslatorTestCase {

	@Test
	public void testTranslate() {
		FieldValueFactorScoreFunction fieldValueFactorScoreFunction =
			new FieldValueFactorScoreFunctionImpl("priority");

		fieldValueFactorScoreFunction.setFactor(100.0F);
		fieldValueFactorScoreFunction.setMissing(0.0);
		fieldValueFactorScoreFunction.setModifier(
			FieldValueFactorScoreFunction.Modifier.LN1P);

		String string = translate(fieldValueFactorScoreFunction);

		_assertContains("\"factor\":100.0", string);
		_assertContains("\"field\":\"priority\"", string);
		_assertContains("\"missing\":0.0", string);
		_assertContains("\"modifier\":\"ln1p\"", string);
	}

	protected abstract String translate(
		FieldValueFactorScoreFunction fieldValueFactorScoreFunction);

	private void _assertContains(String expected, String actual) {
		if (!actual.contains(expected)) {
			Assert.assertEquals(expected, actual);
		}
	}

}