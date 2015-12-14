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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.util.Enumeration;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class RequestDispatcherUtil {

	public static ObjectValuePair<String, Long> getContentAndLastModifiedTime(
			RequestDispatcher requestDispatcher, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(response);

		requestDispatcher.include(
			new HttpServletRequestWrapper(request) {

				@Override
				public long getDateHeader(String name) {
					if (name.equals(HttpHeaders.IF_MODIFIED_SINCE)) {
						return -1;
					}

					return super.getDateHeader(name);
				}

				@Override
				public String getHeader(String name) {
					if (name.equals(HttpHeaders.IF_MODIFIED_SINCE) ||
						name.equals(HttpHeaders.IF_NONE_MATCH) ||
						name.equals(HttpHeaders.LAST_MODIFIED)) {

						return null;
					}

					return super.getHeader(name);
				}

				@Override
				public Enumeration<String> getHeaders(String name) {
					if (name.equals(HttpHeaders.IF_MODIFIED_SINCE) ||
						name.equals(HttpHeaders.IF_NONE_MATCH) ||
						name.equals(HttpHeaders.LAST_MODIFIED)) {

						return null;
					}

					return super.getHeaders(name);
				}

				@Override
				public String getMethod() {
					return HttpMethods.GET;
				}

			}, bufferCacheServletResponse);

		return new ObjectValuePair<>(
			bufferCacheServletResponse.getString(),
			GetterUtil.getLong(
				bufferCacheServletResponse.getHeader(HttpHeaders.LAST_MODIFIED),
				-1));
	}

	public static String getEffectivePath(HttpServletRequest request) {
		DispatcherType dispatcherType = request.getDispatcherType();

		switch (dispatcherType) {
			case FORWARD:
				return (String)request.getAttribute(
					RequestDispatcher.FORWARD_SERVLET_PATH);
			case INCLUDE:
				return (String)request.getAttribute(
					RequestDispatcher.INCLUDE_SERVLET_PATH);
			default:
				return request.getServletPath();
		}
	}

	public static long getLastModifiedTime(
			RequestDispatcher requestDispatcher, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(response);

		requestDispatcher.include(
			new HttpServletRequestWrapper(request) {

				@Override
				public String getMethod() {
					return HttpMethods.HEAD;
				}

			}, metaInfoCacheServletResponse);

		return GetterUtil.getLong(
			metaInfoCacheServletResponse.getHeader(HttpHeaders.LAST_MODIFIED),
			-1);
	}

}