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

package com.liferay.commerce.warehouse.web.internal.display.context;

import com.liferay.commerce.item.selector.criterion.CommerceWarehouseItemSelectorCriterion;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.service.CommerceWarehouseItemService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public abstract class CommerceWarehouseItemsDisplayContext
	<T extends GroupedModel> {

	public CommerceWarehouseItemsDisplayContext(
		CommerceWarehouseItemService commerceWarehouseItemService,
		ItemSelector itemSelector, HttpServletRequest httpServletRequest) {

		_commerceWarehouseItemService = commerceWarehouseItemService;
		_itemSelector = itemSelector;

		cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public abstract String getBackURL() throws PortalException;

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		CommerceWarehouseItemSelectorCriterion
			commerceWarehouseItemSelectorCriterion =
				new CommerceWarehouseItemSelectorCriterion();

		commerceWarehouseItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "commerceWarehousesSelectItem",
			commerceWarehouseItemSelectorCriterion);

		T model = getModel();

		List<CommerceWarehouseItem> commerceWarehouseItems =
			_commerceWarehouseItemService.getCommerceWarehouseItems(
				model.getModelClassName(), (Long)model.getPrimaryKeyObj());

		long[] commerceWarehouseIds = new long[commerceWarehouseItems.size()];

		for (int i = 0; i < commerceWarehouseItems.size(); i++) {
			CommerceWarehouseItem commerceWarehouseItem =
				commerceWarehouseItems.get(i);

			commerceWarehouseIds[i] =
				commerceWarehouseItem.getCommerceWarehouseId();
		}

		String commerceWarehouseIdsString = StringUtil.merge(
			commerceWarehouseIds);

		itemSelectorURL.setParameter(
			"checkedCommerceWarehouseIds", commerceWarehouseIdsString);
		itemSelectorURL.setParameter(
			"disabledCommerceWarehouseIds", commerceWarehouseIdsString);

		return itemSelectorURL.toString();
	}

	public abstract T getModel() throws PortalException;

	public String getOrderByCol() {
		return ParamUtil.getString(
			cpRequestHelper.getRenderRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "name");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			cpRequestHelper.getRenderRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");
	}

	public PortletURL getPortletURL() throws PortalException {
		RenderResponse renderResponse = cpRequestHelper.getRenderResponse();

		PortletURL portletURL = renderResponse.createRenderURL();

		String delta = ParamUtil.getString(
			cpRequestHelper.getRenderRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		String redirect = ParamUtil.getString(
			cpRequestHelper.getRenderRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		return portletURL;
	}

	public SearchContainer<CommerceWarehouseItem> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null,
			"there-are-no-items-in-the-warehouse");

		if (isShowAddButton()) {
			_searchContainer.setEmptyResultsMessageCssClass(
				"taglib-empty-result-message-header-has-plus-btn");
		}

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CommerceWarehouseItem> orderByComparator =
			CommerceUtil.getCommerceWarehouseItemOrderByComparator(
				orderByCol, orderByType);

		_searchContainer.setOrderByCol(orderByCol);
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(orderByType);

		T model = getModel();

		String className = model.getModelClassName();
		long classPK = (Long)model.getPrimaryKeyObj();

		int total =
			_commerceWarehouseItemService.getCommerceWarehouseItemsCount(
				className, classPK);
		List<CommerceWarehouseItem> results =
			_commerceWarehouseItemService.getCommerceWarehouseItems(
				className, classPK, _searchContainer.getStart(),
				_searchContainer.getEnd(),
				_searchContainer.getOrderByComparator());

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public abstract String getTitle() throws PortalException;

	public abstract boolean isShowAddButton() throws PortalException;

	protected final CPRequestHelper cpRequestHelper;

	private final CommerceWarehouseItemService _commerceWarehouseItemService;
	private final ItemSelector _itemSelector;
	private SearchContainer<CommerceWarehouseItem> _searchContainer;

}