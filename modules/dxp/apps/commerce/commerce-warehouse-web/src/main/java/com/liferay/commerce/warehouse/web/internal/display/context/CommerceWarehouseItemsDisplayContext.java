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
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPInstanceScreenNavigationConstants;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.commerce.service.CommerceWarehouseItemService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Marco Leo
 */
public class CommerceWarehouseItemsDisplayContext {

	public CommerceWarehouseItemsDisplayContext(
		CommerceWarehouseItemService commerceWarehouseItemService,
		CPInstanceService cpInstanceService, ItemSelector itemSelector,
		HttpServletRequest httpServletRequest, Portal portal) {

		_commerceWarehouseItemService = commerceWarehouseItemService;
		_cpInstanceService = cpInstanceService;
		_itemSelector = itemSelector;
		_portal = portal;

		cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public String getBackURL() throws PortalException {
		RenderRequest renderRequest = cpRequestHelper.getRenderRequest();

		String lifecycle = (String)renderRequest.getAttribute(
			LiferayPortletRequest.LIFECYCLE_PHASE);

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			renderRequest, CPPortletKeys.CP_DEFINITIONS, lifecycle);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");

		CPInstance cpInstance = getCPInstance();

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId()));

		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS);

		return portletURL.toString();
	}

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance == null) {
			long cpInstanceId = ParamUtil.getLong(
				cpRequestHelper.getRenderRequest(), "cpInstanceId");

			_cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);
		}

		return _cpInstance;
	}

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

		CPInstance cpInstance = getCPInstance();

		List<CommerceWarehouseItem> commerceWarehouseItems =
			_commerceWarehouseItemService.getCommerceWarehouseItems(
				cpInstance.getCPInstanceId());

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

		portletURL.setParameter("mvcRenderCommandName", "editProductInstance");

		CPInstance cpInstance = getCPInstance();

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(cpInstance.getCPInstanceId()));

		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPInstanceScreenNavigationConstants.CATEGORY_KEY_DETAILS);

		portletURL.setParameter("screenNavigationEntryKey", "warehouses");

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

		CPInstance cpInstance = getCPInstance();

		int total =
			_commerceWarehouseItemService.getCommerceWarehouseItemsCount(
				cpInstance.getCPInstanceId());
		List<CommerceWarehouseItem> results =
			_commerceWarehouseItemService.getCommerceWarehouseItems(
				cpInstance.getCPInstanceId(), _searchContainer.getStart(),
				_searchContainer.getEnd(),
				_searchContainer.getOrderByComparator());

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public String getTitle() throws PortalException {
		CPInstance cpInstance = getCPInstance();

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();

		return cpDefinition.getTitle(themeDisplay.getLanguageId()) + " - " +
			cpInstance.getSku();
	}

	public boolean isShowAddButton() throws PortalException {
		CPInstance cpInstance = getCPInstance();

		return CPDefinitionPermission.contains(
			cpRequestHelper.getPermissionChecker(),
			cpInstance.getCPDefinitionId(), ActionKeys.UPDATE);
	}

	protected final CPRequestHelper cpRequestHelper;

	private final CommerceWarehouseItemService _commerceWarehouseItemService;
	private CPInstance _cpInstance;
	private final CPInstanceService _cpInstanceService;
	private final ItemSelector _itemSelector;
	private final Portal _portal;
	private SearchContainer<CommerceWarehouseItem> _searchContainer;

}