/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		CommerceOrderType commerceOrderType = null;

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