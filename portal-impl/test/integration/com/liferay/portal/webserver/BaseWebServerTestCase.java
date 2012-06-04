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

package com.liferay.portal.webserver;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.service.BaseDLAppTestCase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Alexander Chow
 */
public abstract class BaseWebServerTestCase extends BaseDLAppTestCase {

	public MockHttpServletResponse service(
			String method, String path, Map<String, String> headers,
			byte[] data)
		throws Exception {

		MockHttpServletResponse response = new MockHttpServletResponse();

		response.setCharacterEncoding(StringPool.UTF8);

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		String requestURI =
			_CONTEXT_PATH + _SERVLET_PATH + _PATH_INFO_PREFACE + path;

		MockHttpServletRequest request = new MockHttpServletRequest(
			method, requestURI);

		request.setAttribute(WebKeys.USER, TestPropsValues.getUser());
		request.setContextPath(_CONTEXT_PATH);
		request.setServletPath(_SERVLET_PATH);
		request.setPathInfo(_PATH_INFO_PREFACE + path);

		if (data != null) {
			request.setContent(data);

			String contentType = headers.remove(HttpHeaders.CONTENT_TYPE);

			if (contentType != null) {
				request.setContentType(contentType);
			}
			else {
				request.setContentType(ContentTypes.TEXT_PLAIN);
			}
		}

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			request.addHeader(key, value);
		}

		getServlet().service(request, response);

		return response;
	}

	protected HttpServlet getServlet() {
		return new WebServerServlet();
	}

	private static final String _CONTEXT_PATH = "/documents";

	private static final String _PATH_INFO_PREFACE = "";

	private static final String _SERVLET_PATH = "";

}