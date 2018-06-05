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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExternalReferenceOrganizationLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalReferenceOrganizationLocalService
 * @generated
 */
@ProviderType
public class ExternalReferenceOrganizationLocalServiceWrapper
	implements ExternalReferenceOrganizationLocalService,
		ServiceWrapper<ExternalReferenceOrganizationLocalService> {
	public ExternalReferenceOrganizationLocalServiceWrapper(
		ExternalReferenceOrganizationLocalService externalReferenceOrganizationLocalService) {
		_externalReferenceOrganizationLocalService = externalReferenceOrganizationLocalService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _externalReferenceOrganizationLocalService.getOSGiServiceIdentifier();
	}

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
	@Override
	public com.liferay.portal.kernel.model.Organization upsertOrganization(
		long userId, long parentOrganizationId, String name, String type,
		long regionId, long countryId, long statusId, String comments,
		boolean site, boolean logo, byte[] logoBytes,
		String externalReferenceCode,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _externalReferenceOrganizationLocalService.upsertOrganization(userId,
			parentOrganizationId, name, type, regionId, countryId, statusId,
			comments, site, logo, logoBytes, externalReferenceCode,
			serviceContext);
	}

	@Override
	public ExternalReferenceOrganizationLocalService getWrappedService() {
		return _externalReferenceOrganizationLocalService;
	}

	@Override
	public void setWrappedService(
		ExternalReferenceOrganizationLocalService externalReferenceOrganizationLocalService) {
		_externalReferenceOrganizationLocalService = externalReferenceOrganizationLocalService;
	}

	private ExternalReferenceOrganizationLocalService _externalReferenceOrganizationLocalService;
}