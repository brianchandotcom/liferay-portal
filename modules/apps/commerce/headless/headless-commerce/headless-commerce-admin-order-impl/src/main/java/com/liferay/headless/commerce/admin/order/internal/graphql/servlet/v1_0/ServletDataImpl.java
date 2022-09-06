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

package com.liferay.headless.commerce.admin.order.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.admin.order.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.admin.order.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.AccountGroupResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.AccountResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.BillingAddressResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.ChannelResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.OrderItemResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.OrderNoteResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.OrderResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.OrderRuleAccountGroupResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.OrderRuleAccountResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.OrderRuleChannelResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.OrderRuleOrderTypeResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.OrderRuleResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.OrderTypeChannelResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.OrderTypeResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.ShippingAddressResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.TermOrderTypeResourceImpl;
import com.liferay.headless.commerce.admin.order.internal.resource.v1_0.TermResourceImpl;
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
import com.liferay.headless.commerce.admin.order.resource.v1_0.TermOrderTypeResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.TermResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setBillingAddressResourceComponentServiceObjects(
			_billingAddressResourceComponentServiceObjects);
		Mutation.setOrderResourceComponentServiceObjects(
			_orderResourceComponentServiceObjects);
		Mutation.setOrderItemResourceComponentServiceObjects(
			_orderItemResourceComponentServiceObjects);
		Mutation.setOrderNoteResourceComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects);
		Mutation.setOrderRuleResourceComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects);
		Mutation.setOrderRuleAccountResourceComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects);
		Mutation.setOrderRuleAccountGroupResourceComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects);
		Mutation.setOrderRuleChannelResourceComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects);
		Mutation.setOrderRuleOrderTypeResourceComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects);
		Mutation.setOrderTypeResourceComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects);
		Mutation.setOrderTypeChannelResourceComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects);
		Mutation.setShippingAddressResourceComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects);
		Mutation.setTermResourceComponentServiceObjects(
			_termResourceComponentServiceObjects);
		Mutation.setTermOrderTypeResourceComponentServiceObjects(
			_termOrderTypeResourceComponentServiceObjects);

		Query.setAccountResourceComponentServiceObjects(
			_accountResourceComponentServiceObjects);
		Query.setAccountGroupResourceComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects);
		Query.setBillingAddressResourceComponentServiceObjects(
			_billingAddressResourceComponentServiceObjects);
		Query.setChannelResourceComponentServiceObjects(
			_channelResourceComponentServiceObjects);
		Query.setOrderResourceComponentServiceObjects(
			_orderResourceComponentServiceObjects);
		Query.setOrderItemResourceComponentServiceObjects(
			_orderItemResourceComponentServiceObjects);
		Query.setOrderNoteResourceComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects);
		Query.setOrderRuleResourceComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects);
		Query.setOrderRuleAccountResourceComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects);
		Query.setOrderRuleAccountGroupResourceComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects);
		Query.setOrderRuleChannelResourceComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects);
		Query.setOrderRuleOrderTypeResourceComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects);
		Query.setOrderTypeResourceComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects);
		Query.setOrderTypeChannelResourceComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects);
		Query.setShippingAddressResourceComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects);
		Query.setTermResourceComponentServiceObjects(
			_termResourceComponentServiceObjects);
		Query.setTermOrderTypeResourceComponentServiceObjects(
			_termOrderTypeResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Admin.Order";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-admin-order-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#patchOrderByExternalReferenceCodeBillingAddress",
			new ObjectValuePair<>(
				BillingAddressResourceImpl.class,
				"patchOrderByExternalReferenceCodeBillingAddress"));
		_resourceMethodPairs.put(
			"mutation#patchOrderIdBillingAddress",
			new ObjectValuePair<>(
				BillingAddressResourceImpl.class,
				"patchOrderIdBillingAddress"));
		_resourceMethodPairs.put(
			"mutation#createOrder",
			new ObjectValuePair<>(OrderResourceImpl.class, "postOrder"));
		_resourceMethodPairs.put(
			"mutation#createOrderBatch",
			new ObjectValuePair<>(OrderResourceImpl.class, "postOrderBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderResourceImpl.class, "deleteOrderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchOrderByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderResourceImpl.class, "patchOrderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteOrder",
			new ObjectValuePair<>(OrderResourceImpl.class, "deleteOrder"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderBatch",
			new ObjectValuePair<>(OrderResourceImpl.class, "deleteOrderBatch"));
		_resourceMethodPairs.put(
			"mutation#patchOrder",
			new ObjectValuePair<>(OrderResourceImpl.class, "patchOrder"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderItemByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class,
				"deleteOrderItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchOrderItemByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class,
				"patchOrderItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateOrderItemByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class,
				"putOrderItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderItem",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class, "deleteOrderItem"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderItemBatch",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class, "deleteOrderItemBatch"));
		_resourceMethodPairs.put(
			"mutation#patchOrderItem",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class, "patchOrderItem"));
		_resourceMethodPairs.put(
			"mutation#updateOrderItem",
			new ObjectValuePair<>(OrderItemResourceImpl.class, "putOrderItem"));
		_resourceMethodPairs.put(
			"mutation#updateOrderItemBatch",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class, "putOrderItemBatch"));
		_resourceMethodPairs.put(
			"mutation#createOrderByExternalReferenceCodeOrderItem",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class,
				"postOrderByExternalReferenceCodeOrderItem"));
		_resourceMethodPairs.put(
			"mutation#createOrderIdOrderItem",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class, "postOrderIdOrderItem"));
		_resourceMethodPairs.put(
			"mutation#createOrderIdOrderItemBatch",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class, "postOrderIdOrderItemBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderNoteByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderNoteResourceImpl.class,
				"deleteOrderNoteByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchOrderNoteByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderNoteResourceImpl.class,
				"patchOrderNoteByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderNote",
			new ObjectValuePair<>(
				OrderNoteResourceImpl.class, "deleteOrderNote"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderNoteBatch",
			new ObjectValuePair<>(
				OrderNoteResourceImpl.class, "deleteOrderNoteBatch"));
		_resourceMethodPairs.put(
			"mutation#patchOrderNote",
			new ObjectValuePair<>(
				OrderNoteResourceImpl.class, "patchOrderNote"));
		_resourceMethodPairs.put(
			"mutation#createOrderByExternalReferenceCodeOrderNote",
			new ObjectValuePair<>(
				OrderNoteResourceImpl.class,
				"postOrderByExternalReferenceCodeOrderNote"));
		_resourceMethodPairs.put(
			"mutation#createOrderIdOrderNote",
			new ObjectValuePair<>(
				OrderNoteResourceImpl.class, "postOrderIdOrderNote"));
		_resourceMethodPairs.put(
			"mutation#createOrderIdOrderNoteBatch",
			new ObjectValuePair<>(
				OrderNoteResourceImpl.class, "postOrderIdOrderNoteBatch"));
		_resourceMethodPairs.put(
			"mutation#createOrderRule",
			new ObjectValuePair<>(
				OrderRuleResourceImpl.class, "postOrderRule"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleBatch",
			new ObjectValuePair<>(
				OrderRuleResourceImpl.class, "postOrderRuleBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderRuleByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderRuleResourceImpl.class,
				"deleteOrderRuleByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchOrderRuleByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderRuleResourceImpl.class,
				"patchOrderRuleByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderRule",
			new ObjectValuePair<>(
				OrderRuleResourceImpl.class, "deleteOrderRule"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderRuleBatch",
			new ObjectValuePair<>(
				OrderRuleResourceImpl.class, "deleteOrderRuleBatch"));
		_resourceMethodPairs.put(
			"mutation#patchOrderRule",
			new ObjectValuePair<>(
				OrderRuleResourceImpl.class, "patchOrderRule"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderRuleAccount",
			new ObjectValuePair<>(
				OrderRuleAccountResourceImpl.class, "deleteOrderRuleAccount"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderRuleAccountBatch",
			new ObjectValuePair<>(
				OrderRuleAccountResourceImpl.class,
				"deleteOrderRuleAccountBatch"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleByExternalReferenceCodeOrderRuleAccount",
			new ObjectValuePair<>(
				OrderRuleAccountResourceImpl.class,
				"postOrderRuleByExternalReferenceCodeOrderRuleAccount"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleIdOrderRuleAccount",
			new ObjectValuePair<>(
				OrderRuleAccountResourceImpl.class,
				"postOrderRuleIdOrderRuleAccount"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleIdOrderRuleAccountBatch",
			new ObjectValuePair<>(
				OrderRuleAccountResourceImpl.class,
				"postOrderRuleIdOrderRuleAccountBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderRuleAccountGroup",
			new ObjectValuePair<>(
				OrderRuleAccountGroupResourceImpl.class,
				"deleteOrderRuleAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderRuleAccountGroupBatch",
			new ObjectValuePair<>(
				OrderRuleAccountGroupResourceImpl.class,
				"deleteOrderRuleAccountGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleByExternalReferenceCodeOrderRuleAccountGroup",
			new ObjectValuePair<>(
				OrderRuleAccountGroupResourceImpl.class,
				"postOrderRuleByExternalReferenceCodeOrderRuleAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleIdOrderRuleAccountGroup",
			new ObjectValuePair<>(
				OrderRuleAccountGroupResourceImpl.class,
				"postOrderRuleIdOrderRuleAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleIdOrderRuleAccountGroupBatch",
			new ObjectValuePair<>(
				OrderRuleAccountGroupResourceImpl.class,
				"postOrderRuleIdOrderRuleAccountGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderRuleChannel",
			new ObjectValuePair<>(
				OrderRuleChannelResourceImpl.class, "deleteOrderRuleChannel"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderRuleChannelBatch",
			new ObjectValuePair<>(
				OrderRuleChannelResourceImpl.class,
				"deleteOrderRuleChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleByExternalReferenceCodeOrderRuleChannel",
			new ObjectValuePair<>(
				OrderRuleChannelResourceImpl.class,
				"postOrderRuleByExternalReferenceCodeOrderRuleChannel"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleIdOrderRuleChannel",
			new ObjectValuePair<>(
				OrderRuleChannelResourceImpl.class,
				"postOrderRuleIdOrderRuleChannel"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleIdOrderRuleChannelBatch",
			new ObjectValuePair<>(
				OrderRuleChannelResourceImpl.class,
				"postOrderRuleIdOrderRuleChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderRuleOrderType",
			new ObjectValuePair<>(
				OrderRuleOrderTypeResourceImpl.class,
				"deleteOrderRuleOrderType"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderRuleOrderTypeBatch",
			new ObjectValuePair<>(
				OrderRuleOrderTypeResourceImpl.class,
				"deleteOrderRuleOrderTypeBatch"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleByExternalReferenceCodeOrderRuleOrderType",
			new ObjectValuePair<>(
				OrderRuleOrderTypeResourceImpl.class,
				"postOrderRuleByExternalReferenceCodeOrderRuleOrderType"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleIdOrderRuleOrderType",
			new ObjectValuePair<>(
				OrderRuleOrderTypeResourceImpl.class,
				"postOrderRuleIdOrderRuleOrderType"));
		_resourceMethodPairs.put(
			"mutation#createOrderRuleIdOrderRuleOrderTypeBatch",
			new ObjectValuePair<>(
				OrderRuleOrderTypeResourceImpl.class,
				"postOrderRuleIdOrderRuleOrderTypeBatch"));
		_resourceMethodPairs.put(
			"mutation#createOrderType",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class, "postOrderType"));
		_resourceMethodPairs.put(
			"mutation#createOrderTypeBatch",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class, "postOrderTypeBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderTypeByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class,
				"deleteOrderTypeByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchOrderTypeByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class,
				"patchOrderTypeByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderType",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class, "deleteOrderType"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderTypeBatch",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class, "deleteOrderTypeBatch"));
		_resourceMethodPairs.put(
			"mutation#patchOrderType",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class, "patchOrderType"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderTypeChannel",
			new ObjectValuePair<>(
				OrderTypeChannelResourceImpl.class, "deleteOrderTypeChannel"));
		_resourceMethodPairs.put(
			"mutation#deleteOrderTypeChannelBatch",
			new ObjectValuePair<>(
				OrderTypeChannelResourceImpl.class,
				"deleteOrderTypeChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#createOrderTypeByExternalReferenceCodeOrderTypeChannel",
			new ObjectValuePair<>(
				OrderTypeChannelResourceImpl.class,
				"postOrderTypeByExternalReferenceCodeOrderTypeChannel"));
		_resourceMethodPairs.put(
			"mutation#createOrderTypeIdOrderTypeChannel",
			new ObjectValuePair<>(
				OrderTypeChannelResourceImpl.class,
				"postOrderTypeIdOrderTypeChannel"));
		_resourceMethodPairs.put(
			"mutation#createOrderTypeIdOrderTypeChannelBatch",
			new ObjectValuePair<>(
				OrderTypeChannelResourceImpl.class,
				"postOrderTypeIdOrderTypeChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#patchOrderByExternalReferenceCodeShippingAddress",
			new ObjectValuePair<>(
				ShippingAddressResourceImpl.class,
				"patchOrderByExternalReferenceCodeShippingAddress"));
		_resourceMethodPairs.put(
			"mutation#patchOrderIdShippingAddress",
			new ObjectValuePair<>(
				ShippingAddressResourceImpl.class,
				"patchOrderIdShippingAddress"));
		_resourceMethodPairs.put(
			"mutation#createTerm",
			new ObjectValuePair<>(TermResourceImpl.class, "postTerm"));
		_resourceMethodPairs.put(
			"mutation#createTermBatch",
			new ObjectValuePair<>(TermResourceImpl.class, "postTermBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteTermByExternalReferenceCode",
			new ObjectValuePair<>(
				TermResourceImpl.class, "deleteTermByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchTermByExternalReferenceCode",
			new ObjectValuePair<>(
				TermResourceImpl.class, "patchTermByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteTerm",
			new ObjectValuePair<>(TermResourceImpl.class, "deleteTerm"));
		_resourceMethodPairs.put(
			"mutation#deleteTermBatch",
			new ObjectValuePair<>(TermResourceImpl.class, "deleteTermBatch"));
		_resourceMethodPairs.put(
			"mutation#patchTerm",
			new ObjectValuePair<>(TermResourceImpl.class, "patchTerm"));
		_resourceMethodPairs.put(
			"mutation#deleteTermOrderType",
			new ObjectValuePair<>(
				TermOrderTypeResourceImpl.class, "deleteTermOrderType"));
		_resourceMethodPairs.put(
			"mutation#deleteTermOrderTypeBatch",
			new ObjectValuePair<>(
				TermOrderTypeResourceImpl.class, "deleteTermOrderTypeBatch"));
		_resourceMethodPairs.put(
			"mutation#createTermByExternalReferenceCodeTermOrderType",
			new ObjectValuePair<>(
				TermOrderTypeResourceImpl.class,
				"postTermByExternalReferenceCodeTermOrderType"));
		_resourceMethodPairs.put(
			"mutation#createTermIdTermOrderType",
			new ObjectValuePair<>(
				TermOrderTypeResourceImpl.class, "postTermIdTermOrderType"));
		_resourceMethodPairs.put(
			"mutation#createTermIdTermOrderTypeBatch",
			new ObjectValuePair<>(
				TermOrderTypeResourceImpl.class,
				"postTermIdTermOrderTypeBatch"));
		_resourceMethodPairs.put(
			"query#orderRuleAccountAccount",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "getOrderRuleAccountAccount"));
		_resourceMethodPairs.put(
			"query#orderByExternalReferenceCodeAccount",
			new ObjectValuePair<>(
				AccountResourceImpl.class,
				"getOrderByExternalReferenceCodeAccount"));
		_resourceMethodPairs.put(
			"query#orderIdAccount",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "getOrderIdAccount"));
		_resourceMethodPairs.put(
			"query#orderRuleAccountGroupAccountGroup",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class,
				"getOrderRuleAccountGroupAccountGroup"));
		_resourceMethodPairs.put(
			"query#orderByExternalReferenceCodeBillingAddress",
			new ObjectValuePair<>(
				BillingAddressResourceImpl.class,
				"getOrderByExternalReferenceCodeBillingAddress"));
		_resourceMethodPairs.put(
			"query#orderIdBillingAddress",
			new ObjectValuePair<>(
				BillingAddressResourceImpl.class, "getOrderIdBillingAddress"));
		_resourceMethodPairs.put(
			"query#orderRuleChannelChannel",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "getOrderRuleChannelChannel"));
		_resourceMethodPairs.put(
			"query#orderTypeChannelChannel",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "getOrderTypeChannelChannel"));
		_resourceMethodPairs.put(
			"query#orderByExternalReferenceCodeChannel",
			new ObjectValuePair<>(
				ChannelResourceImpl.class,
				"getOrderByExternalReferenceCodeChannel"));
		_resourceMethodPairs.put(
			"query#orderIdChannel",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "getOrderIdChannel"));
		_resourceMethodPairs.put(
			"query#orders",
			new ObjectValuePair<>(OrderResourceImpl.class, "getOrdersPage"));
		_resourceMethodPairs.put(
			"query#orderByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderResourceImpl.class, "getOrderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#order",
			new ObjectValuePair<>(OrderResourceImpl.class, "getOrder"));
		_resourceMethodPairs.put(
			"query#orderItems",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class, "getOrderItemsPage"));
		_resourceMethodPairs.put(
			"query#orderItemByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class,
				"getOrderItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#orderItem",
			new ObjectValuePair<>(OrderItemResourceImpl.class, "getOrderItem"));
		_resourceMethodPairs.put(
			"query#orderByExternalReferenceCodeOrderItems",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class,
				"getOrderByExternalReferenceCodeOrderItemsPage"));
		_resourceMethodPairs.put(
			"query#orderIdOrderItems",
			new ObjectValuePair<>(
				OrderItemResourceImpl.class, "getOrderIdOrderItemsPage"));
		_resourceMethodPairs.put(
			"query#orderNoteByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderNoteResourceImpl.class,
				"getOrderNoteByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#orderNote",
			new ObjectValuePair<>(OrderNoteResourceImpl.class, "getOrderNote"));
		_resourceMethodPairs.put(
			"query#orderByExternalReferenceCodeOrderNotes",
			new ObjectValuePair<>(
				OrderNoteResourceImpl.class,
				"getOrderByExternalReferenceCodeOrderNotesPage"));
		_resourceMethodPairs.put(
			"query#orderIdOrderNotes",
			new ObjectValuePair<>(
				OrderNoteResourceImpl.class, "getOrderIdOrderNotesPage"));
		_resourceMethodPairs.put(
			"query#orderRules",
			new ObjectValuePair<>(
				OrderRuleResourceImpl.class, "getOrderRulesPage"));
		_resourceMethodPairs.put(
			"query#orderRuleByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderRuleResourceImpl.class,
				"getOrderRuleByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#orderRule",
			new ObjectValuePair<>(OrderRuleResourceImpl.class, "getOrderRule"));
		_resourceMethodPairs.put(
			"query#orderRuleByExternalReferenceCodeOrderRuleAccounts",
			new ObjectValuePair<>(
				OrderRuleAccountResourceImpl.class,
				"getOrderRuleByExternalReferenceCodeOrderRuleAccountsPage"));
		_resourceMethodPairs.put(
			"query#orderRuleIdOrderRuleAccounts",
			new ObjectValuePair<>(
				OrderRuleAccountResourceImpl.class,
				"getOrderRuleIdOrderRuleAccountsPage"));
		_resourceMethodPairs.put(
			"query#orderRuleByExternalReferenceCodeOrderRuleAccountGroups",
			new ObjectValuePair<>(
				OrderRuleAccountGroupResourceImpl.class,
				"getOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#orderRuleIdOrderRuleAccountGroups",
			new ObjectValuePair<>(
				OrderRuleAccountGroupResourceImpl.class,
				"getOrderRuleIdOrderRuleAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#orderRuleByExternalReferenceCodeOrderRuleChannels",
			new ObjectValuePair<>(
				OrderRuleChannelResourceImpl.class,
				"getOrderRuleByExternalReferenceCodeOrderRuleChannelsPage"));
		_resourceMethodPairs.put(
			"query#orderRuleIdOrderRuleChannels",
			new ObjectValuePair<>(
				OrderRuleChannelResourceImpl.class,
				"getOrderRuleIdOrderRuleChannelsPage"));
		_resourceMethodPairs.put(
			"query#orderRuleByExternalReferenceCodeOrderRuleOrderTypes",
			new ObjectValuePair<>(
				OrderRuleOrderTypeResourceImpl.class,
				"getOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPage"));
		_resourceMethodPairs.put(
			"query#orderRuleIdOrderRuleOrderTypes",
			new ObjectValuePair<>(
				OrderRuleOrderTypeResourceImpl.class,
				"getOrderRuleIdOrderRuleOrderTypesPage"));
		_resourceMethodPairs.put(
			"query#orderRuleOrderTypeOrderType",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class, "getOrderRuleOrderTypeOrderType"));
		_resourceMethodPairs.put(
			"query#orderTypes",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class, "getOrderTypesPage"));
		_resourceMethodPairs.put(
			"query#orderTypeByExternalReferenceCode",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class,
				"getOrderTypeByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#orderType",
			new ObjectValuePair<>(OrderTypeResourceImpl.class, "getOrderType"));
		_resourceMethodPairs.put(
			"query#termOrderTypeOrderType",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class, "getTermOrderTypeOrderType"));
		_resourceMethodPairs.put(
			"query#orderTypeByExternalReferenceCodeOrderTypeChannels",
			new ObjectValuePair<>(
				OrderTypeChannelResourceImpl.class,
				"getOrderTypeByExternalReferenceCodeOrderTypeChannelsPage"));
		_resourceMethodPairs.put(
			"query#orderTypeIdOrderTypeChannels",
			new ObjectValuePair<>(
				OrderTypeChannelResourceImpl.class,
				"getOrderTypeIdOrderTypeChannelsPage"));
		_resourceMethodPairs.put(
			"query#orderItemShippingAddress",
			new ObjectValuePair<>(
				ShippingAddressResourceImpl.class,
				"getOrderItemShippingAddress"));
		_resourceMethodPairs.put(
			"query#orderByExternalReferenceCodeShippingAddress",
			new ObjectValuePair<>(
				ShippingAddressResourceImpl.class,
				"getOrderByExternalReferenceCodeShippingAddress"));
		_resourceMethodPairs.put(
			"query#orderIdShippingAddress",
			new ObjectValuePair<>(
				ShippingAddressResourceImpl.class,
				"getOrderIdShippingAddress"));
		_resourceMethodPairs.put(
			"query#terms",
			new ObjectValuePair<>(TermResourceImpl.class, "getTermsPage"));
		_resourceMethodPairs.put(
			"query#termByExternalReferenceCode",
			new ObjectValuePair<>(
				TermResourceImpl.class, "getTermByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#term",
			new ObjectValuePair<>(TermResourceImpl.class, "getTerm"));
		_resourceMethodPairs.put(
			"query#termByExternalReferenceCodeTermOrderTypes",
			new ObjectValuePair<>(
				TermOrderTypeResourceImpl.class,
				"getTermByExternalReferenceCodeTermOrderTypesPage"));
		_resourceMethodPairs.put(
			"query#termIdTermOrderTypes",
			new ObjectValuePair<>(
				TermOrderTypeResourceImpl.class,
				"getTermIdTermOrderTypesPage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<BillingAddressResource>
		_billingAddressResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderResource>
		_orderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderItemResource>
		_orderItemResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderNoteResource>
		_orderNoteResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderRuleResource>
		_orderRuleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderRuleAccountResource>
		_orderRuleAccountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderRuleAccountGroupResource>
		_orderRuleAccountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderRuleChannelResource>
		_orderRuleChannelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderRuleOrderTypeResource>
		_orderRuleOrderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderTypeResource>
		_orderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderTypeChannelResource>
		_orderTypeChannelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ShippingAddressResource>
		_shippingAddressResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TermResource>
		_termResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TermOrderTypeResource>
		_termOrderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountGroupResource>
		_accountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;

}