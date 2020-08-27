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

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.persistence.CPTaxCategoryPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Marco Leo
 * @generated
 */
public class CPTaxCategoryFinderBaseImpl
	extends BasePersistenceImpl<CPTaxCategory> {

	public CPTaxCategoryFinderBaseImpl() {
		setModelClass(CPTaxCategory.class);
	}

	/**
	 * Returns the cp tax category persistence.
	 *
	 * @return the cp tax category persistence
	 */
	public CPTaxCategoryPersistence getCPTaxCategoryPersistence() {
		return cpTaxCategoryPersistence;
	}

	/**
	 * Sets the cp tax category persistence.
	 *
	 * @param cpTaxCategoryPersistence the cp tax category persistence
	 */
	public void setCPTaxCategoryPersistence(
		CPTaxCategoryPersistence cpTaxCategoryPersistence) {

		this.cpTaxCategoryPersistence = cpTaxCategoryPersistence;
	}

	@BeanReference(type = CPTaxCategoryPersistence.class)
	protected CPTaxCategoryPersistence cpTaxCategoryPersistence;

}