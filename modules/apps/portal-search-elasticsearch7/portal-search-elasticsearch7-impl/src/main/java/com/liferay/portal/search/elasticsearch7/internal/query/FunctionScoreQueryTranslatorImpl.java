/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.search.elasticsearch7.internal.query.function.score.ElasticsearchScoreFunctionTranslator;
import com.liferay.portal.search.query.FunctionScoreQuery;
import com.liferay.portal.search.query.FunctionScoreQuery.FilterQueryScoreFunctionHolder;
import com.liferay.portal.search.query.QueryTranslator;
import com.liferay.portal.search.query.function.score.ScoreFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctionTranslator;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.FilterFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = FunctionScoreQueryTranslator.class)
public class FunctionScoreQueryTranslatorImpl
	implements FunctionScoreQueryTranslator {

	@Override
	public QueryBuilder translate(
		FunctionScoreQuery functionScoreQuery,
		QueryTranslator<QueryBuilder> queryTranslator) {

		QueryBuilder queryBuilder = queryTranslator.translate(
			functionScoreQuery.getQuery());

		FunctionScoreQueryBuilder functionScoreQueryBuilder =
			QueryBuilders.functionScoreQuery(
				queryBuilder,
				TransformUtil.transformToArray(
					functionScoreQuery.getFilterQueryScoreFunctionHolders(),
					filterQueryScoreFunctionHolder -> _translateFilterFunction(
						filterQueryScoreFunctionHolder, queryTranslator,
						_translateScoreFunction(
							filterQueryScoreFunctionHolder.getScoreFunction())),
					FilterFunctionBuilder.class));

		if (functionScoreQuery.getMinScore() != null) {
			functionScoreQueryBuilder.setMinScore(
				functionScoreQuery.getMinScore());
		}

		if (functionScoreQuery.getMaxBoost() != null) {
			functionScoreQueryBuilder.maxBoost(
				functionScoreQuery.getMaxBoost());
		}

		if (functionScoreQuery.getScoreMode() != null) {
			functionScoreQueryBuilder.scoreMode(
				translate(functionScoreQuery.getScoreMode()));
		}

		if (functionScoreQuery.getCombineFunction() != null) {
			functionScoreQueryBuilder.boostMode(
				_combineFunctionTranslator.translate(
					functionScoreQuery.getCombineFunction()));
		}

		return functionScoreQueryBuilder;
	}

	protected
		org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.
			ScoreMode translate(FunctionScoreQuery.ScoreMode scoreMode) {

		if (scoreMode == FunctionScoreQuery.ScoreMode.AVG) {
			return org.elasticsearch.common.lucene.search.function.
				FunctionScoreQuery.ScoreMode.AVG;
		}
		else if (scoreMode == FunctionScoreQuery.ScoreMode.FIRST) {
			return org.elasticsearch.common.lucene.search.function.
				FunctionScoreQuery.ScoreMode.FIRST;
		}
		else if (scoreMode == FunctionScoreQuery.ScoreMode.MAX) {
			return org.elasticsearch.common.lucene.search.function.
				FunctionScoreQuery.ScoreMode.MAX;
		}
		else if (scoreMode == FunctionScoreQuery.ScoreMode.MIN) {
			return org.elasticsearch.common.lucene.search.function.
				FunctionScoreQuery.ScoreMode.MIN;
		}
		else if (scoreMode == FunctionScoreQuery.ScoreMode.MULTIPLY) {
			return org.elasticsearch.common.lucene.search.function.
				FunctionScoreQuery.ScoreMode.MULTIPLY;
		}
		else if (scoreMode == FunctionScoreQuery.ScoreMode.SUM) {
			return org.elasticsearch.common.lucene.search.function.
				FunctionScoreQuery.ScoreMode.SUM;
		}

		throw new IllegalArgumentException(
			"Invalid FunctionScoreQuery.ScoreMode: " + scoreMode);
	}

	private FilterFunctionBuilder _translateFilterFunction(
		FilterQueryScoreFunctionHolder filterQueryScoreFunctionHolder,
		QueryTranslator<QueryBuilder> queryTranslator,
		ScoreFunctionBuilder<?> scoreFunctionBuilder) {

		if (filterQueryScoreFunctionHolder.getFilterQuery() == null) {
			return new FilterFunctionBuilder(scoreFunctionBuilder);
		}

		return new FilterFunctionBuilder(
			queryTranslator.translate(
				filterQueryScoreFunctionHolder.getFilterQuery()),
			scoreFunctionBuilder);
	}

	private ScoreFunctionBuilder<?> _translateScoreFunction(
		ScoreFunction scoreFunction) {

		ScoreFunctionBuilder<?> scoreFunctionBuilder = scoreFunction.accept(
			_scoreFunctionTranslator);

		if (scoreFunction.getWeight() != null) {
			scoreFunctionBuilder.setWeight(scoreFunction.getWeight());
		}

		return scoreFunctionBuilder;
	}

	private final CombineFunctionTranslator _combineFunctionTranslator =
		new CombineFunctionTranslator();
	private final ScoreFunctionTranslator<ScoreFunctionBuilder<?>>
		_scoreFunctionTranslator = new ElasticsearchScoreFunctionTranslator();

}