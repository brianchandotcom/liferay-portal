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

package com.liferay.shielded.container.internal.proxy;

import com.liferay.shielded.container.internal.ShieldedContainerClassLoader;
import com.liferay.shielded.container.internal.session.SerializationUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author Shuyang Zhou
 */
public class HttpSessionDelegate {

	public HttpSessionDelegate(HttpSession httpSession) {
		_httpSession = httpSession;
	}

	public Object getAttribute(String name) {
		Object value = _httpSession.getAttribute(name);

		if (value instanceof byte[]) {
			ServletContext servletContext = _httpSession.getServletContext();

			ClassLoader classLoader = (ClassLoader)servletContext.getAttribute(
				ShieldedContainerClassLoader.NAME);

			try {
				return SerializationUtil.deserialize(
					(byte[])value, classLoader);
			}
			catch (Exception exception) {
			}
		}

		return value;
	}

	private final HttpSession _httpSession;

}