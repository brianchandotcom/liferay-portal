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

package com.liferay.lcs.service.impl;

import com.liferay.petra.json.web.service.client.BaseJSONWebServiceClientHandler;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class BaseLCSServiceImpl extends BaseJSONWebServiceClientHandler {

	@Override
	public JSONWebServiceClient getJSONWebServiceClient() {
		return _jsonWebServiceClient;
	}

	public void setJSONWebServiceClient(
		JSONWebServiceClient jsonWebServiceClient) {

		_jsonWebServiceClient = jsonWebServiceClient;
	}

	private JSONWebServiceClient _jsonWebServiceClient;

}