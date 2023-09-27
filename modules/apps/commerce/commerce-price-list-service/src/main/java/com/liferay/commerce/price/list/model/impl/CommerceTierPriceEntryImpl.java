/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.model.impl;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactoryUtil;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTierPriceEntryImpl extends CommerceTierPriceEntryBaseImpl {

	@Override
	public CommercePriceEntry getCommercePriceEntry() throws PortalException {
		return CommercePriceEntryLocalServiceUtil.getCommercePriceEntry(
			getCommercePriceEntryId());
	}

	@Override
	public CommerceMoney getPriceCommerceMoney(long commerceCurrencyId)
		throws PortalException {

		return CommerceMoneyFactoryUtil.create(commerceCurrencyId, getPrice());
	}

	@Override
	public CommerceMoney getPromoPriceCommerceMoney(long commerceCurrencyId)
		throws PortalException {

		return CommerceMoneyFactoryUtil.create(
			commerceCurrencyId, getPromoPrice());
	}

}