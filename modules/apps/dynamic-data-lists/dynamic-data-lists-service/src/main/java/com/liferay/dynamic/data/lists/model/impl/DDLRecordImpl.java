/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.model.impl;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDLRecordImpl extends DDLRecordBaseImpl {

	@Override
	public List<DDMFormFieldValue> getDDMFormFieldValues(String fieldName)
		throws PortalException {

		DDMFormValues ddmFormValues = getDDMFormValues();

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		return ddmFormFieldValuesMap.get(fieldName);
	}

	@Override
	public DDMFormValues getDDMFormValues() throws PortalException {
		return DDLRecordLocalServiceUtil.getDDMFormValues(getDDMStorageId());
	}

	@Override
	public Serializable getFieldDataType(String fieldName)
		throws PortalException {

		DDLRecordSet recordSet = getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return ddmStructure.getFieldDataType(fieldName);
	}

	@Override
	public Serializable getFieldType(String fieldName) throws Exception {
		DDLRecordSet recordSet = getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return ddmStructure.getFieldType(fieldName);
	}

	@Override
	public DDLRecordVersion getLatestRecordVersion() throws PortalException {
		return DDLRecordVersionLocalServiceUtil.getLatestRecordVersion(
			getRecordId());
	}

	@Override
	public DDLRecordSet getRecordSet() throws PortalException {
		return DDLRecordSetLocalServiceUtil.getRecordSet(getRecordSetId());
	}

	@Override
	public DDLRecordVersion getRecordVersion() throws PortalException {
		return getRecordVersion(getVersion());
	}

	@Override
	public DDLRecordVersion getRecordVersion(String version)
		throws PortalException {

		return DDLRecordVersionLocalServiceUtil.getRecordVersion(
			getRecordId(), version);
	}

	@Override
	public int getStatus() throws PortalException {
		DDLRecordVersion recordVersion = getRecordVersion();

		return recordVersion.getStatus();
	}

}