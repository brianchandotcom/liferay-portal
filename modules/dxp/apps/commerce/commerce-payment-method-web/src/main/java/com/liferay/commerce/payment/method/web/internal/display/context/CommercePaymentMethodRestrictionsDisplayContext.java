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

package com.liferay.commerce.payment.method.web.internal.display.context;

import com.liferay.commerce.item.selector.criterion.CommerceCountryItemSelectorCriterion;
import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.payment.method.web.internal.util.PaymentMethodsCommerceAdminModule;
import com.liferay.commerce.service.CommerceAddressRestrictionService;
import com.liferay.commerce.service.CommercePaymentMethodService;
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
public class CommercePaymentMethodRestrictionsDisplayContext {

	public CommercePaymentMethodRestrictionsDisplayContext(
		CommerceAddressRestrictionService commerceAddressRestrictionService,
		CommercePaymentMethodService commercePaymentMethodService,
		ItemSelector itemSelector, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_commerceAddressRestrictionService = commerceAddressRestrictionService;
		_commercePaymentMethodService = commercePaymentMethodService;
		_itemSelector = itemSelector;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CommercePaymentMethod getCommercePaymentMethod()
		throws PortalException {

		if (_commercePaymentMethod != null) {
			return _commercePaymentMethod;
		}

		long commercePaymentMethodId = ParamUtil.getLong(
			_renderRequest, "commercePaymentMethodId");

		if (commercePaymentMethodId > 0) {
			_commercePaymentMethod =
				_commercePaymentMethodService.getCommercePaymentMethod(
					commercePaymentMethodId);
		}

		return _commercePaymentMethod;
	}

	public long getCommercePaymentMethodId() throws PortalException {
		CommercePaymentMethod commercePaymentMethod =
			getCommercePaymentMethod();

		if (commercePaymentMethod == null) {
			return 0;
		}

		return commercePaymentMethod.getCommercePaymentMethodId();
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
			"commerceAdminModuleKey", PaymentMethodsCommerceAdminModule.KEY);
		portletURL.setParameter(
			"mvcRenderCommandName",
			"viewCommercePaymentMethodAddressRestriction");

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(_renderResponse);
		}

		return _rowChecker;
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
					CommercePaymentMethod.class.getName(),
					getCommercePaymentMethodId());

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
				CommercePaymentMethod.class.getName(),
				getCommercePaymentMethodId(), start, end, orderByComparator);
	}

	private final CommerceAddressRestrictionService
		_commerceAddressRestrictionService;
	private CommercePaymentMethod _commercePaymentMethod;
	private final CommercePaymentMethodService _commercePaymentMethodService;
	private final ItemSelector _itemSelector;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RowChecker _rowChecker;
	private SearchContainer<CommerceAddressRestriction> _searchContainer;

}