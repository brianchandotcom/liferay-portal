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

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRateAddressRelPersistence;

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
public class CommerceTaxFixedRateAddressRelFinderBaseImpl
	extends BasePersistenceImpl<CommerceTaxFixedRateAddressRel> {
	public CommerceTaxFixedRateAddressRelFinderBaseImpl() {
		setModelClass(CommerceTaxFixedRateAddressRel.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("commerceTaxFixedRateAddressRelId",
				"CTaxFixedRateAddressRelId");

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
		return getCommerceTaxFixedRateAddressRelPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce tax fixed rate address rel persistence.
	 *
	 * @return the commerce tax fixed rate address rel persistence
	 */
	public CommerceTaxFixedRateAddressRelPersistence getCommerceTaxFixedRateAddressRelPersistence() {
		return commerceTaxFixedRateAddressRelPersistence;
	}

	/**
	 * Sets the commerce tax fixed rate address rel persistence.
	 *
	 * @param commerceTaxFixedRateAddressRelPersistence the commerce tax fixed rate address rel persistence
	 */
	public void setCommerceTaxFixedRateAddressRelPersistence(
		CommerceTaxFixedRateAddressRelPersistence commerceTaxFixedRateAddressRelPersistence) {
		this.commerceTaxFixedRateAddressRelPersistence = commerceTaxFixedRateAddressRelPersistence;
	}

	@BeanReference(type = CommerceTaxFixedRateAddressRelPersistence.class)
	protected CommerceTaxFixedRateAddressRelPersistence commerceTaxFixedRateAddressRelPersistence;
	private static final Log _log = LogFactoryUtil.getLog(CommerceTaxFixedRateAddressRelFinderBaseImpl.class);
}