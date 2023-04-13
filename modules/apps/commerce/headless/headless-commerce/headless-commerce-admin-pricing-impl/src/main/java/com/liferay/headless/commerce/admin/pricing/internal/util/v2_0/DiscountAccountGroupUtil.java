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

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupService;
import com.liferay.commerce.account.exception.NoSuchAccountGroupException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccountGroup;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Riccardo Alberti
 */
public class DiscountAccountGroupUtil {

	public static CommerceDiscountCommerceAccountGroupRel
			addCommerceDiscountAccountGroupRel(
				AccountGroupService accountGroupService,
				CommerceDiscountCommerceAccountGroupRelService
					commerceDiscountCommerceAccountGroupRelService,
				DiscountAccountGroup discountAccountGroup,
				CommerceDiscount commerceDiscount,
				ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		AccountGroup accountGroup;

		if (Validator.isNull(
				discountAccountGroup.getAccountGroupExternalReferenceCode())) {

			accountGroup = accountGroupService.getAccountGroup(
				discountAccountGroup.getAccountGroupId());
		}
		else {
			accountGroup =
				accountGroupService.fetchAccountGroupByExternalReferenceCode(
					discountAccountGroup.getAccountGroupExternalReferenceCode(),
					serviceContext.getCompanyId());

			if (accountGroup == null) {
				String accountGroupExternalReferenceCode =
					discountAccountGroup.getAccountGroupExternalReferenceCode();

				throw new NoSuchAccountGroupException(
					"Unable to find account group with external reference " +
						"code " + accountGroupExternalReferenceCode);
			}
		}

		return commerceDiscountCommerceAccountGroupRelService.
			addCommerceDiscountCommerceAccountGroupRel(
				commerceDiscount.getCommerceDiscountId(),
				accountGroup.getAccountGroupId(), serviceContext);
	}

}