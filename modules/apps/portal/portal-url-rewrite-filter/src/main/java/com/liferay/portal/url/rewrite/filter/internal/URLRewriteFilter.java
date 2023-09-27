/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.rewrite.filter.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DelegateProxyFactory;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

/**
 * @author László Csontos
 */
@Component(
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

		ClassLoader classLoader = URLRewriteFilter.class.getClassLoader();

		try {
			_urlRewriteFilter.init(
				_delegateProxyFactory.newDelegateProxyInstance(
					classLoader, FilterConfig.class,
					new FilterConfigDelegate(
						_delegateProxyFactory.newDelegateProxyInstance(
							classLoader, ServletContext.class,
							new ServletContextDelegate(servletContext),
							servletContext)),
					filterConfig));
		}
		catch (ServletException servletException) {
			_urlRewriteFilter = null;

			_log.error(servletException);
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

	@Reference
	private DelegateProxyFactory _delegateProxyFactory;

	private UrlRewriteFilter _urlRewriteFilter;

}