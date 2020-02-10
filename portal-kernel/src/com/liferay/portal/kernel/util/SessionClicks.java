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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;

import java.util.ConcurrentModificationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class SessionClicks {

	public static String get(
		HttpServletRequest httpServletRequest, String key,
		String defaultValue) {

		return get(httpServletRequest, _DEFAULT_NAMESPACE, key, defaultValue);
	}

	public static String get(
		HttpServletRequest httpServletRequest, String namespace, String key,
		String defaultValue) {

		try {
			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					httpServletRequest);

			return portalPreferences.getValue(namespace, key, defaultValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return null;
		}
	}

	public static String get(
		HttpSession session, String key, String defaultValue) {

		return get(session, _DEFAULT_NAMESPACE, key, defaultValue);
	}

	public static String get(
		HttpSession session, String namespace, String key,
		String defaultValue) {

		String sessionKey = namespace.concat(
			StringPool.COLON
		).concat(
			key
		);

		return GetterUtil.getString(
			session.getAttribute(sessionKey), defaultValue);
	}

	public static void put(
		HttpServletRequest httpServletRequest, String key, String value) {

		put(httpServletRequest, _DEFAULT_NAMESPACE, key, value);
	}

	public static void put(
		HttpServletRequest httpServletRequest, String namespace, String key,
		String value) {

		if ((key.length() > _SESSION_CLICKS_MAX_SIZE_TERMS) ||
			(value.length() > _SESSION_CLICKS_MAX_SIZE_TERMS)) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Session clicks has attempted to exceed the maximum ",
						"size allowed for keys or values with {key=", key,
						", value=", value, "}"));
			}

			return;
		}

		while (true) {
			try {
				PortalPreferences portalPreferences =
					PortletPreferencesFactoryUtil.getPortalPreferences(
						httpServletRequest);

				int size = portalPreferences.size();

				if (size <= _SESSION_CLICKS_MAX_ALLOWED_VALUES) {
					portalPreferences.setValue(namespace, key, value);
				}
				else {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Session clicks has attempted to exceed the ",
								"maximum number of allowed values with {key=",
								key, ", value=", value, "}"));
					}
				}

				break;
			}
			catch (ConcurrentModificationException
						concurrentModificationException) {
			}
			catch (Exception exception) {
				_log.error(exception, exception);

				break;
			}
		}
	}

	public static void put(HttpSession session, String key, String value) {
		put(session, _DEFAULT_NAMESPACE, key, value);
	}

	public static void put(
		HttpSession session, String namespace, String key, String value) {

		String sessionKey = namespace.concat(
			StringPool.COLON
		).concat(
			key
		);

		session.setAttribute(sessionKey, value);
	}

	private static final String _DEFAULT_NAMESPACE =
		SessionClicks.class.getName();

	private static final int _SESSION_CLICKS_MAX_ALLOWED_VALUES =
		GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.SESSION_CLICKS_MAX_ALLOWED_VALUES));

	private static final int _SESSION_CLICKS_MAX_SIZE_TERMS =
		GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.SESSION_CLICKS_MAX_SIZE_TERMS));

	private static final Log _log = LogFactoryUtil.getLog(SessionClicks.class);

}