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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.portlet.async.PortletAsyncListenerFactory;
import com.liferay.portal.kernel.portlet.async.PortletAsyncScopeManager;
import com.liferay.portal.kernel.portlet.async.PortletAsyncScopeManagerFactory;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portlet.AsyncPortletServletRequest;
import com.liferay.portlet.PortletAsyncListenerAdapter;

import java.util.function.Supplier;

import javax.portlet.PortletAsyncContext;
import javax.portlet.PortletAsyncListener;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Neil Griffin
 * @author Dante Wang
 * @author Leon Chi
 */
public class PortletAsyncContextImpl implements PortletAsyncContext {

	@Override
	public void addListener(PortletAsyncListener portletAsyncListener)
		throws IllegalStateException {

		addListener(portletAsyncListener, null, null);
	}

	@Override
	public void addListener(
			PortletAsyncListener portletAsyncListener,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IllegalStateException {

		if (!_resourceRequest.isAsyncStarted() || _returnedToContainer) {
			throw new IllegalStateException();
		}

		_portletAsyncListenerAdapter.addListener(
			portletAsyncListener, resourceRequest, resourceResponse);
	}

	@Override
	public void complete() throws IllegalStateException {
		_asyncContext.complete();

		_calledComplete = true;
	}

	@Override
	public <T extends PortletAsyncListener> T createPortletAsyncListener(
			Class<T> listenerClass)
		throws PortletException {

		if ((listenerClass == null) ||
			!PortletAsyncListener.class.isAssignableFrom(listenerClass)) {

			throw new PortletException(
				"listenerClass is not of type PortletAsyncListener");
		}

		try {
			listenerClass.getConstructor();
		}
		catch (NoSuchMethodException nsme) {
			throw new PortletException(
				listenerClass.getName() +
					" does not have a zero-arg constructor",
				nsme);
		}

		PortletAsyncListenerFactory portletAsyncListenerFactory =
			_portletAsyncListenerFactory;

		if (portletAsyncListenerFactory == null) {
			try {
				return listenerClass.newInstance();
			}
			catch (Throwable e) {
				throw new PortletException(e);
			}
		}

		T portletAsyncListener = null;

		try {
			portletAsyncListener =
				portletAsyncListenerFactory.getPortletAsyncListener(
					listenerClass);
		}
		catch (Exception e) {
			throw new PortletException(
				"Unable to create an instance of " + listenerClass.getName(),
				e);
		}

		if (portletAsyncListener == null) {
			throw new PortletException(
				"Unable to create an instance of " + listenerClass.getName());
		}

		return portletAsyncListener;
	}

	@Override
	public void dispatch() throws IllegalStateException {
		if (!_resourceRequest.isAsyncStarted() || _calledComplete ||
			_calledDispatch) {

			throw new IllegalStateException();
		}

		HttpServletRequest originalHttpServletRequest =
			(HttpServletRequest)_getOriginalServletRequest();

		String path = StringBundler.concat(
			originalHttpServletRequest.getRequestURI(), "?p_p_async=1&",
			originalHttpServletRequest.getQueryString());

		ServletContext servletContext =
			originalHttpServletRequest.getServletContext();

		_asyncPortletServletRequest.update(
			servletContext.getContextPath(), path);

		_asyncContext.dispatch(servletContext, path);

		_calledDispatch = true;
	}

	@Override
	public void dispatch(String path) throws IllegalStateException {
		if (!_resourceRequest.isAsyncStarted() || _calledComplete ||
			_calledDispatch) {

			throw new IllegalStateException();
		}

		ServletRequest originalServletRequest = _getOriginalServletRequest();

		ServletContext servletContext =
			originalServletRequest.getServletContext();

		String contextPath = _resourceRequest.getContextPath();

		path = contextPath.concat(path);

		_asyncPortletServletRequest.update(
			servletContext.getContextPath(), path);

		_asyncContext.dispatch(servletContext, path);

		_calledDispatch = true;
	}

	@Override
	public ResourceRequest getResourceRequest() throws IllegalStateException {
		if (_calledComplete ||
			(_calledDispatch && !_resourceRequest.isAsyncStarted())) {

			throw new IllegalStateException();
		}

		return _resourceRequest;
	}

	@Override
	public ResourceResponse getResourceResponse() throws IllegalStateException {
		if (_calledComplete ||
			(_calledDispatch && !_resourceRequest.isAsyncStarted())) {

			throw new IllegalStateException();
		}

		return _resourceResponse;
	}

	@Override
	public long getTimeout() {
		return _asyncContext.getTimeout();
	}

	@Override
	public boolean hasOriginalRequestAndResponse() {
		return _hasOriginalRequestAndResponse;
	}

	public boolean isCalledDispatch() {
		return _calledDispatch;
	}

	public void setReturnedToContainer() {
		_returnedToContainer = true;
	}

	@Override
	public void setTimeout(long timeout) {
		_asyncContext.setTimeout(timeout);
	}

	@Override
	public void start(Runnable runnable) throws IllegalStateException {
		_asyncContext.start(
			new PortletAsyncScopingRunnable(
				runnable, _portletAsyncListenerAdapter,
				_getPortletAsyncScopeManagerSupplier(
					_resourceRequest, _resourceResponse, _portletConfig)));
	}

	protected void initialize(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		PortletConfig portletConfig, AsyncContext asyncContext,
		boolean hasOriginalRequestAndResponse) {

		_resourceRequest = resourceRequest;
		_resourceResponse = resourceResponse;
		_portletConfig = portletConfig;
		_asyncContext = asyncContext;
		_hasOriginalRequestAndResponse = hasOriginalRequestAndResponse;

		_calledDispatch = false;
		_calledComplete = false;
		_returnedToContainer = false;

		Supplier<PortletAsyncScopeManager> portletAsyncScopeManagerSupplier =
			_getPortletAsyncScopeManagerSupplier(
				resourceRequest, resourceResponse, portletConfig);

		PortletAsyncScopeManager portletAsyncScopeManager =
			portletAsyncScopeManagerSupplier.get();

		portletAsyncScopeManager.activateScopeContexts();

		if (_portletAsyncListenerAdapter == null) {
			_portletAsyncListenerAdapter = new PortletAsyncScopingListener(
				this, portletAsyncScopeManagerSupplier);

			_asyncContext.addListener(_portletAsyncListenerAdapter);
		}

		if (_asyncPortletServletRequest == null) {
			_asyncPortletServletRequest =
				(AsyncPortletServletRequest)_asyncContext.getRequest();
		}
	}

	private static Supplier<PortletAsyncScopeManager>
		_getPortletAsyncScopeManagerSupplier(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			PortletConfig portletConfig) {

		PortletAsyncScopeManagerFactory portletAsyncScopeManagerFactory =
			_portletAsyncScopeManagerFactory;

		if (portletAsyncScopeManagerFactory == null) {
			portletAsyncScopeManagerFactory =
				_dummyPortletAsyncScopeManagerFactory;
		}

		PortletAsyncScopeManagerFactory finalPortletAsyncScopeManagerFactory =
			portletAsyncScopeManagerFactory;

		return () ->
			finalPortletAsyncScopeManagerFactory.getPortletAsyncScopeManager(
				resourceRequest, resourceResponse, portletConfig);
	}

	private ServletRequest _getOriginalServletRequest() {
		ServletRequest originalServletRequest = _asyncPortletServletRequest;

		while (originalServletRequest instanceof ServletRequestWrapper) {
			ServletRequestWrapper servletRequestWrapper =
				(ServletRequestWrapper)originalServletRequest;

			originalServletRequest = servletRequestWrapper.getRequest();
		}

		return originalServletRequest;
	}

	private static final PortletAsyncScopeManagerFactory
		_dummyPortletAsyncScopeManagerFactory =
			(resourceRequest, resourceResponse, portletConfig) ->
				new PortletAsyncScopeManager() {

					@Override
					public void activateScopeContexts() {
					}

					@Override
					public void deactivateScopeContexts() {
					}

				};

	private static volatile PortletAsyncListenerFactory
		_portletAsyncListenerFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				PortletAsyncListenerFactory.class,
				PortletAsyncContextImpl.class, "_portletAsyncListenerFactory",
				false, true);
	private static volatile PortletAsyncScopeManagerFactory
		_portletAsyncScopeManagerFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				PortletAsyncScopeManagerFactory.class,
				PortletAsyncContextImpl.class,
				"_portletAsyncScopeManagerFactory", false, true);

	private AsyncContext _asyncContext;
	private AsyncPortletServletRequest _asyncPortletServletRequest;
	private boolean _calledComplete;
	private boolean _calledDispatch;
	private boolean _hasOriginalRequestAndResponse;
	private PortletAsyncListenerAdapter _portletAsyncListenerAdapter;
	private PortletConfig _portletConfig;
	private ResourceRequest _resourceRequest;
	private ResourceResponse _resourceResponse;
	private boolean _returnedToContainer;

}