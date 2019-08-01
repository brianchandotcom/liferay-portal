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

package com.liferay.portal.search.elasticsearch6.internal.query;

import com.liferay.portal.search.elasticsearch6.internal.query.function.score.ElasticsearchScoreFunctionTranslator;
import com.liferay.portal.search.query.FunctionScoreQuery;
import com.liferay.portal.search.query.FunctionScoreQuery.FilterQueryScoreFunctionHolder;
import com.liferay.portal.search.query.QueryTranslator;
import com.liferay.portal.search.query.function.score.ScoreFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctionTranslator;

import java.util.List;
import java.util.stream.Stream;

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

		List<FilterQueryScoreFunctionHolder> filterQueryScoreFunctionHolders =
			functionScoreQuery.getFilterQueryScoreFunctionHolders();

		Stream<FilterQueryScoreFunctionHolder> stream =
			filterQueryScoreFunctionHolders.stream();

		FilterFunctionBuilder[] filterFunctionBuilders = stream.map(
			filterQueryScoreFunctionHolder -> translateFilterFunction(
				filterQueryScoreFunctionHolder, queryTranslator,
				translateScoreFunction(
					filterQueryScoreFunctionHolder.getScoreFunction()))
		).toArray(
			FilterFunctionBuilder[]::new
		);

		FunctionScoreQueryBuilder functionScoreQueryBuilder =
			QueryBuilders.functionScoreQuery(
				queryBuilder, filterFunctionBuilders);

		if (functionScoreQuery.getMinScore() != null) {
			functionScoreQueryBuilder.setMinScore(
				functionScoreQuery.getMinScore());
		}

		if (functionScoreQuery.getMaxBoost() != null) {
			functionScoreQueryBuilder.maxBoost(
				functionScoreQuery.getMaxBoost());
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
		else {
			throw new IllegalArgumentException(
				"Invalid FunctionScoreQuery.ScoreMode: " + scoreMode);
		}
	}

	protected FilterFunctionBuilder translateFilterFunction(
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

	protected ScoreFunctionBuilder<?> translateScoreFunction(
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