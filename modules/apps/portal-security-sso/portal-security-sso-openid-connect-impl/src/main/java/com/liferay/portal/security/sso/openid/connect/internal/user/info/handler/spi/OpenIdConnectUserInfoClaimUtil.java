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

package com.liferay.portal.security.sso.openid.connect.internal.user.info.handler.spi;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * @author Arthur Chan
 */
public class OpenIdConnectUserInfoClaimUtil {

	public static Object getClaim(
			String fieldName, JSONObject mapperJSONObject,
			JSONObject userInfoJSONObject)
		throws ParseException {

		String mappedClaim = JSONObjectUtils.getString(
			mapperJSONObject, fieldName);

		String[] mappedClaimChain = mappedClaim.split("->");

		Object claim = userInfoJSONObject.get(mappedClaimChain[0]);

		for (int i = 1; i < mappedClaimChain.length; ++i) {
			JSONObject claimJSONObject = (JSONObject)claim;

			claim = claimJSONObject.get(mappedClaimChain[i]);
		}

		return claim;
	}

	public static JSONArray getJSONArrayClaim(
		String fieldName, JSONObject mapperJSONObject,
		JSONObject userInfoJSONObject) {

		try {
			Object claim = getClaim(
				fieldName, mapperJSONObject, userInfoJSONObject);

			if ((claim == null) || (claim instanceof JSONArray)) {
				return (JSONArray)claim;
			}
		}
		catch (ParseException parseException) {
			if (_log.isInfoEnabled()) {
				_log.info(parseException);
			}
		}

		return null;
	}

	public static String getStringClaim(
		String fieldName, JSONObject mapperJSONObject,
		JSONObject userInfoJSONObject) {

		try {
			Object claim = getClaim(
				fieldName, mapperJSONObject, userInfoJSONObject);

			if ((claim != null) && !(claim instanceof String)) {
				throw new ParseException("Claim is not String");
			}

			return (String)claim;
		}
		catch (ParseException parseException) {
			if (_log.isInfoEnabled()) {
				_log.info(parseException);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectUserInfoClaimUtil.class);

}