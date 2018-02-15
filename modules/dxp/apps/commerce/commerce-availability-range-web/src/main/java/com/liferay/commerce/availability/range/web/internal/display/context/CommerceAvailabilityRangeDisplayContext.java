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

package com.liferay.commerce.availability.range.web.internal.display.context;

import com.liferay.commerce.availability.range.web.internal.admin.AvailabilityRangesCommerceAdminModule;
import com.liferay.commerce.availability.range.web.internal.util.CommerceAvailabilityRangeUtil;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.service.CommerceAvailabilityRangeService;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
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
public class CommerceAvailabilityRangeDisplayContext {

	public CommerceAvailabilityRangeDisplayContext(
		CommerceAvailabilityRangeService commerceAvailabilityRangeService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_commerceAvailabilityRangeService = commerceAvailabilityRangeService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CommerceAvailabilityRange getCommerceAvailabilityRange()
		throws PortalException {

		if (_commerceAvailabilityRange != null) {
			return _commerceAvailabilityRange;
		}

		long commerceAvailabilityRangeId = ParamUtil.getLong(
			_renderRequest, "commerceAvailabilityRangeId");

		if (commerceAvailabilityRangeId > 0) {
			_commerceAvailabilityRange =
				_commerceAvailabilityRangeService.getCommerceAvailabilityRange(
					commerceAvailabilityRangeId);
		}

		return _commerceAvailabilityRange;
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

		portletURL.setParameter(
			"commerceAdminModuleKey",
			AvailabilityRangesCommerceAdminModule.KEY);
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public SearchContainer<CommerceAvailabilityRange> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String emptyResultsMessage = "there-are-no-availability-ranges";

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CommerceAvailabilityRange> orderByComparator =
			CommerceAvailabilityRangeUtil.
				getCommerceAvailabilityRangeOrderByComparator(
					orderByCol, orderByType);

		_searchContainer.setOrderByCol(orderByCol);
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(orderByType);
		_searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceAvailabilityRangeService.
				getCommerceAvailabilityRangesCount(
					themeDisplay.getScopeGroupId());

		List<CommerceAvailabilityRange> results =
			_commerceAvailabilityRangeService.getCommerceAvailabilityRanges(
				themeDisplay.getScopeGroupId(), _searchContainer.getStart(),
				_searchContainer.getEnd(), orderByComparator);

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	protected RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(_renderResponse);
		}

		return _rowChecker;
	}

	private CommerceAvailabilityRange _commerceAvailabilityRange;
	private final CommerceAvailabilityRangeService
		_commerceAvailabilityRangeService;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RowChecker _rowChecker;
	private SearchContainer<CommerceAvailabilityRange> _searchContainer;

}