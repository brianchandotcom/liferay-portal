/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Sergio González
 */
public class PortalMessages {

	public static final String KEY_ANIMATION = "animation";

	public static final String KEY_CSS_CLASS = "cssClass";

	public static final String KEY_JSP_PATH = "jspPath";

	public static final String KEY_MESSAGE = "message";

	public static final String KEY_PORTLET_ID = "portletId";

	public static final String KEY_TIMEOUT = "timeout";

	public static void add(HttpServletRequest request, Class<?> clazz) {
		add(request.getSession(), clazz.getName());
	}

	public static void add(
		HttpServletRequest request, Class<?> clazz, Object value) {

		add(request.getSession(), clazz.getName(), value);
	}

	public static void add(HttpServletRequest request, String key) {
		add(request.getSession(), key);
	}

	public static void add(
		HttpServletRequest request, String key, Object value) {

		add(request.getSession(), key, value);
	}

	public static void add(HttpSession session, Class<?> clazz) {
		add(session, clazz.getName());
	}

	public static void add(HttpSession session, Class<?> clazz, Object value) {
		add(session, clazz.getName(), value);
	}

	public static void add(HttpSession session, String key) {
		Map<String, Object> map = _getMap(session, true);

		map.put(key, key);
	}

	public static void add(HttpSession session, String key, Object value) {
		Map<String, Object> map = _getMap(session, true);

		map.put(key, value);
	}

	public static void add(PortletRequest portletRequest, Class<?> clazz) {
		add(PortalUtil.getHttpServletRequest(portletRequest), clazz.getName());
	}

	public static void add(
		PortletRequest portletRequest, Class<?> clazz, Object value) {

		add(
			PortalUtil.getHttpServletRequest(portletRequest), clazz.getName(),
			value);
	}

	public static void add(PortletRequest portletRequest, String key) {
		add(PortalUtil.getHttpServletRequest(portletRequest), key);
	}

	public static void add(
		PortletRequest portletRequest, String key, Object value) {

		add(PortalUtil.getHttpServletRequest(portletRequest), key, value);
	}

	public static void clear(HttpServletRequest request) {
		clear(request.getSession());
	}

	public static void clear(HttpSession session) {
		Map<String, Object> map = _getMap(session, false);

		if (map != null) {
			map.clear();
		}
	}

	public static void clear(PortletRequest portletRequest) {
		clear(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static boolean contains(HttpServletRequest request, Class<?> clazz) {
		return contains(request.getSession(), clazz.getName());
	}

	public static boolean contains(HttpServletRequest request, String key) {
		return contains(request.getSession(), key);
	}

	public static boolean contains(HttpSession session, Class<?> clazz) {
		return contains(session, clazz.getName());
	}

	public static boolean contains(HttpSession session, String key) {
		Map<String, Object> map = _getMap(session, false);

		if (map == null) {
			return false;
		}

		return map.containsKey(key);
	}

	public static boolean contains(
		PortletRequest portletRequest, Class<?> clazz) {

		return contains(
			PortalUtil.getHttpServletRequest(portletRequest), clazz.getName());
	}

	public static boolean contains(PortletRequest portletRequest, String key) {
		return contains(PortalUtil.getHttpServletRequest(portletRequest), key);
	}

	public static Object get(HttpServletRequest request, Class<?> clazz) {
		return get(request.getSession(), clazz.getName());
	}

	public static Object get(HttpServletRequest request, String key) {
		return get(request.getSession(), key);
	}

	public static Object get(HttpSession session, Class<?> clazz) {
		return get(session, clazz.getName());
	}

	public static Object get(HttpSession session, String key) {
		Map<String, Object> map = _getMap(session, false);

		if (map == null) {
			return null;
		}

		return map.get(key);
	}

	public static Object get(PortletRequest portletRequest, Class<?> clazz) {
		return get(
			PortalUtil.getHttpServletRequest(portletRequest), clazz.getName());
	}

	public static Object get(PortletRequest portletRequest, String key) {
		return get(PortalUtil.getHttpServletRequest(portletRequest), key);
	}

	public static boolean isEmpty(HttpServletRequest request) {
		return isEmpty(request.getSession());
	}

	public static boolean isEmpty(HttpSession session) {
		Map<String, Object> map = _getMap(session, false);

		if (map == null) {
			return true;
		}

		return map.isEmpty();
	}

	public static boolean isEmpty(PortletRequest portletRequest) {
		return isEmpty(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static Iterator<String> iterator(HttpServletRequest request) {
		return iterator(request.getSession());
	}

	public static Iterator<String> iterator(HttpSession session) {
		Map<String, Object> map = _getMap(session, false);

		if (map == null) {
			List<String> list = Collections.<String>emptyList();

			return list.iterator();
		}

		Set<String> set = Collections.unmodifiableSet(map.keySet());

		return set.iterator();
	}

	public static Iterator<String> iterator(PortletRequest portletRequest) {
		return iterator(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static Set<String> keySet(HttpServletRequest request) {
		return keySet(request.getSession());
	}

	public static Set<String> keySet(HttpSession session) {
		Map<String, Object> map = _getMap(session, false);

		if (map == null) {
			return Collections.emptySet();
		}

		return Collections.unmodifiableSet(map.keySet());
	}

	public static Set<String> keySet(PortletRequest portletRequest) {
		return keySet(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static void print(HttpServletRequest request) {
		print(request.getSession());
	}

	public static void print(HttpSession session) {
		Iterator<String> itr = iterator(session);

		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}

	public static void print(PortletRequest portletRequest) {
		print(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static int size(HttpServletRequest request) {
		return size(request.getSession());
	}

	public static int size(HttpSession session) {
		Map<String, Object> map = _getMap(session, false);

		if (map == null) {
			return 0;
		}

		return map.size();
	}

	public static int size(PortletRequest portletRequest) {
		return size(PortalUtil.getHttpServletRequest(portletRequest));
	}

	private static Map<String, Object> _getMap(
		HttpSession session, boolean createIfAbsent) {

		Map<String, Object> map = null;

		try {
			map = (Map<String, Object>)session.getAttribute(
				WebKeys.PORTAL_MESSAGES);

			if ((map == null) && createIfAbsent) {
				map = new LinkedHashMap<String, Object>();

				session.setAttribute(WebKeys.PORTAL_MESSAGES, map);
			}
		}
		catch (IllegalStateException ise) {

			// Session is already invalidated, just return a null map

		}

		return map;
	}

}