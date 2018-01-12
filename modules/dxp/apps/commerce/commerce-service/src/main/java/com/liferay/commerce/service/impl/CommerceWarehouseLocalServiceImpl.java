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

import com.liferay.commerce.exception.CommerceWarehouseActiveException;
import com.liferay.commerce.exception.CommerceWarehouseCommerceRegionIdException;
import com.liferay.commerce.exception.CommerceWarehouseNameException;
import com.liferay.commerce.model.CommerceGeocoder;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.model.CommerceWarehouseConstants;
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
			String name, String description, boolean active, String street1,
			String street2, String street3, String city, String zip,
			long commerceRegionId, long commerceCountryId, double latitude,
			double longitude, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(
			name, active, commerceRegionId, commerceCountryId, latitude,
			longitude);

		long commerceWarehouseId = counterLocalService.increment();

		CommerceWarehouse commerceWarehouse =
			commerceWarehousePersistence.create(commerceWarehouseId);

		commerceWarehouse.setGroupId(groupId);
		commerceWarehouse.setCompanyId(user.getCompanyId());
		commerceWarehouse.setUserId(user.getUserId());
		commerceWarehouse.setUserName(user.getFullName());
		commerceWarehouse.setName(name);
		commerceWarehouse.setDescription(description);
		commerceWarehouse.setActive(active);
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

		double[] coordinates = _commerceGeocoder.getCoordinates(
			commerceWarehouse.getStreet1(), commerceWarehouse.getCity(),
			commerceWarehouse.getZip(), commerceWarehouse.getCommerceRegion(),
			commerceWarehouse.getCommerceCountry());

		commerceWarehouse.setLatitude(coordinates[0]);
		commerceWarehouse.setLongitude(coordinates[1]);

		return commerceWarehousePersistence.update(commerceWarehouse);
	}

	@Override
	public List<CommerceWarehouse> getCommerceWarehouses(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {

		return commerceWarehousePersistence.findByG_A(
			groupId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWarehouse> getCommerceWarehouses(
		long groupId, boolean active, long commerceCountryId, int start,
		int end, OrderByComparator<CommerceWarehouse> orderByComparator) {

		if (commerceCountryId >= 0) {
			return commerceWarehousePersistence.findByG_A_C(
				groupId, active, commerceCountryId, start, end,
				orderByComparator);
		}

		return commerceWarehousePersistence.findByG_A(
			groupId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWarehouse> getCommerceWarehouses(
		long cpInstanceId, int start, int end) {

		return commerceWarehouseFinder.findByCPInstanceId(
			cpInstanceId, start, end);
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
		long groupId, boolean active, long commerceCountryId) {

		if (commerceCountryId >= 0) {
			return commerceWarehousePersistence.countByG_A_C(
				groupId, active, commerceCountryId);
		}

		return commerceWarehousePersistence.countByG_A(groupId, active);
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
	public CommerceWarehouse getDefaultCommerceWarehouse(
			ServiceContext serviceContext)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			commerceWarehousePersistence.fetchByPrimaryKey(
				CommerceWarehouseConstants.DEFAULT_ID);

		if (commerceWarehouse != null) {
			return commerceWarehouse;
		}

		return _addDefaultCommerceWarehouse(
			CommerceWarehouseConstants.DEFAULT_ID,
			CommerceWarehouseConstants.DEFAULT_NAME, null, null, null, null,
			null, 0L, 0L, -1, -1, serviceContext);
	}

	@Override
	public List<CommerceWarehouse> search(
		long groupId, String keywords, boolean all, long commerceCountryId,
		int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {

		return commerceWarehouseFinder.findByKeywords(
			groupId, keywords, all, commerceCountryId, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(
		long groupId, String keywords, boolean all, long commerceCountryId) {

		return commerceWarehouseFinder.countByKeywords(
			groupId, keywords, all, commerceCountryId);
	}

	@Override
	public CommerceWarehouse setActive(long commerceWarehouseId, boolean active)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			commerceWarehousePersistence.findByPrimaryKey(commerceWarehouseId);

		commerceWarehouse.setActive(active);

		commerceWarehousePersistence.update(commerceWarehouse);

		return commerceWarehouse;
	}

	@Override
	public CommerceWarehouse updateCommerceWarehouse(
			long commerceWarehouseId, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, long commerceRegionId,
			long commerceCountryId, double latitude, double longitude,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			commerceWarehousePersistence.findByPrimaryKey(commerceWarehouseId);

		validate(
			name, active, commerceRegionId, commerceCountryId, latitude,
			longitude);

		commerceWarehouse.setName(name);
		commerceWarehouse.setDescription(description);
		commerceWarehouse.setActive(active);
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
	public CommerceWarehouse updateDefaultCommerceWarehouse(
			long commerceWarehouseId, String name, String street1,
			String street2, String street3, String city, String zip,
			long commerceRegionId, long commerceCountryId, double latitude,
			double longitude, ServiceContext serviceContext)
		throws PortalException {

		List<CommerceWarehouse> commerceWarehouses =
			commerceWarehousePersistence.findByG_A(
				serviceContext.getScopeGroupId(), true);

		for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
			commerceWarehouseLocalService.setActive(
				commerceWarehouse.getCommerceWarehouseId(), false);
		}

		CommerceWarehouse commerceWarehouse =
			commerceWarehousePersistence.fetchByPrimaryKey(commerceWarehouseId);

		if (commerceWarehouse == null) {
			return _addDefaultCommerceWarehouse(
				commerceWarehouseId, name, street1, street2, street3, city, zip,
				commerceRegionId, commerceCountryId, latitude, longitude,
				serviceContext);
		}

		return commerceWarehouseLocalService.updateCommerceWarehouse(
			commerceWarehouseId, name, null, true, street1, street2, street3,
			city, zip, commerceRegionId, commerceCountryId, latitude, longitude,
			serviceContext);
	}

	protected void validate(
			String name, boolean active, long commerceRegionId,
			long commerceCountryId, double latitude, double longitude)
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

		if (active && (latitude == 0) && (longitude == 0)) {
			throw new CommerceWarehouseActiveException();
		}
	}

	private CommerceWarehouse _addDefaultCommerceWarehouse(
			long commerceWarehouseId, String name, String street1,
			String street2, String street3, String city, String zip,
			long commerceRegionId, long commerceCountryId, double latitude,
			double longitude, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(
			name, true, commerceRegionId, commerceCountryId, latitude,
			longitude);

		CommerceWarehouse commerceWarehouse =
			commerceWarehousePersistence.create(commerceWarehouseId);

		commerceWarehouse.setGroupId(groupId);
		commerceWarehouse.setCompanyId(user.getCompanyId());
		commerceWarehouse.setUserId(user.getUserId());
		commerceWarehouse.setUserName(user.getFullName());
		commerceWarehouse.setName(name);
		commerceWarehouse.setActive(true);
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

	@ServiceReference(type = CommerceGeocoder.class)
	private CommerceGeocoder _commerceGeocoder;

}