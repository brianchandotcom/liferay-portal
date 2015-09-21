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

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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

	public static void add(HttpServletRequest request, Class<?> clazz) {
		add(request.getSession(false), clazz.getName());
	}

	public static void add(
		HttpServletRequest request, Class<?> clazz, Object value) {

		add(request.getSession(false), clazz.getName(), value);
	}

	public static void add(HttpServletRequest request, String key) {
		add(request.getSession(false), key);
	}

	public static void add(
		HttpServletRequest request, String key, Object value) {

		add(request.getSession(false), key, value);
	}

	public static void add(HttpSession session, Class<?> clazz) {
		add(session, clazz.getName());
	}

	public static void add(HttpSession session, Class<?> clazz, Object value) {
		add(session, clazz.getName(), value);
	}

	public static void add(HttpSession session, String key) {
		Map<String, Object> map = _getMap(session, null, true);

		if (map == null) {
			return;
		}

		map.put(key, key);
	}

	public static void add(HttpSession session, String key, Object value) {
		Map<String, Object> map = _getMap(session, null, true);

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
		clear(request.getSession(false));
	}

	public static void clear(HttpSession session) {
		Map<String, Object> map = _getMap(session, null, false);

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
		return contains(request.getSession(false), clazz.getName());
	}

	public static boolean contains(HttpServletRequest request, String key) {
		return contains(request.getSession(false), key);
	}

	public static boolean contains(HttpSession session, Class<?> clazz) {
		return contains(session, clazz.getName());
	}

	public static boolean contains(HttpSession session, String key) {
		Map<String, Object> map = _getMap(session, null, false);

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

	public static Object get(HttpServletRequest request, Class<?> clazz) {
		return get(request.getSession(false), clazz.getName());
	}

	public static Object get(HttpServletRequest request, String key) {
		return get(request.getSession(false), key);
	}

	public static Object get(HttpSession session, Class<?> clazz) {
		return get(session, clazz.getName());
	}

	public static Object get(HttpSession session, String key) {
		Map<String, Object> map = _getMap(session, null, false);

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
		return isEmpty(request.getSession(false));
	}

	public static boolean isEmpty(HttpSession session) {
		Map<String, Object> map = _getMap(session, null, false);

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
		return iterator(request.getSession(false));
	}

	public static Iterator<String> iterator(HttpSession session) {
		Map<String, Object> map = _getMap(session, null, false);

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
		return keySet(request.getSession(false));
	}

	public static Set<String> keySet(HttpSession session) {
		Map<String, Object> map = _getMap(session, null, false);

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
		print(request.getSession(false));
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
		return size(request.getSession(false));
	}

	public static int size(HttpSession session) {
		Map<String, Object> map = _getMap(session, null, false);

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

	protected static Map<String, Object> _getMap(
		HttpSession session, String portletKey, boolean createIfAbsent) {

		if (session == null) {
			return null;
		}

		if (portletKey == null) {
			portletKey = StringPool.BLANK;
		}

		Map<String, Object> map = null;

		try {
			map = (Map<String, Object>)session.getAttribute(
				portletKey + _CLASS_NAME);

			if ((map == null) && createIfAbsent) {
				map = new SessionMessagesMap();

				session.setAttribute(portletKey + _CLASS_NAME, map);
			}
		}
		catch (IllegalStateException ise) {

			// Session is already invalidated, just return a null map

		}

		return map;
	}

	protected static Map<String, Object> _getMap(
		PortletRequest portletRequest, boolean createIfAbsent) {

		return _getMap(
			_getPortalSession(portletRequest), _getPortletKey(portletRequest),
			createIfAbsent);
	}

	protected static HttpSession _getPortalSession(
		PortletRequest portletRequest) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		request = PortalUtil.getOriginalServletRequest(request);

		return request.getSession();
	}

	protected static String _getPortletKey(PortletRequest portletRequest) {
		StringBundler sb = new StringBundler(5);

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(portletRequest);

		sb.append(LiferayPortletSession.PORTLET_SCOPE_NAMESPACE);
		sb.append(liferayPortletRequest.getPortletName());
		sb.append(LiferayPortletSession.LAYOUT_SEPARATOR);
		sb.append(liferayPortletRequest.getPlid());
		sb.append(StringPool.QUESTION);

		return sb.toString();
	}

	private static final String _CLASS_NAME = SessionMessages.class.getName();

	private static class SessionMessagesMap
		extends LinkedHashMap<String, Object> {

		@Override
		public boolean containsKey(Object key) {
			key = _transformKey(key);

			return super.containsKey(key);
		}

		@Override
		public Object get(Object key) {
			key = _transformKey(key);

			return super.get(key);
		}

		@Override
		public Object put(String key, Object value) {
			key = _transformKey(key);

			return super.put(key, value);
		}

		private String _transformKey(Object key) {
			String keyString = String.valueOf(key);

			if (keyString != null) {
				if (keyString.equals("request_processed")) {
					keyString = "requestProcessed";
				}
			}

			return keyString;
		}

	}

}