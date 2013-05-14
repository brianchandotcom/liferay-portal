/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.util.GetterUtil;

import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JSONRPCResponse implements JSONSerializable {

	public JSONRPCResponse(
		JSONRPCRequest jsonRPCRequest, Object result, Exception exception) {

		_id = jsonRPCRequest.getId();

		_jsonrpc = GetterUtil.getString(jsonRPCRequest.getJsonrpc());

		if (!_jsonrpc.equals("2.0")) {
			_error = new Error(-32700, "Invalid JSON RPC version " + _jsonrpc);
		}
		else if (exception == null) {
			_result = result;
		}
		else {
			int code = -32603;

			String message = null;

			if (exception instanceof InvocationTargetException) {
				code = -32602;

				Throwable cause = exception.getCause();

				message = cause.toString();
			}
			else {
				message = exception.toString();
			}

			if (message == null) {
				message = exception.toString();
			}

			_error = new Error(code, message);
		}
	}

	@Override
	public String toJSONString() {
		Map<String, Object> response = new HashMap<String, Object>();

		if (_error != null) {
			response.put("error", _error);
		}

		if (_id != null) {
			response.put("id", _id);
		}

		if (_jsonrpc != null) {
			response.put("jsonrpc", "2.0");
		}

		if (_result != null) {
			response.put("result", _result);
		}

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.exclude("*.class");
		jsonSerializer.include("error", "result");

		return jsonSerializer.serialize(response);
	}

	public class Error {

		public Error(int code, String message) {
			_code = code;
			_message = message;
		}

		public int getCode() {
			return _code;
		}

		public String getMessage() {
			return _message;
		}

		private int _code;
		private String _message;

	}

	private Error _error;
	private Integer _id;
	private String _jsonrpc;
	private Object _result;

}