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

package com.liferay.portal.search.elasticsearch6.internal.aggregation;

import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket.DateHistogramAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket.DateRangeAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket.FilterAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket.FiltersAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket.GeoDistanceAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket.HistogramAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket.RangeAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket.SignificantTermsAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket.SignificantTextAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket.TermsAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.metrics.ScriptedMetricAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.metrics.TopHitsAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.metrics.WeightedAvgAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.pipeline.ElasticsearchPipelineAggregationVisitorFixture;
import com.liferay.portal.search.elasticsearch6.internal.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.elasticsearch6.internal.sort.ElasticsearchSortFieldTranslatorFixture;

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
			new AggregationBuilderAssemblerFactoryImpl() {
				{
					setPipelineAggregationTranslator(
						pipelineAggregationTranslator);
				}
			};

		ElasticsearchAggregationVisitor elasticsearchAggregationVisitor =
			new ElasticsearchAggregationVisitor() {
				{
					setAggregationBuilderAssemblerFactory(
						aggregationBuilderAssemblerFactory);
					setDateHistogramAggregationTranslator(
						new DateHistogramAggregationTranslatorImpl());
					setDateRangeAggregationTranslator(
						new DateRangeAggregationTranslatorImpl());
					setHistogramAggregationTranslator(
						new HistogramAggregationTranslatorImpl());
					setPipelineAggregationTranslator(
						pipelineAggregationTranslator);
					setRangeAggregationTranslator(
						new RangeAggregationTranslatorImpl());
					setTermsAggregationTranslator(
						new TermsAggregationTranslatorImpl());
				}
			};

		injectGeoAggregationTranslators(elasticsearchAggregationVisitor);
		injectQueryAggregationTranslators(
			elasticsearchAggregationVisitor,
			elasticsearchQueryTranslatorFixture);
		injectScriptAggregationTranslators(elasticsearchAggregationVisitor);
		injectTopHitsAggregationTranslators(
			elasticsearchAggregationVisitor,
			elasticsearchQueryTranslatorFixture);

		_elasticsearchAggregationVisitor = elasticsearchAggregationVisitor;
	}

	public ElasticsearchAggregationVisitor
		getElasticsearchAggregationVisitor() {

		return _elasticsearchAggregationVisitor;
	}

	protected static void injectGeoAggregationTranslators(
		ElasticsearchAggregationVisitor elasticsearchAggregationVisitor) {

		elasticsearchAggregationVisitor.setGeoDistanceAggregationTranslator(
			new GeoDistanceAggregationTranslatorImpl());
	}

	protected static void injectQueryAggregationTranslators(
		ElasticsearchAggregationVisitor elasticsearchAggregationVisitor,
		ElasticsearchQueryTranslatorFixture
			elasticsearchQueryTranslatorFixture) {

		elasticsearchAggregationVisitor.setFilterAggregationTranslator(
			new FilterAggregationTranslatorImpl() {
				{
					setQueryTranslator(
						elasticsearchQueryTranslatorFixture.
							getElasticsearchQueryTranslator());
				}
			});
		elasticsearchAggregationVisitor.setFiltersAggregationTranslator(
			new FiltersAggregationTranslatorImpl() {
				{
					setQueryTranslator(
						elasticsearchQueryTranslatorFixture.
							getElasticsearchQueryTranslator());
				}
			});
		elasticsearchAggregationVisitor.
			setSignificantTermsAggregationTranslator(
				new SignificantTermsAggregationTranslatorImpl() {
					{
						setQueryTranslator(
							elasticsearchQueryTranslatorFixture.
								getElasticsearchQueryTranslator());
					}
				});
		elasticsearchAggregationVisitor.setSignificantTextAggregationTranslator(
			new SignificantTextAggregationTranslatorImpl() {
				{
					setQueryTranslator(
						elasticsearchQueryTranslatorFixture.
							getElasticsearchQueryTranslator());
				}
			});
	}

	protected static void injectScriptAggregationTranslators(
		ElasticsearchAggregationVisitor elasticsearchAggregationVisitor) {

		elasticsearchAggregationVisitor.setScriptedMetricAggregationTranslator(
			new ScriptedMetricAggregationTranslatorImpl());
		elasticsearchAggregationVisitor.setWeightedAvgAggregationTranslator(
			new WeightedAvgAggregationTranslatorImpl());
	}

	protected static void injectTopHitsAggregationTranslators(
		ElasticsearchAggregationVisitor elasticsearchAggregationVisitor,
		ElasticsearchQueryTranslatorFixture
			elasticsearchQueryTranslatorFixture) {

		ElasticsearchSortFieldTranslatorFixture
			elasticsearchSortFieldTranslatorFixture =
				new ElasticsearchSortFieldTranslatorFixture(
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());

		elasticsearchAggregationVisitor.setTopHitsAggregationTranslator(
			new TopHitsAggregationTranslatorImpl() {
				{
					setQueryTranslator(
						elasticsearchQueryTranslatorFixture.
							getElasticsearchQueryTranslator());
					setSortFieldTranslator(
						elasticsearchSortFieldTranslatorFixture.
							getElasticsearchSortFieldTranslator());
				}
			});
	}

	private final ElasticsearchAggregationVisitor
		_elasticsearchAggregationVisitor;

}