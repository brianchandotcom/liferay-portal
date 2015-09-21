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

import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jodd.util.NameValue;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionParameters {

	public void collectAll(
		HttpServletRequest request, String parameterPath,
		JSONRPCRequest jsonRPCRequest, Map<String, Object> parameterMap) {

		_jsonRPCRequest = jsonRPCRequest;

		try {
			_serviceContext = ServiceContextFactory.getInstance(request);
		}
		catch (Exception e) {
		}

		_addDefaultParameters();

		_collectDefaultsFromRequestAttributes(request);

		_collectFromPath(parameterPath);
		_collectFromRequestParameters(request);
		_collectFromJSONRPCRequest(jsonRPCRequest);
		_collectFromMap(parameterMap);
	}

	public List<NameValue<String, Object>> getInnerParameters(String baseName) {
		return _jsonWebServiceActionParameters.getInnerParameters(baseName);
	}

	public JSONRPCRequest getJSONRPCRequest() {
		return _jsonRPCRequest;
	}

	public Object getParameter(String name) {
		return _jsonWebServiceActionParameters.get(name);
	}

	public String[] getParameterNames() {
		String[] names = new String[_jsonWebServiceActionParameters.size()];

		int i = 0;

		for (String key : _jsonWebServiceActionParameters.keySet()) {
			names[i] = key;

			i++;
		}

		return names;
	}

	public String getParameterTypeName(String name) {
		return _jsonWebServiceActionParameters.getParameterTypeName(name);
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public boolean includeDefaultParameters() {
		return _jsonWebServiceActionParameters.includeDefaultParameters();
	}

	private void _addDefaultParameters() {
		_jsonWebServiceActionParameters.put("serviceContext", Void.TYPE);
	}

	private void _collectDefaultsFromRequestAttributes(
		HttpServletRequest request) {

		Enumeration<String> enu = request.getAttributeNames();

		while (enu.hasMoreElements()) {
			String attributeName = enu.nextElement();

			Object value = request.getAttribute(attributeName);

			_jsonWebServiceActionParameters.putDefaultParameter(
				attributeName, value);
		}
	}

	private void _collectFromJSONRPCRequest(JSONRPCRequest jsonRPCRequest) {
		if (jsonRPCRequest == null) {
			return;
		}

		Set<String> parameterNames = jsonRPCRequest.getParameterNames();

		for (String parameterName : parameterNames) {
			String value = jsonRPCRequest.getParameter(parameterName);

			parameterName = CamelCaseUtil.normalizeCamelCase(parameterName);

			_jsonWebServiceActionParameters.put(parameterName, value);
		}
	}

	private void _collectFromMap(Map<String, Object> parameterMap) {
		if (parameterMap == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
			String parameterName = entry.getKey();

			Object value = entry.getValue();

			_jsonWebServiceActionParameters.put(parameterName, value);
		}
	}

	private void _collectFromPath(String parameterPath) {
		if (parameterPath == null) {
			return;
		}

		if (parameterPath.startsWith(StringPool.SLASH)) {
			parameterPath = parameterPath.substring(1);
		}

		String[] parameterPathParts = StringUtil.split(
			parameterPath, CharPool.SLASH);

		int i = 0;

		while (i < parameterPathParts.length) {
			String name = parameterPathParts[i];

			if (name.length() == 0) {
				i++;

				continue;
			}

			String value = null;

			if (name.startsWith(StringPool.DASH)) {
				name = name.substring(1);
			}
			else if (!name.startsWith(StringPool.PLUS)) {
				i++;

				if (i >= parameterPathParts.length) {
					throw new IllegalArgumentException(
						"Missing value for parameter " + name);
				}

				value = parameterPathParts[i];
			}

			name = CamelCaseUtil.toCamelCase(name);

			_jsonWebServiceActionParameters.put(name, value);

			i++;
		}
	}

	private void _collectFromRequestParameters(HttpServletRequest request) {
		UploadServletRequest uploadServletRequest = null;

		if (request instanceof UploadServletRequest) {
			uploadServletRequest = (UploadServletRequest)request;
		}

		List<String> parameterNames = Collections.list(
			request.getParameterNames());

		if (uploadServletRequest != null) {
			Map<String, FileItem[]> multipartParameterMap =
				uploadServletRequest.getMultipartParameterMap();

			parameterNames.addAll(multipartParameterMap.keySet());
		}

		for (String parameterName : parameterNames) {
			Object value = null;

			if ((uploadServletRequest != null) &&
				(uploadServletRequest.getFileName(parameterName) != null)) {

				value = uploadServletRequest.getFile(parameterName, true);
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

			parameterName = CamelCaseUtil.normalizeCamelCase(parameterName);

			_jsonWebServiceActionParameters.put(parameterName, value);
		}
	}

	private JSONRPCRequest _jsonRPCRequest;
	private final JSONWebServiceActionParametersMap
		_jsonWebServiceActionParameters =
			new JSONWebServiceActionParametersMap();
	private ServiceContext _serviceContext;

}