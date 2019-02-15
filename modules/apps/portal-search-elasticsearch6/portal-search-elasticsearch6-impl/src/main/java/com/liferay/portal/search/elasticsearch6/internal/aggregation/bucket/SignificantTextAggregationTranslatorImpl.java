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

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.bucket.SignificantTextAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.BaseAggregationTranslator;
import com.liferay.portal.search.elasticsearch6.internal.significance.SignificanceHeuristicTranslator;
import com.liferay.portal.search.query.QueryTranslator;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTextAggregationBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = SignificantTextAggregationTranslator.class)
public class SignificantTextAggregationTranslatorImpl
	implements SignificantTextAggregationTranslator {

	@Override
	public SignificantTextAggregationBuilder translate(
		SignificantTextAggregation significantTextAggregation,
		AggregationTranslator<AggregationBuilder> aggregationTranslator,
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		SignificantTextAggregationBuilder significantTextAggregationBuilder =
			AggregationBuilders.significantText(
				significantTextAggregation.getName(),
				significantTextAggregation.getField());

		if (significantTextAggregation.getBucketCountThresholds() != null) {
			significantTextAggregationBuilder.bucketCountThresholds(
				_bucketCountThresholdsTranslator.translate(
					significantTextAggregation.getBucketCountThresholds()));
		}

		significantTextAggregationBuilder.bucketCountThresholds();

		if (significantTextAggregation.getBackgroundFilterQuery() != null) {
			significantTextAggregationBuilder.backgroundFilter(
				_queryTranslator.translate(
					significantTextAggregation.getBackgroundFilterQuery()));
		}

		if (significantTextAggregation.getFilterDuplicateText() != null) {
			significantTextAggregationBuilder.filterDuplicateText(
				significantTextAggregation.getFilterDuplicateText());
		}

		if (significantTextAggregation.getIncludeExcludeClause() != null) {
			significantTextAggregationBuilder.includeExclude(
				_includeExcludeTranslator.translate(
					significantTextAggregation.getIncludeExcludeClause()));
		}

		if (significantTextAggregation.getMinDocCount() != null) {
			significantTextAggregationBuilder.minDocCount(
				significantTextAggregation.getMinDocCount());
		}

		if (significantTextAggregation.getShardMinDocCount() != null) {
			significantTextAggregationBuilder.shardMinDocCount(
				significantTextAggregation.getShardMinDocCount());
		}

		if (significantTextAggregation.getShardSize() != null) {
			significantTextAggregationBuilder.shardSize(
				significantTextAggregation.getShardSize());
		}

		if (significantTextAggregation.getSignificanceHeuristic() != null) {
			significantTextAggregationBuilder.significanceHeuristic(
				_significanceHeuristicTranslator.translate(
					significantTextAggregation.getSignificanceHeuristic()));
		}

		if (ListUtil.isNotEmpty(significantTextAggregation.getSourceFields())) {
			significantTextAggregationBuilder.sourceFieldNames(
				significantTextAggregation.getSourceFields());
		}

		_baseAggregationTranslator.translate(
			significantTextAggregationBuilder, significantTextAggregation,
			aggregationTranslator, pipelineAggregationTranslator);

		return significantTextAggregationBuilder;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = queryTranslator;
	}

	private final BaseAggregationTranslator _baseAggregationTranslator =
		new BaseAggregationTranslator();
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