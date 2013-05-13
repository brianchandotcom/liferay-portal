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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Shuyang Zhou
 */
public class DirectServletPathRegisterDispatcher implements RequestDispatcher {

	public DirectServletPathRegisterDispatcher(
		String path, RequestDispatcher requestDispatcher) {

		_path = path;
		_requestDispatcher = requestDispatcher;
	}

	@Override
	public void forward(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		servletRequest.setAttribute(WebKeys.SERVLET_PATH, _path);

		_requestDispatcher.forward(servletRequest, servletResponse);
	}

	@Override
	public void include(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		servletRequest.setAttribute(WebKeys.SERVLET_PATH, _path);

		_requestDispatcher.include(servletRequest, servletResponse);
	}

	private String _path;
	private RequestDispatcher _requestDispatcher;

}