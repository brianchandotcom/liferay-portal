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

package com.liferay.commerce.user.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the remote service interface for CommerceUser. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceUserServiceUtil
 * @see com.liferay.commerce.user.service.base.CommerceUserServiceBaseImpl
 * @see com.liferay.commerce.user.service.impl.CommerceUserServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceUser"}, service = CommerceUserService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceUserService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceUserServiceUtil} to access the commerce user remote service. Add custom service methods to {@link com.liferay.commerce.user.service.impl.CommerceUserServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public Organization addOrganization(long parentOrganization,
		java.lang.String name, java.lang.String type,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Address getOrganizationPrimaryAddress(long organizationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public EmailAddress getOrganizationPrimaryEmailAddress(long organizationId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<Organization> searchOrganizations(
		Organization organization, java.lang.String type,
		java.lang.String keywords, int start, int end, Sort[] sorts)
		throws PortalException;
}