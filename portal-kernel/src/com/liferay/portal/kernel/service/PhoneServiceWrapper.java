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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link PhoneService}.
 *
 * @author Brian Wing Shun Chan
 * @see PhoneService
 * @generated
 */
@ProviderType
public class PhoneServiceWrapper implements PhoneService,
	ServiceWrapper<PhoneService> {
	public PhoneServiceWrapper(PhoneService phoneService) {
		_phoneService = phoneService;
	}

	@Override
	public com.liferay.portal.kernel.model.Phone addPhone(String className,
		long classPK, String number, String extension, long typeId,
		boolean primary, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _phoneService.addPhone(className, classPK, number, extension,
			typeId, primary, serviceContext);
	}

	@Override
	public void deletePhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_phoneService.deletePhone(phoneId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _phoneService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.Phone getPhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _phoneService.getPhone(phoneId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Phone> getPhones(
		String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _phoneService.getPhones(className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.model.Phone updatePhone(long phoneId,
		String number, String extension, long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _phoneService.updatePhone(phoneId, number, extension, typeId,
			primary);
	}

	@Override
	public PhoneService getWrappedService() {
		return _phoneService;
	}

	@Override
	public void setWrappedService(PhoneService phoneService) {
		_phoneService = phoneService;
	}

	private PhoneService _phoneService;
}