/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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