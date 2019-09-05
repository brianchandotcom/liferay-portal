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
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.cache.CacheField;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMFormInstanceImpl extends DDMFormInstanceBaseImpl {

	@Override
	public DDMForm getDDMForm() throws PortalException {
		DDMStructure ddmStructure = getStructure();

		return ddmStructure.getDDMForm();
	}

	@Override
	public List<DDMFormInstanceRecord> getFormInstanceRecords() {
		return DDMFormInstanceRecordLocalServiceUtil.getFormInstanceRecords(
			getFormInstanceId());
	}

	@Override
	public DDMFormInstanceVersion getFormInstanceVersion(String version)
		throws PortalException {

		return DDMFormInstanceVersionLocalServiceUtil.getFormInstanceVersion(
			getFormInstanceId(), version);
	}

	@Override
	public DDMFormValues getSettingsDDMFormValues() throws PortalException {
		if (_ddmFormValues == null) {
			_ddmFormValues =
				DDMFormInstanceLocalServiceUtil.
					getFormInstanceSettingsFormValues(this);
		}

		return _ddmFormValues;
	}

	@Override
	public DDMFormInstanceSettings getSettingsModel() throws PortalException {
		if (_formInstanceSettings == null) {
			_formInstanceSettings =
				DDMFormInstanceLocalServiceUtil.getFormInstanceSettingsModel(
					this);
		}

		return _formInstanceSettings;
	}

	@Override
	public DDMStructure getStructure() throws PortalException {
		return DDMStructureLocalServiceUtil.getStructure(getStructureId());
	}

	@Override
	public void setSettings(String settings) {
		super.setSettings(settings);

		_formInstanceSettings = null;
	}

	@CacheField(methodName = "DDMFormValues", propagateToInterface = true)
	private DDMFormValues _ddmFormValues;

	private DDMFormInstanceSettings _formInstanceSettings;

}