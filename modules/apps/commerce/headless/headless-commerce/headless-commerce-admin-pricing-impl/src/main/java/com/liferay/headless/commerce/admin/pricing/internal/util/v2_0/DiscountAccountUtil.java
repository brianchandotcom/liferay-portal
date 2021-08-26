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

package com.liferay.headless.commerce.admin.pricing.internal.util.v2_0;

import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
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
			CommerceAccountService commerceAccountService,
			CommerceDiscountAccountRelService commerceDiscountAccountRelService,
			DiscountAccount discountAccount, CommerceDiscount commerceDiscount,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		CommerceAccount commerceAccount;

		if (Validator.isNull(
				discountAccount.getAccountExternalReferenceCode())) {

			commerceAccount = commerceAccountService.getCommerceAccount(
				discountAccount.getAccountId());
		}
		else {
			commerceAccount =
				commerceAccountService.fetchByExternalReferenceCode(
					serviceContext.getCompanyId(),
					discountAccount.getAccountExternalReferenceCode());

			if (commerceAccount == null) {
				throw new NoSuchAccountException(
					"Unable to find account with external reference code " +
						discountAccount.getAccountExternalReferenceCode());
			}
		}

		return commerceDiscountAccountRelService.addCommerceDiscountAccountRel(
			commerceDiscount.getCommerceDiscountId(),
			commerceAccount.getCommerceAccountId(), serviceContext);
	}

}