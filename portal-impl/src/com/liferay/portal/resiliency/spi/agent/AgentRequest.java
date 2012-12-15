/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
		_serverName = request.getServerName();
		_serverPort = request.getServerPort();

		_cookies = request.getCookies();

		_headerMap = extractRequestHeaders(request);

		_parameterMap = request.getParameterMap();

		_distributedRequestAttributes = extractDistributedRequestAttributes(
			request, Direction.Request);

		HttpSession session = request.getSession();

		_originalSessionAttributes = extractSessionAttributes(session);

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
				_multipartParameterMap = multipartParameterMap;
			}

			if (!regularParameterMap.isEmpty()) {
				_regularParameterMap = regularParameterMap;
			}
		}

		captureThreadLocals();
	}

	public Map<String, Serializable> getOriginalSessionAttributes() {
		return _originalSessionAttributes;
	}

	public HttpServletRequest populateRequest(HttpServletRequest request) {
		request = new AgentHttpServletRequestWrapper(request);

		for (Map.Entry<String, Serializable> entry :
				_distributedRequestAttributes.entrySet()) {

			String name = entry.getKey();
			Serializable value = entry.getValue();

			request.setAttribute(name, value);
		}

		if ((_multipartParameterMap != null) ||
			(_regularParameterMap != null)) {

			request = new UploadServletRequestImpl(
				request, _multipartParameterMap, _regularParameterMap);
		}

		restoreThreadLocals();

		return request;
	}

	public void populateSessionAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession();

		for (Map.Entry<String, Serializable> entry :
				_originalSessionAttributes.entrySet()) {

			String name = entry.getKey();
			Serializable value = entry.getValue();

			session.setAttribute(name, value);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{cookies=");
		sb.append(Arrays.toString(_cookies));
		sb.append(", parameterMap=");
		sb.append(_parameterMap);
		sb.append(", distributedRequestAttributes=");
		sb.append(_distributedRequestAttributes);
		sb.append(", originalSessionAttributes=");
		sb.append(_originalSessionAttributes);
		sb.append("}");

		return sb.toString();
	}

	private Cookie[] _cookies;
	private Map<String, Serializable> _distributedRequestAttributes;
	private Map<String, List<String>> _headerMap;
	private Map<String, FileItem[]> _multipartParameterMap;
	private Map<String, Serializable> _originalSessionAttributes;
	private Map<String, String[]> _parameterMap;
	private Map<String, List<String>> _regularParameterMap;
	private String _serverName;
	private int _serverPort;

	private class AgentHttpServletRequestWrapper
		extends PersistentHttpServletRequestWrapper {

		public AgentHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public Cookie[] getCookies() {
			return _cookies;
		}

		@Override
		public String getHeader(String name) {
			List<String> values = _headerMap.get(name.toLowerCase());

			if ((values == null) || values.isEmpty()) {
				return null;
			}

			return values.get(0);
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			return Collections.enumeration(_headerMap.keySet());
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			List<String> values = _headerMap.get(name.toLowerCase());

			if (values == null) {
				values = Collections.emptyList();
			}

			return Collections.enumeration(values);
		}

		@Override
		public String getParameter(String name) {
			String[] values = _parameterMap.get(name);

			if ((values != null) && (values.length > 0)) {
				return values[0];
			}
			else {
				return null;
			}
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return _parameterMap;
		}

		@Override
		public Enumeration<String> getParameterNames() {
			return Collections.enumeration(_parameterMap.keySet());
		}

		@Override
		public String[] getParameterValues(String name) {
			return _parameterMap.get(name);
		}

		@Override
		public String getServerName() {
			return _serverName;
		}

		@Override
		public int getServerPort() {
			return _serverPort;
		}

	}

}