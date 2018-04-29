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

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.order.web.internal.display.context.util.CommerceOrderRequestHelper;
import com.liferay.commerce.order.web.internal.search.CommerceOrderItemSearch;
import com.liferay.commerce.order.web.internal.search.CommerceOrderItemSearchTerms;
import com.liferay.commerce.order.web.internal.servlet.taglib.ui.CommerceOrderScreenNavigationConstants;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.product.item.selector.criterion.CPInstanceItemSelectorCriterion;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommercePaymentMethodService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.text.DateFormat;
import java.text.Format;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderEditDisplayContext {

	public CommerceOrderEditDisplayContext(
			CommerceCurrencyService commerceCurrencyService,
			CommerceOrderService commerceOrderService,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderNoteService commerceOrderNoteService,
			CommercePaymentMethodService commercePaymentMethodService,
			CommercePriceFormatter commercePriceFormatter,
			ItemSelector itemSelector, RenderRequest renderRequest)
		throws PortalException {

		_commerceOrderService = commerceOrderService;
		_commerceOrderItemService = commerceOrderItemService;
		_commerceOrderNoteService = commerceOrderNoteService;
		_commercePaymentMethodService = commercePaymentMethodService;
		_commercePriceFormatter = commercePriceFormatter;
		_itemSelector = itemSelector;

		long commerceOrderId = ParamUtil.getLong(
			renderRequest, "commerceOrderId");

		if (commerceOrderId > 0) {
			_commerceOrder = _commerceOrderService.getCommerceOrder(
				commerceOrderId);
		}
		else {
			_commerceOrder = null;
		}

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			renderRequest);

		_commerceCurrency =
			commerceCurrencyService.fetchPrimaryCommerceCurrency(
				_commerceOrderRequestHelper.getScopeGroupId());

		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		_commerceOrderDateFormatDateTime =
			FastDateFormatFactoryUtil.getDateTime(
				DateFormat.SHORT, DateFormat.SHORT, themeDisplay.getLocale(),
				themeDisplay.getTimeZone());
	}

	public int[] getAvailableOrderStatuses() throws PortalException {
		return _commerceOrderService.getAvailableOrderStatuses(
			getCommerceOrderId());
	}

	public String getCommerceCurrencyCode() {
		if (_commerceCurrency != null) {
			return _commerceCurrency.getCode();
		}

		return StringPool.BLANK;
	}

	public CommerceOrder getCommerceOrder() {
		return _commerceOrder;
	}

	public String getCommerceOrderDateTime() {
		if (_commerceOrder == null) {
			return StringPool.BLANK;
		}

		return _commerceOrderDateFormatDateTime.format(
			_commerceOrder.getCreateDate());
	}

	public long getCommerceOrderId() {
		if (_commerceOrder == null) {
			return 0;
		}

		return _commerceOrder.getCommerceOrderId();
	}

	public String getCommerceOrderItemPrice(CommerceOrderItem commerceOrderItem)
		throws PortalException {

		return _commercePriceFormatter.format(
			_commerceOrder.getCommerceCurrency(), commerceOrderItem.getPrice());
	}

	public PortletURL getCommerceOrderItemsPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_commerceOrderRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "editCommerceOrder");
		portletURL.setParameter(
			"commerceOrderId", String.valueOf(getCommerceOrderId()));
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CommerceOrderScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_ORDER_ITEMS);

		return portletURL;
	}

	public SearchContainer<CommerceOrderItem>
		getCommerceOrderItemsSearchContainer() throws PortalException {

		if (_itemSearchContainer != null) {
			return _itemSearchContainer;
		}

		_itemSearchContainer = new CommerceOrderItemSearch(
			_commerceOrderRequestHelper.getLiferayPortletRequest(),
			getCommerceOrderItemsPortletURL());

		_itemSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_commerceOrderRequestHelper.getLiferayPortletResponse()));

		CommerceOrderItemSearchTerms commerceOrderItemSearchTerms =
			(CommerceOrderItemSearchTerms)_itemSearchContainer.getSearchTerms();

		BaseModelSearchResult<CommerceOrderItem> baseModelSearchResult = null;

		Sort sort = SortFactoryUtil.getSort(
			CommerceOrderItem.class, _itemSearchContainer.getOrderByCol(),
			_itemSearchContainer.getOrderByType());

		if (commerceOrderItemSearchTerms.isAdvancedSearch()) {
			baseModelSearchResult = _commerceOrderItemService.search(
				getCommerceOrderId(), commerceOrderItemSearchTerms.getSku(),
				commerceOrderItemSearchTerms.getTitle(),
				commerceOrderItemSearchTerms.isAndOperator(),
				_itemSearchContainer.getStart(), _itemSearchContainer.getEnd(),
				sort);
		}
		else {
			baseModelSearchResult = _commerceOrderItemService.search(
				getCommerceOrderId(),
				commerceOrderItemSearchTerms.getKeywords(),
				_itemSearchContainer.getStart(), _itemSearchContainer.getEnd(),
				sort);
		}

		_itemSearchContainer.setTotal(baseModelSearchResult.getLength());
		_itemSearchContainer.setResults(baseModelSearchResult.getBaseModels());

		return _itemSearchContainer;
	}

	public List<CommerceOrderNote> getCommerceOrderNotes()
		throws PortalException {

		long commerceOrderId = getCommerceOrderId();

		if (commerceOrderId <= 0) {
			return Collections.emptyList();
		}

		return _commerceOrderNoteService.getCommerceOrderNotes(
			commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public String getCommercePaymentMethodLabel(
		CommercePaymentMethod commercePaymentMethod) {

		String label = commercePaymentMethod.getName(
			_commerceOrderRequestHelper.getLocale());

		if (!commercePaymentMethod.isActive()) {
			StringBundler sb = new StringBundler(4);

			sb.append(label);
			sb.append(" (");
			sb.append(
				LanguageUtil.get(
					_commerceOrderRequestHelper.getRequest(), "inactive"));
			sb.append(CharPool.CLOSE_PARENTHESIS);

			label = sb.toString();
		}

		return label;
	}

	public List<CommercePaymentMethod> getCommercePaymentMethods() {
		return _commercePaymentMethodService.getCommercePaymentMethods(
			_commerceOrderRequestHelper.getScopeGroupId());
	}

	public String getItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				_commerceOrderRequestHelper.getRequest());

		CPInstanceItemSelectorCriterion cpInstanceItemSelectorCriterion =
			new CPInstanceItemSelectorCriterion();

		cpInstanceItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "productInstancesSelectItem",
			cpInstanceItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private final CommerceCurrency _commerceCurrency;
	private final CommerceOrder _commerceOrder;
	private final Format _commerceOrderDateFormatDateTime;
	private final CommerceOrderItemService _commerceOrderItemService;
	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final CommerceOrderService _commerceOrderService;
	private final CommercePaymentMethodService _commercePaymentMethodService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private SearchContainer<CommerceOrderItem> _itemSearchContainer;
	private final ItemSelector _itemSelector;

}