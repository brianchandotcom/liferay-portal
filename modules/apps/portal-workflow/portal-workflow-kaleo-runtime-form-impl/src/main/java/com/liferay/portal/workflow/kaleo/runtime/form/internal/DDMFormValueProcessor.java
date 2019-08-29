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

package com.liferay.portal.workflow.kaleo.runtime.form.internal;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance;
import com.liferay.portal.workflow.kaleo.runtime.form.FormValueProcessor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = FormValueProcessor.class)
public class DDMFormValueProcessor implements FormValueProcessor {

	@Override
	public KaleoTaskFormInstance processFormValues(
			KaleoTaskForm kaleoTaskForm,
			KaleoTaskFormInstance kaleoTaskFormInstance, String formValues,
			ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			kaleoTaskForm.getFormUuid(), kaleoTaskFormInstance.getGroupId());

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DDMFormValues ddmFormValues = deserialize(
			formValues, ddmStructure.getDDMForm());

		DDLRecord ddlRecord = _ddlRecordLocalService.addRecord(
			kaleoTaskFormInstance.getUserId(),
			kaleoTaskFormInstance.getGroupId(), ddlRecordSet.getRecordSetId(),
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, ddmFormValues,
			serviceContext);

		kaleoTaskFormInstance.setFormValueEntryGroupId(ddlRecord.getGroupId());
		kaleoTaskFormInstance.setFormValueEntryId(ddlRecord.getRecordId());
		kaleoTaskFormInstance.setFormValueEntryUuid(ddlRecord.getUuid());

		return kaleoTaskFormInstance;
	}

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_jsonDDMFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference(target = "(ddm.form.values.deserializer.type=json)")
	private DDMFormValuesDeserializer _jsonDDMFormValuesDeserializer;

}