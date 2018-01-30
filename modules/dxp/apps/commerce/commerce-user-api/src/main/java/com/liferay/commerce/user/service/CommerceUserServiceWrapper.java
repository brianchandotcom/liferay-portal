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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceUserService}.
 *
 * @author Marco Leo
 * @see CommerceUserService
 * @generated
 */
@ProviderType
public class CommerceUserServiceWrapper implements CommerceUserService,
	ServiceWrapper<CommerceUserService> {
	public CommerceUserServiceWrapper(CommerceUserService commerceUserService) {
		_commerceUserService = commerceUserService;
	}

	@Override
	public com.liferay.portal.kernel.model.Organization addOrganization(
		long parentOrganization, java.lang.String name, java.lang.String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserService.addOrganization(parentOrganization, name,
			type, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.model.Address getOrganizationPrimaryAddress(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserService.getOrganizationPrimaryAddress(organizationId);
	}

	@Override
	public com.liferay.portal.kernel.model.EmailAddress getOrganizationPrimaryEmailAddress(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserService.getOrganizationPrimaryEmailAddress(organizationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceUserService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizations(
		com.liferay.portal.kernel.model.Organization organization,
		java.lang.String type, java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort[] sorts)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserService.searchOrganizations(organization, type,
			keywords, start, end, sorts);
	}

	@Override
	public CommerceUserService getWrappedService() {
		return _commerceUserService;
	}

	@Override
	public void setWrappedService(CommerceUserService commerceUserService) {
		_commerceUserService = commerceUserService;
	}

	private CommerceUserService _commerceUserService;
}