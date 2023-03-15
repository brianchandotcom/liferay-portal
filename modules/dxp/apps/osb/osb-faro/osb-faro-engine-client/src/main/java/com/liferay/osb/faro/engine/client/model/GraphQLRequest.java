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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class GraphQLRequest {

	public String getOperationName() {
		return _operationName;
	}

	public String getQuery() {
		return _query;
	}

	public Map<String, Object> getVariables() {
		return _variables;
	}

	public void setOperationName(String operationName) {
		_operationName = operationName;
	}

	public void setQuery(String query) {
		_query = query;
	}

	public void setVariables(Map<String, Object> variables) {
		_variables = variables;
	}

	private String _operationName;
	private String _query;
	private Map<String, Object> _variables = new HashMap<>();

}