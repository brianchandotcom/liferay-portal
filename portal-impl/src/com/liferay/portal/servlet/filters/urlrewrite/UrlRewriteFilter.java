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

package com.liferay.portal.servlet.filters.urlrewrite;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author László Csontos
 */
public class UrlRewriteFilter extends BasePortalFilter {

	@Override
	public void destroy() {
		if (_urlRewriteFilter != null) {
			_urlRewriteFilter.destroy();
		}

		super.destroy();
	}

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_urlRewriteFilter =
			new org.tuckey.web.filters.urlrewrite.UrlRewriteFilter();

		try {
			_urlRewriteFilter.init(filterConfig);
		}
		catch (ServletException se) {
			_urlRewriteFilter = null;

			_log.error(se, se);
		}
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		if (_urlRewriteFilter != null) {
			_urlRewriteFilter.doFilter(
				httpServletRequest, httpServletResponse, filterChain);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UrlRewriteFilter.class);

	private org.tuckey.web.filters.urlrewrite.UrlRewriteFilter
		_urlRewriteFilter;

}