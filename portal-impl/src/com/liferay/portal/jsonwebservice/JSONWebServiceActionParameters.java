/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import jodd.util.KeyValue;

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

	public List<KeyValue<String, Object>> getInnerParameters(String baseName) {
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

	private ServiceContext _mergeServiceContext(ServiceContext serviceContext) {
		_serviceContext.setAddGroupPermissions(
			serviceContext.isAddGroupPermissions());
		_serviceContext.setAddGuestPermissions(
			serviceContext.isAddGuestPermissions());

		if (serviceContext.getAssetCategoryIds() != null) {
			_serviceContext.setAssetCategoryIds(
				serviceContext.getAssetCategoryIds());
		}

		if (serviceContext.getAssetLinkEntryIds() != null) {
			_serviceContext.setAssetLinkEntryIds(
				serviceContext.getAssetLinkEntryIds());
		}

		if (serviceContext.getAssetTagNames() != null) {
			_serviceContext.setAssetTagNames(serviceContext.getAssetTagNames());
		}

		if (serviceContext.getAttributes() != null) {
			_serviceContext.setAttributes(serviceContext.getAttributes());
		}

		if (Validator.isNotNull(serviceContext.getCommand())) {
			_serviceContext.setCommand(serviceContext.getCommand());
		}

		if (serviceContext.getCompanyId() > 0) {
			_serviceContext.setCompanyId(serviceContext.getCompanyId());
		}

		if (serviceContext.getCreateDate() != null) {
			_serviceContext.setCreateDate(serviceContext.getCreateDate());
		}

		if (Validator.isNotNull(serviceContext.getCurrentURL())) {
			_serviceContext.setCurrentURL(serviceContext.getCurrentURL());
		}

		if (serviceContext.getExpandoBridgeAttributes() != null) {
			_serviceContext.setExpandoBridgeAttributes(
				serviceContext.getExpandoBridgeAttributes());
		}

		if (serviceContext.getGroupPermissions() != null) {
			_serviceContext.setGroupPermissions(
				serviceContext.getGroupPermissions());
		}

		if (serviceContext.getGuestPermissions() != null) {
			_serviceContext.setGuestPermissions(
				serviceContext.getGuestPermissions());
		}

		if (serviceContext.getHeaders() != null) {
			_serviceContext.setHeaders(serviceContext.getHeaders());
		}

		_serviceContext.setLanguageId(serviceContext.getLanguageId());

		if (Validator.isNotNull(serviceContext.getLayoutFullURL())) {
			_serviceContext.setLayoutFullURL(serviceContext.getLayoutFullURL());
		}

		if (Validator.isNotNull(serviceContext.getLayoutURL())) {
			_serviceContext.setLayoutURL(serviceContext.getLayoutURL());
		}

		if (serviceContext.getModifiedDate() != null) {
			_serviceContext.setModifiedDate(serviceContext.getModifiedDate());
		}

		if (Validator.isNotNull(serviceContext.getPathMain())) {
			_serviceContext.setPathMain(serviceContext.getPathMain());
		}

		if (serviceContext.getPlid() > 0) {
			_serviceContext.setPlid(serviceContext.getPlid());
		}

		if (Validator.isNotNull(serviceContext.getPortalURL())) {
			_serviceContext.setPortalURL(serviceContext.getPortalURL());
		}

		if (serviceContext.getPortletPreferencesIds() != null) {
			_serviceContext.setPortletPreferencesIds(
				serviceContext.getPortletPreferencesIds());
		}

		if (Validator.isNotNull(serviceContext.getRemoteAddr())) {
			_serviceContext.setRemoteAddr(serviceContext.getRemoteAddr());
		}

		if (Validator.isNotNull(serviceContext.getRemoteHost())) {
			_serviceContext.setRemoteHost(serviceContext.getRemoteHost());
		}

		if (serviceContext.getScopeGroupId() > 0) {
			_serviceContext.setScopeGroupId(serviceContext.getScopeGroupId());
		}

		_serviceContext.setSignedIn(serviceContext.isSignedIn());

		if (Validator.isNotNull(serviceContext.getUserDisplayURL())) {
			_serviceContext.setUserDisplayURL(
				serviceContext.getUserDisplayURL());
		}

		if (serviceContext.getUserId() > 0) {
			_serviceContext.setUserId(serviceContext.getUserId());
		}

		if (Validator.isNotNull(serviceContext.getUuid())) {
			_serviceContext.setUuid(serviceContext.getUuid());
		}

		if (serviceContext.getWorkflowAction() > 0) {
			_serviceContext.setWorkflowAction(
				serviceContext.getWorkflowAction());
		}

		return serviceContext;
	}

	private Map<String, List<KeyValue<String, Object>>> _innerParameters;
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
						new HashMap<String, List<KeyValue<String, Object>>>();
				}

				List<KeyValue<String, Object>> values = _innerParameters.get(
					baseName);

				if (values == null) {
					values = new ArrayList<KeyValue<String, Object>>();

					_innerParameters.put(baseName, values);
				}

				values.add(new KeyValue<String, Object>(innerName, value));

				return value;
			}

			if ((_serviceContext != null) && key.equals("serviceContext")) {
				if ((value != null) &&
					ServiceContext.class.isAssignableFrom(value.getClass())) {

					value = _mergeServiceContext((ServiceContext)value);
				}
				else {
					value = _serviceContext;
				}
			}

			return super.put(key, value);
		}

	};

	private Map<String, String> _parameterTypes;
	private ServiceContext _serviceContext;

}