/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.base.RegionServiceBaseImpl;

import java.util.LinkedHashMap;
import java.util.List;

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
			countryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public List<Region> getRegions(long countryId, boolean active) {
		return regionPersistence.findByC_A(
			countryId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public BaseModelSearchResult<Region> searchRegions(
			long companyId, Boolean active, String keywords,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator<Region> orderByComparator)
		throws PortalException {

		return regionLocalService.searchRegions(
			companyId, active, keywords, params, start, end, orderByComparator);
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

	@BeanReference(type = CountryService.class)
	private CountryService _countryService;

}