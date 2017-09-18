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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.service.base.CommerceAddressServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceAddressServiceImpl extends CommerceAddressServiceBaseImpl {

	@Override
	public CommerceAddress addCommerceAddress(
			long addressUserId, String name, String description, String street1,
			String street2, String street3, String city, String zip,
			long commerceRegionId, long commerceCountryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			ServiceContext serviceContext)
		throws PortalException {

		checkPermission(serviceContext.getScopeGroupId(), addressUserId);

		return commerceAddressLocalService.addCommerceAddress(
			addressUserId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			defaultBilling, defaultShipping, serviceContext);
	}

	@Override
	public void deleteCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.getCommerceAddress(commerceAddressId);

		checkPermission(commerceAddress);

		commerceAddressLocalService.deleteCommerceAddress(commerceAddress);
	}

	@Override
	public CommerceAddress fetchCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.fetchCommerceAddress(commerceAddressId);

		checkPermission(commerceAddress);

		return commerceAddress;
	}

	@Override
	public CommerceAddress getCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.getCommerceAddress(commerceAddressId);

		checkPermission(commerceAddress);

		return commerceAddress;
	}

	@Override
	public List<CommerceAddress> getCommerceAddresses(
			long groupId, long addressUserId)
		throws PortalException {

		checkPermission(groupId, addressUserId);

		return commerceAddressLocalService.getCommerceAddresses(
			groupId, addressUserId);
	}

	@Override
	public List<CommerceAddress> getCommerceAddresses(
			long groupId, long addressUserId, int start, int end,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws PortalException {

		checkPermission(groupId, addressUserId);

		return commerceAddressLocalService.getCommerceAddresses(
			groupId, addressUserId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAddressesCount(long groupId, long addressUserId)
		throws PortalException {

		checkPermission(groupId, addressUserId);

		return commerceAddressLocalService.getCommerceAddressesCount(
			groupId, addressUserId);
	}

	@Override
	public CommerceAddress updateCommerceAddress(
			long commerceAddressId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber, boolean defaultBilling, boolean defaultShipping,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.getCommerceAddress(commerceAddressId);

		checkPermission(commerceAddress);

		return commerceAddressLocalService.updateCommerceAddress(
			commerceAddress.getCommerceAddressId(), name, description, street1,
			street2, street3, city, zip, commerceRegionId, commerceCountryId,
			phoneNumber, defaultBilling, defaultShipping, serviceContext);
	}

	protected void checkPermission(CommerceAddress commerceAddress)
		throws PortalException {

		checkPermission(
			commerceAddress.getGroupId(), commerceAddress.getAddressUserId());
	}

	protected void checkPermission(long groupId, long addressUserId)
		throws PortalException {

		if ((addressUserId != getUserId()) &&
			!CommercePermission.contains(
				getPermissionChecker(), groupId,
				CommerceActionKeys.MANAGE_COMMERCE_ADDRESSES)) {

			throw new PrincipalException();
		}
	}

}