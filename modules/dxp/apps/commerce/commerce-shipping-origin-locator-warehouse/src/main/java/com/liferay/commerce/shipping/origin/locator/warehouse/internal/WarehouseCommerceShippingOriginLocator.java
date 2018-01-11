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

package com.liferay.commerce.shipping.origin.locator.warehouse.internal;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.model.CommerceShippingOriginLocator;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceWarehouseLocalService;
import com.liferay.commerce.shipping.origin.locator.warehouse.internal.util.DistanceCalculator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = "commerce.shipping.origin.locator.key=" + WarehouseCommerceShippingOriginLocator.KEY,
	service = CommerceShippingOriginLocator.class
)
public class WarehouseCommerceShippingOriginLocator
	implements CommerceShippingOriginLocator {

	public static final String KEY = "warehouse";

	@Override
	public CommerceWarehouse getClosestCommerceWarehouse(
			CommerceAddress commerceAddress, long cpInstanceId)
		throws PortalException {

		List<CommerceWarehouse> commerceWarehouses =
			_commerceWarehouseLocalService.getCommerceWarehouses(
				cpInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		CommerceWarehouse closestCommerceWarehouse = null;
		double closestDistance = Double.MAX_VALUE;

		for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
			if (!commerceWarehouse.isGeolocated()) {
				commerceWarehouse =
					_commerceWarehouseLocalService.geolocateCommerceWarehouse(
						commerceWarehouse.getCommerceWarehouseId());
			}

			double distance = _distanceCalculator.getDistance(
				commerceAddress.getLatitude(), commerceAddress.getLongitude(),
				commerceWarehouse.getLatitude(),
				commerceWarehouse.getLongitude());

			if (distance < closestDistance) {
				closestCommerceWarehouse = commerceWarehouse;
				closestDistance = distance;
			}
		}

		return closestCommerceWarehouse;
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(
			resourceBundle, "use-closest-warehouse-description");
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "use-closest-warehouse");
	}

	@Override
	public Map<CommerceAddress, List<CommerceCartItem>> getOriginAddresses(
			CommerceCart commerceCart)
		throws Exception {

		CommerceAddress commerceAddress = commerceCart.getShippingAddress();

		if (commerceAddress == null) {
			return Collections.emptyMap();
		}

		Map<CommerceWarehouse, List<CommerceCartItem>>
			commerceWarehouseCartItemsMap = new HashMap<>();

		List<CommerceCartItem> commerceCartItems =
			commerceCart.getCommerceCartItems();

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			CommerceWarehouse commerceWarehouse = getClosestCommerceWarehouse(
				commerceAddress, commerceCartItem.getCPInstanceId());

			List<CommerceCartItem> commerceWarehouseCartItems =
				commerceWarehouseCartItemsMap.get(commerceWarehouse);

			if (commerceWarehouseCartItems == null) {
				commerceWarehouseCartItems = new ArrayList<>(
					commerceCartItems.size());

				commerceWarehouseCartItemsMap.put(
					commerceWarehouse, commerceWarehouseCartItems);
			}

			commerceWarehouseCartItems.add(commerceCartItem);
		}

		Map<CommerceAddress, List<CommerceCartItem>> originAddress =
			new HashMap<>();

		for (Map.Entry<CommerceWarehouse, List<CommerceCartItem>> entry :
				commerceWarehouseCartItemsMap.entrySet()) {

			CommerceWarehouse commerceWarehouse = entry.getKey();

			originAddress.put(
				_getCommerceAddress(commerceWarehouse), entry.getValue());
		}

		return originAddress;
	}

	@Override
	public void renderConfiguration(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {
	}

	@Override
	public void updateConfiguration(
			Map<String, String> parameterMap, ServiceContext serviceContext)
		throws Exception {
	}

	private CommerceAddress _getCommerceAddress(
		CommerceWarehouse commerceWarehouse) {

		CommerceAddress commerceAddress =
			_commerceAddressLocalService.createCommerceAddress(
				-commerceWarehouse.getCommerceWarehouseId());

		commerceAddress.setStreet1(commerceWarehouse.getStreet1());
		commerceAddress.setStreet2(commerceWarehouse.getStreet2());
		commerceAddress.setStreet3(commerceWarehouse.getStreet3());
		commerceAddress.setCity(commerceWarehouse.getCity());
		commerceAddress.setZip(commerceWarehouse.getZip());
		commerceAddress.setCommerceRegionId(
			commerceWarehouse.getCommerceRegionId());
		commerceAddress.setCommerceCountryId(
			commerceWarehouse.getCommerceCountryId());
		commerceAddress.setLatitude(commerceWarehouse.getLatitude());
		commerceAddress.setLongitude(commerceWarehouse.getLongitude());

		return commerceAddress;
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private CommerceWarehouseLocalService _commerceWarehouseLocalService;

	private final DistanceCalculator _distanceCalculator =
		new DistanceCalculator();

}