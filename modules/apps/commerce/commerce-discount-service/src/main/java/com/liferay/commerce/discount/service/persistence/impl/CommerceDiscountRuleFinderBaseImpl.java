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

package com.liferay.commerce.discount.service.persistence.impl;

import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountRulePersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountRuleFinderBaseImpl
	extends BasePersistenceImpl<CommerceDiscountRule> {

	public CommerceDiscountRuleFinderBaseImpl() {
		setModelClass(CommerceDiscountRule.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceDiscountRulePersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce discount rule persistence.
	 *
	 * @return the commerce discount rule persistence
	 */
	public CommerceDiscountRulePersistence
		getCommerceDiscountRulePersistence() {

		return commerceDiscountRulePersistence;
	}

	/**
	 * Sets the commerce discount rule persistence.
	 *
	 * @param commerceDiscountRulePersistence the commerce discount rule persistence
	 */
	public void setCommerceDiscountRulePersistence(
		CommerceDiscountRulePersistence commerceDiscountRulePersistence) {

		this.commerceDiscountRulePersistence = commerceDiscountRulePersistence;
	}

	@BeanReference(type = CommerceDiscountRulePersistence.class)
	protected CommerceDiscountRulePersistence commerceDiscountRulePersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountRuleFinderBaseImpl.class);

}