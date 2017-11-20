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
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.comparator.CommerceCurrencyPriorityComparator;
import com.liferay.commerce.item.selector.criterion.CommercePriceListQualificationTypeItemSelectorCriterion;
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;
import com.liferay.commerce.price.CommercePriceListQualificationType;
import com.liferay.commerce.price.CommercePriceListQualificationTypeRegistry;
import com.liferay.commerce.price.list.web.display.context.BaseCommercePriceListDisplayContext;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.service.CommercePriceListQualificationTypeRelService;
import com.liferay.commerce.service.CommercePriceListService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListDisplayContext
	extends BaseCommercePriceListDisplayContext<CommercePriceList> {

	public CommercePriceListDisplayContext(
		CommercePriceListActionHelper commercePriceListActionHelper,
		CommerceCurrencyService commerceCurrencyService,
		CommercePriceListQualificationTypeRegistry
			commercePriceListQualificationTypeRegistry,
		CommercePriceListQualificationTypeRelService
			commercePriceListQualificationTypeRelService,
		CommercePriceListService commercePriceListService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector) {

		super(commercePriceListActionHelper, httpServletRequest);

		_commerceCurrencyService = commerceCurrencyService;
		_commercePriceListQualificationTypeRegistry =
			commercePriceListQualificationTypeRegistry;
		_commercePriceListQualificationTypeRelService =
			commercePriceListQualificationTypeRelService;
		_commercePriceListService = commercePriceListService;
		_itemSelector = itemSelector;

		setDefaultOrderByCol("priority");
		setDefaultOrderByType("asc");
	}

	public List<CommerceCurrency> getCommerceCurrencies() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _commerceCurrencyService.getCommerceCurrencies(
			themeDisplay.getScopeGroupId(), true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new CommerceCurrencyPriorityComparator(true));
	}

	public CommercePriceListQualificationType
		getCommercePriceListQualificationType(String key) {

		return _commercePriceListQualificationTypeRegistry.
			getCommercePriceListQualificationType(key);
	}

	public List<CommercePriceListQualificationTypeRel>
			getCommercePriceListQualificationTypeRels()
		throws PortalException {

		return _commercePriceListQualificationTypeRelService.
			getCommercePriceListQualificationTypeRels(getCommercePriceListId());
	}

	public List<CommercePriceListQualificationType>
		getCommercePriceListQualificationTypes() {

		return _commercePriceListQualificationTypeRegistry.
			getCommercePriceListQualificationTypes();
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		CommercePriceListQualificationTypeItemSelectorCriterion
			commercePriceListQualificationTypeItemSelectorCriterion =
				new CommercePriceListQualificationTypeItemSelectorCriterion();

		commercePriceListQualificationTypeItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "qualificationTypesSelectItem",
			commercePriceListQualificationTypeItemSelectorCriterion);

		String checkedCommercePriceListQualificationTypeKeys = StringUtil.merge(
			getCheckedCommercePriceListQualificationTypeKeys());

		itemSelectorURL.setParameter(
			"checkedCommercePriceListQualificationTypeKeys",
			checkedCommercePriceListQualificationTypeKeys);

		return itemSelectorURL.toString();
	}

	@Override
	public SearchContainer<CommercePriceList> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null,
			"there-are-no-price-lists");

		OrderByComparator<CommercePriceList> orderByComparator =
			CommerceUtil.getCommercePriceListOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CommerceUtil.getCommercePriceListSort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CommercePriceList>
				commercePriceListBaseModelSearchResult =
					_commercePriceListService.searchCommercePriceLists(
						themeDisplay.getCompanyId(),
						themeDisplay.getScopeGroupId(), getKeywords(),
						getStatus(), searchContainer.getStart(),
						searchContainer.getEnd(), sort);

			searchContainer.setTotal(
				commercePriceListBaseModelSearchResult.getLength());
			searchContainer.setResults(
				commercePriceListBaseModelSearchResult.getBaseModels());
		}
		else {
			int total = _commercePriceListService.getCommercePriceListsCount(
				themeDisplay.getScopeGroupId(), getStatus());

			searchContainer.setTotal(total);

			List<CommercePriceList> results =
				_commercePriceListService.getCommercePriceLists(
					themeDisplay.getScopeGroupId(), getStatus(),
					searchContainer.getStart(), searchContainer.getEnd(),
					orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	public int getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = ParamUtil.getInteger(
			httpServletRequest, "status", WorkflowConstants.STATUS_ANY);

		return _status;
	}

	protected String[] getCheckedCommercePriceListQualificationTypeKeys()
		throws PortalException {

		List<String> commercePriceListQualificationTypeKeysList =
			new ArrayList<>();

		List<CommercePriceListQualificationTypeRel>
			commercePriceListQualificationTypeRels =
				getCommercePriceListQualificationTypeRels();

		for (CommercePriceListQualificationTypeRel
				commercePriceListQualificationTypeRel :
					commercePriceListQualificationTypeRels) {

			commercePriceListQualificationTypeKeysList.add(
				commercePriceListQualificationTypeRel.
					getCommercePriceListQualificationType());
		}

		if (!commercePriceListQualificationTypeKeysList.isEmpty()) {
			return ArrayUtil.toStringArray(
				commercePriceListQualificationTypeKeysList);
		}

		return new String[0];
	}

	private final CommerceCurrencyService _commerceCurrencyService;
	private final CommercePriceListQualificationTypeRegistry
		_commercePriceListQualificationTypeRegistry;
	private final CommercePriceListQualificationTypeRelService
		_commercePriceListQualificationTypeRelService;
	private final CommercePriceListService _commercePriceListService;
	private final ItemSelector _itemSelector;
	private Integer _status;

}