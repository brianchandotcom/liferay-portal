/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.util.v2_0;

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.commerce.discount.service.CommerceDiscountAccountRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccount;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Riccardo Alberti
 */
public class DiscountAccountUtil {

	public static CommerceDiscountAccountRel addCommerceDiscountAccountRel(
			AccountEntryService accountEntryService,
			CommerceDiscountAccountRelService commerceDiscountAccountRelService,
			DiscountAccount discountAccount, CommerceDiscount commerceDiscount,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		AccountEntry accountEntry;

		if (Validator.isNull(
				discountAccount.getAccountExternalReferenceCode())) {

			accountEntry = accountEntryService.getAccountEntry(
				discountAccount.getAccountId());
		}
		else {
			accountEntry =
				accountEntryService.fetchAccountEntryByExternalReferenceCode(
					serviceContext.getCompanyId(),
					discountAccount.getAccountExternalReferenceCode());

			if (accountEntry == null) {
				throw new NoSuchEntryException(
					"Unable to find account with external reference code " +
						discountAccount.getAccountExternalReferenceCode());
			}
		}

		return commerceDiscountAccountRelService.addCommerceDiscountAccountRel(
			commerceDiscount.getCommerceDiscountId(),
			accountEntry.getAccountEntryId(), serviceContext);
	}

}