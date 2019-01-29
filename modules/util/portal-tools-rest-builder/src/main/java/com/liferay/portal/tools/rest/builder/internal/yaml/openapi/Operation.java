/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools.rest.builder.internal.yaml.openapi;

import java.util.List;
import java.util.Map;

/**
 * @author Peter Shin
 */
public class Operation {

	public String getOperationId() {
		return _operationId;
	}

	public List<Parameter> getParameters() {
		return _parameters;
	}

	public RequestBody getRequestBody() {
		return _requestBody;
	}

	public Map<String, Response> getResponses() {
		return _responses;
	}

	public void setOperationId(String operationId) {
		_operationId = operationId;
	}

	public void setParameters(List<Parameter> parameters) {
		_parameters = parameters;
	}

	public void setRequestBody(RequestBody requestBody) {
		_requestBody = requestBody;
	}

	public void setResponses(Map<String, Response> responses) {
		_responses = responses;
	}

	private String _operationId;
	private List<Parameter> _parameters;
	private RequestBody _requestBody;
	private Map<String, Response> _responses;

}