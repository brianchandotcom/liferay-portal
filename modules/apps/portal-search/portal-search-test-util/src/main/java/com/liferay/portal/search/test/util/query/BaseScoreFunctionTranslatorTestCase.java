/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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