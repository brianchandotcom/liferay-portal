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

package com.liferay.portal.resiliency.spi.agent;

import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PersistentHttpServletRequestWrapper;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.CookieUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Shuyang Zhou
 */
public class AgentRequest extends AgentSerializable {

	public AgentRequest(HttpServletRequest request) {
		cookies = request.getCookies();
		distributedRequestAttributes = extractDistributedRequestAttributes(
			request, Direction.REQUEST);
		headerMap = extractRequestHeaders(request);
		parameterMap = request.getParameterMap();
		serverName = request.getServerName();
		serverPort = request.getServerPort();

		String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);

		if ((contentType != null) &&
			contentType.startsWith(ContentTypes.MULTIPART_FORM_DATA)) {

			UploadServletRequest uploadServletRequest =
				PortalUtil.getUploadServletRequest(request);

			Map<String, FileItem[]> multipartParameterMap =
				uploadServletRequest.getMultipartParameterMap();
			Map<String, List<String>> regularParameterMap =
				uploadServletRequest.getRegularParameterMap();

			if (!multipartParameterMap.isEmpty()) {
				this.multipartParameterMap = multipartParameterMap;
			}

			if (!regularParameterMap.isEmpty()) {
				this.regularParameterMap = regularParameterMap;
			}
		}

		HttpSession session = request.getSession();

		originalSessionAttributes = extractSessionAttributes(session);

		captureThreadLocals();
	}

	public Map<String, Serializable> getOriginalSessionAttributes() {
		return originalSessionAttributes;
	}

	public HttpServletRequest populateRequest(HttpServletRequest request) {
		request = new AgentHttpServletRequestWrapper(request);

		for (Map.Entry<String, Serializable> entry :
				distributedRequestAttributes.entrySet()) {

			request.setAttribute(entry.getKey(), entry.getValue());
		}

		if ((multipartParameterMap != null) || (regularParameterMap != null)) {
			request = new UploadServletRequestImpl(
				request, multipartParameterMap, regularParameterMap);
		}

		restoreThreadLocals();

		return request;
	}

	public void populateSessionAttributes(HttpSession httpSession) {
		for (Map.Entry<String, Serializable> entry :
				originalSessionAttributes.entrySet()) {

			httpSession.setAttribute(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public String toString() {
		int length = 16 + parameterMap.size() * 4;

		if (cookies != null) {
			length += cookies.length * 2 - 1;
		}

		StringBundler sb = new StringBundler(length);

		sb.append("{cookies=[");

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				sb.append(CookieUtil.toString(cookie));
				sb.append(", ");
			}

			sb.setIndex(sb.index() - 1);
		}

		sb.append("], distributedRequestAttributes=");
		sb.append(distributedRequestAttributes);
		sb.append(", _headerMap=");
		sb.append(headerMap);
		sb.append(", _multipartParameterMap=");
		sb.append(multipartParameterMap);
		sb.append(", originalSessionAttributes=");
		sb.append(originalSessionAttributes);
		sb.append(", parameterMap={");

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(Arrays.toString(entry.getValue()));
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append("}, _regularParameterMap=");
		sb.append(regularParameterMap);
		sb.append(", _serverName=");
		sb.append(serverName);
		sb.append(", _serverPort=");
		sb.append(serverPort);
		sb.append("}");

		return sb.toString();
	}

	protected Cookie[] cookies;
	protected Map<String, Serializable> distributedRequestAttributes;
	protected Map<String, List<String>> headerMap;
	protected Map<String, FileItem[]> multipartParameterMap;
	protected Map<String, Serializable> originalSessionAttributes;
	protected Map<String, String[]> parameterMap;
	protected Map<String, List<String>> regularParameterMap;
	protected String serverName;
	protected int serverPort;

	protected class AgentHttpServletRequestWrapper
		extends PersistentHttpServletRequestWrapper {

		public AgentHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public Cookie[] getCookies() {
			return cookies;
		}

		@Override
		public String getHeader(String name) {
			List<String> values = headerMap.get(name.toLowerCase());

			if ((values == null) || values.isEmpty()) {
				return null;
			}

			return values.get(0);
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			return Collections.enumeration(headerMap.keySet());
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			List<String> values = headerMap.get(name.toLowerCase());

			if (values == null) {
				values = Collections.emptyList();
			}

			return Collections.enumeration(values);
		}

		@Override
		public String getParameter(String name) {
			String[] values = parameterMap.get(name);

			if ((values != null) && (values.length > 0)) {
				return values[0];
			}
			else {
				return null;
			}
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return parameterMap;
		}

		@Override
		public Enumeration<String> getParameterNames() {
			return Collections.enumeration(parameterMap.keySet());
		}

		@Override
		public String[] getParameterValues(String name) {
			return parameterMap.get(name);
		}

		@Override
		public String getServerName() {
			return serverName;
		}

		@Override
		public int getServerPort() {
			return serverPort;
		}

	}

}