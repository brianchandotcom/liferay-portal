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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.CommonPermissionUtil;
import com.liferay.portal.service.base.AddressServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class AddressServiceImpl extends AddressServiceBaseImpl {

	@Override
	public Address addAddress(
			String className, long classPK, String street1, String street2,
			String street3, String city, String zip, long regionId,
			long countryId, long typeId, boolean mailing, boolean primary,
			ServiceContext serviceContext)
		throws PortalException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return addressLocalService.addAddress(
			getUserId(), className, classPK, street1, street2, street3, city,
			zip, regionId, countryId, typeId, mailing, primary, serviceContext);
	}

	@Override
	public void deleteAddress(long addressId) throws PortalException {
		Address address = addressPersistence.findByPrimaryKey(addressId);

		CommonPermissionUtil.check(
			getPermissionChecker(), address.getClassNameId(),
			address.getClassPK(), ActionKeys.UPDATE);

		addressLocalService.deleteAddress(address);
	}

	@Override
	public Address getAddress(long addressId) throws PortalException {
		Address address = addressPersistence.findByPrimaryKey(addressId);

		CommonPermissionUtil.check(
			getPermissionChecker(), address.getClassNameId(),
			address.getClassPK(), ActionKeys.VIEW);

		return address;
	}

	@Override
	public List<Address> getAddresses(String className, long classPK)
		throws PortalException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.VIEW);

		User user = getUser();

		return addressLocalService.getAddresses(
			user.getCompanyId(), className, classPK);
	}

	@Override
	public Address updateAddress(
			long addressId, String street1, String street2, String street3,
			String city, String zip, long regionId, long countryId, long typeId,
			boolean mailing, boolean primary)
		throws PortalException {

		Address address = addressPersistence.findByPrimaryKey(addressId);

		CommonPermissionUtil.check(
			getPermissionChecker(), address.getClassNameId(),
			address.getClassPK(), ActionKeys.UPDATE);

		return addressLocalService.updateAddress(
			addressId, street1, street2, street3, city, zip, regionId,
			countryId, typeId, mailing, primary);
	}

}