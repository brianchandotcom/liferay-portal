/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

	@SuppressWarnings("unused")
	private AggregationWrapper() {
		_aggregation = null;
		_pipelineAggregation = null;
		_pipeline = true;
	}

	private final Aggregation _aggregation;
	private final boolean _pipeline;
	private final PipelineAggregation _pipelineAggregation;

}