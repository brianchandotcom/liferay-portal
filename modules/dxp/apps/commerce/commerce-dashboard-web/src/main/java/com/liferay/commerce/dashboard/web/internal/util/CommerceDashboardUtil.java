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

package com.liferay.commerce.dashboard.web.internal.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceDashboardUtil {

	public static <T> T first(Iterable<T> iterable) {
		Iterator<T> iterator = iterable.iterator();

		return iterator.next();
	}

	public static <K, V> Map<K, V> getSessionMap(
		PortletRequest portletRequest, String name) {

		Map<K, V> map = (Map<K, V>)_getSessionValue(portletRequest, name);

		if (map == null) {
			map = Collections.emptyMap();
		}

		return map;
	}

	public static int getSessionValue(
		PortletRequest portletRequest, String name, int defaultValue) {

		return GetterUtil.getInteger(
			_getSessionValue(portletRequest, name), defaultValue);
	}

	public static long getSessionValue(
		PortletRequest portletRequest, String name, long defaultValue) {

		return GetterUtil.getLong(
			_getSessionValue(portletRequest, name), defaultValue);
	}

	public static void setSessionInteger(
		PortletRequest portletRequest, String name) {

		int value = ParamUtil.getInteger(portletRequest, name);

		PortletSession portletSession = portletRequest.getPortletSession();

		portletSession.setAttribute(
			name, value, PortletSession.APPLICATION_SCOPE);
	}

	private static final Object _getSessionValue(
		PortletRequest portletRequest, String name) {

		PortletSession portletSession = portletRequest.getPortletSession();

		return portletSession.getAttribute(
			name, PortletSession.APPLICATION_SCOPE);
	}

}