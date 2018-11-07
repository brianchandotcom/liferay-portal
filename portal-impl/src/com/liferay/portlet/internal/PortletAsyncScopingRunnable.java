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

import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

/**
 * @author Neil Griffin
 */
public class PortletAsyncScopingRunnable implements Runnable {

	public PortletAsyncScopingRunnable(
		Runnable runnable, AsyncListener asyncListener,
		PortletAsyncScopeManager portletAsyncScopeManager) {

		_runnable = runnable;
		_asyncListener = asyncListener;
		_portletAsyncScopeManager = portletAsyncScopeManager;
	}

	@Override
	public void run() {
		_portletAsyncScopeManager.activateScopeContexts();

		try {
			_runnable.run();
		}
		catch (Throwable t) {
			try {
				_asyncListener.onError(new AsyncEvent(null, t));
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}
		finally {
			_portletAsyncScopeManager.deactivateScopeContexts(false);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletAsyncScopingRunnable.class);

	private final AsyncListener _asyncListener;
	private final PortletAsyncScopeManager _portletAsyncScopeManager;
	private final Runnable _runnable;

}