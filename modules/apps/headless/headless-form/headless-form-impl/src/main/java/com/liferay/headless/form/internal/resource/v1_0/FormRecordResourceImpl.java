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

package com.liferay.headless.form.internal.resource.v1_0;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.internal.dto.v1_0.util.DDMFormValuesUtil;
import com.liferay.headless.form.internal.dto.v1_0.util.FormRecordUtil;
import com.liferay.headless.form.resource.v1_0.FormRecordResource;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 * @author Victor Oliveira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/form-record.properties",
	scope = ServiceScope.PROTOTYPE, service = FormRecordResource.class
)
public class FormRecordResourceImpl extends BaseFormRecordResourceImpl {

	@Override
	public FormRecord getFormFormRecordByLatestDraft(Long formId)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(formId);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			_ddmFormInstanceRecordVersionService.
				fetchLatestFormInstanceRecordVersion(
					contextUser.getUserId(),
					ddmFormInstance.getFormInstanceId(),
					ddmFormInstance.getVersion(),
					WorkflowConstants.STATUS_DRAFT);

		return FormRecordUtil.toFormRecord(
			ddmFormInstanceRecordVersion.getFormInstanceRecord(), _dlAppService,
			_dlurlHelper, contextAcceptLanguage.getPreferredLocale(), _portal,
			_userLocalService);
	}

	@Override
	public Page<FormRecord> getFormFormRecordsPage(
			Long formId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddmFormInstanceRecordService.getFormInstanceRecords(
					formId, WorkflowConstants.STATUS_ANY,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				formRecord -> FormRecordUtil.toFormRecord(
					formRecord, _dlAppService, _dlurlHelper,
					contextAcceptLanguage.getPreferredLocale(), _portal,
					_userLocalService)),
			pagination,
			_ddmFormInstanceRecordService.getFormInstanceRecordsCount(formId));
	}

	@Override
	public FormRecord getFormRecord(Long formRecordId) throws Exception {
		return FormRecordUtil.toFormRecord(
			_ddmFormInstanceRecordService.getFormInstanceRecord(formRecordId),
			_dlAppService, _dlurlHelper,
			contextAcceptLanguage.getPreferredLocale(), _portal,
			_userLocalService);
	}

	@Override
	public FormRecord postFormFormRecord(Long formId, FormRecord formRecord)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(formId);

		DDMFormValues ddmFormValues = DDMFormValuesUtil.createDDMFormValues(
			ddmFormInstance, formRecord.getFormFieldValues(),
			contextAcceptLanguage.getPreferredLocale());

		_linkFileEntries(ddmFormInstance.getDDMForm(), ddmFormValues);

		return FormRecordUtil.toFormRecord(
			_ddmFormInstanceRecordService.addFormInstanceRecord(
				ddmFormInstance.getGroupId(),
				ddmFormInstance.getFormInstanceId(), ddmFormValues,
				_createServiceContext(formRecord.getDraft())),
			_dlAppService, _dlurlHelper,
			contextAcceptLanguage.getPreferredLocale(), _portal,
			_userLocalService);
	}

	@Override
	public FormRecord putFormRecord(Long formRecordId, FormRecord formRecord)
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			_ddmFormInstanceRecordService.getFormInstanceRecord(formRecordId);

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecord.getFormInstance();

		DDMFormValues ddmFormValues = DDMFormValuesUtil.createDDMFormValues(
			ddmFormInstance, formRecord.getFormFieldValues(),
			contextAcceptLanguage.getPreferredLocale());

		_linkFileEntries(ddmFormInstance.getDDMForm(), ddmFormValues);

		return FormRecordUtil.toFormRecord(
			_ddmFormInstanceRecordService.updateFormInstanceRecord(
				formRecordId, false, ddmFormValues,
				_createServiceContext(formRecord.getDraft())),
			_dlAppService, _dlurlHelper,
			contextAcceptLanguage.getPreferredLocale(), _portal,
			_userLocalService);
	}

	private ServiceContext _createServiceContext(boolean draft)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMFormInstanceRecord.class.getName(), contextHttpServletRequest);

		if (draft) {
			serviceContext.setAttribute(
				"status", WorkflowConstants.STATUS_DRAFT);
			serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}
		else {
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}

		return serviceContext;
	}

	private void _linkFileEntries(
		DDMForm ddmForm, DDMFormValues ddmFormValues) {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		Stream<DDMFormField> ddmFormFieldsStream = ddmFormFields.stream();

		ddmFormFieldsStream.filter(
			ddmFormField -> Objects.equals(
				ddmFormField.getType(), "document_library")
		).map(
			field -> {
				List<DDMFormFieldValue> ddmFormFieldValues =
					ddmFormValues.getDDMFormFieldValues();

				Stream<DDMFormFieldValue> ddmFormFieldValuesStream =
					ddmFormFieldValues.stream();

				return ddmFormFieldValuesStream.filter(
					value -> Objects.equals(field.getName(), value.getName())
				).collect(
					Collectors.toList()
				);
			}
		).forEach(
			ddmFormFieldValues -> {
				try {
					for (DDMFormFieldValue ddmFormFieldValue :
							ddmFormFieldValues) {

						_setValue(ddmFormFieldValue);
					}
				}
				catch (Exception exception) {
					_log.error(exception, exception);

					throw new BadRequestException(exception);
				}
			}
		);
	}

	private void _setValue(DDMFormFieldValue ddmFormFieldValue)
		throws Exception {

		Value value = ddmFormFieldValue.getValue();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			value.getString(contextAcceptLanguage.getPreferredLocale()));

		long fileEntryId = jsonObject.getLong("fileEntryId");

		if (fileEntryId == 0) {
			return;
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		String json = JSONUtil.put(
			"fileEntryId", fileEntry.getFileEntryId()
		).put(
			"groupId", fileEntry.getGroupId()
		).put(
			"title", fileEntry.getTitle()
		).put(
			"type", fileEntry.getMimeType()
		).put(
			"uuid", fileEntry.getUuid()
		).put(
			"version", fileEntry.getVersion()
		).toString();

		value = new UnlocalizedValue(json);

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		if (ddmFormField.isLocalizable()) {
			value = new LocalizedValue();

			value.addString(value.getDefaultLocale(), json);
		}

		ddmFormFieldValue.setValue(value);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormRecordResourceImpl.class);

	@Reference
	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	@Reference
	private DDMFormInstanceRecordVersionService
		_ddmFormInstanceRecordVersionService;

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}