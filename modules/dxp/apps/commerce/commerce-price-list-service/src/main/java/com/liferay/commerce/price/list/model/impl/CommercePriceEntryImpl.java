/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.price.list.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactoryUtil;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CommercePriceEntryImpl extends CommercePriceEntryBaseImpl {

	public CommercePriceEntryImpl() {
	}

	@Override
	public CommercePriceList getCommercePriceList() throws PortalException {
		return CommercePriceListLocalServiceUtil.getCommercePriceList(
			getCommercePriceListId());
	}

	@Override
	public CPInstance getCPInstance() throws PortalException {
		return CPInstanceLocalServiceUtil.getCPInstance(getCPInstanceId());
	}

	@Override
	public CommerceMoney getPriceMoney(long commerceCurrencyId)
		throws PortalException {

		return CommerceMoneyFactoryUtil.create(commerceCurrencyId, getPrice());
	}

	@Override
	public CommerceMoney getPromoPriceMoney(long commerceCurrencyId)
		throws PortalException {

		return CommerceMoneyFactoryUtil.create(
			commerceCurrencyId, getPromoPrice());
	}

}