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

package com.liferay.commerce.shipment.web.internal.display.context;

import com.liferay.commerce.item.selector.criterion.CommerceOrderItemItemSelectorCriterion;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.service.CommerceWarehouseService;
import com.liferay.commerce.shipment.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.shipment.web.internal.util.CommerceShipmentPortletUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentItemDisplayContext
	extends BaseCommerceShipmentDisplayContext<CommerceShipmentItem> {

	public CommerceShipmentItemDisplayContext(
			ActionHelper actionHelper,
			CommerceShippingMethodLocalService
				commerceShippingMethodLocalService,
			HttpServletRequest httpServletRequest,
			CommerceShipmentItemService commerceShipmentItemService,
			CommerceWarehouseService commerceWarehouseService,
			ItemSelector itemSelector)
		throws PortalException {

		super(
			actionHelper, commerceShippingMethodLocalService,
			httpServletRequest, CommerceShipmentItem.class.getSimpleName());

		_commerceShipmentItemService = commerceShipmentItemService;
		_commerceWarehouseService = commerceWarehouseService;
		_itemSelector = itemSelector;
	}

	public CommerceShipmentItem getCommerceShipmentItem()
		throws PortalException {

		if (_commerceShipmentItem != null) {
			return _commerceShipmentItem;
		}

		_commerceShipmentItem = actionHelper.getCommerceShipmentItem(
			cpRequestHelper.getRenderRequest());

		return _commerceShipmentItem;
	}

	public long getCommerceShipmentItemId() throws PortalException {
		CommerceShipmentItem commerceShipmentItem = getCommerceShipmentItem();

		if (commerceShipmentItem == null) {
			return 0;
		}

		return commerceShipmentItem.getCommerceShipmentItemId();
	}

	public String getCommerceWarehouseName(long commerceWarehouseId)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			_commerceWarehouseService.getCommerceWarehouse(commerceWarehouseId);

		return commerceWarehouse.getName();
	}

	public CPDefinition getCPDefinition(long commerceShipmentItemId)
		throws PortalException {

		CPDefinition cpDefinition = null;

		CPInstance cpInstance = getCPInstance(commerceShipmentItemId);

		if (cpInstance != null) {
			cpDefinition = cpInstance.getCPDefinition();
		}

		return cpDefinition;
	}

	public CPInstance getCPInstance(long commerceShipmentItemId)
		throws PortalException {

		CPInstance cpInstance = null;

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.fetchCommerceShipmentItem(
				commerceShipmentItemId);

		if (commerceShipmentItem != null) {
			CommerceOrderItem commerceOrderItem =
				commerceShipmentItem.fetchCommerceOrderItem();

			if (commerceOrderItem != null) {
				cpInstance = commerceOrderItem.getCPInstance();
			}
		}

		return cpInstance;
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		CommerceOrderItemItemSelectorCriterion
			commerceOrderItemItemSelectorCriterion =
				new CommerceOrderItemItemSelectorCriterion();

		commerceOrderItemItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "orderItemsSelectItem",
			commerceOrderItemItemSelectorCriterion);

		itemSelectorURL.setParameter(
			"commerceWarehouseId", String.valueOf(getCommerceWarehouseId()));
		itemSelectorURL.setParameter(
			"commerceAddressId", String.valueOf(getCommerceAddressId()));

		return itemSelectorURL.toString();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceShipmentDetail");

		return portletURL;
	}

	@Override
	public SearchContainer<CommerceShipmentItem> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceShipmentItem> orderByComparator =
			CommerceShipmentPortletUtil.
				getCommerceShipmentItemOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setEmptyResultsMessage("no-shipment-items-were-found");
		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		int total = _commerceShipmentItemService.getCommerceShipmentItemsCount(
			getCommerceShipmentId());

		searchContainer.setTotal(total);

		List<CommerceShipmentItem> results =
			_commerceShipmentItemService.getCommerceShipmentItems(
				getCommerceShipmentId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	protected long getCommerceAddressId() throws PortalException {
		long commerceAddressId = 0;

		CommerceShipment commerceShipment = getCommerceShipment();

		if (commerceShipment != null) {
			CommerceAddress commerceAddress =
				commerceShipment.fetchCommerceAddress();

			if (commerceAddress != null) {
				commerceAddressId = commerceAddress.getCommerceAddressId();
			}
		}

		return commerceAddressId;
	}

	protected long getCommerceWarehouseId() throws PortalException {
		long commerceWarehouseId = 0;

		CommerceShipment commerceShipment = getCommerceShipment();

		if (commerceShipment != null) {
			CommerceWarehouse commerceWarehouse =
				commerceShipment.fetchCommerceWarehouse();

			if (commerceWarehouse != null) {
				commerceWarehouseId =
					commerceWarehouse.getCommerceWarehouseId();
			}
		}

		return commerceWarehouseId;
	}

	private CommerceShipmentItem _commerceShipmentItem;
	private final CommerceShipmentItemService _commerceShipmentItemService;
	private final CommerceWarehouseService _commerceWarehouseService;
	private final ItemSelector _itemSelector;

}