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

package com.liferay.commerce.shipping.engine.fixed.service.persistence.impl;

import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CShippingFixedOptionRelPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CShippingFixedOptionRelFinderBaseImpl extends BasePersistenceImpl<CShippingFixedOptionRel> {
	public CShippingFixedOptionRelFinderBaseImpl() {
		setModelClass(CShippingFixedOptionRel.class);
	}

	/**
	 * Returns the c shipping fixed option rel persistence.
	 *
	 * @return the c shipping fixed option rel persistence
	 */
	public CShippingFixedOptionRelPersistence getCShippingFixedOptionRelPersistence() {
		return cShippingFixedOptionRelPersistence;
	}

	/**
	 * Sets the c shipping fixed option rel persistence.
	 *
	 * @param cShippingFixedOptionRelPersistence the c shipping fixed option rel persistence
	 */
	public void setCShippingFixedOptionRelPersistence(
		CShippingFixedOptionRelPersistence cShippingFixedOptionRelPersistence) {
		this.cShippingFixedOptionRelPersistence = cShippingFixedOptionRelPersistence;
	}

	@BeanReference(type = CShippingFixedOptionRelPersistence.class)
	protected CShippingFixedOptionRelPersistence cShippingFixedOptionRelPersistence;
}