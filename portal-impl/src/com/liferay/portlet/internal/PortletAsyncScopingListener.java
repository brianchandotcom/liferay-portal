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

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.portlet.async.PortletAsyncScopeManager;
import com.liferay.portal.kernel.portlet.async.PortletAsyncScopeManagerFactory;
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
		_invokeCallback(() -> super.onComplete(asyncEvent));
	}

	@Override
	public void onError(AsyncEvent asyncEvent) throws IOException {
		_invokeCallback(() -> super.onError(asyncEvent));
	}

	@Override
	public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
		_invokeCallback(() -> super.onStartAsync(asyncEvent));
	}

	@Override
	public void onTimeout(AsyncEvent asyncEvent) throws IOException {
		_invokeCallback(() -> super.onTimeout(asyncEvent));
	}

	private void _invokeCallback(UnsafeRunnable<IOException> unsafeRunnable)
		throws IOException {

		PortletAsyncScopeManager portletAsyncScopeManager = null;

		if (_portletAsyncScopeManagerFactory != null) {
			portletAsyncScopeManager =
				_portletAsyncScopeManagerFactory.getPortletAsyncScopeManager(
					_resourceRequest, _resourceResponse, _portletConfig);

			portletAsyncScopeManager.activateScopeContexts();
		}

		unsafeRunnable.run();

		if (portletAsyncScopeManager != null) {
			portletAsyncScopeManager.deactivateScopeContexts();
		}
	}

	private final PortletAsyncScopeManagerFactory
		_portletAsyncScopeManagerFactory;
	private final PortletConfig _portletConfig;
	private final ResourceRequest _resourceRequest;
	private final ResourceResponse _resourceResponse;

}