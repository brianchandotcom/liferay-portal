/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.util.v2_0;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel;
import com.liferay.commerce.discount.service.CommerceDiscountOrderTypeRelService;
import com.liferay.commerce.exception.NoSuchOrderTypeException;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountOrderType;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class DiscountOrderTypeUtil {

	public static CommerceDiscountOrderTypeRel addCommerceDiscountOrderTypeRel(
			CommerceDiscount commerceDiscount,
			CommerceDiscountOrderTypeRelService
				commerceDiscountOrderTypeRelService,
			CommerceOrderTypeService commerceOrderTypeService,
			DiscountOrderType discountOrderType,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		CommerceOrderType commerceOrderType = null;

		if (Validator.isNull(
				discountOrderType.getOrderTypeExternalReferenceCode())) {

			commerceOrderType = commerceOrderTypeService.getCommerceOrderType(
				discountOrderType.getOrderTypeId());
		}
		else {
			commerceOrderType =
				commerceOrderTypeService.fetchByExternalReferenceCode(
					discountOrderType.getOrderTypeExternalReferenceCode(),
					serviceContext.getCompanyId());

			if (commerceOrderType == null) {
				String orderTypeExternalReferenceCode =
					discountOrderType.getOrderTypeExternalReferenceCode();

				throw new NoSuchOrderTypeException(
					"Unable to find order type with external reference code " +
						orderTypeExternalReferenceCode);
			}
		}

		return commerceDiscountOrderTypeRelService.
			addCommerceDiscountOrderTypeRel(
				commerceDiscount.getCommerceDiscountId(),
				commerceOrderType.getCommerceOrderTypeId(),
				GetterUtil.get(discountOrderType.getPriority(), 0),
				serviceContext);
	}

}