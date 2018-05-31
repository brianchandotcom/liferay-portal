/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.price.list.test.util;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalServiceUtil;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.math.BigDecimal;

/**
 * @author Zoltán Takács
 */
public class CommerceTierPriceEntryTestUtil {

	public static CommerceTierPriceEntry addCommerceTierPriceEntry(
			long commercePriceEntryId, int minQuantity, double price,
			double promoPrice, String externalReferenceCode)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryLocalServiceUtil.getCommercePriceEntry(
				commercePriceEntryId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				commercePriceEntry.getGroupId());

		return CommerceTierPriceEntryLocalServiceUtil.addCommerceTierPriceEntry(
			commercePriceEntryId, externalReferenceCode,
			BigDecimal.valueOf(price), BigDecimal.valueOf(promoPrice),
			minQuantity, serviceContext);
	}

	public static CommerceTierPriceEntry upsertCommerceTierPriceEntry(
			long commerceTierPriceEntryId, long commercePriceEntryId,
			int minQuantity, double price, double promoPrice,
			String externalReferenceCode,
			String priceEntryExternalReferenceCode)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryLocalServiceUtil.getCommercePriceEntry(
				commercePriceEntryId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				commercePriceEntry.getGroupId());

		return CommerceTierPriceEntryLocalServiceUtil.
			upsertCommerceTierPriceEntry(
				commerceTierPriceEntryId, commercePriceEntryId,
				externalReferenceCode, BigDecimal.valueOf(price),
				BigDecimal.valueOf(promoPrice), minQuantity,
				priceEntryExternalReferenceCode, serviceContext);
	}

}