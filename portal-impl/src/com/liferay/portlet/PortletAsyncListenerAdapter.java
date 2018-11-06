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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletAsyncContext;
import javax.portlet.PortletAsyncEvent;
import javax.portlet.PortletAsyncListener;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

/**
 * @author Dante Wang
 */
public class PortletAsyncListenerAdapter implements AsyncListener {

	public PortletAsyncListenerAdapter(
		PortletAsyncContext portletAsyncContext) {

		_portletAsyncContext = portletAsyncContext;
	}

	public void addListener(PortletAsyncListener portletAsyncListener)
		throws IllegalStateException {

		addListener(portletAsyncListener, null, null);
	}

	public void addListener(
			PortletAsyncListener portletAsyncListener,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IllegalStateException {

		if (_firedOnError) {
			try {
				portletAsyncListener.onError(
					new PortletAsyncEvent(
						_portletAsyncContext, resourceRequest,
						resourceResponse));
			}
			catch (IOException ioe) {
				_log.error("Unable to notify listener for onError", ioe);
			}
		}

		if (_firedOnTimeout) {
			try {
				portletAsyncListener.onTimeout(
					new PortletAsyncEvent(
						_portletAsyncContext, resourceRequest,
						resourceResponse));
			}
			catch (IOException ioe) {
				_log.error("Unable to notify listener for onTimeout", ioe);
			}
		}

		if (_firedOnComplete) {
			try {
				portletAsyncListener.onComplete(
					new PortletAsyncEvent(
						_portletAsyncContext, resourceRequest,
						resourceResponse));
			}
			catch (IOException ioe) {
				_log.error("Unable to notify listener for onComplete", ioe);
			}
		}

		_portletAsyncListenerAdapterEntries.add(
			new PortletAsyncListenerAdapterEntry(
				portletAsyncListener, resourceRequest, resourceResponse));
	}

	@Override
	public void onComplete(AsyncEvent asyncEvent) throws IOException {
		_firedOnComplete = true;

		for (PortletAsyncListenerAdapterEntry asyncListenerAdapterEntry :
				_portletAsyncListenerAdapterEntries) {

			PortletAsyncListener portletAsyncListener =
				asyncListenerAdapterEntry.getPortletAsyncListener();

			portletAsyncListener.onComplete(
				new PortletAsyncEvent(
					_portletAsyncContext,
					asyncListenerAdapterEntry.getResourceRequest(),
					asyncListenerAdapterEntry.getResourceResponse()));
		}
	}

	@Override
	public void onError(AsyncEvent asyncEvent) throws IOException {
		_firedOnError = true;

		Throwable t = asyncEvent.getThrowable();

		if (_portletAsyncListenerAdapterEntries.isEmpty()) {
			_log.error(t, t);
		}

		try {
			for (PortletAsyncListenerAdapterEntry asyncListenerAdapterEntry :
					_portletAsyncListenerAdapterEntries) {

				PortletAsyncListener portletAsyncListener =
					asyncListenerAdapterEntry.getPortletAsyncListener();

				portletAsyncListener.onError(
					new PortletAsyncEvent(
						_portletAsyncContext,
						asyncListenerAdapterEntry.getResourceRequest(),
						asyncListenerAdapterEntry.getResourceResponse(), t));
			}
		}
		finally {
			try {
				_portletAsyncContext.complete();
			}
			catch (IllegalStateException ise) {
			}
		}
	}

	@Override
	public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
		_firedOnComplete = false;
		_firedOnError = false;
		_firedOnTimeout = false;

		List<PortletAsyncListenerAdapterEntry>
			portletAsyncListenerAdapterEntries = new ArrayList<>(
				_portletAsyncListenerAdapterEntries);

		_portletAsyncListenerAdapterEntries.clear();

		try {
			for (PortletAsyncListenerAdapterEntry asyncListenerAdapterEntry :
					portletAsyncListenerAdapterEntries) {

				PortletAsyncListener portletAsyncListener =
					asyncListenerAdapterEntry.getPortletAsyncListener();

				portletAsyncListener.onStartAsync(
					new PortletAsyncEvent(
						_portletAsyncContext,
						asyncListenerAdapterEntry.getResourceRequest(),
						asyncListenerAdapterEntry.getResourceResponse()));
			}
		}
		finally {

			// Ensure the adapter is still registered when the AsyncContext is
			// reinitialized

			AsyncContext asyncContext = asyncEvent.getAsyncContext();

			asyncContext.addListener(this);
		}
	}

	@Override
	public void onTimeout(AsyncEvent asyncEvent) throws IOException {
		_firedOnTimeout = true;

		try {
			for (PortletAsyncListenerAdapterEntry asyncListenerAdapterEntry :
					_portletAsyncListenerAdapterEntries) {

				PortletAsyncListener portletAsyncListener =
					asyncListenerAdapterEntry.getPortletAsyncListener();

				portletAsyncListener.onTimeout(
					new PortletAsyncEvent(
						_portletAsyncContext,
						asyncListenerAdapterEntry.getResourceRequest(),
						asyncListenerAdapterEntry.getResourceResponse()));
			}
		}
		finally {
			try {
				_portletAsyncContext.complete();
			}
			catch (IllegalStateException ise) {
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletAsyncListenerAdapter.class);

	private boolean _firedOnComplete;
	private boolean _firedOnError;
	private boolean _firedOnTimeout;
	private final PortletAsyncContext _portletAsyncContext;
	private final List<PortletAsyncListenerAdapterEntry>
		_portletAsyncListenerAdapterEntries = new ArrayList<>();

	private static class PortletAsyncListenerAdapterEntry {

		public PortletAsyncListener getPortletAsyncListener() {
			return _portletAsyncListener;
		}

		public ResourceRequest getResourceRequest() {
			return _resourceRequest;
		}

		public ResourceResponse getResourceResponse() {
			return _resourceResponse;
		}

		private PortletAsyncListenerAdapterEntry(
			PortletAsyncListener portletAsyncListener,
			ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) {

			_portletAsyncListener = portletAsyncListener;
			_resourceRequest = resourceRequest;
			_resourceResponse = resourceResponse;
		}

		private final PortletAsyncListener _portletAsyncListener;
		private final ResourceRequest _resourceRequest;
		private final ResourceResponse _resourceResponse;

	}

}