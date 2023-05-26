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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.InfrastructureUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

/**
 * @author Shuyang Zhou
 */
public class HealthCheckDataSourceFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		// curl -i http://localhost:8080/health_check/data_source

		HttpServletResponse httpServletResponse =
			(HttpServletResponse)servletResponse;

		DataSource dataSource = InfrastructureUtil.getDataSource();

		try (Connection connection = dataSource.getConnection()) {
			if (connection.isValid(0)) {
				_writeMessage(
					httpServletResponse, HttpServletResponse.SC_OK,
					"Data source is healthy.");
			}
			else {
				_writeMessage(
					httpServletResponse,
					HttpServletResponse.SC_SERVICE_UNAVAILABLE,
					"Data source is not healthy.");
			}
		}
		catch (SQLException sqlException) {
			_writeMessage(
				httpServletResponse,
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				sqlException.getMessage());
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	private void _writeMessage(
			HttpServletResponse httpServletResponse, int status, String message)
		throws IOException {

		httpServletResponse.setStatus(status);

		try (PrintWriter printWriter = httpServletResponse.getWriter()) {
			printWriter.println(message);
		}
	}

}