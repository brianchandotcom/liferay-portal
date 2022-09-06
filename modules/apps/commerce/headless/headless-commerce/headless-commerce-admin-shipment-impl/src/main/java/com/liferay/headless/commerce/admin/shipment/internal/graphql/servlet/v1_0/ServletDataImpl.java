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

package com.liferay.headless.commerce.admin.shipment.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.admin.shipment.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.admin.shipment.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.admin.shipment.internal.resource.v1_0.ShipmentItemResourceImpl;
import com.liferay.headless.commerce.admin.shipment.internal.resource.v1_0.ShipmentResourceImpl;
import com.liferay.headless.commerce.admin.shipment.internal.resource.v1_0.ShippingAddressResourceImpl;
import com.liferay.headless.commerce.admin.shipment.resource.v1_0.ShipmentItemResource;
import com.liferay.headless.commerce.admin.shipment.resource.v1_0.ShipmentResource;
import com.liferay.headless.commerce.admin.shipment.resource.v1_0.ShippingAddressResource;
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
		Mutation.setShipmentResourceComponentServiceObjects(
			_shipmentResourceComponentServiceObjects);
		Mutation.setShipmentItemResourceComponentServiceObjects(
			_shipmentItemResourceComponentServiceObjects);
		Mutation.setShippingAddressResourceComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects);

		Query.setShipmentResourceComponentServiceObjects(
			_shipmentResourceComponentServiceObjects);
		Query.setShipmentItemResourceComponentServiceObjects(
			_shipmentItemResourceComponentServiceObjects);
		Query.setShippingAddressResourceComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Admin.Shipment";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-admin-shipment-graphql/v1_0";
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
			"mutation#createShipment",
			new ObjectValuePair<>(ShipmentResourceImpl.class, "postShipment"));
		_resourceMethodPairs.put(
			"mutation#createShipmentBatch",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class, "postShipmentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteShipmentByExternalReferenceCode",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class,
				"deleteShipmentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchShipmentByExternalReferenceCode",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class,
				"patchShipmentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateShipmentByExternalReferenceCode",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class,
				"putShipmentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#createShipmentByExternalReferenceCodeStatusDelivered",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class,
				"postShipmentByExternalReferenceCodeStatusDelivered"));
		_resourceMethodPairs.put(
			"mutation#createShipmentByExternalReferenceCodeStatusFinishProcessing",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class,
				"postShipmentByExternalReferenceCodeStatusFinishProcessing"));
		_resourceMethodPairs.put(
			"mutation#createShipmentByExternalReferenceCodeStatusShipped",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class,
				"postShipmentByExternalReferenceCodeStatusShipped"));
		_resourceMethodPairs.put(
			"mutation#deleteShipment",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class, "deleteShipment"));
		_resourceMethodPairs.put(
			"mutation#deleteShipmentBatch",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class, "deleteShipmentBatch"));
		_resourceMethodPairs.put(
			"mutation#patchShipment",
			new ObjectValuePair<>(ShipmentResourceImpl.class, "patchShipment"));
		_resourceMethodPairs.put(
			"mutation#createShipmentStatusDelivered",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class, "postShipmentStatusDelivered"));
		_resourceMethodPairs.put(
			"mutation#createShipmentStatusFinishProcessing",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class,
				"postShipmentStatusFinishProcessing"));
		_resourceMethodPairs.put(
			"mutation#createShipmentStatusShipped",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class, "postShipmentStatusShipped"));
		_resourceMethodPairs.put(
			"mutation#deleteShipmentItemByExternalReferenceCode",
			new ObjectValuePair<>(
				ShipmentItemResourceImpl.class,
				"deleteShipmentItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchShipmentItemByExternalReferenceCode",
			new ObjectValuePair<>(
				ShipmentItemResourceImpl.class,
				"patchShipmentItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteShipmentItem",
			new ObjectValuePair<>(
				ShipmentItemResourceImpl.class, "deleteShipmentItem"));
		_resourceMethodPairs.put(
			"mutation#deleteShipmentItemBatch",
			new ObjectValuePair<>(
				ShipmentItemResourceImpl.class, "deleteShipmentItemBatch"));
		_resourceMethodPairs.put(
			"mutation#patchShipmentItem",
			new ObjectValuePair<>(
				ShipmentItemResourceImpl.class, "patchShipmentItem"));
		_resourceMethodPairs.put(
			"mutation#updateShipmentByExternalReferenceCodeItem",
			new ObjectValuePair<>(
				ShipmentItemResourceImpl.class,
				"putShipmentByExternalReferenceCodeItem"));
		_resourceMethodPairs.put(
			"mutation#createShipmentItem",
			new ObjectValuePair<>(
				ShipmentItemResourceImpl.class, "postShipmentItem"));
		_resourceMethodPairs.put(
			"mutation#patchShipmentByExternalReferenceCodeShippingAddress",
			new ObjectValuePair<>(
				ShippingAddressResourceImpl.class,
				"patchShipmentByExternalReferenceCodeShippingAddress"));
		_resourceMethodPairs.put(
			"mutation#patchShipmentShippingAddress",
			new ObjectValuePair<>(
				ShippingAddressResourceImpl.class,
				"patchShipmentShippingAddress"));
		_resourceMethodPairs.put(
			"query#shipments",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class, "getShipmentsPage"));
		_resourceMethodPairs.put(
			"query#shipmentByExternalReferenceCode",
			new ObjectValuePair<>(
				ShipmentResourceImpl.class,
				"getShipmentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#shipment",
			new ObjectValuePair<>(ShipmentResourceImpl.class, "getShipment"));
		_resourceMethodPairs.put(
			"query#shipmentByExternalReferenceCodeItem",
			new ObjectValuePair<>(
				ShipmentItemResourceImpl.class,
				"getShipmentByExternalReferenceCodeItem"));
		_resourceMethodPairs.put(
			"query#shipmentItem",
			new ObjectValuePair<>(
				ShipmentItemResourceImpl.class, "getShipmentItem"));
		_resourceMethodPairs.put(
			"query#shipmentByExternalReferenceCodeItems",
			new ObjectValuePair<>(
				ShipmentItemResourceImpl.class,
				"getShipmentByExternalReferenceCodeItemsPage"));
		_resourceMethodPairs.put(
			"query#shipmentItems",
			new ObjectValuePair<>(
				ShipmentItemResourceImpl.class, "getShipmentItemsPage"));
		_resourceMethodPairs.put(
			"query#shipmentByExternalReferenceCodeShippingAddress",
			new ObjectValuePair<>(
				ShippingAddressResourceImpl.class,
				"getShipmentByExternalReferenceCodeShippingAddress"));
		_resourceMethodPairs.put(
			"query#shipmentShippingAddress",
			new ObjectValuePair<>(
				ShippingAddressResourceImpl.class,
				"getShipmentShippingAddress"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ShipmentResource>
		_shipmentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ShipmentItemResource>
		_shipmentItemResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ShippingAddressResource>
		_shippingAddressResourceComponentServiceObjects;

}