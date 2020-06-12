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

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public String getDescription() {
		return _description;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public String getOperationId() {
		return _operationId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public List<Parameter> getParameters() {
		return _parameters;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public RequestBody getRequestBody() {
		return _requestBody;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Map<ResponseCode, Response> getResponses() {
		return _responses;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public List<String> getTags() {
		return _tags;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setDescription(String description) {
		_description = description;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setOperationId(String operationId) {
		_operationId = operationId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setParameters(List<Parameter> parameters) {
		_parameters = parameters;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setRequestBody(RequestBody requestBody) {
		_requestBody = requestBody;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setResponses(Map<ResponseCode, Response> responses) {
		_responses = responses;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setTags(List<String> tags) {
		_tags = tags;
	}

	private String _description;
	private String _operationId;
	private List<Parameter> _parameters = new ArrayList<>();
	private RequestBody _requestBody;
	private Map<ResponseCode, Response> _responses;
	private List<String> _tags = new ArrayList<>();

}