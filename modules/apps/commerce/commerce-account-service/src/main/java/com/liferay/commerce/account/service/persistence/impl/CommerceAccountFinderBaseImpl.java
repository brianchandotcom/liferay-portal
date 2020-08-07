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

package com.liferay.commerce.account.service.persistence.impl;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.persistence.CommerceAccountPersistence;
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
public class CommerceAccountFinderBaseImpl
	extends BasePersistenceImpl<CommerceAccount> {

	public CommerceAccountFinderBaseImpl() {
		setModelClass(CommerceAccount.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceAccountPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce account persistence.
	 *
	 * @return the commerce account persistence
	 */
	public CommerceAccountPersistence getCommerceAccountPersistence() {
		return commerceAccountPersistence;
	}

	/**
	 * Sets the commerce account persistence.
	 *
	 * @param commerceAccountPersistence the commerce account persistence
	 */
	public void setCommerceAccountPersistence(
		CommerceAccountPersistence commerceAccountPersistence) {

		this.commerceAccountPersistence = commerceAccountPersistence;
	}

	@BeanReference(type = CommerceAccountPersistence.class)
	protected CommerceAccountPersistence commerceAccountPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountFinderBaseImpl.class);

}