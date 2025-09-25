/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation;

import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;

import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = AggregationBuilderAssemblerFactory.class)
public class AggregationBuilderAssemblerFactoryImpl
	implements AggregationBuilderAssemblerFactory {

	@Override
	public AggregationBuilderAssemblerImpl getAggregationBuilderAssembler(
		AggregationTranslator<AggregationBuilder> aggregationTranslator) {

		return new AggregationBuilderAssemblerImpl(
			aggregationTranslator, _pipelineAggregationTranslator);
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private PipelineAggregationTranslator<PipelineAggregationBuilder>
		_pipelineAggregationTranslator;

}