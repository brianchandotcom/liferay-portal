/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * Provides a wrapper for {@link AddressService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       AddressService
 * @generated
 */
public class AddressServiceWrapper implements AddressService,
	ServiceWrapper<AddressService> {
	public AddressServiceWrapper(AddressService addressService) {
		_addressService = addressService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _addressService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_addressService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.Address addAddress(
		java.lang.String className, long classPK, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long regionId,
		long countryId, int typeId, boolean mailing, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _addressService.addAddress(className, classPK, street1, street2,
			street3, city, zip, regionId, countryId, typeId, mailing, primary);
	}

	public void deleteAddress(long addressId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_addressService.deleteAddress(addressId);
	}

	public com.liferay.portal.model.Address getAddress(long addressId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _addressService.getAddress(addressId);
	}

	public java.util.List<com.liferay.portal.model.Address> getAddresses(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _addressService.getAddresses(className, classPK);
	}

	public com.liferay.portal.model.Address updateAddress(long addressId,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long regionId, long countryId, int typeId, boolean mailing,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _addressService.updateAddress(addressId, street1, street2,
			street3, city, zip, regionId, countryId, typeId, mailing, primary);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public AddressService getWrappedAddressService() {
		return _addressService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedAddressService(AddressService addressService) {
		_addressService = addressService;
	}

	public AddressService getWrappedService() {
		return _addressService;
	}

	public void setWrappedService(AddressService addressService) {
		_addressService = addressService;
	}

	private AddressService _addressService;
}