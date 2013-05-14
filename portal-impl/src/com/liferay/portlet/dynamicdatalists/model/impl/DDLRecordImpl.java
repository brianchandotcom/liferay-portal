/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDLRecordImpl extends DDLRecordBaseImpl {

	public DDLRecordImpl() {
	}

	@Override
	public Field getField(String fieldName) throws PortalException {
		Fields fields = getFields();

		return fields.get(fieldName);
	}

	@Override
	public Serializable getFieldDataType(String fieldName)
		throws PortalException, SystemException {

		DDLRecordSet recordSet = getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return ddmStructure.getFieldDataType(fieldName);
	}

	@Override
	public Fields getFields() throws PortalException {
		return StorageEngineUtil.getFields(getDDMStorageId());
	}

	@Override
	public Serializable getFieldType(String fieldName) throws Exception {
		DDLRecordSet recordSet = getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return ddmStructure.getFieldType(fieldName);
	}

	@Override
	public Serializable getFieldValue(String fieldName) throws PortalException {
		Field field = getField(fieldName);

		return field.getValue();
	}

	@Override
	public DDLRecordVersion getLatestRecordVersion()
		throws PortalException, SystemException {

		return DDLRecordLocalServiceUtil.getLatestRecordVersion(getRecordId());
	}

	@Override
	public DDLRecordSet getRecordSet() throws PortalException, SystemException {
		return DDLRecordSetLocalServiceUtil.getRecordSet(getRecordSetId());
	}

	@Override
	public DDLRecordVersion getRecordVersion()
		throws PortalException, SystemException {

		return getRecordVersion(getVersion());
	}

	@Override
	public DDLRecordVersion getRecordVersion(String version)
		throws PortalException, SystemException {

		return DDLRecordLocalServiceUtil.getRecordVersion(
			getRecordId(), version);
	}

	@Override
	public int getStatus() throws PortalException, SystemException {
		DDLRecordVersion recordVersion = getRecordVersion();

		return recordVersion.getStatus();
	}

}