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

package com.liferay.commerce.product.tax.category.web.internal.display.context;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceTaxMethod;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.commerce.product.tax.category.web.internal.servlet.taglib.ui.CPTaxCategoryScreenNavigationEntry;
import com.liferay.commerce.product.util.comparator.CPTaxCategoryCreateDateComparator;
import com.liferay.commerce.service.CommerceTaxMethodService;
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
public class CPTaxCategoryDisplayContext {

	public CPTaxCategoryDisplayContext(
		CommerceTaxMethodService commerceTaxMethodService,
		CPTaxCategoryService cpTaxCategoryService, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_commerceTaxMethodService = commerceTaxMethodService;
		_cpTaxCategoryService = cpTaxCategoryService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CommerceTaxMethod getCommerceTaxMethod() throws PortalException {
		if (_commerceTaxMethod != null) {
			return _commerceTaxMethod;
		}

		long commerceTaxMethodId = ParamUtil.getLong(
			_renderRequest, "commerceTaxMethodId");

		if (commerceTaxMethodId > 0) {
			_commerceTaxMethod = _commerceTaxMethodService.getCommerceTaxMethod(
				commerceTaxMethodId);
		}

		return _commerceTaxMethod;
	}

	public long getCommerceTaxMethodId() throws PortalException {
		CommerceTaxMethod commerceTaxMethod = getCommerceTaxMethod();

		if (commerceTaxMethod == null) {
			return 0;
		}

		return commerceTaxMethod.getCommerceTaxMethodId();
	}

	public CPTaxCategory getCPTaxCategory() throws PortalException {
		if (_cpTaxCategory != null) {
			return _cpTaxCategory;
		}

		long cpTaxCategoryId = ParamUtil.getLong(
			_renderRequest, "cpTaxCategoryId");

		if (cpTaxCategoryId > 0) {
			_cpTaxCategory = _cpTaxCategoryService.getCPTaxCategory(
				cpTaxCategoryId);
		}

		return _cpTaxCategory;
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

		portletURL.setParameter(
			"commerceAdminModuleKey",
			CommerceConstants.TAXES_COMMERCE_ADMIN_MODULE_KEY);
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());
		portletURL.setParameter(
			"screenNavigationEntryKey", getScreenNavigationEntryKey());

		return portletURL;
	}

	public String getScreenNavigationEntryKey() {
		return CPTaxCategoryScreenNavigationEntry.ENTRY_KEY;
	}

	public SearchContainer<CPTaxCategory> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String emptyResultsMessage = "there-are-no-tax-categories";

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CPTaxCategory> orderByComparator =
			getCPTaxCategoryOrderByComparator(orderByCol, orderByType);

		_searchContainer.setOrderByCol(orderByCol);
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(orderByType);
		_searchContainer.setRowChecker(getRowChecker());

		int total = _cpTaxCategoryService.getCPTaxCategoriesCount(
			themeDisplay.getScopeGroupId());

		List<CPTaxCategory> results = _cpTaxCategoryService.getCPTaxCategories(
			themeDisplay.getScopeGroupId(), _searchContainer.getStart(),
			_searchContainer.getEnd(), orderByComparator);

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	protected OrderByComparator<CPTaxCategory>
		getCPTaxCategoryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPTaxCategory> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CPTaxCategoryCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	protected RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(_renderResponse);
		}

		return _rowChecker;
	}

	private CommerceTaxMethod _commerceTaxMethod;
	private final CommerceTaxMethodService _commerceTaxMethodService;
	private CPTaxCategory _cpTaxCategory;
	private final CPTaxCategoryService _cpTaxCategoryService;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RowChecker _rowChecker;
	private SearchContainer<CPTaxCategory> _searchContainer;

}