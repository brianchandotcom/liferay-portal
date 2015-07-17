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

package com.liferay.dynamic.data.lists.service.http.jsonws;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.service.DDLRecordService;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for DDLRecord. This utility wraps
 * {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=ddl", "json.web.service.context.path=DDLRecord"}, service = DDLRecordJsonService.class)
@JSONWebService
@ProviderType
public class DDLRecordJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long groupId, long recordSetId, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addRecord(groupId, recordSetId, displayIndex,
			ddmFormValues, serviceContext);
	}

	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long groupId, long recordSetId, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addRecord(groupId, recordSetId, displayIndex, fields,
			serviceContext);
	}

	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long groupId, long recordSetId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addRecord(groupId, recordSetId, displayIndex,
			fieldsMap, serviceContext);
	}

	public void deleteRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deleteRecord(recordId);
	}

	public com.liferay.dynamic.data.lists.model.DDLRecord deleteRecordLocale(
		long recordId, java.util.Locale locale,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.deleteRecordLocale(recordId, locale, serviceContext);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public com.liferay.dynamic.data.lists.model.DDLRecord getRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getRecord(recordId);
	}

	public void revertRecord(long recordId, java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.revertRecord(recordId, version, serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #revertRecord(long, String,
	ServiceContext)}
	*/
	@Deprecated
	public void revertRecordVersion(long recordId, java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.revertRecordVersion(recordId, version, serviceContext);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long recordId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateRecord(recordId, displayIndex, fieldsMap,
			mergeFields, serviceContext);
	}

	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long recordId, boolean majorVersion, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateRecord(recordId, majorVersion, displayIndex,
			ddmFormValues, serviceContext);
	}

	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long recordId, boolean majorVersion, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateRecord(recordId, majorVersion, displayIndex,
			fields, mergeFields, serviceContext);
	}

	@Reference
	protected void setService(DDLRecordService service) {
		_service = service;
	}

	private DDLRecordService _service;
}