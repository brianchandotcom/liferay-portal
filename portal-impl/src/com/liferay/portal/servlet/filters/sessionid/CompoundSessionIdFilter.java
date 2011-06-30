/*
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.sessionid;

import com.liferay.portal.kernel.servlet.WrapHttpServletRequestFilter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PropsValues;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * See LPS-18587
 *
 * @author Michael C. Han
 */
public class CompoundSessionIdFilter
	extends BasePortalFilter implements WrapHttpServletRequestFilter {

	public static boolean hasCompoundSessionId() {
		return _validDelimiter;
	}

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_validDelimiter = Validator.isNotNull(PropsValues.SESSION_ID_DELIMITER);
	}

	@Override
	public boolean isFilterEnabled() {
		if(_validDelimiter) {
			return true;
		}

		return false;
	}

	public HttpServletRequest getWrappedHttpServletRequest(
		HttpServletRequest request, HttpServletResponse response) {

		return new CompoundSessionIdServletRequest(request);
	}

	private static boolean _validDelimiter;
}