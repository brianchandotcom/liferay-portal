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

package com.liferay.portal.kernel.notifications;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jonathan Lee
 */
public class UserNotificationDefinition {

	public UserNotificationDefinition(
		String portletId, long classNameId, int notificationType,
		String description) {

		_classNameId = classNameId;
		_deliveryTypes = new HashMap<Integer, UserNotificationDeliveryType>();
		_description = description;
		_notificationType = notificationType;
		_portletId = portletId;
	}

	public void addUserNotificationDeliveryType(
		UserNotificationDeliveryType userNotificationDeliveryType) {

		_deliveryTypes.put(
			userNotificationDeliveryType.getType(),
			userNotificationDeliveryType);
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public String getDescription() {
		return _description;
	}

	public int getNotificationType() {
		return _notificationType;
	}

	public String getPortletId() {
		return _portletId;
	}

	public Map<Integer, UserNotificationDeliveryType>
		getDeliveryTypes() {

		return _deliveryTypes;
	}

	public boolean hasDeliveryType(int type) {
		return _deliveryTypes.containsKey(type);
	}

	private long _classNameId;
	private Map<Integer, UserNotificationDeliveryType> _deliveryTypes;
	private String _description;
	private int _notificationType;
	private String _portletId;

}
