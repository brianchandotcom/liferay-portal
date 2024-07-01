/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.upgrade.v3_10_2;

import com.liferay.notification.internal.upgrade.BaseNotificationRecipientSettingUpgradeProcess;
import com.liferay.petra.string.StringPool;

/**
 * @author Pedro Leite
 */
public class NotificationRecipientSettingUpgradeProcess
	extends BaseNotificationRecipientSettingUpgradeProcess {

	@Override
	protected String getNotificationRecipientSettingName() {
		return "usePreferredLocaleForGuestUsers";
	}

	@Override
	protected String getNotificationRecipientSettingValue() {
		return StringPool.FALSE;
	}

}