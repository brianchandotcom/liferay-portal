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

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.persistence.CommerceChannelRelPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Marco Leo
 * @generated
 */
public class CommerceChannelRelFinderBaseImpl
	extends BasePersistenceImpl<CommerceChannelRel> {

	public CommerceChannelRelFinderBaseImpl() {
		setModelClass(CommerceChannelRel.class);
	}

	/**
	 * Returns the commerce channel rel persistence.
	 *
	 * @return the commerce channel rel persistence
	 */
	public CommerceChannelRelPersistence getCommerceChannelRelPersistence() {
		return commerceChannelRelPersistence;
	}

	/**
	 * Sets the commerce channel rel persistence.
	 *
	 * @param commerceChannelRelPersistence the commerce channel rel persistence
	 */
	public void setCommerceChannelRelPersistence(
		CommerceChannelRelPersistence commerceChannelRelPersistence) {

		this.commerceChannelRelPersistence = commerceChannelRelPersistence;
	}

	@BeanReference(type = CommerceChannelRelPersistence.class)
	protected CommerceChannelRelPersistence commerceChannelRelPersistence;

}