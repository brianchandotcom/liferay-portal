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

package com.liferay.commerce.price.list.web.internal.display.context;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.item.selector.criterion.CommerceProductInstanceItemSelectorCriterion;
import com.liferay.commerce.price.list.constants.CommercePriceListScreenNavigationConstants;
import com.liferay.commerce.price.list.display.context.BaseCommercePriceListDisplayContext;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.web.internal.util.CommercePriceListPortletUtil;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceEntryDisplayContext
	extends BaseCommercePriceListDisplayContext<CommercePriceEntry> {

	public CommercePriceEntryDisplayContext(
		CommercePriceListActionHelper commercePriceListActionHelper,
		CommercePriceEntryService commercePriceEntryService,
		ModelResourcePermission<CommercePriceList>
			commercePriceListModelResourcePermission,
		ItemSelector itemSelector, HttpServletRequest httpServletRequest) {

		super(
			commercePriceListActionHelper,
			commercePriceListModelResourcePermission, httpServletRequest);

		_commercePriceEntryService = commercePriceEntryService;
		_itemSelector = itemSelector;
	}

	public CommercePriceEntry getCommercePriceEntry() throws PortalException {
		if (_commercePriceEntry != null) {
			return _commercePriceEntry;
		}

		CPRequestHelper cpRequestHelper = new CPRequestHelper(
			httpServletRequest);

		_commercePriceEntry =
			commercePriceListActionHelper.getCommercePriceEntry(
				cpRequestHelper.getRenderRequest());

		return _commercePriceEntry;
	}

	public long getCommercePriceEntryId() throws PortalException {
		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry == null) {
			return 0;
		}

		return commercePriceEntry.getCommercePriceEntryId();
	}

	public String getCommercePriceEntryPrice(
			CommercePriceEntry commercePriceEntry)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceEntry.getCommercePriceList();

		CommerceMoney priceCommerceMoney = commercePriceEntry.getPriceMoney(
			commercePriceList.getCommerceCurrencyId());

		return priceCommerceMoney.format(
			PortalUtil.getLocale(httpServletRequest));
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		CommerceProductInstanceItemSelectorCriterion
			commerceProductInstanceItemSelectorCriterion =
				new CommerceProductInstanceItemSelectorCriterion();

		commerceProductInstanceItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "productInstancesSelectItem",
			commerceProductInstanceItemSelectorCriterion);

		CommercePriceList commercePriceList = getCommercePriceList();

		itemSelectorURL.setParameter(
			"commerceCatalogGroupId",
			String.valueOf(commercePriceList.getGroupId()));
		itemSelectorURL.setParameter(
			"commercePriceListId",
			String.valueOf(commercePriceList.getCommercePriceListId()));

		return itemSelectorURL.toString();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommercePriceList");
		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());

		return portletURL;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return CommercePriceListScreenNavigationConstants.CATEGORY_KEY_ENTRIES;
	}

	@Override
	public SearchContainer<CommercePriceEntry> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null,
			"there-are-no-price-entries");

		OrderByComparator<CommercePriceEntry> orderByComparator =
			CommercePriceListPortletUtil.getCommercePriceEntryOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CommercePriceListPortletUtil.getCommercePriceEntrySort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CommercePriceEntry>
				commercePriceListBaseModelSearchResult =
					_commercePriceEntryService.searchCommercePriceEntries(
						themeDisplay.getCompanyId(), getCommercePriceListId(),
						getKeywords(), searchContainer.getStart(),
						searchContainer.getEnd(), sort);

			searchContainer.setTotal(
				commercePriceListBaseModelSearchResult.getLength());
			searchContainer.setResults(
				commercePriceListBaseModelSearchResult.getBaseModels());
		}
		else {
			int total = _commercePriceEntryService.getCommercePriceEntriesCount(
				getCommercePriceListId());

			searchContainer.setTotal(total);

			List<CommercePriceEntry> results =
				_commercePriceEntryService.getCommercePriceEntries(
					getCommercePriceListId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	private CommercePriceEntry _commercePriceEntry;
	private final CommercePriceEntryService _commercePriceEntryService;
	private final ItemSelector _itemSelector;

}