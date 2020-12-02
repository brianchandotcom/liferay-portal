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

package com.liferay.dynamic.data.lists.service;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDLRecordService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordService
 * @generated
 */
public class DDLRecordServiceWrapper
	implements DDLRecordService, ServiceWrapper<DDLRecordService> {

	public DDLRecordServiceWrapper(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	/**
	 * Adds a record referencing the record set.
	 *
	 * @param groupId the primary key of the record's group
	 * @param recordSetId the primary key of the record set
	 * @param displayIndex the index position in which the record is displayed
	 in the spreadsheet view
	 * @param ddmFormValues the record values. See <code>DDMFormValues</code>
	 in the <code>dynamic.data.mapping.api</code> module.
	 * @param serviceContext the service context to be applied. This can set
	 the UUID, guest permissions, and group permissions for the
	 record.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public DDLRecord addRecord(
			long groupId, long recordSetId, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordService.addRecord(
			groupId, recordSetId, displayIndex, ddmFormValues, serviceContext);
	}

	/**
	 * Deletes the record and its resources.
	 *
	 * @param recordId the primary key of the record to be deleted
	 * @throws PortalException
	 */
	@Override
	public void deleteRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddlRecordService.deleteRecord(recordId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddlRecordService.getOSGiServiceIdentifier();
	}

	/**
	 * Returns the record with the ID.
	 *
	 * @param recordId the primary key of the record
	 * @return the record with the ID
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public DDLRecord getRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordService.getRecord(recordId);
	}

	/**
	 * Returns all the records matching the record set ID
	 *
	 * @param recordSetId the record's record set ID
	 * @return the matching records
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public java.util.List<DDLRecord> getRecords(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordService.getRecords(recordSetId);
	}

	/**
	 * Reverts the record to a given version.
	 *
	 * @param recordId the primary key of the record
	 * @param version the version to be reverted
	 * @param serviceContext the service context to be applied. This can set
	 the record modified date.
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void revertRecord(
			long recordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddlRecordService.revertRecord(recordId, version, serviceContext);
	}

	/**
	 * Updates a record, replacing its display index and values.
	 *
	 * @param recordId the primary key of the record
	 * @param majorVersion whether this update is a major change. A major
	 change increments the record's major version number.
	 * @param displayIndex the index position in which the record is displayed
	 in the spreadsheet view
	 * @param ddmFormValues the record values. See <code>DDMFormValues</code>
	 in the <code>dynamic.data.mapping.api</code> module.
	 * @param serviceContext the service context to be applied. This can set
	 the record modified date.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public DDLRecord updateRecord(
			long recordId, boolean majorVersion, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordService.updateRecord(
			recordId, majorVersion, displayIndex, ddmFormValues,
			serviceContext);
	}

	@Override
	public DDLRecordService getWrappedService() {
		return _ddlRecordService;
	}

	@Override
	public void setWrappedService(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	private DDLRecordService _ddlRecordService;

}