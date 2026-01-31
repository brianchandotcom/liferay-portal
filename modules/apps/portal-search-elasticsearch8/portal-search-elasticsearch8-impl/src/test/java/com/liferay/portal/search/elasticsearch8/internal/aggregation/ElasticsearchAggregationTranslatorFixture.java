/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.aggregation;

import com.liferay.portal.kernel.test.ReflectionTestUtil;

/**
 * @author Michael C. Han
 */
public class ElasticsearchAggregationTranslatorFixture {

	public ElasticsearchAggregationTranslatorFixture() {
		ElasticsearchAggregationTranslator elasticsearchAggregationTranslator =
			new ElasticsearchAggregationTranslator();

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_pipelineAggregationTranslator",
			new ElasticsearchPipelineAggregationTranslator());

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