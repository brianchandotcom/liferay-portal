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

package com.liferay.headless.commerce.admin.order.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
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
import com.liferay.headless.commerce.admin.order.resource.v1_0.BillingAddressResource;
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
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class Mutation {

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

	@GraphQLField
	public Response patchOrderByExternalReferenceCodeBillingAddress(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("billingAddress") BillingAddress billingAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_billingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			billingAddressResource ->
				billingAddressResource.
					patchOrderByExternalReferenceCodeBillingAddress(
						externalReferenceCode, billingAddress));
	}

	@GraphQLField
	public Response patchOrderIdBillingAddress(
			@GraphQLName("id") Long id,
			@GraphQLName("billingAddress") BillingAddress billingAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_billingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			billingAddressResource ->
				billingAddressResource.patchOrderIdBillingAddress(
					id, billingAddress));
	}

	@GraphQLField
	public Order createOrder(@GraphQLName("order") Order order)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.postOrder(order));
	}

	@GraphQLField
	public Response createOrderBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.postOrderBatch(callbackURL, object));
	}

	@GraphQLField
	public Response deleteOrderByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.deleteOrderByExternalReferenceCode(
				externalReferenceCode));
	}

	@GraphQLField
	public Response patchOrderByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("order") Order order)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.patchOrderByExternalReferenceCode(
				externalReferenceCode, order));
	}

	@GraphQLField
	public Response deleteOrder(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.deleteOrder(id));
	}

	@GraphQLField
	public Response deleteOrderBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.deleteOrderBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchOrder(
			@GraphQLName("id") Long id, @GraphQLName("order") Order order)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.patchOrder(id, order));
	}

	@GraphQLField
	public Response deleteOrderItemByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource ->
				orderItemResource.deleteOrderItemByExternalReferenceCode(
					externalReferenceCode));
	}

	@GraphQLField
	public Response patchOrderItemByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderItem") OrderItem orderItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource ->
				orderItemResource.patchOrderItemByExternalReferenceCode(
					externalReferenceCode, orderItem));
	}

	@GraphQLField
	public Response deleteOrderItem(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.deleteOrderItem(id));
	}

	@GraphQLField
	public Response deleteOrderItemBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.deleteOrderItemBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchOrderItem(
			@GraphQLName("id") Long id,
			@GraphQLName("orderItem") OrderItem orderItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.patchOrderItem(
				id, orderItem));
	}

	@GraphQLField
	public OrderItem createOrderByExternalReferenceCodeOrderItem(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderItem") OrderItem orderItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource ->
				orderItemResource.postOrderByExternalReferenceCodeOrderItem(
					externalReferenceCode, orderItem));
	}

	@GraphQLField
	public OrderItem createOrderIdOrderItem(
			@GraphQLName("id") Long id,
			@GraphQLName("orderItem") OrderItem orderItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.postOrderIdOrderItem(
				id, orderItem));
	}

	@GraphQLField
	public Response createOrderIdOrderItemBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.postOrderIdOrderItemBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response deleteOrderNoteByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource ->
				orderNoteResource.deleteOrderNoteByExternalReferenceCode(
					externalReferenceCode));
	}

	@GraphQLField
	public Response patchOrderNoteByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderNote") OrderNote orderNote)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource ->
				orderNoteResource.patchOrderNoteByExternalReferenceCode(
					externalReferenceCode, orderNote));
	}

	@GraphQLField
	public Response deleteOrderNote(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.deleteOrderNote(id));
	}

	@GraphQLField
	public Response deleteOrderNoteBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.deleteOrderNoteBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchOrderNote(
			@GraphQLName("id") Long id,
			@GraphQLName("orderNote") OrderNote orderNote)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.patchOrderNote(
				id, orderNote));
	}

	@GraphQLField
	public OrderNote createOrderByExternalReferenceCodeOrderNote(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderNote") OrderNote orderNote)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource ->
				orderNoteResource.postOrderByExternalReferenceCodeOrderNote(
					externalReferenceCode, orderNote));
	}

	@GraphQLField
	public OrderNote createOrderIdOrderNote(
			@GraphQLName("id") Long id,
			@GraphQLName("orderNote") OrderNote orderNote)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.postOrderIdOrderNote(
				id, orderNote));
	}

	@GraphQLField
	public Response createOrderIdOrderNoteBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.postOrderIdOrderNoteBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public OrderRule createOrderRule(
			@GraphQLName("orderRule") OrderRule orderRule)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> orderRuleResource.postOrderRule(orderRule));
	}

	@GraphQLField
	public Response createOrderRuleBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> orderRuleResource.postOrderRuleBatch(
				callbackURL, object));
	}

	@GraphQLField
	public boolean deleteOrderRuleByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource ->
				orderRuleResource.deleteOrderRuleByExternalReferenceCode(
					externalReferenceCode));

		return true;
	}

	@GraphQLField
	public OrderRule patchOrderRuleByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderRule") OrderRule orderRule)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource ->
				orderRuleResource.patchOrderRuleByExternalReferenceCode(
					externalReferenceCode, orderRule));
	}

	@GraphQLField
	public boolean deleteOrderRule(@GraphQLName("id") Long id)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> orderRuleResource.deleteOrderRule(id));

		return true;
	}

	@GraphQLField
	public Response deleteOrderRuleBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> orderRuleResource.deleteOrderRuleBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public OrderRule patchOrderRule(
			@GraphQLName("id") Long id,
			@GraphQLName("orderRule") OrderRule orderRule)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> orderRuleResource.patchOrderRule(
				id, orderRule));
	}

	@GraphQLField
	public boolean deleteOrderRuleAccount(
			@GraphQLName("orderRuleAccountId") Long orderRuleAccountId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource ->
				orderRuleAccountResource.deleteOrderRuleAccount(
					orderRuleAccountId));

		return true;
	}

	@GraphQLField
	public Response deleteOrderRuleAccountBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource ->
				orderRuleAccountResource.deleteOrderRuleAccountBatch(
					callbackURL, object));
	}

	@GraphQLField
	public OrderRuleAccount
			createOrderRuleByExternalReferenceCodeOrderRuleAccount(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("orderRuleAccount") OrderRuleAccount
					orderRuleAccount)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource ->
				orderRuleAccountResource.
					postOrderRuleByExternalReferenceCodeOrderRuleAccount(
						externalReferenceCode, orderRuleAccount));
	}

	@GraphQLField
	public OrderRuleAccount createOrderRuleIdOrderRuleAccount(
			@GraphQLName("id") Long id,
			@GraphQLName("orderRuleAccount") OrderRuleAccount orderRuleAccount)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource ->
				orderRuleAccountResource.postOrderRuleIdOrderRuleAccount(
					id, orderRuleAccount));
	}

	@GraphQLField
	public Response createOrderRuleIdOrderRuleAccountBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource ->
				orderRuleAccountResource.postOrderRuleIdOrderRuleAccountBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteOrderRuleAccountGroup(
			@GraphQLName("orderRuleAccountGroupId") Long
				orderRuleAccountGroupId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource ->
				orderRuleAccountGroupResource.deleteOrderRuleAccountGroup(
					orderRuleAccountGroupId));

		return true;
	}

	@GraphQLField
	public Response deleteOrderRuleAccountGroupBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource ->
				orderRuleAccountGroupResource.deleteOrderRuleAccountGroupBatch(
					callbackURL, object));
	}

	@GraphQLField
	public OrderRuleAccountGroup
			createOrderRuleByExternalReferenceCodeOrderRuleAccountGroup(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("orderRuleAccountGroup") OrderRuleAccountGroup
					orderRuleAccountGroup)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource ->
				orderRuleAccountGroupResource.
					postOrderRuleByExternalReferenceCodeOrderRuleAccountGroup(
						externalReferenceCode, orderRuleAccountGroup));
	}

	@GraphQLField
	public OrderRuleAccountGroup createOrderRuleIdOrderRuleAccountGroup(
			@GraphQLName("id") Long id,
			@GraphQLName("orderRuleAccountGroup") OrderRuleAccountGroup
				orderRuleAccountGroup)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource ->
				orderRuleAccountGroupResource.
					postOrderRuleIdOrderRuleAccountGroup(
						id, orderRuleAccountGroup));
	}

	@GraphQLField
	public Response createOrderRuleIdOrderRuleAccountGroupBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource ->
				orderRuleAccountGroupResource.
					postOrderRuleIdOrderRuleAccountGroupBatch(
						id, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteOrderRuleChannel(
			@GraphQLName("orderRuleChannelId") Long orderRuleChannelId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource ->
				orderRuleChannelResource.deleteOrderRuleChannel(
					orderRuleChannelId));

		return true;
	}

	@GraphQLField
	public Response deleteOrderRuleChannelBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource ->
				orderRuleChannelResource.deleteOrderRuleChannelBatch(
					callbackURL, object));
	}

	@GraphQLField
	public OrderRuleChannel
			createOrderRuleByExternalReferenceCodeOrderRuleChannel(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("orderRuleChannel") OrderRuleChannel
					orderRuleChannel)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource ->
				orderRuleChannelResource.
					postOrderRuleByExternalReferenceCodeOrderRuleChannel(
						externalReferenceCode, orderRuleChannel));
	}

	@GraphQLField
	public OrderRuleChannel createOrderRuleIdOrderRuleChannel(
			@GraphQLName("id") Long id,
			@GraphQLName("orderRuleChannel") OrderRuleChannel orderRuleChannel)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource ->
				orderRuleChannelResource.postOrderRuleIdOrderRuleChannel(
					id, orderRuleChannel));
	}

	@GraphQLField
	public Response createOrderRuleIdOrderRuleChannelBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource ->
				orderRuleChannelResource.postOrderRuleIdOrderRuleChannelBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteOrderRuleOrderType(
			@GraphQLName("orderRuleOrderTypeId") Long orderRuleOrderTypeId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource ->
				orderRuleOrderTypeResource.deleteOrderRuleOrderType(
					orderRuleOrderTypeId));

		return true;
	}

	@GraphQLField
	public Response deleteOrderRuleOrderTypeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource ->
				orderRuleOrderTypeResource.deleteOrderRuleOrderTypeBatch(
					callbackURL, object));
	}

	@GraphQLField
	public OrderRuleOrderType
			createOrderRuleByExternalReferenceCodeOrderRuleOrderType(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("orderRuleOrderType") OrderRuleOrderType
					orderRuleOrderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource ->
				orderRuleOrderTypeResource.
					postOrderRuleByExternalReferenceCodeOrderRuleOrderType(
						externalReferenceCode, orderRuleOrderType));
	}

	@GraphQLField
	public OrderRuleOrderType createOrderRuleIdOrderRuleOrderType(
			@GraphQLName("id") Long id,
			@GraphQLName("orderRuleOrderType") OrderRuleOrderType
				orderRuleOrderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource ->
				orderRuleOrderTypeResource.postOrderRuleIdOrderRuleOrderType(
					id, orderRuleOrderType));
	}

	@GraphQLField
	public Response createOrderRuleIdOrderRuleOrderTypeBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource ->
				orderRuleOrderTypeResource.
					postOrderRuleIdOrderRuleOrderTypeBatch(
						id, callbackURL, object));
	}

	@GraphQLField
	public OrderType createOrderType(
			@GraphQLName("orderType") OrderType orderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> orderTypeResource.postOrderType(orderType));
	}

	@GraphQLField
	public Response createOrderTypeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> orderTypeResource.postOrderTypeBatch(
				callbackURL, object));
	}

	@GraphQLField
	public boolean deleteOrderTypeByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource ->
				orderTypeResource.deleteOrderTypeByExternalReferenceCode(
					externalReferenceCode));

		return true;
	}

	@GraphQLField
	public OrderType patchOrderTypeByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderType") OrderType orderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource ->
				orderTypeResource.patchOrderTypeByExternalReferenceCode(
					externalReferenceCode, orderType));
	}

	@GraphQLField
	public boolean deleteOrderType(@GraphQLName("id") Long id)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> orderTypeResource.deleteOrderType(id));

		return true;
	}

	@GraphQLField
	public Response deleteOrderTypeBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> orderTypeResource.deleteOrderTypeBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public OrderType patchOrderType(
			@GraphQLName("id") Long id,
			@GraphQLName("orderType") OrderType orderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> orderTypeResource.patchOrderType(
				id, orderType));
	}

	@GraphQLField
	public boolean deleteOrderTypeChannel(
			@GraphQLName("orderTypeChannelId") Long orderTypeChannelId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource ->
				orderTypeChannelResource.deleteOrderTypeChannel(
					orderTypeChannelId));

		return true;
	}

	@GraphQLField
	public Response deleteOrderTypeChannelBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource ->
				orderTypeChannelResource.deleteOrderTypeChannelBatch(
					callbackURL, object));
	}

	@GraphQLField
	public OrderTypeChannel
			createOrderTypeByExternalReferenceCodeOrderTypeChannel(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("orderTypeChannel") OrderTypeChannel
					orderTypeChannel)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource ->
				orderTypeChannelResource.
					postOrderTypeByExternalReferenceCodeOrderTypeChannel(
						externalReferenceCode, orderTypeChannel));
	}

	@GraphQLField
	public OrderTypeChannel createOrderTypeIdOrderTypeChannel(
			@GraphQLName("id") Long id,
			@GraphQLName("orderTypeChannel") OrderTypeChannel orderTypeChannel)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource ->
				orderTypeChannelResource.postOrderTypeIdOrderTypeChannel(
					id, orderTypeChannel));
	}

	@GraphQLField
	public Response createOrderTypeIdOrderTypeChannelBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource ->
				orderTypeChannelResource.postOrderTypeIdOrderTypeChannelBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Response patchOrderByExternalReferenceCodeShippingAddress(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("shippingAddress") ShippingAddress shippingAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingAddressResource ->
				shippingAddressResource.
					patchOrderByExternalReferenceCodeShippingAddress(
						externalReferenceCode, shippingAddress));
	}

	@GraphQLField
	public Response patchOrderIdShippingAddress(
			@GraphQLName("id") Long id,
			@GraphQLName("shippingAddress") ShippingAddress shippingAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingAddressResource ->
				shippingAddressResource.patchOrderIdShippingAddress(
					id, shippingAddress));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
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
		billingAddressResource.setGroupLocalService(_groupLocalService);
		billingAddressResource.setRoleLocalService(_roleLocalService);
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

	private static ComponentServiceObjects<BillingAddressResource>
		_billingAddressResourceComponentServiceObjects;
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
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}