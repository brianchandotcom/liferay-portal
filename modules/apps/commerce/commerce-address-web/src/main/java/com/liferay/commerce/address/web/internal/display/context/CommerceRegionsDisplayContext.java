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

package com.liferay.commerce.address.web.internal.display.context;

import com.liferay.commerce.address.web.internal.portlet.action.helper.ActionHelper;
import com.liferay.commerce.address.web.internal.servlet.taglib.ui.constants.CommerceCountryScreenNavigationConstants;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceRegionsDisplayContext
	extends BaseCommerceCountriesDisplayContext<Region> {

	public CommerceRegionsDisplayContext(
		ActionHelper actionHelper,
		PortletResourcePermission portletResourcePermission,
		RegionService regionService, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		super(
			actionHelper, portletResourcePermission, renderRequest,
			renderResponse);

		_regionService = regionService;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		return PortletURLBuilder.create(
			super.getPortletURL()
		).setMVCRenderCommandName(
			"/commerce_country/edit_commerce_country"
		).setParameter(
			"countryId",
			() -> {
				long countryId = getCountryId();

				if (countryId > 0) {
					return countryId;
				}

				return null;
			}
		).setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey()
		).buildPortletURL();
	}

	public Region getRegion() throws PortalException {
		if (_region != null) {
			return _region;
		}

		_region = actionHelper.getRegion(renderRequest);

		return _region;
	}

	public long getRegionId() throws PortalException {
		Region region = getRegion();

		if (region == null) {
			return 0;
		}

		return region.getRegionId();
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return ParamUtil.getString(
			renderRequest, "screenNavigationCategoryKey",
			CommerceCountryScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_COUNTRY_REGIONS);
	}

	@Override
	public SearchContainer<Region> getSearchContainer() throws PortalException {
		if (searchContainer != null) {
			return searchContainer;
		}

		Boolean active = null;
		String emptyResultsMessage = "there-are-no-regions";

		String navigation = getNavigation();

		if (navigation.equals("active")) {
			active = Boolean.TRUE;
			emptyResultsMessage = "there-are-no-active-regions";
		}
		else if (navigation.equals("inactive")) {
			active = Boolean.FALSE;
			emptyResultsMessage = "there-are-no-inactive-regions";
		}

		searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<Region> orderByComparator =
			CommerceUtil.getRegionOrderByComparator(orderByCol, orderByType);

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setRowChecker(getRowChecker());

		long countryId = getCountryId();

		if (active != null) {
			boolean navigationActive = active;

			searchContainer.setResultsAndTotal(
				() -> _regionService.getRegions(
					countryId, navigationActive, searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator),
				_regionService.getRegionsCount(countryId, navigationActive));
		}
		else {
			searchContainer.setResultsAndTotal(
				() -> _regionService.getRegions(
					countryId, searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator),
				_regionService.getRegionsCount(countryId));
		}

		return searchContainer;
	}

	private Region _region;
	private final RegionService _regionService;

}