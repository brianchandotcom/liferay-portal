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

package com.liferay.document.library.external.video.internal.servlet;

import com.liferay.document.library.external.video.internal.constants.DLExternalVideoConstants;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.document.library.external.video.internal.servlet.DLExternalVideoServlet",
		"osgi.http.whiteboard.servlet.pattern=/" + DLExternalVideoConstants.SERVLET_PATH + "/*",
		"servlet.init.httpMethods=GET,HEAD"
	},
	service = Servlet.class
)
public class DLExternalVideoServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/video.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.external.video)"
	)
	private ServletContext _servletContext;

}