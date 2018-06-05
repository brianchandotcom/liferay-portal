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

package com.liferay.external.reference.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the local service interface for ExternalReferenceOrganization. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalReferenceOrganizationLocalServiceUtil
 * @see com.liferay.external.reference.service.base.ExternalReferenceOrganizationLocalServiceBaseImpl
 * @see com.liferay.external.reference.service.impl.ExternalReferenceOrganizationLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ExternalReferenceOrganizationLocalService
	extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExternalReferenceOrganizationLocalServiceUtil} to access the external reference organization local service. Add custom service methods to {@link com.liferay.external.reference.service.impl.ExternalReferenceOrganizationLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	/**
	* Add or update an organization.
	*
	* @param userId the primary key of the user
	* @param parentOrganizationId the primary key of organization's parent
	organization
	* @param name the organization's name
	* @param type the organization's type
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param logo whether to update the ogranization's logo
	* @param logoBytes the new logo image data
	* @param externalReferenceCode the organization's external reference
	code
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs and asset tag
	names for the organization, and merge expando bridge
	attributes for the organization.
	* @review
	* @return the organization
	*/
	public Organization upsertOrganization(long userId,
		long parentOrganizationId, String name, String type, long regionId,
		long countryId, long statusId, String comments, boolean site,
		boolean logo, byte[] logoBytes, String externalReferenceCode,
		ServiceContext serviceContext) throws PortalException;
}