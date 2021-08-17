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

import com.liferay.commerce.exception.NoSuchOrderTypeException;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
import com.liferay.commerce.price.list.service.CommercePriceListOrderTypeRelService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListOrderType;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class PriceListOrderTypeUtil {

	public static CommercePriceListOrderTypeRel
			addCommercePriceListOrderTypeRel(
				CommerceOrderTypeService commerceOrderTypeService,
				CommercePriceListOrderTypeRelService
					commercePriceListOrderTypeRelService,
				PriceListOrderType priceListOrderType,
				CommercePriceList commercePriceList,
				ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext = serviceContextHelper.getServiceContext(
			commercePriceList.getGroupId());

		CommerceOrderType commerceOrderType;

		if (Validator.isNull(
				priceListOrderType.getOrderTypeExternalReferenceCode())) {

			commerceOrderType = commerceOrderTypeService.getCommerceOrderType(
				priceListOrderType.getOrderTypeId());
		}
		else {
			commerceOrderType =
				commerceOrderTypeService.fetchByExternalReferenceCode(
					priceListOrderType.getOrderTypeExternalReferenceCode(),
					serviceContext.getCompanyId());

			if (commerceOrderType == null) {
				String orderTypeExternalReferenceCode =
					priceListOrderType.getOrderTypeExternalReferenceCode();

				throw new NoSuchOrderTypeException(
					"Unable to find order type with external reference code " +
						orderTypeExternalReferenceCode);
			}
		}

		return commercePriceListOrderTypeRelService.
			addCommercePriceListOrderTypeRel(
				commercePriceList.getCommercePriceListId(),
				commerceOrderType.getCommerceOrderTypeId(),
				GetterUtil.get(priceListOrderType.getPriority(), 0),
				serviceContext);
	}

}