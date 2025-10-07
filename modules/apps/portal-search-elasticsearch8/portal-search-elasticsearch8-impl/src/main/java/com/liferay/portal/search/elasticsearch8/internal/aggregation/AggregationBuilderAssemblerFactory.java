/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.aggregation;

import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;

import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;

/**
 * @author André de Oliveira
 */
public class AggregationBuilderAssemblerFactory {

	public AggregationBuilderAssemblerFactory(
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		_pipelineAggregationTranslator = pipelineAggregationTranslator;
	}

	public AggregationBuilderAssemblerImpl getAggregationBuilderAssembler(
		AggregationTranslator<AggregationBuilder> aggregationTranslator) {

		return new AggregationBuilderAssemblerImpl(
			aggregationTranslator, _pipelineAggregationTranslator);
	}

	private final PipelineAggregationTranslator<PipelineAggregationBuilder>
		_pipelineAggregationTranslator;

}