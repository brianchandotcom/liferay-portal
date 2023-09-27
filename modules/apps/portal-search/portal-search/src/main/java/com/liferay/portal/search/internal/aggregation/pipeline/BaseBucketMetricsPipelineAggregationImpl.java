/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.pipeline;

import com.liferay.portal.search.aggregation.pipeline.BucketMetricsPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.GapPolicy;

/**
 * @author Michael C. Han
 */
public abstract class BaseBucketMetricsPipelineAggregationImpl
	extends BasePipelineAggregation
	implements BucketMetricsPipelineAggregation {

	public BaseBucketMetricsPipelineAggregationImpl(
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