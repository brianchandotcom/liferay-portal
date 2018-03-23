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

package com.liferay.commerce.organization.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the local service interface for CommerceOrganization. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CommerceOrganizationLocalServiceUtil
 * @see com.liferay.commerce.organization.service.base.CommerceOrganizationLocalServiceBaseImpl
 * @see com.liferay.commerce.organization.service.impl.CommerceOrganizationLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceOrganizationLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrganizationLocalServiceUtil} to access the commerce organization local service. Add custom service methods to {@link com.liferay.commerce.organization.service.impl.CommerceOrganizationLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public Organization addOrganization(long parentOrganizationId,
		java.lang.String name, java.lang.String type,
		ServiceContext serviceContext) throws PortalException;

	public void addOrganizationUsers(long organizationId,
		java.lang.String[] emailAddresses, ServiceContext serviceContext)
		throws PortalException;

	public void configureB2BSite(long groupId, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Organization getAccountOrganization(long organizationId)
		throws PortalException;

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
	public boolean hasGroupOrganization(long siteGroupId, long organizationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isB2BOrganization(long organizationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<Organization> searchOrganizations(
		long userId, long parentOrganizationId, java.lang.String type,
		java.lang.String keywords, int start, int end, Sort[] sorts)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<Organization> searchOrganizationsByGroup(
		long groupId, long userId, java.lang.String type,
		java.lang.String keywords, int start, int end, Sort[] sorts)
		throws PortalException;

	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException;

	public Organization updateOrganization(long organizationId,
		java.lang.String name, long emailAddressId, java.lang.String address,
		long addressId, java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long regionId, long countryId, boolean logo, byte[] logoBytes,
		ServiceContext serviceContext) throws PortalException;
}