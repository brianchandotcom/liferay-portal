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

package com.liferay.portal.jsonwebservice;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jodd.servlet.ServletUtil;

/**
 * @author Igor Spasic
 */
public class JSONRPCRequest {

	public static JSONRPCRequest detectJSONRPCRequest(
		HttpServletRequest request) {

		try {
			String requestBody = ServletUtil.readRequestBody(request);

			if (Validator.isNull(requestBody) ||
				!requestBody.startsWith(StringPool.OPEN_CURLY_BRACE) ||
				!requestBody.endsWith(StringPool.CLOSE_CURLY_BRACE)) {

				return null;
			}

			JSONDeserializer<HashMap<Object, Object>> jsonDeserializer =
				JSONFactoryUtil.createJSONDeserializer();

			jsonDeserializer.use(null, HashMap.class);
			jsonDeserializer.use("parameters", HashMap.class);

			HashMap<Object, Object> requestBodyMap =
				jsonDeserializer.deserialize(requestBody);

			JSONRPCRequest jsonrpcRequest = new JSONRPCRequest();

			Number id = (Number)requestBodyMap.get("id");

			if (id != null) {
				jsonrpcRequest._id = Integer.valueOf(id.intValue());
			}

			jsonrpcRequest._jsonrpc = (String)requestBodyMap.get("jsonrpc");
			jsonrpcRequest._method = (String)requestBodyMap.get("method");
			jsonrpcRequest._parameters = (Map<String, ?>)requestBodyMap.get(
				"params");

			if (Validator.isNull(jsonrpcRequest._method)) {
				return null;
			}

			return jsonrpcRequest;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON RPC request", e);
			}

			return null;
		}
	}

	public Integer getId() {
		return _id;
	}

	public String getJsonrpc() {
		return _jsonrpc;
	}

	public String getMethod() {
		return _method;
	}

	public String getParameter(String name) {
		Object value = _parameters.get(name);

		if (value != null) {
			return String.valueOf(value);
		}

		return null;
	}

	public Set<String> getParameterNames() {
		return _parameters.keySet();
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setJsonrpc(String jsonrpc) {
		_jsonrpc = jsonrpc;
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setParameters(Map<String, ?> parameters) {
		_parameters = parameters;
	}

	private static final Log _log = LogFactoryUtil.getLog(JSONRPCRequest.class);

	private Integer _id;
	private String _jsonrpc;
	private String _method;
	private Map<String, ?> _parameters;

}