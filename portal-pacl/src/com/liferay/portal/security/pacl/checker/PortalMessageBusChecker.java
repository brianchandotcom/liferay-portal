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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalMessageBusPermission;

import java.security.Permission;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalMessageBusChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initListenDestinationNames();
		initSendDestinationNames();
	}

	@Override
	public AuthorizationProperty generateAuthorizationProperty(
		Object... arguments) {

		if ((arguments == null) || (arguments.length != 1) ||
			!(arguments[0] instanceof Permission)) {

			return null;
		}

		PortalMessageBusPermission portalMessageBusPermission =
			(PortalMessageBusPermission)arguments[0];

		String name = portalMessageBusPermission.getName();

		String key = null;

		if (name.equals(PORTAL_MESSAGE_BUS_PERMISSION_LISTEN)) {
			key = "security-manager-message-bus-listen";
		}
		else if (name.equals(PORTAL_MESSAGE_BUS_PERMISSION_SEND)) {
			key = "security-manager-message-bus-send";
		}
		else {
			return null;
		}

		AuthorizationProperty authorizationProperty =
			new AuthorizationProperty();

		authorizationProperty.setKey(key);
		authorizationProperty.setValue(
			portalMessageBusPermission.getDestinationName());

		return authorizationProperty;
	}

	public boolean implies(Permission permission) {
		PortalMessageBusPermission portalMessageBusPermission =
			(PortalMessageBusPermission)permission;

		String name = portalMessageBusPermission.getName();
		String destinationName =
			portalMessageBusPermission.getDestinationName();

		if (name.equals(PORTAL_MESSAGE_BUS_PERMISSION_LISTEN)) {
			if (!_listenDestinationNames.contains(destinationName)) {
				logSecurityException(
					_log,
					"Attempted to listen on destination " + destinationName);

				return false;
			}
		}
		else if (name.equals(PORTAL_MESSAGE_BUS_PERMISSION_SEND)) {
			if (!_sendDestinationNames.contains(destinationName)) {
				logSecurityException(
					_log, "Attempted to send to " + destinationName);

				return false;
			}
		}

		return true;
	}

	protected void initListenDestinationNames() {
		_listenDestinationNames = getPropertySet(
			"security-manager-message-bus-listen");

		if (_log.isDebugEnabled()) {
			Set<String> destinationNames = new TreeSet<String>(
				_listenDestinationNames);

			for (String destinationName : destinationNames) {
				_log.debug(
					"Allowing message listeners to listen on destination " +
						destinationName);
			}
		}
	}

	protected void initSendDestinationNames() {
		_sendDestinationNames = getPropertySet(
			"security-manager-message-bus-send");

		if (_log.isDebugEnabled()) {
			Set<String> destinationNames = new TreeSet<String>(
				_sendDestinationNames);

			for (String destinationName : destinationNames) {
				_log.debug(
					"Allowing the message bus to send to destination " +
						destinationName);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalMessageBusChecker.class);

	private Set<String> _listenDestinationNames;
	private Set<String> _sendDestinationNames;

}