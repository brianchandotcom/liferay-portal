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

package com.liferay.commerce.price.list.internal.discovery;

import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.price.list.discovery.CommercePriceListDiscovery;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = "commerce.price.list.discovery.key=" + CommercePricingConstants.ORDER_BY_LOWEST_ENTRY,
	service = CommercePriceListDiscovery.class
)
public class CommercePriceListLowestDiscoveryImpl
	implements CommercePriceListDiscovery {

	@Override
	public CommercePriceList getCommercePriceList(
			long groupId, long commerceAccountId, long commerceChannelId,
			long commerceOrderTypeId, String cPInstanceUuid, String type)
		throws PortalException {

		return _commercePriceListLocalService.getCommercePriceListByLowestPrice(
			groupId, commerceAccountId,
			_commerceAccountHelper.getCommerceAccountGroupIds(
				commerceAccountId),
			commerceChannelId, commerceOrderTypeId, cPInstanceUuid, type);
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

}