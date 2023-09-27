/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.internal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.SubscriptionSender;

/**
 * @author Brian I. Kim
 */
public class ReportsEngineConsoleSubscriptionSender extends SubscriptionSender {

	@Override
	protected void sendNotification(User user) throws Exception {
		sendEmailNotification(user);

		if (currentUserId == user.getUserId()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Skip notification for user " + currentUserId);
			}

			return;
		}

		sendUserNotification(user);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReportsEngineConsoleSubscriptionSender.class);

}