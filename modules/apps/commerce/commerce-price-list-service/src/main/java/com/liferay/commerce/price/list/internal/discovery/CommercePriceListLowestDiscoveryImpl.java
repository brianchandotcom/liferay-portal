/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.discovery;

import com.liferay.account.service.AccountGroupLocalService;
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
@Component(service = CommercePriceListDiscovery.class)
public class CommercePriceListLowestDiscoveryImpl
	implements CommercePriceListDiscovery {

	@Override
	public CommercePriceList getCommercePriceList(
			long groupId, long commerceAccountId, long commerceChannelId,
			long commerceOrderTypeId, String cpInstanceUuid, String type,
			String unitOfMeasureKey)
		throws PortalException {

		return _commercePriceListLocalService.getCommercePriceListByLowestPrice(
			groupId, commerceAccountId,
			_accountGroupLocalService.getAccountGroupIds(commerceAccountId),
			commerceChannelId, commerceOrderTypeId, cpInstanceUuid, type,
			unitOfMeasureKey);
	}

	@Override
	public String getCommercePriceListDiscoveryKey() {
		return CommercePricingConstants.ORDER_BY_LOWEST_ENTRY;
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

}