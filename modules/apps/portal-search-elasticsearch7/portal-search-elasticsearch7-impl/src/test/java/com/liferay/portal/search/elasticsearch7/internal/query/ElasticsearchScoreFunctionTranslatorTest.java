/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.elasticsearch7.internal.query.function.score.ElasticsearchScoreFunctionTranslator;
import com.liferay.portal.search.query.function.score.FieldValueFactorScoreFunction;
import com.liferay.portal.search.test.util.query.BaseScoreFunctionTranslatorTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author André de Oliveira
 */
public class ElasticsearchScoreFunctionTranslatorTest
	extends BaseScoreFunctionTranslatorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	protected String translate(
		FieldValueFactorScoreFunction fieldValueFactorScoreFunction) {

		ElasticsearchScoreFunctionTranslator
			elasticsearchScoreFunctionTranslator =
				new ElasticsearchScoreFunctionTranslator();

		ScoreFunctionBuilder scoreFunctionBuilder =
			elasticsearchScoreFunctionTranslator.translate(
				fieldValueFactorScoreFunction);

		return Strings.toString(scoreFunctionBuilder, false, false);
	}

}