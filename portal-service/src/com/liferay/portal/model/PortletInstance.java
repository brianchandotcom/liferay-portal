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
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletInstance {

	public PortletInstance(String portletInstanceId) {
		this(
			_getRootPortletId(portletInstanceId), _getUserId(portletInstanceId),
			_getInstanceId(portletInstanceId));
	}

	public PortletInstance(String rootPortletId, long userId) {
		this(rootPortletId, userId, null);
	}

	public PortletInstance(
		String rootPortletId, long userId, String instanceId) {

		_rootPortletId = rootPortletId;
		_instanceId = instanceId;
		_userId = userId;
	}

	public PortletInstance(String rootPortletId, String instanceId) {
		this(rootPortletId, 0, instanceId);
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public String getRootPortletId() {
		return _rootPortletId;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean hasIdenticalRootPortletId(PortletInstance portletInstance) {
		return hasIdenticalRootPortletId(portletInstance.getRootPortletId());
	}

	public boolean hasIdenticalRootPortletId(String rootPortletId) {
		return _rootPortletId.equals(rootPortletId);
	}

	public boolean hasInstanceId() {
		return Validator.isNotNull(_instanceId);
	}

	public boolean hasUserId() {
		return (_userId > 0);
	}

	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append(_rootPortletId);

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

	private static String _getInstanceId(String portletInstance) {
		int pos = portletInstance.indexOf(_INSTANCE_SEPARATOR);

		if (pos == -1) {
			return null;
		}

		return portletInstance.substring(pos + _INSTANCE_SEPARATOR.length());
	}

	private static String _getRootPortletId(String portletInstance) {
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

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

	private static final String _USER_SEPARATOR = "_USER_";

	private final String _instanceId;
	private final String _rootPortletId;
	private final long _userId;

}