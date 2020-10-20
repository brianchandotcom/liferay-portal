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

import com.liferay.commerce.address.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.address.web.internal.servlet.taglib.ui.constants.CommerceCountryScreenNavigationConstants;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceRegionsDisplayContext
	extends BaseCommerceCountriesDisplayContext<CommerceRegion> {

	public CommerceRegionsDisplayContext(
		ActionHelper actionHelper, CommerceRegionService commerceRegionService,
		PortletResourcePermission portletResourcePermission,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(
			actionHelper, portletResourcePermission, renderRequest,
			renderResponse);

		_commerceRegionService = commerceRegionService;
	}

	public CommerceRegion getCommerceRegion() throws PortalException {
		if (_commerceRegion != null) {
			return _commerceRegion;
		}

		_commerceRegion = actionHelper.getCommerceRegion(renderRequest);

		return _commerceRegion;
	}

	public long getCommerceRegionId() throws PortalException {
		CommerceRegion commerceRegion = getCommerceRegion();

		if (commerceRegion == null) {
			return 0;
		}

		return commerceRegion.getCommerceRegionId();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/commerce_country/edit_commerce_country");
		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());

		long commerceCountryId = getCommerceCountryId();

		if (commerceCountryId > 0) {
			portletURL.setParameter(
				"commerceCountryId", String.valueOf(commerceCountryId));
		}

		return portletURL;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return ParamUtil.getString(
			renderRequest, "screenNavigationCategoryKey",
			CommerceCountryScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_COUNTRY_REGIONS);
	}

	@Override
	public SearchContainer<CommerceRegion> getSearchContainer()
		throws PortalException {

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

		OrderByComparator<CommerceRegion> orderByComparator =
			CommerceUtil.getCommerceRegionOrderByComparator(
				orderByCol, orderByType);

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setRowChecker(getRowChecker());

		int total;
		List<CommerceRegion> results;

		long commerceCountryId = getCommerceCountryId();

		if (active != null) {
			total = _commerceRegionService.getCommerceRegionsCount(
				commerceCountryId, active);
			results = _commerceRegionService.getCommerceRegions(
				commerceCountryId, active, searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);
		}
		else {
			total = _commerceRegionService.getCommerceRegionsCount(
				commerceCountryId);
			results = _commerceRegionService.getCommerceRegions(
				commerceCountryId, searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);
		}

		searchContainer.setTotal(total);
		searchContainer.setResults(results);

		return searchContainer;
	}

	private CommerceRegion _commerceRegion;
	private final CommerceRegionService _commerceRegionService;

}