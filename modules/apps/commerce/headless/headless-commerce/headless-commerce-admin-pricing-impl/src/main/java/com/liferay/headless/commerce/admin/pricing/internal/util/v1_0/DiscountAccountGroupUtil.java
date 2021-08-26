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

package com.liferay.headless.commerce.admin.pricing.internal.util.v1_0;

import com.liferay.commerce.account.exception.NoSuchAccountGroupException;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountAccountGroup;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class DiscountAccountGroupUtil {

	public static CommerceDiscountCommerceAccountGroupRel
			addCommerceDiscountCommerceAccountGroupRel(
				CommerceAccountGroupService commerceAccountGroupService,
				CommerceDiscountCommerceAccountGroupRelService
					commerceDiscountCommerceAccountGroupRelService,
				DiscountAccountGroup discountAccountGroup,
				CommerceDiscount commerceDiscount,
				ServiceContext serviceContext)
		throws PortalException {

		CommerceAccountGroup commerceAccountGroup;

		if (Validator.isNull(
				discountAccountGroup.getAccountGroupExternalReferenceCode())) {

			commerceAccountGroup =
				commerceAccountGroupService.getCommerceAccountGroup(
					discountAccountGroup.getAccountGroupId());
		}
		else {
			commerceAccountGroup =
				commerceAccountGroupService.fetchByExternalReferenceCode(
					serviceContext.getCompanyId(),
					discountAccountGroup.
						getAccountGroupExternalReferenceCode());

			if (commerceAccountGroup == null) {
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
				commerceAccountGroup.getCommerceAccountGroupId(),
				serviceContext);
	}

}