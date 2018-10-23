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

import com.liferay.portal.kernel.util.PortletAsyncScopeManager;
import com.liferay.portal.kernel.util.PortletAsyncScopeManagerFactory;
import com.liferay.portlet.PortletAsyncListenerAdapter;

import java.io.IOException;

import javax.portlet.PortletAsyncContext;
import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.AsyncEvent;

/**
 * @author Neil Griffin
 */
public class PortletAsyncScopingListener extends PortletAsyncListenerAdapter {

	public PortletAsyncScopingListener(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		PortletConfig portletConfig, PortletAsyncContext portletAsyncContext,
		PortletAsyncScopeManagerFactory portletAsyncScopeManagerFactory) {

		super(portletAsyncContext);

		_resourceRequest = resourceRequest;
		_resourceResponse = resourceResponse;
		_portletConfig = portletConfig;
		_portletAsyncScopeManagerFactory = portletAsyncScopeManagerFactory;
	}

	@Override
	public void onComplete(AsyncEvent asyncEvent) throws IOException {
		_invokeCallback(CallbackType.ON_COMPLETE, asyncEvent);
	}

	@Override
	public void onError(AsyncEvent asyncEvent) throws IOException {
		_invokeCallback(CallbackType.ON_ERROR, asyncEvent);
	}

	@Override
	public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
		_invokeCallback(CallbackType.ON_START_ASYNC, asyncEvent);
	}

	@Override
	public void onTimeout(AsyncEvent asyncEvent) throws IOException {
		_invokeCallback(CallbackType.ON_TIMEOUT, asyncEvent);
	}

	private void _invokeCallback(
			CallbackType callbackType, AsyncEvent asyncEvent)
		throws IOException {

		PortletAsyncScopeManager portletAsyncScopeManager = null;

		if (_portletAsyncScopeManagerFactory != null) {
			portletAsyncScopeManager =
				_portletAsyncScopeManagerFactory.getPortletAsyncScopeManager(
					_resourceRequest, _resourceResponse, _portletConfig);

			portletAsyncScopeManager.activateScopeContexts();
		}

		if (callbackType == CallbackType.ON_COMPLETE) {
			super.onComplete(asyncEvent);
		}
		else if (callbackType == CallbackType.ON_ERROR) {
			super.onError(asyncEvent);
		}
		else if (callbackType == CallbackType.ON_START_ASYNC) {
			super.onStartAsync(asyncEvent);
		}
		else if (callbackType == CallbackType.ON_TIMEOUT) {
			super.onTimeout(asyncEvent);
		}

		if (portletAsyncScopeManager != null) {
			portletAsyncScopeManager.deactivateScopeContexts();
		}
	}

	private final PortletAsyncScopeManagerFactory
		_portletAsyncScopeManagerFactory;
	private final PortletConfig _portletConfig;
	private final ResourceRequest _resourceRequest;
	private final ResourceResponse _resourceResponse;

	private enum CallbackType {

		ON_COMPLETE, ON_ERROR, ON_START_ASYNC, ON_TIMEOUT

	}

}