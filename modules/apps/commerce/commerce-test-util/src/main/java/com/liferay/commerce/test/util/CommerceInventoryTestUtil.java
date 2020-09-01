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
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceChannelRelLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.commerce.service.CommerceRegionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Luca Pellizzon
 */
public class CommerceInventoryTestUtil {

	public static CommerceCountry addCommerceCountry(
			ServiceContext serviceContext)
		throws Exception {

		return CommerceCountryLocalServiceUtil.addCommerceCountry(
			RandomTestUtil.randomLocaleStringMap(), true, true,
			RandomTestUtil.randomString(2), RandomTestUtil.randomString(3),
			RandomTestUtil.nextInt(), false, 0, true, serviceContext);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse()
		throws Exception {

		return addCommerceInventoryWarehouse(
			RandomTestUtil.randomString(), true);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			boolean active)
		throws Exception {

		return addCommerceInventoryWarehouse(
			RandomTestUtil.randomString(), active);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			boolean active, ServiceContext serviceContext)
		throws Exception {

		return addCommerceInventoryWarehouse(
			RandomTestUtil.randomString(), active, serviceContext);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			ServiceContext serviceContext)
		throws Exception {

		return addCommerceInventoryWarehouse(
			RandomTestUtil.randomString(), true, serviceContext);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String name)
		throws Exception {

		return addCommerceInventoryWarehouse(name, true);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String name, boolean active)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		CommerceCountry commerceCountry = _setUpCountry(serviceContext);

		CommerceRegion commerceRegion = _setUpRegion(
			commerceCountry, serviceContext);

		return CommerceInventoryWarehouseLocalServiceUtil.
			addCommerceInventoryWarehouse(
				name, RandomTestUtil.randomString(), active,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), commerceRegion.getCode(),
				commerceCountry.getTwoLettersISOCode(),
				RandomTestUtil.nextDouble(), RandomTestUtil.nextDouble(), null,
				serviceContext);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String name, boolean active, ServiceContext serviceContext)
		throws Exception {

		CommerceCountry commerceCountry = _setUpCountry(serviceContext);

		CommerceRegion commerceRegion = _setUpRegion(
			commerceCountry, serviceContext);

		return CommerceInventoryWarehouseLocalServiceUtil.
			addCommerceInventoryWarehouse(
				name, RandomTestUtil.randomString(), active,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), commerceRegion.getCode(),
				commerceCountry.getTwoLettersISOCode(),
				RandomTestUtil.nextDouble(), RandomTestUtil.nextDouble(), null,
				serviceContext);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String name, ServiceContext serviceContext)
		throws Exception {

		return addCommerceInventoryWarehouse(name, true, serviceContext);
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
				long groupId, String name)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceCountry commerceCountry = addCommerceCountry(serviceContext);

		CommerceRegion commerceRegion = addCommerceRegion(
			commerceCountry.getCommerceCountryId(), serviceContext);

		return CommerceInventoryWarehouseLocalServiceUtil.
			addCommerceInventoryWarehouse(
				name, RandomTestUtil.randomString(), true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), commerceRegion.getCode(),
				commerceCountry.getTwoLettersISOCode(),
				RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble(),
				RandomTestUtil.randomString(), serviceContext);
	}

	public static CommerceRegion addCommerceRegion(
			long commerceCountryId, ServiceContext serviceContext)
		throws PortalException {

		return CommerceRegionLocalServiceUtil.addCommerceRegion(
			commerceCountryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), 0, true, serviceContext);
	}

	public static CPInstance addRandomCPInstanceSku(long groupId)
		throws Exception {

		CPInstance cpInstance = CPTestUtil.addCPInstance(groupId);

		cpInstance.setSku(RandomTestUtil.randomString());

		return CPInstanceLocalServiceUtil.updateCPInstance(cpInstance);
	}

	private static CommerceCountry _setUpCountry(ServiceContext serviceContext)
		throws Exception {

		CommerceCountry commerceCountry =
			CommerceCountryLocalServiceUtil.fetchCommerceCountry(
				serviceContext.getCompanyId(), 000);

		if (commerceCountry == null) {
			commerceCountry =
				CommerceCountryLocalServiceUtil.addCommerceCountry(
					RandomTestUtil.randomLocaleStringMap(), true, true, "ZZ",
					"ZZZ", 000, false, RandomTestUtil.randomDouble(), true,
					serviceContext);
		}

		return commerceCountry;
	}

	private static CommerceRegion _setUpRegion(
			CommerceCountry commerceCountry, ServiceContext serviceContext)
		throws Exception {

		CommerceRegion commerceRegion;

		try {
			commerceRegion = CommerceRegionLocalServiceUtil.getCommerceRegion(
				commerceCountry.getCommerceCountryId(), "ZZ");
		}
		catch (Exception exception) {
			commerceRegion = CommerceRegionLocalServiceUtil.addCommerceRegion(
				commerceCountry.getCommerceCountryId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomDouble(), true, serviceContext);
		}

		return commerceRegion;
	}

}