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

package com.liferay.headless.commerce.machine.learning.client.aggregation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public class Aggregation {

	public Map<String, String> getAggregationTerms() {
		return _aggregationTerms;
	}

	public void setAggregationTerms(Map<String, String> aggregationTerms) {
		_aggregationTerms = aggregationTerms;
	}

	private Map<String, String> _aggregationTerms = new HashMap<>();

}