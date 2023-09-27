/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public EROrganizationLocalServiceWrapper() {
		this(null);
	}

	public EROrganizationLocalServiceWrapper(
		EROrganizationLocalService erOrganizationLocalService) {

		_erOrganizationLocalService = erOrganizationLocalService;
	}

	@Override
	public com.liferay.portal.kernel.model.Organization addOrUpdateOrganization(
			String externalReferenceCode, long userId,
			long parentOrganizationId, String name, String type, long regionId,
			long countryId, long statusId, String comments, boolean site,
			boolean hasLogo, byte[] logoBytes,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _erOrganizationLocalService.addOrUpdateOrganization(
			externalReferenceCode, userId, parentOrganizationId, name, type,
			regionId, countryId, statusId, comments, site, hasLogo, logoBytes,
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