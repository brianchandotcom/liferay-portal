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

package com.liferay.headless.commerce.admin.order.internal.graphql.query.v1_0;

import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderNote;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.order.resource.v1_0.BillingAddressResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderItemResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderNoteResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.ShippingAddressResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class Query {

	public static void setBillingAddressResourceComponentServiceObjects(
		ComponentServiceObjects<BillingAddressResource>
			billingAddressResourceComponentServiceObjects) {

		_billingAddressResourceComponentServiceObjects =
			billingAddressResourceComponentServiceObjects;
	}

	public static void setOrderResourceComponentServiceObjects(
		ComponentServiceObjects<OrderResource>
			orderResourceComponentServiceObjects) {

		_orderResourceComponentServiceObjects =
			orderResourceComponentServiceObjects;
	}

	public static void setOrderItemResourceComponentServiceObjects(
		ComponentServiceObjects<OrderItemResource>
			orderItemResourceComponentServiceObjects) {

		_orderItemResourceComponentServiceObjects =
			orderItemResourceComponentServiceObjects;
	}

	public static void setOrderNoteResourceComponentServiceObjects(
		ComponentServiceObjects<OrderNoteResource>
			orderNoteResourceComponentServiceObjects) {

		_orderNoteResourceComponentServiceObjects =
			orderNoteResourceComponentServiceObjects;
	}

	public static void setShippingAddressResourceComponentServiceObjects(
		ComponentServiceObjects<ShippingAddressResource>
			shippingAddressResourceComponentServiceObjects) {

		_shippingAddressResourceComponentServiceObjects =
			shippingAddressResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderByExternalReferenceCodeBillingAddress(externalReferenceCode: ___){city, countryISOCode, description, externalReferenceCode, id, latitude, longitude, name, phoneNumber, regionISOCode, street1, street2, street3, vatNumber, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public BillingAddress orderByExternalReferenceCodeBillingAddress(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_billingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			billingAddressResource ->
				billingAddressResource.
					getOrderByExternalReferenceCodeBillingAddress(
						externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderIdBillingAddress(id: ___){city, countryISOCode, description, externalReferenceCode, id, latitude, longitude, name, phoneNumber, regionISOCode, street1, street2, street3, vatNumber, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public BillingAddress orderIdBillingAddress(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_billingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			billingAddressResource ->
				billingAddressResource.getOrderIdBillingAddress(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orders(filter: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderPage orders(
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> new OrderPage(
				orderResource.getOrdersPage(
					_filterBiFunction.apply(orderResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(orderResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderByExternalReferenceCode(externalReferenceCode: ___){accountExternalReferenceCode, accountId, advanceStatus, billingAddress, billingAddressId, channelId, couponCode, createDate, currencyCode, customFields, externalReferenceCode, id, lastPriceUpdateDate, modifiedDate, orderDate, orderItems, orderStatus, paymentMethod, paymentStatus, printedNote, purchaseOrderNumber, requestedDeliveryDate, shippingAddress, shippingAddressId, shippingAmount, shippingAmountFormatted, shippingAmountValue, shippingDiscountAmount, shippingDiscountAmountFormatted, shippingDiscountPercentageLevel1, shippingDiscountPercentageLevel2, shippingDiscountPercentageLevel3, shippingDiscountPercentageLevel4, shippingMethod, shippingOption, subtotal, subtotalAmount, subtotalDiscountAmount, subtotalDiscountAmountFormatted, subtotalDiscountPercentageLevel1, subtotalDiscountPercentageLevel2, subtotalDiscountPercentageLevel3, subtotalDiscountPercentageLevel4, subtotalFormatted, taxAmount, taxAmountFormatted, total, totalAmount, totalDiscountAmount, totalDiscountAmountFormatted, totalDiscountPercentageLevel1, totalDiscountPercentageLevel2, totalDiscountPercentageLevel3, totalDiscountPercentageLevel4, totalFormatted, transactionId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Order orderByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.getOrderByExternalReferenceCode(
				externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {order(id: ___){accountExternalReferenceCode, accountId, advanceStatus, billingAddress, billingAddressId, channelId, couponCode, createDate, currencyCode, customFields, externalReferenceCode, id, lastPriceUpdateDate, modifiedDate, orderDate, orderItems, orderStatus, paymentMethod, paymentStatus, printedNote, purchaseOrderNumber, requestedDeliveryDate, shippingAddress, shippingAddressId, shippingAmount, shippingAmountFormatted, shippingAmountValue, shippingDiscountAmount, shippingDiscountAmountFormatted, shippingDiscountPercentageLevel1, shippingDiscountPercentageLevel2, shippingDiscountPercentageLevel3, shippingDiscountPercentageLevel4, shippingMethod, shippingOption, subtotal, subtotalAmount, subtotalDiscountAmount, subtotalDiscountAmountFormatted, subtotalDiscountPercentageLevel1, subtotalDiscountPercentageLevel2, subtotalDiscountPercentageLevel3, subtotalDiscountPercentageLevel4, subtotalFormatted, taxAmount, taxAmountFormatted, total, totalAmount, totalDiscountAmount, totalDiscountAmountFormatted, totalDiscountPercentageLevel1, totalDiscountPercentageLevel2, totalDiscountPercentageLevel3, totalDiscountPercentageLevel4, totalFormatted, transactionId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Order order(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.getOrder(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderItemByExternalReferenceCode(externalReferenceCode: ___){bookedQuantityId, customFields, deliveryGroup, discountAmount, discountPercentageLevel1, discountPercentageLevel2, discountPercentageLevel3, discountPercentageLevel4, externalReferenceCode, finalPrice, id, name, orderExternalReferenceCode, orderId, printedNote, promoPrice, quantity, requestedDeliveryDate, shippedQuantity, shippingAddress, shippingAddressId, sku, skuExternalReferenceCode, skuId, subscription, unitPrice}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderItem orderItemByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource ->
				orderItemResource.getOrderItemByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderItem(id: ___){bookedQuantityId, customFields, deliveryGroup, discountAmount, discountPercentageLevel1, discountPercentageLevel2, discountPercentageLevel3, discountPercentageLevel4, externalReferenceCode, finalPrice, id, name, orderExternalReferenceCode, orderId, printedNote, promoPrice, quantity, requestedDeliveryDate, shippedQuantity, shippingAddress, shippingAddressId, sku, skuExternalReferenceCode, skuId, subscription, unitPrice}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderItem orderItem(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.getOrderItem(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderByExternalReferenceCodeOrderItems(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderItemPage orderByExternalReferenceCodeOrderItems(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> new OrderItemPage(
				orderItemResource.getOrderByExternalReferenceCodeOrderItemsPage(
					externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderIdOrderItems(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderItemPage orderIdOrderItems(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> new OrderItemPage(
				orderItemResource.getOrderIdOrderItemsPage(
					id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderNoteByExternalReferenceCode(externalReferenceCode: ___){author, content, externalReferenceCode, id, orderExternalReferenceCode, orderId, restricted}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderNote orderNoteByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource ->
				orderNoteResource.getOrderNoteByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderNote(id: ___){author, content, externalReferenceCode, id, orderExternalReferenceCode, orderId, restricted}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderNote orderNote(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.getOrderNote(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderByExternalReferenceCodeOrderNotes(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderNotePage orderByExternalReferenceCodeOrderNotes(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> new OrderNotePage(
				orderNoteResource.getOrderByExternalReferenceCodeOrderNotesPage(
					externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderIdOrderNotes(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderNotePage orderIdOrderNotes(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> new OrderNotePage(
				orderNoteResource.getOrderIdOrderNotesPage(
					id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderByExternalReferenceCodeShippingAddress(externalReferenceCode: ___){city, countryISOCode, description, externalReferenceCode, id, latitude, longitude, name, phoneNumber, regionISOCode, street1, street2, street3, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ShippingAddress orderByExternalReferenceCodeShippingAddress(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingAddressResource ->
				shippingAddressResource.
					getOrderByExternalReferenceCodeShippingAddress(
						externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderIdShippingAddress(id: ___){city, countryISOCode, description, externalReferenceCode, id, latitude, longitude, name, phoneNumber, regionISOCode, street1, street2, street3, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ShippingAddress orderIdShippingAddress(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingAddressResource ->
				shippingAddressResource.getOrderIdShippingAddress(id));
	}

	@GraphQLTypeExtension(Order.class)
	public class GetOrderItemByExternalReferenceCodeTypeExtension {

		public GetOrderItemByExternalReferenceCodeTypeExtension(Order order) {
			_order = order;
		}

		@GraphQLField
		public OrderItem itemByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_orderItemResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderItemResource ->
					orderItemResource.getOrderItemByExternalReferenceCode(
						_order.getExternalReferenceCode()));
		}

		private Order _order;

	}

	@GraphQLTypeExtension(OrderItem.class)
	public class GetOrderByExternalReferenceCodeTypeExtension {

		public GetOrderByExternalReferenceCodeTypeExtension(
			OrderItem orderItem) {

			_orderItem = orderItem;
		}

		@GraphQLField
		public Order orderByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_orderResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderResource -> orderResource.getOrderByExternalReferenceCode(
					_orderItem.getExternalReferenceCode()));
		}

		private OrderItem _orderItem;

	}

	@GraphQLTypeExtension(Order.class)
	public class GetOrderNoteByExternalReferenceCodeTypeExtension {

		public GetOrderNoteByExternalReferenceCodeTypeExtension(Order order) {
			_order = order;
		}

		@GraphQLField
		public OrderNote noteByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_orderNoteResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderNoteResource ->
					orderNoteResource.getOrderNoteByExternalReferenceCode(
						_order.getExternalReferenceCode()));
		}

		private Order _order;

	}

	@GraphQLTypeExtension(Order.class)
	public class GetOrderByExternalReferenceCodeBillingAddressTypeExtension {

		public GetOrderByExternalReferenceCodeBillingAddressTypeExtension(
			Order order) {

			_order = order;
		}

		@GraphQLField
		public BillingAddress byExternalReferenceCodeBillingAddress()
			throws Exception {

			return _applyComponentServiceObjects(
				_billingAddressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				billingAddressResource ->
					billingAddressResource.
						getOrderByExternalReferenceCodeBillingAddress(
							_order.getExternalReferenceCode()));
		}

		private Order _order;

	}

	@GraphQLTypeExtension(Order.class)
	public class GetOrderByExternalReferenceCodeOrderNotesPageTypeExtension {

		public GetOrderByExternalReferenceCodeOrderNotesPageTypeExtension(
			Order order) {

			_order = order;
		}

		@GraphQLField
		public OrderNotePage byExternalReferenceCodeOrderNotes(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_orderNoteResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderNoteResource -> new OrderNotePage(
					orderNoteResource.
						getOrderByExternalReferenceCodeOrderNotesPage(
							_order.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Order _order;

	}

	@GraphQLTypeExtension(Order.class)
	public class GetOrderByExternalReferenceCodeShippingAddressTypeExtension {

		public GetOrderByExternalReferenceCodeShippingAddressTypeExtension(
			Order order) {

			_order = order;
		}

		@GraphQLField
		public ShippingAddress byExternalReferenceCodeShippingAddress()
			throws Exception {

			return _applyComponentServiceObjects(
				_shippingAddressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				shippingAddressResource ->
					shippingAddressResource.
						getOrderByExternalReferenceCodeShippingAddress(
							_order.getExternalReferenceCode()));
		}

		private Order _order;

	}

	@GraphQLTypeExtension(Order.class)
	public class GetOrderByExternalReferenceCodeOrderItemsPageTypeExtension {

		public GetOrderByExternalReferenceCodeOrderItemsPageTypeExtension(
			Order order) {

			_order = order;
		}

		@GraphQLField
		public OrderItemPage byExternalReferenceCodeOrderItems(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_orderItemResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderItemResource -> new OrderItemPage(
					orderItemResource.
						getOrderByExternalReferenceCodeOrderItemsPage(
							_order.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Order _order;

	}

	@GraphQLName("BillingAddressPage")
	public class BillingAddressPage {

		public BillingAddressPage(Page billingAddressPage) {
			actions = billingAddressPage.getActions();
			items = billingAddressPage.getItems();
			lastPage = billingAddressPage.getLastPage();
			page = billingAddressPage.getPage();
			pageSize = billingAddressPage.getPageSize();
			totalCount = billingAddressPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<BillingAddress> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrderPage")
	public class OrderPage {

		public OrderPage(Page orderPage) {
			actions = orderPage.getActions();
			items = orderPage.getItems();
			lastPage = orderPage.getLastPage();
			page = orderPage.getPage();
			pageSize = orderPage.getPageSize();
			totalCount = orderPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Order> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrderItemPage")
	public class OrderItemPage {

		public OrderItemPage(Page orderItemPage) {
			actions = orderItemPage.getActions();
			items = orderItemPage.getItems();
			lastPage = orderItemPage.getLastPage();
			page = orderItemPage.getPage();
			pageSize = orderItemPage.getPageSize();
			totalCount = orderItemPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OrderItem> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrderNotePage")
	public class OrderNotePage {

		public OrderNotePage(Page orderNotePage) {
			actions = orderNotePage.getActions();
			items = orderNotePage.getItems();
			lastPage = orderNotePage.getLastPage();
			page = orderNotePage.getPage();
			pageSize = orderNotePage.getPageSize();
			totalCount = orderNotePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OrderNote> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ShippingAddressPage")
	public class ShippingAddressPage {

		public ShippingAddressPage(Page shippingAddressPage) {
			actions = shippingAddressPage.getActions();
			items = shippingAddressPage.getItems();
			lastPage = shippingAddressPage.getLastPage();
			page = shippingAddressPage.getPage();
			pageSize = shippingAddressPage.getPageSize();
			totalCount = shippingAddressPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ShippingAddress> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			BillingAddressResource billingAddressResource)
		throws Exception {

		billingAddressResource.setContextAcceptLanguage(_acceptLanguage);
		billingAddressResource.setContextCompany(_company);
		billingAddressResource.setContextHttpServletRequest(
			_httpServletRequest);
		billingAddressResource.setContextHttpServletResponse(
			_httpServletResponse);
		billingAddressResource.setContextUriInfo(_uriInfo);
		billingAddressResource.setContextUser(_user);
	}

	private void _populateResourceContext(OrderResource orderResource)
		throws Exception {

		orderResource.setContextAcceptLanguage(_acceptLanguage);
		orderResource.setContextCompany(_company);
		orderResource.setContextHttpServletRequest(_httpServletRequest);
		orderResource.setContextHttpServletResponse(_httpServletResponse);
		orderResource.setContextUriInfo(_uriInfo);
		orderResource.setContextUser(_user);
	}

	private void _populateResourceContext(OrderItemResource orderItemResource)
		throws Exception {

		orderItemResource.setContextAcceptLanguage(_acceptLanguage);
		orderItemResource.setContextCompany(_company);
		orderItemResource.setContextHttpServletRequest(_httpServletRequest);
		orderItemResource.setContextHttpServletResponse(_httpServletResponse);
		orderItemResource.setContextUriInfo(_uriInfo);
		orderItemResource.setContextUser(_user);
	}

	private void _populateResourceContext(OrderNoteResource orderNoteResource)
		throws Exception {

		orderNoteResource.setContextAcceptLanguage(_acceptLanguage);
		orderNoteResource.setContextCompany(_company);
		orderNoteResource.setContextHttpServletRequest(_httpServletRequest);
		orderNoteResource.setContextHttpServletResponse(_httpServletResponse);
		orderNoteResource.setContextUriInfo(_uriInfo);
		orderNoteResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			ShippingAddressResource shippingAddressResource)
		throws Exception {

		shippingAddressResource.setContextAcceptLanguage(_acceptLanguage);
		shippingAddressResource.setContextCompany(_company);
		shippingAddressResource.setContextHttpServletRequest(
			_httpServletRequest);
		shippingAddressResource.setContextHttpServletResponse(
			_httpServletResponse);
		shippingAddressResource.setContextUriInfo(_uriInfo);
		shippingAddressResource.setContextUser(_user);
	}

	private static ComponentServiceObjects<BillingAddressResource>
		_billingAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderResource>
		_orderResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderItemResource>
		_orderItemResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderNoteResource>
		_orderNoteResourceComponentServiceObjects;
	private static ComponentServiceObjects<ShippingAddressResource>
		_shippingAddressResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private com.liferay.portal.kernel.model.Company _company;
	private com.liferay.portal.kernel.model.User _user;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;

}