/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.pipeline;

import com.liferay.portal.search.script.Script;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface BucketSelectorPipelineAggregation extends PipelineAggregation {

	public void addBucketPath(String paramName, String bucketPath);

	public Map<String, String> getBucketsPathsMap();

	public GapPolicy getGapPolicy();

	public Script getScript();

	public void setGapPolicy(GapPolicy gapPolicy);

}