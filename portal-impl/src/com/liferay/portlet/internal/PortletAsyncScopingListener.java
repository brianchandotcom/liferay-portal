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
import com.liferay.portlet.PortletAsyncListenerAdapter;

import java.io.IOException;

import java.util.function.Supplier;

import javax.portlet.PortletAsyncContext;

import javax.servlet.AsyncEvent;

/**
 * @author Neil Griffin
 */
public class PortletAsyncScopingListener extends PortletAsyncListenerAdapter {

	public PortletAsyncScopingListener(
		PortletAsyncContext portletAsyncContext,
		Supplier<PortletAsyncScopeManager> portletAsyncScopeManagerSupplier) {

		super(portletAsyncContext);

		_portletAsyncScopeManagerSupplier = portletAsyncScopeManagerSupplier;
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

		PortletAsyncScopeManager portletAsyncScopeManager =
			_portletAsyncScopeManagerSupplier.get();

		portletAsyncScopeManager.activateScopeContexts();

		unsafeRunnable.run();

		portletAsyncScopeManager.deactivateScopeContexts();
	}

	private final Supplier<PortletAsyncScopeManager>
		_portletAsyncScopeManagerSupplier;

}