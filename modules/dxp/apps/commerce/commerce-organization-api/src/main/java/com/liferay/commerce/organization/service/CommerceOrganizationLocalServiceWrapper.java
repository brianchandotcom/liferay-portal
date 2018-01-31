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
 * Provides a wrapper for {@link CommerceOrganizationLocalService}.
 *
 * @author Marco Leo
 * @see CommerceOrganizationLocalService
 * @generated
 */
@ProviderType
public class CommerceOrganizationLocalServiceWrapper
	implements CommerceOrganizationLocalService,
		ServiceWrapper<CommerceOrganizationLocalService> {
	public CommerceOrganizationLocalServiceWrapper(
		CommerceOrganizationLocalService commerceOrganizationLocalService) {
		_commerceOrganizationLocalService = commerceOrganizationLocalService;
	}

	@Override
	public com.liferay.portal.kernel.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationLocalService.addOrganization(parentOrganizationId,
			name, type, serviceContext);
	}

	@Override
	public void addOrganizationUsers(long organizationId,
		java.lang.String[] emailAddresses,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceOrganizationLocalService.addOrganizationUsers(organizationId,
			emailAddresses, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.model.Address getOrganizationPrimaryAddress(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationLocalService.getOrganizationPrimaryAddress(organizationId);
	}

	@Override
	public com.liferay.portal.kernel.model.EmailAddress getOrganizationPrimaryEmailAddress(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationLocalService.getOrganizationPrimaryEmailAddress(organizationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceOrganizationLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public boolean isB2BOrganization(long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationLocalService.isB2BOrganization(organizationId);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizations(
		long organizationId, java.lang.String type, java.lang.String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort[] sorts)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrganizationLocalService.searchOrganizations(organizationId,
			type, keywords, start, end, sorts);
	}

	@Override
	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceOrganizationLocalService.unsetOrganizationUsers(organizationId,
			userIds);
	}

	@Override
	public CommerceOrganizationLocalService getWrappedService() {
		return _commerceOrganizationLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceOrganizationLocalService commerceOrganizationLocalService) {
		_commerceOrganizationLocalService = commerceOrganizationLocalService;
	}

	private CommerceOrganizationLocalService _commerceOrganizationLocalService;
}