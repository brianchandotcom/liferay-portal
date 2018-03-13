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

import com.liferay.commerce.item.selector.web.internal.search.CommerceTaxCategoryItemSelectorChecker;
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.service.CommerceTaxCategoryService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxCategoryItemSelectorViewDisplayContext
	extends BaseCommerceItemSelectorViewDisplayContext<CommerceTaxCategory> {

	public CommerceTaxCategoryItemSelectorViewDisplayContext(
		CommerceTaxCategoryService commerceTaxCategoryService,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		super(httpServletRequest, portletURL, itemSelectedEventName);

		_commerceTaxCategoryService = commerceTaxCategoryService;

		setDefaultOrderByCol("create-date");
		setDefaultOrderByType("desc");
	}

	@Override
	public PortletURL getPortletURL() {
		PortletURL portletURL = super.getPortletURL();

		String checkedCommerceTaxCategoryIds = StringUtil.merge(
			getCheckedCommerceTaxCategoryIds());

		portletURL.setParameter(
			"checkedCommerceTaxCategoryIds", checkedCommerceTaxCategoryIds);

		return portletURL;
	}

	public SearchContainer<CommerceTaxCategory> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("there-are-no-tax-categories");

		OrderByComparator<CommerceTaxCategory> orderByComparator =
			CommerceUtil.getCommerceTaxCategoryOrderByComparator(
				getOrderByCol(), getOrderByType());

		RowChecker rowChecker = new CommerceTaxCategoryItemSelectorChecker(
			cpRequestHelper.getRenderResponse(),
			getCheckedCommerceTaxCategoryIds());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(rowChecker);

		int total = _commerceTaxCategoryService.getCommerceTaxCategoriesCount(
			themeDisplay.getScopeGroupId());

		searchContainer.setTotal(total);

		List<CommerceTaxCategory> results =
			_commerceTaxCategoryService.getCommerceTaxCategories(
				themeDisplay.getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	protected long[] getCheckedCommerceTaxCategoryIds() {
		return ParamUtil.getLongValues(
			cpRequestHelper.getRenderRequest(),
			"checkedCommerceTaxCategoryIds");
	}

	private final CommerceTaxCategoryService _commerceTaxCategoryService;

}