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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.wish.list.constants.CommerceWishListActionKeys;
import com.liferay.commerce.wish.list.constants.CommerceWishListPortletKeys;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.commerce.wish.list.util.CommerceWishListHttpHelper;
import com.liferay.commerce.wish.list.util.comparator.CommerceWishListNameComparator;
import com.liferay.commerce.wish.list.web.internal.display.context.util.CommerceWishListRequestHelper;
import com.liferay.commerce.wish.list.web.internal.util.CommerceWishListPortletUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
public class CommerceWishListDisplayContext {

	public CommerceWishListDisplayContext(
		CommerceProductPriceCalculation commerceProductPriceCalculation,
		CommerceWishListHttpHelper commerceWishListHttpHelper,
		CommerceWishListItemService commerceWishListItemService,
		CommerceWishListService commerceWishListService,
		CPDefinitionHelper cpDefinitionHelper,
		CPInstanceHelper cpInstanceHelper,
		HttpServletRequest httpServletRequest,
		PortletResourcePermission portletResourcePermission) {

		_commerceProductPriceCalculation = commerceProductPriceCalculation;
		_commerceWishListHttpHelper = commerceWishListHttpHelper;
		_commerceWishListItemService = commerceWishListItemService;
		_commerceWishListService = commerceWishListService;
		_cpDefinitionHelper = cpDefinitionHelper;
		_cpInstanceHelper = cpInstanceHelper;
		_portletResourcePermission = portletResourcePermission;

		_commerceWishListRequestHelper = new CommerceWishListRequestHelper(
			httpServletRequest);
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			httpServletRequest);
	}

	public long getCommerceAccountId() throws PortalException {
		CommerceContext commerceContext =
			_commerceWishListRequestHelper.getCommerceContext();

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		return commerceAccount.getCommerceAccountId();
	}

	public long getCommerceChannelId() throws PortalException {
		CommerceContext commerceContext =
			_commerceWishListRequestHelper.getCommerceContext();

		return commerceContext.getCommerceChannelId();
	}

	public String getCommerceCurrencyCode() throws PortalException {
		CommerceContext commerceContext =
			_commerceWishListRequestHelper.getCommerceContext();

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		return commerceCurrency.getCode();
	}

	public long getCommerceOrderId() throws PortalException {
		CommerceContext commerceContext =
			_commerceWishListRequestHelper.getCommerceContext();

		CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

		if (commerceOrder != null) {
			return commerceOrder.getCommerceOrderId();
		}

		return 0;
	}

	public CommerceWishList getCommerceWishList() throws PortalException {
		if (_commerceWishList != null) {
			return _commerceWishList;
		}

		HttpServletRequest httpServletRequest =
			_commerceWishListRequestHelper.getRequest();

		long commerceWishListId = ParamUtil.getLong(
			httpServletRequest, "commerceWishListId",
			getDefaultCommerceWishListId());

		if (commerceWishListId > 0) {
			try {
				_commerceWishList =
					_commerceWishListService.getCommerceWishList(
						commerceWishListId);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException, portalException);
				}
			}
		}
		else if (isContentPortlet()) {
			_commerceWishList =
				_commerceWishListHttpHelper.getCurrentCommerceWishList(
					httpServletRequest,
					_commerceWishListRequestHelper.
						getResponseHttpServletResponse());
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

		CPDefinition cpDefinition = commerceWishListItem.getCPDefinition();

		List<KeyValuePair> keyValuePairs = _cpInstanceHelper.getKeyValuePairs(
			cpDefinition.getCPDefinitionId(), commerceWishListItem.getJson(),
			_commerceWishListRequestHelper.getLocale());

		StringBundler sb = new StringBundler((keyValuePairs.size() * 2) - 1);

		for (Iterator<KeyValuePair> iterator = keyValuePairs.iterator();
			 iterator.hasNext();) {

			sb.append(iterator.next());

			if (iterator.hasNext()) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}
		}

		return sb.toString();
	}

	public String getCommerceWishListItemPrice(
			CommerceWishListItem commerceWishListItem)
		throws PortalException {

		CPInstance cpInstance = commerceWishListItem.fetchCPInstance();

		if (cpInstance == null) {
			return StringPool.BLANK;
		}

		CommerceMoney commerceMoney =
			_commerceProductPriceCalculation.getFinalPrice(
				cpInstance.getCPInstanceId(), 1,
				_commerceWishListRequestHelper.getCommerceContext());

		if (commerceMoney.isEmpty()) {
			return StringPool.BLANK;
		}

		return commerceMoney.format(_commerceWishListRequestHelper.getLocale());
	}

	public SearchContainer<CommerceWishListItem>
			getCommerceWishListItemsSearchContainer()
		throws PortalException {

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

		CommerceWishList commerceWishList = getCommerceWishList();

		if (commerceWishList == null) {
			return _commerceWishListItemsSearchContainer;
		}

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

	public String getRowURL(long commerceWishListId) {
		LiferayPortletResponse liferayPortletResponse =
			_commerceWishListRequestHelper.getLiferayPortletResponse();

		PortletURL rowURL = liferayPortletResponse.createRenderURL();

		rowURL.setParameter(
			"commerceWishListId", String.valueOf(commerceWishListId));

		return rowURL.toString();
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

		int total = _commerceWishListService.getCommerceWishListsCount(
			_commerceWishListRequestHelper.getScopeGroupId(),
			_commerceWishListRequestHelper.getUserId());

		List<CommerceWishList> results =
			_commerceWishListService.getCommerceWishLists(
				_commerceWishListRequestHelper.getScopeGroupId(),
				_commerceWishListRequestHelper.getUserId(),
				_searchContainer.getStart(), _searchContainer.getEnd(),
				orderByComparator);

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public boolean hasManageCommerceWishListsPermission() {
		return _portletResourcePermission.contains(
			_commerceWishListRequestHelper.getPermissionChecker(),
			_commerceWishListRequestHelper.getScopeGroupId(),
			CommerceWishListActionKeys.MANAGE_COMMERCE_WISH_LISTS);
	}

	protected long getDefaultCommerceWishListId() throws PortalException {
		long defaultCommerceWishListId = 0;

		CommerceWishList commerceWishList =
			_commerceWishListService.fetchCommerceWishList(
				_commerceWishListRequestHelper.getScopeGroupId(),
				_commerceWishListRequestHelper.getUserId(), true,
				new CommerceWishListNameComparator(true));

		if (commerceWishList != null) {
			defaultCommerceWishListId =
				commerceWishList.getCommerceWishListId();
		}

		return defaultCommerceWishListId;
	}

	protected boolean isContentPortlet() {
		if (CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT.equals(
				_commerceWishListRequestHelper.getPortletId())) {

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

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceWishListDisplayContext.class);

	private final CommerceProductPriceCalculation
		_commerceProductPriceCalculation;
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
	private final PortletResourcePermission _portletResourcePermission;
	private SearchContainer<CommerceWishList> _searchContainer;

}