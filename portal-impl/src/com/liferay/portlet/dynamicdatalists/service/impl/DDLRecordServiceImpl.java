/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.base.DDLRecordServiceBaseImpl;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLRecordSetPermission;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

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
			long groupId, long recordSetId, int displayIndex, Fields fields,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.ADD_RECORD);

		return ddlRecordLocalService.addRecord(
			getGuestOrUserId(), groupId, recordSetId, displayIndex, fieldsMap,
			serviceContext);
	}

	public void deleteRecord(long recordId)
		throws PortalException, SystemException {

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.DELETE);

		ddlRecordLocalService.deleteRecord(record);
	}

	@Override
	public DDLRecord deleteRecordLocale(
			long recordId, Locale locale, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.deleteRecordLocale(
			recordId, locale, serviceContext);
	}

	@Override
	public DDLRecord getRecord(long recordId)
		throws PortalException, SystemException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.VIEW);

		return record;
	}

	public DDLRecordVersion getRecordVersion(long recordId, String version)
		throws PortalException, SystemException {

		return ddlRecordVersionPersistence.findByR_V(recordId, version);
	}


	public void revertRecordVersion(
		long userId, long recordId, String version,
		ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecordVersion recordVersion = getRecordVersion(recordId, version);

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.UPDATE);


		if (!recordVersion.isApproved()) {
			return;
		}

		Fields fields = StorageEngineUtil.getFields(
			recordVersion.getDDMStorageId());

		updateRecord(
			userId, recordId, recordVersion.getDisplayIndex(), fields,
				serviceContext);
	}

	@Override
	public DDLRecord updateRecord(
			long recordId, boolean majorVersion, int displayIndex,
			Fields fields, boolean mergeFields, ServiceContext serviceContext)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.updateRecord(
			getUserId(), recordId, displayIndex, fieldsMap, mergeFields,
			serviceContext);
	}


	public DDLRecord updateRecord(
			long userId, long recordId, int displayIndex,
			Fields fields, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Record

		User user = userPersistence.findByPrimaryKey(userId);

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		record.setModifiedDate(serviceContext.getModifiedDate(null));

		ddlRecordPersistence.update(record);

		// Record version

		DDLRecordVersion recordVersion = record.getLatestRecordVersion();

		DDLRecordSet recordSet = record.getRecordSet();

		long ddmStorageId = StorageEngineUtil.create(
			recordSet.getCompanyId(), recordSet.getDDMStructureId(), fields,
			serviceContext);
		String version = getNextVersion(
			recordVersion.getVersion(), true,
			serviceContext.getWorkflowAction());

		recordVersion = addRecordVersion(
			user, record, ddmStorageId, version, displayIndex,
			WorkflowConstants.STATUS_DRAFT);


		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), record.getGroupId(), userId,
			DDLRecord.class.getName(), recordVersion.getRecordVersionId(),
			recordVersion, serviceContext);

		return record;
	}

	protected DDLRecordVersion addRecordVersion(
		User user, DDLRecord record, long ddmStorageId, String version,
		int displayIndex, int status)
		throws SystemException {

		long recordVersionId = counterLocalService.increment();

		DDLRecordVersion recordVersion = ddlRecordVersionPersistence.create(
			recordVersionId);

		recordVersion.setGroupId(record.getGroupId());
		recordVersion.setCompanyId(record.getCompanyId());
		recordVersion.setUserId(user.getUserId());
		recordVersion.setUserName(user.getFullName());
		recordVersion.setCreateDate(record.getModifiedDate());
		recordVersion.setDDMStorageId(ddmStorageId);
		recordVersion.setRecordSetId(record.getRecordSetId());
		recordVersion.setRecordId(record.getRecordId());
		recordVersion.setVersion(version);
		recordVersion.setDisplayIndex(displayIndex);
		recordVersion.setStatus(status);
		recordVersion.setStatusByUserId(user.getUserId());
		recordVersion.setStatusByUserName(user.getFullName());
		recordVersion.setStatusDate(record.getModifiedDate());

		ddlRecordVersionPersistence.update(recordVersion);

		return recordVersion;
	}

	protected String getNextVersion(
		String version, boolean majorVersion, int workflowAction) {

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			majorVersion = false;
		}

		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		if (majorVersion) {
			versionParts[0]++;
			versionParts[1] = 0;
		}
		else {
			versionParts[1]++;
		}

		return versionParts[0] + StringPool.PERIOD + versionParts[1];
	}
}