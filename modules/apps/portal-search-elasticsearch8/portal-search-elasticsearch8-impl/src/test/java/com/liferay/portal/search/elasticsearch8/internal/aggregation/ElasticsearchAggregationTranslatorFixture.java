/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.aggregation;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.bucket.TermsAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.metrics.TopHitsAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.metrics.WeightedAvgAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.pipeline.ElasticsearchPipelineAggregationTranslatorFixture;
import com.liferay.portal.search.elasticsearch8.internal.query.ElasticsearchQueryTranslator;
import com.liferay.portal.search.elasticsearch8.internal.sort.ElasticsearchSortFieldTranslatorFixture;

import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;

/**
 * @author Michael C. Han
 */
public class ElasticsearchAggregationTranslatorFixture {

	public ElasticsearchAggregationTranslatorFixture() {
		ElasticsearchPipelineAggregationTranslatorFixture
			pipelineAggregationTranslatorFixture =
				new ElasticsearchPipelineAggregationTranslatorFixture();

		ElasticsearchQueryTranslator elasticsearchQueryTranslator =
			new ElasticsearchQueryTranslator();

		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator =
				pipelineAggregationTranslatorFixture.
					getElasticsearchPipelineAggregationTranslator();

		AggregationBuilderAssemblerFactory aggregationBuilderAssemblerFactory =
			new AggregationBuilderAssemblerFactory(
				pipelineAggregationTranslator);

		ElasticsearchAggregationTranslator elasticsearchAggregationTranslator =
			new ElasticsearchAggregationTranslator();

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_aggregationBuilderAssemblerFactory",
			aggregationBuilderAssemblerFactory);
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_pipelineAggregationTranslator", pipelineAggregationTranslator);
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator, "_termsAggregationTranslator",
			new TermsAggregationTranslator());

		_injectScriptAggregationTranslators(elasticsearchAggregationTranslator);
		_injectTopHitsAggregationTranslators(
			elasticsearchAggregationTranslator, elasticsearchQueryTranslator);

		_elasticsearchAggregationTranslator =
			elasticsearchAggregationTranslator;
	}

	public ElasticsearchAggregationTranslator
		getElasticsearchAggregationTranslator() {

		return _elasticsearchAggregationTranslator;
	}

	private void _injectScriptAggregationTranslators(
		ElasticsearchAggregationTranslator elasticsearchAggregationTranslator) {

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_weightedAvgAggregationTranslator",
			new WeightedAvgAggregationTranslator());
	}

	private void _injectTopHitsAggregationTranslators(
		ElasticsearchAggregationTranslator elasticsearchAggregationTranslator,
		ElasticsearchQueryTranslator elasticsearchQueryTranslator) {

		ElasticsearchSortFieldTranslatorFixture
			elasticsearchSortFieldTranslatorFixture =
				new ElasticsearchSortFieldTranslatorFixture(
					elasticsearchQueryTranslator);

		TopHitsAggregationTranslator topHitsAggregationTranslator =
			new TopHitsAggregationTranslator();

		ReflectionTestUtil.setFieldValue(
			topHitsAggregationTranslator, "_queryTranslator",
			elasticsearchQueryTranslator);
		ReflectionTestUtil.setFieldValue(
			topHitsAggregationTranslator, "_sortFieldTranslator",
			elasticsearchSortFieldTranslatorFixture.
				getElasticsearchSortFieldTranslator());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator, "_topHitsAggregationTranslator",
			topHitsAggregationTranslator);
	}

	private final ElasticsearchAggregationTranslator
		_elasticsearchAggregationTranslator;

}