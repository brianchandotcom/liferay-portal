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
import com.liferay.headless.commerce.admin.order.dto.v1_0.AccountGroup;
import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderNote;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRule;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccount;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccountGroup;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleChannel;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleOrderType;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderType;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderTypeChannel;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.order.resource.v1_0.AccountGroupResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.AccountResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.BillingAddressResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderItemResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderNoteResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleAccountGroupResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleAccountResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleChannelResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleOrderTypeResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderTypeChannelResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderTypeResource;
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

	public static void setAccountGroupResourceComponentServiceObjects(
		ComponentServiceObjects<AccountGroupResource>
			accountGroupResourceComponentServiceObjects) {

		_accountGroupResourceComponentServiceObjects =
			accountGroupResourceComponentServiceObjects;
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

	public static void setOrderRuleResourceComponentServiceObjects(
		ComponentServiceObjects<OrderRuleResource>
			orderRuleResourceComponentServiceObjects) {

		_orderRuleResourceComponentServiceObjects =
			orderRuleResourceComponentServiceObjects;
	}

	public static void setOrderRuleAccountResourceComponentServiceObjects(
		ComponentServiceObjects<OrderRuleAccountResource>
			orderRuleAccountResourceComponentServiceObjects) {

		_orderRuleAccountResourceComponentServiceObjects =
			orderRuleAccountResourceComponentServiceObjects;
	}

	public static void setOrderRuleAccountGroupResourceComponentServiceObjects(
		ComponentServiceObjects<OrderRuleAccountGroupResource>
			orderRuleAccountGroupResourceComponentServiceObjects) {

		_orderRuleAccountGroupResourceComponentServiceObjects =
			orderRuleAccountGroupResourceComponentServiceObjects;
	}

	public static void setOrderRuleChannelResourceComponentServiceObjects(
		ComponentServiceObjects<OrderRuleChannelResource>
			orderRuleChannelResourceComponentServiceObjects) {

		_orderRuleChannelResourceComponentServiceObjects =
			orderRuleChannelResourceComponentServiceObjects;
	}

	public static void setOrderRuleOrderTypeResourceComponentServiceObjects(
		ComponentServiceObjects<OrderRuleOrderTypeResource>
			orderRuleOrderTypeResourceComponentServiceObjects) {

		_orderRuleOrderTypeResourceComponentServiceObjects =
			orderRuleOrderTypeResourceComponentServiceObjects;
	}

	public static void setOrderTypeResourceComponentServiceObjects(
		ComponentServiceObjects<OrderTypeResource>
			orderTypeResourceComponentServiceObjects) {

		_orderTypeResourceComponentServiceObjects =
			orderTypeResourceComponentServiceObjects;
	}

	public static void setOrderTypeChannelResourceComponentServiceObjects(
		ComponentServiceObjects<OrderTypeChannelResource>
			orderTypeChannelResourceComponentServiceObjects) {

		_orderTypeChannelResourceComponentServiceObjects =
			orderTypeChannelResourceComponentServiceObjects;
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleAccountAccount(orderRuleAccountId: ___){customFields, emailAddress, externalReferenceCode, id, logoId, name, root, taxId, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Account orderRuleAccountAccount(
			@GraphQLName("orderRuleAccountId") Long orderRuleAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.getOrderRuleAccountAccount(
				orderRuleAccountId));
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleAccountGroupAccountGroup(orderRuleAccountGroupId: ___){id, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountGroup orderRuleAccountGroupAccountGroup(
			@GraphQLName("orderRuleAccountGroupId") Long
				orderRuleAccountGroupId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountGroupResource ->
				accountGroupResource.getOrderRuleAccountGroupAccountGroup(
					orderRuleAccountGroupId));
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleChannelChannel(orderRuleChannelId: ___){currencyCode, externalReferenceCode, id, name, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Channel orderRuleChannelChannel(
			@GraphQLName("orderRuleChannelId") Long orderRuleChannelId)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.getOrderRuleChannelChannel(
				orderRuleChannelId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderTypeChannelChannel(orderTypeChannelId: ___){currencyCode, externalReferenceCode, id, name, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Channel orderTypeChannelChannel(
			@GraphQLName("orderTypeChannelId") Long orderTypeChannelId)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.getOrderTypeChannelChannel(
				orderTypeChannelId));
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderByExternalReferenceCode(externalReferenceCode: ___){account, accountExternalReferenceCode, accountId, actions, advanceStatus, billingAddress, billingAddressId, channel, channelExternalReferenceCode, channelId, couponCode, createDate, currencyCode, customFields, externalReferenceCode, id, lastPriceUpdateDate, modifiedDate, orderDate, orderItems, orderStatus, orderStatusInfo, orderTypeExternalReferenceCode, orderTypeId, paymentMethod, paymentStatus, paymentStatusInfo, printedNote, purchaseOrderNumber, requestedDeliveryDate, shippingAddress, shippingAddressId, shippingAmount, shippingAmountFormatted, shippingAmountValue, shippingDiscountAmount, shippingDiscountAmountFormatted, shippingDiscountPercentageLevel1, shippingDiscountPercentageLevel1WithTaxAmount, shippingDiscountPercentageLevel2, shippingDiscountPercentageLevel2WithTaxAmount, shippingDiscountPercentageLevel3, shippingDiscountPercentageLevel3WithTaxAmount, shippingDiscountPercentageLevel4, shippingDiscountPercentageLevel4WithTaxAmount, shippingDiscountWithTaxAmount, shippingDiscountWithTaxAmountFormatted, shippingMethod, shippingOption, shippingWithTaxAmount, shippingWithTaxAmountFormatted, shippingWithTaxAmountValue, subtotal, subtotalAmount, subtotalDiscountAmount, subtotalDiscountAmountFormatted, subtotalDiscountPercentageLevel1, subtotalDiscountPercentageLevel1WithTaxAmount, subtotalDiscountPercentageLevel2, subtotalDiscountPercentageLevel2WithTaxAmount, subtotalDiscountPercentageLevel3, subtotalDiscountPercentageLevel3WithTaxAmount, subtotalDiscountPercentageLevel4, subtotalDiscountPercentageLevel4WithTaxAmount, subtotalDiscountWithTaxAmount, subtotalDiscountWithTaxAmountFormatted, subtotalFormatted, subtotalWithTaxAmount, subtotalWithTaxAmountFormatted, subtotalWithTaxAmountValue, taxAmount, taxAmountFormatted, taxAmountValue, total, totalAmount, totalDiscountAmount, totalDiscountAmountFormatted, totalDiscountPercentageLevel1, totalDiscountPercentageLevel1WithTaxAmount, totalDiscountPercentageLevel2, totalDiscountPercentageLevel2WithTaxAmount, totalDiscountPercentageLevel3, totalDiscountPercentageLevel3WithTaxAmount, totalDiscountPercentageLevel4, totalDiscountPercentageLevel4WithTaxAmount, totalDiscountWithTaxAmount, totalDiscountWithTaxAmountFormatted, totalFormatted, totalWithTaxAmount, totalWithTaxAmountFormatted, totalWithTaxAmountValue, transactionId, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {order(id: ___){account, accountExternalReferenceCode, accountId, actions, advanceStatus, billingAddress, billingAddressId, channel, channelExternalReferenceCode, channelId, couponCode, createDate, currencyCode, customFields, externalReferenceCode, id, lastPriceUpdateDate, modifiedDate, orderDate, orderItems, orderStatus, orderStatusInfo, orderTypeExternalReferenceCode, orderTypeId, paymentMethod, paymentStatus, paymentStatusInfo, printedNote, purchaseOrderNumber, requestedDeliveryDate, shippingAddress, shippingAddressId, shippingAmount, shippingAmountFormatted, shippingAmountValue, shippingDiscountAmount, shippingDiscountAmountFormatted, shippingDiscountPercentageLevel1, shippingDiscountPercentageLevel1WithTaxAmount, shippingDiscountPercentageLevel2, shippingDiscountPercentageLevel2WithTaxAmount, shippingDiscountPercentageLevel3, shippingDiscountPercentageLevel3WithTaxAmount, shippingDiscountPercentageLevel4, shippingDiscountPercentageLevel4WithTaxAmount, shippingDiscountWithTaxAmount, shippingDiscountWithTaxAmountFormatted, shippingMethod, shippingOption, shippingWithTaxAmount, shippingWithTaxAmountFormatted, shippingWithTaxAmountValue, subtotal, subtotalAmount, subtotalDiscountAmount, subtotalDiscountAmountFormatted, subtotalDiscountPercentageLevel1, subtotalDiscountPercentageLevel1WithTaxAmount, subtotalDiscountPercentageLevel2, subtotalDiscountPercentageLevel2WithTaxAmount, subtotalDiscountPercentageLevel3, subtotalDiscountPercentageLevel3WithTaxAmount, subtotalDiscountPercentageLevel4, subtotalDiscountPercentageLevel4WithTaxAmount, subtotalDiscountWithTaxAmount, subtotalDiscountWithTaxAmountFormatted, subtotalFormatted, subtotalWithTaxAmount, subtotalWithTaxAmountFormatted, subtotalWithTaxAmountValue, taxAmount, taxAmountFormatted, taxAmountValue, total, totalAmount, totalDiscountAmount, totalDiscountAmountFormatted, totalDiscountPercentageLevel1, totalDiscountPercentageLevel1WithTaxAmount, totalDiscountPercentageLevel2, totalDiscountPercentageLevel2WithTaxAmount, totalDiscountPercentageLevel3, totalDiscountPercentageLevel3WithTaxAmount, totalDiscountPercentageLevel4, totalDiscountPercentageLevel4WithTaxAmount, totalDiscountWithTaxAmount, totalDiscountWithTaxAmountFormatted, totalFormatted, totalWithTaxAmount, totalWithTaxAmountFormatted, totalWithTaxAmountValue, transactionId, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRules(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderRulePage orderRules(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> new OrderRulePage(
				orderRuleResource.getOrderRulesPage(
					search,
					_filterBiFunction.apply(orderRuleResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(orderRuleResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleByExternalReferenceCode(externalReferenceCode: ___){actions, active, author, createDate, description, displayDate, expirationDate, externalReferenceCode, id, name, neverExpire, orderRuleAccount, orderRuleAccountGroup, orderRuleChannel, orderRuleOrderType, priority, type, typeSettings, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderRule orderRuleByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource ->
				orderRuleResource.getOrderRuleByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRule(id: ___){actions, active, author, createDate, description, displayDate, expirationDate, externalReferenceCode, id, name, neverExpire, orderRuleAccount, orderRuleAccountGroup, orderRuleChannel, orderRuleOrderType, priority, type, typeSettings, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderRule orderRule(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> orderRuleResource.getOrderRule(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleByExternalReferenceCodeOrderRuleAccounts(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderRuleAccountPage
			orderRuleByExternalReferenceCodeOrderRuleAccounts(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource -> new OrderRuleAccountPage(
				orderRuleAccountResource.
					getOrderRuleByExternalReferenceCodeOrderRuleAccountsPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleIdOrderRuleAccounts(filter: ___, id: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderRuleAccountPage orderRuleIdOrderRuleAccounts(
			@GraphQLName("id") Long id, @GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource -> new OrderRuleAccountPage(
				orderRuleAccountResource.getOrderRuleIdOrderRuleAccountsPage(
					id, search,
					_filterBiFunction.apply(
						orderRuleAccountResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						orderRuleAccountResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleByExternalReferenceCodeOrderRuleAccountGroups(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderRuleAccountGroupPage
			orderRuleByExternalReferenceCodeOrderRuleAccountGroups(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource -> new OrderRuleAccountGroupPage(
				orderRuleAccountGroupResource.
					getOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleIdOrderRuleAccountGroups(filter: ___, id: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderRuleAccountGroupPage orderRuleIdOrderRuleAccountGroups(
			@GraphQLName("id") Long id, @GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource -> new OrderRuleAccountGroupPage(
				orderRuleAccountGroupResource.
					getOrderRuleIdOrderRuleAccountGroupsPage(
						id, search,
						_filterBiFunction.apply(
							orderRuleAccountGroupResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							orderRuleAccountGroupResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleByExternalReferenceCodeOrderRuleChannels(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderRuleChannelPage
			orderRuleByExternalReferenceCodeOrderRuleChannels(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource -> new OrderRuleChannelPage(
				orderRuleChannelResource.
					getOrderRuleByExternalReferenceCodeOrderRuleChannelsPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleIdOrderRuleChannels(filter: ___, id: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderRuleChannelPage orderRuleIdOrderRuleChannels(
			@GraphQLName("id") Long id, @GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource -> new OrderRuleChannelPage(
				orderRuleChannelResource.getOrderRuleIdOrderRuleChannelsPage(
					id, search,
					_filterBiFunction.apply(
						orderRuleChannelResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						orderRuleChannelResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleByExternalReferenceCodeOrderRuleOrderTypes(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderRuleOrderTypePage
			orderRuleByExternalReferenceCodeOrderRuleOrderTypes(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource -> new OrderRuleOrderTypePage(
				orderRuleOrderTypeResource.
					getOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleIdOrderRuleOrderTypes(id: ___, page: ___, pageSize: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderRuleOrderTypePage orderRuleIdOrderRuleOrderTypes(
			@GraphQLName("id") Long id, @GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource -> new OrderRuleOrderTypePage(
				orderRuleOrderTypeResource.
					getOrderRuleIdOrderRuleOrderTypesPage(
						id, search, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderRuleOrderTypeOrderType(orderRuleOrderTypeId: ___){actions, active, customFields, description, displayDate, displayOrder, expirationDate, externalReferenceCode, id, name, neverExpire, orderTypeChannels, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderType orderRuleOrderTypeOrderType(
			@GraphQLName("orderRuleOrderTypeId") Long orderRuleOrderTypeId)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource ->
				orderTypeResource.getOrderRuleOrderTypeOrderType(
					orderRuleOrderTypeId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderTypes(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderTypePage orderTypes(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> new OrderTypePage(
				orderTypeResource.getOrderTypesPage(
					search,
					_filterBiFunction.apply(orderTypeResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(orderTypeResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderTypeByExternalReferenceCode(externalReferenceCode: ___){actions, active, customFields, description, displayDate, displayOrder, expirationDate, externalReferenceCode, id, name, neverExpire, orderTypeChannels, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderType orderTypeByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource ->
				orderTypeResource.getOrderTypeByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderType(id: ___){actions, active, customFields, description, displayDate, displayOrder, expirationDate, externalReferenceCode, id, name, neverExpire, orderTypeChannels, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderType orderType(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> orderTypeResource.getOrderType(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderTypeByExternalReferenceCodeOrderTypeChannels(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderTypeChannelPage
			orderTypeByExternalReferenceCodeOrderTypeChannels(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource -> new OrderTypeChannelPage(
				orderTypeChannelResource.
					getOrderTypeByExternalReferenceCodeOrderTypeChannelsPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {orderTypeIdOrderTypeChannels(id: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderTypeChannelPage orderTypeIdOrderTypeChannels(
			@GraphQLName("id") Long id, @GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource -> new OrderTypeChannelPage(
				orderTypeChannelResource.getOrderTypeIdOrderTypeChannelsPage(
					id, search, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						orderTypeChannelResource, sortsString))));
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
	public class GetOrderRuleByExternalReferenceCodeTypeExtension {

		public GetOrderRuleByExternalReferenceCodeTypeExtension(Order order) {
			_order = order;
		}

		@GraphQLField
		public OrderRule ruleByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_orderRuleResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderRuleResource ->
					orderRuleResource.getOrderRuleByExternalReferenceCode(
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
	public class
		GetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPageTypeExtension {

		public GetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPageTypeExtension(
			Order order) {

			_order = order;
		}

		@GraphQLField
		public OrderRuleAccountGroupPage
				ruleByExternalReferenceCodeOrderRuleAccountGroups(
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_orderRuleAccountGroupResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderRuleAccountGroupResource -> new OrderRuleAccountGroupPage(
					orderRuleAccountGroupResource.
						getOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage(
							_order.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Order _order;

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
	public class GetOrderTypeByExternalReferenceCodeTypeExtension {

		public GetOrderTypeByExternalReferenceCodeTypeExtension(Order order) {
			_order = order;
		}

		@GraphQLField
		public OrderType typeByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_orderTypeResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderTypeResource ->
					orderTypeResource.getOrderTypeByExternalReferenceCode(
						_order.getExternalReferenceCode()));
		}

		private Order _order;

	}

	@GraphQLTypeExtension(Order.class)
	public class
		GetOrderRuleByExternalReferenceCodeOrderRuleChannelsPageTypeExtension {

		public GetOrderRuleByExternalReferenceCodeOrderRuleChannelsPageTypeExtension(
			Order order) {

			_order = order;
		}

		@GraphQLField
		public OrderRuleChannelPage
				ruleByExternalReferenceCodeOrderRuleChannels(
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_orderRuleChannelResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderRuleChannelResource -> new OrderRuleChannelPage(
					orderRuleChannelResource.
						getOrderRuleByExternalReferenceCodeOrderRuleChannelsPage(
							_order.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
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
	public class
		GetOrderRuleByExternalReferenceCodeOrderRuleAccountsPageTypeExtension {

		public GetOrderRuleByExternalReferenceCodeOrderRuleAccountsPageTypeExtension(
			Order order) {

			_order = order;
		}

		@GraphQLField
		public OrderRuleAccountPage
				ruleByExternalReferenceCodeOrderRuleAccounts(
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_orderRuleAccountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderRuleAccountResource -> new OrderRuleAccountPage(
					orderRuleAccountResource.
						getOrderRuleByExternalReferenceCodeOrderRuleAccountsPage(
							_order.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Order _order;

	}

	@GraphQLTypeExtension(Order.class)
	public class
		GetOrderTypeByExternalReferenceCodeOrderTypeChannelsPageTypeExtension {

		public GetOrderTypeByExternalReferenceCodeOrderTypeChannelsPageTypeExtension(
			Order order) {

			_order = order;
		}

		@GraphQLField
		public OrderTypeChannelPage
				typeByExternalReferenceCodeOrderTypeChannels(
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_orderTypeChannelResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderTypeChannelResource -> new OrderTypeChannelPage(
					orderTypeChannelResource.
						getOrderTypeByExternalReferenceCodeOrderTypeChannelsPage(
							_order.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Order _order;

	}

	@GraphQLTypeExtension(Order.class)
	public class
		GetOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPageTypeExtension {

		public GetOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPageTypeExtension(
			Order order) {

			_order = order;
		}

		@GraphQLField
		public OrderRuleOrderTypePage
				ruleByExternalReferenceCodeOrderRuleOrderTypes(
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_orderRuleOrderTypeResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				orderRuleOrderTypeResource -> new OrderRuleOrderTypePage(
					orderRuleOrderTypeResource.
						getOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPage(
							_order.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
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

	@GraphQLName("AccountGroupPage")
	public class AccountGroupPage {

		public AccountGroupPage(Page accountGroupPage) {
			actions = accountGroupPage.getActions();

			items = accountGroupPage.getItems();
			lastPage = accountGroupPage.getLastPage();
			page = accountGroupPage.getPage();
			pageSize = accountGroupPage.getPageSize();
			totalCount = accountGroupPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<AccountGroup> items;

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

	@GraphQLName("OrderRulePage")
	public class OrderRulePage {

		public OrderRulePage(Page orderRulePage) {
			actions = orderRulePage.getActions();

			items = orderRulePage.getItems();
			lastPage = orderRulePage.getLastPage();
			page = orderRulePage.getPage();
			pageSize = orderRulePage.getPageSize();
			totalCount = orderRulePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OrderRule> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrderRuleAccountPage")
	public class OrderRuleAccountPage {

		public OrderRuleAccountPage(Page orderRuleAccountPage) {
			actions = orderRuleAccountPage.getActions();

			items = orderRuleAccountPage.getItems();
			lastPage = orderRuleAccountPage.getLastPage();
			page = orderRuleAccountPage.getPage();
			pageSize = orderRuleAccountPage.getPageSize();
			totalCount = orderRuleAccountPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OrderRuleAccount> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrderRuleAccountGroupPage")
	public class OrderRuleAccountGroupPage {

		public OrderRuleAccountGroupPage(Page orderRuleAccountGroupPage) {
			actions = orderRuleAccountGroupPage.getActions();

			items = orderRuleAccountGroupPage.getItems();
			lastPage = orderRuleAccountGroupPage.getLastPage();
			page = orderRuleAccountGroupPage.getPage();
			pageSize = orderRuleAccountGroupPage.getPageSize();
			totalCount = orderRuleAccountGroupPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OrderRuleAccountGroup> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrderRuleChannelPage")
	public class OrderRuleChannelPage {

		public OrderRuleChannelPage(Page orderRuleChannelPage) {
			actions = orderRuleChannelPage.getActions();

			items = orderRuleChannelPage.getItems();
			lastPage = orderRuleChannelPage.getLastPage();
			page = orderRuleChannelPage.getPage();
			pageSize = orderRuleChannelPage.getPageSize();
			totalCount = orderRuleChannelPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OrderRuleChannel> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrderRuleOrderTypePage")
	public class OrderRuleOrderTypePage {

		public OrderRuleOrderTypePage(Page orderRuleOrderTypePage) {
			actions = orderRuleOrderTypePage.getActions();

			items = orderRuleOrderTypePage.getItems();
			lastPage = orderRuleOrderTypePage.getLastPage();
			page = orderRuleOrderTypePage.getPage();
			pageSize = orderRuleOrderTypePage.getPageSize();
			totalCount = orderRuleOrderTypePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OrderRuleOrderType> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrderTypePage")
	public class OrderTypePage {

		public OrderTypePage(Page orderTypePage) {
			actions = orderTypePage.getActions();

			items = orderTypePage.getItems();
			lastPage = orderTypePage.getLastPage();
			page = orderTypePage.getPage();
			pageSize = orderTypePage.getPageSize();
			totalCount = orderTypePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OrderType> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrderTypeChannelPage")
	public class OrderTypeChannelPage {

		public OrderTypeChannelPage(Page orderTypeChannelPage) {
			actions = orderTypeChannelPage.getActions();

			items = orderTypeChannelPage.getItems();
			lastPage = orderTypeChannelPage.getLastPage();
			page = orderTypeChannelPage.getPage();
			pageSize = orderTypeChannelPage.getPageSize();
			totalCount = orderTypeChannelPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OrderTypeChannel> items;

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
			AccountGroupResource accountGroupResource)
		throws Exception {

		accountGroupResource.setContextAcceptLanguage(_acceptLanguage);
		accountGroupResource.setContextCompany(_company);
		accountGroupResource.setContextHttpServletRequest(_httpServletRequest);
		accountGroupResource.setContextHttpServletResponse(
			_httpServletResponse);
		accountGroupResource.setContextUriInfo(_uriInfo);
		accountGroupResource.setContextUser(_user);
		accountGroupResource.setGroupLocalService(_groupLocalService);
		accountGroupResource.setRoleLocalService(_roleLocalService);
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

	private void _populateResourceContext(OrderRuleResource orderRuleResource)
		throws Exception {

		orderRuleResource.setContextAcceptLanguage(_acceptLanguage);
		orderRuleResource.setContextCompany(_company);
		orderRuleResource.setContextHttpServletRequest(_httpServletRequest);
		orderRuleResource.setContextHttpServletResponse(_httpServletResponse);
		orderRuleResource.setContextUriInfo(_uriInfo);
		orderRuleResource.setContextUser(_user);
		orderRuleResource.setGroupLocalService(_groupLocalService);
		orderRuleResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			OrderRuleAccountResource orderRuleAccountResource)
		throws Exception {

		orderRuleAccountResource.setContextAcceptLanguage(_acceptLanguage);
		orderRuleAccountResource.setContextCompany(_company);
		orderRuleAccountResource.setContextHttpServletRequest(
			_httpServletRequest);
		orderRuleAccountResource.setContextHttpServletResponse(
			_httpServletResponse);
		orderRuleAccountResource.setContextUriInfo(_uriInfo);
		orderRuleAccountResource.setContextUser(_user);
		orderRuleAccountResource.setGroupLocalService(_groupLocalService);
		orderRuleAccountResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			OrderRuleAccountGroupResource orderRuleAccountGroupResource)
		throws Exception {

		orderRuleAccountGroupResource.setContextAcceptLanguage(_acceptLanguage);
		orderRuleAccountGroupResource.setContextCompany(_company);
		orderRuleAccountGroupResource.setContextHttpServletRequest(
			_httpServletRequest);
		orderRuleAccountGroupResource.setContextHttpServletResponse(
			_httpServletResponse);
		orderRuleAccountGroupResource.setContextUriInfo(_uriInfo);
		orderRuleAccountGroupResource.setContextUser(_user);
		orderRuleAccountGroupResource.setGroupLocalService(_groupLocalService);
		orderRuleAccountGroupResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			OrderRuleChannelResource orderRuleChannelResource)
		throws Exception {

		orderRuleChannelResource.setContextAcceptLanguage(_acceptLanguage);
		orderRuleChannelResource.setContextCompany(_company);
		orderRuleChannelResource.setContextHttpServletRequest(
			_httpServletRequest);
		orderRuleChannelResource.setContextHttpServletResponse(
			_httpServletResponse);
		orderRuleChannelResource.setContextUriInfo(_uriInfo);
		orderRuleChannelResource.setContextUser(_user);
		orderRuleChannelResource.setGroupLocalService(_groupLocalService);
		orderRuleChannelResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			OrderRuleOrderTypeResource orderRuleOrderTypeResource)
		throws Exception {

		orderRuleOrderTypeResource.setContextAcceptLanguage(_acceptLanguage);
		orderRuleOrderTypeResource.setContextCompany(_company);
		orderRuleOrderTypeResource.setContextHttpServletRequest(
			_httpServletRequest);
		orderRuleOrderTypeResource.setContextHttpServletResponse(
			_httpServletResponse);
		orderRuleOrderTypeResource.setContextUriInfo(_uriInfo);
		orderRuleOrderTypeResource.setContextUser(_user);
		orderRuleOrderTypeResource.setGroupLocalService(_groupLocalService);
		orderRuleOrderTypeResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(OrderTypeResource orderTypeResource)
		throws Exception {

		orderTypeResource.setContextAcceptLanguage(_acceptLanguage);
		orderTypeResource.setContextCompany(_company);
		orderTypeResource.setContextHttpServletRequest(_httpServletRequest);
		orderTypeResource.setContextHttpServletResponse(_httpServletResponse);
		orderTypeResource.setContextUriInfo(_uriInfo);
		orderTypeResource.setContextUser(_user);
		orderTypeResource.setGroupLocalService(_groupLocalService);
		orderTypeResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			OrderTypeChannelResource orderTypeChannelResource)
		throws Exception {

		orderTypeChannelResource.setContextAcceptLanguage(_acceptLanguage);
		orderTypeChannelResource.setContextCompany(_company);
		orderTypeChannelResource.setContextHttpServletRequest(
			_httpServletRequest);
		orderTypeChannelResource.setContextHttpServletResponse(
			_httpServletResponse);
		orderTypeChannelResource.setContextUriInfo(_uriInfo);
		orderTypeChannelResource.setContextUser(_user);
		orderTypeChannelResource.setGroupLocalService(_groupLocalService);
		orderTypeChannelResource.setRoleLocalService(_roleLocalService);
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
	private static ComponentServiceObjects<AccountGroupResource>
		_accountGroupResourceComponentServiceObjects;
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
	private static ComponentServiceObjects<OrderRuleResource>
		_orderRuleResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderRuleAccountResource>
		_orderRuleAccountResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderRuleAccountGroupResource>
		_orderRuleAccountGroupResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderRuleChannelResource>
		_orderRuleChannelResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderRuleOrderTypeResource>
		_orderRuleOrderTypeResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderTypeResource>
		_orderTypeResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderTypeChannelResource>
		_orderTypeChannelResourceComponentServiceObjects;
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