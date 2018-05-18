/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.item.selector.web.internal.display.context;

import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderItemItemSelectorViewDisplayContext
	extends BaseCommerceItemSelectorViewDisplayContext<CommerceOrderItem> {

	public CommerceOrderItemItemSelectorViewDisplayContext(
		CommerceOrderItemLocalService commerceOrderItemLocalService,
		CommercePriceFormatter commercePriceFormatter,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		super(httpServletRequest, portletURL, itemSelectedEventName);

		_commerceOrderItemLocalService = commerceOrderItemLocalService;
		_commercePriceFormatter = commercePriceFormatter;

		setDefaultOrderByCol("create-date");
		setDefaultOrderByType("desc");
	}

	public int getCommerceWarehouseItemQuantity(long commerceOrderItemId)
		throws PortalException {

		return _commerceOrderItemLocalService.getCommerceWarehouseItemQuantity(
			commerceOrderItemId, getCommerceWarehouseId());
	}

	public String getFormattedPrice(long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.fetchCommerceOrderItem(
				commerceOrderItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		return _commercePriceFormatter.format(
			commerceOrder.getCommerceCurrency(), commerceOrderItem.getPrice());
	}

	public SearchContainer<CommerceOrderItem> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-order-items-were-found");

		RowChecker rowChecker = new EmptyOnClickRowChecker(
			cpRequestHelper.getRenderResponse());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(rowChecker);

		List<CommerceOrderItem> results =
			_commerceOrderItemLocalService.getCommerceOrderItems(
				getCommerceWarehouseId(), getCommerceAddressId(),
				searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		searchContainer.setTotal(results.size());

		return searchContainer;
	}

	protected long getCommerceAddressId() {
		return ParamUtil.getLong(httpServletRequest, "commerceAddressId");
	}

	protected long getCommerceWarehouseId() {
		return ParamUtil.getLong(httpServletRequest, "commerceWarehouseId");
	}

	private final CommerceOrderItemLocalService _commerceOrderItemLocalService;
	private final CommercePriceFormatter _commercePriceFormatter;

}