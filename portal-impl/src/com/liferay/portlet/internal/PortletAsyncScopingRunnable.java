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
import com.liferay.portal.kernel.portlet.async.PortletAsyncScopeManager;
import com.liferay.portlet.PortletAsyncListenerAdapter;

import java.io.IOException;

import java.util.function.Supplier;

import javax.servlet.AsyncEvent;

/**
 * @author Neil Griffin
 */
public class PortletAsyncScopingRunnable implements Runnable {

	public PortletAsyncScopingRunnable(
		Runnable runnable,
		PortletAsyncListenerAdapter portletAsyncListenerAdapter,
		Supplier<PortletAsyncScopeManager> portletAsyncScopeManagerSupplier) {

		_runnable = runnable;
		_portletAsyncListenerAdapter = portletAsyncListenerAdapter;
		_portletAsyncScopeManagerSupplier = portletAsyncScopeManagerSupplier;
	}

	@Override
	public void run() {
		PortletAsyncScopeManager portletAsyncScopeManager =
			_portletAsyncScopeManagerSupplier.get();

		portletAsyncScopeManager.activateScopeContexts();

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
		finally {
			portletAsyncScopeManager.deactivateScopeContexts();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletAsyncScopingRunnable.class);

	private final PortletAsyncListenerAdapter _portletAsyncListenerAdapter;
	private final Supplier<PortletAsyncScopeManager>
		_portletAsyncScopeManagerSupplier;
	private final Runnable _runnable;

}