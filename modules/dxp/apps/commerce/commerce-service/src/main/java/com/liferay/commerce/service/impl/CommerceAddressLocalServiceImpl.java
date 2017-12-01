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

import com.liferay.commerce.exception.CommerceAddressCityException;
import com.liferay.commerce.exception.CommerceAddressCountryException;
import com.liferay.commerce.exception.CommerceAddressNameException;
import com.liferay.commerce.exception.CommerceAddressStreetException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceGeocoder;
import com.liferay.commerce.service.base.CommerceAddressLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceAddressLocalServiceImpl
	extends CommerceAddressLocalServiceBaseImpl {

	@Override
	public CommerceAddress addCommerceAddress(
			long addressUserId, String name, String description, String street1,
			String street2, String street3, String city, String zip,
			long commerceRegionId, long commerceCountryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(
			0, groupId, addressUserId, name, street1, city, commerceCountryId,
			defaultBilling, defaultShipping);

		long commerceAddressId = counterLocalService.increment();

		CommerceAddress commerceAddress = commerceAddressPersistence.create(
			commerceAddressId);

		commerceAddress.setGroupId(groupId);
		commerceAddress.setCompanyId(user.getCompanyId());
		commerceAddress.setUserId(user.getUserId());
		commerceAddress.setUserName(user.getFullName());
		commerceAddress.setAddressUserId(addressUserId);
		commerceAddress.setName(name);
		commerceAddress.setDescription(description);
		commerceAddress.setStreet1(street1);
		commerceAddress.setStreet2(street2);
		commerceAddress.setStreet3(street3);
		commerceAddress.setCity(city);
		commerceAddress.setZip(zip);
		commerceAddress.setCommerceRegionId(commerceRegionId);
		commerceAddress.setCommerceCountryId(commerceCountryId);
		commerceAddress.setPhoneNumber(phoneNumber);
		commerceAddress.setDefaultBilling(defaultBilling);
		commerceAddress.setDefaultShipping(defaultShipping);

		commerceAddressPersistence.update(commerceAddress);

		return commerceAddress;
	}

	@Override
	public CommerceAddress deleteCommerceAddress(
			CommerceAddress commerceAddress)
		throws PortalException {

		// Commerce address

		commerceAddressPersistence.remove(commerceAddress);

		// Commerce carts

		List<CommerceCart> commerceCarts =
			commerceCartLocalService.getCommerceCartsByBillingAddress(
				commerceAddress.getCommerceAddressId());

		removeCommerceCartAddresses(
			commerceCarts, commerceAddress.getCommerceAddressId());

		commerceCarts =
			commerceCartLocalService.getCommerceCartsByShippingAddress(
				commerceAddress.getCommerceAddressId());

		removeCommerceCartAddresses(
			commerceCarts, commerceAddress.getCommerceAddressId());

		return commerceAddress;
	}

	@Override
	public CommerceAddress deleteCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressPersistence.findByPrimaryKey(commerceAddressId);

		return commerceAddressLocalService.deleteCommerceAddress(
			commerceAddress);
	}

	@Override
	public void deleteCountryCommerceAddresses(long commerceCountryId)
		throws PortalException {

		List<CommerceAddress> commerceAddresses =
			commerceAddressPersistence.findByCommerceCountryId(
				commerceCountryId);

		for (CommerceAddress commerceAddress : commerceAddresses) {
			commerceAddressLocalService.deleteCommerceAddress(commerceAddress);
		}
	}

	@Override
	public void deleteRegionCommerceAddresses(long commerceRegionId)
		throws PortalException {

		List<CommerceAddress> commerceAddresses =
			commerceAddressPersistence.findByCommerceRegionId(commerceRegionId);

		for (CommerceAddress commerceAddress : commerceAddresses) {
			commerceAddressLocalService.deleteCommerceAddress(commerceAddress);
		}
	}

	@Override
	public void deleteUserCommerceAddresses(long addressUserId)
		throws PortalException {

		List<CommerceAddress> commerceAddresses =
			commerceAddressPersistence.findByAddressUserId(addressUserId);

		for (CommerceAddress commerceAddress : commerceAddresses) {
			commerceAddressLocalService.deleteCommerceAddress(commerceAddress);
		}
	}

	@Override
	public CommerceAddress geolocateCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressPersistence.findByPrimaryKey(commerceAddressId);

		double[] coordinates = commerceGeocoder.getCoordinates(
			commerceAddress.getStreet1(), commerceAddress.getCity(),
			commerceAddress.getZip(), commerceAddress.getCommerceRegion(),
			commerceAddress.getCommerceCountry());

		commerceAddress.setLatitude(coordinates[0]);
		commerceAddress.setLongitude(coordinates[1]);

		return commerceAddressPersistence.update(commerceAddress);
	}

	@Override
	public List<CommerceAddress> getCommerceAddresses(
		long groupId, long addressUserId) {

		return commerceAddressPersistence.findByG_A(groupId, addressUserId);
	}

	@Override
	public List<CommerceAddress> getCommerceAddresses(
		long groupId, long addressUserId, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return commerceAddressPersistence.findByG_A(
			groupId, addressUserId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAddressesCount(long groupId, long addressUserId) {
		return commerceAddressPersistence.countByG_A(groupId, addressUserId);
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
			commerceAddressPersistence.findByPrimaryKey(commerceAddressId);

		validate(
			commerceAddress.getCommerceAddressId(),
			commerceAddress.getGroupId(), commerceAddress.getAddressUserId(),
			name, street1, city, commerceCountryId, defaultBilling,
			defaultShipping);

		commerceAddress.setName(name);
		commerceAddress.setDescription(description);
		commerceAddress.setStreet1(street1);
		commerceAddress.setStreet2(street2);
		commerceAddress.setStreet3(street3);
		commerceAddress.setCity(city);
		commerceAddress.setZip(zip);
		commerceAddress.setCommerceRegionId(commerceRegionId);
		commerceAddress.setCommerceCountryId(commerceCountryId);
		commerceAddress.setLatitude(0);
		commerceAddress.setLongitude(0);
		commerceAddress.setPhoneNumber(phoneNumber);
		commerceAddress.setDefaultBilling(defaultBilling);
		commerceAddress.setDefaultShipping(defaultShipping);

		commerceAddressPersistence.update(commerceAddress);

		return commerceAddress;
	}

	protected void removeCommerceCartAddresses(
			List<CommerceCart> commerceCarts, long commerceAddressId)
		throws PortalException {

		for (CommerceCart commerceCart : commerceCarts) {
			long billingAddressId = commerceCart.getBillingAddressId();
			long shippingAddressId = commerceCart.getShippingAddressId();

			if (billingAddressId == commerceAddressId) {
				billingAddressId = 0;
			}

			if (shippingAddressId == commerceAddressId) {
				shippingAddressId = 0;
			}

			commerceCartLocalService.updateCommerceCart(
				commerceCart.getCommerceCartId(), billingAddressId,
				shippingAddressId, commerceCart.getCommerceShippingMethodId(),
				commerceCart.getShippingOptionName());
		}
	}

	protected void validate(
			long commerceAddressId, long groupId, long addressUserId,
			String name, String street1, String city, long commerceCountryId,
			boolean defaultBilling, boolean defaultShipping)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new CommerceAddressNameException();
		}

		if (Validator.isNull(street1)) {
			throw new CommerceAddressStreetException();
		}

		if (Validator.isNull(city)) {
			throw new CommerceAddressCityException();
		}

		if (commerceCountryId <= 0) {
			throw new CommerceAddressCountryException();
		}

		if (defaultBilling) {
			List<CommerceAddress> commerceAddresses =
				commerceAddressPersistence.findByG_A_DB(
					groupId, addressUserId, true);

			for (CommerceAddress commerceAddress : commerceAddresses) {
				if (commerceAddress.getCommerceAddressId() !=
						commerceAddressId) {

					commerceAddress.setDefaultBilling(false);

					commerceAddressPersistence.update(commerceAddress);
				}
			}
		}

		if (defaultShipping) {
			List<CommerceAddress> commerceAddresses =
				commerceAddressPersistence.findByG_A_DS(
					groupId, addressUserId, true);

			for (CommerceAddress commerceAddress : commerceAddresses) {
				if (commerceAddress.getCommerceAddressId() !=
						commerceAddressId) {

					commerceAddress.setDefaultShipping(false);

					commerceAddressPersistence.update(commerceAddress);
				}
			}
		}
	}

	@ServiceReference(type = CommerceGeocoder.class)
	protected CommerceGeocoder commerceGeocoder;

}