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

package com.liferay.commerce.availability.estimate.web.internal.display.context;

import com.liferay.commerce.availability.estimate.web.internal.util.CommerceAvailabilityEstimateUtil;
import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.service.CommerceAvailabilityEstimateService;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAvailabilityEstimateDisplayContext {

	public CommerceAvailabilityEstimateDisplayContext(
		CommerceAvailabilityEstimateService commerceAvailabilityEstimateService,
		PortletResourcePermission portletResourcePermission,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_commerceAvailabilityEstimateService =
			commerceAvailabilityEstimateService;
		_portletResourcePermission = portletResourcePermission;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CommerceAvailabilityEstimate getCommerceAvailabilityEstimate()
		throws PortalException {

		if (_commerceAvailabilityEstimate != null) {
			return _commerceAvailabilityEstimate;
		}

		long commerceAvailabilityEstimateId = ParamUtil.getLong(
			_renderRequest, "commerceAvailabilityEstimateId");

		if (commerceAvailabilityEstimateId > 0) {
			_commerceAvailabilityEstimate =
				_commerceAvailabilityEstimateService.
					getCommerceAvailabilityEstimate(
						commerceAvailabilityEstimateId);
		}

		return _commerceAvailabilityEstimate;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"priority");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public SearchContainer<CommerceAvailabilityEstimate> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String emptyResultsMessage = "there-are-no-availability-estimates";

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator =
			CommerceAvailabilityEstimateUtil.
				getCommerceAvailabilityEstimateOrderByComparator(
					orderByCol, orderByType);

		_searchContainer.setOrderByCol(orderByCol);
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(orderByType);
		_searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceAvailabilityEstimateService.
				getCommerceAvailabilityEstimatesCount(
					themeDisplay.getCompanyId());

		List<CommerceAvailabilityEstimate> results =
			_commerceAvailabilityEstimateService.
				getCommerceAvailabilityEstimates(
					themeDisplay.getCompanyId(), _searchContainer.getStart(),
					_searchContainer.getEnd(), orderByComparator);

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public boolean hasManageCommerceAvailabilityEstimatesPermission() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _portletResourcePermission.contains(
			themeDisplay.getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_AVAILABILITY_ESTIMATES);
	}

	protected RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(_renderResponse);
		}

		return _rowChecker;
	}

	private CommerceAvailabilityEstimate _commerceAvailabilityEstimate;
	private final CommerceAvailabilityEstimateService
		_commerceAvailabilityEstimateService;
	private final PortletResourcePermission _portletResourcePermission;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RowChecker _rowChecker;
	private SearchContainer<CommerceAvailabilityEstimate> _searchContainer;

}