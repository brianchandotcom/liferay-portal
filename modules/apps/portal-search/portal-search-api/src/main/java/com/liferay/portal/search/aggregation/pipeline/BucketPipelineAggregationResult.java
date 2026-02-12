/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.pipeline;

import com.liferay.portal.search.aggregation.AggregationResult;

/**
 * @author André de Oliveira
 */
public abstract class BucketPipelineAggregationResult
	extends AggregationResult {

	public BucketPipelineAggregationResult(String name, double value) {
		super(name);

		_value = value;
	}

	public String[] getKeys() {
		return _keys;
	}

	public double getValue() {
		return _value;
	}

	public void setKeys(String... keys) {
		_keys = keys;
	}

	private String[] _keys;
	private final double _value;

}