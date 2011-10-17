/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecordService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLRecordService
 * @generated
 */
public class DDLRecordServiceWrapper implements DDLRecordService,
	ServiceWrapper<DDLRecordService> {
	public DDLRecordServiceWrapper(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecord addRecord(
		long groupId, long recordSetId, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordService.addRecord(groupId, recordSetId, displayIndex,
			fields, serviceContext);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecord addRecord(
		long groupId, long recordSetId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordService.addRecord(groupId, recordSetId, displayIndex,
			fieldsMap, serviceContext);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecord updateRecord(
		long recordId, boolean majorVersion, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordService.updateRecord(recordId, majorVersion,
			displayIndex, fields, mergeFields, serviceContext);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecord updateRecord(
		long recordId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordService.updateRecord(recordId, displayIndex,
			fieldsMap, mergeFields, serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public DDLRecordService getWrappedDDLRecordService() {
		return _ddlRecordService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedDDLRecordService(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	public DDLRecordService getWrappedService() {
		return _ddlRecordService;
	}

	public void setWrappedService(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	private DDLRecordService _ddlRecordService;
}