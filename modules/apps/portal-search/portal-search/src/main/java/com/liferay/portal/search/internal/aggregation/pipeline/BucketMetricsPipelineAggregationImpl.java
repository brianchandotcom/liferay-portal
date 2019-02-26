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

import com.liferay.portal.search.aggregation.pipeline.BucketMetricsPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.GapPolicy;

/**
 * @author Michael C. Han
 */
public abstract class BucketMetricsPipelineAggregationImpl
	extends BasePipelineAggregation
	implements BucketMetricsPipelineAggregation {

	public BucketMetricsPipelineAggregationImpl(
		String name, String bucketsPath) {

		super(name);

		_bucketsPath = bucketsPath;
	}

	@Override
	public String getBucketsPath() {
		return _bucketsPath;
	}

	@Override
	public String getFormat() {
		return _format;
	}

	@Override
	public GapPolicy getGapPolicy() {
		return _gapPolicy;
	}

	@Override
	public void setFormat(String format) {
		_format = format;
	}

	@Override
	public void setGapPolicy(GapPolicy gapPolicy) {
		_gapPolicy = gapPolicy;
	}

	private final String _bucketsPath;
	private String _format;
	private GapPolicy _gapPolicy;

}