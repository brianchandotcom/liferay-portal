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

import com.liferay.commerce.cart.model.CommerceCart;
import com.liferay.commerce.cart.service.base.CommerceCartServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Marco Leo
 */
public class CommerceCartServiceImpl extends CommerceCartServiceBaseImpl {

	public CommerceCart fetchCommerceCart(long commerceCartId){
		return commerceCartLocalService.fetchCommerceCart(commerceCartId);
	}

	public CommerceCart addCommerceCart(
		String name, int type, ServiceContext serviceContext) throws PortalException{

		return commerceCartLocalService.addCommerceCart(name,type,serviceContext);
	}

}