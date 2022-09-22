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

package com.liferay.portal.search.elasticsearch7.internal.aggregation;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.DateHistogramAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.DateRangeAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.FilterAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.FilterAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.FiltersAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.FiltersAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.GeoDistanceAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.HistogramAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.RangeAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.SignificantTermsAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.SignificantTermsAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.SignificantTextAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.SignificantTextAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.TermsAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.ScriptedMetricAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.TopHitsAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.TopHitsAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.WeightedAvgAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.pipeline.ElasticsearchPipelineAggregationVisitorFixture;
import com.liferay.portal.search.elasticsearch7.internal.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.elasticsearch7.internal.sort.ElasticsearchSortFieldTranslatorFixture;

import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;

/**
 * @author Michael C. Han
 */
public class ElasticsearchAggregationVisitorFixture {

	public ElasticsearchAggregationVisitorFixture() {
		ElasticsearchPipelineAggregationVisitorFixture
			pipelineAggregationVisitorFixture =
				new ElasticsearchPipelineAggregationVisitorFixture();

		ElasticsearchQueryTranslatorFixture
			elasticsearchQueryTranslatorFixture =
				new ElasticsearchQueryTranslatorFixture();

		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator =
				pipelineAggregationVisitorFixture.
					getElasticsearchPipelineAggregationVisitor();

		AggregationBuilderAssemblerFactory aggregationBuilderAssemblerFactory =
			new AggregationBuilderAssemblerFactoryImpl();

		ReflectionTestUtil.setFieldValue(
			aggregationBuilderAssemblerFactory,
			"_pipelineAggregationTranslator", pipelineAggregationTranslator);

		ElasticsearchAggregationVisitor elasticsearchAggregationVisitor =
			new ElasticsearchAggregationVisitor();

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor,
			"_aggregationBuilderAssemblerFactory",
			aggregationBuilderAssemblerFactory);
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor,
			"_dateHistogramAggregationTranslator",
			new DateHistogramAggregationTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor, "_dateRangeAggregationTranslator",
			new DateRangeAggregationTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor, "_histogramAggregationTranslator",
			new HistogramAggregationTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor, "_pipelineAggregationTranslator",
			pipelineAggregationTranslator);
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor, "_rangeAggregationTranslator",
			new RangeAggregationTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor, "_termsAggregationTranslator",
			new TermsAggregationTranslatorImpl());

		_injectGeoAggregationTranslators(elasticsearchAggregationVisitor);
		_injectQueryAggregationTranslators(
			elasticsearchAggregationVisitor,
			elasticsearchQueryTranslatorFixture);
		_injectScriptAggregationTranslators(elasticsearchAggregationVisitor);
		_injectTopHitsAggregationTranslators(
			elasticsearchAggregationVisitor,
			elasticsearchQueryTranslatorFixture);

		_elasticsearchAggregationVisitor = elasticsearchAggregationVisitor;
	}

	public ElasticsearchAggregationVisitor
		getElasticsearchAggregationVisitor() {

		return _elasticsearchAggregationVisitor;
	}

	private void _injectGeoAggregationTranslators(
		ElasticsearchAggregationVisitor elasticsearchAggregationVisitor) {

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor,
			"_geoDistanceAggregationTranslator",
			new GeoDistanceAggregationTranslatorImpl());
	}

	private void _injectQueryAggregationTranslators(
		ElasticsearchAggregationVisitor elasticsearchAggregationVisitor,
		ElasticsearchQueryTranslatorFixture
			elasticsearchQueryTranslatorFixture) {

		FilterAggregationTranslator filterAggregationTranslator =
			new FilterAggregationTranslatorImpl();

		ReflectionTestUtil.setFieldValue(
			filterAggregationTranslator, "_queryTranslator",
			elasticsearchQueryTranslatorFixture.
				getElasticsearchQueryTranslator());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor, "_filterAggregationTranslator",
			filterAggregationTranslator);

		FiltersAggregationTranslator filtersAggregationTranslator =
			new FiltersAggregationTranslatorImpl();

		ReflectionTestUtil.setFieldValue(
			filtersAggregationTranslator, "_queryTranslator",
			elasticsearchQueryTranslatorFixture.
				getElasticsearchQueryTranslator());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor, "_filtersAggregationTranslator",
			filtersAggregationTranslator);

		SignificantTermsAggregationTranslator
			significantTermsAggregationTranslator =
				new SignificantTermsAggregationTranslatorImpl();

		ReflectionTestUtil.setFieldValue(
			significantTermsAggregationTranslator, "_queryTranslator",
			elasticsearchQueryTranslatorFixture.
				getElasticsearchQueryTranslator());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor,
			"_significantTermsAggregationTranslator",
			significantTermsAggregationTranslator);

		SignificantTextAggregationTranslator
			significantTextAggregationTranslator =
				new SignificantTextAggregationTranslatorImpl();

		ReflectionTestUtil.setFieldValue(
			significantTextAggregationTranslator, "_queryTranslator",
			elasticsearchQueryTranslatorFixture.
				getElasticsearchQueryTranslator());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor,
			"_significantTextAggregationTranslator",
			significantTextAggregationTranslator);
	}

	private void _injectScriptAggregationTranslators(
		ElasticsearchAggregationVisitor elasticsearchAggregationVisitor) {

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor,
			"_scriptedMetricAggregationTranslator",
			new ScriptedMetricAggregationTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor,
			"_weightedAvgAggregationTranslator",
			new WeightedAvgAggregationTranslatorImpl());
	}

	private void _injectTopHitsAggregationTranslators(
		ElasticsearchAggregationVisitor elasticsearchAggregationVisitor,
		ElasticsearchQueryTranslatorFixture
			elasticsearchQueryTranslatorFixture) {

		ElasticsearchSortFieldTranslatorFixture
			elasticsearchSortFieldTranslatorFixture =
				new ElasticsearchSortFieldTranslatorFixture(
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());

		TopHitsAggregationTranslator topHitsAggregationTranslator =
			new TopHitsAggregationTranslatorImpl();

		ReflectionTestUtil.setFieldValue(
			topHitsAggregationTranslator, "_queryTranslator",
			elasticsearchQueryTranslatorFixture.
				getElasticsearchQueryTranslator());
		ReflectionTestUtil.setFieldValue(
			topHitsAggregationTranslator, "_sortFieldTranslator",
			elasticsearchSortFieldTranslatorFixture.
				getElasticsearchSortFieldTranslator());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationVisitor, "_topHitsAggregationTranslator",
			topHitsAggregationTranslator);
	}

	private final ElasticsearchAggregationVisitor
		_elasticsearchAggregationVisitor;

}