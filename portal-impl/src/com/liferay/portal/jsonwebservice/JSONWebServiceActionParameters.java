/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ActionParameters.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class JSONWebServiceActionParameters {

	public void collectAll(
		HttpServletRequest request, String pathParameters,
		JSONRPCRequest jsonRpcRequest) {

		_jsonRpcRequest = jsonRpcRequest;

		_addDefaultParameters();

		_collectDefaultsFromRequestAttributes(request);

		_collectFromPath(pathParameters);
		_collectFromRequestParameters(request);
		_collectFromJSONRPCRequest(jsonRpcRequest);
	}

	public JSONRPCRequest getJSONRPCRequest() {
		return _jsonRpcRequest;
	}

	public Object getParameter(String name) {
		return _parameters.get(name);
	}

	public String[] getParameterNames() {
		String[] names = new String[_parameters.size()];

		int i = 0;

		for (String key : _parameters.keySet()) {
			names[i] = key;

			i++;
		}

		return names;
	}

	private void _addDefaultParameters() {
		_parameters.put("serviceContext", Void.TYPE);
	}

	private void _collectDefaultsFromRequestAttributes(
		HttpServletRequest request) {

		Enumeration<String> enu = request.getAttributeNames();

		while (enu.hasMoreElements()) {
			String attributeName = enu.nextElement();

			Object value = request.getAttribute(attributeName);

			_parameters.put(attributeName, value);
		}
	}

	private void _collectFromJSONRPCRequest(JSONRPCRequest jsonRpcRequest) {
		if (jsonRpcRequest == null) {
			return;
		}

		Set<String> parameterNames = jsonRpcRequest.getParameterNames();

		for (String parameterName : parameterNames) {
			String value = jsonRpcRequest.getParameter(parameterName);

			_parameters.put(parameterName, value);
		}
	}

	private void _collectFromPath(String pathParameters) {
		if (pathParameters == null) {
			return;
		}

		if (pathParameters.startsWith(StringPool.SLASH)) {
			pathParameters = pathParameters.substring(1);
		}

		String[] pathParametersParts = StringUtil.split(
			pathParameters, CharPool.SLASH);

		int i = 0;

		while (i < pathParametersParts.length) {
			String name = pathParametersParts[i];

			if (name.length() == 0) {
				i++;

				continue;
			}

			String value = null;

			if (name.startsWith(StringPool.DASH)) {
				name = name.substring(1);
			}
			else {
				i++;

				value = pathParametersParts[i];
			}

			name = jodd.util.StringUtil.wordsToCamelCase(name, CharPool.DASH);

			_parameters.put(name, value);

			i++;
		}
	}

	private void _collectFromRequestParameters(HttpServletRequest request) {
		UploadServletRequest uploadServletRequest = null;

		if (request instanceof UploadServletRequest) {
			uploadServletRequest = (UploadServletRequest)request;
		}

		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String parameterName = enu.nextElement();

			Object value = null;

			if ((uploadServletRequest != null) &&
				!uploadServletRequest.isFormField(parameterName)) {

				value = uploadServletRequest.getFile(parameterName);
			}
			else {
				String[] parameterValues = request.getParameterValues(
					parameterName);

				if (parameterValues.length == 1) {
					value = parameterValues[0];
				}
				else {
					value = parameterValues;
				}
			}

			_parameters.put(parameterName, value);
		}
	}

	private JSONRPCRequest _jsonRpcRequest;
	private Map<String, Object> _parameters = new HashMap<String, Object>() {

		@Override
		public Object put(String key, Object value) {

			if (key.startsWith(StringPool.DASH)) {
				key = key.substring(1);

				value = null;
			}

			return super.put(key, value);
		}

	};

}