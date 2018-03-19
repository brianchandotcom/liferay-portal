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

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.item.selector.criterion.CommercePriceListItemSelectorCriterion;
import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPInstanceScreenNavigationConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CommercePriceEntryService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPInstanceCommercePriceEntryDisplayContext
	extends BaseCPDefinitionsSearchContainerDisplayContext<CommercePriceEntry> {

	public CPInstanceCommercePriceEntryDisplayContext(
		ActionHelper actionHelper,
		CommercePriceEntryService commercePriceEntryService,
		CommercePriceFormatter commercePriceFormatter,
		CommercePriceListActionHelper commercePriceListActionHelper,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector) {

		super(
			actionHelper, httpServletRequest,
			CommercePriceEntry.class.getSimpleName());

		_commercePriceEntryService = commercePriceEntryService;
		_commercePriceFormatter = commercePriceFormatter;
		_commercePriceListActionHelper = commercePriceListActionHelper;
		_itemSelector = itemSelector;

		setDefaultOrderByCol("create-date");
		setDefaultOrderByType("desc");
	}

	public CommercePriceEntry getCommercePriceEntry() {
		return _commercePriceListActionHelper.getCommercePriceEntry(
			cpRequestHelper.getRenderRequest());
	}

	public long getCommercePriceEntryId() {
		long commercePriceEntryId = 0;

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry != null) {
			commercePriceEntryId = commercePriceEntry.getCommercePriceEntryId();
		}

		return commercePriceEntryId;
	}

	public String getCommercePriceEntryPrice(
			CommercePriceEntry commercePriceEntry)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceEntry.getCommercePriceList();

		return _commercePriceFormatter.format(
			commercePriceList.getCommerceCurrency(),
			commercePriceEntry.getPrice());
	}

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance != null) {
			return _cpInstance;
		}

		_cpInstance = actionHelper.getCPInstance(
			cpRequestHelper.getRenderRequest());

		return _cpInstance;
	}

	public long getCPInstanceId() throws PortalException {
		long cpInstanceId = 0;

		CPInstance cpInstance = getCPInstance();

		if (cpInstance != null) {
			cpInstanceId = cpInstance.getCPInstanceId();
		}

		return cpInstanceId;
	}

	public PortletURL getInstancePriceListURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "editProductInstance");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(getCPInstanceId()));
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPInstanceScreenNavigationConstants.CATEGORY_KEY_DETAILS);
		portletURL.setParameter(
			"screenNavigationEntryKey", getScreenNavigationEntryKey());

		return portletURL;
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		CommercePriceListItemSelectorCriterion
			commercePriceListItemSelectorCriterion =
				new CommercePriceListItemSelectorCriterion();

		commercePriceListItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "priceListsSelectItem",
			commercePriceListItemSelectorCriterion);

		String checkedCommercePriceListIds = StringUtil.merge(
			getCheckedCommercePriceListIds());

		itemSelectorURL.setParameter(
			"checkedCommercePriceListIds", checkedCommercePriceListIds);

		return itemSelectorURL.toString();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter("mvcRenderCommandName", "editProductInstance");
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(getCPInstanceId()));
		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());
		portletURL.setParameter("screenNavigationEntryKey", "price-lists");

		return portletURL;
	}

	public PortletURL getProductSkusURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS);

		return portletURL;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return "price-lists";
	}

	public String getScreenNavigationEntryKey() {
		return "price-lists";
	}

	@Override
	public SearchContainer<CommercePriceEntry> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null,
			"there-are-no-price-entries");

		OrderByComparator<CommercePriceEntry> orderByComparator =
			CommerceUtil.getCommercePriceEntryOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commercePriceEntryService.getInstanceCommercePriceEntriesCount(
				getCPInstanceId());

		searchContainer.setTotal(total);

		List<CommercePriceEntry> results =
			_commercePriceEntryService.getInstanceCommercePriceEntries(
				getCPInstanceId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	protected long[] getCheckedCommercePriceListIds() throws PortalException {
		List<Long> commercePriceListIds = new ArrayList<>();

		List<CommercePriceEntry> commercePriceEntries =
			getCommercePriceEntries();

		for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
			commercePriceListIds.add(
				commercePriceEntry.getCommercePriceListId());
		}

		if (!commercePriceListIds.isEmpty()) {
			return ArrayUtil.toLongArray(commercePriceListIds);
		}

		return new long[0];
	}

	protected List<CommercePriceEntry> getCommercePriceEntries()
		throws PortalException {

		return _commercePriceEntryService.getInstanceCommercePriceEntries(
			getCPInstanceId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	private final CommercePriceEntryService _commercePriceEntryService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CommercePriceListActionHelper _commercePriceListActionHelper;
	private CPInstance _cpInstance;
	private final ItemSelector _itemSelector;

}