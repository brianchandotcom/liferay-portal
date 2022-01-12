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

package com.liferay.portal.url.rewrite.filter.internal;

import com.liferay.portal.asm.ASMWrapperUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

/**
 * @author László Csontos
 */
@Component(
	immediate = true,
	property = {
		"before-filter=Session Id Filter", "dispatcher=ERROR",
		"dispatcher=FORWARD", "dispatcher=INCLUDE", "dispatcher=REQUEST",
		"init-param.logLevel=ERROR", "init-param.statusEnabled=false",
		"init-param.url-regex-ignore-pattern=(^/combo/)|(^/html/.+\\.(css|gif|html|ico|jpg|js|png)(\\?.*)?$)",
		"servlet-context-name=", "servlet-filter-name=URL Rewrite Filter",
		"url-pattern=/*"
	},
	service = Filter.class
)
public class URLRewriteFilter extends BasePortalFilter {

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

		_urlRewriteFilter = new UrlRewriteFilter();

		ServletContext servletContext = filterConfig.getServletContext();

		ClassLoader classLoader = AggregateClassLoader.getAggregateClassLoader(
			URLRewriteFilter.class.getClassLoader(),
			servletContext.getClassLoader());

		try {
			_urlRewriteFilter.init(
				ASMWrapperUtil.createASMWrapper(
					classLoader, FilterConfig.class,
					new FilterConfigDelegate(
						ASMWrapperUtil.createASMWrapper(
							classLoader, ServletContext.class,
							new ServletContextDelegate(servletContext),
							servletContext)),
					filterConfig));
		}
		catch (ServletException servletException) {
			_urlRewriteFilter = null;

			_log.error(servletException, servletException);
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
		URLRewriteFilter.class);

	private UrlRewriteFilter _urlRewriteFilter;

}