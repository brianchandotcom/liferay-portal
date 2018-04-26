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
 * Provides a wrapper for {@link AddressService}.
 *
 * @author Brian Wing Shun Chan
 * @see AddressService
 * @generated
 */
@ProviderType
public class AddressServiceWrapper implements AddressService,
	ServiceWrapper<AddressService> {
	public AddressServiceWrapper(AddressService addressService) {
		_addressService = addressService;
	}

	@Override
	public com.liferay.portal.kernel.model.Address addAddress(
		String className, long classPK, String street1, String street2,
		String street3, String city, String zip, long regionId, long countryId,
		long typeId, boolean mailing, boolean primary,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _addressService.addAddress(className, classPK, street1, street2,
			street3, city, zip, regionId, countryId, typeId, mailing, primary,
			serviceContext);
	}

	@Override
	public void deleteAddress(long addressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_addressService.deleteAddress(addressId);
	}

	@Override
	public com.liferay.portal.kernel.model.Address getAddress(long addressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _addressService.getAddress(addressId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Address> getAddresses(
		String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _addressService.getAddresses(className, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _addressService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.Address updateAddress(
		long addressId, String street1, String street2, String street3,
		String city, String zip, long regionId, long countryId, long typeId,
		boolean mailing, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _addressService.updateAddress(addressId, street1, street2,
			street3, city, zip, regionId, countryId, typeId, mailing, primary);
	}

	@Override
	public AddressService getWrappedService() {
		return _addressService;
	}

	@Override
	public void setWrappedService(AddressService addressService) {
		_addressService = addressService;
	}

	private AddressService _addressService;
}