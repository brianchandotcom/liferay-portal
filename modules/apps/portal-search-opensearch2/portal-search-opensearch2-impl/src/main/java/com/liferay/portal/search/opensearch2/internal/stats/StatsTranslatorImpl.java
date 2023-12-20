/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.stats;

import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsResponse;
import com.liferay.portal.search.stats.StatsResponseBuilder;
import com.liferay.portal.search.stats.StatsResponseBuilderFactory;

import java.util.Map;

import org.opensearch.client.opensearch._types.aggregations.Aggregate;
import org.opensearch.client.opensearch._types.aggregations.Aggregation;
import org.opensearch.client.opensearch._types.aggregations.AggregationBuilders;
import org.opensearch.client.opensearch._types.aggregations.CardinalityAggregate;
import org.opensearch.client.opensearch._types.aggregations.ExtendedStatsAggregate;
import org.opensearch.client.opensearch._types.aggregations.MaxAggregate;
import org.opensearch.client.opensearch._types.aggregations.MinAggregate;
import org.opensearch.client.opensearch._types.aggregations.MissingAggregate;
import org.opensearch.client.opensearch._types.aggregations.StatsAggregate;
import org.opensearch.client.opensearch._types.aggregations.SumAggregate;
import org.opensearch.client.opensearch._types.aggregations.ValueCountAggregate;
import org.opensearch.client.opensearch.core.SearchRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
@Component(service = StatsTranslator.class)
public class StatsTranslatorImpl implements StatsTranslator {

	@Override
	public void populateRequest(
		SearchRequest.Builder searchRequestBuilder, StatsRequest statsRequest) {

		String field = statsRequest.getField();

		if (statsRequest.isCardinality()) {
			searchRequestBuilder.aggregations(
				field + "_cardinality",
				new Aggregation(
					AggregationBuilders.cardinality(
					).field(
						field
					).build()));
		}

		if (statsRequest.isCount()) {
			searchRequestBuilder.aggregations(
				field + "_count",
				new Aggregation(
					AggregationBuilders.valueCount(
					).field(
						field
					).build()));
		}

		if (statsRequest.isMax()) {
			searchRequestBuilder.aggregations(
				field + "_max",
				new Aggregation(
					AggregationBuilders.max(
					).field(
						field
					).build()));
		}

		if (statsRequest.isMean()) {
			searchRequestBuilder.aggregations(
				field + "_stats",
				new Aggregation(
					AggregationBuilders.stats(
					).field(
						field
					).build()));
		}

		if (statsRequest.isMin()) {
			searchRequestBuilder.aggregations(
				field + "_min",
				new Aggregation(
					AggregationBuilders.min(
					).field(
						field
					).build()));
		}

		if (statsRequest.isMissing()) {
			searchRequestBuilder.aggregations(
				field + "_missing",
				new Aggregation(
					AggregationBuilders.missing(
					).field(
						field
					).build()));
		}

		if (statsRequest.isStandardDeviation() ||
			statsRequest.isSumOfSquares()) {

			searchRequestBuilder.aggregations(
				field + "_extendedStats",
				new Aggregation(
					AggregationBuilders.extendedStats(
					).field(
						field
					).build()));
		}

		if (statsRequest.isSum()) {
			searchRequestBuilder.aggregations(
				field + "_sum",
				new Aggregation(
					AggregationBuilders.sum(
					).field(
						field
					).build()));
		}
	}

	@Override
	public StatsResponse translateResponse(
		Map<String, Aggregate> aggregateMap, StatsRequest statsRequest) {

		StatsResponseBuilder statsResponseBuilder =
			_statsResponseBuilderFactory.getStatsResponseBuilder();

		String field = statsRequest.getField();

		if (statsRequest.isCardinality()) {
			Aggregate aggregate = aggregateMap.get(field + "_cardinality");

			CardinalityAggregate cardinalityAggregate = aggregate.cardinality();

			statsResponseBuilder.cardinality(cardinalityAggregate.value());
		}

		if (statsRequest.isCount()) {
			Aggregate aggregate = aggregateMap.get(field + "_count");

			ValueCountAggregate valueCountAggregate = aggregate.valueCount();

			statsResponseBuilder.count((long)valueCountAggregate.value());
		}

		statsResponseBuilder.field(field);

		if (statsRequest.isMax()) {
			Aggregate aggregate = aggregateMap.get(field + "_max");

			MaxAggregate maxAggregate = aggregate.max();

			statsResponseBuilder.max(maxAggregate.value());
		}

		if (statsRequest.isMean()) {
			Aggregate aggregate = aggregateMap.get(field + "_stats");

			StatsAggregate statsAggregate = aggregate.stats();

			statsResponseBuilder.mean(statsAggregate.avg());
		}

		if (statsRequest.isMin()) {
			Aggregate aggregate = aggregateMap.get(field + "_min");

			MinAggregate minAggregate = aggregate.min();

			statsResponseBuilder.min(minAggregate.value());
		}

		if (statsRequest.isMissing()) {
			Aggregate aggregate = aggregateMap.get(field + "_missing");

			MissingAggregate missingAggregate = aggregate.missing();

			statsResponseBuilder.missing(missingAggregate.docCount());
		}

		if (statsRequest.isStandardDeviation()) {
			Aggregate aggregate = aggregateMap.get(field + "_extendedStats");

			ExtendedStatsAggregate extendedStatsAggregate =
				aggregate.extendedStats();

			statsResponseBuilder.standardDeviation(
				extendedStatsAggregate.stdDeviation());
		}

		if (statsRequest.isSum()) {
			Aggregate aggregate = aggregateMap.get(field + "_sum");

			SumAggregate sumAggregate = aggregate.sum();

			statsResponseBuilder.sum(sumAggregate.value());
		}

		if (statsRequest.isSumOfSquares()) {
			Aggregate aggregate = aggregateMap.get(field + "_extendedStats");

			ExtendedStatsAggregate extendedStatsAggregate =
				aggregate.extendedStats();

			statsResponseBuilder.sumOfSquares(
				extendedStatsAggregate.sumOfSquares());
		}

		return statsResponseBuilder.build();
	}

	@Reference
	private StatsResponseBuilderFactory _statsResponseBuilderFactory;

}