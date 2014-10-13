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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;

import java.io.IOException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;

/**
 * @author Brian Wing Shun Chan
 */
public class StrutsUtil {

	public static final String STRUTS_PACKAGE = "org.apache.struts.";

	public static final String TEXT_HTML_DIR = "/html";

	public static final String TEXT_WAP_DIR = "/wap";

	public static void forward(
			String uri, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException {

		if (_log.isDebugEnabled()) {
			_log.debug("Forward URI " + uri);
		}

		if (uri.equals(ActionConstants.COMMON_NULL)) {
			return;
		}

		if (!response.isCommitted()) {
			String path = TEXT_HTML_DIR + uri;

			if (BrowserSnifferUtil.isWap(request)) {
				path = TEXT_WAP_DIR + uri;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Forward path " + path);
			}

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(path);

			try {
				requestDispatcher.forward(request, response);
			}
			catch (IOException ioe1) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioe1, ioe1);
				}
			}
			catch (ServletException se1) {
				request.setAttribute(PageContext.EXCEPTION, se1.getRootCause());

				String errorPath = TEXT_HTML_DIR + "/common/error.jsp";

				if (BrowserSnifferUtil.isWap(request)) {
					path = TEXT_WAP_DIR + "/common/error.jsp";
				}

				requestDispatcher = servletContext.getRequestDispatcher(
					errorPath);

				try {
					requestDispatcher.forward(request, response);
				}
				catch (IOException ioe2) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe2, ioe2);
					}
				}
				catch (ServletException se2) {
					throw se2;
				}
			}
		}
		else if (_log.isWarnEnabled()) {
			_log.warn(uri + " is already committed");
		}
	}

	public static void include(
			String uri, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException {

		if (_log.isDebugEnabled()) {
			_log.debug("Include URI " + uri);
		}

		String path = TEXT_HTML_DIR + uri;

		if (BrowserSnifferUtil.isWap(request)) {
			path = TEXT_WAP_DIR + uri;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Include path " + path);
		}

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		try {
			requestDispatcher.include(request, response);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}
		}
	}

	public static Map<String, Object> removeStrutsAttributes(
		PortletContext portletContext, PortletRequest portletRequest) {

		Map<String, Object> strutsAttributes = new HashMap<String, Object>();

		Enumeration<String> enu = portletRequest.getAttributeNames();

		while (enu.hasMoreElements()) {
			String attributeName = enu.nextElement();

			if (attributeName.startsWith(STRUTS_PACKAGE)) {
				strutsAttributes.put(
					attributeName, portletRequest.getAttribute(attributeName));
			}
		}

		for (String attributeName : strutsAttributes.keySet()) {
			portletRequest.setAttribute(attributeName, null);
		}

		Object moduleConfig = portletContext.getAttribute(Globals.MODULE_KEY);

		portletRequest.setAttribute(Globals.MODULE_KEY, moduleConfig);

		return strutsAttributes;
	}

	public static void setStrutsAttributes(
		PortletRequest portletRequest, Map<String, Object> strutsAttributes) {

		for (Map.Entry<String, Object> entry : strutsAttributes.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			portletRequest.setAttribute(key, value);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(StrutsUtil.class);

}