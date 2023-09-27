/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMFormInstanceRecordVersionImpl
	extends DDMFormInstanceRecordVersionBaseImpl {

	@Override
	public DDMForm getDDMForm() throws PortalException {
		DDMFormInstanceVersion ddmFormInstanceVersion =
			DDMFormInstanceVersionLocalServiceUtil.getFormInstanceVersion(
				getFormInstanceId(), getFormInstanceVersion());

		DDMStructureVersion ddmStructureVersion =
			ddmFormInstanceVersion.getStructureVersion();

		return ddmStructureVersion.getDDMForm();
	}

	@Override
	public DDMFormValues getDDMFormValues() throws PortalException {
		DDMFormInstance ddmFormInstance =
			DDMFormInstanceLocalServiceUtil.getFormInstance(
				getFormInstanceId());

		return DDMFormInstanceRecordLocalServiceUtil.getDDMFormValues(
			getDDMForm(), getStorageId(), ddmFormInstance.getStorageType());
	}

	@Override
	public DDMFormInstance getFormInstance() throws PortalException {
		return DDMFormInstanceLocalServiceUtil.getFormInstance(
			getFormInstanceId());
	}

	@Override
	public DDMFormInstanceRecord getFormInstanceRecord()
		throws PortalException {

		return DDMFormInstanceRecordLocalServiceUtil.getFormInstanceRecord(
			getFormInstanceRecordId());
	}

}