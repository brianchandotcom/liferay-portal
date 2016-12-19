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

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
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
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for DDMDataProviderInstance. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceServiceUtil
 * @see com.liferay.dynamic.data.mapping.service.base.DDMDataProviderInstanceServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=ddm", "json.web.service.context.path=DDMDataProviderInstance"}, service = DDMDataProviderInstanceService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDMDataProviderInstanceService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMDataProviderInstanceServiceUtil} to access the ddm data provider instance remote service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public DDMDataProviderInstance addDataProviderInstance(long groupId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		DDMFormValues ddmFormValues, java.lang.String type,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMDataProviderInstance fetchDataProviderInstance(
		long dataProviderInstanceId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMDataProviderInstance fetchDataProviderInstanceByUuid(
		java.lang.String uuid) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMDataProviderInstance getDataProviderInstance(
		long dataProviderInstanceId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMDataProviderInstance getDataProviderInstanceByUuid(
		java.lang.String uuid) throws PortalException;

	public DDMDataProviderInstance updateDataProviderInstance(
		long dataProviderInstanceId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String name, java.lang.String description, boolean andOperator);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMDataProviderInstance> search(long companyId,
		long[] groupIds, java.lang.String keywords, int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMDataProviderInstance> search(long companyId,
		long[] groupIds, java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator);

	public void deleteDataProviderInstance(long dataProviderInstanceId)
		throws PortalException;
}