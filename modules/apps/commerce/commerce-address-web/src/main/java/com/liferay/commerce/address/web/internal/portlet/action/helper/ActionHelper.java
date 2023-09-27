/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ActionHelper.class)
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