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
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class StrutsUtil {

	public static final String TEXT_HTML_DIR = "/html";

	public static void forward(
			String uri, ServletContext servletContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ServletException {

		if (_log.isDebugEnabled()) {
			_log.debug("Forward URI " + uri);
		}

		if (uri.equals(ActionConstants.COMMON_NULL)) {
			return;
		}

		if (!httpServletResponse.isCommitted()) {
			String path = TEXT_HTML_DIR.concat(uri);

			if (_log.isDebugEnabled()) {
				_log.debug("Forward path " + path);
			}

			RequestDispatcher requestDispatcher =
				DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
					servletContext, path);

			try {
				requestDispatcher.forward(
					httpServletRequest, httpServletResponse);
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioe, ioe);
				}
			}
			catch (ServletException se1) {
				httpServletRequest.setAttribute(
					PageContext.EXCEPTION, se1.getRootCause());

				String errorPath = TEXT_HTML_DIR + "/common/error.jsp";

				requestDispatcher =
					DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
						servletContext, errorPath);

				try {
					requestDispatcher.forward(
						httpServletRequest, httpServletResponse);
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

	private static final Log _log = LogFactoryUtil.getLog(StrutsUtil.class);

}