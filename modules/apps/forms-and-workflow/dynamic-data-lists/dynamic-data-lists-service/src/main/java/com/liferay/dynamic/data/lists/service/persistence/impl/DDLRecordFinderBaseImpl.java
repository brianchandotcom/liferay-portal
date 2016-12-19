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

package com.liferay.dynamic.data.lists.service.persistence.impl;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDLRecordFinderBaseImpl extends BasePersistenceImpl<DDLRecord> {
	@Override
	public Set<String> getBadColumnNames() {
		return getDDLRecordPersistence().getBadColumnNames();
	}

	/**
	 * Returns the ddl record persistence.
	 *
	 * @return the ddl record persistence
	 */
	public DDLRecordPersistence getDDLRecordPersistence() {
		return ddlRecordPersistence;
	}

	/**
	 * Sets the ddl record persistence.
	 *
	 * @param ddlRecordPersistence the ddl record persistence
	 */
	public void setDDLRecordPersistence(
		DDLRecordPersistence ddlRecordPersistence) {
		this.ddlRecordPersistence = ddlRecordPersistence;
	}

	@BeanReference(type = DDLRecordPersistence.class)
	protected DDLRecordPersistence ddlRecordPersistence;
}