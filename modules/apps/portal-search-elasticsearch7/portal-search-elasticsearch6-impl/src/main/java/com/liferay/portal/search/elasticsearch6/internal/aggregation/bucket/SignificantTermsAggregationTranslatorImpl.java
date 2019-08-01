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

package com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.bucket.SignificantTermsAggregation;
import com.liferay.portal.search.elasticsearch6.internal.significance.SignificanceHeuristicTranslator;
import com.liferay.portal.search.query.QueryTranslator;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTermsAggregationBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = SignificantTermsAggregationTranslator.class)
public class SignificantTermsAggregationTranslatorImpl
	implements SignificantTermsAggregationTranslator {

	@Override
	public SignificantTermsAggregationBuilder translate(
		SignificantTermsAggregation significantTermsAggregation) {

		SignificantTermsAggregationBuilder significantTermsAggregationBuilder =
			AggregationBuilders.significantTerms(
				significantTermsAggregation.getName());

		significantTermsAggregationBuilder.field(
			significantTermsAggregation.getField());

		if (significantTermsAggregation.getBucketCountThresholds() != null) {
			significantTermsAggregationBuilder.bucketCountThresholds(
				_bucketCountThresholdsTranslator.translate(
					significantTermsAggregation.getBucketCountThresholds()));
		}

		if (significantTermsAggregation.getBackgroundFilterQuery() != null) {
			significantTermsAggregationBuilder.backgroundFilter(
				_queryTranslator.translate(
					significantTermsAggregation.getBackgroundFilterQuery()));
		}

		if (significantTermsAggregation.getExecutionHint() != null) {
			significantTermsAggregationBuilder.executionHint(
				significantTermsAggregation.getExecutionHint());
		}

		if (significantTermsAggregation.getIncludeExcludeClause() != null) {
			significantTermsAggregationBuilder.includeExclude(
				_includeExcludeTranslator.translate(
					significantTermsAggregation.getIncludeExcludeClause()));
		}

		if (significantTermsAggregation.getMinDocCount() != null) {
			significantTermsAggregationBuilder.minDocCount(
				significantTermsAggregation.getMinDocCount());
		}

		if (significantTermsAggregation.getShardMinDocCount() != null) {
			significantTermsAggregationBuilder.shardMinDocCount(
				significantTermsAggregation.getShardMinDocCount());
		}

		if (significantTermsAggregation.getShardSize() != null) {
			significantTermsAggregationBuilder.shardSize(
				significantTermsAggregation.getShardSize());
		}

		if (significantTermsAggregation.getSignificanceHeuristic() != null) {
			significantTermsAggregationBuilder.significanceHeuristic(
				_significanceHeuristicTranslator.translate(
					significantTermsAggregation.getSignificanceHeuristic()));
		}

		return significantTermsAggregationBuilder;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = queryTranslator;
	}

	private final BucketCountThresholdsTranslator
		_bucketCountThresholdsTranslator =
			new BucketCountThresholdsTranslator();
	private final IncludeExcludeTranslator _includeExcludeTranslator =
		new IncludeExcludeTranslator();
	private QueryTranslator<QueryBuilder> _queryTranslator;
	private final SignificanceHeuristicTranslator
		_significanceHeuristicTranslator =
			new SignificanceHeuristicTranslator();

}