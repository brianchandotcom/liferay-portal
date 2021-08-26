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

import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
import com.liferay.commerce.price.list.service.CommercePriceListDiscountRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListDiscount;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Riccardo Alberti
 */
public class PriceListDiscountUtil {

	public static CommercePriceListDiscountRel addCommercePriceListDiscountRel(
			CommerceDiscountService commerceDiscountService,
			CommercePriceListDiscountRelService
				commercePriceListDiscountRelService,
			PriceListDiscount priceListDiscount,
			CommercePriceList commercePriceList,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext = serviceContextHelper.getServiceContext(
			commercePriceList.getGroupId());

		CommerceDiscount commerceDiscount;

		if (Validator.isNull(
				priceListDiscount.getDiscountExternalReferenceCode())) {

			commerceDiscount = commerceDiscountService.getCommerceDiscount(
				priceListDiscount.getDiscountId());
		}
		else {
			commerceDiscount =
				commerceDiscountService.fetchByExternalReferenceCode(
					priceListDiscount.getDiscountExternalReferenceCode(),
					serviceContext.getCompanyId());

			if (commerceDiscount == null) {
				throw new NoSuchDiscountException(
					"Unable to find discount with external reference code " +
						priceListDiscount.getDiscountExternalReferenceCode());
			}
		}

		return commercePriceListDiscountRelService.
			addCommercePriceListDiscountRel(
				commercePriceList.getCommercePriceListId(),
				commerceDiscount.getCommerceDiscountId(),
				GetterUtil.get(priceListDiscount.getOrder(), 0),
				serviceContext);
	}

}