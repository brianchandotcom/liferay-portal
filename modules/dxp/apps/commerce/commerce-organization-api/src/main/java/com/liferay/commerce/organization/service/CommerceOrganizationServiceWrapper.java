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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrganizationService}.
 *
 * @author Marco Leo
 * @see CommerceOrganizationService
 * @generated
 */
@ProviderType
public class CommerceOrganizationServiceWrapper
	implements CommerceOrganizationService,
		ServiceWrapper<CommerceOrganizationService> {
	public CommerceOrganizationServiceWrapper(
		CommerceOrganizationService commerceOrganizationService) {
		_commerceOrganizationService = commerceOrganizationService;
	}

	@Override
	public com.liferay.portal.kernel.model.Organization addOrganization(
		long parentOrganizationId, String name, String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationService.addOrganization(parentOrganizationId,
			name, type, serviceContext);
	}

	@Override
	public void addOrganizationUsers(long organizationId,
		String[] emailAddresses,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceOrganizationService.addOrganizationUsers(organizationId,
			emailAddresses, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.model.Organization getOrganization(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationService.getOrganization(organizationId);
	}

	@Override
	public com.liferay.portal.kernel.model.Address getOrganizationPrimaryAddress(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationService.getOrganizationPrimaryAddress(organizationId);
	}

	@Override
	public com.liferay.portal.kernel.model.EmailAddress getOrganizationPrimaryEmailAddress(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationService.getOrganizationPrimaryEmailAddress(organizationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrganizationService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizations(
		long userId, long organizationId, String type, String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort[] sorts)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationService.searchOrganizations(userId,
			organizationId, type, keywords, start, end, sorts);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizationsByGroup(
		long groupId, long userId, String type, String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort[] sorts)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationService.searchOrganizationsByGroup(groupId,
			userId, type, keywords, start, end, sorts);
	}

	@Override
	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceOrganizationService.unsetOrganizationUsers(organizationId,
			userIds);
	}

	@Override
	public com.liferay.portal.kernel.model.Organization updateOrganization(
		long organizationId, String name, long emailAddressId, String address,
		long addressId, String street1, String street2, String street3,
		String city, String zip, long regionId, long countryId, boolean logo,
		byte[] logoBytes,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationService.updateOrganization(organizationId,
			name, emailAddressId, address, addressId, street1, street2,
			street3, city, zip, regionId, countryId, logo, logoBytes,
			serviceContext);
	}

	@Override
	public CommerceOrganizationService getWrappedService() {
		return _commerceOrganizationService;
	}

	@Override
	public void setWrappedService(
		CommerceOrganizationService commerceOrganizationService) {
		_commerceOrganizationService = commerceOrganizationService;
	}

	private CommerceOrganizationService _commerceOrganizationService;
}