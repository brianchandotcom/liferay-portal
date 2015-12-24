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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDLRecordService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordService
 * @generated
 */
@ProviderType
public class DDLRecordServiceWrapper implements DDLRecordService,
	ServiceWrapper<DDLRecordService> {
	public DDLRecordServiceWrapper(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long groupId, long recordSetId, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordService.addRecord(groupId, recordSetId, displayIndex,
			ddmFormValues, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long groupId, long recordSetId, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordService.addRecord(groupId, recordSetId, displayIndex,
			fields, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long groupId, long recordSetId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordService.addRecord(groupId, recordSetId, displayIndex,
			fieldsMap, serviceContext);
	}

	@Override
	public void deleteRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddlRecordService.deleteRecord(recordId);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord deleteRecordLocale(
		long recordId, java.util.Locale locale,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordService.deleteRecordLocale(recordId, locale,
			serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddlRecordService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord getRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordService.getRecord(recordId);
	}

	@Override
	public void revertRecord(long recordId, java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddlRecordService.revertRecord(recordId, version, serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #revertRecord(long, String,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public void revertRecordVersion(long recordId, java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddlRecordService.revertRecordVersion(recordId, version, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long recordId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordService.updateRecord(recordId, displayIndex,
			fieldsMap, mergeFields, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long recordId, boolean majorVersion, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordService.updateRecord(recordId, majorVersion,
			displayIndex, ddmFormValues, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long recordId, boolean majorVersion, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.Fields fields,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordService.updateRecord(recordId, majorVersion,
			displayIndex, fields, mergeFields, serviceContext);
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