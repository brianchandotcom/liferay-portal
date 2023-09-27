/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.pipeline;

import com.liferay.portal.search.sort.FieldSort;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface BucketSortPipelineAggregation extends PipelineAggregation {

	public void addSortFields(FieldSort... fieldSorts);

	public List<FieldSort> getFieldSorts();

	public Integer getFrom();

	public GapPolicy getGapPolicy();

	public Integer getSize();

	public void setFrom(Integer from);

	public void setGapPolicy(GapPolicy gapPolicy);

	public void setSize(Integer size);

}