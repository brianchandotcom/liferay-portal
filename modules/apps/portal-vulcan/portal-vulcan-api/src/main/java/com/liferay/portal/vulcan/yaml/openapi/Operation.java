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

package com.liferay.portal.vulcan.yaml.openapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Peter Shin
 */
public class Operation {

	public String getDescription() {
		return _description;
	}

	public String getOperationId() {
		return _operationId;
	}

	public List<Parameter> getParameters() {
		return _parameters;
	}

	public RequestBody getRequestBody() {
		return _requestBody;
	}

	public Map<Integer, Response> getResponses() {
		return _responses;
	}

	public List<String> getTags() {
		return _tags;
	}

	public void setDescription(String description) {
		_description = description;
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

	public void setResponses(Map<Integer, Response> responses) {
		_responses = responses;
	}

	public void setTags(List<String> tags) {
		_tags = tags;
	}

	private String _description;
	private String _operationId;
	private List<Parameter> _parameters = new ArrayList<>();
	private RequestBody _requestBody;
	private Map<Integer, Response> _responses;
	private List<String> _tags = new ArrayList<>();

}