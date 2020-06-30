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
 * Provides a wrapper for {@link ERUserGroupLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ERUserGroupLocalService
 * @generated
 */
public class ERUserGroupLocalServiceWrapper
	implements ERUserGroupLocalService,
			   ServiceWrapper<ERUserGroupLocalService> {

	public ERUserGroupLocalServiceWrapper(
		ERUserGroupLocalService erUserGroupLocalService) {

		_erUserGroupLocalService = erUserGroupLocalService;
	}

	@Override
	public com.liferay.portal.kernel.model.UserGroup addOrUpdateUserGroup(
			String externalReferenceCode, long userId, long companyId,
			String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _erUserGroupLocalService.addOrUpdateUserGroup(
			externalReferenceCode, userId, companyId, name, description,
			serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _erUserGroupLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public ERUserGroupLocalService getWrappedService() {
		return _erUserGroupLocalService;
	}

	@Override
	public void setWrappedService(
		ERUserGroupLocalService erUserGroupLocalService) {

		_erUserGroupLocalService = erUserGroupLocalService;
	}

	private ERUserGroupLocalService _erUserGroupLocalService;

}