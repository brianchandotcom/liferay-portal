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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.PortletResponseHeadersHelper;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tina Tian
 */
public class PortletResponseHeadersHelperImpl
	implements PortletResponseHeadersHelper {

	@Override
	public void transferHeaders(
		Map<String, Object> headers, HttpServletResponse httpServletResponse) {

		for (Map.Entry<String, Object> entry : headers.entrySet()) {
			String name = entry.getKey();
			Object values = entry.getValue();

			if (values instanceof Integer[]) {
				Integer[] intValues = (Integer[])values;

				for (int value : intValues) {
					if (httpServletResponse.containsHeader(name)) {
						httpServletResponse.addIntHeader(name, value);
					}
					else {
						httpServletResponse.setIntHeader(name, value);
					}
				}
			}
			else if (values instanceof Long[]) {
				Long[] dateValues = (Long[])values;

				for (long value : dateValues) {
					if (httpServletResponse.containsHeader(name)) {
						httpServletResponse.addDateHeader(name, value);
					}
					else {
						httpServletResponse.setDateHeader(name, value);
					}
				}
			}
			else if (values instanceof String[]) {
				String[] stringValues = (String[])values;

				for (String value : stringValues) {
					if (httpServletResponse.containsHeader(name)) {
						httpServletResponse.addHeader(name, value);
					}
					else {
						httpServletResponse.setHeader(name, value);
					}
				}
			}
			else if (values instanceof Cookie[]) {
				Cookie[] cookies = (Cookie[])values;

				for (Cookie cookie : cookies) {
					httpServletResponse.addCookie(cookie);
				}
			}
		}
	}

}