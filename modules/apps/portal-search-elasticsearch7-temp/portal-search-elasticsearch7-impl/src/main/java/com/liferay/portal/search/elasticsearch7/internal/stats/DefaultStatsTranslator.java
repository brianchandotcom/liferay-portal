/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.stats;

import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsResponse;
import com.liferay.portal.search.stats.StatsResponseBuilder;
import com.liferay.portal.search.stats.StatsResponseBuilderFactory;

import java.util.Map;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
import org.elasticsearch.search.aggregations.bucket.missing.MissingAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ExtendedStats;
import org.elasticsearch.search.aggregations.metrics.ExtendedStatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Min;
import org.elasticsearch.search.aggregations.metrics.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Stats;
import org.elasticsearch.search.aggregations.metrics.StatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ValueCount;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = StatsTranslator.class)
public class DefaultStatsTranslator implements StatsTranslator {

	@Override
	public void populateRequest(
		SearchSourceBuilder searchSourceBuilder, StatsRequest statsRequest) {

		String field = statsRequest.getField();

		if (statsRequest.isCardinality()) {
			CardinalityAggregationBuilder cardinalityAggregationBuilder =
				AggregationBuilders.cardinality(field + "_cardinality");

			cardinalityAggregationBuilder.field(field);

			searchSourceBuilder.aggregation(cardinalityAggregationBuilder);
		}

		if (statsRequest.isCount()) {
			ValueCountAggregationBuilder valueCountAggregationBuilder =
				AggregationBuilders.count(field + "_count");

			valueCountAggregationBuilder.field(field);

			searchSourceBuilder.aggregation(valueCountAggregationBuilder);
		}

		if (statsRequest.isMax()) {
			MaxAggregationBuilder maxAggregationBuilder =
				AggregationBuilders.max(field + "_max");

			maxAggregationBuilder.field(field);

			searchSourceBuilder.aggregation(maxAggregationBuilder);
		}

		if (statsRequest.isMean()) {
			StatsAggregationBuilder statsAggregationBuilder =
				AggregationBuilders.stats(field + "_stats");

			statsAggregationBuilder.field(field);

			searchSourceBuilder.aggregation(statsAggregationBuilder);
		}

		if (statsRequest.isMin()) {
			MinAggregationBuilder minAggregationBuilder =
				AggregationBuilders.min(field + "_min");

			minAggregationBuilder.field(field);

			searchSourceBuilder.aggregation(minAggregationBuilder);
		}

		if (statsRequest.isMissing()) {
			MissingAggregationBuilder missingAggregationBuilder =
				AggregationBuilders.missing(field + "_missing");

			missingAggregationBuilder.field(field);

			searchSourceBuilder.aggregation(missingAggregationBuilder);
		}

		if (statsRequest.isStandardDeviation() ||
			statsRequest.isSumOfSquares()) {

			ExtendedStatsAggregationBuilder extendedStatsAggregationBuilder =
				AggregationBuilders.extendedStats(field + "_extendedStats");

			extendedStatsAggregationBuilder.field(field);

			searchSourceBuilder.aggregation(extendedStatsAggregationBuilder);
		}

		if (statsRequest.isSum()) {
			SumAggregationBuilder sumAggregationBuilder =
				AggregationBuilders.sum(field + "_sum");

			sumAggregationBuilder.field(field);

			searchSourceBuilder.aggregation(sumAggregationBuilder);
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

	@Reference
	private StatsResponseBuilderFactory _statsResponseBuilderFactory;

}