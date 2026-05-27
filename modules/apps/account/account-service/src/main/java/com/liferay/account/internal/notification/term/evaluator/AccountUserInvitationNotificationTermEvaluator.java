/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.notification.term.evaluator;

import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stefano Motta
 */
@Component(
	property = {
		"class.name=com.liferay.account.model.AccountEntry",
		"service.ranking:Integer=1"
	},
	service = NotificationTermEvaluator.class
)
public class AccountUserInvitationNotificationTermEvaluator
	implements NotificationTermEvaluator {

	@Override
	public String evaluate(
			Context context, NotificationContext notificationContext,
			String termName)
		throws PortalException {

		Map<String, Object> termValues = notificationContext.getTermValues();

		if (!termValues.containsKey("currentUserId") &&
			(notificationContext.getUserId() > 0)) {

			termValues.put("currentUserId", notificationContext.getUserId());
		}

		return evaluate(context, termValues, termName);
	}

	@Override
	public String evaluate(Context context, Object object, String termName)
		throws PortalException {

		if (!(object instanceof Map) ||
			!ArrayUtil.contains(_TERM_NAMES, termName)) {

			return termName;
		}

		Map<String, Object> termValues = (Map<String, Object>)object;

		Object value = termValues.get(termName);

		if (value == null) {
			return termName;
		}

		return value.toString();
	}

	private static final String[] _TERM_NAMES = {
		"[%ACCOUNT_NAME%]", "[%CREATE_ACCOUNT_URL%]", "[%INVITE_SENDER_NAME%]",
		"[%TO%]"
	};

}