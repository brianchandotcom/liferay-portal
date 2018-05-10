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

package com.liferay.forms.apio.internal.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.language.Language;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionModel;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.forms.apio.architect.identifier.FormInstanceIdentifier;
import com.liferay.forms.apio.architect.identifier.FormInstanceRecordIdentifier;
import com.liferay.forms.apio.internal.FormInstanceRecordServiceContext;
import com.liferay.forms.apio.internal.form.FormInstanceRecordForm;
import com.liferay.forms.apio.internal.helper.FormInstanceRecordResourceHelper;
import com.liferay.person.apio.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose FormInstanceRecord resources
 * through a web API. The resources are mapped from the internal model {@code
 * DDMFormInstanceRecord}.
 *
 * @author Paulo Cruz
 */
@Component(immediate = true)
public class FormInstanceRecordNestedCollectionResource
	implements NestedCollectionResource<DDMFormInstanceRecord, Long,
		FormInstanceRecordIdentifier, Long, FormInstanceIdentifier> {

	@Override
	public NestedCollectionRoutes<DDMFormInstanceRecord, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<DDMFormInstanceRecord, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addFormInstanceRecord, Language.class,
			FormInstanceRecordServiceContext.class,
			(credentials, aLong) -> true, FormInstanceRecordForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "form-instance-records";
	}

	@Override
	public ItemRoutes<DDMFormInstanceRecord, Long> itemRoutes(
		ItemRoutes.Builder<DDMFormInstanceRecord, Long> builder) {

		return builder.addGetter(
			_ddmFormInstanceRecordService::getFormInstanceRecord
		).addUpdater(
			this::_updateFormInstanceRecord, Language.class,
			FormInstanceRecordServiceContext.class,
			(credentials, aLong) -> true, FormInstanceRecordForm::buildForm
		).build();
	}

	@Override
	public Representor<DDMFormInstanceRecord> representor(
		Representor.Builder<DDMFormInstanceRecord, Long> builder) {

		return builder.types(
			"FormInstanceRecord"
		).identifier(
			DDMFormInstanceRecord::getFormInstanceRecordId
		).addBidirectionalModel(
			"formInstance", "formInstanceRecords", FormInstanceIdentifier.class,
			DDMFormInstanceRecord::getFormInstanceId
		).addDate(
			"dateCreated", DDMFormInstanceRecord::getCreateDate
		).addDate(
			"dateModified", DDMFormInstanceRecord::getModifiedDate
		).addDate(
			"datePublished", DDMFormInstanceRecord::getLastPublishDate
		).addLinkedModel(
			"author", PersonIdentifier.class, DDMFormInstanceRecord::getUserId
		).addLocalizedStringByLocale(
			"fieldValues", FormInstanceRecordResourceHelper::getFieldValuesJSON
		).addNested(
			"version", this::_getVersion,
			nestedBuilder -> nestedBuilder.types(
				"FormInstanceRecordVersion"
			).addLinkedModel(
				"author", PersonIdentifier.class,
				DDMFormInstanceRecordVersion::getUserId
			).addString(
				"name", DDMFormInstanceRecordVersionModel::getVersion
			).build()
		).build();
	}

	private DDMFormInstanceRecord _addFormInstanceRecord(
			Long ddmFormInstanceId,
			FormInstanceRecordForm formInstanceRecordForm, Language language,
			FormInstanceRecordServiceContext formInstanceRecordServiceContext)
		throws PortalException {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(ddmFormInstanceId);

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMFormValues ddmFormValues =
			FormInstanceRecordResourceHelper.getDDMFormValues(
				formInstanceRecordForm.getFieldValues(),
				ddmStructure.getDDMForm(), language.getPreferredLocale());

		ServiceContext serviceContext =
			formInstanceRecordServiceContext.getServiceContext();

		_setServiceContextAttributes(
			serviceContext, formInstanceRecordForm.isDraft());

		return _ddmFormInstanceRecordService.addFormInstanceRecord(
			ddmFormInstance.getGroupId(), ddmFormInstance.getFormInstanceId(),
			ddmFormValues,
			formInstanceRecordServiceContext.getServiceContext());
	}

	private PageItems<DDMFormInstanceRecord> _getPageItems(
			Pagination pagination, Long formInstanceId)
		throws PortalException {

		List<DDMFormInstanceRecord> ddmFormInstances =
			_ddmFormInstanceRecordService.getFormInstanceRecords(
				formInstanceId, WorkflowConstants.STATUS_ANY,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int count = _ddmFormInstanceRecordService.getFormInstanceRecordsCount(
			formInstanceId);

		return new PageItems<>(ddmFormInstances, count);
	}

	private DDMFormInstanceRecordVersion _getVersion(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		return Try.fromFallible(
			ddmFormInstanceRecord::getVersion
		).map(
			ddmFormInstanceRecord::getFormInstanceRecordVersion
		).orElse(
			null
		);
	}

	private void _setServiceContextAttributes(
		ServiceContext serviceContext, boolean draft) {

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
	}

	private DDMFormInstanceRecord _updateFormInstanceRecord(
			Long formInstanceRecordId,
			FormInstanceRecordForm formInstanceRecordForm, Language language,
			FormInstanceRecordServiceContext formInstanceRecordServiceContext)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			_ddmFormInstanceRecordService.getFormInstanceRecord(
				formInstanceRecordId);

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecord.getFormInstance();

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMFormValues ddmFormValues =
			FormInstanceRecordResourceHelper.getDDMFormValues(
				formInstanceRecordForm.getFieldValues(),
				ddmStructure.getDDMForm(), language.getPreferredLocale());

		ServiceContext serviceContext =
			formInstanceRecordServiceContext.getServiceContext();

		_setServiceContextAttributes(
			serviceContext, formInstanceRecordForm.isDraft());

		return _ddmFormInstanceRecordService.updateFormInstanceRecord(
			formInstanceRecordId, false, ddmFormValues, serviceContext);
	}

	@Reference
	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

}