/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.pipeline;

import com.liferay.portal.search.aggregation.pipeline.BucketSelectorPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.GapPolicy;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationVisitor;
import com.liferay.portal.search.script.Script;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class BucketSelectorPipelineAggregationImpl
	extends BasePipelineAggregation
	implements BucketSelectorPipelineAggregation {

	public BucketSelectorPipelineAggregationImpl(String name, Script script) {
		super(name);

		_script = script;
	}

	public BucketSelectorPipelineAggregationImpl(
		String name, Script script, Map<String, String> bucketsPathsMap) {

		super(name);

		_script = script;
		_bucketsPathsMap.putAll(bucketsPathsMap);
	}

	@Override
	public <T> T accept(
		PipelineAggregationVisitor<T> pipelineAggregationVisitor) {

		return pipelineAggregationVisitor.visit(this);
	}

	@Override
	public void addBucketPath(String paramName, String bucketPath) {
		_bucketsPathsMap.put(paramName, bucketPath);
	}

	@Override
	public Map<String, String> getBucketsPathsMap() {
		return Collections.unmodifiableMap(_bucketsPathsMap);
	}

	@Override
	public GapPolicy getGapPolicy() {
		return _gapPolicy;
	}

	@Override
	public Script getScript() {
		return _script;
	}

	@Override
	public void setGapPolicy(GapPolicy gapPolicy) {
		_gapPolicy = gapPolicy;
	}

	private final Map<String, String> _bucketsPathsMap = new HashMap<>();
	private GapPolicy _gapPolicy;
	private final Script _script;

}