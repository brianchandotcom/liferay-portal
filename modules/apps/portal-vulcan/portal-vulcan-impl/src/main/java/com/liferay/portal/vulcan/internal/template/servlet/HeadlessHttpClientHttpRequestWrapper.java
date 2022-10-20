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

package com.liferay.portal.vulcan.internal.template.servlet;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Alejandro Tardín
 */
public class HeadlessHttpClientHttpRequestWrapper
	extends HttpServletRequestWrapper {

	public HeadlessHttpClientHttpRequestWrapper(
		HttpServletRequest httpServletRequest) {

		super(httpServletRequest);
	}

	@Override
	public String getHeader(String name) {
		if (StringUtil.equalsIgnoreCase(name, HttpHeaders.ACCEPT)) {
			return ContentTypes.APPLICATION_JSON;
		}

		return super.getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		if (StringUtil.equalsIgnoreCase(name, HttpHeaders.ACCEPT)) {
			return Collections.enumeration(
				Arrays.asList(ContentTypes.APPLICATION_JSON));
		}

		return super.getHeaders(name);
	}

}