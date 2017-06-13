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
package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.security.InvalidParameterException;

/**
 * @author Tina Tian
 */
public class PortletIdCodec {

	public static final int PORTLET_INSTANCE_KEY_MAX_LENGTH =
		255 - PortletIdCodec._INSTANCE_SEPARATOR.length() +
			PortletIdCodec._USER_SEPARATOR.length() + 39;

	public static String decodeInstanceId(String portletId) {
		int index = portletId.indexOf(_INSTANCE_SEPARATOR);

		if (index == -1) {
			return null;
		}

		return portletId.substring(index + _INSTANCE_SEPARATOR.length());
	}

	public static String decodePortletName(String portletId) {
		int x = portletId.indexOf(_USER_SEPARATOR);
		int y = portletId.indexOf(_INSTANCE_SEPARATOR);

		if ((x == -1) && (y == -1)) {
			return portletId;
		}

		if (x != -1) {
			return portletId.substring(0, x);
		}

		return portletId.substring(0, y);
	}

	public static long decodeUserId(String portletId) {
		int x = portletId.indexOf(_USER_SEPARATOR);
		int y = portletId.indexOf(_INSTANCE_SEPARATOR);

		if (x == -1) {
			return 0;
		}

		if (y != -1) {
			return GetterUtil.getLong(
				portletId.substring(x + _USER_SEPARATOR.length(), y));
		}

		return GetterUtil.getLong(
			portletId.substring(x + _USER_SEPARATOR.length()));
	}

	public static ObjectValuePair<Long, String>
		decodeUserIdAndInstanceId(String userIdAndInstanceId) {

		if (userIdAndInstanceId == null) {
			throw new InvalidParameterException(
				"User ID and instance ID are null");
		}

		if (userIdAndInstanceId.isEmpty()) {
			return new ObjectValuePair<>(0L, null);
		}

		int slashCount = StringUtil.count(userIdAndInstanceId, CharPool.SLASH);

		if (slashCount > 0) {
			throw new InvalidParameterException(
				"User ID and instance ID contain slashes");
		}

		int underlineCount = StringUtil.count(
			userIdAndInstanceId, CharPool.UNDERLINE);

		if (underlineCount > 1) {
			throw new InvalidParameterException(
				"User ID and instance ID has more than one underscore");
		}

		if (underlineCount == 1) {
			int index = userIdAndInstanceId.indexOf(CharPool.UNDERLINE);

			long userId = GetterUtil.getLong(
				userIdAndInstanceId.substring(0, index), -1);

			if (userId == -1) {
				throw new InvalidParameterException("User ID is not a number");
			}

			String instanceId = null;

			if (index < (userIdAndInstanceId.length() - 1)) {
				instanceId = userIdAndInstanceId.substring(index + 1);
			}

			return new ObjectValuePair<>(userId, instanceId);
		}

		return new ObjectValuePair<>(0L, userIdAndInstanceId);
	}

	public static String encode(String portletName) {
		return encode(portletName, 0, StringUtil.randomString(12));
	}

	public static String encode(String portletName, long userId) {
		return encode(portletName, userId, null);
	}

	public static String encode(
		String portletName, long userId, String instanceId) {

		StringBundler sb = new StringBundler(5);

		sb.append(portletName);

		if (userId > 0) {
			sb.append(_USER_SEPARATOR);
			sb.append(userId);
		}

		if (Validator.isNotNull(instanceId)) {
			sb.append(_INSTANCE_SEPARATOR);
			sb.append(instanceId);
		}

		return sb.toString();
	}

	public static String encode(String portletName, String instanceId) {
		return encode(portletName, 0, instanceId);
	}

	public static String encodeUserIdAndInstanceId(
		long userId, String instanceId) {

		if ((userId <= 0) && Validator.isBlank(instanceId)) {
			return null;
		}

		StringBundler sb = new StringBundler(3);

		if (userId > 0) {
			sb.append(userId);
			sb.append(StringPool.UNDERLINE);
		}

		if (instanceId != null) {
			sb.append(instanceId);
		}

		return sb.toString();
	}

	public static String generateInstanceId() {
		return StringUtil.randomString(12);
	}

	public static boolean hasInstanceId(String portletId) {
		return portletId.contains(_INSTANCE_SEPARATOR);
	}

	public static boolean hasUserId(String portletId) {
		return portletId.contains(_USER_SEPARATOR);
	}

	public static void validatePortletName(String portletName) {
		String keyword = null;

		if (portletName.contains(_INSTANCE_SEPARATOR)) {
			keyword = _INSTANCE_SEPARATOR;
		}
		else if (portletName.contains(_USER_SEPARATOR)) {
			keyword = _USER_SEPARATOR;
		}

		if (keyword != null) {
			throw new InvalidParameterException(
				"The portletName '" + portletName +
					"' must not contain the keyword " + keyword);
		}
	}

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

	private static final String _USER_SEPARATOR = "_USER_";

}