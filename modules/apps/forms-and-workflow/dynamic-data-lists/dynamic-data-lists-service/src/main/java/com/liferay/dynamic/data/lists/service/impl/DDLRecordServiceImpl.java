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

package com.liferay.dynamic.data.lists.service.impl;

import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.base.DDLRecordServiceBaseImpl;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordPermission;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * dynamic data lists (DDL) records. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDLRecordServiceImpl extends DDLRecordServiceBaseImpl {

	/**
	 * Adds a record referencing a record set.
	 *
	 * @param  groupId the primary key of the record's group
	 * @param  recordSetId the primary key of the record set
	 * @param  displayIndex the index position in which the record is displayed
	 *         in the spreadsheat view
	 * @param  ddmFormValues the record values. See {@link DDMFormValues}.
	 * @param  serviceContext the service context to be applied. This can set
	 *         the UUID, guest permissions, and group permissions for the
	 *         record.
	 * @return the record
	 */
	@Override
	public DDLRecord addRecord(
			long groupId, long recordSetId, int displayIndex,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, DDLActionKeys.ADD_RECORD);

		return ddlRecordLocalService.addRecord(
			getGuestOrUserId(), groupId, recordSetId, displayIndex,
			ddmFormValues, serviceContext);
	}

	/**
	 * Adds a record referencing a record set.
	 *
	 * @param      groupId the primary key of the record's group
	 * @param      recordSetId the primary key of the record set
	 * @param      displayIndex the index position in which the record is
	 *             displayed in the spreadsheat view
	 * @param      fields the record values. See {@link Fields}.
	 * @param      serviceContext the service context to be applied. This can
	 *             set the UUID, guest permissions, and group permissions for
	 *             the record.
	 * @return     the record
	 * @deprecated As of 7.0.0, replaced by {@link #addRecord(long, long, int,
	 *             DDMFormValues, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DDLRecord addRecord(
			long groupId, long recordSetId, int displayIndex, Fields fields,
			ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, DDLActionKeys.ADD_RECORD);

		return ddlRecordLocalService.addRecord(
			getGuestOrUserId(), groupId, recordSetId, displayIndex, fields,
			serviceContext);
	}

	/**
	 * Adds a record referencing a record set.
	 *
	 * @param      groupId the primary key of the record's group
	 * @param      recordSetId the primary key of the record set
	 * @param      displayIndex the index position in which the record is
	 *             displayed in the spreadsheat view
	 * @param      fieldsMap the record values. The fieldsMap is a map of field
	 *             names and its Serializable values.
	 * @param      serviceContext the service context to be applied. This can
	 *             set the UUID, guest permissions, and group permissions for
	 *             the record.
	 * @return     the record
	 * @deprecated As of 7.0.0, replaced by {@link #addRecord(long, long, int,
	 *             DDMFormValues, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DDLRecord addRecord(
			long groupId, long recordSetId, int displayIndex,
			Map<String, Serializable> fieldsMap, ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, DDLActionKeys.ADD_RECORD);

		return ddlRecordLocalService.addRecord(
			getGuestOrUserId(), groupId, recordSetId, displayIndex, fieldsMap,
			serviceContext);
	}

	/**
	 * Deletes the record and its resources.
	 *
	 * @param recordId the primary key of the record to be deleted
	 */
	@Override
	public void deleteRecord(long recordId) throws PortalException {
		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordPermission.check(
			getPermissionChecker(), record.getRecordId(), ActionKeys.DELETE);

		ddlRecordLocalService.deleteRecord(record);
	}

	/**
	 * Disassociates a record from the specified locale.
	 *
	 * @param      recordId the primary key of the record
	 * @param      locale the locale that will be used to delete the localized
	 *             record values
	 * @param      serviceContext the service context to be applied. This can
	 *             set the record modified date.
	 * @deprecated As of 7.0.0, replaced by {@link #updateRecord(long, boolean,
	 *             int, DDMFormValues, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DDLRecord deleteRecordLocale(
			long recordId, Locale locale, ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordPermission.check(
			getPermissionChecker(), record.getRecordId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.deleteRecordLocale(
			recordId, locale, serviceContext);
	}

	/**
	 * Returns the record with the ID.
	 *
	 * @param  recordId the primary key of the record
	 * @return the record with the ID
	 */
	@Override
	public DDLRecord getRecord(long recordId) throws PortalException {
		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordPermission.check(
			getPermissionChecker(), record.getRecordId(), ActionKeys.VIEW);

		return record;
	}

	/**
	 * Reverts the record to a given version.
	 *
	 * @param recordId the primary key of the record
	 * @param version the version to be reverted
	 * @param serviceContext the service context to be applied. This can set the
	 *        record modified date.
	 */
	@Override
	public void revertRecord(
			long recordId, String version, ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordPermission.check(
			getPermissionChecker(), record.getRecordId(), ActionKeys.UPDATE);

		ddlRecordLocalService.revertRecord(
			getGuestOrUserId(), recordId, version, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #revertRecord(long, String,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public void revertRecordVersion(
			long recordId, String version, ServiceContext serviceContext)
		throws PortalException {

		revertRecord(recordId, version, serviceContext);
	}

	/**
	 * Updates a record, replacing its display index and values.
	 *
	 * @param  recordId the primary key of the record
	 * @param  majorVersion whether this update is a major change. Major changes
	 *         causes the increment of the major version number.
	 * @param  displayIndex the index position in which the record is displayed
	 *         in the spreadsheat view
	 * @param  ddmFormValues the record values. See {@link DDMFormValues}.
	 * @param  serviceContext the service context to be applied. This can set
	 *         the record modified date.
	 * @return the record
	 */
	@Override
	public DDLRecord updateRecord(
			long recordId, boolean majorVersion, int displayIndex,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordPermission.check(
			getPermissionChecker(), record.getRecordId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.updateRecord(
			getUserId(), recordId, majorVersion, displayIndex, ddmFormValues,
			serviceContext);
	}

	/**
	 * Updates a record, replacing its display index and values.
	 *
	 * @param      recordId the primary key of the record
	 * @param      majorVersion whether this update is a major change. Major
	 *             changes causes the increment of the major version number.
	 * @param      displayIndex the index position in which the record is
	 *             displayed in the spreadsheat view
	 * @param      fields the record values. See {@link Fields}.
	 * @param      serviceContext the service context to be applied. This can
	 *             set the record modified date.
	 * @return     the record
	 * @deprecated As of 7.0.0, replaced by {@link #updateRecord(long, boolean,
	 *             int, DDMFormValues, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DDLRecord updateRecord(
			long recordId, boolean majorVersion, int displayIndex,
			Fields fields, boolean mergeFields, ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordPermission.check(
			getPermissionChecker(), record.getRecordId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.updateRecord(
			getUserId(), recordId, majorVersion, displayIndex, fields,
			mergeFields, serviceContext);
	}

	/**
	 * Updates a record, replacing its display index and values.
	 *
	 * @param      recordId the primary key of the record
	 * @param      displayIndex the index position in which the record is
	 *             displayed in the spreadsheat view
	 * @param      fieldsMap the record values. The fieldsMap is a map of field
	 *             names and its Serializable values.
	 * @param      serviceContext the service context to be applied. This can
	 *             set the record modified date.
	 * @return     the record
	 * @deprecated As of 7.0.0, replaced by {@link #updateRecord(long, boolean,
	 *             int, DDMFormValues, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DDLRecord updateRecord(
			long recordId, int displayIndex,
			Map<String, Serializable> fieldsMap, boolean mergeFields,
			ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordPermission.check(
			getPermissionChecker(), record.getRecordId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.updateRecord(
			getUserId(), recordId, displayIndex, fieldsMap, mergeFields,
			serviceContext);
	}

}