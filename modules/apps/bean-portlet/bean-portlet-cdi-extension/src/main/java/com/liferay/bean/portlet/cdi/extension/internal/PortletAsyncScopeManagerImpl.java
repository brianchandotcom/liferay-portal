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

package com.liferay.bean.portlet.cdi.extension.internal;

import com.liferay.bean.portlet.cdi.extension.internal.scope.ScopedBeanHolder;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortletAsyncScopeManager;

import java.io.Closeable;
import java.io.IOException;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ResourceRequestWrapper;

/**
 * @author Neil Griffin
 */
public class PortletAsyncScopeManagerImpl implements PortletAsyncScopeManager {

	public PortletAsyncScopeManagerImpl(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		PortletConfig portletConfig) {

		_resourceRequest = new AsyncResourceRequest(resourceRequest);
		_resourceResponse = resourceResponse;
		_portletConfig = portletConfig;
	}

	@Override
	public void activateScopeContexts() {
		if (_scopeContexts != null) {
			return;
		}

		ScopedBeanHolder scopedBeanHolder =
			ScopedBeanHolder.getCurrentInstance();

		if (scopedBeanHolder == null) {
			scopedBeanHolder = new ScopedBeanHolder(
				_resourceRequest, _resourceResponse, _portletConfig);
		}

		scopedBeanHolder.setPortletRequest(_resourceRequest);

		_scopeContexts = scopedBeanHolder.install();
	}

	@Override
	public void deactivateScopeContexts() {
		if (_scopeContexts == null) {
			return;
		}

		try {
			_scopeContexts.close();
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletAsyncScopeManagerImpl.class);

	private final PortletConfig _portletConfig;
	private final ResourceRequest _resourceRequest;
	private final ResourceResponse _resourceResponse;
	private Closeable _scopeContexts;

	private static class AsyncResourceRequest extends ResourceRequestWrapper {

		public AsyncResourceRequest(ResourceRequest resourceRequest) {
			super(resourceRequest);
		}

		@Override
		public void removeAttribute(String name) {
			if (_log.isDebugEnabled()) {
				_log.debug("Retaining @PortletRequestScoped bean " + name);
			}
		}

	}

}