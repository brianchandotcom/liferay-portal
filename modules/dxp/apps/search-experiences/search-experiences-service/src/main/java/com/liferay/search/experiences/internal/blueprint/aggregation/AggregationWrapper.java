/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.aggregation;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;

/**
 * @author Petteri Karttunen
 */
public final class AggregationWrapper {

	public AggregationWrapper(Aggregation aggregation) {
		_aggregation = aggregation;

		_pipelineAggregation = null;
		_pipeline = false;
	}

	public AggregationWrapper(PipelineAggregation pipelineAggregation) {
		_pipelineAggregation = pipelineAggregation;

		_aggregation = null;
		_pipeline = true;
	}

	public Aggregation getAggregation() {
		return _aggregation;
	}

	public PipelineAggregation getPipelineAggregation() {
		return _pipelineAggregation;
	}

	public boolean isPipeline() {
		return _pipeline;
	}

	private AggregationWrapper() {
		_aggregation = null;
		_pipelineAggregation = null;
		_pipeline = true;
	}

	private final Aggregation _aggregation;
	private final boolean _pipeline;
	private final PipelineAggregation _pipelineAggregation;

}