/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.util;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public class AccountEntryUtil {

	public static AccountEntry getUserAccountEntry(long userId)
		throws Exception {

		List<AccountEntry> accountEntries =
			AccountEntryLocalServiceUtil.getUserAccountEntries(
				userId, AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
				AccountConstants.ACCOUNT_ENTRY_TYPES_DEFAULT_ALLOWED_TYPES,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		if (accountEntries.isEmpty()) {
			return null;
		}

		for (AccountEntry accountEntry : accountEntries) {
			if (!Objects.equals(
					accountEntry.getExternalReferenceCode(), "L_AI_HUB")) {

				return accountEntry;
			}
		}

		return null;
	}

}