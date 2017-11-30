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

import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.search.StatsResults;

import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
import org.elasticsearch.search.aggregations.bucket.missing.MissingAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = StatsTranslator.class)
public class DefaultStatsTranslator implements StatsTranslator {

	@Override
	public StatsResults translate(
		Map<String, Aggregation> aggregationMap, Stats stats) {

		String field = stats.getField();

		StatsResults statsResults = new StatsResults(field);

		if (stats.isCount()) {
			ValueCount valueCount = (ValueCount)aggregationMap.get(
				field + "_count");

			statsResults.setCount(valueCount.getValue());
		}

		if (stats.isMax()) {
			Max max = (Max)aggregationMap.get(field + "_max");

			statsResults.setMax(max.getValue());
		}

		if (stats.isMean()) {
			org.elasticsearch.search.aggregations.metrics.stats.Stats
				elasticsearchStats =
					(org.elasticsearch.search.aggregations.metrics.stats.Stats)
						aggregationMap.get(field + "_stats");

			statsResults.setMean(elasticsearchStats.getAvg());
		}

		if (stats.isMin()) {
			Min min = (Min)aggregationMap.get(field + "_min");

			statsResults.setMin(min.getValue());
		}

		if (stats.isMissing()) {
			Missing missing = (Missing)aggregationMap.get(field + "_missing");

			statsResults.setMissing((int)missing.getDocCount());
		}

		if (stats.isStandardDeviation()) {
			ExtendedStats extendedStats = (ExtendedStats)aggregationMap.get(
				field + "_extendedStats");

			statsResults.setStandardDeviation(extendedStats.getStdDeviation());
		}

		if (stats.isSum()) {
			Sum sum = (Sum)aggregationMap.get(field + "_sum");

			statsResults.setSum(sum.getValue());
		}

		if (stats.isSumOfSquares()) {
			ExtendedStats extendedStats = (ExtendedStats)aggregationMap.get(
				field + "_extendedStats");

			statsResults.setSumOfSquares(extendedStats.getSumOfSquares());
		}

		return statsResults;
	}

	@Override
	public void translate(
		SearchRequestBuilder searchRequestBuilder, Stats stats) {

		if (!stats.isEnabled()) {
			return;
		}

		String field = stats.getField();

		if (stats.isCount()) {
			ValueCountAggregationBuilder valueCountAggregationBuilder =
				AggregationBuilders.count(field + "_count");

			valueCountAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(valueCountAggregationBuilder);
		}

		if (stats.isMax()) {
			MaxAggregationBuilder maxAggregationBuilder =
				AggregationBuilders.max(field + "_max");

			maxAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(maxAggregationBuilder);
		}

		if (stats.isMean()) {
			StatsAggregationBuilder statsAggregationBuilder =
				AggregationBuilders.stats(field + "_stats");

			statsAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(statsAggregationBuilder);
		}

		if (stats.isMin()) {
			MinAggregationBuilder minAggregationBuilder =
				AggregationBuilders.min(field + "_min");

			minAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(minAggregationBuilder);
		}

		if (stats.isMissing()) {
			MissingAggregationBuilder missingAggregationBuilder =
				AggregationBuilders.missing(field + "_missing");

			missingAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(missingAggregationBuilder);
		}

		if (stats.isStandardDeviation() || stats.isSumOfSquares()) {
			ExtendedStatsAggregationBuilder extendedStatsAggregationBuilder =
				AggregationBuilders.extendedStats(field + "_extendedStats");

			extendedStatsAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(
				extendedStatsAggregationBuilder);
		}

		if (stats.isSum()) {
			SumAggregationBuilder sumAggregationBuilder =
				AggregationBuilders.sum(field + "_sum");

			sumAggregationBuilder.field(field);

			searchRequestBuilder.addAggregation(sumAggregationBuilder);
		}
	}

}