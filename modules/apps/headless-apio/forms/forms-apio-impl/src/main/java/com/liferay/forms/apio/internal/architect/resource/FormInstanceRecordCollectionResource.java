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

package com.liferay.forms.apio.internal.architect.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.forms.apio.architect.identifier.FormInstanceIdentifier;
import com.liferay.forms.apio.architect.identifier.FormInstanceRecordIdentifier;
import com.liferay.forms.apio.internal.architect.helper.FormInstanceRecordResourceHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.InternalServerErrorException;
import java.util.List;

/**
 * Provides the information necessary to expose FormInstanceRecord resources
 * through a web API. The resources are mapped from the internal model {@code
 * DDMFormInstanceRecord}.
 * @author Paulo Cruz
 */
@Component(immediate = true)
public class FormInstanceRecordCollectionResource
	implements NestedCollectionResource<DDMFormInstanceRecord, Long,
		FormInstanceRecordIdentifier, Long, FormInstanceIdentifier> {

	@Override
	public NestedCollectionRoutes<DDMFormInstanceRecord, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<DDMFormInstanceRecord, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "form-instance-record";
	}

	@Override
	public ItemRoutes<DDMFormInstanceRecord, Long> itemRoutes(
		ItemRoutes.Builder<DDMFormInstanceRecord, Long> builder) {

		return builder.addGetter(
			this::_getFormInstanceRecord
		).build();
	}

	@Override
	public Representor<DDMFormInstanceRecord, Long> representor(
		Representor.Builder<DDMFormInstanceRecord, Long> builder) {

		return builder.types(
			"FormInstanceRecord"
		).identifier(
			DDMFormInstanceRecord::getFormInstanceRecordId
		).addBidirectionalModel(
			"form-instance", "form-instance-record",
			FormInstanceIdentifier.class,
			DDMFormInstanceRecord::getFormInstanceId
		).addDate(
			"createDate", DDMFormInstanceRecord::getCreateDate
		).addDate(
			"modifiedDate", DDMFormInstanceRecord::getModifiedDate
		).addDate(
			"lastPublishDate", DDMFormInstanceRecord::getLastPublishDate
		).addNumber(
			"companyId", DDMFormInstanceRecord::getCompanyId
		).addNumber(
			"groupId", DDMFormInstanceRecord::getGroupId
		).addNumber(
			"userId", DDMFormInstanceRecord::getUserId
		).addNumber(
			"versionUserId", DDMFormInstanceRecord::getVersionUserId
		).addString(
			"userName", DDMFormInstanceRecord::getUserName
		).addString(
			"versionUserName", DDMFormInstanceRecord::getVersionUserName
		).addString(
			"version", DDMFormInstanceRecord::getVersion
		).addLocalizedString(
			"fieldValues", FormInstanceRecordResourceHelper::getFieldValuesJSON
		).build();
	}

	private DDMFormInstanceRecord _getFormInstanceRecord(
		Long formInstanceRecordId) {

		try {
			return _ddmFormInstanceRecordService.getFormInstanceRecord(
				formInstanceRecordId);
		}
		catch (PortalException pe) {
			throw new InternalServerErrorException(pe.getMessage(), pe);
		}
	}

	private PageItems<DDMFormInstanceRecord> _getPageItems(
		Pagination pagination, Long formInstanceId) {

		try {
			List<DDMFormInstanceRecord> ddmFormInstances =
				_ddmFormInstanceRecordService.getFormInstanceRecords(
					formInstanceId, WorkflowConstants.STATUS_ANY,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);

			int count =
				_ddmFormInstanceRecordService.getFormInstanceRecordsCount(
					formInstanceId);

			return new PageItems<>(ddmFormInstances, count);
		}
		catch (PortalException pe) {
			throw new InternalServerErrorException(pe.getMessage(), pe);
		}
	}

	@Reference
	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

}