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

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFormInstanceRecordFinderBaseImpl extends BasePersistenceImpl<DDMFormInstanceRecord> {
	public DDMFormInstanceRecordFinderBaseImpl() {
		setModelClass(DDMFormInstanceRecord.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");

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
		return getDDMFormInstanceRecordPersistence().getBadColumnNames();
	}

	/**
	 * Returns the ddm form instance record persistence.
	 *
	 * @return the ddm form instance record persistence
	 */
	public DDMFormInstanceRecordPersistence getDDMFormInstanceRecordPersistence() {
		return ddmFormInstanceRecordPersistence;
	}

	/**
	 * Sets the ddm form instance record persistence.
	 *
	 * @param ddmFormInstanceRecordPersistence the ddm form instance record persistence
	 */
	public void setDDMFormInstanceRecordPersistence(
		DDMFormInstanceRecordPersistence ddmFormInstanceRecordPersistence) {
		this.ddmFormInstanceRecordPersistence = ddmFormInstanceRecordPersistence;
	}

	@BeanReference(type = DDMFormInstanceRecordPersistence.class)
	protected DDMFormInstanceRecordPersistence ddmFormInstanceRecordPersistence;
	private static final Log _log = LogFactoryUtil.getLog(DDMFormInstanceRecordFinderBaseImpl.class);
}