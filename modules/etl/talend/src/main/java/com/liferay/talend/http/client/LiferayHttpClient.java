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

package com.liferay.talend.http.client;

import com.liferay.talend.data.store.OAuth2DataStore;
import com.liferay.talend.http.client.codec.XWWWFormURLEncoder;

import javax.json.JsonObject;

import org.talend.sdk.component.api.service.http.Codec;
import org.talend.sdk.component.api.service.http.Header;
import org.talend.sdk.component.api.service.http.HttpClient;
import org.talend.sdk.component.api.service.http.Path;
import org.talend.sdk.component.api.service.http.Request;
import org.talend.sdk.component.api.service.http.Response;

/**
 * @author Igor Beslic
 * @author Zoltán Takács
 */
public interface LiferayHttpClient extends HttpClient {

	@Codec(encoder = XWWWFormURLEncoder.class)
	@Request(method = "POST", path = "/o/oauth2/token")
	public Response<JsonObject> getAccessToken(
		@Header("Content-Type") String contentType,
		OAuth2DataStore oAuth2DataStore);

	@Request(method = "GET", path = "/{endpointPath}")
	public Response<JsonObject> getJsonObjectResponse(
		@Header("Authorization") String authorizationHeader,
		@Header("Accept") String acceptHeader,
		@Path("endpointPath") String endpointPath);

	@Request(method = "GET", path = "/{endpointPath}")
	public Response<String> getRawStringResponse(
		@Header("Authorization") String authorizationHeader,
		@Header("Accept") String acceptHeader,
		@Path("endpointPath") String endpointPath);

}