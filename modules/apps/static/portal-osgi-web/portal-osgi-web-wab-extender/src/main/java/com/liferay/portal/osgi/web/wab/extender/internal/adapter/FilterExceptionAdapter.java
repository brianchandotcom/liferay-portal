/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void init(FilterConfig filterConfig) {
		try {
			_filter.init(filterConfig);
		}
		catch (Exception exception) {
			_exception = exception;
		}
	}

	private Exception _exception;
	private final Filter _filter;

}