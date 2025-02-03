/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.account.model.AccountEntry;
import com.liferay.headless.admin.user.dto.v1_0.AccountBrief;

/**
 * @author Crescenzo Rega
 */
public class AccountBriefUtil {

	public static AccountBrief toAccountBrief(AccountEntry accountEntry) {
		if (accountEntry == null) {
			return null;
		}

		return new AccountBrief() {
			{
				setExternalReferenceCode(
					accountEntry::getExternalReferenceCode);
				setId(accountEntry::getAccountEntryId);
				setName(accountEntry::getName);
			}
		};
	}

}