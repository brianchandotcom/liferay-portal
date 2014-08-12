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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Brian Wing Shun Chan
 */
public class ServletRequestUtil {

	public static void logRequestWrappers(HttpServletRequest request) {
		HttpServletRequest tempRequest = request;

		while (true) {
			if (_log.isInfoEnabled()) {
				_log.info("Request class " + tempRequest.getClass().getName());
			}

			if (tempRequest instanceof HttpServletRequestWrapper) {
				HttpServletRequestWrapper requestWrapper =
					(HttpServletRequestWrapper)tempRequest;

				tempRequest = (HttpServletRequest)requestWrapper.getRequest();
			}
			else {
				break;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServletRequestUtil.class);

}