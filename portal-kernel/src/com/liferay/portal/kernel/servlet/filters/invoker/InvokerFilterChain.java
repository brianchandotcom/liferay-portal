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

package com.liferay.portal.kernel.servlet.filters.invoker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.DirectCallFilter;
import com.liferay.portal.kernel.servlet.LiferayFilter;
import com.liferay.portal.kernel.servlet.TryFilter;
import com.liferay.portal.kernel.servlet.TryFinallyFilter;
import com.liferay.portal.kernel.servlet.WrapHttpServletRequestFilter;
import com.liferay.portal.kernel.servlet.WrapHttpServletResponseFilter;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class InvokerFilterChain implements FilterChain {

	public InvokerFilterChain(FilterChain filterChain) {
		_filterChain = filterChain;
	}

	public void addFilter(Filter filter) {
		if (_filters == null) {
			_filters = new ArrayList<>();
		}

		_filters.add(filter);
	}

	public InvokerFilterChain clone(FilterChain filterChain) {
		InvokerFilterChain invokerFilterChain = new InvokerFilterChain(
			filterChain);

		invokerFilterChain._filters = _filters;

		return invokerFilterChain;
	}

	@Override
	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		if (_filters != null) {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)servletRequest;
			HttpServletResponse httpServletResponse =
				(HttpServletResponse)servletResponse;

			while (_index < _filters.size()) {
				Filter filter = _filters.get(_index++);

				if (filter instanceof LiferayFilter) {
					LiferayFilter liferayFilter = (LiferayFilter)filter;

					if (!liferayFilter.isFilterEnabled() ||
						!liferayFilter.isFilterEnabled(
							httpServletRequest, httpServletResponse)) {

						if (_log.isDebugEnabled()) {
							_log.debug(
								"Skip disabled filter " + filter.getClass());
						}

						continue;
					}
				}

				if (filter instanceof DirectCallFilter) {
					try {
						processDirectCallFilter(
							filter, httpServletRequest, httpServletResponse);
					}
					catch (IOException ioe) {
						throw ioe;
					}
					catch (RuntimeException re) {
						throw re;
					}
					catch (ServletException se) {
						throw se;
					}
					catch (Exception e) {
						throw new ServletException(e);
					}
				}
				else {
					processDoFilter(
						filter, httpServletRequest, httpServletResponse);
				}

				return;
			}
		}

		_filterChain.doFilter(servletRequest, servletResponse);
	}

	public void setContextClassLoader(ClassLoader contextClassLoader) {
		_contextClassLoader = contextClassLoader;
	}

	protected void processDirectCallFilter(
			Filter filter, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (filter instanceof WrapHttpServletRequestFilter) {
			if (_log.isDebugEnabled()) {
				_log.debug("Wrap response with filter " + filter.getClass());
			}

			WrapHttpServletRequestFilter wrapHttpServletRequestFilter =
				(WrapHttpServletRequestFilter)filter;

			httpServletRequest =
				wrapHttpServletRequestFilter.getWrappedHttpServletRequest(
					httpServletRequest, httpServletResponse);
		}

		if (filter instanceof WrapHttpServletResponseFilter) {
			if (_log.isDebugEnabled()) {
				_log.debug("Wrap request with filter " + filter.getClass());
			}

			WrapHttpServletResponseFilter wrapHttpServletResponseFilter =
				(WrapHttpServletResponseFilter)filter;

			httpServletResponse =
				wrapHttpServletResponseFilter.getWrappedHttpServletResponse(
					httpServletRequest, httpServletResponse);
		}

		if (filter instanceof TryFinallyFilter) {
			TryFinallyFilter tryFinallyFilter = (TryFinallyFilter)filter;

			Object object = null;

			try {
				if (_log.isDebugEnabled()) {
					_log.debug("Invoke try for filter " + filter.getClass());
				}

				object = tryFinallyFilter.doFilterTry(
					httpServletRequest, httpServletResponse);

				doFilter(httpServletRequest, httpServletResponse);
			}
			finally {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Invoke finally for filter " + filter.getClass());
				}

				tryFinallyFilter.doFilterFinally(
					httpServletRequest, httpServletResponse, object);
			}
		}
		else if (filter instanceof TryFilter) {
			TryFilter tryFilter = (TryFilter)filter;

			if (_log.isDebugEnabled()) {
				_log.debug("Invoke try for filter " + filter.getClass());
			}

			tryFilter.doFilterTry(httpServletRequest, httpServletResponse);

			doFilter(httpServletRequest, httpServletResponse);
		}
		else {
			doFilter(httpServletRequest, httpServletResponse);
		}
	}

	protected void processDoFilter(
			Filter filter, ServletRequest servletRequest,
			ServletResponse servletResponse)
		throws IOException, ServletException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(_contextClassLoader);

		try {
			filter.doFilter(servletRequest, servletResponse, this);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InvokerFilterChain.class);

	private ClassLoader _contextClassLoader;
	private final FilterChain _filterChain;
	private List<Filter> _filters;
	private int _index;

}