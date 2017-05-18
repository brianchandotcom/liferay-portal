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

import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletInstance {

	public static final int PORTLET_INSTANCE_KEY_MAX_LENGTH =
		PortletIdCodec.PORTLET_INSTANCE_KEY_MAX_LENGTH;

	public static PortletInstance fromPortletInstanceKey(
		String portletInstanceKey) {

		return new PortletInstance(
			_getPortletName(portletInstanceKey), _getUserId(portletInstanceKey),
			_getInstanceId(portletInstanceKey));
	}

	public static PortletInstance fromPortletNameAndUserIdAndInstanceId(
		String portletName, String userIdAndInstanceId) {

		ObjectValuePair<Long, String> objectValuePair =
			_decodeUserIdAndInstanceId(userIdAndInstanceId);

		return new PortletInstance(
			portletName, objectValuePair.getKey(), objectValuePair.getValue());
	}

	public PortletInstance(String portletName) {
		this(portletName, PortletIdCodec.generateInstanceId());
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
		return PortletIdCodec.encode(_portletName, _userId, _instanceId);
	}

	public String getPortletName() {
		return _portletName;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserIdAndInstanceId() {
		return PortletIdCodec.encodeUserIdAndInstanceId(_userId, _instanceId);
	}

	public boolean hasIdenticalPortletName(PortletInstance portletInstance) {
		return hasIdenticalPortletName(portletInstance.getPortletName());
	}

	public boolean hasIdenticalPortletName(String portletName) {
		return PortletIdCodec.hasIdenticalPortletName(
			_portletName, portletName);
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

	private static ObjectValuePair<Long, String>
		_decodeUserIdAndInstanceId(String userIdAndInstanceId) {

		return PortletIdCodec.decodeUserIdAndInstanceId(userIdAndInstanceId);
	}

	private static String _getInstanceId(String portletInstanceKey) {
		return PortletIdCodec.decodeInstanceId(portletInstanceKey);
	}

	private static String _getPortletName(String portletInstanceKey) {
		return PortletIdCodec.decodePortletName(portletInstanceKey);
	}

	private static long _getUserId(String portletInstanceKey) {
		return PortletIdCodec.decodeUserId(portletInstanceKey);
	}

	private void _validatePortletName(String portletName) {
		PortletIdCodec.validatePortletName(portletName);
	}

	private final String _instanceId;
	private final String _portletName;
	private final long _userId;

}