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

import com.liferay.commerce.exception.NoSuchOrderTypeException;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleOrderType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class OrderRuleOrderTypeUtil {

	public static COREntryRel addCOREntryCommerceOrderTypeRel(
			COREntryRelService corEntryRelService, COREntry corEntry,
			CommerceOrderTypeService commerceOrderTypeService,
			OrderRuleOrderType orderRuleOrderType)
		throws PortalException {

		CommerceOrderType commerceOrderType;

		if (Validator.isNull(
				orderRuleOrderType.getOrderTypeExternalReferenceCode())) {

			commerceOrderType = commerceOrderTypeService.getCommerceOrderType(
				orderRuleOrderType.getOrderTypeId());
		}
		else {
			commerceOrderType =
				commerceOrderTypeService.fetchByExternalReferenceCode(
					orderRuleOrderType.getOrderTypeExternalReferenceCode(),
					corEntry.getCompanyId());

			if (commerceOrderType == null) {
				String orderTypeExternalReferenceCode =
					orderRuleOrderType.getOrderTypeExternalReferenceCode();

				throw new NoSuchOrderTypeException(
					"Unable to find order type with external reference code " +
						orderTypeExternalReferenceCode);
			}
		}

		return corEntryRelService.addCOREntryRel(
			CommerceOrderType.class.getName(),
			commerceOrderType.getCommerceOrderTypeId(),
			corEntry.getCOREntryId());
	}

}