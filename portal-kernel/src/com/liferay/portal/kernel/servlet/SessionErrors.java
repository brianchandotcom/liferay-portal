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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class SessionErrors {

	public static void add(HttpServletRequest request, Class<?> clazz) {
		add(_getPortalSession(request), clazz.getName());
	}

	public static void add(
		HttpServletRequest request, Class<?> clazz, Object value) {

		add(_getPortalSession(request), clazz.getName(), value);
	}

	public static void add(HttpServletRequest request, String key) {
		add(_getPortalSession(request), key);
	}

	public static void add(
		HttpServletRequest request, String key, Object value) {

		add(_getPortalSession(request), key, value);
	}

	public static void add(HttpSession session, Class<?> clazz) {
		add(session, clazz.getName());
	}

	public static void add(HttpSession session, Class<?> clazz, Object value) {
		add(session, clazz.getName(), value);
	}

	public static void add(HttpSession session, String key) {
		Map<String, Object> map = _getMap(session, _CLASS_NAME, true);

		if (map == null) {
			return;
		}

		map.put(key, key);
	}

	public static void add(HttpSession session, String key, Object value) {
		Map<String, Object> map = _getMap(session, _CLASS_NAME, true);

		if (map == null) {
			return;
		}

		map.put(key, value);
	}

	public static void add(PortletRequest portletRequest, Class<?> clazz) {
		add(portletRequest, clazz.getName());
	}

	public static void add(
		PortletRequest portletRequest, Class<?> clazz, Object value) {

		add(portletRequest, clazz.getName(), value);
	}

	public static void add(PortletRequest portletRequest, String key) {
		Map<String, Object> map = _getMap(portletRequest, true);

		if (map == null) {
			return;
		}

		map.put(key, key);
	}

	public static void add(
		PortletRequest portletRequest, String key, Object value) {

		Map<String, Object> map = _getMap(portletRequest, true);

		if (map == null) {
			return;
		}

		map.put(key, value);
	}

	public static void clear(HttpServletRequest request) {
		clear(_getPortalSession(request));
	}

	public static void clear(HttpSession session) {
		Map<String, Object> map = _getMap(session, _CLASS_NAME, false);

		if (map != null) {
			map.clear();
		}
	}

	public static void clear(PortletRequest portletRequest) {
		Map<String, Object> map = _getMap(portletRequest, false);

		if (map != null) {
			map.clear();
		}
	}

	public static boolean contains(HttpServletRequest request, Class<?> clazz) {
		return contains(_getPortalSession(request), clazz.getName());
	}

	public static boolean contains(
		HttpServletRequest request, Class<?>[] classes) {

		return contains(_getPortalSession(request), classes);
	}

	public static boolean contains(HttpServletRequest request, String key) {
		return contains(_getPortalSession(request), key);
	}

	public static boolean contains(HttpSession session, Class<?> clazz) {
		return contains(session, clazz.getName());
	}

	public static boolean contains(HttpSession session, Class<?>[] classes) {
		for (Class<?> clazz : classes) {
			if (contains(session, clazz.getName())) {
				return true;
			}
		}

		return false;
	}

	public static boolean contains(HttpSession session, String key) {
		Map<String, Object> map = _getMap(session, _CLASS_NAME, false);

		if (map == null) {
			return false;
		}

		return map.containsKey(key);
	}

	public static boolean contains(
		PortletRequest portletRequest, Class<?> clazz) {

		return contains(portletRequest, clazz.getName());
	}

	public static boolean contains(
		PortletRequest portletRequest, Class<?>[] classes) {

		for (Class<?> clazz : classes) {
			if (contains(portletRequest, clazz.getName())) {
				return true;
			}
		}

		return false;
	}

	public static boolean contains(PortletRequest portletRequest, String key) {
		Map<String, Object> map = _getMap(portletRequest, false);

		if (map == null) {
			return false;
		}

		return map.containsKey(key);
	}

	public static Object get(HttpServletRequest request, Class<?> clazz) {
		return get(_getPortalSession(request), clazz.getName());
	}

	public static Object get(HttpServletRequest request, String key) {
		return get(_getPortalSession(request), key);
	}

	public static Object get(HttpSession session, Class<?> clazz) {
		return get(session, clazz.getName());
	}

	public static Object get(HttpSession session, String key) {
		Map<String, Object> map = _getMap(session, _CLASS_NAME, false);

		if (map == null) {
			return null;
		}

		return map.get(key);
	}

	public static Object get(PortletRequest portletRequest, Class<?> clazz) {
		return get(portletRequest, clazz.getName());
	}

	public static Object get(PortletRequest portletRequest, String key) {
		Map<String, Object> map = _getMap(portletRequest, false);

		if (map == null) {
			return null;
		}

		return map.get(key);
	}

	public static boolean isEmpty(HttpServletRequest request) {
		return isEmpty(_getPortalSession(request));
	}

	public static boolean isEmpty(HttpSession session) {
		Map<String, Object> map = _getMap(session, _CLASS_NAME, false);

		if (map == null) {
			return true;
		}

		return map.isEmpty();
	}

	public static boolean isEmpty(PortletRequest portletRequest) {
		Map<String, Object> map = _getMap(portletRequest, false);

		if (map == null) {
			return true;
		}

		return map.isEmpty();
	}

	public static Iterator<String> iterator(HttpServletRequest request) {
		return iterator(_getPortalSession(request));
	}

	public static Iterator<String> iterator(HttpSession session) {
		Map<String, Object> map = _getMap(session, _CLASS_NAME, false);

		if (map == null) {
			List<String> list = Collections.<String>emptyList();

			return list.iterator();
		}

		Set<String> set = Collections.unmodifiableSet(map.keySet());

		return set.iterator();
	}

	public static Iterator<String> iterator(PortletRequest portletRequest) {
		Map<String, Object> map = _getMap(portletRequest, false);

		if (map == null) {
			List<String> list = Collections.<String>emptyList();

			return list.iterator();
		}

		Set<String> set = Collections.unmodifiableSet(map.keySet());

		return set.iterator();
	}

	public static Set<String> keySet(HttpServletRequest request) {
		return keySet(_getPortalSession(request));
	}

	public static Set<String> keySet(HttpSession session) {
		Map<String, Object> map = _getMap(session, _CLASS_NAME, false);

		if (map == null) {
			return Collections.emptySet();
		}

		return Collections.unmodifiableSet(map.keySet());
	}

	public static Set<String> keySet(PortletRequest portletRequest) {
		Map<String, Object> map = _getMap(portletRequest, false);

		if (map == null) {
			return Collections.emptySet();
		}

		return Collections.unmodifiableSet(map.keySet());
	}

	public static void print(HttpServletRequest request) {
		print(_getPortalSession(request));
	}

	public static void print(HttpSession session) {
		Iterator<String> itr = iterator(session);

		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}

	public static void print(PortletRequest portletRequest) {
		Iterator<String> itr = iterator(portletRequest);

		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}

	public static int size(HttpServletRequest request) {
		return size(_getPortalSession(request));
	}

	public static int size(HttpSession session) {
		Map<String, Object> map = _getMap(session, _CLASS_NAME, false);

		if (map == null) {
			return 0;
		}

		return map.size();
	}

	public static int size(PortletRequest portletRequest) {
		Map<String, Object> map = _getMap(portletRequest, false);

		if (map == null) {
			return 0;
		}

		return map.size();
	}

	private static String _getKey(PortletRequest portletRequest) {
		StringBundler sb = new StringBundler(6);

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(portletRequest);

		sb.append(LiferayPortletSession.PORTLET_SCOPE_NAMESPACE);
		sb.append(liferayPortletRequest.getPortletName());
		sb.append(LiferayPortletSession.LAYOUT_SEPARATOR);
		sb.append(liferayPortletRequest.getPlid());
		sb.append(StringPool.QUESTION);
		sb.append(_CLASS_NAME);

		return sb.toString();
	}

	private static Map<String, Object> _getMap(
		HttpSession session, String key, boolean createIfAbsent) {

		if (session == null) {
			return null;
		}

		try {
			Map<String, Object> map = (Map<String, Object>)session.getAttribute(
				key);

			if ((map == null) && createIfAbsent) {
				map = new HashMap<>();

				session.setAttribute(key, map);
			}

			return map;
		}
		catch (IllegalStateException ise) {

			// Session is already invalidated, just return a null map

			return null;
		}
	}

	private static Map<String, Object> _getMap(
		PortletRequest portletRequest, boolean createIfAbsent) {

		return _getMap(
			_getPortalSession(portletRequest), _getKey(portletRequest),
			createIfAbsent);
	}

	private static HttpSession _getPortalSession(HttpServletRequest request) {
		HttpServletRequest originalRequest =
			PortalUtil.getOriginalServletRequest(request);

		return originalRequest.getSession();
	}

	private static HttpSession _getPortalSession(
		PortletRequest portletRequest) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return _getPortalSession(request);
	}

	private static final String _CLASS_NAME = SessionErrors.class.getName();

}