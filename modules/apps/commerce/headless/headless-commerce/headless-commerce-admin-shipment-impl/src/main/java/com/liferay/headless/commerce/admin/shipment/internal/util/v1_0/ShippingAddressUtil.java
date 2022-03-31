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

package com.liferay.headless.commerce.admin.shipment.internal.util.v1_0;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Riccardo Alberti
 */
public class ShippingAddressUtil {

	public static CommerceShipment updateShippingAddress(
			CommerceAddressService commerceAddressService,
			CommerceShipmentService commerceShipmentService,
			CommerceShipment commerceShipment, CountryService countryService,
			RegionService regionService, ShippingAddress shippingAddress,
			ServiceContextHelper serviceContextHelper)
		throws Exception {

		CommerceAddress commerceAddress =
			commerceAddressService.fetchCommerceAddress(
				commerceShipment.getCommerceAddressId());

		Country country = countryService.fetchCountryByA2(
			commerceShipment.getCompanyId(),
			shippingAddress.getCountryISOCode());

		return commerceShipmentService.updateAddress(
			commerceShipment.getCommerceShipmentId(),
			GetterUtil.get(
				shippingAddress.getName(),
				BeanPropertiesUtil.getString(commerceAddress, "name")),
			GetterUtil.get(
				shippingAddress.getDescription(),
				BeanPropertiesUtil.getString(commerceAddress, "description")),
			GetterUtil.get(
				shippingAddress.getStreet1(),
				BeanPropertiesUtil.getString(commerceAddress, "street1")),
			GetterUtil.get(
				shippingAddress.getStreet2(),
				BeanPropertiesUtil.getString(commerceAddress, "street2")),
			GetterUtil.get(
				shippingAddress.getStreet3(),
				BeanPropertiesUtil.getString(commerceAddress, "street3")),
			GetterUtil.get(
				shippingAddress.getCity(),
				BeanPropertiesUtil.getString(commerceAddress, "city")),
			GetterUtil.get(
				shippingAddress.getZip(),
				BeanPropertiesUtil.getString(commerceAddress, "zip")),
			_getRegionId(
				commerceAddress, country, regionService, shippingAddress),
			_getCountryId(commerceAddress, country, shippingAddress),
			GetterUtil.get(
				shippingAddress.getPhoneNumber(),
				BeanPropertiesUtil.getString(commerceAddress, "phoneNumber")),
			serviceContextHelper.getServiceContext());
	}

	private static long _getCountryId(
		CommerceAddress commerceAddress, Country country,
		ShippingAddress shippingAddress) {

		if (Validator.isNull(shippingAddress.getCountryISOCode()) &&
			(commerceAddress != null)) {

			return commerceAddress.getCountryId();
		}

		if (country == null) {
			return 0;
		}

		return country.getCountryId();
	}

	private static long _getRegionId(
			CommerceAddress commerceAddress, Country country,
			RegionService regionService, ShippingAddress shippingAddress)
		throws Exception {

		if (Validator.isNull(shippingAddress.getRegionISOCode()) &&
			(commerceAddress != null)) {

			return commerceAddress.getRegionId();
		}

		Region region = regionService.fetchRegion(
			_getCountryId(commerceAddress, country, shippingAddress),
			shippingAddress.getRegionISOCode());

		if (region == null) {
			return 0;
		}

		return region.getRegionId();
	}

}