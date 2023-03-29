/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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