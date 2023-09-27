/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.util.v1_0;

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccount;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class OrderRuleAccountUtil {

	public static COREntryRel addCOREntryAccountRel(
			AccountEntryService accountEntryService,
			COREntryRelService corEntryRelService, COREntry corEntry,
			OrderRuleAccount orderRuleAccount)
		throws PortalException {

		AccountEntry accountEntry = null;

		if (Validator.isNull(
				orderRuleAccount.getAccountExternalReferenceCode())) {

			accountEntry = accountEntryService.getAccountEntry(
				orderRuleAccount.getAccountId());
		}
		else {
			accountEntry =
				accountEntryService.fetchAccountEntryByExternalReferenceCode(
					corEntry.getCompanyId(),
					orderRuleAccount.getAccountExternalReferenceCode());

			if (accountEntry == null) {
				String accountExternalReferenceCode =
					orderRuleAccount.getAccountExternalReferenceCode();

				throw new NoSuchEntryException(
					"Unable to find account with external reference code " +
						accountExternalReferenceCode);
			}
		}

		return corEntryRelService.addCOREntryRel(
			AccountEntry.class.getName(), accountEntry.getAccountEntryId(),
			corEntry.getCOREntryId());
	}

}