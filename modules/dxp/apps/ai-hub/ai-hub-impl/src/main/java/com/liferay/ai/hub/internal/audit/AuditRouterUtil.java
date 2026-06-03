/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.audit;

import com.liferay.account.model.AccountEntry;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Date;

/**
 * @author Feliphe Marinho
 * @author Pedro Leite
 */
public class AuditRouterUtil {

	public static void route(
			String className, long classPK, Date createDate, String eventType,
			JSONObject jsonObject, long userId)
		throws Exception {

		AccountEntry accountEntry = AccountEntryUtil.getUserAccountEntry(
			userId);

		for (String key : jsonObject.keySet()) {
			if (jsonObject.get(key) instanceof String value) {
				jsonObject.put(key, _truncate(value));
			}
		}

		com.liferay.portal.kernel.audit.AuditRouterUtil.route(
			new AuditMessage(
				0, accountEntry.getCompanyId(), userId,
				PortalUtil.getUserName(userId, StringPool.BLANK), createDate,
				accountEntry.getAccountEntryId(), jsonObject, className,
				String.valueOf(classPK), null, eventType, null));
	}

	private static String _truncate(String string) {
		if (string.length() > 200) {
			return string.substring(0, 200);
		}

		return string;
	}

}