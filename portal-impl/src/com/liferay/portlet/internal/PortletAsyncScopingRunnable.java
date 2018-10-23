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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortletAsyncScopeManager;
import com.liferay.portal.kernel.util.PortletAsyncScopeManagerFactory;
import com.liferay.portlet.PortletAsyncListenerAdapter;

import java.io.IOException;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.AsyncEvent;

/**
 * @author Neil Griffin
 */
public class PortletAsyncScopingRunnable implements Runnable {

	public PortletAsyncScopingRunnable(
		Runnable runnable, ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, PortletConfig portletConfig,
		PortletAsyncListenerAdapter portletAsyncListenerAdapter,
		PortletAsyncScopeManagerFactory portletAsyncScopeManagerFactory) {

		_runnable = runnable;
		_resourceRequest = resourceRequest;
		_resourceResponse = resourceResponse;
		_portletConfig = portletConfig;
		_portletAsyncListenerAdapter = portletAsyncListenerAdapter;
		_portletAsyncScopeManagerFactory = portletAsyncScopeManagerFactory;
	}

	@Override
	public void run() {
		PortletAsyncScopeManager portletAsyncScopeManager =
			_portletAsyncScopeManagerFactory.getPortletAsyncScopeManager(
				_resourceRequest, _resourceResponse, _portletConfig);

		if (portletAsyncScopeManager != null) {
			portletAsyncScopeManager.activateScopeContexts();
		}

		try {
			_runnable.run();
		}
		catch (Throwable t) {
			try {
				_portletAsyncListenerAdapter.onError(new AsyncEvent(null, t));
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		if (portletAsyncScopeManager != null) {
			portletAsyncScopeManager.deactivateScopeContexts();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletAsyncScopingRunnable.class);

	private final PortletAsyncListenerAdapter _portletAsyncListenerAdapter;
	private final PortletAsyncScopeManagerFactory
		_portletAsyncScopeManagerFactory;
	private final PortletConfig _portletConfig;
	private final ResourceRequest _resourceRequest;
	private final ResourceResponse _resourceResponse;
	private final Runnable _runnable;

}