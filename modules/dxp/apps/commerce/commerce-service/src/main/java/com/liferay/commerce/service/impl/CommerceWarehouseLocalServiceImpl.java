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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.CommerceWarehouseCommerceRegionIdException;
import com.liferay.commerce.exception.CommerceWarehouseNameException;
import com.liferay.commerce.model.CommerceGeocoder;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.service.base.CommerceWarehouseLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWarehouseLocalServiceImpl
	extends CommerceWarehouseLocalServiceBaseImpl {

	@Override
	public CommerceWarehouse addCommerceWarehouse(
			String name, String description, String street1, String street2,
			String street3, String city, String zip, long commerceRegionId,
			long commerceCountryId, double latitude, double longitude,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(name, commerceRegionId, commerceCountryId);

		long commerceWarehouseId = counterLocalService.increment();

		CommerceWarehouse commerceWarehouse =
			commerceWarehousePersistence.create(commerceWarehouseId);

		commerceWarehouse.setGroupId(groupId);
		commerceWarehouse.setCompanyId(user.getCompanyId());
		commerceWarehouse.setUserId(user.getUserId());
		commerceWarehouse.setUserName(user.getFullName());
		commerceWarehouse.setName(name);
		commerceWarehouse.setDescription(description);
		commerceWarehouse.setStreet1(street1);
		commerceWarehouse.setStreet2(street2);
		commerceWarehouse.setStreet3(street3);
		commerceWarehouse.setCity(city);
		commerceWarehouse.setZip(zip);
		commerceWarehouse.setCommerceRegionId(commerceRegionId);
		commerceWarehouse.setCommerceCountryId(commerceCountryId);
		commerceWarehouse.setLatitude(latitude);
		commerceWarehouse.setLongitude(longitude);

		commerceWarehousePersistence.update(commerceWarehouse);

		return commerceWarehouse;
	}

	@Override
	public CommerceWarehouse deleteCommerceWarehouse(
		CommerceWarehouse commerceWarehouse) {

		// Commerce warehouse

		commerceWarehousePersistence.remove(commerceWarehouse);

		// Commerce warehouse items

		commerceWarehouseItemLocalService.deleteCommerceWarehouseItems(
			commerceWarehouse.getCommerceWarehouseId());

		return commerceWarehouse;
	}

	@Override
	public CommerceWarehouse deleteCommerceWarehouse(long commerceWarehouseId)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			commerceWarehousePersistence.findByPrimaryKey(commerceWarehouseId);

		return commerceWarehouseLocalService.deleteCommerceWarehouse(
			commerceWarehouse);
	}

	@Override
	public void deleteCommerceWarehouses(long groupId) {
		List<CommerceWarehouse> commerceWarehouses =
			commerceWarehousePersistence.findByGroupId(groupId);

		for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
			commerceWarehouseLocalService.deleteCommerceWarehouse(
				commerceWarehouse);
		}
	}

	@Override
	public CommerceWarehouse geolocateCommerceWarehouse(
			long commerceWarehouseId)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			commerceWarehousePersistence.findByPrimaryKey(commerceWarehouseId);

		double[] coordinates = commerceGeocoder.getCoordinates(
			commerceWarehouse.getStreet1(), commerceWarehouse.getCity(),
			commerceWarehouse.getZip(), commerceWarehouse.getCommerceRegion(),
			commerceWarehouse.getCommerceCountry());

		commerceWarehouse.setLatitude(coordinates[0]);
		commerceWarehouse.setLongitude(coordinates[1]);

		return commerceWarehousePersistence.update(commerceWarehouse);
	}

	@Override
	public List<CommerceWarehouse> getCommerceWarehouses(
		long cpInstanceId, int quantity, int start, int end) {

		return commerceWarehouseFinder.findByCommerceWarehouseItemQuantity(
			cpInstanceId, quantity, start, end);
	}

	@Override
	public List<CommerceWarehouse> getCommerceWarehouses(
		long groupId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {

		return commerceWarehousePersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWarehouse> getCommerceWarehouses(
		long groupId, long commerceCountryId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {

		if (commerceCountryId >= 0) {
			return commerceWarehousePersistence.findByG_C(
				groupId, commerceCountryId, start, end, orderByComparator);
		}

		return commerceWarehousePersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWarehousesCount(
		long groupId, long commerceCountryId) {

		if (commerceCountryId >= 0) {
			return commerceWarehousePersistence.countByG_C(
				groupId, commerceCountryId);
		}

		return commerceWarehousePersistence.countByGroupId(groupId);
	}

	@Override
	public List<CommerceWarehouse> search(
		long groupId, String keywords, long commerceCountryId, int start,
		int end, OrderByComparator<CommerceWarehouse> orderByComparator) {

		return commerceWarehouseFinder.findByKeywords(
			groupId, keywords, commerceCountryId, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(
		long groupId, String keywords, long commerceCountryId) {

		return commerceWarehouseFinder.countByKeywords(
			groupId, keywords, commerceCountryId);
	}

	@Override
	public CommerceWarehouse updateCommerceWarehouse(
			long commerceWarehouseId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			double latitude, double longitude, ServiceContext serviceContext)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			commerceWarehousePersistence.findByPrimaryKey(commerceWarehouseId);

		validate(name, commerceRegionId, commerceCountryId);

		commerceWarehouse.setName(name);
		commerceWarehouse.setDescription(description);
		commerceWarehouse.setStreet1(street1);
		commerceWarehouse.setStreet2(street2);
		commerceWarehouse.setStreet3(street3);
		commerceWarehouse.setCity(city);
		commerceWarehouse.setZip(zip);
		commerceWarehouse.setCommerceRegionId(commerceRegionId);
		commerceWarehouse.setCommerceCountryId(commerceCountryId);
		commerceWarehouse.setLatitude(latitude);
		commerceWarehouse.setLongitude(longitude);

		commerceWarehousePersistence.update(commerceWarehouse);

		return commerceWarehouse;
	}

	protected void validate(
			String name, long commerceRegionId, long commerceCountryId)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new CommerceWarehouseNameException();
		}

		if (commerceRegionId > 0) {
			CommerceRegion commerceRegion =
				commerceRegionLocalService.getCommerceRegion(commerceRegionId);

			if (commerceRegion.getCommerceCountryId() != commerceCountryId) {
				throw new CommerceWarehouseCommerceRegionIdException();
			}
		}
	}

	@ServiceReference(type = CommerceGeocoder.class)
	protected CommerceGeocoder commerceGeocoder;

}