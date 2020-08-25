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

package com.liferay.commerce.price.list.model.impl;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalServiceUtil;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Ethan Bustad
 */
public class CommercePriceListAccountRelImpl
	extends CommercePriceListAccountRelBaseImpl {

	public CommercePriceListAccountRelImpl() {
	}

	@Override
	public CommerceAccount getCommerceAccount() throws PortalException {
		return CommerceAccountLocalServiceUtil.getCommerceAccount(
			getCommerceAccountId());
	}

	@Override
	public CommercePriceList getCommercePriceList() throws PortalException {
		return CommercePriceListLocalServiceUtil.getCommercePriceList(
			getCommercePriceListId());
	}

}