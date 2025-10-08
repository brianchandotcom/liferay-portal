/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.aggregation;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.bucket.TermsAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.pipeline.ElasticsearchPipelineAggregationTranslatorFixture;

import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;

/**
 * @author Michael C. Han
 */
public class ElasticsearchAggregationTranslatorFixture {

	public ElasticsearchAggregationTranslatorFixture() {
		ElasticsearchPipelineAggregationTranslatorFixture
			pipelineAggregationTranslatorFixture =
				new ElasticsearchPipelineAggregationTranslatorFixture();

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

		_elasticsearchAggregationTranslator =
			elasticsearchAggregationTranslator;
	}

	public ElasticsearchAggregationTranslator
		getElasticsearchAggregationTranslator() {

		return _elasticsearchAggregationTranslator;
	}

	private final ElasticsearchAggregationTranslator
		_elasticsearchAggregationTranslator;

}