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

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.headless.form.dto.v1_0.FieldValue;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.form.resource.v1_0.FormRecordResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.ws.rs.core.Context;

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
	public FormRecord getFormFetchLatestDraft(Long formId) throws Exception {
		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(formId);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			_ddmFormInstanceRecordVersionService.
				fetchLatestFormInstanceRecordVersion(
					_user.getUserId(), ddmFormInstance.getFormInstanceId(),
					ddmFormInstance.getVersion(),
					WorkflowConstants.STATUS_DRAFT);

		return _toFormRecord(
			ddmFormInstanceRecordVersion.getFormInstanceRecord());
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
				this::_toFormRecord),
			pagination,
			_ddmFormInstanceRecordService.getFormInstanceRecordsCount(formId));
	}

	@Override
	public FormRecord getFormRecord(Long formRecordId) throws Exception {
		return _toFormRecord(
			_ddmFormInstanceRecordService.getFormInstanceRecord(formRecordId));
	}

	private FormRecord _toFormRecord(
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		DDMFormValues ddmFormValues = ddmFormInstanceRecord.getDDMFormValues();

		return new FormRecord() {
			{
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(
						ddmFormInstanceRecord.getUserId()));
				draft =
					ddmFormInstanceRecord.getStatus() ==
						WorkflowConstants.STATUS_DRAFT;
				dateCreated = ddmFormInstanceRecord.getCreateDate();
				dateModified = ddmFormInstanceRecord.getModifiedDate();
				datePublished = ddmFormInstanceRecord.getLastPublishDate();
				fieldValues = transformToArray(
					ddmFormValues.getDDMFormFieldValues(),
					ddmFormFieldValue -> {
						Value localizedValue = ddmFormFieldValue.getValue();

						if (localizedValue == null) {
							return null;
						}

						return new FieldValue() {
							{
								name = ddmFormFieldValue.getName();
								value = localizedValue.getString(
									contextAcceptLanguage.getPreferredLocale());
							}
						};
					},
					FieldValue.class);
				id = ddmFormInstanceRecord.getFormInstanceRecordId();
			}
		};
	}

	@Reference
	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	@Reference
	private DDMFormInstanceRecordVersionService
		_ddmFormInstanceRecordVersionService;

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Reference
	private Portal _portal;

	@Context
	private User _user;

	@Reference
	private UserLocalService _userLocalService;

}