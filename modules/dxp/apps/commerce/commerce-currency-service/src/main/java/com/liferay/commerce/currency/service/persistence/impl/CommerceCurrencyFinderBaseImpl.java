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

package com.liferay.commerce.currency.service.persistence.impl;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.persistence.CommerceCurrencyPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrea Di Giorgi
 * @generated
 */
public class CommerceCurrencyFinderBaseImpl extends BasePersistenceImpl<CommerceCurrency> {
	public CommerceCurrencyFinderBaseImpl() {
		setModelClass(CommerceCurrency.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("code", "code_");
			dbColumnNames.put("primary", "primary_");
			dbColumnNames.put("active", "active_");

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
		return getCommerceCurrencyPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce currency persistence.
	 *
	 * @return the commerce currency persistence
	 */
	public CommerceCurrencyPersistence getCommerceCurrencyPersistence() {
		return commerceCurrencyPersistence;
	}

	/**
	 * Sets the commerce currency persistence.
	 *
	 * @param commerceCurrencyPersistence the commerce currency persistence
	 */
	public void setCommerceCurrencyPersistence(
		CommerceCurrencyPersistence commerceCurrencyPersistence) {
		this.commerceCurrencyPersistence = commerceCurrencyPersistence;
	}

	@BeanReference(type = CommerceCurrencyPersistence.class)
	protected CommerceCurrencyPersistence commerceCurrencyPersistence;
	private static final Log _log = LogFactoryUtil.getLog(CommerceCurrencyFinderBaseImpl.class);
}