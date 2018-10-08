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

package com.liferay.users.admin.web.internal.helper;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Samuel Trong Tran
 */
public class AddressContactInformationHelper
	extends BaseContactInformationHelper<Address> {

	public AddressContactInformationHelper(
		Class entityClass, long entityClassPK,
		AddressLocalService addressLocalService,
		AddressService addressService) {

		_entityClass = entityClass;
		_entityClassPK = entityClassPK;
		_addressLocalService = addressLocalService;
		_addressService = addressService;
	}

	@Override
	protected Address addEntry(Address address) throws Exception {
		return _addressService.addAddress(
			_entityClass.getName(), _entityClassPK, address.getStreet1(),
			address.getStreet2(), address.getStreet3(), address.getCity(),
			address.getZip(), address.getRegionId(), address.getCountryId(),
			address.getTypeId(), address.isMailing(), address.isPrimary(),
			new ServiceContext());
	}

	@Override
	protected Address constructEntry(ActionRequest actionRequest)
		throws Exception {

		long addressId = ParamUtil.getLong(actionRequest, "primaryKey");

		String city = ParamUtil.getString(actionRequest, "addressCity");
		long countryId = ParamUtil.getLong(actionRequest, "addressCountryId");
		boolean mailing = ParamUtil.getBoolean(actionRequest, "addressMailing");
		boolean primary = ParamUtil.getBoolean(actionRequest, "addressPrimary");
		long regionId = ParamUtil.getLong(actionRequest, "addressRegionId");
		String street1 = ParamUtil.getString(actionRequest, "addressStreet1");
		String street2 = ParamUtil.getString(actionRequest, "addressStreet2");
		String street3 = ParamUtil.getString(actionRequest, "addressStreet3");
		long typeId = ParamUtil.getLong(actionRequest, "addressTypeId");
		String zip = ParamUtil.getString(actionRequest, "addressZip");

		if (Validator.isNull(street1) && Validator.isNull(street2) &&
			Validator.isNull(street3) && Validator.isNull(city) &&
			Validator.isNull(zip) && (countryId == 0)) {

			return null;
		}

		Address address = _addressLocalService.createAddress(addressId);

		address.setStreet1(street1);
		address.setStreet2(street2);
		address.setStreet3(street3);
		address.setCity(city);
		address.setZip(zip);
		address.setRegionId(regionId);
		address.setCountryId(countryId);
		address.setTypeId(typeId);
		address.setMailing(mailing);
		address.setPrimary(primary);

		return address;
	}

	@Override
	protected void deleteEntry(long addressId) throws Exception {
		_addressService.deleteAddress(addressId);
	}

	@Override
	protected List<Address> getEntries() throws Exception {
		return _addressService.getAddresses(
			_entityClass.getName(), _entityClassPK);
	}

	@Override
	protected Address getEntry(long addressId) throws Exception {
		return _addressService.getAddress(addressId);
	}

	@Override
	protected long getEntryId(Address address) {
		return address.getAddressId();
	}

	@Override
	protected boolean isPrimaryEntry(Address address) {
		return address.isPrimary();
	}

	@Override
	protected void setEntryPrimary(Address address, boolean primary) {
		address.setPrimary(primary);
	}

	@Override
	protected void updateEntry(Address address) throws Exception {
		_addressService.updateAddress(
			address.getAddressId(), address.getStreet1(), address.getStreet2(),
			address.getStreet3(), address.getCity(), address.getZip(),
			address.getRegionId(), address.getCountryId(), address.getTypeId(),
			address.isMailing(), address.isPrimary());
	}

	private final AddressLocalService _addressLocalService;
	private final AddressService _addressService;
	private final Class _entityClass;
	private final long _entityClassPK;

}