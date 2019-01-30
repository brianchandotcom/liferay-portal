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

package com.liferay.portal.search.elasticsearch6.internal.stats;

import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsResponse;
import com.liferay.portal.search.stats.StatsResponseBuilder;
import com.liferay.portal.search.stats.StatsResponseBuilderFactory;

import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
import org.elasticsearch.search.aggregations.bucket.missing.MissingAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = StatsTranslator.class)
public class DefaultStatsTranslator implements StatsTranslator {

	@Override
	public void populateRequest(
		SearchRequestBuilder searchRequestBuilder, StatsRequest statsRequest) {

		String field = statsRequest.getField();

		if (statsRequest.isCardinality()) {
			CardinalityAggregationBuilder cardinalityAggregationBuilder =
				AggregationBuilders.cardinality(field + "_cardinality");

			cardinalityAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(cardinalityAggregationBuilder);
		}

		if (statsRequest.isCount()) {
			ValueCountAggregationBuilder valueCountAggregationBuilder =
				AggregationBuilders.count(field + "_count");

			valueCountAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(valueCountAggregationBuilder);
		}

		if (statsRequest.isMax()) {
			MaxAggregationBuilder maxAggregationBuilder =
				AggregationBuilders.max(field + "_max");

			maxAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(maxAggregationBuilder);
		}

		if (statsRequest.isMean()) {
			StatsAggregationBuilder statsAggregationBuilder =
				AggregationBuilders.stats(field + "_stats");

			statsAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(statsAggregationBuilder);
		}

		if (statsRequest.isMin()) {
			MinAggregationBuilder minAggregationBuilder =
				AggregationBuilders.min(field + "_min");

			minAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(minAggregationBuilder);
		}

		if (statsRequest.isMissing()) {
			MissingAggregationBuilder missingAggregationBuilder =
				AggregationBuilders.missing(field + "_missing");

			missingAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(missingAggregationBuilder);
		}

		if (statsRequest.isStandardDeviation() ||
			statsRequest.isSumOfSquares()) {

			ExtendedStatsAggregationBuilder extendedStatsAggregationBuilder =
				AggregationBuilders.extendedStats(field + "_extendedStats");

			extendedStatsAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(
				extendedStatsAggregationBuilder);
		}

		if (statsRequest.isSum()) {
			SumAggregationBuilder sumAggregationBuilder =
				AggregationBuilders.sum(field + "_sum");

			sumAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(sumAggregationBuilder);
		}
	}

	@Override
	public StatsResponse translateResponse(
		Map<String, Aggregation> aggregationMap, StatsRequest statsRequest) {

		StatsResponseBuilder statsResponseBuilder =
			_statsResponseBuilderFactory.getStatsResponseBuilder();

		String field = statsRequest.getField();

		statsResponseBuilder.field(field);

		if (statsRequest.isCardinality()) {
			Cardinality cardinality = (Cardinality)aggregationMap.get(
				field + "_cardinality");

			statsResponseBuilder.cardinality(cardinality.getValue());
		}

		if (statsRequest.isCount()) {
			ValueCount valueCount = (ValueCount)aggregationMap.get(
				field + "_count");

			statsResponseBuilder.count(valueCount.getValue());
		}

		if (statsRequest.isMax()) {
			Max max = (Max)aggregationMap.get(field + "_max");

			statsResponseBuilder.max(max.getValue());
		}

		if (statsRequest.isMean()) {
			Stats stats = (Stats)aggregationMap.get(field + "_stats");

			statsResponseBuilder.mean(stats.getAvg());
		}

		if (statsRequest.isMin()) {
			Min min = (Min)aggregationMap.get(field + "_min");

			statsResponseBuilder.min(min.getValue());
		}

		if (statsRequest.isMissing()) {
			Missing missing = (Missing)aggregationMap.get(field + "_missing");

			statsResponseBuilder.missing((int)missing.getDocCount());
		}

		if (statsRequest.isStandardDeviation()) {
			ExtendedStats extendedStats = (ExtendedStats)aggregationMap.get(
				field + "_extendedStats");

			statsResponseBuilder.standardDeviation(
				extendedStats.getStdDeviation());
		}

		if (statsRequest.isSum()) {
			Sum sum = (Sum)aggregationMap.get(field + "_sum");

			statsResponseBuilder.sum(sum.getValue());
		}

		if (statsRequest.isSumOfSquares()) {
			ExtendedStats extendedStats = (ExtendedStats)aggregationMap.get(
				field + "_extendedStats");

			statsResponseBuilder.sumOfSquares(extendedStats.getSumOfSquares());
		}

		return statsResponseBuilder.build();
	}

	@Reference(unbind = "-")
	protected void setStatsResponseBuilderFactory(
		StatsResponseBuilderFactory statsResponseBuilderFactory) {

		_statsResponseBuilderFactory = statsResponseBuilderFactory;
	}

	private StatsResponseBuilderFactory _statsResponseBuilderFactory;

}