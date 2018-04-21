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

package com.liferay.commerce.user.segment.item.selector.web.internal.display.context;

import com.liferay.commerce.user.segment.item.selector.web.internal.display.context.util.CommerceUserSegmentItemSelectorRequestHelper;
import com.liferay.commerce.user.segment.item.selector.web.internal.search.CommerceUserSegmentEntryItemSelectorChecker;
import com.liferay.commerce.user.segment.item.selector.web.internal.util.CommerceUserSegmentItemSelectorViewUtil;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryService;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceUserSegmentEntryItemSelectorViewDisplayContext {

	public CommerceUserSegmentEntryItemSelectorViewDisplayContext(
		CommerceUserSegmentEntryService commerceUserSegmentEntryService,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		_commerceUserSegmentEntryService = commerceUserSegmentEntryService;
		_portletURL = portletURL;
		_itemSelectedEventName = itemSelectedEventName;

		_commerceUserSegmentItemSelectorRequestHelper =
			new CommerceUserSegmentItemSelectorRequestHelper(
				httpServletRequest);
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_commerceUserSegmentItemSelectorRequestHelper.getRenderRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "priority");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_commerceUserSegmentItemSelectorRequestHelper.getRenderRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public SearchContainer<CommerceUserSegmentEntry> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_commerceUserSegmentItemSelectorRequestHelper.
				getLiferayPortletRequest(),
			getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("there-are-no-entries");

		OrderByComparator<CommerceUserSegmentEntry> orderByComparator =
			CommerceUserSegmentItemSelectorViewUtil.
				getCommerceUserSegmentEntryOrderByComparator(
					getOrderByCol(), getOrderByType());

		RowChecker rowChecker = new CommerceUserSegmentEntryItemSelectorChecker(
			_commerceUserSegmentItemSelectorRequestHelper.getRenderResponse(),
			getCheckedCommerceUserSegmentEntryIds());

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setRowChecker(rowChecker);

		if (isSearch()) {
			Sort sort =
				CommerceUserSegmentItemSelectorViewUtil.
					getCommerceUserSegmentEntrySort(
						getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CommerceUserSegmentEntry> results =
				_commerceUserSegmentEntryService.
					searchCommerceUserSegmentEntries(
						_commerceUserSegmentItemSelectorRequestHelper.
							getCompanyId(),
						_commerceUserSegmentItemSelectorRequestHelper.
							getScopeGroupId(),
						getKeywords(), _searchContainer.getStart(),
						_searchContainer.getEnd(), sort);

			_searchContainer.setTotal(results.getLength());
			_searchContainer.setResults(results.getBaseModels());
		}
		else {
			int total =
				_commerceUserSegmentEntryService.getCommerceUserSegmentEntriesCount(
					_commerceUserSegmentItemSelectorRequestHelper.
						getScopeGroupId());

			_searchContainer.setTotal(total);

			List<CommerceUserSegmentEntry> results =
				_commerceUserSegmentEntryService.getCommerceUserSegmentEntries(
					_commerceUserSegmentItemSelectorRequestHelper.
						getScopeGroupId(),
					_searchContainer.getStart(), _searchContainer.getEnd(),
					orderByComparator);

			_searchContainer.setResults(results);
		}

		return _searchContainer;
	}

	protected long[] getCheckedCommerceUserSegmentEntryIds() {
		return ParamUtil.getLongValues(
			_commerceUserSegmentItemSelectorRequestHelper.getRenderRequest(),
			"checkedCommerceUserSegmentEntryIds");
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			_commerceUserSegmentItemSelectorRequestHelper.getRenderRequest(),
			"keywords");

		return _keywords;
	}

	private final CommerceUserSegmentEntryService
		_commerceUserSegmentEntryService;
	private final CommerceUserSegmentItemSelectorRequestHelper
		_commerceUserSegmentItemSelectorRequestHelper;
	private final String _itemSelectedEventName;
	private SearchContainer<CommerceUserSegmentEntry> _searchContainer;
	private String _keywords;
	private final PortletURL _portletURL;

}