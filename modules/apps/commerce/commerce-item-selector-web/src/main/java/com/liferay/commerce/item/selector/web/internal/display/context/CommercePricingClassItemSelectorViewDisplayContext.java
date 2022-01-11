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

package com.liferay.commerce.item.selector.web.internal.display.context;

import com.liferay.commerce.item.selector.web.internal.search.CommercePricingClassItemSelectorChecker;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.commerce.pricing.util.comparator.CommercePricingClassCreateDateComparator;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingClassItemSelectorViewDisplayContext
	extends BaseCommerceItemSelectorViewDisplayContext<CommercePricingClass> {

	public CommercePricingClassItemSelectorViewDisplayContext(
		CommercePricingClassService commercePricingClassService,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		super(httpServletRequest, portletURL, itemSelectedEventName);

		_commercePricingClassService = commercePricingClassService;

		setDefaultOrderByCol("create-date");
		setDefaultOrderByType("desc");
	}

	@Override
	public SearchContainer<CommercePricingClass> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("there-are-no-product-groups");

		searchContainer.setOrderByCol(getOrderByCol());

		OrderByComparator<CommercePricingClass> orderByComparator =
			_getCommercePricingClassOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByComparator(orderByComparator);

		searchContainer.setOrderByType(getOrderByType());

		RowChecker rowChecker = new CommercePricingClassItemSelectorChecker(
			cpRequestHelper.getRenderResponse(),
			_getCheckedCommercePricingClassIds());

		searchContainer.setRowChecker(rowChecker);

		searchContainer.setResultsAndTotal(
			() -> _commercePricingClassService.getCommercePricingClasses(
				themeDisplay.getCompanyId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator),
			_commercePricingClassService.getCommercePricingClassesCount(
				themeDisplay.getCompanyId()));

		return searchContainer;
	}

	private long[] _getCheckedCommercePricingClassIds() {
		return ParamUtil.getLongValues(
			cpRequestHelper.getRenderRequest(),
			"checkedCommercePricingClassIds");
	}

	private OrderByComparator<CommercePricingClass>
		_getCommercePricingClassOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		if (orderByCol.equals("create-date")) {
			return new CommercePricingClassCreateDateComparator(orderByAsc);
		}

		return null;
	}

	private final CommercePricingClassService _commercePricingClassService;

}