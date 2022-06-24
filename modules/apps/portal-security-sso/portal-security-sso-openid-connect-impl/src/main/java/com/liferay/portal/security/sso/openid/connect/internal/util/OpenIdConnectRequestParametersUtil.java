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

package com.liferay.portal.security.sso.openid.connect.internal.util;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;

import java.net.URI;

import java.util.List;
import java.util.function.BiConsumer;

import net.minidev.json.JSONObject;

/**
 * Responsible for fetching static configured common request parameters
 *
 * @author Arthur Chan
 */
public class OpenIdConnectRequestParametersUtil {

	public static void consumeCustomRequestParameters(
			JSONObject requestParametersJSONObject,
			BiConsumer<String, String[]> biConsumer)
		throws ParseException {

		if (!requestParametersJSONObject.containsKey(
				"custom_request_parameters")) {

			return;
		}

		JSONObject customRequestParametersJSONObject =
			JSONObjectUtils.getJSONObject(
				requestParametersJSONObject, "custom_request_parameters");

		for (String key : customRequestParametersJSONObject.keySet()) {
			biConsumer.accept(
				key,
				JSONObjectUtils.getStringArray(
					customRequestParametersJSONObject, key));
		}
	}

	public static URI[] getResources(JSONObject requestParametersJSONObject)
		throws ParseException {

		if (!requestParametersJSONObject.containsKey("resource")) {
			return new URI[0];
		}

		List<String> resourceList = JSONObjectUtils.getStringList(
			requestParametersJSONObject, "resource");

		URI[] resourceURIs = new URI[resourceList.size()];

		for (int i = 0; i < resourceList.size(); ++i) {
			resourceURIs[i] = URI.create(resourceList.get(i));
		}

		return resourceURIs;
	}

	public static ResponseType getResponseType(
			JSONObject requestParametersJSONObject)
		throws ParseException {

		return ResponseType.parse(
			JSONObjectUtils.getString(
				requestParametersJSONObject, "response_type"));
	}

	public static Scope getScope(JSONObject requestParametersJSONObject)
		throws ParseException {

		return Scope.parse(
			JSONObjectUtils.getString(requestParametersJSONObject, "scope"));
	}

}