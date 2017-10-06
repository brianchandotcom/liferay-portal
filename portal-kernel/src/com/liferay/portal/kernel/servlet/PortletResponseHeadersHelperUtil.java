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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Tina Tian
 */
public class PortletResponseHeadersHelperUtil {

	public static void transferHeaders(
		Map<String, Object> headers, HttpServletResponse httpServletResponse) {

		_getPortletResponseHeadersHelper().transferHeaders(
			headers, httpServletResponse);
	}

	public void setPortletResponseHeadersHelper(
		PortletResponseHeadersHelper portletResponseHeadersHelper) {

		PortalRuntimePermission.checkSetBeanProperty(
			PortletResponseHeadersHelperUtil.class);

		_portletResponseHeadersHelper = portletResponseHeadersHelper;
	}

	private static PortletResponseHeadersHelper
		_getPortletResponseHeadersHelper() {

		PortalRuntimePermission.checkGetBeanProperty(
			PortletResponseHeadersHelperUtil.class);

		return _portletResponseHeadersHelper;
	}

	private static PortletResponseHeadersHelper _portletResponseHeadersHelper;

}