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

import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.service.persistence.CPInstanceOptionValueRelPersistence;
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
public class CPInstanceOptionValueRelFinderBaseImpl
	extends BasePersistenceImpl<CPInstanceOptionValueRel> {

	public CPInstanceOptionValueRelFinderBaseImpl() {
		setModelClass(CPInstanceOptionValueRel.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCPInstanceOptionValueRelPersistence().getBadColumnNames();
	}

	/**
	 * Returns the cp instance option value rel persistence.
	 *
	 * @return the cp instance option value rel persistence
	 */
	public CPInstanceOptionValueRelPersistence
		getCPInstanceOptionValueRelPersistence() {

		return cpInstanceOptionValueRelPersistence;
	}

	/**
	 * Sets the cp instance option value rel persistence.
	 *
	 * @param cpInstanceOptionValueRelPersistence the cp instance option value rel persistence
	 */
	public void setCPInstanceOptionValueRelPersistence(
		CPInstanceOptionValueRelPersistence
			cpInstanceOptionValueRelPersistence) {

		this.cpInstanceOptionValueRelPersistence =
			cpInstanceOptionValueRelPersistence;
	}

	@BeanReference(type = CPInstanceOptionValueRelPersistence.class)
	protected CPInstanceOptionValueRelPersistence
		cpInstanceOptionValueRelPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceOptionValueRelFinderBaseImpl.class);

}