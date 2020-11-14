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

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.elasticsearch7.internal.query.function.score.ElasticsearchScoreFunctionTranslator;
import com.liferay.portal.search.query.function.score.FieldValueFactorScoreFunction;
import com.liferay.portal.search.test.util.query.BaseScoreFunctionTranslatorTestCase;

import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;

/**
 * @author André de Oliveira
 */
public class ElasticsearchScoreFunctionTranslatorTest
	extends BaseScoreFunctionTranslatorTestCase {

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