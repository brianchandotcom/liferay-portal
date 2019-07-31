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

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;

import java.util.List;
import java.util.stream.Stream;

import org.elasticsearch.search.aggregations.Aggregations;

/**
 * @author André de Oliveira
 */
public class ElasticsearchAggregationResultsTranslator {

	public ElasticsearchAggregationResultsTranslator(
		AggregationResultTranslatorFactory aggregationResultTranslatorFactory,
		PipelineAggregationResultTranslatorFactory
			pipelineAggregationResultTranslatorFactory,
		AggregationLookup aggregationLookup,
		PipelineAggregationLookup pipelineAggregationLookup) {

		_aggregationResultTranslatorFactory =
			aggregationResultTranslatorFactory;
		_pipelineAggregationResultTranslatorFactory =
			pipelineAggregationResultTranslatorFactory;
		_aggregationLookup = aggregationLookup;
		_pipelineAggregationLookup = pipelineAggregationLookup;
	}

	public Stream<AggregationResult> translate(
		Aggregations elasticsearchAggregations) {

		Stream<org.elasticsearch.search.aggregations.Aggregation> stream =
			getElasticsearchAggregations(elasticsearchAggregations);

		return stream.map(
			this::translate
		).filter(
			aggregationResult -> aggregationResult != null
		);
	}

	public interface AggregationLookup {

		public Aggregation lookup(String name);

	}

	public interface PipelineAggregationLookup {

		public PipelineAggregation lookup(String name);

	}

	protected Stream<org.elasticsearch.search.aggregations.Aggregation>
		getElasticsearchAggregations(Aggregations aggregations) {

		List<org.elasticsearch.search.aggregations.Aggregation> list =
			aggregations.asList();

		return list.stream();
	}

	protected AggregationResult translate(
		org.elasticsearch.search.aggregations.Aggregation
			elasticsearchAggregation) {

		String name = elasticsearchAggregation.getName();

		Aggregation aggregation = _aggregationLookup.lookup(name);

		if (aggregation != null) {
			return aggregation.accept(
				_aggregationResultTranslatorFactory.
					createAggregationResultTranslator(
						elasticsearchAggregation));
		}

		PipelineAggregation pipelineAggregation =
			_pipelineAggregationLookup.lookup(name);

		if (pipelineAggregation != null) {
			return pipelineAggregation.accept(
				_pipelineAggregationResultTranslatorFactory.
					createPipelineAggregationResultTranslator(
						elasticsearchAggregation));
		}

		return null;
	}

	private final AggregationLookup _aggregationLookup;
	private final AggregationResultTranslatorFactory
		_aggregationResultTranslatorFactory;
	private final PipelineAggregationLookup _pipelineAggregationLookup;
	private final PipelineAggregationResultTranslatorFactory
		_pipelineAggregationResultTranslatorFactory;

}