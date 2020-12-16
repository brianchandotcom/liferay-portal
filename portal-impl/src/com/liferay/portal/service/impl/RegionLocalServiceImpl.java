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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RegionCodeException;
import com.liferay.portal.kernel.exception.RegionNameException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.RegionLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class RegionLocalServiceImpl extends RegionLocalServiceBaseImpl {

	@Override
	public Region addRegion(
			long countryId, boolean active, String name, double position,
			String regionCode, ServiceContext serviceContext)
		throws PortalException {

		countryPersistence.findByPrimaryKey(countryId);

		validate(name, regionCode);

		long regionId = counterLocalService.increment();

		Region region = regionPersistence.create(regionId);

		region.setCompanyId(serviceContext.getCompanyId());

		User user = userLocalService.getUser(serviceContext.getUserId());

		region.setUserId(user.getUserId());
		region.setUserName(user.getFullName());

		region.setCountryId(countryId);
		region.setActive(active);
		region.setName(name);
		region.setPosition(position);
		region.setRegionCode(regionCode);

		return regionPersistence.update(region);
	}

	@Override
	public void deleteCountryRegions(long countryId) {
		regionPersistence.removeByCountryId(countryId);
	}

	@Override
	public Region deleteRegion(long regionId) throws PortalException {
		Region region = regionPersistence.findByPrimaryKey(regionId);

		return deleteRegion(region);
	}

	@Override
	public Region deleteRegion(Region region) throws PortalException {

		// Region

		regionPersistence.remove(region);

		// Address

		addressLocalService.deleteRegionAddresses(region.getRegionId());

		// Organizations

		List<Organization> organizations = organizationLocalService.search(
			region.getCompanyId(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null,
			region.getRegionId(), region.getCountryId(), null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Organization organization : organizations) {
			organization.setRegionId(0);

			organizationLocalService.updateOrganization(organization);
		}

		return region;
	}

	@Override
	public Region fetchRegion(long countryId, String regionCode) {
		return regionPersistence.fetchByC_R(countryId, regionCode);
	}

	@Override
	public Region getRegion(long countryId, String regionCode)
		throws PortalException {

		return regionPersistence.findByC_R(countryId, regionCode);
	}

	@Override
	public List<Region> getRegions(long countryId, boolean active)
		throws PortalException {

		return regionPersistence.findByC_A(countryId, active);
	}

	@Override
	public List<Region> getRegions(
		long countryId, boolean active, int start, int end,
		OrderByComparator<Region> orderByComparator) {

		return regionPersistence.findByC_A(
			countryId, active, start, end, orderByComparator);
	}

	@Override
	public List<Region> getRegions(
		long countryId, int start, int end,
		OrderByComparator<Region> orderByComparator) {

		return regionPersistence.findByCountryId(
			countryId, start, end, orderByComparator);
	}

	@Override
	public List<Region> getRegions(long companyId, String a2, boolean active)
		throws PortalException {

		Country country = countryPersistence.findByC_A2(companyId, a2);

		return regionPersistence.findByC_A(country.getCountryId(), active);
	}

	@Override
	public int getRegionsCount(long countryId) {
		return regionPersistence.countByCountryId(countryId);
	}

	@Override
	public int getRegionsCount(long countryId, boolean active) {
		return regionPersistence.countByC_A(countryId, active);
	}

	@Override
	public Region updateActive(long regionId, boolean active)
		throws PortalException {

		Region region = regionPersistence.findByPrimaryKey(regionId);

		region.setActive(active);

		return regionPersistence.update(region);
	}

	@Override
	public Region updateRegion(
			long regionId, boolean active, String name, double position,
			String regionCode)
		throws PortalException {

		Region region = regionPersistence.findByPrimaryKey(regionId);

		validate(name, regionCode);

		region.setActive(active);
		region.setName(name);
		region.setPosition(position);
		region.setRegionCode(regionCode);

		return regionPersistence.update(region);
	}

	protected void validate(String name, String regionCode)
		throws PortalException {

		if (Validator.isNull(regionCode)) {
			throw new RegionCodeException();
		}

		if (Validator.isNull(name)) {
			throw new RegionNameException();
		}
	}

}