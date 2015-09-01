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

import com.liferay.portal.kernel.servlet.MetaInfoCacheServletResponse;
import com.liferay.portal.kernel.servlet.WrapHttpServletResponseFilter;
import com.liferay.portal.kernel.servlet.filters.invoker.InvokerFilterHelper;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Minhchau Dang
 */
public class WebLogicIncludeFilter
	extends BasePortalFilter implements WrapHttpServletResponseFilter {

	@Override
	public HttpServletResponse getWrappedHttpServletResponse(
		HttpServletRequest request, HttpServletResponse response) {

		if (isWrap(response)) {
			return new WebLogicIncludeServletResponse(response);
		}

		return response;
	}

	@Override
	public boolean isFilterEnabled() {
		return ServerDetector.isWebLogic();
	}

	protected boolean isWrap(HttpServletResponse response) {
		if (response instanceof WebLogicIncludeServletResponse) {
			return false;
		}

		boolean wrap = false;

		HttpServletResponseWrapper previousResponseWrapper = null;

		while (response instanceof HttpServletResponseWrapper) {
			if (!wrap && (response instanceof MetaInfoCacheServletResponse)) {
				wrap = true;
			}

			HttpServletResponseWrapper responseWrapper =
				(HttpServletResponseWrapper)response;

			response = (HttpServletResponse)responseWrapper.getResponse();

			if (responseWrapper instanceof WebLogicIncludeServletResponse) {
				previousResponseWrapper.setResponse(response);
			}

			previousResponseWrapper = responseWrapper;
		}

		return wrap;
	}

}