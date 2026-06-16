/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.util;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public class AccountEntryUtil {

	public static AccountEntry getUserAccountEntry(long userId)
		throws PortalException {

		List<AccountEntryUserRel> accountEntryUserRels =
			AccountEntryUserRelLocalServiceUtil.
				getAccountEntryUserRelsByAccountUserId(userId);

		if ((accountEntryUserRels.size() != 2) &&
			(accountEntryUserRels.size() != 3)) {

			return null;
		}

		AccountEntry customerAccountEntry = null;
		boolean hasAIHubAccountEntry = false;
		boolean hasSEOStudioAccountEntry = false;

		for (AccountEntryUserRel accountEntryUserRel : accountEntryUserRels) {
			AccountEntry accountEntry = accountEntryUserRel.getAccountEntry();

			String externalReferenceCode =
				accountEntry.getExternalReferenceCode();

			if (Objects.equals(externalReferenceCode, "L_AI_HUB")) {
				hasAIHubAccountEntry = true;
			}
			else if (Objects.equals(externalReferenceCode, "L_SEO_STUDIO")) {
				hasSEOStudioAccountEntry = true;
			}
			else {
				customerAccountEntry = accountEntry;
			}
		}

		if (((accountEntryUserRels.size() == 2) &&
			 (!hasAIHubAccountEntry || hasSEOStudioAccountEntry)) ||
			((accountEntryUserRels.size() == 3) &&
			 (!hasAIHubAccountEntry || !hasSEOStudioAccountEntry))) {

			return null;
		}

		return customerAccountEntry;
	}

	public static long getUserAccountEntryGroupId(long userId)
		throws Exception {

		AccountEntry accountEntry = getUserAccountEntry(userId);

		return accountEntry.getAccountEntryGroupId();
	}

}