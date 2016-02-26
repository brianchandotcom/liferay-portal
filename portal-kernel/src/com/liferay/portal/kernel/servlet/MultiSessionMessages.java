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

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class MultiSessionMessages {

	public static void add(PortletRequest portletRequest, Class<?> clazz) {
		SessionMessages.add(portletRequest, clazz.getName());

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		SessionMessages.add(request, clazz.getName());
	}

	public static void add(
		PortletRequest portletRequest, Class<?> clazz, Object value) {

		SessionMessages.add(portletRequest, clazz.getName(), value);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		SessionMessages.add(request, clazz.getName(), value);
	}

	public static void add(PortletRequest portletRequest, String key) {
		SessionMessages.add(portletRequest, key);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		SessionMessages.add(request, key);
	}

	public static void add(
		PortletRequest portletRequest, String key, Object value) {

		SessionMessages.add(portletRequest, key, value);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		SessionMessages.add(request, key, value);
	}

	public static void clear(PortletRequest portletRequest) {
		SessionMessages.clear(portletRequest);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		SessionMessages.clear(request);
	}

	public static boolean contains(
		PortletRequest portletRequest, Class<?> clazz) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		if (SessionMessages.contains(portletRequest, clazz.getName()) ||
			SessionMessages.contains(request, clazz.getName())) {

			return true;
		}

		return false;
	}

	public static boolean contains(PortletRequest portletRequest, String key) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		if (SessionMessages.contains(portletRequest, key) ||
			SessionMessages.contains(request, key)) {

			return true;
		}

		return false;
	}

	public static Object get(PortletRequest portletRequest, Class<?> clazz) {
		Object value = SessionMessages.get(portletRequest, clazz.getName());

		if (value != null) {
			return value;
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return SessionMessages.get(request, clazz.getName());
	}

	public static Object get(PortletRequest portletRequest, String key) {
		Object value = SessionMessages.get(portletRequest, key);

		if (value != null) {
			return value;
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return SessionMessages.get(request, key);
	}

	public static boolean isEmpty(PortletRequest portletRequest) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		if (SessionMessages.isEmpty(portletRequest) &&
			SessionMessages.isEmpty(request)) {

			return true;
		}

		return false;
	}

	public static Iterator<String> iterator(PortletRequest portletRequest) {
		Set<String> set = keySet(portletRequest);

		return set.iterator();
	}

	public static Set<String> keySet(PortletRequest portletRequest) {
		Set<String> set = new HashSet<>();

		set.addAll(SessionMessages.keySet(portletRequest));

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		set.addAll(SessionMessages.keySet(request));

		return Collections.unmodifiableSet(set);
	}

	public static void print(PortletRequest portletRequest) {
		SessionMessages.print(portletRequest);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		SessionMessages.print(request);
	}

	public static int size(PortletRequest portletRequest) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return SessionMessages.size(portletRequest) +
			SessionMessages.size(request);
	}

}