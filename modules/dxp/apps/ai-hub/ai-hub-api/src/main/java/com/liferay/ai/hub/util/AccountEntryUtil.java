/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.util;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;

import java.util.List;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public class AccountEntryUtil {

	public static AccountEntry getUserAccountEntry(long userId)
		throws Exception {

		List<AccountEntryUserRel> accountEntryUserRels =
			AccountEntryUserRelLocalServiceUtil.
				getAccountEntryUserRelsByAccountUserId(userId);

		if (accountEntryUserRels.size() != 2) {
			return null;
		}

		for (AccountEntryUserRel accountEntryUserRel : accountEntryUserRels) {
			AccountEntry accountEntry = accountEntryUserRel.getAccountEntry();

			if (!Objects.equals(
					accountEntry.getExternalReferenceCode(), "L_AI_HUB")) {

				return accountEntry;
			}
		}

		return null;
	}

}