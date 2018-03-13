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

package com.liferay.commerce.tax.engine.fixed.service.persistence.impl;

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRatePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceTaxFixedRateFinderBaseImpl extends BasePersistenceImpl<CommerceTaxFixedRate> {
	public CommerceTaxFixedRateFinderBaseImpl() {
		setModelClass(CommerceTaxFixedRate.class);
	}

	/**
	 * Returns the commerce tax fixed rate persistence.
	 *
	 * @return the commerce tax fixed rate persistence
	 */
	public CommerceTaxFixedRatePersistence getCommerceTaxFixedRatePersistence() {
		return commerceTaxFixedRatePersistence;
	}

	/**
	 * Sets the commerce tax fixed rate persistence.
	 *
	 * @param commerceTaxFixedRatePersistence the commerce tax fixed rate persistence
	 */
	public void setCommerceTaxFixedRatePersistence(
		CommerceTaxFixedRatePersistence commerceTaxFixedRatePersistence) {
		this.commerceTaxFixedRatePersistence = commerceTaxFixedRatePersistence;
	}

	@BeanReference(type = CommerceTaxFixedRatePersistence.class)
	protected CommerceTaxFixedRatePersistence commerceTaxFixedRatePersistence;
}