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

import com.liferay.portal.kernel.portlet.LiferayPortletAsyncContext;
import com.liferay.portlet.PortletAsyncListenerAdapter;

import javax.portlet.PortletAsyncListener;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncListener;

/**
 * @author Neil Griffin
 * @author Dante Wang
 * @author Leon Chi
 */
public class PortletAsyncContextImpl implements LiferayPortletAsyncContext {

	@Override
	public void addListener(AsyncListener asyncListener) {

		// TODO

		throw new UnsupportedOperationException();
	}

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

		// TODO

		throw new UnsupportedOperationException();
	}

	@Override
	public void dispatch() throws IllegalStateException {
		_asyncContext.dispatch();

		_calledDispatch = true;
	}

	@Override
	public void dispatch(String path) throws IllegalStateException {
		_asyncContext.dispatch(path);

		_calledDispatch = true;
	}

	@Override
	public void doStart() {

		// TODO

		throw new UnsupportedOperationException();
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

	@Override
	public boolean isCalledDispatch() {
		return _calledDispatch;
	}

	@Override
	public void removeListener(AsyncListener asyncListener) {

		// TODO

		throw new UnsupportedOperationException();
	}

	@Override
	public void reset(AsyncContext asyncContext) {
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
		_asyncContext.start(runnable);
	}

	protected void initialize(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		AsyncContext asyncContext, boolean hasOriginalRequestAndResponse) {

		_resourceRequest = resourceRequest;
		_resourceResponse = resourceResponse;
		_asyncContext = asyncContext;
		_hasOriginalRequestAndResponse = hasOriginalRequestAndResponse;

		_calledDispatch = false;
		_calledComplete = false;
		_returnedToContainer = false;

		if (_portletAsyncListenerAdapter == null) {
			_portletAsyncListenerAdapter = new PortletAsyncListenerAdapter(
				this);

			_asyncContext.addListener(_portletAsyncListenerAdapter);
		}
	}

	private AsyncContext _asyncContext;
	private boolean _calledComplete;
	private boolean _calledDispatch;
	private boolean _hasOriginalRequestAndResponse;
	private PortletAsyncListenerAdapter _portletAsyncListenerAdapter;
	private ResourceRequest _resourceRequest;
	private ResourceResponse _resourceResponse;
	private boolean _returnedToContainer;

}