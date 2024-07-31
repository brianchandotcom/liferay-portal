/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.model.Address;

/**
 * Provides a wrapper for {@link AddressService}.
 *
 * @author Brian Wing Shun Chan
 * @see AddressService
 * @generated
 */
public class AddressServiceWrapper
	implements AddressService, ServiceWrapper<AddressService> {

	public AddressServiceWrapper() {
		this(null);
	}

	public AddressServiceWrapper(AddressService addressService) {
		_addressService = addressService;
	}

	@Override
	public Address addAddress(
			String externalReferenceCode, String className, long classPK,
			String name, String description, String street1, String street2,
			String street3, String city, String zip, long regionId,
			long countryId, long listTypeId, boolean mailing, boolean primary,
			String phoneNumber, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _addressService.addAddress(
			externalReferenceCode, className, classPK, name, description,
			street1, street2, street3, city, zip, regionId, countryId,
			listTypeId, mailing, primary, phoneNumber, serviceContext);
	}

	@Override
	public void deleteAddress(long addressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_addressService.deleteAddress(addressId);
	}

	@Override
	public Address getAddress(long addressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _addressService.getAddress(addressId);
	}

	@Override
	public java.util.List<Address> getAddresses(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _addressService.getAddresses(className, classPK);
	}

	@Override
	public java.util.List<Address> getListTypeAddresses(
			String className, long classPK, long[] listTypeIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _addressService.getListTypeAddresses(
			className, classPK, listTypeIds);
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
	public Address updateAddress(
			long addressId, String name, String description, String street1,
			String street2, String street3, String city, String zip,
			long regionId, long countryId, long listTypeId, boolean mailing,
			boolean primary, String phoneNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _addressService.updateAddress(
			addressId, name, description, street1, street2, street3, city, zip,
			regionId, countryId, listTypeId, mailing, primary, phoneNumber);
	}

	@Override
	public Address updateExternalReferenceCode(
			Address address, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _addressService.updateExternalReferenceCode(
			address, externalReferenceCode);
	}

	@Override
	public Address updateExternalReferenceCode(
			long addressId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _addressService.updateExternalReferenceCode(
			addressId, externalReferenceCode);
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