/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.util.v1_0;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.TierPrice;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.math.BigDecimal;

/**
 * @author Alessio Antonio Rendina
 */
public class TierPriceUtil {

	public static CommerceTierPriceEntry addOrUpdateCommerceTierPriceEntry(
			CommerceTierPriceEntryService commerceTierPriceEntryService,
			TierPrice tierPrice, CommercePriceEntry commercePriceEntry,
			ServiceContext serviceContext)
		throws PortalException {

		return commerceTierPriceEntryService.addOrUpdateCommerceTierPriceEntry(
			tierPrice.getExternalReferenceCode(),
			GetterUtil.getLong(tierPrice.getId()),
			commercePriceEntry.getCommercePriceEntryId(), tierPrice.getPrice(),
			tierPrice.getPromoPrice(),
			BigDecimal.valueOf(tierPrice.getMinimumQuantity()),
			tierPrice.getPriceEntryExternalReferenceCode(), serviceContext);
	}

}