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

package com.liferay.portal.osgi.web.wab.extender.internal.adapter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Raymond Augé
 */
public class FilterExceptionAdapter implements Filter {

	public FilterExceptionAdapter(Filter filter) {
		_filter = filter;
	}

	@Override
	public void destroy() {
		_filter.destroy();
	}

	@Override
	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		_filter.doFilter(servletRequest, servletResponse, filterChain);
	}

	public Exception getException() {
		return _exception;
	}

	@Override
	public void init(final FilterConfig filterConfig) {
		try {
			_filter.init(filterConfig);
		}
		catch (Exception e) {
			_exception = e;
		}
	}

	private Exception _exception;
	private final Filter _filter;

}