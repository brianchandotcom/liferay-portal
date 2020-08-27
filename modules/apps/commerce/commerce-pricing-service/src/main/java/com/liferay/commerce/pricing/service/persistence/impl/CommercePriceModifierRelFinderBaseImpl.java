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

package com.liferay.commerce.pricing.service.persistence.impl;

import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.service.persistence.CommercePriceModifierRelPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Riccardo Alberti
 * @generated
 */
public class CommercePriceModifierRelFinderBaseImpl
	extends BasePersistenceImpl<CommercePriceModifierRel> {

	public CommercePriceModifierRelFinderBaseImpl() {
		setModelClass(CommercePriceModifierRel.class);
	}

	/**
	 * Returns the commerce price modifier rel persistence.
	 *
	 * @return the commerce price modifier rel persistence
	 */
	public CommercePriceModifierRelPersistence
		getCommercePriceModifierRelPersistence() {

		return commercePriceModifierRelPersistence;
	}

	/**
	 * Sets the commerce price modifier rel persistence.
	 *
	 * @param commercePriceModifierRelPersistence the commerce price modifier rel persistence
	 */
	public void setCommercePriceModifierRelPersistence(
		CommercePriceModifierRelPersistence
			commercePriceModifierRelPersistence) {

		this.commercePriceModifierRelPersistence =
			commercePriceModifierRelPersistence;
	}

	@BeanReference(type = CommercePriceModifierRelPersistence.class)
	protected CommercePriceModifierRelPersistence
		commercePriceModifierRelPersistence;

}