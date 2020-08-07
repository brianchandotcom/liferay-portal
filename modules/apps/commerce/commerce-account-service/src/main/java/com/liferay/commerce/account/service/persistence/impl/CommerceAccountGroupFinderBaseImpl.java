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

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupPersistence;
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
public class CommerceAccountGroupFinderBaseImpl
	extends BasePersistenceImpl<CommerceAccountGroup> {

	public CommerceAccountGroupFinderBaseImpl() {
		setModelClass(CommerceAccountGroup.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");
		dbColumnNames.put("system", "system_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceAccountGroupPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce account group persistence.
	 *
	 * @return the commerce account group persistence
	 */
	public CommerceAccountGroupPersistence
		getCommerceAccountGroupPersistence() {

		return commerceAccountGroupPersistence;
	}

	/**
	 * Sets the commerce account group persistence.
	 *
	 * @param commerceAccountGroupPersistence the commerce account group persistence
	 */
	public void setCommerceAccountGroupPersistence(
		CommerceAccountGroupPersistence commerceAccountGroupPersistence) {

		this.commerceAccountGroupPersistence = commerceAccountGroupPersistence;
	}

	@BeanReference(type = CommerceAccountGroupPersistence.class)
	protected CommerceAccountGroupPersistence commerceAccountGroupPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountGroupFinderBaseImpl.class);

}