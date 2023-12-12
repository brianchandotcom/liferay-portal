/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.exception;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Set;

/**
 * @author Gabriel Albuquerque
 */
public class NotificationRecipientSettingNameException extends PortalException {

	public static class NotAllowedNames
		extends NotificationRecipientSettingNameException {

		public NotAllowedNames(Set<String> notificationRecipientSettingsNames) {
			super(
				String.format(
					"The settings %s are not allowed",
					StringUtil.merge(
						notificationRecipientSettingsNames,
						StringPool.COMMA_AND_SPACE)));
		}

	}

	private NotificationRecipientSettingNameException(String msg) {
		super(msg);
	}

}