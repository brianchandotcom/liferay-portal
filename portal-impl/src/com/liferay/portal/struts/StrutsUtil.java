/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.struts;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.struts.constants.ActionConstants;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class StrutsUtil {

	public static final String EXCEPTION =
		StrutsUtil.class.getName() + "_EXCEPTION";

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
			catch (IOException ioException) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioException);
				}
			}
			catch (ServletException servletException1) {
				httpServletRequest.setAttribute(
					EXCEPTION, servletException1.getRootCause());

				String errorPath = TEXT_HTML_DIR + "/common/error.jsp";

				requestDispatcher =
					DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
						servletContext, errorPath);

				try {
					requestDispatcher.forward(
						httpServletRequest, httpServletResponse);
				}
				catch (IOException ioException) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioException);
					}
				}
				catch (ServletException servletException2) {
					throw servletException2;
				}
			}
		}
		else if (_log.isWarnEnabled()) {
			_log.warn(uri + " is already committed");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(StrutsUtil.class);

}