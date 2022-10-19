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

import com.liferay.petra.function.transform.TransformUtil;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;

import java.net.URI;

import java.util.function.BiConsumer;

import net.minidev.json.JSONObject;

/**
 * @author Arthur Chan
 */
public class OpenIdConnectRequestParametersUtil {

	public static void consumeCustomRequestParameters(
			BiConsumer<String, String[]> biConsumer,
			JSONObject requestParametersJSONObject)
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

	public static URI[] getResourceURIs(JSONObject requestParametersJSONObject)
		throws ParseException {

		if (!requestParametersJSONObject.containsKey("resource")) {
			return new URI[0];
		}

		return TransformUtil.transformToArray(
			JSONObjectUtils.getStringList(
				requestParametersJSONObject, "resource"),
			resource -> URI.create(resource), URI.class);
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