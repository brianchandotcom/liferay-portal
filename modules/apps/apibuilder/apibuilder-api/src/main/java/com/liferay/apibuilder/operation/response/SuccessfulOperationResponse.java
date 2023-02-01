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

package com.liferay.apibuilder.operation.response;

import com.liferay.apibuilder.model.APIBuilderEntry;
import com.liferay.apibuilder.operation.Response;

/**
 * @author Carlos Correa
 */
public class SuccessfulOperationResponse implements OperationResponse {

	public SuccessfulOperationResponse(
		APIBuilderEntry apiBuilderEntry, Response response) {

		_apiBuilderEntry = apiBuilderEntry;
		_response = response;
	}

	public APIBuilderEntry getApiBuilderEntry() {
		return _apiBuilderEntry;
	}

	@Override
	public Response getResponse() {
		return _response;
	}

	@Override
	public ResponseCode getResponseCode() {
		return ResponseCode.SUCCESSFUL;
	}

	private final APIBuilderEntry _apiBuilderEntry;
	private final Response _response;

}