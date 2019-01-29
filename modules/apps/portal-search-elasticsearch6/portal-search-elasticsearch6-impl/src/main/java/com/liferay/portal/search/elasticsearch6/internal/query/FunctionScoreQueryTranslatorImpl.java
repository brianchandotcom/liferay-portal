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
import com.liferay.portal.search.query.QueryTranslator;
import com.liferay.portal.search.query.function.score.ScoreFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctionTranslator;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
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

		List<FunctionScoreQuery.FilterQueryScoreFunctionHolder>
			filterQueryScoreFunctionHolders =
				functionScoreQuery.getFilterQueryScoreFunctionHolders();

		List<FunctionScoreQueryBuilder.FilterFunctionBuilder>
			filterFunctionBuilders = new ArrayList<>(
				filterQueryScoreFunctionHolders.size());

		filterQueryScoreFunctionHolders.forEach(
			filterQueryScoreFunctionHolder -> {
				ScoreFunction scoreFunction =
					filterQueryScoreFunctionHolder.getScoreFunction();

				ScoreFunctionBuilder<?> scoreFunctionBuilder =
					scoreFunction.accept(_scoreFunctionTranslator);

				FunctionScoreQueryBuilder.FilterFunctionBuilder
					filterFunctionBuilder = null;

				if (filterQueryScoreFunctionHolder.getFilterQuery() == null) {
					filterFunctionBuilder =
						new FunctionScoreQueryBuilder.FilterFunctionBuilder(
							scoreFunctionBuilder);
				}
				else {
					QueryBuilder filterQueryBuilder = queryTranslator.translate(
						filterQueryScoreFunctionHolder.getFilterQuery());

					filterFunctionBuilder =
						new FunctionScoreQueryBuilder.FilterFunctionBuilder(
							filterQueryBuilder, scoreFunctionBuilder);
				}

				filterFunctionBuilders.add(filterFunctionBuilder);

			});

		FunctionScoreQueryBuilder functionScoreQueryBuilder =
			QueryBuilders.functionScoreQuery(
				queryBuilder,
				filterFunctionBuilders.toArray(
					new FunctionScoreQueryBuilder.FilterFunctionBuilder[
						filterFunctionBuilders.size()]));

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

	protected org.elasticsearch.common.lucene.search.function.
		FunctionScoreQuery.ScoreMode translate(
			FunctionScoreQuery.ScoreMode scoreMode) {

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

	private final CombineFunctionTranslator _combineFunctionTranslator =
		new CombineFunctionTranslator();
	private final ScoreFunctionTranslator<ScoreFunctionBuilder<?>>
		_scoreFunctionTranslator = new ElasticsearchScoreFunctionTranslator();

}