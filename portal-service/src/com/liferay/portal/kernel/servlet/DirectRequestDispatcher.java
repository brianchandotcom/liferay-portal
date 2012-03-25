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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class DirectRequestDispatcher implements RequestDispatcher {

	public DirectRequestDispatcher(Servlet servlet, String queryString) {
		_servlet = servlet;

		String[] queryParamsArray = StringUtil.split(
			queryString, CharPool.AMPERSAND);

		if (queryParamsArray.length > 0) {
			_queryParams = new HashMap<String, String[]>();

			for (String queryParam : queryParamsArray) {
				String[] nameValuePair = StringUtil.split(
					queryParam, CharPool.EQUAL);

				String name = nameValuePair[0];
				String value = StringPool.BLANK;

				if (nameValuePair.length == 2) {
					value = nameValuePair[1];
				}

				String[] values = _queryParams.get(name);

				if (values == null) {
					_queryParams.put(name, new String[] {value});
				}
				else {
					String[] newValues = new String[values.length + 1];

					System.arraycopy(values, 0, newValues, 0, values.length);

					newValues[newValues.length - 1] = value;

					_queryParams.put(name, newValues);
				}
			}
		}
	}

	public void forward(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		if (_queryParams != null) {
			servletRequest = new DynamicServletRequest(
				(HttpServletRequest)servletRequest, _queryParams);
		}

		_servlet.service(servletRequest, servletResponse);
	}

	public void include(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		if (_queryParams != null) {
			servletRequest = new DynamicServletRequest(
				(HttpServletRequest)servletRequest, _queryParams);
		}

		_servlet.service(servletRequest, servletResponse);
	}

	private Map<String, String[]> _queryParams;
	private Servlet _servlet;

}