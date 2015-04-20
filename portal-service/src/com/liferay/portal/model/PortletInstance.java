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

package com.liferay.portal.model;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.security.InvalidParameterException;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletInstance {

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

	private static final String _USER_SEPARATOR = "_USER_";

	public static final int PORTLET_INSTANCE_KEY_MAX_LENGTH =
		255 - _INSTANCE_SEPARATOR.length() + _USER_SEPARATOR.length() + 39;

	public static PortletInstance newInstanceFor(String portletName) {
		return new PortletInstance(portletName, StringUtil.randomString(12));
	}

	public PortletInstance(String portletInstanceId) {
		this(
			_getPortletName(portletInstanceId), _getUserId(portletInstanceId),
			_getInstanceId(portletInstanceId));
	}

	public PortletInstance(String portletName, long userId) {
		this(portletName, userId, null);
	}

	public PortletInstance(String portletName, long userId, String instanceId) {
		validatePortletName(portletName);

		_portletName = portletName;
		_userId = userId;
		_instanceId = instanceId;
	}

	private void validatePortletName(String portletName) {
		for (String keyword : _PORTLET_NAME_RESERVED_KEYWORDS) {
			if (portletName.indexOf(keyword) != -1) {
				throw new InvalidParameterException(
					"The portletName '" + portletName +
						"' must not contain the keyword " + keyword);
			}
		}
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
		return (_userId > 0);
	}

	public String toString() {
		return getPortletInstanceKey();
	}

	private static String _getInstanceId(String portletInstance) {
		int pos = portletInstance.indexOf(_INSTANCE_SEPARATOR);

		if (pos == -1) {
			return null;
		}

		return portletInstance.substring(pos + _INSTANCE_SEPARATOR.length());
	}

	private static String _getPortletName(String portletInstance) {
		int x = portletInstance.indexOf(_USER_SEPARATOR);
		int y = portletInstance.indexOf(_INSTANCE_SEPARATOR);

		if ((x == -1) && (y == -1)) {
			return portletInstance;
		}
		else if (x != -1) {
			return portletInstance.substring(0, x);
		}

		return portletInstance.substring(0, y);
	}

	private static long _getUserId(String portletInstance) {
		int x = portletInstance.indexOf(_USER_SEPARATOR);
		int y = portletInstance.indexOf(_INSTANCE_SEPARATOR);

		if (x == -1) {
			return 0;
		}

		if (y != -1) {
			return GetterUtil.getLong(
				portletInstance.substring(x + _USER_SEPARATOR.length(), y));
		}

		return GetterUtil.getLong(
			portletInstance.substring(x + _USER_SEPARATOR.length()));
	}

	private final String[] _PORTLET_NAME_RESERVED_KEYWORDS =
		new String[] {_INSTANCE_SEPARATOR, _USER_SEPARATOR};

	private final String _instanceId;
	private final String _portletName;
	private final long _userId;

}