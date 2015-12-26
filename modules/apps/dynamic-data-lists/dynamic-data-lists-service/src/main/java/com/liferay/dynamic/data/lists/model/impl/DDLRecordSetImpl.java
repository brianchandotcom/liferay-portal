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
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormInstanceFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.CacheField;

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
	public DDMFormValues getSettingsDDMFormValues() {
		if (_ddmFormValues == null) {
			try {
				DDMForm ddmForm = DDMFormFactory.create(
					DDLRecordSetSettings.class);

				_ddmFormValues = DDMFormValuesJSONDeserializerUtil.deserialize(
					ddmForm, getSettings());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _ddmFormValues;
	}

	@Override
	public DDLRecordSetSettings getSettingsModel() {
		if (_recordSetSettings == null) {
			_recordSetSettings = DDMFormInstanceFactory.create(
				DDLRecordSetSettings.class, getSettingsDDMFormValues());
		}

		return _recordSetSettings;
	}

	@Override
	public void setSettings(String settings) {
		super.setSettings(settings);

		_ddmFormValues = null;
		_recordSetSettings = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordSetImpl.class);

	@CacheField(methodName = "DDMFormValues")
	private DDMFormValues _ddmFormValues;

	private DDLRecordSetSettings _recordSetSettings;

}