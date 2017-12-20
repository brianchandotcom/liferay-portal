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

package com.liferay.commerce.shipping.web.internal.display.context;

import com.liferay.commerce.item.selector.criterion.CommerceCountryItemSelectorCriterion;
import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.CommerceAddressRestrictionService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.web.servlet.taglib.ui.CommerceShippingScreenNavigationConstants;
import com.liferay.commerce.shipping.web.util.ShippingMethodsCommerceAdminModule;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingMethodRestrictionsDisplayContext {

	public CommerceShippingMethodRestrictionsDisplayContext(
		CommerceAddressRestrictionService commerceAddressRestrictionService,
		CommerceShippingMethodService commerceShippingMethodService,
		ItemSelector itemSelector, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_commerceAddressRestrictionService = commerceAddressRestrictionService;
		_commerceShippingMethodService = commerceShippingMethodService;
		_itemSelector = itemSelector;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CommerceShippingMethod getCommerceShippingMethod()
		throws PortalException {

		if (_commerceShippingMethod != null) {
			return _commerceShippingMethod;
		}

		long commerceShippingMethodId = ParamUtil.getLong(
			_renderRequest, "commerceShippingMethodId");

		if (commerceShippingMethodId > 0) {
			_commerceShippingMethod =
				_commerceShippingMethodService.getCommerceShippingMethod(
					commerceShippingMethodId);
		}

		return _commerceShippingMethod;
	}

	public long getCommerceShippingMethodId() throws PortalException {
		CommerceShippingMethod commerceShippingMethod =
			getCommerceShippingMethod();

		if (commerceShippingMethod == null) {
			return 0;
		}

		return commerceShippingMethod.getCommerceShippingMethodId();
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_renderRequest);

		CommerceCountryItemSelectorCriterion
			commerceCountryItemSelectorCriterion =
				new CommerceCountryItemSelectorCriterion();

		commerceCountryItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "countriesSelectItem",
			commerceCountryItemSelectorCriterion);

		String checkedCommerceCountryIds = StringUtil.merge(
			getCheckedCommerceCountryIds());

		itemSelectorURL.setParameter(
			"checkedCommerceCountryIds", checkedCommerceCountryIds);

		return itemSelectorURL.toString();
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"commerceAdminModuleKey", ShippingMethodsCommerceAdminModule.KEY);

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		portletURL.setParameter(
			"screenNavigationEntryKey", getScreenNavigationEntryKey());

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(_renderResponse);
		}

		return _rowChecker;
	}

	public String getScreenNavigationEntryKey() {
		return CommerceShippingScreenNavigationConstants.
			ENTRY_KEY_COMMERCE_SHIPPING_METHOD_RESTRICTIONS;
	}

	public SearchContainer<CommerceAddressRestriction> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("there-are-no-restrictions");

		String orderByCol = "create-date";
		String orderByType = "desc";

		OrderByComparator<CommerceAddressRestriction> orderByComparator =
			CommerceUtil.getCommerceAddressRestrictionOrderByComparator(
				orderByCol, orderByType);

		_searchContainer.setOrderByCol(orderByCol);
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(orderByType);
		_searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceAddressRestrictionService.
				getCommerceAddressRestrictionsCount(
					CommerceShippingMethod.class.getName(),
					getCommerceShippingMethodId());

		_searchContainer.setTotal(total);

		List<CommerceAddressRestriction> results =
			getCommerceAddressRestrictions(
				_searchContainer.getStart(), _searchContainer.getEnd(),
				orderByComparator);

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	protected long[] getCheckedCommerceCountryIds() throws PortalException {
		List<Long> commerceCountryIdsList = new ArrayList<>();

		List<CommerceAddressRestriction> commerceAddressRestrictions =
			getCommerceAddressRestrictions();

		for (CommerceAddressRestriction commerceAddressRestriction :
				commerceAddressRestrictions) {

			commerceCountryIdsList.add(
				commerceAddressRestriction.getCommerceCountryId());
		}

		if (!commerceCountryIdsList.isEmpty()) {
			return ArrayUtil.toLongArray(commerceCountryIdsList);
		}

		return new long[0];
	}

	protected List<CommerceAddressRestriction> getCommerceAddressRestrictions()
		throws PortalException {

		return getCommerceAddressRestrictions(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	protected List<CommerceAddressRestriction> getCommerceAddressRestrictions(
			int start, int end,
			OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws PortalException {

		return
			_commerceAddressRestrictionService.getCommerceAddressRestrictions(
				CommerceShippingMethod.class.getName(),
				getCommerceShippingMethodId(), start, end, orderByComparator);
	}

	private final CommerceAddressRestrictionService
		_commerceAddressRestrictionService;
	private CommerceShippingMethod _commerceShippingMethod;
	private final CommerceShippingMethodService _commerceShippingMethodService;
	private final ItemSelector _itemSelector;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RowChecker _rowChecker;
	private SearchContainer<CommerceAddressRestriction> _searchContainer;

}