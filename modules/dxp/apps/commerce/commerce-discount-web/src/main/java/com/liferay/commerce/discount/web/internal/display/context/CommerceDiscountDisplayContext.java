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

package com.liferay.commerce.discount.web.internal.display.context;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.discount.service.CommerceDiscountUserSegmentRelService;
import com.liferay.commerce.discount.target.CommerceDiscountTarget;
import com.liferay.commerce.discount.target.CommerceDiscountTargetRegistry;
import com.liferay.commerce.discount.util.comparator.CommerceDiscountUserSegmentRelCreateDateComparator;
import com.liferay.commerce.discount.web.internal.display.context.util.CommerceDiscountRequestHelper;
import com.liferay.commerce.discount.web.internal.util.CommerceDiscountPortletUtil;
import com.liferay.commerce.user.segment.item.selector.criterion.CommerceUserSegmentEntryItemSelectorCriterion;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountDisplayContext {

	public CommerceDiscountDisplayContext(
		CommerceCurrencyService commerceCurrencyService,
		CommerceDiscountService commerceDiscountService,
		CommerceDiscountTargetRegistry commerceDiscountTargetRegistry,
		CommerceDiscountUserSegmentRelService
			commerceDiscountUserSegmentRelService,
		CommercePriceFormatter commercePriceFormatter,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector) {

		_commerceCurrencyService = commerceCurrencyService;
		_commerceDiscountService = commerceDiscountService;
		_commerceDiscountTargetRegistry = commerceDiscountTargetRegistry;
		_commerceDiscountUserSegmentRelService =
			commerceDiscountUserSegmentRelService;
		_commercePriceFormatter = commercePriceFormatter;

		this.itemSelector = itemSelector;

		commerceDiscountRequestHelper = new CommerceDiscountRequestHelper(
			httpServletRequest);
		portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			httpServletRequest);
	}

	public String format(BigDecimal price) {
		return _commercePriceFormatter.format(price);
	}

	public CommerceDiscount getCommerceDiscount() throws PortalException {
		if (_commerceDiscount != null) {
			return _commerceDiscount;
		}

		long commerceDiscountId = ParamUtil.getLong(
			commerceDiscountRequestHelper.getRequest(), "commerceDiscountId");

		if (commerceDiscountId > 0) {
			_commerceDiscount = _commerceDiscountService.getCommerceDiscount(
				commerceDiscountId);
		}

		return _commerceDiscount;
	}

	public long getCommerceDiscountId() throws PortalException {
		CommerceDiscount commerceDiscount = getCommerceDiscount();

		if (commerceDiscount == null) {
			return 0;
		}

		return commerceDiscount.getCommerceDiscountId();
	}

	public List<CommerceDiscountTarget> getCommerceDiscountTargets() {
		return _commerceDiscountTargetRegistry.getCommerceDiscountTargets();
	}

	public List<CommerceDiscountUserSegmentRel>
			getCommerceDiscountUserSegmentRels()
		throws PortalException {

		if (getCommerceDiscountId() > 0) {
			return _commerceDiscountUserSegmentRelService.
				getCommerceDiscountUserSegmentRels(
					getCommerceDiscountId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS,
					new CommerceDiscountUserSegmentRelCreateDateComparator());
		}

		return Collections.emptyList();
	}

	public String getDefaultCommerceCurrencyCode() {
		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(
				commerceDiscountRequestHelper.getScopeGroupId());

		if (commerceCurrency == null) {
			return StringPool.BLANK;
		}

		return commerceCurrency.getCode();
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				commerceDiscountRequestHelper.getRequest());

		CommerceUserSegmentEntryItemSelectorCriterion
			commerceUserSegmentEntryItemSelectorCriterion =
				new CommerceUserSegmentEntryItemSelectorCriterion();

		commerceUserSegmentEntryItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "userSegmentSelectItem",
			commerceUserSegmentEntryItemSelectorCriterion);

		String checkedCommerceUserSegmentEntryIds = StringUtil.merge(
			getCheckedCommerceUserSegmentEntryIds());

		itemSelectorURL.setParameter(
			"checkedCommerceUserSegmentEntryIds",
			checkedCommerceUserSegmentEntryIds);

		return itemSelectorURL.toString();
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			commerceDiscountRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		HttpServletRequest httpServletRequest =
			commerceDiscountRequestHelper.getRequest();

		long commerceDiscountId = ParamUtil.getLong(
			httpServletRequest, "commerceDiscountId");

		if (commerceDiscountId > 0) {
			portletURL.setParameter(
				"commerceDiscountId", String.valueOf(commerceDiscountId));
		}

		String delta = ParamUtil.getString(httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String orderByCol = ParamUtil.getString(
			httpServletRequest, "orderByCol");

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = ParamUtil.getString(
			httpServletRequest, "orderByType");

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String screenNavigationCategoryKey = ParamUtil.getString(
			httpServletRequest, "screenNavigationCategoryKey");

		if (Validator.isNotNull(screenNavigationCategoryKey)) {
			portletURL.setParameter(
				"screenNavigationCategoryKey", screenNavigationCategoryKey);
		}

		String screenNavigationEntryKey = ParamUtil.getString(
			httpServletRequest, "screenNavigationEntryKey");

		if (Validator.isNotNull(screenNavigationEntryKey)) {
			portletURL.setParameter(
				"screenNavigationEntryKey", screenNavigationEntryKey);
		}

		return portletURL;
	}

	public SearchContainer<CommerceDiscount> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			commerceDiscountRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-discounts");

		setOrderByColAndType(
			CommerceDiscount.class, _searchContainer, "create-date", "desc");

		OrderByComparator<CommerceDiscount> orderByComparator =
			CommerceDiscountPortletUtil.getCommerceDiscountOrderByComparator(
				_searchContainer.getOrderByCol(),
				_searchContainer.getOrderByType());

		_searchContainer.setOrderByComparator(orderByComparator);

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				commerceDiscountRequestHelper.getLiferayPortletResponse()));

		long groupId = commerceDiscountRequestHelper.getScopeGroupId();

		if (isSearch()) {
			Sort sort = CommerceDiscountPortletUtil.getCommerceDiscountSort(
				_searchContainer.getOrderByCol(),
				_searchContainer.getOrderByType());

			BaseModelSearchResult<CommerceDiscount> baseModelSearchResult =
				_commerceDiscountService.searchCommerceDiscounts(
					commerceDiscountRequestHelper.getCompanyId(), groupId,
					getKeywords(), WorkflowConstants.STATUS_ANY,
					_searchContainer.getStart(), _searchContainer.getEnd(),
					sort);

			_searchContainer.setTotal(baseModelSearchResult.getLength());
			_searchContainer.setResults(baseModelSearchResult.getBaseModels());
		}
		else {
			int total = _commerceDiscountService.getCommerceDiscountsCount(
				groupId);

			_searchContainer.setTotal(total);

			List<CommerceDiscount> results =
				_commerceDiscountService.getCommerceDiscounts(
					groupId, _searchContainer.getStart(),
					_searchContainer.getEnd(), orderByComparator);

			_searchContainer.setResults(results);
		}

		return _searchContainer;
	}

	public boolean hasCustomAttributesAvailable() throws Exception {
		return CustomAttributesUtil.hasCustomAttributes(
			commerceDiscountRequestHelper.getCompanyId(),
			CommerceDiscount.class.getName(), getCommerceDiscountId(), null);
	}

	protected long[] getCheckedCommerceUserSegmentEntryIds()
		throws PortalException {

		List<Long> commerceUserSegmentEntryIdsList = new ArrayList<>();

		List<CommerceDiscountUserSegmentRel> commerceDiscountUserSegmentRels =
			getCommerceDiscountUserSegmentRels();

		for (CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel :
				commerceDiscountUserSegmentRels) {

			commerceUserSegmentEntryIdsList.add(
				commerceDiscountUserSegmentRel.getCommerceUserSegmentEntryId());
		}

		if (commerceUserSegmentEntryIdsList.isEmpty()) {
			return new long[0];
		}

		Stream<Long> stream = commerceUserSegmentEntryIdsList.stream();

		return stream.mapToLong(l -> l).toArray();
	}

	protected String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			commerceDiscountRequestHelper.getRequest(), "keywords");

		return _keywords;
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected <T> void setOrderByColAndType(
		Class<T> clazz, SearchContainer<T> searchContainer,
		String defaultOrderByCol, String defaultOrderByType) {

		HttpServletRequest httpServletRequest =
			commerceDiscountRequestHelper.getRequest();

		String orderByCol = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByColParam());
		String orderByType = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByTypeParam());

		String namespace = commerceDiscountRequestHelper.getPortletId();
		String prefix = TextFormatter.format(
			clazz.getSimpleName(), TextFormatter.K);

		if (Validator.isNotNull(orderByCol) &&
			Validator.isNotNull(orderByType)) {

			portalPreferences.setValue(
				namespace, prefix + "-order-by-col", orderByCol);
			portalPreferences.setValue(
				namespace, prefix + "-order-by-type", orderByType);
		}
		else {
			orderByCol = portalPreferences.getValue(
				namespace, prefix + "-order-by-col", defaultOrderByCol);
			orderByType = portalPreferences.getValue(
				namespace, prefix + "-order-by-type", defaultOrderByType);
		}

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
	}

	protected final CommerceDiscountRequestHelper commerceDiscountRequestHelper;
	protected final ItemSelector itemSelector;
	protected final PortalPreferences portalPreferences;

	private final CommerceCurrencyService _commerceCurrencyService;
	private CommerceDiscount _commerceDiscount;
	private final CommerceDiscountService _commerceDiscountService;
	private final CommerceDiscountTargetRegistry
		_commerceDiscountTargetRegistry;
	private final CommerceDiscountUserSegmentRelService
		_commerceDiscountUserSegmentRelService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private String _keywords;
	private SearchContainer<CommerceDiscount> _searchContainer;

}