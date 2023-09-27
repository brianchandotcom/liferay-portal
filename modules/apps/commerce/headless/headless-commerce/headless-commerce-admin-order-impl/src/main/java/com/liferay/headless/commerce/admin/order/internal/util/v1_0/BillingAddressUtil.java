/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.util.v1_0;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
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
public class BillingAddressUtil {

	public static CommerceOrder addOrUpdateBillingAddress(
			CommerceAddressService commerceAddressService,
			CommerceOrderService commerceOrderService,
			CommerceOrder commerceOrder, BillingAddress billingAddress,
			ServiceContext serviceContext)
		throws Exception {

		if (commerceOrder.getBillingAddressId() > 0) {
			return _updateCommerceOrderBillingAddress(
				commerceAddressService, commerceOrderService, commerceOrder,
				billingAddress, serviceContext);
		}

		CommerceAddress commerceAddress = _addCommerceAddress(
			commerceAddressService, commerceOrder, billingAddress,
			serviceContext);

		return commerceOrderService.updateBillingAddress(
			commerceOrder.getCommerceOrderId(), commerceAddress.getName(),
			commerceAddress.getDescription(), commerceAddress.getStreet1(),
			commerceAddress.getStreet2(), commerceAddress.getStreet3(),
			commerceAddress.getCity(), commerceAddress.getZip(),
			commerceAddress.getRegionId(), commerceAddress.getCountryId(),
			commerceAddress.getPhoneNumber(), serviceContext);
	}

	private static CommerceAddress _addCommerceAddress(
			CommerceAddressService commerceAddressService,
			CommerceOrder commerceOrder, BillingAddress billingAddress,
			ServiceContext serviceContext)
		throws Exception {

		Country country = CountryLocalServiceUtil.getCountryByA2(
			commerceOrder.getCompanyId(), billingAddress.getCountryISOCode());

		return commerceAddressService.addCommerceAddress(
			commerceOrder.getModelClassName(),
			commerceOrder.getCommerceOrderId(), billingAddress.getName(),
			billingAddress.getDescription(), billingAddress.getStreet1(),
			billingAddress.getStreet2(), billingAddress.getStreet3(),
			billingAddress.getCity(), billingAddress.getZip(),
			_getRegionId(null, country, billingAddress), country.getCountryId(),
			billingAddress.getPhoneNumber(), false, false, serviceContext);
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
			BillingAddress billingAddress)
		throws Exception {

		if (Validator.isNull(billingAddress.getRegionISOCode()) &&
			(commerceAddress != null)) {

			return commerceAddress.getRegionId();
		}

		if (Validator.isNull(billingAddress.getRegionISOCode()) ||
			(country == null)) {

			return 0;
		}

		Region region = RegionLocalServiceUtil.getRegion(
			country.getCountryId(), billingAddress.getRegionISOCode());

		return region.getRegionId();
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

	private static CommerceOrder _updateCommerceOrderBillingAddress(
			CommerceAddressService commerceAddressService,
			CommerceOrderService commerceOrderService,
			CommerceOrder commerceOrder, BillingAddress billingAddress,
			ServiceContext serviceContext)
		throws Exception {

		CommerceAddress commerceAddress =
			commerceAddressService.fetchCommerceAddress(
				commerceOrder.getBillingAddressId());

		Country country = null;

		if (commerceAddress != null) {
			country = commerceAddress.getCountry();
		}

		return commerceOrderService.updateBillingAddress(
			commerceOrder.getCommerceOrderId(), billingAddress.getName(),
			GetterUtil.get(
				billingAddress.getDescription(),
				_getDescription(commerceAddress)),
			billingAddress.getStreet1(),
			GetterUtil.get(
				billingAddress.getStreet2(), _getStreet2(commerceAddress)),
			GetterUtil.get(
				billingAddress.getStreet3(), _getStreet3(commerceAddress)),
			billingAddress.getCity(),
			GetterUtil.get(billingAddress.getZip(), _getZip(commerceAddress)),
			_getRegionId(commerceAddress, country, billingAddress),
			_getCountryId(country),
			GetterUtil.get(
				billingAddress.getPhoneNumber(),
				_getPhoneNumber(commerceAddress)),
			serviceContext);
	}

}