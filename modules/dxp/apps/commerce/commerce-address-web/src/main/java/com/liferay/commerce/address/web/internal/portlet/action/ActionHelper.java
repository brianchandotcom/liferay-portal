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

package com.liferay.commerce.address.web.internal.portlet.action;

import com.liferay.commerce.address.constants.CommerceAddressWebKeys;
import com.liferay.commerce.address.model.CommerceCountry;
import com.liferay.commerce.address.model.CommerceRegion;
import com.liferay.commerce.address.service.CommerceCountryService;
import com.liferay.commerce.address.service.CommerceRegionService;
import com.liferay.portal.kernel.exception.PortalException;
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

	public List<CommerceCountry> getCommerceCountries(
			PortletRequest portletRequest)
		throws PortalException {

		List<CommerceCountry> commerceCountries = new ArrayList<>();

		long[] commerceCountryIds = ParamUtil.getLongValues(
			portletRequest, "rowIds");

		for (long commerceCountryId : commerceCountryIds) {
			CommerceCountry commerceCountry =
				_commerceCountryService.fetchCommerceCountry(commerceCountryId);

			if (commerceCountry != null) {
				commerceCountries.add(commerceCountry);
			}
		}

		return commerceCountries;
	}

	public CommerceCountry getCommerceCountry(RenderRequest renderRequest)
		throws PortalException {

		CommerceCountry commerceCountry =
			(CommerceCountry)renderRequest.getAttribute(
				CommerceAddressWebKeys.COMMERCE_COUNTRY);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		long commerceCountryId = ParamUtil.getLong(
			renderRequest, "commerceCountryId");

		if (commerceCountryId > 0) {
			commerceCountry = _commerceCountryService.fetchCommerceCountry(
				commerceCountryId);
		}

		if (commerceCountry != null) {
			renderRequest.setAttribute(
				CommerceAddressWebKeys.COMMERCE_COUNTRY, commerceCountry);
		}

		return commerceCountry;
	}

	public CommerceRegion getCommerceRegion(RenderRequest renderRequest)
		throws PortalException {

		CommerceRegion commerceRegion =
			(CommerceRegion)renderRequest.getAttribute(
				CommerceAddressWebKeys.COMMERCE_REGION);

		if (commerceRegion != null) {
			return commerceRegion;
		}

		long commerceRegionId = ParamUtil.getLong(
			renderRequest, "commerceRegionId");

		if (commerceRegionId > 0) {
			commerceRegion = _commerceRegionService.fetchCommerceRegion(
				commerceRegionId);
		}

		if (commerceRegion != null) {
			renderRequest.setAttribute(
				CommerceAddressWebKeys.COMMERCE_REGION, commerceRegion);
		}

		return commerceRegion;
	}

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommerceRegionService _commerceRegionService;

}