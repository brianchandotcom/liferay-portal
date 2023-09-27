/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model.impl;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalServiceUtil;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountCommerceAccountGroupRelImpl
	extends CommerceDiscountCommerceAccountGroupRelBaseImpl {

	@Override
	public AccountGroup getAccountGroup() throws PortalException {
		return AccountGroupLocalServiceUtil.getAccountGroup(
			getCommerceAccountGroupId());
	}

	@Override
	public CommerceDiscount getCommerceDiscount() throws PortalException {
		return CommerceDiscountLocalServiceUtil.getCommerceDiscount(
			getCommerceDiscountId());
	}

}