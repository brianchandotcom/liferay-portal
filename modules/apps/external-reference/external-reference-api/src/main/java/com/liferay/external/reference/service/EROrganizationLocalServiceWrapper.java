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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link EROrganizationLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see EROrganizationLocalService
 * @generated
 */
public class EROrganizationLocalServiceWrapper
	implements EROrganizationLocalService,
			   ServiceWrapper<EROrganizationLocalService> {

	public EROrganizationLocalServiceWrapper(
		EROrganizationLocalService erOrganizationLocalService) {

		_erOrganizationLocalService = erOrganizationLocalService;
	}

	@Override
	public com.liferay.portal.kernel.model.Organization addOrUpdateOrganization(
			String externalReferenceCode, long userId,
			long parentOrganizationId, String name, String type, long regionId,
			long countryId, long statusId, String comments, boolean site,
			boolean logo, byte[] logoBytes,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _erOrganizationLocalService.addOrUpdateOrganization(
			externalReferenceCode, userId, parentOrganizationId, name, type,
			regionId, countryId, statusId, comments, site, logo, logoBytes,
			serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _erOrganizationLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public EROrganizationLocalService getWrappedService() {
		return _erOrganizationLocalService;
	}

	@Override
	public void setWrappedService(
		EROrganizationLocalService erOrganizationLocalService) {

		_erOrganizationLocalService = erOrganizationLocalService;
	}

	private EROrganizationLocalService _erOrganizationLocalService;

}