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

package com.liferay.commerce.vat.web.internal.display.context;

import com.liferay.commerce.vat.model.CommerceVatNumber;
import com.liferay.commerce.vat.service.CommerceVatNumberService;
import com.liferay.commerce.vat.util.CommerceVatNumberUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceVatNumberDisplayContext {

	public CommerceVatNumberDisplayContext(
		CommerceVatNumberService commerceVatNumberService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_commerceVatNumberService = commerceVatNumberService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CommerceVatNumber getCommerceVatNumber() throws PortalException {
		long commerceVatNumberId = ParamUtil.getLong(
			_renderRequest, "commerceVatNumberId");

		return _commerceVatNumberService.fetchCommerceVatNumber(
			commerceVatNumberId);
	}

	public long getCommerceVatNumberId() throws PortalException {
		CommerceVatNumber commerceVatNumber = getCommerceVatNumber();

		if (commerceVatNumber == null) {
			return 0;
		}

		return commerceVatNumber.getCommerceVatNumberId();
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"create-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM,
			"desc");
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		portletURL.setParameter("keywords", getKeywords());
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(_renderResponse);
		}

		return _rowChecker;
	}

	public SearchContainer<CommerceVatNumber> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String emptyResultsMessage = "there-are-no-vat-numbers";

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CommerceVatNumber> orderByComparator =
			CommerceVatNumberUtil.getCommerceVatNumberOrderByComparator(
				orderByCol, orderByType);

		_searchContainer.setOrderByCol(orderByCol);
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(orderByType);
		_searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CommerceVatNumberUtil.getCommerceVatNumberSort(
				orderByCol, orderByType);

			BaseModelSearchResult<CommerceVatNumber> results =
				_commerceVatNumberService.searchCommerceVatNumbers(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					getKeywords(), _searchContainer.getStart(),
					_searchContainer.getEnd(), sort);

			_searchContainer.setTotal(results.getLength());
			_searchContainer.setResults(results.getBaseModels());
		}
		else {
			int total = _commerceVatNumberService.getCommerceVatNumbersCount(
				themeDisplay.getScopeGroupId());

			List<CommerceVatNumber> results =
				_commerceVatNumberService.getCommerceVatNumbers(
					themeDisplay.getScopeGroupId(), _searchContainer.getStart(),
					_searchContainer.getEnd(), orderByComparator);

			_searchContainer.setTotal(total);
			_searchContainer.setResults(results);
		}

		return _searchContainer;
	}

	protected String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_renderRequest, "keywords");

		return _keywords;
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	private final CommerceVatNumberService _commerceVatNumberService;
	private String _keywords;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RowChecker _rowChecker;
	private SearchContainer<CommerceVatNumber> _searchContainer;

}