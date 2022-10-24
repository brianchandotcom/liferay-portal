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

package com.liferay.commerce.test.util;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalServiceUtil;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalServiceUtil;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceChannelRelLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.service.RegionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Luca Pellizzon
 */
public class CommerceInventoryTestUtil {

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse()
		throws Exception {

		return addCommerceInventoryWarehouse(
			RandomTestUtil.randomLocaleStringMap(), true);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			boolean active)
		throws Exception {

		return addCommerceInventoryWarehouse(
			RandomTestUtil.randomLocaleStringMap(), active);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			boolean active, ServiceContext serviceContext)
		throws Exception {

		return addCommerceInventoryWarehouse(
			RandomTestUtil.randomLocaleStringMap(), active, serviceContext);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			Map<Locale, String> nameMap)
		throws Exception {

		return addCommerceInventoryWarehouse(nameMap, true);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			Map<Locale, String> nameMap, boolean active)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		Country country = _setUpCountry(serviceContext);

		Region region = _setUpRegion(country, serviceContext);

		return CommerceInventoryWarehouseLocalServiceUtil.
			addCommerceInventoryWarehouse(
				null, nameMap, RandomTestUtil.randomLocaleStringMap(), active,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), region.getRegionCode(),
				country.getA2(), RandomTestUtil.nextDouble(),
				RandomTestUtil.nextDouble(), serviceContext);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			Map<Locale, String> nameMap, boolean active,
			ServiceContext serviceContext)
		throws Exception {

		Country country = _setUpCountry(serviceContext);

		Region region = _setUpRegion(country, serviceContext);

		return CommerceInventoryWarehouseLocalServiceUtil.
			addCommerceInventoryWarehouse(
				null, nameMap, RandomTestUtil.randomLocaleStringMap(), active,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), region.getRegionCode(),
				country.getA2(), RandomTestUtil.nextDouble(),
				RandomTestUtil.nextDouble(), serviceContext);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			Map<Locale, String> nameMap, ServiceContext serviceContext)
		throws Exception {

		return addCommerceInventoryWarehouse(nameMap, true, serviceContext);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			ServiceContext serviceContext)
		throws Exception {

		return addCommerceInventoryWarehouse(
			RandomTestUtil.randomLocaleStringMap(), true, serviceContext);
	}

	public static CommerceInventoryWarehouseItem
			addCommerceInventoryWarehouseItem(
				long userId,
				CommerceInventoryWarehouse commerceInventoryWarehouse,
				String sku, int quantity)
		throws Exception {

		return CommerceInventoryWarehouseItemLocalServiceUtil.
			addCommerceInventoryWarehouseItem(
				userId,
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				sku, quantity);
	}

	public static CommerceInventoryWarehouseItem
			addCommerceInventoryWarehouseItem(
				long commerceChannelId, String sku, int quantity,
				ServiceContext serviceContext)
		throws Exception {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			addCommerceInventoryWarehouse(serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			commerceChannelId, serviceContext);

		return CommerceInventoryWarehouseItemLocalServiceUtil.
			addCommerceInventoryWarehouseItem(
				serviceContext.getUserId(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				sku, quantity);
	}

	public static CommerceInventoryWarehouse
			addCommerceInventoryWarehouseWithExternalReferenceCode(
				long groupId, Map<Locale, String> nameMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		Country country = addCountry(serviceContext);

		Region region = addRegion(country.getCountryId(), serviceContext);

		return CommerceInventoryWarehouseLocalServiceUtil.
			addCommerceInventoryWarehouse(
				RandomTestUtil.randomString(), nameMap,
				RandomTestUtil.randomLocaleStringMap(), true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), region.getRegionCode(),
				country.getA2(), RandomTestUtil.randomDouble(),
				RandomTestUtil.randomDouble(), serviceContext);
	}

	public static Country addCountry(ServiceContext serviceContext)
		throws Exception {

		int safetyCount = 0;

		String a2 = RandomTestUtil.randomString(
			2, UniqueStringRandomizerBumper.INSTANCE);

		while ((safetyCount < 10) &&
			   (CountryLocalServiceUtil.fetchCountryByA2(
				   serviceContext.getCompanyId(), a2) != null)) {

			a2 = RandomTestUtil.randomString(
				2, UniqueStringRandomizerBumper.INSTANCE);

			safetyCount++;
		}

		safetyCount = 0;

		String a3 = RandomTestUtil.randomString(
			3, UniqueStringRandomizerBumper.INSTANCE);

		while ((safetyCount < 10) &&
			   (CountryLocalServiceUtil.fetchCountryByA3(
				   serviceContext.getCompanyId(), a3) != null)) {

			a3 = RandomTestUtil.randomString(
				3, UniqueStringRandomizerBumper.INSTANCE);

			safetyCount++;
		}

		return CountryLocalServiceUtil.addCountry(
			a2, a3, true, true, null, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(NumericStringRandomizerBumper.INSTANCE),
			0, true, false, false, serviceContext);
	}

	public static CPInstance addRandomCPInstanceSku(long groupId)
		throws Exception {

		CPInstance cpInstance = CPTestUtil.addCPInstance(groupId);

		cpInstance.setSku(RandomTestUtil.randomString());

		return CPInstanceLocalServiceUtil.updateCPInstance(cpInstance);
	}

	public static Region addRegion(
			long countryId, ServiceContext serviceContext)
		throws PortalException {

		return RegionLocalServiceUtil.addRegion(
			countryId, true, RandomTestUtil.randomString(), 0,
			RandomTestUtil.randomString(), serviceContext);
	}

	private static Country _setUpCountry(ServiceContext serviceContext)
		throws Exception {

		Country country = CountryLocalServiceUtil.fetchCountryByNumber(
			serviceContext.getCompanyId(), "000");

		if (country == null) {
			country = CountryLocalServiceUtil.addCountry(
				"ZZ", "ZZZ", true, true, null, RandomTestUtil.randomString(),
				"000", RandomTestUtil.randomDouble(), true, false, false,
				serviceContext);
		}

		return country;
	}

	private static Region _setUpRegion(
			Country country, ServiceContext serviceContext)
		throws Exception {

		Region region = RegionLocalServiceUtil.fetchRegion(
			country.getCountryId(), "ZZ");

		if (region != null) {
			return region;
		}

		return RegionLocalServiceUtil.addRegion(
			country.getCountryId(), true, RandomTestUtil.randomString(),
			RandomTestUtil.randomDouble(), RandomTestUtil.randomString(),
			serviceContext);
	}

}