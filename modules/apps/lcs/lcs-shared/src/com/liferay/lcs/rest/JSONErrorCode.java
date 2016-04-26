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

package com.liferay.lcs.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.liferay.jsonwebserviceclient.JSONWebServiceInvocationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mladen Cikara
 */
public enum JSONErrorCode {

	UNDEFINED(0), NO_SUCH_LCS_SUBSCRIPTION_ENTRY(1);

	public static JSONErrorCode getJSONErrorCode(
		JSONWebServiceInvocationException jsonwsie) {

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.configure(
				JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			objectMapper.configure(
				JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

			ObjectNode objectNode = objectMapper.readValue(
				jsonwsie.getMessage(), ObjectNode.class);

			JsonNode errorCodeJsonNode = objectNode.get("errorCode");

			if (errorCodeJsonNode == null) {
				return UNDEFINED;
			}

			return toJSONErrorCode(errorCodeJsonNode.asInt());
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);

			return UNDEFINED;
		}
	}

	public static JSONErrorCode toJSONErrorCode(int errorCode) {
		if (errorCode == NO_SUCH_LCS_SUBSCRIPTION_ENTRY.getErrorCode()) {
			return NO_SUCH_LCS_SUBSCRIPTION_ENTRY;
		}
		else {
			return UNDEFINED;
		}
	}

	public int getErrorCode() {
		return _errorCode;
	}

	private JSONErrorCode(int errorCode) {
		_errorCode = errorCode;
	}

	private static Logger _log = LoggerFactory.getLogger(JSONErrorCode.class);

	private int _errorCode;

}