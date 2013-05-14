/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.shopping.NoSuchCouponException;
import com.liferay.portlet.shopping.model.ShoppingCartItem;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.service.ShoppingCartLocalServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingCouponLocalServiceUtil;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingCartImpl extends ShoppingCartBaseImpl {

	public ShoppingCartImpl() {
	}

	@Override
	public void addItemId(long itemId, String fields) {
		setItemIds(
			StringUtil.add(
				getItemIds(), itemId + fields, StringPool.COMMA, true));
	}

	@Override
	public ShoppingCoupon getCoupon() throws PortalException, SystemException {
		ShoppingCoupon coupon = null;

		if (Validator.isNotNull(getCouponCodes())) {
			String code = StringUtil.split(getCouponCodes())[0];

			try {
				coupon = ShoppingCouponLocalServiceUtil.getCoupon(code);
			}
			catch (NoSuchCouponException nsce) {
			}
		}

		return coupon;
	}

	@Override
	public Map<ShoppingCartItem, Integer> getItems() throws SystemException {
		return ShoppingCartLocalServiceUtil.getItems(
			getGroupId(), getItemIds());
	}

	@Override
	public int getItemsSize() {
		return StringUtil.split(getItemIds()).length;
	}

}