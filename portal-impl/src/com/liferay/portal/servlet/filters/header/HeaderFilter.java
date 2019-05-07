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

package com.liferay.portal.servlet.filters.header;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PropsValues;

import java.text.Format;

import java.util.Enumeration;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 */
public class HeaderFilter extends BasePortalFilter {

	protected long getLastModified(HttpServletRequest httpServletRequest) {
		String value = httpServletRequest.getParameter("t");

		if (Validator.isNull(value)) {
			return -1;
		}

		return GetterUtil.getLong(value) / 1000 * 1000;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		FilterConfig filterConfig = getFilterConfig();

		Enumeration<String> enu = filterConfig.getInitParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (!_requestHeaderIgnoreInitParams.contains(name)) {
				_addHeader(
					httpServletRequest, httpServletResponse, name,
					filterConfig.getInitParameter(name));
			}
		}

		long lastModified = getLastModified(httpServletRequest);

		if (lastModified > 0) {
			long ifModifiedSince = httpServletRequest.getDateHeader(
				HttpHeaders.IF_MODIFIED_SINCE);

			httpServletResponse.setDateHeader(
				HttpHeaders.LAST_MODIFIED, lastModified);

			if (lastModified <= ifModifiedSince) {
				httpServletResponse.setDateHeader(
					HttpHeaders.LAST_MODIFIED, ifModifiedSince);
				httpServletResponse.setStatus(
					HttpServletResponse.SC_NOT_MODIFIED);

				return;
			}
		}

		processFilter(
			HeaderFilter.class.getName(), httpServletRequest,
			httpServletResponse, filterChain);
	}

	private void _addHeader(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String name, String value) {

		// LEP-5895, LPS-15802, LPS-69908

		if (StringUtil.equalsIgnoreCase(name, HttpHeaders.CACHE_CONTROL)) {
			if (_isNewSession(httpServletRequest)) {
				if (value.contains(HttpHeaders.CACHE_CONTROL_PUBLIC_VALUE)) {
					return;
				}

				if (PropsValues.WEB_SERVER_PROXY_LEGACY_MODE) {
					String contextPath = httpServletRequest.getContextPath();

					if (contextPath.equals(PortalUtil.getPathContext())) {
						return;
					}
				}
			}
		}
		else if (StringUtil.equalsIgnoreCase(name, HttpHeaders.EXPIRES)) {
			if (_isNewSession(httpServletRequest)) {
				return;
			}

			if (Validator.isNumber(value)) {
				int seconds = GetterUtil.getInteger(value);

				value = _dateFormat.format(
					System.currentTimeMillis() + (seconds * Time.SECOND));
			}
		}

		httpServletResponse.addHeader(name, value);
	}

	private boolean _isNewSession(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession(false);

		if ((session == null) || session.isNew()) {
			return true;
		}

		return false;
	}

	private static final Format _dateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat(
			Time.RFC822_FORMAT, LocaleUtil.US, TimeZoneUtil.GMT);
	private static final Set<String> _requestHeaderIgnoreInitParams =
		SetUtil.fromArray(PropsValues.REQUEST_HEADER_IGNORE_INIT_PARAMS);

}