/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

	public static COREntryRel addCOREntryCommerceAccountRel(
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