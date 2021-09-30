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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.model.impl.RegionModelImpl;
import com.liferay.portal.service.base.RegionServiceBaseImpl;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class RegionServiceImpl extends RegionServiceBaseImpl {

	@Override
	public Region addRegion(
			long countryId, boolean active, String name, double position,
			String regionCode, ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.MANAGE_COUNTRIES);

		return regionLocalService.addRegion(
			countryId, active, name, position, regionCode, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public Region addRegion(
			long countryId, String regionCode, String name, boolean active)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		PermissionChecker permissionChecker = getPermissionChecker();

		serviceContext.setCompanyId(permissionChecker.getCompanyId());
		serviceContext.setUserId(permissionChecker.getUserId());

		return addRegion(
			countryId, active, name, 0, regionCode, serviceContext);
	}

	@Override
	public void deleteRegion(long regionId) throws PortalException {
		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.MANAGE_COUNTRIES);

		regionLocalService.deleteRegion(regionId);
	}

	@Override
	public Region fetchRegion(long regionId) {
		return regionPersistence.fetchByPrimaryKey(regionId);
	}

	@Override
	public Region fetchRegion(long countryId, String regionCode) {
		return regionPersistence.fetchByC_R(countryId, regionCode);
	}

	@Override
	public Region getRegion(long regionId) throws PortalException {
		return regionPersistence.findByPrimaryKey(regionId);
	}

	@Override
	public Region getRegion(long countryId, String regionCode)
		throws PortalException {

		return regionPersistence.findByC_R(countryId, regionCode);
	}

	@Override
	public List<Region> getRegions() {
		return regionPersistence.findAll();
	}

	@Override
	public List<Region> getRegions(boolean active) {
		return regionPersistence.findByActive(active);
	}

	@Override
	public List<Region> getRegions(long countryId) {
		return regionPersistence.findByCountryId(
			countryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			_getOrderByComparator(countryId));
	}

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public List<Region> getRegions(long countryId, boolean active) {
		return regionPersistence.findByC_A(
			countryId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			_getOrderByComparator(countryId));
	}

	@Override
	public List<Region> getRegions(
		long countryId, boolean active, int start, int end,
		OrderByComparator<Region> orderByComparator) {

		return regionLocalService.getRegions(
			countryId, active, start, end, orderByComparator);
	}

	@Override
	public List<Region> getRegions(
		long countryId, int start, int end,
		OrderByComparator<Region> orderByComparator) {

		return regionLocalService.getRegions(
			countryId, start, end, orderByComparator);
	}

	@Override
	public List<Region> getRegions(long companyId, String a2, boolean active)
		throws PortalException {

		return regionLocalService.getRegions(companyId, a2, active);
	}

	@Override
	public int getRegionsCount(long countryId) {
		return regionLocalService.getRegionsCount(countryId);
	}

	@Override
	public int getRegionsCount(long countryId, boolean active) {
		return regionLocalService.getRegionsCount(countryId, active);
	}

	@Override
	public Region updateActive(long regionId, boolean active)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.MANAGE_COUNTRIES);

		return regionLocalService.updateActive(regionId, active);
	}

	@Override
	public Region updateRegion(
			long regionId, boolean active, String name, double position,
			String regionCode)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.MANAGE_COUNTRIES);

		return regionLocalService.updateRegion(
			regionId, active, name, position, regionCode);
	}

	private OrderByComparator<Region> _getOrderByComparator(long countryId) {
		Country country = _countryService.fetchCountry(countryId);

		if (country == null) {
			return null;
		}

		return _orderByComparators.get(country.getA2());
	}

	private static final Map<String, OrderByComparator<Region>>
		_orderByComparators =
			HashMapBuilder.<String, OrderByComparator<Region>>put(
				"JP",
				OrderByComparatorFactoryUtil.create(
					RegionModelImpl.TABLE_NAME, "regionCode", true)
			).build();

	@BeanReference(type = CountryService.class)
	private CountryService _countryService;

}