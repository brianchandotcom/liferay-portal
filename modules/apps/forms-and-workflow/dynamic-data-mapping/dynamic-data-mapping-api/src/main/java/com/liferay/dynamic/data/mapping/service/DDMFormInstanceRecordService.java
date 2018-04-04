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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service interface for DDMFormInstanceRecord. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordServiceUtil
 * @see com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceRecordServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=ddm", "json.web.service.context.path=DDMFormInstanceRecord"}, service = DDMFormInstanceRecordService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDMFormInstanceRecordService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMFormInstanceRecordServiceUtil} to access the ddm form instance record remote service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public DDMFormInstanceRecord addFormInstanceRecord(long groupId,
		long ddmFormInstanceId, DDMFormValues ddmFormValues,
		ServiceContext serviceContext) throws PortalException;

	public void deleteFormInstanceRecord(long ddmFormInstanceRecordId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormInstanceRecord getFormInstanceRecord(
		long ddmFormInstanceRecordId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId, int status, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFormInstanceRecordsCount(long ddmFormInstanceId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public void revertFormInstanceRecord(long ddmFormInstanceRecordId,
		java.lang.String version, ServiceContext serviceContext)
		throws PortalException;

	public DDMFormInstanceRecord updateFormInstanceRecord(
		long ddmFormInstanceRecordId, boolean majorVersion,
		DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException;
}