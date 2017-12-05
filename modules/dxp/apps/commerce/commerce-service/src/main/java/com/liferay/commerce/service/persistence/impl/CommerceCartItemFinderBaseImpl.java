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

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.service.persistence.CommerceCartItemPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceCartItemFinderBaseImpl extends BasePersistenceImpl<CommerceCartItem> {
	public CommerceCartItemFinderBaseImpl() {
		setModelClass(CommerceCartItem.class);
	}

	/**
	 * Returns the commerce cart item persistence.
	 *
	 * @return the commerce cart item persistence
	 */
	public CommerceCartItemPersistence getCommerceCartItemPersistence() {
		return commerceCartItemPersistence;
	}

	/**
	 * Sets the commerce cart item persistence.
	 *
	 * @param commerceCartItemPersistence the commerce cart item persistence
	 */
	public void setCommerceCartItemPersistence(
		CommerceCartItemPersistence commerceCartItemPersistence) {
		this.commerceCartItemPersistence = commerceCartItemPersistence;
	}

	@BeanReference(type = CommerceCartItemPersistence.class)
	protected CommerceCartItemPersistence commerceCartItemPersistence;
}