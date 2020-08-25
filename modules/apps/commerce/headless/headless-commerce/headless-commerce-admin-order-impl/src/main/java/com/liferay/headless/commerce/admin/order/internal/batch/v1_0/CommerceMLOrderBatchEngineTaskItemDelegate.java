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

package com.liferay.headless.commerce.admin.order.internal.batch.v1_0;

import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.constants.v1_0.OrderBatchEngineTaskItemDelegateConstants;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.internal.helper.v1_0.OrderHelper;
import com.liferay.headless.commerce.admin.order.internal.helper.v1_0.OrderItemHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	immediate = true,
	property = "batch.engine.task.item.delegate.name=" + OrderBatchEngineTaskItemDelegateConstants.COMMERCE_ML_ORDER,
	service = BatchEngineTaskItemDelegate.class
)
public class CommerceMLOrderBatchEngineTaskItemDelegate
	extends BaseBatchEngineTaskItemDelegate<Order> {

	@Override
	public Class<Order> getItemClass() {
		return Order.class;
	}

	@Override
	public Page<Order> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		com.liferay.portal.vulcan.pagination.Pagination vulcanPagination =
			com.liferay.portal.vulcan.pagination.Pagination.of(
				pagination.getPage(), pagination.getPageSize());

		com.liferay.portal.vulcan.pagination.Page<Order> ordersPage =
			_orderHelper.getOrdersPage(
				contextCompany.getCompanyId(), filter, vulcanPagination, search,
				sorts,
				document -> _toOrder(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))),
				false);

		return Page.of(
			ordersPage.getItems(),
			Pagination.of(
				(int)ordersPage.getPage(), (int)ordersPage.getPageSize()),
			ordersPage.getTotalCount());
	}

	private Order _toOrder(long commerceOrderId) throws Exception {
		Order order = _orderHelper.toOrder(
			commerceOrderId, contextUser.getLocale());

		com.liferay.portal.vulcan.pagination.Pagination fullPagination =
			com.liferay.portal.vulcan.pagination.Pagination.of(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		com.liferay.portal.vulcan.pagination.Page<OrderItem> orderItemsPage =
			_orderItemHelper.getOrderItemsPage(
				order.getId(), contextUser.getLocale(), fullPagination);

		Collection<OrderItem> items = orderItemsPage.getItems();

		order.setOrderItems(items.toArray(new OrderItem[0]));

		return order;
	}

	@Reference
	private OrderHelper _orderHelper;

	@Reference
	private OrderItemHelper _orderItemHelper;

}