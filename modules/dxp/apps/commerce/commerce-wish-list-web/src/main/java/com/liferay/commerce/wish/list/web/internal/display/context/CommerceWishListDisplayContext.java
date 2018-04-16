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

package com.liferay.commerce.wish.list.web.internal.display.context;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommercePriceCalculationLocalService;
import com.liferay.commerce.wish.list.constants.CommerceWishListPortletKeys;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.commerce.wish.list.util.CommerceWishListHttpHelper;
import com.liferay.commerce.wish.list.web.internal.display.context.util.CommerceWishListRequestHelper;
import com.liferay.commerce.wish.list.web.internal.util.CommerceWishListPortletUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
public class CommerceWishListDisplayContext {

	public CommerceWishListDisplayContext(
		CommercePriceCalculationLocalService
			commercePriceCalculationLocalService,
		CommerceWishListHttpHelper commerceWishListHttpHelper,
		CommerceWishListItemService commerceWishListItemService,
		CommerceWishListService commerceWishListService,
		CPDefinitionHelper cpDefinitionHelper,
		CPInstanceHelper cpInstanceHelper,
		HttpServletRequest httpServletRequest) {

		_commercePriceCalculationLocalService =
			commercePriceCalculationLocalService;
		_commerceWishListHttpHelper = commerceWishListHttpHelper;
		_commerceWishListItemService = commerceWishListItemService;
		_commerceWishListService = commerceWishListService;
		_cpDefinitionHelper = cpDefinitionHelper;
		_cpInstanceHelper = cpInstanceHelper;

		_commerceWishListRequestHelper = new CommerceWishListRequestHelper(
			httpServletRequest);
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			httpServletRequest);
	}

	public CommerceWishList getCommerceWishList() throws PortalException {
		if (_commerceWishList != null) {
			return _commerceWishList;
		}

		HttpServletRequest httpServletRequest =
			_commerceWishListRequestHelper.getRequest();

		long commerceWishListId = ParamUtil.getLong(
			httpServletRequest, "commerceWishListId");

		if (commerceWishListId > 0) {
			_commerceWishList = _commerceWishListService.getCommerceWishList(
				commerceWishListId);
		}
		else if (isContentPortlet()) {
			_commerceWishList =
				_commerceWishListHttpHelper.getCurrentCommerceWishList(
					httpServletRequest,
					_commerceWishListRequestHelper.getResponse());
		}

		return _commerceWishList;
	}

	public long getCommerceWishListId() throws PortalException {
		CommerceWishList commerceWishList = getCommerceWishList();

		if (commerceWishList == null) {
			return 0;
		}

		return commerceWishList.getCommerceWishListId();
	}

	public String getCommerceWishListItemDescription(
			CommerceWishListItem commerceWishListItem)
		throws PortalException {

		List<KeyValuePair> keyValuePairs = _cpInstanceHelper.getKeyValuePairs(
			commerceWishListItem.getJson(),
			_commerceWishListRequestHelper.getLocale());

		StringBundler sb = new StringBundler(keyValuePairs.size() * 2 - 1);

		boolean first = true;

		for (KeyValuePair keyValuePair : keyValuePairs) {
			if (!first) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}
			else {
				first = false;
			}

			sb.append(keyValuePair.getValue());
		}

		return sb.toString();
	}

	public String getCommerceWishListItemPrice(
			CommerceWishListItem commerceWishListItem)
		throws PortalException {

		CPInstance cpInstance = commerceWishListItem.fetchCPInstance();

		if (cpInstance != null) {
			return _commercePriceCalculationLocalService.getFormattedFinalPrice(
				_commerceWishListRequestHelper.getScopeGroupId(),
				_commerceWishListRequestHelper.getUserId(),
				cpInstance.getCPInstanceId(), 1);
		}

		return StringPool.BLANK;
	}

	public SearchContainer<CommerceWishListItem>
		getCommerceWishListItemsSearchContainer() throws PortalException {

		if (_commerceWishListItemsSearchContainer != null) {
			return _commerceWishListItemsSearchContainer;
		}

		_commerceWishListItemsSearchContainer = new SearchContainer<>(
			_commerceWishListRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "the-wish-list-is-empty");

		setOrderByColAndType(
			CommerceWishListItem.class, _commerceWishListItemsSearchContainer,
			"create-date", "desc");

		OrderByComparator<CommerceWishListItem> orderByComparator =
			CommerceWishListPortletUtil.
				getCommerceWishListItemOrderByComparator(
					_commerceWishListItemsSearchContainer.getOrderByCol(),
					_commerceWishListItemsSearchContainer.getOrderByType());

		_commerceWishListItemsSearchContainer.setOrderByComparator(
			orderByComparator);

		_commerceWishListItemsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_commerceWishListRequestHelper.getLiferayPortletResponse()));

		CommerceWishList commerceWishList = getCommerceWishList();

		int total = _commerceWishListItemService.getCommerceWishListItemsCount(
			commerceWishList.getCommerceWishListId());

		_commerceWishListItemsSearchContainer.setTotal(total);

		List<CommerceWishListItem> results =
			_commerceWishListItemService.getCommerceWishListItems(
				commerceWishList.getCommerceWishListId(),
				_commerceWishListItemsSearchContainer.getStart(),
				_commerceWishListItemsSearchContainer.getEnd(),
				orderByComparator);

		_commerceWishListItemsSearchContainer.setResults(results);

		return _commerceWishListItemsSearchContainer;
	}

	public String getCPDefinitionURL(
			long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException {

		return _cpDefinitionHelper.getFriendlyURL(cpDefinitionId, themeDisplay);
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceWishListRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		long commerceWishListId = ParamUtil.getLong(
			_commerceWishListRequestHelper.getRequest(), "commerceWishListId");

		if (commerceWishListId > 0) {
			portletURL.setParameter(
				"commerceWishListId", String.valueOf(commerceWishListId));
		}

		return portletURL;
	}

	public SearchContainer<CommerceWishList> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_commerceWishListRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "no-wish-lists-were-found");

		setOrderByColAndType(
			CommerceWishList.class, _searchContainer, "name", "asc");

		OrderByComparator<CommerceWishList> orderByComparator =
			CommerceWishListPortletUtil.getCommerceWishListOrderByComparator(
				_searchContainer.getOrderByCol(),
				_searchContainer.getOrderByType());

		_searchContainer.setOrderByComparator(orderByComparator);

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_commerceWishListRequestHelper.getLiferayPortletResponse()));

		int total = 0;
		List<CommerceWishList> results = null;

		if (isControlPanelPortlet()) {
			total = _commerceWishListService.getCommerceWishListsCount(
				_commerceWishListRequestHelper.getScopeGroupId());
			results = _commerceWishListService.getCommerceWishLists(
				_commerceWishListRequestHelper.getScopeGroupId(),
				_searchContainer.getStart(), _searchContainer.getEnd(),
				orderByComparator);
		}
		else {
			total = _commerceWishListService.getCommerceWishListsCount(
				_commerceWishListRequestHelper.getScopeGroupId(),
				_commerceWishListRequestHelper.getUserId());
			results = _commerceWishListService.getCommerceWishLists(
				_commerceWishListRequestHelper.getScopeGroupId(),
				_commerceWishListRequestHelper.getUserId(),
				_searchContainer.getStart(), _searchContainer.getEnd(),
				orderByComparator);
		}

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	protected boolean isContentPortlet() {
		if (CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT.equals(
				_commerceWishListRequestHelper.getPortletId())) {

			return true;
		}

		return false;
	}

	protected boolean isControlPanelPortlet() {
		if (isContentPortlet()) {
			return false;
		}

		ThemeDisplay themeDisplay =
			_commerceWishListRequestHelper.getThemeDisplay();

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return true;
		}

		return false;
	}

	protected <T> void setOrderByColAndType(
		Class<T> clazz, SearchContainer<T> searchContainer,
		String defaultOrderByCol, String defaultOrderByType) {

		HttpServletRequest httpServletRequest =
			_commerceWishListRequestHelper.getRequest();

		String orderByCol = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByColParam());
		String orderByType = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByTypeParam());

		String namespace = _commerceWishListRequestHelper.getPortletId();
		String prefix = TextFormatter.format(
			clazz.getSimpleName(), TextFormatter.K);

		if (Validator.isNotNull(orderByCol) &&
			Validator.isNotNull(orderByType)) {

			_portalPreferences.setValue(
				namespace, prefix + "-order-by-col", orderByCol);
			_portalPreferences.setValue(
				namespace, prefix + "-order-by-type", orderByType);
		}
		else {
			orderByCol = _portalPreferences.getValue(
				namespace, prefix + "-order-by-col", defaultOrderByCol);
			orderByType = _portalPreferences.getValue(
				namespace, prefix + "-order-by-type", defaultOrderByType);
		}

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
	}

	private final CommercePriceCalculationLocalService
		_commercePriceCalculationLocalService;
	private CommerceWishList _commerceWishList;
	private final CommerceWishListHttpHelper _commerceWishListHttpHelper;
	private final CommerceWishListItemService _commerceWishListItemService;
	private SearchContainer<CommerceWishListItem>
		_commerceWishListItemsSearchContainer;
	private final CommerceWishListRequestHelper _commerceWishListRequestHelper;
	private final CommerceWishListService _commerceWishListService;
	private final CPDefinitionHelper _cpDefinitionHelper;
	private final CPInstanceHelper _cpInstanceHelper;
	private final PortalPreferences _portalPreferences;
	private SearchContainer<CommerceWishList> _searchContainer;

}