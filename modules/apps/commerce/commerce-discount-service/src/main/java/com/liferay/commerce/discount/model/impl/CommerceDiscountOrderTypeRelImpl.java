/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model.impl;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class CommerceDiscountOrderTypeRelImpl
	extends CommerceDiscountOrderTypeRelBaseImpl {

	@Override
	public CommerceDiscount getCommerceDiscount() throws PortalException {
		return CommerceDiscountLocalServiceUtil.getCommerceDiscount(
			getCommerceDiscountId());
	}

}