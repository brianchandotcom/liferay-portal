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

import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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
		if (_innerParameters == null) {
			return null;
		}

		return _innerParameters.get(baseName);
	}

	public JSONRPCRequest getJSONRPCRequest() {
		return _jsonRPCRequest;
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

	public String getParameterTypeName(String name) {
		if (_parameterTypes == null) {
			return null;
		}

		return _parameterTypes.get(name);
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
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

	private void _collectFromJSONRPCRequest(JSONRPCRequest jsonRPCRequest) {
		if (jsonRPCRequest == null) {
			return;
		}

		Set<String> parameterNames = jsonRPCRequest.getParameterNames();

		for (String parameterName : parameterNames) {
			String value = jsonRPCRequest.getParameter(parameterName);

			parameterName = CamelCaseUtil.normalizeCamelCase(parameterName);

			_parameters.put(parameterName, value);
		}
	}

	private void _collectFromMap(Map<String, Object> parameterMap) {
		if (parameterMap == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
			String parameterName = entry.getKey();

			Object value = entry.getValue();

			_parameters.put(parameterName, value);
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
			String name = enu.nextElement();

			Object value = null;

			if ((uploadServletRequest != null) &&
				(uploadServletRequest.getFileName(name) != null)) {

				value = uploadServletRequest.getFile(name, true);
			}
			else {
				String[] parameterValues = request.getParameterValues(name);

				if (parameterValues.length == 1) {
					value = parameterValues[0];
				}
				else {
					value = parameterValues;
				}
			}

			name = CamelCaseUtil.normalizeCamelCase(name);

			_parameters.put(name, value);
		}
	}

	private Map<String, List<NameValue<String, Object>>> _innerParameters;
	private JSONRPCRequest _jsonRPCRequest;

	private Map<String, Object> _parameters = new HashMap<String, Object>() {

		@Override
		public Object put(String key, Object value) {
			if (key.startsWith(StringPool.DASH)) {
				key = key.substring(1);

				value = null;
			}
			else if (key.startsWith(StringPool.PLUS)) {
				key = key.substring(1);

				int pos = key.indexOf(CharPool.COLON);

				if (pos != -1) {
					value = key.substring(pos + 1);

					key = key.substring(0, pos);
				}

				if (Validator.isNotNull(value)) {
					if (_parameterTypes == null) {
						_parameterTypes = new HashMap<String, String>();
					}

					_parameterTypes.put(key, value.toString());
				}

				value = Void.TYPE;
			}

			int pos = key.indexOf(CharPool.PERIOD);

			if (pos != -1) {
				String baseName = key.substring(0, pos);

				String innerName = key.substring(pos + 1);

				if (_innerParameters == null) {
					_innerParameters =
						new HashMap<String, List<NameValue<String, Object>>>();
				}

				List<NameValue<String, Object>> values = _innerParameters.get(
					baseName);

				if (values == null) {
					values = new ArrayList<NameValue<String, Object>>();

					_innerParameters.put(baseName, values);
				}

				values.add(new NameValue<String, Object>(innerName, value));

				return value;
			}

			return super.put(key, value);
		}

	};

	private Map<String, String> _parameterTypes;
	private ServiceContext _serviceContext;

}