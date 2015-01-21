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
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Minhchau Dang
 */
public class WeblogicIncludeFilter extends BasePortalFilter
	implements WrapHttpServletResponseFilter {

	@Override
	public HttpServletResponse getWrappedHttpServletResponse(
		HttpServletRequest request, HttpServletResponse response) {

		if (needsWrappedHttpServletResponse(response)) {
			return new WeblogicIncludeServletResponse(response);
		}

		return response;
	}

	protected boolean needsWrappedHttpServletResponse(
		HttpServletResponse response) {

		if (!ServerDetector.isWebLogic()) {
			return false;
		}

		while (response instanceof HttpServletResponseWrapper) {
			if (response instanceof WeblogicIncludeServletResponse) {
				return false;
			}

			if (response instanceof MetaInfoCacheServletResponse) {
				return true;
			}

			HttpServletResponseWrapper wrapper =
				(HttpServletResponseWrapper)response;

			response = (HttpServletResponse)wrapper.getResponse();
		}

		return false;
	}

}