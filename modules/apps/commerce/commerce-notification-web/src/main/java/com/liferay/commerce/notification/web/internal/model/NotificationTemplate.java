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

package com.liferay.commerce.notification.web.internal.model;

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Alessio Antonio Rendina
 */
public class NotificationTemplate {

	public NotificationTemplate(
		LabelField enabled, String name, long notificationTemplateId,
		String type) {

		_enabled = enabled;
		_name = name;
		_notificationTemplateId = notificationTemplateId;
		_type = type;
	}

	public LabelField getEnabled() {
		return _enabled;
	}

	public String getName() {
		return _name;
	}

	public long getNotificationTemplateId() {
		return _notificationTemplateId;
	}

	public String getType() {
		return _type;
	}

	private final LabelField _enabled;
	private final String _name;
	private final long _notificationTemplateId;
	private final String _type;

}