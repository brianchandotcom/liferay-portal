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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class SessionMessages {

	public static final String KEY_SUFFIX_CLOSE_REDIRECT = ".closeRedirect";

	public static final String KEY_SUFFIX_CLOSE_REFRESH_PORTLET =
		".closeRefreshPortlet";

	public static final String KEY_SUFFIX_DELETE_SUCCESS_DATA =
		".deleteSuccessData";

	public static final String KEY_SUFFIX_FORCE_SEND_REDIRECT =
		".forceSendRedirect";

	public static final String KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE =
		".hideDefaultErrorMessage";

	public static final String KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE =
		".hideDefaultSuccessMessage";

	public static final String KEY_SUFFIX_PORTLET_NOT_AJAXABLE =
		".portletNotAjaxable";

	public static final String KEY_SUFFIX_REFRESH_PORTLET = ".refreshPortlet";

	public static final String KEY_SUFFIX_REFRESH_PORTLET_DATA =
		".refreshPortletData";

	public static final String KEY_SUFFIX_UPDATED_CONFIGURATION =
		".updatedConfiguration";

	public static final String KEY_SUFFIX_UPDATED_PREFERENCES =
		".updatedPreferences";

	public static void add(
		HttpServletRequest httpServletRequest, Class<?> clazz) {

		add(_getPortalSession(httpServletRequest), clazz.getName());
	}

	public static void add(
		HttpServletRequest httpServletRequest, Class<?> clazz, Object value) {

		add(_getPortalSession(httpServletRequest), clazz.getName(), value);
	}

	public static void add(HttpServletRequest httpServletRequest, String key) {
		add(_getPortalSession(httpServletRequest), key);
	}

	public static void add(
		HttpServletRequest httpServletRequest, String key, Object value) {

		add(_getPortalSession(httpServletRequest), key, value);
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

	public static void clear(HttpServletRequest httpServletRequest) {
		clear(_getPortalSession(httpServletRequest));
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

	public static boolean contains(
		HttpServletRequest httpServletRequest, Class<?> clazz) {

		return contains(_getPortalSession(httpServletRequest), clazz.getName());
	}

	public static boolean contains(
		HttpServletRequest httpServletRequest, String key) {

		return contains(_getPortalSession(httpServletRequest), key);
	}

	public static boolean contains(HttpSession session, Class<?> clazz) {
		return contains(session, clazz.getName());
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

	public static boolean contains(PortletRequest portletRequest, String key) {
		Map<String, Object> map = _getMap(portletRequest, false);

		if (map == null) {
			return false;
		}

		return map.containsKey(key);
	}

	public static Object get(
		HttpServletRequest httpServletRequest, Class<?> clazz) {

		return get(_getPortalSession(httpServletRequest), clazz.getName());
	}

	public static Object get(
		HttpServletRequest httpServletRequest, String key) {

		return get(_getPortalSession(httpServletRequest), key);
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

	public static boolean isEmpty(HttpServletRequest httpServletRequest) {
		return isEmpty(_getPortalSession(httpServletRequest));
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

	public static Iterator<String> iterator(
		HttpServletRequest httpServletRequest) {

		return iterator(_getPortalSession(httpServletRequest));
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

	public static Set<String> keySet(HttpServletRequest httpServletRequest) {
		return keySet(_getPortalSession(httpServletRequest));
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

	public static void print(HttpServletRequest httpServletRequest) {
		print(_getPortalSession(httpServletRequest));
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

	public static int size(HttpServletRequest httpServletRequest) {
		return size(_getPortalSession(httpServletRequest));
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
				map = new SessionMessagesMap();

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

	private static HttpSession _getPortalSession(
		HttpServletRequest httpServletRequest) {

		HttpServletRequest originalHttpServletRequest =
			PortalUtil.getOriginalServletRequest(httpServletRequest);

		return originalHttpServletRequest.getSession();
	}

	private static HttpSession _getPortalSession(
		PortletRequest portletRequest) {

		return _getPortalSession(
			PortalUtil.getHttpServletRequest(portletRequest));
	}

	private static final String _CLASS_NAME = SessionMessages.class.getName();

	private static class SessionMessagesMap extends HashMap<String, Object> {

		@Override
		public boolean containsKey(Object key) {
			return super.containsKey(_transformKey((String)key));
		}

		@Override
		public Object get(Object key) {
			return super.get(_transformKey((String)key));
		}

		@Override
		public Object put(String key, Object value) {
			return super.put(_transformKey(key), value);
		}

		private String _transformKey(String key) {
			if (Objects.equals(key, "request_processed")) {
				key = "requestProcessed";
			}

			return key;
		}

	}

}