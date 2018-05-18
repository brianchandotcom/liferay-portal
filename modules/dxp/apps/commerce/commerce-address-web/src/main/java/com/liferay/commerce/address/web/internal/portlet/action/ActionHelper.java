/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.address.web.internal.portlet.action;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
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
				_commerceCountryService.getCommerceCountry(commerceCountryId);

			commerceCountries.add(commerceCountry);
		}

		return commerceCountries;
	}

	public CommerceCountry getCommerceCountry(RenderRequest renderRequest)
		throws PortalException {

		long commerceCountryId = ParamUtil.getLong(
			renderRequest, "commerceCountryId");

		if (commerceCountryId > 0) {
			return _commerceCountryService.getCommerceCountry(
				commerceCountryId);
		}

		return null;
	}

	public CommerceRegion getCommerceRegion(RenderRequest renderRequest)
		throws PortalException {

		long commerceRegionId = ParamUtil.getLong(
			renderRequest, "commerceRegionId");

		if (commerceRegionId > 0) {
			return _commerceRegionService.getCommerceRegion(commerceRegionId);
		}

		return null;
	}

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommerceRegionService _commerceRegionService;

}