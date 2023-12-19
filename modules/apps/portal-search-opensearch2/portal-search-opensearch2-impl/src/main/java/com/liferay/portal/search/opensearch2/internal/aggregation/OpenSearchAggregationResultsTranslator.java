/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.aggregation;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opensearch.client.opensearch._types.aggregations.Aggregate;

/**
 * @author André de Oliveira
 */
public class OpenSearchAggregationResultsTranslator {

	public OpenSearchAggregationResultsTranslator(
		AggregationLookup aggregationLookup,
		AggregationResultTranslatorFactory aggregationResultTranslatorFactory,
		PipelineAggregationLookup pipelineAggregationLookup,
		PipelineAggregationResultTranslatorFactory
			pipelineAggregationResultTranslatorFactory) {

		_aggregationLookup = aggregationLookup;
		_aggregationResultTranslatorFactory =
			aggregationResultTranslatorFactory;
		_pipelineAggregationLookup = pipelineAggregationLookup;
		_pipelineAggregationResultTranslatorFactory =
			pipelineAggregationResultTranslatorFactory;
	}

	public List<AggregationResult> translate(
		Map<String, Aggregate> aggregations) {

		List<AggregationResult> aggregationResults = new ArrayList<>();

		MapUtil.isNotEmptyForEach(
			aggregations,
			(name, aggregate) -> aggregationResults.add(
				translate(aggregate, name)));

		return aggregationResults;
	}

	public interface AggregationLookup {

		public Aggregation lookup(String name);

	}

	public interface PipelineAggregationLookup {

		public PipelineAggregation lookup(String name);

	}

	protected AggregationResult translate(Aggregate aggregate, String name) {
		Aggregation aggregation = _aggregationLookup.lookup(name);

		if (aggregation != null) {
			return aggregation.accept(
				_aggregationResultTranslatorFactory.
					createAggregationResultTranslator(aggregate));
		}

		PipelineAggregation pipelineAggregation =
			_pipelineAggregationLookup.lookup(name);

		if (pipelineAggregation != null) {
			return pipelineAggregation.accept(
				_pipelineAggregationResultTranslatorFactory.
					createPipelineAggregationResultTranslator(aggregate));
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