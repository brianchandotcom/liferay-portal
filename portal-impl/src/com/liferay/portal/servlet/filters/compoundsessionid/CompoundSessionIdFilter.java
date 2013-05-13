/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.compoundsessionid;

import com.liferay.portal.kernel.servlet.WrapHttpServletRequestFilter;
import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdServletRequest;
import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdSplitterUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-18587.
 * </p>
 *
 * @author Michael C. Han
 */
public class CompoundSessionIdFilter
	extends BasePortalFilter implements WrapHttpServletRequestFilter {

	@Override
	public HttpServletRequest getWrappedHttpServletRequest(
		HttpServletRequest request, HttpServletResponse response) {

		return new CompoundSessionIdServletRequest(request);
	}

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
			_filterEnabled = true;
		}
		else {
			_filterEnabled = false;
		}
	}

	@Override
	public boolean isFilterEnabled() {
		return _filterEnabled;
	}

	private static boolean _filterEnabled;

}