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

package com.liferay.commerce.model.impl;

import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeLocalServiceUtil;
import com.liferay.portal.kernel.service.RegionLocalServiceUtil;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceAddressImpl extends CommerceAddressBaseImpl {

	public static CommerceAddress fromAddress(Address address) {
		if (address == null) {
			return null;
		}

		CommerceAddress commerceAddress = new CommerceAddressImpl();

		Map<String, BiConsumer<CommerceAddress, Object>>
			attributeSetterBiConsumers =
				commerceAddress.getAttributeSetterBiConsumers();

		Map<String, Object> modelAttributes = address.getModelAttributes();

		for (Map.Entry<String, Object> entry : modelAttributes.entrySet()) {
			BiConsumer<CommerceAddress, Object>
				commerceAddressObjectBiConsumer =
					attributeSetterBiConsumers.get(entry.getKey());

			if (commerceAddressObjectBiConsumer != null) {
				commerceAddressObjectBiConsumer.accept(
					commerceAddress, entry.getValue());
			}
		}

		commerceAddress.setCommerceAddressId(address.getAddressId());
		commerceAddress.setDefaultBilling(
			toCommerceAccountDefaultBilling(address));
		commerceAddress.setDefaultShipping(
			toCommerceAccountDefaultShipping(address));
		commerceAddress.setType(toCommerceAddressType(address));

		return commerceAddress;
	}

	public static boolean isAccountEntryAddress(Address address) {
		if (Objects.equals(
				AccountEntry.class.getName(), address.getClassName())) {

			return true;
		}

		return false;
	}

	public static long toAddressTypeId(int commerceAddressType) {
		if (CommerceAddressConstants.ADDRESS_TYPE_BILLING ==
				commerceAddressType) {

			return _getAddressTypeId(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING);
		}
		else if (CommerceAddressConstants.ADDRESS_TYPE_SHIPPING ==
					commerceAddressType) {

			return _getAddressTypeId(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_SHIPPING);
		}

		return _getAddressTypeId(
			AccountListTypeConstants.
				ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING);
	}

	public static boolean toCommerceAccountDefaultBilling(Address address) {
		if (isAccountEntryAddress(address)) {
			AccountEntry accountEntry =
				AccountEntryLocalServiceUtil.fetchAccountEntry(
					address.getClassPK());

			if (accountEntry != null) {
				Address defaultBillingAddress =
					accountEntry.getDefaultBillingAddress();

				if ((defaultBillingAddress != null) &&
					(defaultBillingAddress.getAddressId() ==
						address.getAddressId())) {

					return true;
				}
			}
		}

		return false;
	}

	public static boolean toCommerceAccountDefaultShipping(Address address) {
		if (isAccountEntryAddress(address)) {
			AccountEntry accountEntry =
				AccountEntryLocalServiceUtil.fetchAccountEntry(
					address.getClassPK());

			if (accountEntry != null) {
				Address defaultShippingAddress =
					accountEntry.getDefaultShippingAddress();

				if ((defaultShippingAddress != null) &&
					(defaultShippingAddress.getAddressId() ==
						address.getAddressId())) {

					return true;
				}
			}
		}

		return false;
	}

	public static int toCommerceAddressType(Address address) {
		ListType listType = address.getType();

		String listTypeName = listType.getName();

		if (Objects.equals(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING,
				listTypeName)) {

			return CommerceAddressConstants.ADDRESS_TYPE_BILLING;
		}
		else if (Objects.equals(
					AccountListTypeConstants.
						ACCOUNT_ENTRY_ADDRESS_TYPE_SHIPPING,
					listTypeName)) {

			return CommerceAddressConstants.ADDRESS_TYPE_SHIPPING;
		}
		else if (Objects.equals(
					AccountListTypeConstants.
						ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING,
					listTypeName)) {

			return CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING;
		}

		return CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING;
	}

	@Override
	public Country fetchCountry() {
		return CountryLocalServiceUtil.fetchCountry(getCountryId());
	}

	@Override
	public Country getCountry() throws PortalException {
		return CountryLocalServiceUtil.getCountry(getCountryId());
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Address.class.getName(), getPrimaryKey());
	}

	@Override
	public Region getRegion() throws PortalException {
		long regionId = getRegionId();

		if (regionId > 0) {
			return RegionLocalServiceUtil.getRegion(regionId);
		}

		return null;
	}

	@Override
	public boolean isGeolocated() {
		if ((getLatitude() == 0) && (getLongitude() == 0)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isSameAddress(CommerceAddress commerceAddress) {
		if (Objects.equals(getName(), commerceAddress.getName()) &&
			Objects.equals(getStreet1(), commerceAddress.getStreet1()) &&
			Objects.equals(getStreet2(), commerceAddress.getStreet2()) &&
			Objects.equals(getStreet3(), commerceAddress.getStreet3()) &&
			Objects.equals(getCity(), commerceAddress.getCity()) &&
			Objects.equals(getZip(), commerceAddress.getZip()) &&
			(getRegionId() == commerceAddress.getRegionId()) &&
			(getCountryId() == commerceAddress.getCountryId()) &&
			Objects.equals(
				getPhoneNumber(), commerceAddress.getPhoneNumber())) {

			return true;
		}

		return false;
	}

	private static long _getAddressTypeId(String name) {
		ListType listType = ListTypeLocalServiceUtil.getListType(
			name, AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);

		return listType.getListTypeId();
	}

}