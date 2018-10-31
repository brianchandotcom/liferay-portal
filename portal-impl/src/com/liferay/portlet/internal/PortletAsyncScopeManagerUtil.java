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

import com.liferay.portal.kernel.portlet.async.PortletAsyncScopeManager;
import com.liferay.portal.kernel.portlet.async.PortletAsyncScopeManagerFactory;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Neil Griffin
 */
public class PortletAsyncScopeManagerUtil {

	public static PortletAsyncScopeManager getPortletAsyncScopeManager(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		PortletConfig portletConfig) {

		PortletAsyncScopeManagerFactory portletAsyncScopeManagerFactory =
			_portletAsyncScopeManagerFactory;

		if (portletAsyncScopeManagerFactory == null) {
			portletAsyncScopeManagerFactory =
				_dummyPortletAsyncScopeManagerFactory;
		}

		return portletAsyncScopeManagerFactory.getPortletAsyncScopeManager(
			resourceRequest, resourceResponse, portletConfig);
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

					@Override
					public void setAsyncProcessingStarted() {
					}

				};

	private static volatile PortletAsyncScopeManagerFactory
		_portletAsyncScopeManagerFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				PortletAsyncScopeManagerFactory.class,
				PortletAsyncScopeManagerUtil.class,
				"_portletAsyncScopeManagerFactory", false, true);

}