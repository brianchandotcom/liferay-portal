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

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionRelPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShippingFixedOptionRelFinderBaseImpl
	extends BasePersistenceImpl<CommerceShippingFixedOptionRel> {
	public CommerceShippingFixedOptionRelFinderBaseImpl() {
		setModelClass(CommerceShippingFixedOptionRel.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("commerceShippingFixedOptionRelId",
				"CShippingFixedOptionRelId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceShippingFixedOptionRelPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce shipping fixed option rel persistence.
	 *
	 * @return the commerce shipping fixed option rel persistence
	 */
	public CommerceShippingFixedOptionRelPersistence getCommerceShippingFixedOptionRelPersistence() {
		return commerceShippingFixedOptionRelPersistence;
	}

	/**
	 * Sets the commerce shipping fixed option rel persistence.
	 *
	 * @param commerceShippingFixedOptionRelPersistence the commerce shipping fixed option rel persistence
	 */
	public void setCommerceShippingFixedOptionRelPersistence(
		CommerceShippingFixedOptionRelPersistence commerceShippingFixedOptionRelPersistence) {
		this.commerceShippingFixedOptionRelPersistence = commerceShippingFixedOptionRelPersistence;
	}

	@BeanReference(type = CommerceShippingFixedOptionRelPersistence.class)
	protected CommerceShippingFixedOptionRelPersistence commerceShippingFixedOptionRelPersistence;
	private static final Log _log = LogFactoryUtil.getLog(CommerceShippingFixedOptionRelFinderBaseImpl.class);
}