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

package com.liferay.commerce.shipping.origin.locator.internal;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceGeocoder;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceCountryLocalService;
import com.liferay.commerce.service.CommerceRegionLocalService;
import com.liferay.commerce.shipping.origin.locator.CommerceShippingOriginLocator;
import com.liferay.commerce.shipping.origin.locator.internal.util.DistanceCalculator;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 * @author Ethan Bustad
 */
@Component(immediate = true, service = CommerceShippingOriginLocator.class)
public class CommerceShippingOriginLocatorImpl
	implements CommerceShippingOriginLocator {

	@Override
	public Map<CommerceAddress, List<CommerceOrderItem>> getOriginAddresses(
			CommerceOrder commerceOrder)
		throws Exception {

		CommerceAddress commerceAddress = commerceOrder.getShippingAddress();

		if (commerceAddress == null) {
			return Collections.emptyMap();
		}

		Map<CommerceInventoryWarehouse, List<CommerceOrderItem>>
			commerceInventoryWarehouseOrderItemsMap = new HashMap<>();

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

			if (cpInstance == null) {
				continue;
			}

			CommerceInventoryWarehouse commerceInventoryWarehouse =
				_getClosestCommerceInventoryWarehouse(
					commerceOrder.getGroupId(), commerceAddress,
					cpInstance.getSku());

			List<CommerceOrderItem> commerceInventoryWarehouseOrderItems =
				commerceInventoryWarehouseOrderItemsMap.get(
					commerceInventoryWarehouse);

			if (commerceInventoryWarehouseOrderItems == null) {
				commerceInventoryWarehouseOrderItems = new ArrayList<>(
					commerceOrderItems.size());

				commerceInventoryWarehouseOrderItemsMap.put(
					commerceInventoryWarehouse,
					commerceInventoryWarehouseOrderItems);
			}

			commerceInventoryWarehouseOrderItems.add(commerceOrderItem);
		}

		Map<CommerceAddress, List<CommerceOrderItem>> originAddress =
			new HashMap<>();

		for (Map.Entry<CommerceInventoryWarehouse, List<CommerceOrderItem>>
				entry : commerceInventoryWarehouseOrderItemsMap.entrySet()) {

			CommerceInventoryWarehouse commerceInventoryWarehouse =
				entry.getKey();

			originAddress.put(
				_getCommerceAddress(
					commerceInventoryWarehouse, commerceOrder.getCompanyId()),
				entry.getValue());
		}

		return originAddress;
	}

	private CommerceInventoryWarehouse _getClosestCommerceInventoryWarehouse(
			long groupId, CommerceAddress commerceAddress, String sku)
		throws PortalException {

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(groupId, sku);

		CommerceInventoryWarehouse closestCommerceInventoryWarehouse = null;
		double closestDistance = Double.MAX_VALUE;

		for (CommerceInventoryWarehouse commerceInventoryWarehouse :
				commerceInventoryWarehouses) {

			if (!commerceInventoryWarehouse.isGeolocated()) {
				CommerceCountry commerceCountry = _getCommerceCountry(
					commerceAddress.getCompanyId(),
					commerceInventoryWarehouse.getCountryTwoLettersISOCode());

				CommerceRegion commerceRegion = _getCommerceRegion(
					commerceCountry.getCommerceCountryId(),
					commerceInventoryWarehouse.getCommerceRegionCode());

				double[] coordinates = _commerceGeocoder.getCoordinates(
					commerceInventoryWarehouse.getStreet1(),
					commerceInventoryWarehouse.getCity(),
					commerceInventoryWarehouse.getZip(), commerceRegion,
					commerceCountry);

				commerceInventoryWarehouse =
					_commerceInventoryWarehouseLocalService.
						geolocateCommerceInventoryWarehouse(
							commerceInventoryWarehouse.
								getCommerceInventoryWarehouseId(),
							coordinates[0], coordinates[1]);
			}

			double distance = _distanceCalculator.getDistance(
				commerceAddress.getLatitude(), commerceAddress.getLongitude(),
				commerceInventoryWarehouse.getLatitude(),
				commerceInventoryWarehouse.getLongitude());

			if (distance < closestDistance) {
				closestCommerceInventoryWarehouse = commerceInventoryWarehouse;
				closestDistance = distance;
			}
		}

		return closestCommerceInventoryWarehouse;
	}

	private CommerceAddress _getCommerceAddress(
			CommerceInventoryWarehouse commerceInventoryWarehouse, long groupId)
		throws PortalException {

		CommerceAddress commerceAddress =
			_commerceAddressLocalService.createCommerceAddress(
				-commerceInventoryWarehouse.getCommerceInventoryWarehouseId());

		commerceAddress.setStreet1(commerceInventoryWarehouse.getStreet1());
		commerceAddress.setStreet2(commerceInventoryWarehouse.getStreet2());
		commerceAddress.setStreet3(commerceInventoryWarehouse.getStreet3());
		commerceAddress.setCity(commerceInventoryWarehouse.getCity());
		commerceAddress.setZip(commerceInventoryWarehouse.getZip());

		CommerceCountry commerceCountry = _getCommerceCountry(
			groupId, commerceInventoryWarehouse.getCountryTwoLettersISOCode());

		CommerceRegion commerceRegion = _getCommerceRegion(
			commerceCountry.getCommerceCountryId(),
			commerceInventoryWarehouse.getCommerceRegionCode());

		commerceAddress.setCommerceRegionId(
			commerceRegion.getCommerceRegionId());

		commerceAddress.setCommerceCountryId(
			commerceCountry.getCommerceCountryId());

		commerceAddress.setLatitude(commerceInventoryWarehouse.getLatitude());
		commerceAddress.setLongitude(commerceInventoryWarehouse.getLongitude());

		return commerceAddress;
	}

	private CommerceCountry _getCommerceCountry(
			long companyId, String countryCode)
		throws PortalException {

		return _commerceCountryLocalService.getCommerceCountry(
			companyId, countryCode);
	}

	private CommerceRegion _getCommerceRegion(
			long commerceCountryId, String regionCode)
		throws PortalException {

		return _commerceRegionLocalService.getCommerceRegion(
			commerceCountryId, regionCode);
	}

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private CommerceCountryLocalService _commerceCountryLocalService;

	@Reference
	private CommerceGeocoder _commerceGeocoder;

	@Reference
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@Reference
	private CommerceRegionLocalService _commerceRegionLocalService;

	private final DistanceCalculator _distanceCalculator =
		new DistanceCalculator();

}