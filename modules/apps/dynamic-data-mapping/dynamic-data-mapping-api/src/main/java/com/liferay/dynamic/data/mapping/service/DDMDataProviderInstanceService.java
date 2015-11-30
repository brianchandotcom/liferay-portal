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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;

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
	 * Never modify or reference this interface directly. Always use {@link DDMDataProviderInstanceServiceUtil} to access the d d m data provider instance remote service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance addDataProviderInstance(
		long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void deleteDataProviderInstance(long dataProviderInstanceId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance fetchDataProviderInstance(
		long dataProviderInstanceId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance getDataProviderInstance(
		long dataProviderInstanceId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		long companyId, long[] groupIds, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		long companyId, long[] groupIds, java.lang.String name,
		java.lang.String description, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String name, java.lang.String description, boolean andOperator);

	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance updateDataProviderInstance(
		long dataProviderInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}