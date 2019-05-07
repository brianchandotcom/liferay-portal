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
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Minhchau Dang
 */
public class WebLogicIncludeFilter
	extends BasePortalFilter implements WrapHttpServletResponseFilter {

	@Override
	public HttpServletResponse getWrappedHttpServletResponse(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		WebLogicIncludeServletResponseFactory
			webLogicIncludeServletResponseFactory =
				_webLogicIncludeServletResponseFactory;

		if (webLogicIncludeServletResponseFactory != null) {
			return webLogicIncludeServletResponseFactory.create(
				httpServletResponse);
		}

		return httpServletResponse;
	}

	@Override
	public boolean isFilterEnabled() {
		if (_webLogicIncludeServletResponseFactory == null) {
			return false;
		}

		return true;
	}

	private static volatile WebLogicIncludeServletResponseFactory
		_webLogicIncludeServletResponseFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				WebLogicIncludeServletResponseFactory.class,
				WebLogicIncludeFilter.class,
				"_webLogicIncludeServletResponseFactory", false, true);

}