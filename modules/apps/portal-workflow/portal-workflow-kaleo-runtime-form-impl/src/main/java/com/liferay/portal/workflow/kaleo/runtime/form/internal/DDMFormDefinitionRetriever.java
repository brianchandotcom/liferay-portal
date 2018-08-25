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

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.form.FormDefinitionRetriever;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = FormDefinitionRetriever.class)
public class DDMFormDefinitionRetriever implements FormDefinitionRetriever {

	@Override
	public String getFormDefinition(
			KaleoTaskForm kaleoTaskForm,
			KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws PortalException {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			kaleoTaskForm.getFormUuid(), kaleoTaskInstanceToken.getGroupId());

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMFormSerializer ddmFormSerializer =
			_ddmFormSerializerTracker.getDDMFormSerializer("json");

		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			ddmFormSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMFormSerializerTracker _ddmFormSerializerTracker;

}