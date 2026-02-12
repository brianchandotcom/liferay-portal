/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationResult;

/**
 * @author Michael C. Han
 */
public class ScriptedMetricAggregationResult extends AggregationResult {

	public ScriptedMetricAggregationResult(String name, Object value) {
		super(name);

		_value = value;
	}

	public Object getValue() {
		return _value;
	}

	public void setValue(Object value) {
		_value = value;
	}

	private Object _value;

}