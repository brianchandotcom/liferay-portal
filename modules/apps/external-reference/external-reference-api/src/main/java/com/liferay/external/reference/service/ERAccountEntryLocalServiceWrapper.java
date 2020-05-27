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
 * Provides a wrapper for {@link ERAccountEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ERAccountEntryLocalService
 * @generated
 */
public class ERAccountEntryLocalServiceWrapper
	implements ERAccountEntryLocalService,
			   ServiceWrapper<ERAccountEntryLocalService> {

	public ERAccountEntryLocalServiceWrapper(
		ERAccountEntryLocalService erAccountEntryLocalService) {

		_erAccountEntryLocalService = erAccountEntryLocalService;
	}

	@Override
	public com.liferay.account.model.AccountEntry addOrUpdateAccountEntry(
			String externalReferenceCode, long userId,
			long parentAccountEntryId, String name, String description,
			boolean deleteLogo, String[] domains, byte[] logoBytes,
			String taxId, String type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _erAccountEntryLocalService.addOrUpdateAccountEntry(
			externalReferenceCode, userId, parentAccountEntryId, name,
			description, deleteLogo, domains, logoBytes, taxId, type, status,
			serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _erAccountEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public ERAccountEntryLocalService getWrappedService() {
		return _erAccountEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ERAccountEntryLocalService erAccountEntryLocalService) {

		_erAccountEntryLocalService = erAccountEntryLocalService;
	}

	private ERAccountEntryLocalService _erAccountEntryLocalService;

}