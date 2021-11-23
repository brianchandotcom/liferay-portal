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

package com.liferay.commerce.address.web.internal.portlet.action.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = ActionHelper.class)
public class ActionHelper {

	public List<Country> getCountries(PortletRequest portletRequest)
		throws PortalException {

		List<Country> countries = new ArrayList<>();

		long[] countryIds = ParamUtil.getLongValues(portletRequest, "rowIds");

		for (long countryId : countryIds) {
			countries.add(_countryService.getCountry(countryId));
		}

		return countries;
	}

	public Country getCountry(RenderRequest renderRequest)
		throws PortalException {

		long countryId = ParamUtil.getLong(renderRequest, "countryId");

		if (countryId > 0) {
			return _countryService.getCountry(countryId);
		}

		return null;
	}

	public Region getRegion(RenderRequest renderRequest)
		throws PortalException {

		long regionId = ParamUtil.getLong(renderRequest, "regionId");

		if (regionId > 0) {
			return _regionService.getRegion(regionId);
		}

		return null;
	}

	@Reference
	private CountryService _countryService;

	@Reference
	private RegionService _regionService;

}