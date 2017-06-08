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

package com.liferay.portal.template.freemarker.helper;

import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Chema Balsas
 */
public class FreeMarkerThemeHelperImpl implements FreeMarkerThemeHelper {

	public FreeMarkerThemeHelperImpl(
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response, Map<String, Object> contextObjects) {

		_servletContext = servletContext;
		_request = request;
		_response = response;
		_contextObjects = contextObjects;
	}

	@Override
	public void include(ServletContext servletContext, String path)
		throws Exception {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		requestDispatcher.include(_request, _response);
	}

	@Override
	public void include(String path) throws Exception {
		RequestDispatcher requestDispatcher =
			DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				_servletContext, path);

		requestDispatcher.include(_request, _response);
	}

	private final Map<String, Object> _contextObjects;
	private final HttpServletRequest _request;
	private final HttpServletResponse _response;
	private final ServletContext _servletContext;

}