/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		Mutation.setPlacedOrderCommentResourceComponentServiceObjects(
			_placedOrderCommentResourceComponentServiceObjects);
		Mutation.setPlacedOrderItemResourceComponentServiceObjects(
			_placedOrderItemResourceComponentServiceObjects);
		Mutation.setPlacedOrderItemShipmentResourceComponentServiceObjects(
			_placedOrderItemShipmentResourceComponentServiceObjects);

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

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createPlacedOrderPlacedOrderCommentsPageExportBatch",
						new ObjectValuePair<>(
							PlacedOrderCommentResourceImpl.class,
							"postPlacedOrderPlacedOrderCommentsPageExportBatch"));
					put(
						"mutation#createPlacedOrderPlacedOrderItemsPageExportBatch",
						new ObjectValuePair<>(
							PlacedOrderItemResourceImpl.class,
							"postPlacedOrderPlacedOrderItemsPageExportBatch"));
					put(
						"mutation#createPlacedOrderItemPlacedOrderItemShipmentsPageExportBatch",
						new ObjectValuePair<>(
							PlacedOrderItemShipmentResourceImpl.class,
							"postPlacedOrderItemPlacedOrderItemShipmentsPageExportBatch"));

					put(
						"query#channelAccountPlacedOrders",
						new ObjectValuePair<>(
							PlacedOrderResourceImpl.class,
							"getChannelAccountPlacedOrdersPage"));
					put(
						"query#placedOrder",
						new ObjectValuePair<>(
							PlacedOrderResourceImpl.class, "getPlacedOrder"));
					put(
						"query#placedOrderPaymentURL",
						new ObjectValuePair<>(
							PlacedOrderResourceImpl.class,
							"getPlacedOrderPaymentURL"));
					put(
						"query#placedOrderPlacedOrderBillingAddres",
						new ObjectValuePair<>(
							PlacedOrderAddressResourceImpl.class,
							"getPlacedOrderPlacedOrderBillingAddres"));
					put(
						"query#placedOrderPlacedOrderShippingAddres",
						new ObjectValuePair<>(
							PlacedOrderAddressResourceImpl.class,
							"getPlacedOrderPlacedOrderShippingAddres"));
					put(
						"query#placedOrderComment",
						new ObjectValuePair<>(
							PlacedOrderCommentResourceImpl.class,
							"getPlacedOrderComment"));
					put(
						"query#placedOrderPlacedOrderComments",
						new ObjectValuePair<>(
							PlacedOrderCommentResourceImpl.class,
							"getPlacedOrderPlacedOrderCommentsPage"));
					put(
						"query#placedOrderItem",
						new ObjectValuePair<>(
							PlacedOrderItemResourceImpl.class,
							"getPlacedOrderItem"));
					put(
						"query#placedOrderPlacedOrderItems",
						new ObjectValuePair<>(
							PlacedOrderItemResourceImpl.class,
							"getPlacedOrderPlacedOrderItemsPage"));
					put(
						"query#placedOrderItemPlacedOrderItemShipments",
						new ObjectValuePair<>(
							PlacedOrderItemShipmentResourceImpl.class,
							"getPlacedOrderItemPlacedOrderItemShipmentsPage"));

					put(
						"query#PlacedOrder.paymentURL",
						new ObjectValuePair<>(
							PlacedOrderResourceImpl.class,
							"getPlacedOrderPaymentURL"));
					put(
						"query#PlacedOrder.placedOrderBillingAddres",
						new ObjectValuePair<>(
							PlacedOrderAddressResourceImpl.class,
							"getPlacedOrderPlacedOrderBillingAddres"));
					put(
						"query#PlacedOrder.placedOrderShippingAddres",
						new ObjectValuePair<>(
							PlacedOrderAddressResourceImpl.class,
							"getPlacedOrderPlacedOrderShippingAddres"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PlacedOrderCommentResource>
		_placedOrderCommentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PlacedOrderItemResource>
		_placedOrderItemResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PlacedOrderItemShipmentResource>
		_placedOrderItemShipmentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PlacedOrderResource>
		_placedOrderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PlacedOrderAddressResource>
		_placedOrderAddressResourceComponentServiceObjects;

}