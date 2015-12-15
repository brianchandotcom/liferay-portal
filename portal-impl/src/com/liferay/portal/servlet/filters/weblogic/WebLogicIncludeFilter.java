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

package com.liferay.portal.servlet.filters.weblogic;

import com.liferay.portal.kernel.servlet.WrapHttpServletResponseFilter;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Minhchau Dang
 */
public class WebLogicIncludeFilter
	extends BasePortalFilter implements WrapHttpServletResponseFilter {

	public WebLogicIncludeFilter() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			WebLogicIncludeServletResponseFactory.class,
			new WebLogicIncludeServletResponseFactoryTrackerCustomizer());

		_serviceTracker.open();
	}

	@Override
	public HttpServletResponse getWrappedHttpServletResponse(
		HttpServletRequest request, HttpServletResponse response) {

		WebLogicIncludeServletResponseFactory
			webLogicIncludeServletResponseFactory =
				_webLogicIncludeServletResponseFactory;

		if (webLogicIncludeServletResponseFactory != null) {
			return webLogicIncludeServletResponseFactory.create(response);
		}

		return response;
	}

	@Override
	public boolean isFilterEnabled() {
		return _webLogicIncludeServletResponseFactory != null;
	}

	private final ServiceTracker
		<WebLogicIncludeServletResponseFactory,
			WebLogicIncludeServletResponseFactory> _serviceTracker;
	private volatile WebLogicIncludeServletResponseFactory
		_webLogicIncludeServletResponseFactory;

	private class WebLogicIncludeServletResponseFactoryTrackerCustomizer
		implements ServiceTrackerCustomizer
			<WebLogicIncludeServletResponseFactory,
				WebLogicIncludeServletResponseFactory> {

		@Override
		public WebLogicIncludeServletResponseFactory addingService(
			ServiceReference<WebLogicIncludeServletResponseFactory>
				serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			WebLogicIncludeServletResponseFactory
				webLogicIncludeServletResponseFactory = registry.getService(
					serviceReference);

			_webLogicIncludeServletResponseFactory =
				webLogicIncludeServletResponseFactory;

			return webLogicIncludeServletResponseFactory;
		}

		@Override
		public void modifiedService(
			ServiceReference<WebLogicIncludeServletResponseFactory>
				serviceReference,
			WebLogicIncludeServletResponseFactory service) {

			removedService(serviceReference, service);
			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<WebLogicIncludeServletResponseFactory>
				serviceReference,
			WebLogicIncludeServletResponseFactory service) {

			_webLogicIncludeServletResponseFactory = null;
		}

	}

}