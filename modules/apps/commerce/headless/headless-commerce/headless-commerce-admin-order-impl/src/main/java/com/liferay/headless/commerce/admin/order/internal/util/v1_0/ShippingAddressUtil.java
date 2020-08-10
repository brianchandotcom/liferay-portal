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

package com.liferay.headless.commerce.admin.order.internal.util.v1_0;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCountryServiceUtil;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceRegionLocalServiceUtil;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class ShippingAddressUtil {

	public static CommerceOrder upsertShippingAddress(
			CommerceAddressService commerceAddressService,
			CommerceOrderService commerceOrderService,
			CommerceOrder commerceOrder, ShippingAddress shippingAddress,
			ServiceContext serviceContext)
		throws Exception {

		if (commerceOrder.getShippingAddressId() > 0) {
			return _updateCommerceOrderShippingAddress(
				commerceAddressService, commerceOrderService, commerceOrder,
				shippingAddress, serviceContext);
		}

		CommerceAddress commerceAddress = _addCommerceAddress(
			commerceAddressService, commerceOrder, shippingAddress,
			serviceContext);

		return commerceOrderService.updateShippingAddress(
			commerceOrder.getCommerceOrderId(), commerceAddress.getName(),
			commerceAddress.getDescription(), commerceAddress.getStreet1(),
			commerceAddress.getStreet2(), commerceAddress.getStreet3(),
			commerceAddress.getCity(), commerceAddress.getZip(),
			commerceAddress.getCommerceRegionId(),
			commerceAddress.getCommerceCountryId(),
			commerceAddress.getPhoneNumber(), serviceContext);
	}

	private static CommerceAddress _addCommerceAddress(
			CommerceAddressService commerceAddressService,
			CommerceOrder commerceOrder, ShippingAddress shippingAddress,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceCountry commerceCountry =
			CommerceCountryServiceUtil.getCommerceCountry(
				commerceOrder.getCompanyId(),
				shippingAddress.getCountryISOCode());

		return commerceAddressService.addCommerceAddress(
			commerceOrder.getModelClassName(),
			commerceOrder.getCommerceOrderId(), shippingAddress.getName(),
			shippingAddress.getDescription(), shippingAddress.getStreet1(),
			shippingAddress.getStreet2(), shippingAddress.getStreet3(),
			shippingAddress.getCity(), shippingAddress.getZip(),
			_getCommerceRegionId(null, commerceCountry, shippingAddress),
			commerceCountry.getCommerceCountryId(),
			shippingAddress.getPhoneNumber(), false, false, serviceContext);
	}

	private static long _getCommerceCountryId(CommerceCountry commerceCountry) {
		if (commerceCountry == null) {
			return 0;
		}

		return commerceCountry.getCommerceCountryId();
	}

	private static long _getCommerceRegionId(
			CommerceAddress commerceAddress, CommerceCountry commerceCountry,
			ShippingAddress shippingAddress)
		throws PortalException {

		if (Validator.isNull(shippingAddress.getRegionISOCode()) &&
			(commerceAddress != null)) {

			return commerceAddress.getCommerceRegionId();
		}

		if (commerceCountry == null) {
			return 0;
		}

		CommerceRegion commerceRegion =
			CommerceRegionLocalServiceUtil.getCommerceRegion(
				commerceCountry.getCommerceCountryId(),
				shippingAddress.getRegionISOCode());

		return commerceRegion.getCommerceRegionId();
	}

	private static String _getDescription(CommerceAddress commerceAddress) {
		if (commerceAddress == null) {
			return null;
		}

		return commerceAddress.getDescription();
	}

	private static String _getPhoneNumber(CommerceAddress commerceAddress) {
		if (commerceAddress == null) {
			return null;
		}

		return commerceAddress.getPhoneNumber();
	}

	private static String _getStreet2(CommerceAddress commerceAddress) {
		if (commerceAddress == null) {
			return null;
		}

		return commerceAddress.getStreet2();
	}

	private static String _getStreet3(CommerceAddress commerceAddress) {
		if (commerceAddress == null) {
			return null;
		}

		return commerceAddress.getStreet3();
	}

	private static String _getZip(CommerceAddress commerceAddress) {
		if (commerceAddress == null) {
			return null;
		}

		return commerceAddress.getZip();
	}

	private static CommerceOrder _updateCommerceOrderShippingAddress(
			CommerceAddressService commerceAddressService,
			CommerceOrderService commerceOrderService,
			CommerceOrder commerceOrder, ShippingAddress shippingAddress,
			ServiceContext serviceContext)
		throws Exception {

		CommerceAddress commerceAddress =
			commerceAddressService.fetchCommerceAddress(
				commerceOrder.getShippingAddressId());

		CommerceCountry commerceCountry = null;

		if (commerceAddress != null) {
			commerceCountry = commerceAddress.getCommerceCountry();
		}

		return commerceOrderService.updateShippingAddress(
			commerceOrder.getCommerceOrderId(), shippingAddress.getName(),
			GetterUtil.get(
				shippingAddress.getDescription(),
				_getDescription(commerceAddress)),
			shippingAddress.getStreet1(),
			GetterUtil.get(
				shippingAddress.getStreet2(), _getStreet2(commerceAddress)),
			GetterUtil.get(
				shippingAddress.getStreet3(), _getStreet3(commerceAddress)),
			shippingAddress.getCity(),
			GetterUtil.get(shippingAddress.getZip(), _getZip(commerceAddress)),
			_getCommerceRegionId(
				commerceAddress, commerceCountry, shippingAddress),
			_getCommerceCountryId(commerceCountry),
			GetterUtil.get(
				shippingAddress.getPhoneNumber(),
				_getPhoneNumber(commerceAddress)),
			serviceContext);
	}

}