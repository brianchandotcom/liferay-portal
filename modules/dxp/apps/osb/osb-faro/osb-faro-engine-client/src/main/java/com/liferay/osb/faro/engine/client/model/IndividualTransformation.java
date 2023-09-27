/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class IndividualTransformation {

	public Map<String, Object> getAggregations() {
		return _aggregations;
	}

	public Map<String, Object> getTerms() {
		return _terms;
	}

	public long getTotalElements() {
		return _totalElements;
	}

	public void setAggregations(Map<String, Object> aggregations) {
		_aggregations = aggregations;
	}

	public void setTerms(Map<String, Object> terms) {
		_terms = terms;
	}

	public void setTotalElements(long totalElements) {
		_totalElements = totalElements;
	}

	private Map<String, Object> _aggregations;
	private Map<String, Object> _terms;
	private long _totalElements;

}