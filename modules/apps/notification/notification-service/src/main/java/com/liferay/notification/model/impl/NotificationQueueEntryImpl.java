/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.model.impl;

import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.service.NotificationRecipientLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Gabriel Albuquerque
 */
public class NotificationQueueEntryImpl extends NotificationQueueEntryBaseImpl {

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		try {
			return PortalUtil.getClassName(getClassNameId());
		}
		catch (RuntimeException runtimeException) {
			if (_log.isDebugEnabled()) {
				_log.debug(runtimeException);
			}

			return "";
		}
	}

	@Override
	public NotificationRecipient getNotificationRecipient() {
		return NotificationRecipientLocalServiceUtil.
			getNotificationRecipientByClassPK(getNotificationQueueEntryId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationQueueEntryImpl.class);

}