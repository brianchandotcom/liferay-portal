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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 * @author Eduardo Lundgren
 */
public abstract class BaseFilter implements LiferayFilter {

	@Override
	public void destroy() {
		LiferayFilterTracker.removeLiferayFilter(this);
	}

	@Override
	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		try {
			processFilter(
				(HttpServletRequest)servletRequest,
				(HttpServletResponse)servletResponse, filterChain);
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (ServletException se) {
			throw se;
		}
		catch (Exception e) {
			Log log = getLog();

			log.error(e, e);
		}
	}

	public FilterConfig getFilterConfig() {
		return _filterConfig;
	}

	@Override
	public void init(FilterConfig filterConfig) {
		_filterConfig = filterConfig;

		LiferayFilterTracker.addLiferayFilter(this);
	}

	@Override
	public boolean isFilterEnabled() {
		return _filterEnabled;
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		return _filterEnabled;
	}

	@Override
	public void setFilterEnabled(boolean filterEnabled) {
		_filterEnabled = filterEnabled;
	}

	protected abstract Log getLog();

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		Class<?> clazz = getClass();

		processFilter(clazz.getName(), request, response, filterChain);
	}

	protected void processFilter(
			String logName, HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
		throws Exception {

		long startTime = 0;

		String threadName = null;
		String depther = null;
		String path = null;

		Log log = getLog();

		if (log.isDebugEnabled()) {
			startTime = System.currentTimeMillis();

			Thread currentThread = Thread.currentThread();

			threadName = currentThread.getName();

			depther = (String)request.getAttribute(_DEPTHER);

			if (depther == null) {
				depther = StringPool.BLANK;
			}
			else {
				depther += StringPool.EQUAL;
			}

			request.setAttribute(_DEPTHER, depther);

			path = request.getRequestURI();

			log.debug(
				"[" + threadName + "]" + depther + "> " + logName + " " + path);
		}

		filterChain.doFilter(request, response);

		if (!log.isDebugEnabled()) {
			return;
		}

		long endTime = System.currentTimeMillis();

		depther = (String)request.getAttribute(_DEPTHER);

		if (depther == null) {
			return;
		}

		log.debug(
			"[" + threadName + "]" + depther + "< " +
				logName + " " + path + " " + (endTime - startTime) + " ms");

		if (depther.length() > 0) {
			depther = depther.substring(1);
		}

		request.setAttribute(_DEPTHER, depther);
	}

	private static final String _DEPTHER = "DEPTHER";

	private FilterConfig _filterConfig;
	private boolean _filterEnabled = true;

}