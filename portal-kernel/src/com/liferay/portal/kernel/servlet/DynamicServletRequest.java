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

package com.liferay.portal.kernel.servlet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Sampsa Sohlman
 */
public class DynamicServletRequest extends HttpServletRequestWrapper {

	public static final String DYNAMIC_QUERY_STRING = "DYNAMIC_QUERY_STRING";

	public static HttpServletRequest addQueryString(
		HttpServletRequest request, Map<String, String[]> parameterMap,
		String queryString) {

		return addQueryString(request, parameterMap, queryString, true);
	}

	public static HttpServletRequest addQueryString(
		HttpServletRequest request, Map<String, String[]> parameterMap,
		String queryString, boolean inherit) {

		String[] parameters = StringUtil.split(queryString, CharPool.AMPERSAND);

		if (parameters.length == 0) {
			return request;
		}

		parameterMap = new HashMap<>(parameterMap);

		for (String parameter : parameters) {
			String[] parameterParts = StringUtil.split(
				parameter, CharPool.EQUAL);

			String name = parameterParts[0];

			String value = StringPool.BLANK;

			if (parameterParts.length == 2) {
				value = parameterParts[1];
			}

			String[] values = parameterMap.get(name);

			if (values == null) {
				parameterMap.put(name, new String[] {value});
			}
			else {
				String[] newValues = new String[values.length + 1];

				System.arraycopy(values, 0, newValues, 0, values.length);

				newValues[newValues.length - 1] = value;

				parameterMap.put(name, newValues);
			}
		}

		request = new DynamicServletRequest(request, parameterMap, inherit);

		request.setAttribute(DYNAMIC_QUERY_STRING, queryString);

		return request;
	}

	public static HttpServletRequest addQueryString(
		HttpServletRequest request, String queryString) {

		return addQueryString(
			request, new HashMap<String, String[]>(), queryString, true);
	}

	public static HttpServletRequest addQueryString(
		HttpServletRequest request, String queryString, boolean inherit) {

		return addQueryString(
			request, new HashMap<String, String[]>(), queryString, inherit);
	}

	public DynamicServletRequest(HttpServletRequest request) {
		this(request, null, true);
	}

	public DynamicServletRequest(HttpServletRequest request, boolean inherit) {
		this(request, null, inherit);
	}

	public DynamicServletRequest(
		HttpServletRequest request, Map<String, String[]> params) {

		this(request, params, true);
	}

	public DynamicServletRequest(
		HttpServletRequest request, Map<String, String[]> params,
		boolean inherit) {

		super(request);

		_params = new HashMap<>();
		_inherit = inherit;

		if (params != null) {
			_params.putAll(params);
		}

		if (_inherit && (request instanceof DynamicServletRequest)) {
			DynamicServletRequest dynamicRequest =
				(DynamicServletRequest)request;

			dynamicRequest.injectInto(this);

			params = dynamicRequest.getDynamicParameterMap();

			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				String name = entry.getKey();
				String[] oldValues = entry.getValue();

				String[] curValues = _params.get(name);

				if (curValues == null) {
					_params.put(name, oldValues);
				}
				else {
					String[] newValues = ArrayUtil.append(oldValues, curValues);

					_params.put(name, newValues);
				}
			}
		}
	}

	public void appendParameter(String name, String value) {
		String[] values = _params.get(name);

		if (values == null) {
			values = new String[] {value};
		}
		else {
			String[] newValues = new String[values.length + 1];

			System.arraycopy(values, 0, newValues, 0, values.length);

			newValues[newValues.length - 1] = value;

			values = newValues;
		}

		_params.put(name, values);
	}

	public Map<String, String[]> getDynamicParameterMap() {
		return _params;
	}

	@Override
	public String getParameter(String name) {
		String[] values = _params.get(name);

		if (_inherit && (values == null)) {
			return super.getParameter(name);
		}

		if (ArrayUtil.isNotEmpty(values)) {
			return values[0];
		}

		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new HashMap<>();

		if (_inherit) {
			map.putAll(super.getParameterMap());
		}

		map.putAll(_params);

		return map;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		if (_params.isEmpty()) {
			if (_inherit) {
				return super.getParameterNames();
			}

			return Collections.emptyEnumeration();
		}

		Set<String> names = null;

		if (_inherit) {
			Enumeration<String> enu = super.getParameterNames();

			while (enu.hasMoreElements()) {
				if (names == null) {
					names = new LinkedHashSet<>();
				}

				names.add(enu.nextElement());
			}
		}

		if (names == null) {
			names = _params.keySet();
		}
		else {
			names.addAll(_params.keySet());
		}

		return Collections.enumeration(names);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = _params.get(name);

		if (_inherit && (values == null)) {
			return super.getParameterValues(name);
		}

		return values;
	}

	public void setParameter(String name, String value) {
		_params.put(name, new String[] {value});
	}

	public void setParameterValues(String name, String[] values) {
		_params.put(name, values);
	}

	protected void injectInto(DynamicServletRequest dynamicServletRequest) {
		dynamicServletRequest.setRequest(getRequest());
	}

	private final boolean _inherit;
	private final Map<String, String[]> _params;

}