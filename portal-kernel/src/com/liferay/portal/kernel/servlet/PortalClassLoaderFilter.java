/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.servlet.filters.invoker.InvokerFilterChain;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.lang.reflect.InvocationHandler;

import java.util.function.Function;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalClassLoaderFilter
	extends BasePortalLifecycle implements LiferayFilter {

	@Override
	public void destroy() {
		portalDestroy();
	}

	@Override
	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			FilterChain contextClassLoaderFilterChain =
				_filterChainProxyProviderFunction.apply(
					new ClassLoaderBeanHandler(
						filterChain, contextClassLoader));

			InvokerFilterChain invokerFilterChain = new InvokerFilterChain(
				contextClassLoaderFilterChain);

			invokerFilterChain.setContextClassLoader(contextClassLoader);

			invokerFilterChain.addFilter(_filter);

			invokerFilterChain.doFilter(servletRequest, servletResponse);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
		_filterConfig = filterConfig;

		registerPortalLifecycle();
	}

	@Override
	public boolean isFilterEnabled() {
		if (_liferayFilter != null) {
			return _liferayFilter.isFilterEnabled();
		}

		return true;
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (_liferayFilter != null) {
			return _liferayFilter.isFilterEnabled(
				httpServletRequest, httpServletResponse);
		}

		return true;
	}

	@Override
	public void setFilterEnabled(boolean filterEnabled) {
		if (_liferayFilter != null) {
			_liferayFilter.setFilterEnabled(filterEnabled);
		}
	}

	@Override
	protected void doPortalDestroy() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			_filter.destroy();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	protected void doPortalInit() throws Exception {
		String filterClass = _filterConfig.getInitParameter("filter-class");

		if (filterClass.startsWith("com.liferay.filters.")) {
			filterClass = StringUtil.replace(
				filterClass, "com.liferay.filters.",
				"com.liferay.portal.servlet.filters.");
		}

		_filter = (Filter)InstanceFactory.newInstance(
			PortalClassLoaderUtil.getClassLoader(), filterClass);

		_filter.init(_filterConfig);

		if (_filter instanceof LiferayFilter) {
			_liferayFilter = (LiferayFilter)_filter;
		}
	}

	private static final Function<InvocationHandler, FilterChain>
		_filterChainProxyProviderFunction = ProxyUtil.getProxyProviderFunction(
			FilterChain.class);

	private Filter _filter;
	private FilterConfig _filterConfig;
	private LiferayFilter _liferayFilter;

}