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

package com.liferay.dynamic.data.mapping.data.provider;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderRequest {

	public DDMDataProviderRequest(
		String ddmDataProviderInstanceId,
		HttpServletRequest httpServletRequest) {

		_ddmDataProviderInstanceId = ddmDataProviderInstanceId;
		_httpServletRequest = httpServletRequest;
	}

	public DDMDataProviderContext getDDMDataProviderContext() {
		return _ddmDataProviderContext;
	}

	public String getDDMDataProviderInstanceId() {
		return _ddmDataProviderInstanceId;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	public String getParameter(String name) {
		return _parameters.get(name);
	}

	public Map<String, String> getParameters() {
		return _parameters;
	}

	public void queryString(Map<String, String> parameters) {
		_parameters.putAll(parameters);
	}

	public void queryString(String name, String value) {
		_parameters.put(name, value);
	}

	public void setDDMDataProviderContext(
		DDMDataProviderContext ddmDataProviderContext) {

		_ddmDataProviderContext = ddmDataProviderContext;
	}

	private DDMDataProviderContext _ddmDataProviderContext;
	private final String _ddmDataProviderInstanceId;
	private final HttpServletRequest _httpServletRequest;
	private final Map<String, String> _parameters = new HashMap<>();

}