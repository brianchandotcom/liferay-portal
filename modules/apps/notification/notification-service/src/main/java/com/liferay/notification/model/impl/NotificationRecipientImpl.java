/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.model.impl;

import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.service.NotificationRecipientSettingLocalServiceUtil;

import java.util.List;

/**
 * @author Feliphe Marinho
 */
public class NotificationRecipientImpl extends NotificationRecipientBaseImpl {

	public List<NotificationRecipientSetting>
		getNotificationRecipientSettings() {

		return NotificationRecipientSettingLocalServiceUtil.
			getNotificationRecipientSettings(getNotificationRecipientId());
	}

}