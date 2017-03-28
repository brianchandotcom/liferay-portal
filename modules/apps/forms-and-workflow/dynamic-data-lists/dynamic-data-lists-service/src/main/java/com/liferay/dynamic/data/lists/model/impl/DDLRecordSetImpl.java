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

package com.liferay.dynamic.data.lists.model.impl;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.cache.CacheField;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLRecordSetImpl extends DDLRecordSetBaseImpl {

	@Override
	public DDMStructure getDDMStructure() throws PortalException {
		return DDMStructureLocalServiceUtil.getStructure(getDDMStructureId());
	}

	@Override
	public DDMStructure getDDMStructure(long formDDMTemplateId)
		throws PortalException {

		DDMStructure ddmStructure = getDDMStructure();

		if (formDDMTemplateId > 0) {
			DDMTemplate ddmTemplate =
				DDMTemplateLocalServiceUtil.fetchDDMTemplate(formDDMTemplateId);

			if (ddmTemplate != null) {

				// Clone ddmStructure to make sure changes are never persisted

				ddmStructure = (DDMStructure)ddmStructure.clone();

				ddmStructure.setDefinition(ddmTemplate.getScript());
			}
		}

		return ddmStructure;
	}

	@Override
	public List<DDLRecord> getRecords() {
		return DDLRecordLocalServiceUtil.getRecords(getRecordSetId());
	}

	@Override
	public DDLRecordSetVersion getRecordSetVersion() throws PortalException {
		return getRecordSetVersion(getVersion());
	}

	@Override
	public DDLRecordSetVersion getRecordSetVersion(String version)
		throws PortalException {

		return DDLRecordSetVersionLocalServiceUtil.getRecordSetVersion(
			getRecordSetId(), version);
	}

	@Override
	public DDMFormValues getSettingsDDMFormValues() throws PortalException {
		if (_ddmFormValues == null) {
			_ddmFormValues =
				DDLRecordSetLocalServiceUtil.getRecordSetSettingsDDMFormValues(
					this);
		}

		return _ddmFormValues;
	}

	@Override
	public DDLRecordSetSettings getSettingsModel() throws PortalException {
		if (_recordSetSettings == null) {
			_recordSetSettings =
				DDLRecordSetLocalServiceUtil.getRecordSetSettingsModel(this);
		}

		return _recordSetSettings;
	}

	@Override
	public void setSettings(String settings) {
		super.setSettings(settings);

		_recordSetSettings = null;
	}

	@CacheField(methodName = "DDMFormValues", propagateToInterface = true)
	private DDMFormValues _ddmFormValues;

	private DDLRecordSetSettings _recordSetSettings;

}