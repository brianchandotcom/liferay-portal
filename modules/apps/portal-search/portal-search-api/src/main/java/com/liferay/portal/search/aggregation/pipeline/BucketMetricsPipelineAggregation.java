/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.pipeline;

/**
 * @author Michael C. Han
 */
public interface BucketMetricsPipelineAggregation extends PipelineAggregation {

	public String getBucketsPath();

	public String getFormat();

	public GapPolicy getGapPolicy();

	public void setFormat(String format);

	public void setGapPolicy(GapPolicy gapPolicy);

}