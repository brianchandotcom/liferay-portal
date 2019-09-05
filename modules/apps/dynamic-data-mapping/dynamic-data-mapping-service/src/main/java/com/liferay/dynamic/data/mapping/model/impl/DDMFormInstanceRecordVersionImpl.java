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
		return DDMFormInstanceRecordLocalServiceUtil.getDDMFormValues(
			getStorageId(), getDDMForm());
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