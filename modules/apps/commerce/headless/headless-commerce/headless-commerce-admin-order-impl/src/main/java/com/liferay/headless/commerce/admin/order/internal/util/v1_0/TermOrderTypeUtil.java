/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.util.v1_0;

import com.liferay.commerce.exception.NoSuchOrderTypeException;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.commerce.term.service.CommerceTermEntryRelService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.TermOrderType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class TermOrderTypeUtil {

	public static CommerceTermEntryRel addCommerceTermEntryCommerceOrderTypeRel(
			CommerceOrderTypeService commerceOrderTypeService,
			CommerceTermEntry commerceTermEntry,
			CommerceTermEntryRelService commerceTermEntryRelService,
			TermOrderType termOrderType)
		throws PortalException {

		CommerceOrderType commerceOrderType = null;

		if (Validator.isNull(
				termOrderType.getOrderTypeExternalReferenceCode())) {

			commerceOrderType = commerceOrderTypeService.getCommerceOrderType(
				termOrderType.getOrderTypeId());
		}
		else {
			commerceOrderType =
				commerceOrderTypeService.fetchByExternalReferenceCode(
					termOrderType.getOrderTypeExternalReferenceCode(),
					commerceTermEntry.getCompanyId());

			if (commerceOrderType == null) {
				String orderTypeExternalReferenceCode =
					termOrderType.getOrderTypeExternalReferenceCode();

				throw new NoSuchOrderTypeException(
					"Unable to find order type with external reference code " +
						orderTypeExternalReferenceCode);
			}
		}

		return commerceTermEntryRelService.addCommerceTermEntryRel(
			CommerceOrderType.class.getName(),
			commerceOrderType.getCommerceOrderTypeId(),
			commerceTermEntry.getCommerceTermEntryId());
	}

}