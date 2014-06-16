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

import com.liferay.portal.RegionCodeException;
import com.liferay.portal.RegionNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Region;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.base.RegionServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class RegionServiceImpl extends RegionServiceBaseImpl {

	@Override
	public Region addRegion(
			long countryId, String regionCode, String name, boolean active)
		throws PortalException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		countryPersistence.findByPrimaryKey(countryId);

		if (Validator.isNull(regionCode)) {
			throw new RegionCodeException();
		}

		if (Validator.isNull(name)) {
			throw new RegionNameException();
		}

		long regionId = counterLocalService.increment();

		Region region = regionPersistence.create(regionId);

		region.setCountryId(countryId);
		region.setRegionCode(regionCode);
		region.setName(name);
		region.setActive(active);

		regionPersistence.update(region);

		return region;
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
		return regionPersistence.findByCountryId(countryId);
	}

	@Override
	public List<Region> getRegions(long countryId, boolean active) {
		return regionPersistence.findByC_A(countryId, active);
	}

}