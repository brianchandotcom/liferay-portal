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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.security.InvalidParameterException;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletInstance {

	public static final int PORTLET_INSTANCE_KEY_MAX_LENGTH =
		255 - PortletInstance._INSTANCE_SEPARATOR.length() +
			PortletInstance._USER_SEPARATOR.length() + 39;

	public static PortletInstance fromPortletInstanceKey(
		String portletInstanceKey) {

		return new PortletInstance(
			_getPortletName(portletInstanceKey), _getUserId(portletInstanceKey),
			_getInstanceId(portletInstanceKey));
	}

	public static PortletInstance fromPortletNameAndUserIdAndInstanceId(
		String portletName, String userIdAndInstanceId) {

		UserIdAndInstanceIdEncoder userIdAndInstanceIdEncoder =
			_buildUserIdAndInstanceIdEncoder(userIdAndInstanceId);

		return new PortletInstance(
			portletName, userIdAndInstanceIdEncoder.getUserId(),
			userIdAndInstanceIdEncoder.getInstanceId());
	}

	public PortletInstance(String portletName) {
		this(portletName, StringUtil.randomString(12));
	}

	public PortletInstance(String portletName, long userId) {
		this(portletName, userId, null);
	}

	public PortletInstance(String portletName, long userId, String instanceId) {
		_validatePortletName(portletName);

		_portletName = portletName;
		_userId = userId;
		_instanceId = instanceId;
	}

	public PortletInstance(String portletName, String instanceId) {
		this(portletName, 0, instanceId);
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public String getPortletInstanceKey() {
		StringBundler sb = new StringBundler(5);

		sb.append(_portletName);

		if (_userId > 0) {
			sb.append(_USER_SEPARATOR);
			sb.append(_userId);
		}

		if (Validator.isNotNull(_instanceId)) {
			sb.append(_INSTANCE_SEPARATOR);
			sb.append(_instanceId);
		}

		return sb.toString();
	}

	public String getPortletName() {
		return _portletName;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserIdAndInstanceId() {
		if ((_userId <= 0) && Validator.isBlank(_instanceId)) {
			return null;
		}

		StringBundler sb = new StringBundler(3);

		if (_userId > 0) {
			sb.append(_userId);
			sb.append(StringPool.UNDERLINE);
		}

		if (_instanceId != null) {
			sb.append(_instanceId);
		}

		return sb.toString();
	}

	public boolean hasIdenticalPortletName(PortletInstance portletInstance) {
		return hasIdenticalPortletName(portletInstance.getPortletName());
	}

	public boolean hasIdenticalPortletName(String portletName) {
		return _portletName.equals(portletName);
	}

	public boolean hasInstanceId() {
		return Validator.isNotNull(_instanceId);
	}

	public boolean hasUserId() {
		if (_userId > 0) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return getPortletInstanceKey();
	}

	private static UserIdAndInstanceIdEncoder _buildUserIdAndInstanceIdEncoder(
		String userIdAndInstanceId) {

		if (userIdAndInstanceId == null) {
			throw new InvalidParameterException(
				"User ID and instance ID are null");
		}

		if (userIdAndInstanceId.isEmpty()) {
			return new UserIdAndInstanceIdEncoder(0, null);
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

			return new UserIdAndInstanceIdEncoder(userId, instanceId);
		}

		return new UserIdAndInstanceIdEncoder(0, userIdAndInstanceId);
	}

	private static String _getInstanceId(String portletInstanceKey) {
		int index = portletInstanceKey.indexOf(_INSTANCE_SEPARATOR);

		if (index == -1) {
			return null;
		}

		return portletInstanceKey.substring(
			index + _INSTANCE_SEPARATOR.length());
	}

	private static String _getPortletName(String portletInstanceKey) {
		int x = portletInstanceKey.indexOf(_USER_SEPARATOR);
		int y = portletInstanceKey.indexOf(_INSTANCE_SEPARATOR);

		if ((x == -1) && (y == -1)) {
			return portletInstanceKey;
		}
		else if (x != -1) {
			return portletInstanceKey.substring(0, x);
		}

		return portletInstanceKey.substring(0, y);
	}

	private static long _getUserId(String portletInstanceKey) {
		int x = portletInstanceKey.indexOf(_USER_SEPARATOR);
		int y = portletInstanceKey.indexOf(_INSTANCE_SEPARATOR);

		if (x == -1) {
			return 0;
		}

		if (y != -1) {
			return GetterUtil.getLong(
				portletInstanceKey.substring(x + _USER_SEPARATOR.length(), y));
		}

		return GetterUtil.getLong(
			portletInstanceKey.substring(x + _USER_SEPARATOR.length()));
	}

	private void _validatePortletName(String portletName) {
		for (String keyword : _PORTLET_NAME_KEYWORDS) {
			if (!portletName.contains(keyword)) {
				continue;
			}

			throw new InvalidParameterException(
				"The portletName '" + portletName +
					"' must not contain the keyword " + keyword);
		}
	}

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

	private static final String[] _PORTLET_NAME_KEYWORDS =
		{PortletInstance._INSTANCE_SEPARATOR, PortletInstance._USER_SEPARATOR};

	private static final String _USER_SEPARATOR = "_USER_";

	private final String _instanceId;
	private final String _portletName;
	private final long _userId;

	private static final class UserIdAndInstanceIdEncoder {

		public UserIdAndInstanceIdEncoder(long userId, String instanceId) {
			_userId = userId;
			_instanceId = instanceId;
		}

		public String getInstanceId() {
			return _instanceId;
		}

		public long getUserId() {
			return _userId;
		}

		private String _instanceId;
		private long _userId;

	}

}