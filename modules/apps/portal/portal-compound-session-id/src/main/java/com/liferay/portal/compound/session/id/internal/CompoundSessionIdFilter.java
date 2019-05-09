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

package com.liferay.portal.compound.session.id.internal;

import com.liferay.portal.kernel.servlet.WrapHttpServletRequestFilter;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-18587.
 * </p>
 *
 * @author Michael C. Han
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"dispatcher=ERROR", "dispatcher=FORWARD", "dispatcher=INCLUDE",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=Compound Session Id Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class CompoundSessionIdFilter
	extends BasePortalFilter implements WrapHttpServletRequestFilter {

	@Override
	public HttpServletRequest getWrappedHttpServletRequest(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		HttpServletRequest wrappedHttpServletRequest = httpServletRequest;

		while (wrappedHttpServletRequest instanceof HttpServletRequestWrapper) {
			if (wrappedHttpServletRequest instanceof
					CompoundSessionIdServletRequest) {

				return httpServletRequest;
			}

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)wrappedHttpServletRequest;

			wrappedHttpServletRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		return new CompoundSessionIdServletRequest(httpServletRequest);
	}

}