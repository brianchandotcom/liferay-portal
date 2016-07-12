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

package com.liferay.portal.weblogic.support.internal.include;

import com.liferay.portal.kernel.servlet.MetaInfoCacheServletResponse;
import com.liferay.portal.servlet.filters.weblogic.WebLogicIncludeServletResponseFactory;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Shuyang Zhou
 */
public class WebLogicIncludeServletResponseFactoryImpl
	implements WebLogicIncludeServletResponseFactory {

	@Override
	public HttpServletResponse create(HttpServletResponse response) {
		if (isWrap(response)) {
			return new WebLogicIncludeServletResponse(response);
		}

		return response;
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