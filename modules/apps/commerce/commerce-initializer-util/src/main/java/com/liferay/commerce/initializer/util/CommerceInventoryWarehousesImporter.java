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

package com.liferay.commerce.initializer.util;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.commerce.service.CommerceCountryLocalService;
import com.liferay.commerce.service.CommerceRegionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = CommerceInventoryWarehousesImporter.class)
public class CommerceInventoryWarehousesImporter {

	public List<CommerceInventoryWarehouse> importCommerceInventoryWarehouses(
			JSONArray jsonArray, long scopeGroupId, long userId)
		throws Exception {

		if ((jsonArray == null) || (jsonArray.length() <= 0)) {
			return Collections.emptyList();
		}

		ServiceContext serviceContext = getServiceContext(scopeGroupId, userId);

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			new ArrayList<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			CommerceInventoryWarehouse commerceInventoryWarehouse =
				_importCommerceInventoryWarehouse(
					jsonArray.getJSONObject(i), serviceContext);

			commerceInventoryWarehouses.add(commerceInventoryWarehouse);
		}

		return commerceInventoryWarehouses;
	}

	protected ServiceContext getServiceContext(long scopeGroupId, long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);
		serviceContext.setCompanyId(user.getCompanyId());

		return serviceContext;
	}

	private CommerceInventoryWarehouse _importCommerceInventoryWarehouse(
			JSONObject jsonObject, ServiceContext serviceContext)
		throws Exception {

		// Commerce inventory warehouse

		String externalReferenceCode = jsonObject.getString(
			"ExternalReferenceCode");

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseLocalService.
				fetchCommerceInventoryWarehouseByReferenceCode(
					serviceContext.getCompanyId(), externalReferenceCode);

		if (Validator.isNotNull(externalReferenceCode) &&
			(commerceInventoryWarehouse == null)) {

			int countryNumericISOCode = jsonObject.getInt("Country");

			CommerceCountry commerceCountry =
				_commerceCountryLocalService.fetchCommerceCountry(
					serviceContext.getCompanyId(), countryNumericISOCode);

			String regionCode = jsonObject.getString("Region");

			CommerceRegion commerceRegion =
				_commerceRegionLocalService.getCommerceRegion(
					commerceCountry.getCommerceCountryId(), regionCode);

			String name = jsonObject.getString("Name");
			String description = jsonObject.getString("Description");
			boolean active = jsonObject.getBoolean("Active", true);
			String street1 = jsonObject.getString("Street1");
			String street2 = jsonObject.getString("Street2");
			String street3 = jsonObject.getString("Street3");
			String city = jsonObject.getString("City");
			String zip = jsonObject.getString("Zip");
			double latitude = jsonObject.getDouble("Latitude");
			double longitude = jsonObject.getDouble("Longitude");

			commerceInventoryWarehouse =
				_commerceInventoryWarehouseLocalService.
					addCommerceInventoryWarehouse(
						externalReferenceCode, name, description, active,
						street1, street2, street3, city, zip,
						commerceRegion.getCode(),
						commerceCountry.getTwoLettersISOCode(), latitude,
						longitude, serviceContext);
		}

		// Commerce channel rel

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				serviceContext.getScopeGroupId());

		if (commerceChannel != null) {
			_commerceChannelRelLocalService.addCommerceChannelRel(
				CommerceInventoryWarehouse.class.getName(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				commerceChannel.getCommerceChannelId(), serviceContext);
		}

		return commerceInventoryWarehouse;
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelRelLocalService _commerceChannelRelLocalService;

	@Reference
	private CommerceCountryLocalService _commerceCountryLocalService;

	@Reference
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@Reference
	private CommerceRegionLocalService _commerceRegionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}