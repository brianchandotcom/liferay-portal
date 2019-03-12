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

package com.liferay.portal.remote.cors.internal.servlet.filters;

import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.remote.cors.internal.CORSSupport;

import java.io.IOException;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Carlos Sierra Andrés
 */
public class CORSServletFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public final void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)servletRequest;

		if (corsSupport.isCorsRequest(request::getHeader)) {
			try {
				processCORSRequest(
					request, (HttpServletResponse)servletResponse, filterChain);
			}
			catch (Exception e) {
				throw new ServletException(e);
			}
		}
		else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void processCORSRequest(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		if (StringUtil.equals(HttpMethods.OPTIONS, request.getMethod())) {
			if (corsSupport.isValidCorsPreflightRequest(request::getHeader)) {
				corsSupport.writeResponseHeaders(
					request::getHeader, response::setHeader);
			}

			return;
		}

		if (corsSupport.isValidCorsRequest(
				request.getMethod(), request::getHeader)) {

			corsSupport.writeResponseHeaders(
				request::getHeader, response::setHeader);
		}

		filterChain.doFilter(request, response);
	}

	public void setCorsHeaders(Map<String, String> corsHeaders) {
		corsSupport.setCorsHeaders(corsHeaders);
	}

	protected final CORSSupport corsSupport = new CORSSupport();

}