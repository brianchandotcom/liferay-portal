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

package com.liferay.commerce.address.web.internal.display.context;

import com.liferay.commerce.address.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.address.web.internal.servlet.taglib.ui.CommerceCountryScreenNavigationConstants;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
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
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(actionHelper, renderRequest, renderResponse);

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

		portletURL.setParameter("mvcRenderCommandName", "editCommerceCountry");
		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());

		long commerceCountryId = getCommerceCountryId();

		if (commerceCountryId > 0) {
			portletURL.setParameter(
				"commerceCountryId", String.valueOf(commerceCountryId));
		}

		return portletURL;
	}

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