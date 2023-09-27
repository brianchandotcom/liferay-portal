/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.model.FaroNotification;

/**
 * @author Geyson Silva
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class NotificationDisplay {

	public NotificationDisplay() {
	}

	public NotificationDisplay(FaroNotification faroNotification) {
		_id = faroNotification.getFaroNotificationId();
		_modifiedTime = faroNotification.getModifiedTime();
		_subtype = faroNotification.getSubtype();
		_type = faroNotification.getType();
	}

	private long _id;
	private long _modifiedTime;
	private String _subtype;
	private String _type;

}