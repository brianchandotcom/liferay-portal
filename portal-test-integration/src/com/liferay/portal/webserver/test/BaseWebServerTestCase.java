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

package com.liferay.portal.webserver.test;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.webserver.WebServerServlet;
import com.liferay.portlet.documentlibrary.service.test.BaseDLAppTestCase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Alexander Chow
 */
public abstract class BaseWebServerTestCase extends BaseDLAppTestCase {

	public MockHttpServletResponse service(
			String method, String path, Map<String, String> headers,
			Map<String, String> params, User user, byte[] data)
		throws Exception {

		if (headers == null) {
			headers = new HashMap<>();
		}

		if (params == null) {
			params = new HashMap<>();
		}

		if (user == null) {
			user = TestPropsValues.getUser();
		}

		String requestURI =
			_CONTEXT_PATH + _SERVLET_PATH + _PATH_INFO_PREFACE + path;

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(method, requestURI);

		mockHttpServletRequest.setAttribute(WebKeys.USER, user);
		mockHttpServletRequest.setContextPath(_CONTEXT_PATH);
		mockHttpServletRequest.setParameters(params);
		mockHttpServletRequest.setPathInfo(_PATH_INFO_PREFACE + path);
		mockHttpServletRequest.setServletPath(_SERVLET_PATH);

		if (data != null) {
			mockHttpServletRequest.setContent(data);

			String contentType = headers.remove(HttpHeaders.CONTENT_TYPE);

			if (contentType != null) {
				mockHttpServletRequest.setContentType(contentType);
			}
			else {
				mockHttpServletRequest.setContentType(ContentTypes.TEXT_PLAIN);
			}
		}

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			mockHttpServletRequest.addHeader(key, value);
		}

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		mockHttpServletResponse.setCharacterEncoding(StringPool.UTF8);

		Servlet httpServlet = getServlet();

		httpServlet.service(mockHttpServletRequest, mockHttpServletResponse);

		return mockHttpServletResponse;
	}

	protected Servlet getServlet() {
		return new WebServerServlet();
	}

	private static final String _CONTEXT_PATH = "/documents";

	private static final String _PATH_INFO_PREFACE = "";

	private static final String _SERVLET_PATH = "";

}