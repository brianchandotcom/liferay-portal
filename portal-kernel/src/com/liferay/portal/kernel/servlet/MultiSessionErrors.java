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

import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia Garcia
 */
public class MultiSessionErrors {

	public static void clear(PortletRequest portletRequest) {
		SessionErrors.clear(portletRequest);

		SessionErrors.clear(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static boolean contains(PortletRequest portletRequest, String key) {
		if (SessionErrors.contains(portletRequest, key) ||
			SessionErrors.contains(
				PortalUtil.getHttpServletRequest(portletRequest), key)) {

			return true;
		}

		return false;
	}

	public static Object get(PortletRequest portletRequest, String key) {
		Object value = SessionErrors.get(portletRequest, key);

		if (value != null) {
			return value;
		}

		return SessionErrors.get(
			PortalUtil.getHttpServletRequest(portletRequest), key);
	}

	public static boolean isEmpty(PortletRequest portletRequest) {
		if (SessionErrors.isEmpty(portletRequest) &&
			_isEmpty(PortalUtil.getHttpServletRequest(portletRequest))) {

			return true;
		}

		return false;
	}

	private static boolean _isEmpty(HttpServletRequest httpServletRequest) {
		if (SessionErrors.isEmpty(httpServletRequest)) {
			return true;
		}

		return false;
	}

}