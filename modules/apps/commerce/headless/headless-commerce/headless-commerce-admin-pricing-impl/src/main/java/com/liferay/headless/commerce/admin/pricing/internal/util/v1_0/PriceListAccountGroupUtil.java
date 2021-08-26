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
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.PriceListAccountGroup;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class PriceListAccountGroupUtil {

	public static CommercePriceListCommerceAccountGroupRel
			addCommercePriceListCommerceAccountGroupRel(
				CommerceAccountGroupService commerceAccountGroupService,
				CommercePriceListCommerceAccountGroupRelService
					commercePriceListCommerceAccountGroupRelService,
				PriceListAccountGroup priceListAccountGroup,
				CommercePriceList commercePriceList,
				ServiceContext serviceContext)
		throws PortalException {

		CommerceAccountGroup commerceAccountGroup;

		if (Validator.isNull(
				priceListAccountGroup.getAccountGroupExternalReferenceCode())) {

			commerceAccountGroup =
				commerceAccountGroupService.getCommerceAccountGroup(
					priceListAccountGroup.getAccountGroupId());
		}
		else {
			commerceAccountGroup =
				commerceAccountGroupService.fetchByExternalReferenceCode(
					serviceContext.getCompanyId(),
					priceListAccountGroup.
						getAccountGroupExternalReferenceCode());

			if (commerceAccountGroup == null) {
				String accountGroupExternalReferenceCode =
					priceListAccountGroup.
						getAccountGroupExternalReferenceCode();

				throw new NoSuchAccountGroupException(
					"Unable to find account group with external reference " +
						"code " + accountGroupExternalReferenceCode);
			}
		}

		return commercePriceListCommerceAccountGroupRelService.
			addCommercePriceListCommerceAccountGroupRel(
				commercePriceList.getCommercePriceListId(),
				commerceAccountGroup.getCommerceAccountGroupId(),
				GetterUtil.get(priceListAccountGroup.getOrder(), 0),
				serviceContext);
	}

}