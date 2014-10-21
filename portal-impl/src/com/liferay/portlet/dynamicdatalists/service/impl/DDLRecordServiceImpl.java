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

package com.liferay.portlet.dynamicdatalists.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.service.base.DDLRecordServiceBaseImpl;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLRecordSetPermission;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * dynamic data list (DDL) records. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDLRecordServiceImpl extends DDLRecordServiceBaseImpl {

	@Override
	public DDLRecord addRecord(
			long groupId, long recordSetId, int displayIndex,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.ADD_RECORD);

		return ddlRecordLocalService.addRecord(
			getGuestOrUserId(), groupId, recordSetId, displayIndex,
			ddmFormValues, serviceContext);
	}

	@Override
	public DDLRecord addRecord(
			long groupId, long recordSetId, int displayIndex, Fields fields,
			ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.ADD_RECORD);

		return ddlRecordLocalService.addRecord(
			getGuestOrUserId(), groupId, recordSetId, displayIndex, fields,
			serviceContext);
	}

	@Override
	public DDLRecord addRecord(
			long groupId, long recordSetId, int displayIndex,
			Map<String, Serializable> fieldsMap, ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.ADD_RECORD);

		return ddlRecordLocalService.addRecord(
			getGuestOrUserId(), groupId, recordSetId, displayIndex, fieldsMap,
			serviceContext);
	}

	@Override
	public void deleteRecord(long recordId) throws PortalException {
		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.DELETE);

		ddlRecordLocalService.deleteRecord(record);
	}

	@Override
	public DDLRecord deleteRecordLocale(
			long recordId, Locale locale, ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.deleteRecordLocale(
			recordId, locale, serviceContext);
	}

	@Override
	public DDLRecord getRecord(long recordId) throws PortalException {
		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.VIEW);

		return record;
	}

	@Override
	public void revertRecord(
			long recordId, String version, ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.UPDATE);

		ddlRecordLocalService.revertRecord(
			getGuestOrUserId(), recordId, version, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #revertRecord(long, long,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void revertRecordVersion(
			long recordId, String version, ServiceContext serviceContext)
		throws PortalException {

		revertRecord(recordId, version, serviceContext);
	}

	@Override
	public DDLRecord updateRecord(
			long recordId, boolean majorVersion, int displayIndex,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.updateRecord(
			getUserId(), recordId, majorVersion, displayIndex, ddmFormValues,
			serviceContext);
	}

	@Override
	public DDLRecord updateRecord(
			long recordId, boolean majorVersion, int displayIndex,
			Fields fields, boolean mergeFields, ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.updateRecord(
			getUserId(), recordId, majorVersion, displayIndex, fields,
			mergeFields, serviceContext);
	}

	@Override
	public DDLRecord updateRecord(
			long recordId, int displayIndex,
			Map<String, Serializable> fieldsMap, boolean mergeFields,
			ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.updateRecord(
			getUserId(), recordId, displayIndex, fieldsMap, mergeFields,
			serviceContext);
	}

}