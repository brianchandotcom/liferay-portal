/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.util.v1_0;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.service.RegionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class ShippingAddressUtil {

	public static CommerceOrder addOrUpdateShippingAddress(
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
			commerceAddress.getRegionId(), commerceAddress.getCountryId(),
			commerceAddress.getPhoneNumber(), serviceContext);
	}

	private static CommerceAddress _addCommerceAddress(
			CommerceAddressService commerceAddressService,
			CommerceOrder commerceOrder, ShippingAddress shippingAddress,
			ServiceContext serviceContext)
		throws Exception {

		Country country = CountryLocalServiceUtil.getCountryByA2(
			commerceOrder.getCompanyId(), shippingAddress.getCountryISOCode());

		return commerceAddressService.addCommerceAddress(
			commerceOrder.getModelClassName(),
			commerceOrder.getCommerceOrderId(), shippingAddress.getName(),
			shippingAddress.getDescription(), shippingAddress.getStreet1(),
			shippingAddress.getStreet2(), shippingAddress.getStreet3(),
			shippingAddress.getCity(), shippingAddress.getZip(),
			_getRegionId(null, country, shippingAddress),
			country.getCountryId(), shippingAddress.getPhoneNumber(), false,
			false, serviceContext);
	}

	private static long _getCountryId(Country country) {
		if (country == null) {
			return 0;
		}

		return country.getCountryId();
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

	private static long _getRegionId(
			CommerceAddress commerceAddress, Country country,
			ShippingAddress shippingAddress)
		throws Exception {

		if ((country != null) &&
			Validator.isNotNull(shippingAddress.getRegionISOCode())) {

			Region region = RegionLocalServiceUtil.getRegion(
				country.getCountryId(), shippingAddress.getRegionISOCode());

			return region.getRegionId();
		}

		if (commerceAddress != null) {
			return commerceAddress.getRegionId();
		}

		return 0;
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

		Country country = null;

		if (commerceAddress != null) {
			country = commerceAddress.getCountry();
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
			_getRegionId(commerceAddress, country, shippingAddress),
			_getCountryId(country),
			GetterUtil.get(
				shippingAddress.getPhoneNumber(),
				_getPhoneNumber(commerceAddress)),
			serviceContext);
	}

}