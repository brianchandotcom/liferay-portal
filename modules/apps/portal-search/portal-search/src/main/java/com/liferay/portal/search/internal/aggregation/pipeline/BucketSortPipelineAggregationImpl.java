/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.pipeline;

import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.GapPolicy;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationVisitor;
import com.liferay.portal.search.sort.FieldSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class BucketSortPipelineAggregationImpl
	extends BasePipelineAggregation implements BucketSortPipelineAggregation {

	public BucketSortPipelineAggregationImpl(String name) {
		super(name);
	}

	@Override
	public <T> T accept(
		PipelineAggregationVisitor<T> pipelineAggregationVisitor) {

		return pipelineAggregationVisitor.visit(this);
	}

	@Override
	public void addSortFields(FieldSort... fieldSorts) {
		Collections.addAll(_fieldSorts, fieldSorts);
	}

	@Override
	public List<FieldSort> getFieldSorts() {
		return Collections.unmodifiableList(_fieldSorts);
	}

	@Override
	public Integer getFrom() {
		return _from;
	}

	@Override
	public GapPolicy getGapPolicy() {
		return _gapPolicy;
	}

	@Override
	public Integer getSize() {
		return _size;
	}

	@Override
	public void setFrom(Integer from) {
		_from = from;
	}

	@Override
	public void setGapPolicy(GapPolicy gapPolicy) {
		_gapPolicy = gapPolicy;
	}

	@Override
	public void setSize(Integer size) {
		_size = size;
	}

	private final List<FieldSort> _fieldSorts = new ArrayList<>();
	private Integer _from;
	private GapPolicy _gapPolicy;
	private Integer _size;

}