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
		long parentOrganization, java.lang.String name, java.lang.String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationService.addOrganization(parentOrganization,
			name, type, serviceContext);
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
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceOrganizationService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizations(
		com.liferay.portal.kernel.model.Organization organization,
		java.lang.String type, java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort[] sorts)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationService.searchOrganizations(organization,
			type, keywords, start, end, sorts);
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