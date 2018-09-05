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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextDeserializer;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextDeserializerRequest;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 * @author Harlan Bruno
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM,
		"mvc.command.name=addFormInstanceRecord"
	},
	service = MVCResourceCommand.class
)
public class AddFormInstanceRecordMVCResourceCommand
	extends BaseMVCResourceCommand {

	protected DDMFormValues createDDMFormValues(
			DDMFormInstance ddmFormInstance, ResourceRequest resourceRequest)
		throws Exception {

		String serializedDDMFormValues = ParamUtil.getString(
			resourceRequest, "serializedDDMFormValues");

		if (Validator.isNull(serializedDDMFormValues)) {
			return null;
		}

		DDMForm ddmForm = getDDMForm(ddmFormInstance);

		DDMFormContextDeserializerRequest ddmFormContextDeserializerRequest =
			DDMFormContextDeserializerRequest.with(
				ddmForm, serializedDDMFormValues);

		Locale currentLocale = LocaleUtil.fromLanguageId(
			LanguageUtil.getLanguageId(resourceRequest));

		ddmFormContextDeserializerRequest.addProperty(
			"currentLocale", currentLocale);

		return _ddmFormBuilderContextToDDMFormValues.deserialize(
			ddmFormContextDeserializerRequest);
	}

	protected ServiceContext createServiceContext(
			ResourceRequest resourceRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMFormInstanceRecord.class.getName(), resourceRequest);

		serviceContext.setAttribute("status", WorkflowConstants.STATUS_DRAFT);
		serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		return serviceContext;
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		boolean preview = ParamUtil.getBoolean(resourceRequest, "preview");

		if (preview) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long formInstanceId = ParamUtil.getLong(
			resourceRequest, "formInstanceId");

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(formInstanceId);

		DDMFormValues ddmFormValues = createDDMFormValues(
			ddmFormInstance, resourceRequest);

		if (ddmFormValues == null) {
			return;
		}

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			_ddmFormInstanceRecordVersionLocalService.
				fetchLatestFormInstanceRecordVersion(
					themeDisplay.getUserId(), formInstanceId,
					ddmFormInstance.getVersion(),
					WorkflowConstants.STATUS_DRAFT);

		ServiceContext serviceContext = createServiceContext(resourceRequest);

		if (ddmFormInstanceRecordVersion == null) {
			_ddmFormInstanceRecordService.addFormInstanceRecord(
				ddmFormInstance.getGroupId(), formInstanceId, ddmFormValues,
				serviceContext);
		}
		else {
			_ddmFormInstanceRecordService.updateFormInstanceRecord(
				ddmFormInstanceRecordVersion.getFormInstanceRecordId(), false,
				ddmFormValues, serviceContext);
		}
	}

	protected DDMForm getDDMForm(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		return ddmStructure.getDDMForm();
	}

	@Reference(
		target = "(dynamic.data.mapping.form.builder.context.deserializer.type=formValues)"
	)
	private DDMFormContextDeserializer<DDMFormValues>
		_ddmFormBuilderContextToDDMFormValues;

	@Reference
	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	@Reference
	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

}