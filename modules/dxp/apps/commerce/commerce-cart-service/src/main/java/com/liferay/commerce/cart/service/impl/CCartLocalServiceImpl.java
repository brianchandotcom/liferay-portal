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

package com.liferay.commerce.cart.service.impl;

import com.liferay.commerce.cart.model.CCart;
import com.liferay.commerce.cart.service.base.CCartLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CCartLocalServiceImpl extends CCartLocalServiceBaseImpl {

	@Override
	public CCart addCCart(
			long cartUserId, String title, int type,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cCartId = counterLocalService.increment();

		CCart cCart = cCartPersistence.create(cCartId);

		cCart.setUuid(serviceContext.getUuid());
		cCart.setGroupId(groupId);
		cCart.setCompanyId(user.getCompanyId());
		cCart.setUserId(user.getUserId());
		cCart.setUserName(user.getFullName());
		cCart.setCartUserId(cartUserId);
		cCart.setTitle(title);
		cCart.setType(type);

		cCartPersistence.update(cCart);

		return cCart;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CCart deleteCCart(CCart cCart) throws PortalException {
		cCartPersistence.remove(cCart);

		return cCart;
	}

	@Override
	public CCart deleteCCart(long cCartId) throws PortalException {
		CCart cCart = cCartPersistence.findByPrimaryKey(cCartId);

		return cCartLocalService.deleteCCart(cCart);
	}

	@Override
	public List<CCart> getCCarts(
		int type, int start, int end,
		OrderByComparator<CCart> orderByComparator) {

		return cCartPersistence.findByType(type, start, end, orderByComparator);
	}

	@Override
	public int getCCartsCount(int type) {
		return cCartPersistence.countByType(type);
	}

	@Override
	public CCart updateCCart(
			long cCartId, long cartUserId, String title, int type)
		throws PortalException {

		CCart cCart = cCartPersistence.findByPrimaryKey(cCartId);

		cCart.setCartUserId(cartUserId);
		cCart.setTitle(title);
		cCart.setType(type);

		cCartPersistence.update(cCart);

		return cCart;
	}

}