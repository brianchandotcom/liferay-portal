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

package com.liferay.portal.servlet.filters.header;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.text.Format;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

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

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_filterConfig = filterConfig;
		_dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			_DATE_FORMAT, Locale.US, TimeZoneUtil.getTimeZone(_TIME_ZONE));
	}

	protected long getLastModified(HttpServletRequest request) {
		long lasModified = -1;

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			request.getQueryString());

		String[] value = parameterMap.get("t");

		if ((value != null) && (value.length > 0)) {
			lasModified = GetterUtil.getLong(value[0]);
		}

		return lasModified;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		Enumeration<String> enu = _filterConfig.getInitParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (ArrayUtil.contains(
					PropsValues.REQUEST_HEADER_IGNORE_INIT_PARAMS, name)) {

				continue;
			}

			String value = _filterConfig.getInitParameter(name);

			if (name.equals(_EXPIRES) && Validator.isNumber(value)) {
				int seconds = GetterUtil.getInteger(value);

				Calendar cal = new GregorianCalendar();

				cal.add(Calendar.SECOND, seconds);

				value = _dateFormat.format(cal.getTime());
			}

			// LEP-5895 and LPS-15802

			boolean addHeader = true;

			if (name.equalsIgnoreCase(HttpHeaders.CACHE_CONTROL) ||
				name.equalsIgnoreCase(HttpHeaders.EXPIRES)) {

				boolean newSession = false;

				HttpSession session = request.getSession(false);

				if ((session == null) || session.isNew()) {
					newSession = true;
				}

				String contextPath = request.getContextPath();

				if (name.equalsIgnoreCase(HttpHeaders.EXPIRES) && newSession) {
					addHeader = false;
				}
				else if (PropsValues.WEB_SERVER_PROXY_LEGACY_MODE &&
						 newSession &&
						 contextPath.equals(PortalUtil.getPathContext())) {

					addHeader = false;
				}
			}

			if (addHeader) {
				response.addHeader(name, value);
			}
		}

		long lastModified = getLastModified(request);
		long ifModifiedSince = request.getDateHeader(
			HttpHeaders.IF_MODIFIED_SINCE);

		if (lastModified > 0) {
			response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModified);

			if (lastModified <= ifModifiedSince) {
				response.setDateHeader(
					HttpHeaders.LAST_MODIFIED, ifModifiedSince);
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

				return;
			}
		}

		processFilter(HeaderFilter.class, request, response, filterChain);
	}

	private static final String _DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

	private static final String _EXPIRES = "Expires";

	private static final String _TIME_ZONE = "GMT";

	private static final String _URL_REGEX_PATTERN = "url-regex-pattern";

	private Format _dateFormat;
	private FilterConfig _filterConfig;

}