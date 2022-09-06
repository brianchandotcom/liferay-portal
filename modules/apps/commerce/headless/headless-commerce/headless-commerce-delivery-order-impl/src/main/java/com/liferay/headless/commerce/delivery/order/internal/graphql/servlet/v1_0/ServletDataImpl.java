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

package com.liferay.headless.commerce.delivery.order.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.delivery.order.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.delivery.order.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.delivery.order.internal.resource.v1_0.PlacedOrderAddressResourceImpl;
import com.liferay.headless.commerce.delivery.order.internal.resource.v1_0.PlacedOrderCommentResourceImpl;
import com.liferay.headless.commerce.delivery.order.internal.resource.v1_0.PlacedOrderItemResourceImpl;
import com.liferay.headless.commerce.delivery.order.internal.resource.v1_0.PlacedOrderItemShipmentResourceImpl;
import com.liferay.headless.commerce.delivery.order.internal.resource.v1_0.PlacedOrderResourceImpl;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderAddressResource;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderCommentResource;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderItemResource;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderItemShipmentResource;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderResource;
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
 * @author Andrea Sbarra
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Query.setPlacedOrderResourceComponentServiceObjects(
			_placedOrderResourceComponentServiceObjects);
		Query.setPlacedOrderAddressResourceComponentServiceObjects(
			_placedOrderAddressResourceComponentServiceObjects);
		Query.setPlacedOrderCommentResourceComponentServiceObjects(
			_placedOrderCommentResourceComponentServiceObjects);
		Query.setPlacedOrderItemResourceComponentServiceObjects(
			_placedOrderItemResourceComponentServiceObjects);
		Query.setPlacedOrderItemShipmentResourceComponentServiceObjects(
			_placedOrderItemShipmentResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Delivery.Order";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-delivery-order-graphql/v1_0";
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
			"query#channelAccountPlacedOrders",
			new ObjectValuePair<>(
				PlacedOrderResourceImpl.class,
				"getChannelAccountPlacedOrdersPage"));
		_resourceMethodPairs.put(
			"query#placedOrder",
			new ObjectValuePair<>(
				PlacedOrderResourceImpl.class, "getPlacedOrder"));
		_resourceMethodPairs.put(
			"query#placedOrderPaymentURL",
			new ObjectValuePair<>(
				PlacedOrderResourceImpl.class, "getPlacedOrderPaymentURL"));
		_resourceMethodPairs.put(
			"query#placedOrderPlacedOrderBillingAddres",
			new ObjectValuePair<>(
				PlacedOrderAddressResourceImpl.class,
				"getPlacedOrderPlacedOrderBillingAddres"));
		_resourceMethodPairs.put(
			"query#placedOrderPlacedOrderShippingAddres",
			new ObjectValuePair<>(
				PlacedOrderAddressResourceImpl.class,
				"getPlacedOrderPlacedOrderShippingAddres"));
		_resourceMethodPairs.put(
			"query#placedOrderComment",
			new ObjectValuePair<>(
				PlacedOrderCommentResourceImpl.class, "getPlacedOrderComment"));
		_resourceMethodPairs.put(
			"query#placedOrderPlacedOrderComments",
			new ObjectValuePair<>(
				PlacedOrderCommentResourceImpl.class,
				"getPlacedOrderPlacedOrderCommentsPage"));
		_resourceMethodPairs.put(
			"query#placedOrderItem",
			new ObjectValuePair<>(
				PlacedOrderItemResourceImpl.class, "getPlacedOrderItem"));
		_resourceMethodPairs.put(
			"query#placedOrderPlacedOrderItems",
			new ObjectValuePair<>(
				PlacedOrderItemResourceImpl.class,
				"getPlacedOrderPlacedOrderItemsPage"));
		_resourceMethodPairs.put(
			"query#placedOrderItemPlacedOrderItemShipments",
			new ObjectValuePair<>(
				PlacedOrderItemShipmentResourceImpl.class,
				"getPlacedOrderItemPlacedOrderItemShipmentsPage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PlacedOrderResource>
		_placedOrderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PlacedOrderAddressResource>
		_placedOrderAddressResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PlacedOrderCommentResource>
		_placedOrderCommentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PlacedOrderItemResource>
		_placedOrderItemResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PlacedOrderItemShipmentResource>
		_placedOrderItemShipmentResourceComponentServiceObjects;

}