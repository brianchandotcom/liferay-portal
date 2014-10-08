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

package com.liferay.portlet.usersadmin.search;

import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.Region;
import com.liferay.portal.service.CountryServiceUtil;
import com.liferay.portal.service.RegionServiceUtil;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class OrganizationSearchTerms extends OrganizationDisplayTerms {

	public OrganizationSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		city = DAOParamUtil.getString(portletRequest, CITY);
		countryId = ParamUtil.getLong(portletRequest, COUNTRY_ID);
		name = DAOParamUtil.getString(portletRequest, NAME);
		parentOrganizationId = ParamUtil.getLong(
			portletRequest, PARENT_ORGANIZATION_ID);
		regionId = ParamUtil.getLong(portletRequest, REGION_ID);
		street = DAOParamUtil.getString(portletRequest, STREET);
		type = DAOParamUtil.getString(portletRequest, TYPE);
		zip = DAOParamUtil.getString(portletRequest, ZIP);
	}

	public Long getCountryIdObj() {
		if (countryId == 0) {
			return null;
		}
		else {
			return new Long(countryId);
		}
	}

	public String getCountryName() throws PortalException {
		String countryName = null;

		if (countryId != 0) {
			try {
				Country country = CountryServiceUtil.getCountry(countryId);

				countryName = StringUtil.toLowerCase(country.getName());

				countryName = StringUtil.quote(countryName, StringPool.QUOTE);
			}
			catch (NoSuchCountryException nsce) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsce.getMessage());
				}
			}
		}

		return countryName;
	}

	public Long getRegionIdObj() {
		if (regionId == 0) {
			return null;
		}
		else {
			return new Long(regionId);
		}
	}

	public String getRegionName() throws PortalException {
		String regionName = null;

		if (regionId != 0) {
			try {
				Region region = RegionServiceUtil.getRegion(regionId);

				regionName = StringUtil.toLowerCase(region.getName());

				regionName = StringUtil.quote(regionName, StringPool.QUOTE);
			}
			catch (NoSuchRegionException nsre) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsre.getMessage());
				}
			}
		}

		return regionName;
	}

	public boolean hasSearchTerms() {
		if (isAdvancedSearch()) {
			if (Validator.isNotNull(city) || (countryId > 0) ||
				Validator.isNotNull(name) || (regionId > 0) ||
				Validator.isNotNull(street) || Validator.isNotNull(type) ||
				Validator.isNotNull(zip)) {

				return true;
			}
		}
		else {
			if (Validator.isNotNull(keywords)) {
				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationSearchTerms.class);

}