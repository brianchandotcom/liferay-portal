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
import com.liferay.portal.kernel.util.StringUtil;

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
			RequestDispatcher requestDispatcher,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		BufferCacheServletResponse bufferCacheServletResponse =
			new LastModifiedCacheServletResponse(httpServletResponse);

		requestDispatcher.include(
			new HttpServletRequestWrapper(httpServletRequest) {

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

			},
			bufferCacheServletResponse);

		return new ObjectValuePair<>(
			bufferCacheServletResponse.getString(),
			GetterUtil.getLong(
				bufferCacheServletResponse.getHeader(HttpHeaders.LAST_MODIFIED),
				-1));
	}

	public static String getEffectivePath(
		HttpServletRequest httpServletRequest) {

		DispatcherType dispatcherType = httpServletRequest.getDispatcherType();

		if (dispatcherType.equals(DispatcherType.FORWARD)) {
			return (String)httpServletRequest.getAttribute(
				RequestDispatcher.FORWARD_SERVLET_PATH);
		}
		else if (dispatcherType.equals(DispatcherType.INCLUDE)) {
			return (String)httpServletRequest.getAttribute(
				RequestDispatcher.INCLUDE_SERVLET_PATH);
		}

		return httpServletRequest.getServletPath();
	}

	public static long getLastModifiedTime(
			RequestDispatcher requestDispatcher,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new LastModifiedCacheServletResponse(httpServletResponse);

		requestDispatcher.include(
			new HttpServletRequestWrapper(httpServletRequest) {

				@Override
				public String getMethod() {
					return HttpMethods.HEAD;
				}

			},
			metaInfoCacheServletResponse);

		return GetterUtil.getLong(
			metaInfoCacheServletResponse.getHeader(HttpHeaders.LAST_MODIFIED),
			-1);
	}

	private static class LastModifiedCacheServletResponse
		extends BufferCacheServletResponse {

		public LastModifiedCacheServletResponse(
			HttpServletResponse httpServletResponse) {

			super(httpServletResponse);
		}

		@Override
		public void addDateHeader(String name, long value) {
			if (StringUtil.equalsIgnoreCase(name, HttpHeaders.LAST_MODIFIED)) {
				_lastModified = String.valueOf(value);

				return;
			}

			super.addDateHeader(name, value);
		}

		@Override
		public void addHeader(String name, String value) {
			if (StringUtil.equalsIgnoreCase(name, HttpHeaders.LAST_MODIFIED)) {
				_lastModified = value;

				return;
			}

			super.addHeader(name, value);
		}

		@Override
		public String getHeader(String name) {
			if (StringUtil.equalsIgnoreCase(name, HttpHeaders.LAST_MODIFIED)) {
				return _lastModified;
			}

			return super.getHeader(name);
		}

		@Override
		public void setDateHeader(String name, long value) {
			if (StringUtil.equalsIgnoreCase(name, HttpHeaders.LAST_MODIFIED)) {
				_lastModified = String.valueOf(value);

				return;
			}

			super.setDateHeader(name, value);
		}

		@Override
		public void setHeader(String name, String value) {
			if (StringUtil.equalsIgnoreCase(name, HttpHeaders.LAST_MODIFIED)) {
				_lastModified = value;

				return;
			}

			super.setHeader(name, value);
		}

		private String _lastModified;

	}

}