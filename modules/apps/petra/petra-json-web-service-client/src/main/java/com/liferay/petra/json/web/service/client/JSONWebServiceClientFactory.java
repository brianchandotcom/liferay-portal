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

package com.liferay.petra.json.web.service.client;

import com.liferay.petra.json.web.service.client.internal.JSONWebServiceClientImpl;
import com.liferay.petra.json.web.service.client.internal.OAuthJSONWebServiceClientImpl;

import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class JSONWebServiceClientFactory {

	public static JSONWebServiceClient getInstance(
			Map<String, Object> properties, boolean oAuthEnabled)
		throws Exception {

		JSONWebServiceClientImpl jsonWebServiceClientImpl = null;

		if (oAuthEnabled) {
			jsonWebServiceClientImpl = new OAuthJSONWebServiceClientImpl();
		}
		else {
			jsonWebServiceClientImpl = new JSONWebServiceClientImpl();
		}

		jsonWebServiceClientImpl.activate(properties);

		return jsonWebServiceClientImpl;
	}

	private JSONWebServiceClientFactory() {
	}

}