/**
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

package com.liferay.portal.servlet.filters.compoundsessionid;

import com.liferay.portal.kernel.servlet.WrapHttpServletRequestFilter;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PropsValues;

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

	public static String getSessionIdDelimiter() {
		if (_sessionIdDelimiter != null) {
			return _sessionIdDelimiter;
		}

		String sessionIdDelimiter = PropsValues.SESSION_ID_DELIMITER;

		if (Validator.isNull(sessionIdDelimiter)) {
			_sessionIdDelimiter = PropsUtil.get(
				"session.id." + ServerDetector.getServerId() + " .delimiter");
		}

		if (_sessionIdDelimiter == null) {
			_sessionIdDelimiter = StringPool.BLANK;
		}

		_sessionIdDelimiter = sessionIdDelimiter;

		return _sessionIdDelimiter;
	}

	public static boolean hasCompoundSessionId() {
		return _filterEnabled;
	}

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		if (Validator.isNotNull(getSessionIdDelimiter())) {
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

	public HttpServletRequest getWrappedHttpServletRequest(
		HttpServletRequest request, HttpServletResponse response) {

		return new CompoundSessionIdServletRequest(request);
	}

	private static boolean _filterEnabled;
	private static String _sessionIdDelimiter;

}