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
 * Provides a wrapper for {@link ExternalReferenceUserGroupLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalReferenceUserGroupLocalService
 * @generated
 */
@ProviderType
public class ExternalReferenceUserGroupLocalServiceWrapper
	implements ExternalReferenceUserGroupLocalService,
		ServiceWrapper<ExternalReferenceUserGroupLocalService> {
	public ExternalReferenceUserGroupLocalServiceWrapper(
		ExternalReferenceUserGroupLocalService externalReferenceUserGroupLocalService) {
		_externalReferenceUserGroupLocalService = externalReferenceUserGroupLocalService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _externalReferenceUserGroupLocalService.getOSGiServiceIdentifier();
	}

	/**
	* Add or update an user group.
	*
	* @param userId the primary key of the user
	* @param companyId the primary key of the user group's company
	* @param name the user group's name
	* @param description the user group's description
	* @param externalReferenceCode the user group's external reference code
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user group.
	* @review
	* @return the user group
	*/
	@Override
	public com.liferay.portal.kernel.model.UserGroup upsertUserGroup(
		long userId, long companyId, String name, String description,
		String externalReferenceCode,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _externalReferenceUserGroupLocalService.upsertUserGroup(userId,
			companyId, name, description, externalReferenceCode, serviceContext);
	}

	@Override
	public ExternalReferenceUserGroupLocalService getWrappedService() {
		return _externalReferenceUserGroupLocalService;
	}

	@Override
	public void setWrappedService(
		ExternalReferenceUserGroupLocalService externalReferenceUserGroupLocalService) {
		_externalReferenceUserGroupLocalService = externalReferenceUserGroupLocalService;
	}

	private ExternalReferenceUserGroupLocalService _externalReferenceUserGroupLocalService;
}