/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.concurrent.ConcurrentLFUCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.NonSerializableObjectRequestWrapper;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class InvokerFilter extends BasePortalLifecycle implements Filter {

	@Override
	public void destroy() {
		portalDestroy();
	}

	@Override
	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)servletRequest;

		String uri = getURI(request);

		request = handleNonSerializableRequest(request);

		request.setAttribute(WebKeys.INVOKER_FILTER_URI, uri);

		try {
			InvokerFilterChain invokerFilterChain = getInvokerFilterChain(
				request, uri, filterChain);

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			invokerFilterChain.setContextClassLoader(contextClassLoader);

			invokerFilterChain.doFilter(request, servletResponse);
		}
		finally {
			request.removeAttribute(WebKeys.INVOKER_FILTER_URI);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		_filterConfig = filterConfig;

		ServletContext servletContext = _filterConfig.getServletContext();

		_contextPath = ContextPathUtil.getContextPath(servletContext);

		boolean registerPortalLifecycle = GetterUtil.getBoolean(
			_filterConfig.getInitParameter("register-portal-lifecycle"), true);

		if (registerPortalLifecycle) {
			registerPortalLifecycle();
		}
		else {
			try {
				doPortalInit();
			}
			catch (Exception e) {
				_log.error(e, e);

				throw new ServletException(e);
			}
		}
	}

	protected void clearFilterChainsCache() {
		if (_filterChains != null) {
			_filterChains.clear();
		}
	}

	@Override
	protected void doPortalDestroy() {
		ServletContext servletContext = _filterConfig.getServletContext();

		InvokerFilterHelper invokerFilterHelper =
			(InvokerFilterHelper)servletContext.getAttribute(
				InvokerFilterHelper.class.getName());

		if (invokerFilterHelper != null) {
			servletContext.removeAttribute(InvokerFilterHelper.class.getName());

			invokerFilterHelper.destroy();
		}
	}

	@Override
	protected void doPortalInit() throws Exception {
		_invokerFilterChainSize = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INVOKER_FILTER_CHAIN_SIZE));

		if (_invokerFilterChainSize > 0) {
			_filterChains = new ConcurrentLFUCache<String, InvokerFilterChain>(
				_invokerFilterChainSize);
		}

		ServletContext servletContext = _filterConfig.getServletContext();

		InvokerFilterHelper invokerFilterHelper =
			(InvokerFilterHelper)servletContext.getAttribute(
				InvokerFilterHelper.class.getName());

		if (invokerFilterHelper == null) {
			invokerFilterHelper = new InvokerFilterHelper();

			servletContext.setAttribute(
				InvokerFilterHelper.class.getName(), invokerFilterHelper);

			invokerFilterHelper.readLiferayFilterWebXML(
				servletContext, "/WEB-INF/liferay-web.xml");
		}

		_invokerFilterHelper = invokerFilterHelper;

		_invokerFilterHelper.addInvokerFilter(this);

		String dispatcher = GetterUtil.getString(
			_filterConfig.getInitParameter("dispatcher"));

		if (dispatcher.equals("ERROR")) {
			_dispatcher = Dispatcher.ERROR;
		}
		else if (dispatcher.equals("FORWARD")) {
			_dispatcher = Dispatcher.FORWARD;
		}
		else if (dispatcher.equals("INCLUDE")) {
			_dispatcher = Dispatcher.INCLUDE;
		}
		else if (dispatcher.equals("REQUEST")) {
			_dispatcher = Dispatcher.REQUEST;
		}
		else {
			throw new IllegalArgumentException(
				"Invalid dispatcher " + dispatcher);
		}
	}

	protected InvokerFilterChain getInvokerFilterChain(
		HttpServletRequest request, String uri, FilterChain filterChain) {

		if (_filterChains == null) {
			return _invokerFilterHelper.createInvokerFilterChain(
				request, _dispatcher, uri, filterChain);
		}

		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(
				InvokerFilter.class.getName());

		String key = String.valueOf(cacheKeyGenerator.getCacheKey(uri));

		InvokerFilterChain invokerFilterChain = _filterChains.get(key);

		if (invokerFilterChain == null) {
			invokerFilterChain = _invokerFilterHelper.createInvokerFilterChain(
				request, _dispatcher, uri, filterChain);

			_filterChains.put(key, invokerFilterChain);
		}

		return invokerFilterChain.clone(filterChain);
	}

	protected String getURI(HttpServletRequest request) {
		String uri = null;

		if (_dispatcher == Dispatcher.ERROR) {
			uri = (String)request.getAttribute(
				JavaConstants.JAVAX_SERVLET_ERROR_REQUEST_URI);
		}
		else if (_dispatcher == Dispatcher.INCLUDE) {
			uri = (String)request.getAttribute(
				JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI);
		}
		else {
			uri = request.getRequestURI();
		}

		if (Validator.isNotNull(_contextPath) &&
			!_contextPath.equals(StringPool.SLASH) &&
			uri.startsWith(_contextPath)) {

			uri = uri.substring(_contextPath.length());
		}

		return uri;
	}

	protected HttpServletRequest handleNonSerializableRequest(
		HttpServletRequest request) {

		if (ServerDetector.isWebLogic()) {
			if (!NonSerializableObjectRequestWrapper.isWrapped(request)) {
				request = new NonSerializableObjectRequestWrapper(request);
			}
		}

		return request;
	}

	private static Log _log = LogFactoryUtil.getLog(InvokerFilter.class);

	private String _contextPath;
	private Dispatcher _dispatcher;
	private ConcurrentLFUCache<String, InvokerFilterChain> _filterChains;
	private FilterConfig _filterConfig;
	private int _invokerFilterChainSize;
	private InvokerFilterHelper _invokerFilterHelper;

}