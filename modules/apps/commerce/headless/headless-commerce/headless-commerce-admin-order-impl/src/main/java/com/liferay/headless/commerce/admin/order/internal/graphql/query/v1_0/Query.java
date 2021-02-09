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

import com.liferay.headless.commerce.admin.order.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderNote;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.order.resource.v1_0.AccountResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.BillingAddressResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderItemResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderNoteResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.ShippingAddressResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
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

	public static void setAccountResourceComponentServiceObjects(
		ComponentServiceObjects<AccountResource>
			accountResourceComponentServiceObjects) {

		_accountResourceComponentServiceObjects =
			accountResourceComponentServiceObjects;
	}

	public static void setBillingAddressResourceComponentServiceObjects(
		ComponentServiceObjects<BillingAddressResource>
			billingAddressResourceComponentServiceObjects) {

		_billingAddressResourceComponentServiceObjects =
			billingAddressResourceComponentServiceObjects;
	}

	public static void setChannelResourceComponentServiceObjects(
		ComponentServiceObjects<ChannelResource>
			channelResourceComponentServiceObjects) {

		_channelResourceComponentServiceObjects =
			channelResourceComponentServiceObjects;
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderByExternalReferenceCodeAccount(externalReferenceCode: ___){customFields, emailAddress, externalReferenceCode, id, logoId, name, root, taxId, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Account orderByExternalReferenceCodeAccount(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource ->
				accountResource.getOrderByExternalReferenceCodeAccount(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderIdAccount(id: ___){customFields, emailAddress, externalReferenceCode, id, logoId, name, root, taxId, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Account orderIdAccount(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.getOrderIdAccount(id));
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderByExternalReferenceCodeChannel(externalReferenceCode: ___){currencyCode, externalReferenceCode, id, name, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Channel orderByExternalReferenceCodeChannel(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource ->
				channelResource.getOrderByExternalReferenceCodeChannel(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderIdChannel(id: ___){currencyCode, externalReferenceCode, id, name, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Channel orderIdChannel(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.getOrderIdChannel(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orders(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderPage orders(
			@GraphQLName("search") String search,
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
					search,
					_filterBiFunction.apply(orderResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(orderResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderByExternalReferenceCode(externalReferenceCode: ___){account, accountExternalReferenceCode, accountId, actions, advanceStatus, billingAddress, billingAddressId, channel, channelExternalReferenceCode, channelId, couponCode, createDate, currencyCode, customFields, externalReferenceCode, id, lastPriceUpdateDate, modifiedDate, orderDate, orderItems, orderStatus, orderStatusInfo, paymentMethod, paymentStatus, paymentStatusInfo, printedNote, purchaseOrderNumber, requestedDeliveryDate, shippingAddress, shippingAddressId, shippingAmount, shippingAmountFormatted, shippingAmountValue, shippingDiscountAmount, shippingDiscountAmountFormatted, shippingDiscountPercentageLevel1, shippingDiscountPercentageLevel1WithTaxAmount, shippingDiscountPercentageLevel2, shippingDiscountPercentageLevel2WithTaxAmount, shippingDiscountPercentageLevel3, shippingDiscountPercentageLevel3WithTaxAmount, shippingDiscountPercentageLevel4, shippingDiscountPercentageLevel4WithTaxAmount, shippingDiscountWithTaxAmount, shippingDiscountWithTaxAmountFormatted, shippingMethod, shippingOption, shippingWithTaxAmount, shippingWithTaxAmountFormatted, shippingWithTaxAmountValue, subtotal, subtotalAmount, subtotalDiscountAmount, subtotalDiscountAmountFormatted, subtotalDiscountPercentageLevel1, subtotalDiscountPercentageLevel1WithTaxAmount, subtotalDiscountPercentageLevel2, subtotalDiscountPercentageLevel2WithTaxAmount, subtotalDiscountPercentageLevel3, subtotalDiscountPercentageLevel3WithTaxAmount, subtotalDiscountPercentageLevel4, subtotalDiscountPercentageLevel4WithTaxAmount, subtotalDiscountWithTaxAmount, subtotalDiscountWithTaxAmountFormatted, subtotalFormatted, subtotalWithTaxAmount, subtotalWithTaxAmountFormatted, subtotalWithTaxAmountValue, taxAmount, taxAmountFormatted, total, totalAmount, totalDiscountAmount, totalDiscountAmountFormatted, totalDiscountPercentageLevel1, totalDiscountPercentageLevel1WithTaxAmount, totalDiscountPercentageLevel2, totalDiscountPercentageLevel2WithTaxAmount, totalDiscountPercentageLevel3, totalDiscountPercentageLevel3WithTaxAmount, totalDiscountPercentageLevel4, totalDiscountPercentageLevel4WithTaxAmount, totalDiscountWithTaxAmount, totalDiscountWithTaxAmountFormatted, totalFormatted, totalWithTaxAmount, totalWithTaxAmountFormatted, totalWithTaxAmountValue, transactionId, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {order(id: ___){account, accountExternalReferenceCode, accountId, actions, advanceStatus, billingAddress, billingAddressId, channel, channelExternalReferenceCode, channelId, couponCode, createDate, currencyCode, customFields, externalReferenceCode, id, lastPriceUpdateDate, modifiedDate, orderDate, orderItems, orderStatus, orderStatusInfo, paymentMethod, paymentStatus, paymentStatusInfo, printedNote, purchaseOrderNumber, requestedDeliveryDate, shippingAddress, shippingAddressId, shippingAmount, shippingAmountFormatted, shippingAmountValue, shippingDiscountAmount, shippingDiscountAmountFormatted, shippingDiscountPercentageLevel1, shippingDiscountPercentageLevel1WithTaxAmount, shippingDiscountPercentageLevel2, shippingDiscountPercentageLevel2WithTaxAmount, shippingDiscountPercentageLevel3, shippingDiscountPercentageLevel3WithTaxAmount, shippingDiscountPercentageLevel4, shippingDiscountPercentageLevel4WithTaxAmount, shippingDiscountWithTaxAmount, shippingDiscountWithTaxAmountFormatted, shippingMethod, shippingOption, shippingWithTaxAmount, shippingWithTaxAmountFormatted, shippingWithTaxAmountValue, subtotal, subtotalAmount, subtotalDiscountAmount, subtotalDiscountAmountFormatted, subtotalDiscountPercentageLevel1, subtotalDiscountPercentageLevel1WithTaxAmount, subtotalDiscountPercentageLevel2, subtotalDiscountPercentageLevel2WithTaxAmount, subtotalDiscountPercentageLevel3, subtotalDiscountPercentageLevel3WithTaxAmount, subtotalDiscountPercentageLevel4, subtotalDiscountPercentageLevel4WithTaxAmount, subtotalDiscountWithTaxAmount, subtotalDiscountWithTaxAmountFormatted, subtotalFormatted, subtotalWithTaxAmount, subtotalWithTaxAmountFormatted, subtotalWithTaxAmountValue, taxAmount, taxAmountFormatted, total, totalAmount, totalDiscountAmount, totalDiscountAmountFormatted, totalDiscountPercentageLevel1, totalDiscountPercentageLevel1WithTaxAmount, totalDiscountPercentageLevel2, totalDiscountPercentageLevel2WithTaxAmount, totalDiscountPercentageLevel3, totalDiscountPercentageLevel3WithTaxAmount, totalDiscountPercentageLevel4, totalDiscountPercentageLevel4WithTaxAmount, totalDiscountWithTaxAmount, totalDiscountWithTaxAmountFormatted, totalFormatted, totalWithTaxAmount, totalWithTaxAmountFormatted, totalWithTaxAmountValue, transactionId, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderItemByExternalReferenceCode(externalReferenceCode: ___){bookedQuantityId, customFields, deliveryGroup, discountAmount, discountPercentageLevel1, discountPercentageLevel1WithTaxAmount, discountPercentageLevel2, discountPercentageLevel2WithTaxAmount, discountPercentageLevel3, discountPercentageLevel3WithTaxAmount, discountPercentageLevel4, discountPercentageLevel4WithTaxAmount, discountWithTaxAmount, externalReferenceCode, finalPrice, finalPriceWithTaxAmount, id, name, orderExternalReferenceCode, orderId, printedNote, promoPrice, promoPriceWithTaxAmount, quantity, requestedDeliveryDate, shippedQuantity, shippingAddress, shippingAddressId, sku, skuExternalReferenceCode, skuId, subscription, unitPrice, unitPriceWithTaxAmount}}"}' -u 'test@liferay.com:test'
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderItem(id: ___){bookedQuantityId, customFields, deliveryGroup, discountAmount, discountPercentageLevel1, discountPercentageLevel1WithTaxAmount, discountPercentageLevel2, discountPercentageLevel2WithTaxAmount, discountPercentageLevel3, discountPercentageLevel3WithTaxAmount, discountPercentageLevel4, discountPercentageLevel4WithTaxAmount, discountWithTaxAmount, externalReferenceCode, finalPrice, finalPriceWithTaxAmount, id, name, orderExternalReferenceCode, orderId, printedNote, promoPrice, promoPriceWithTaxAmount, quantity, requestedDeliveryDate, shippedQuantity, shippingAddress, shippingAddressId, sku, skuExternalReferenceCode, skuId, subscription, unitPrice, unitPriceWithTaxAmount}}"}' -u 'test@liferay.com:test'
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderItemShippingAddress(id: ___){city, countryISOCode, description, externalReferenceCode, id, latitude, longitude, name, phoneNumber, regionISOCode, street1, street2, street3, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ShippingAddress orderItemShippingAddress(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingAddressResource ->
				shippingAddressResource.getOrderItemShippingAddress(id));
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

	@GraphQLTypeExtension(Order.class)
	public class GetOrderByExternalReferenceCodeChannelTypeExtension {

		public GetOrderByExternalReferenceCodeChannelTypeExtension(
			Order order) {

			_order = order;
		}

		@GraphQLField
		public Channel byExternalReferenceCodeChannel() throws Exception {
			return _applyComponentServiceObjects(
				_channelResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				channelResource ->
					channelResource.getOrderByExternalReferenceCodeChannel(
						_order.getExternalReferenceCode()));
		}

		private Order _order;

	}

	@GraphQLTypeExtension(Order.class)
	public class GetOrderByExternalReferenceCodeAccountTypeExtension {

		public GetOrderByExternalReferenceCodeAccountTypeExtension(
			Order order) {

			_order = order;
		}

		@GraphQLField
		public Account byExternalReferenceCodeAccount() throws Exception {
			return _applyComponentServiceObjects(
				_accountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountResource ->
					accountResource.getOrderByExternalReferenceCodeAccount(
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

	@GraphQLName("AccountPage")
	public class AccountPage {

		public AccountPage(Page accountPage) {
			actions = accountPage.getActions();

			items = accountPage.getItems();
			lastPage = accountPage.getLastPage();
			page = accountPage.getPage();
			pageSize = accountPage.getPageSize();
			totalCount = accountPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Account> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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

	@GraphQLName("ChannelPage")
	public class ChannelPage {

		public ChannelPage(Page channelPage) {
			actions = channelPage.getActions();

			items = channelPage.getItems();
			lastPage = channelPage.getLastPage();
			page = channelPage.getPage();
			pageSize = channelPage.getPageSize();
			totalCount = channelPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Channel> items;

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

	private void _populateResourceContext(AccountResource accountResource)
		throws Exception {

		accountResource.setContextAcceptLanguage(_acceptLanguage);
		accountResource.setContextCompany(_company);
		accountResource.setContextHttpServletRequest(_httpServletRequest);
		accountResource.setContextHttpServletResponse(_httpServletResponse);
		accountResource.setContextUriInfo(_uriInfo);
		accountResource.setContextUser(_user);
		accountResource.setGroupLocalService(_groupLocalService);
		accountResource.setRoleLocalService(_roleLocalService);
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
		billingAddressResource.setGroupLocalService(_groupLocalService);
		billingAddressResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(ChannelResource channelResource)
		throws Exception {

		channelResource.setContextAcceptLanguage(_acceptLanguage);
		channelResource.setContextCompany(_company);
		channelResource.setContextHttpServletRequest(_httpServletRequest);
		channelResource.setContextHttpServletResponse(_httpServletResponse);
		channelResource.setContextUriInfo(_uriInfo);
		channelResource.setContextUser(_user);
		channelResource.setGroupLocalService(_groupLocalService);
		channelResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(OrderResource orderResource)
		throws Exception {

		orderResource.setContextAcceptLanguage(_acceptLanguage);
		orderResource.setContextCompany(_company);
		orderResource.setContextHttpServletRequest(_httpServletRequest);
		orderResource.setContextHttpServletResponse(_httpServletResponse);
		orderResource.setContextUriInfo(_uriInfo);
		orderResource.setContextUser(_user);
		orderResource.setGroupLocalService(_groupLocalService);
		orderResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(OrderItemResource orderItemResource)
		throws Exception {

		orderItemResource.setContextAcceptLanguage(_acceptLanguage);
		orderItemResource.setContextCompany(_company);
		orderItemResource.setContextHttpServletRequest(_httpServletRequest);
		orderItemResource.setContextHttpServletResponse(_httpServletResponse);
		orderItemResource.setContextUriInfo(_uriInfo);
		orderItemResource.setContextUser(_user);
		orderItemResource.setGroupLocalService(_groupLocalService);
		orderItemResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(OrderNoteResource orderNoteResource)
		throws Exception {

		orderNoteResource.setContextAcceptLanguage(_acceptLanguage);
		orderNoteResource.setContextCompany(_company);
		orderNoteResource.setContextHttpServletRequest(_httpServletRequest);
		orderNoteResource.setContextHttpServletResponse(_httpServletResponse);
		orderNoteResource.setContextUriInfo(_uriInfo);
		orderNoteResource.setContextUser(_user);
		orderNoteResource.setGroupLocalService(_groupLocalService);
		orderNoteResource.setRoleLocalService(_roleLocalService);
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
		shippingAddressResource.setGroupLocalService(_groupLocalService);
		shippingAddressResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;
	private static ComponentServiceObjects<BillingAddressResource>
		_billingAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderResource>
		_orderResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderItemResource>
		_orderItemResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderNoteResource>
		_orderNoteResourceComponentServiceObjects;
	private static ComponentServiceObjects<ShippingAddressResource>
		_shippingAddressResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}