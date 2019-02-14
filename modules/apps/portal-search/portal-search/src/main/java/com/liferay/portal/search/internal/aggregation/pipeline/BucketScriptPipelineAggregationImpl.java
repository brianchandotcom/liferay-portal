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

package com.liferay.portal.search.internal.aggregation.pipeline;

import com.liferay.portal.search.aggregation.pipeline.BucketScriptPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationVisitor;
import com.liferay.portal.search.script.Script;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class BucketScriptPipelineAggregationImpl
	extends BasePipelineAggregation implements BucketScriptPipelineAggregation {

	public BucketScriptPipelineAggregationImpl(String name, Script script) {
		super(name);

		_script = script;
	}

	public BucketScriptPipelineAggregationImpl(
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
	public String getFormat() {
		return _format;
	}

	@Override
	public Script getScript() {
		return _script;
	}

	public void setFormat(String format) {
		_format = format;
	}

	private final Map<String, String> _bucketsPathsMap = new HashMap<>();
	private String _format;
	private final Script _script;

}