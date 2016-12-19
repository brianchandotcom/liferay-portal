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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderInstancePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMDataProviderInstanceFinderBaseImpl extends BasePersistenceImpl<DDMDataProviderInstance> {
	@Override
	public Set<String> getBadColumnNames() {
		return getDDMDataProviderInstancePersistence()
				   .getBadColumnNames();
	}

	/**
	 * Returns the ddm data provider instance persistence.
	 *
	 * @return the ddm data provider instance persistence
	 */
	public DDMDataProviderInstancePersistence getDDMDataProviderInstancePersistence() {
		return ddmDataProviderInstancePersistence;
	}

	/**
	 * Sets the ddm data provider instance persistence.
	 *
	 * @param ddmDataProviderInstancePersistence the ddm data provider instance persistence
	 */
	public void setDDMDataProviderInstancePersistence(
		DDMDataProviderInstancePersistence ddmDataProviderInstancePersistence) {
		this.ddmDataProviderInstancePersistence = ddmDataProviderInstancePersistence;
	}

	@BeanReference(type = DDMDataProviderInstancePersistence.class)
	protected DDMDataProviderInstancePersistence ddmDataProviderInstancePersistence;
}