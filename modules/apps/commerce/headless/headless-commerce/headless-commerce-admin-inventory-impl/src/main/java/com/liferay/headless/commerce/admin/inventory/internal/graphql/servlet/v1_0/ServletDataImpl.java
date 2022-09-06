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

package com.liferay.headless.commerce.admin.inventory.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.admin.inventory.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.admin.inventory.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0.ChannelResourceImpl;
import com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0.OrderTypeResourceImpl;
import com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0.ReplenishmentItemResourceImpl;
import com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0.WarehouseChannelResourceImpl;
import com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0.WarehouseItemResourceImpl;
import com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0.WarehouseOrderTypeResourceImpl;
import com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0.WarehouseResourceImpl;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.OrderTypeResource;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.ReplenishmentItemResource;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.WarehouseChannelResource;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.WarehouseItemResource;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.WarehouseOrderTypeResource;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.WarehouseResource;
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
		Mutation.setReplenishmentItemResourceComponentServiceObjects(
			_replenishmentItemResourceComponentServiceObjects);
		Mutation.setWarehouseResourceComponentServiceObjects(
			_warehouseResourceComponentServiceObjects);
		Mutation.setWarehouseChannelResourceComponentServiceObjects(
			_warehouseChannelResourceComponentServiceObjects);
		Mutation.setWarehouseItemResourceComponentServiceObjects(
			_warehouseItemResourceComponentServiceObjects);
		Mutation.setWarehouseOrderTypeResourceComponentServiceObjects(
			_warehouseOrderTypeResourceComponentServiceObjects);

		Query.setChannelResourceComponentServiceObjects(
			_channelResourceComponentServiceObjects);
		Query.setOrderTypeResourceComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects);
		Query.setReplenishmentItemResourceComponentServiceObjects(
			_replenishmentItemResourceComponentServiceObjects);
		Query.setWarehouseResourceComponentServiceObjects(
			_warehouseResourceComponentServiceObjects);
		Query.setWarehouseChannelResourceComponentServiceObjects(
			_warehouseChannelResourceComponentServiceObjects);
		Query.setWarehouseItemResourceComponentServiceObjects(
			_warehouseItemResourceComponentServiceObjects);
		Query.setWarehouseOrderTypeResourceComponentServiceObjects(
			_warehouseOrderTypeResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Admin.Inventory";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-admin-inventory-graphql/v1_0";
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
			"mutation#deleteReplenishmentItemByExternalReferenceCode",
			new ObjectValuePair<>(
				ReplenishmentItemResourceImpl.class,
				"deleteReplenishmentItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchReplenishmentItemByExternalReferenceCode",
			new ObjectValuePair<>(
				ReplenishmentItemResourceImpl.class,
				"patchReplenishmentItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteReplenishmentItem",
			new ObjectValuePair<>(
				ReplenishmentItemResourceImpl.class,
				"deleteReplenishmentItem"));
		_resourceMethodPairs.put(
			"mutation#deleteReplenishmentItemBatch",
			new ObjectValuePair<>(
				ReplenishmentItemResourceImpl.class,
				"deleteReplenishmentItemBatch"));
		_resourceMethodPairs.put(
			"mutation#patchReplenishmentItem",
			new ObjectValuePair<>(
				ReplenishmentItemResourceImpl.class, "patchReplenishmentItem"));
		_resourceMethodPairs.put(
			"mutation#createReplenishmentItem",
			new ObjectValuePair<>(
				ReplenishmentItemResourceImpl.class, "postReplenishmentItem"));
		_resourceMethodPairs.put(
			"mutation#createReplenishmentItemBatch",
			new ObjectValuePair<>(
				ReplenishmentItemResourceImpl.class,
				"postReplenishmentItemBatch"));
		_resourceMethodPairs.put(
			"mutation#createWarehouse",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class, "postWarehouse"));
		_resourceMethodPairs.put(
			"mutation#createWarehouseBatch",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class, "postWarehouseBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteWarehouseByExternalReferenceCode",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class,
				"deleteWarehouseByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchWarehouseByExternalReferenceCode",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class,
				"patchWarehouseByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteWarehouseId",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class, "deleteWarehouseId"));
		_resourceMethodPairs.put(
			"mutation#patchWarehouseId",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class, "patchWarehouseId"));
		_resourceMethodPairs.put(
			"mutation#deleteWarehouseChannel",
			new ObjectValuePair<>(
				WarehouseChannelResourceImpl.class, "deleteWarehouseChannel"));
		_resourceMethodPairs.put(
			"mutation#deleteWarehouseChannelBatch",
			new ObjectValuePair<>(
				WarehouseChannelResourceImpl.class,
				"deleteWarehouseChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#createWarehouseByExternalReferenceCodeWarehouseChannel",
			new ObjectValuePair<>(
				WarehouseChannelResourceImpl.class,
				"postWarehouseByExternalReferenceCodeWarehouseChannel"));
		_resourceMethodPairs.put(
			"mutation#createWarehouseIdWarehouseChannel",
			new ObjectValuePair<>(
				WarehouseChannelResourceImpl.class,
				"postWarehouseIdWarehouseChannel"));
		_resourceMethodPairs.put(
			"mutation#createWarehouseIdWarehouseChannelBatch",
			new ObjectValuePair<>(
				WarehouseChannelResourceImpl.class,
				"postWarehouseIdWarehouseChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteWarehouseItemByExternalReferenceCode",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class,
				"deleteWarehouseItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchWarehouseItemByExternalReferenceCode",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class,
				"patchWarehouseItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#createWarehouseItemByExternalReferenceCode",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class,
				"postWarehouseItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteWarehouseItem",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class, "deleteWarehouseItem"));
		_resourceMethodPairs.put(
			"mutation#deleteWarehouseItemBatch",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class, "deleteWarehouseItemBatch"));
		_resourceMethodPairs.put(
			"mutation#patchWarehouseItem",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class, "patchWarehouseItem"));
		_resourceMethodPairs.put(
			"mutation#createWarehouseByExternalReferenceCodeWarehouseItem",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class,
				"postWarehouseByExternalReferenceCodeWarehouseItem"));
		_resourceMethodPairs.put(
			"mutation#createWarehouseIdWarehouseItem",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class,
				"postWarehouseIdWarehouseItem"));
		_resourceMethodPairs.put(
			"mutation#createWarehouseIdWarehouseItemBatch",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class,
				"postWarehouseIdWarehouseItemBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteWarehouseOrderType",
			new ObjectValuePair<>(
				WarehouseOrderTypeResourceImpl.class,
				"deleteWarehouseOrderType"));
		_resourceMethodPairs.put(
			"mutation#deleteWarehouseOrderTypeBatch",
			new ObjectValuePair<>(
				WarehouseOrderTypeResourceImpl.class,
				"deleteWarehouseOrderTypeBatch"));
		_resourceMethodPairs.put(
			"mutation#createWarehouseByExternalReferenceCodeWarehouseOrderType",
			new ObjectValuePair<>(
				WarehouseOrderTypeResourceImpl.class,
				"postWarehouseByExternalReferenceCodeWarehouseOrderType"));
		_resourceMethodPairs.put(
			"mutation#createWarehouseIdWarehouseOrderType",
			new ObjectValuePair<>(
				WarehouseOrderTypeResourceImpl.class,
				"postWarehouseIdWarehouseOrderType"));
		_resourceMethodPairs.put(
			"mutation#createWarehouseIdWarehouseOrderTypeBatch",
			new ObjectValuePair<>(
				WarehouseOrderTypeResourceImpl.class,
				"postWarehouseIdWarehouseOrderTypeBatch"));
		_resourceMethodPairs.put(
			"query#warehouseChannelChannel",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "getWarehouseChannelChannel"));
		_resourceMethodPairs.put(
			"query#warehouseOrderTypeOrderType",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class, "getWarehouseOrderTypeOrderType"));
		_resourceMethodPairs.put(
			"query#replenishmentItemByExternalReferenceCode",
			new ObjectValuePair<>(
				ReplenishmentItemResourceImpl.class,
				"getReplenishmentItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#replenishmentItem",
			new ObjectValuePair<>(
				ReplenishmentItemResourceImpl.class, "getReplenishmentItem"));
		_resourceMethodPairs.put(
			"query#replenishmentItems",
			new ObjectValuePair<>(
				ReplenishmentItemResourceImpl.class,
				"getReplenishmentItemsPage"));
		_resourceMethodPairs.put(
			"query#warehouseIdReplenishmentItems",
			new ObjectValuePair<>(
				ReplenishmentItemResourceImpl.class,
				"getWarehouseIdReplenishmentItemsPage"));
		_resourceMethodPairs.put(
			"query#warehouses",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class, "getWarehousesPage"));
		_resourceMethodPairs.put(
			"query#warehouseByExternalReferenceCode",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class,
				"getWarehouseByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#warehouseId",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class, "getWarehouseId"));
		_resourceMethodPairs.put(
			"query#warehouseByExternalReferenceCodeWarehouseChannels",
			new ObjectValuePair<>(
				WarehouseChannelResourceImpl.class,
				"getWarehouseByExternalReferenceCodeWarehouseChannelsPage"));
		_resourceMethodPairs.put(
			"query#warehouseIdWarehouseChannels",
			new ObjectValuePair<>(
				WarehouseChannelResourceImpl.class,
				"getWarehouseIdWarehouseChannelsPage"));
		_resourceMethodPairs.put(
			"query#warehouseItemByExternalReferenceCode",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class,
				"getWarehouseItemByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#warehouseItemsUpdated",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class,
				"getWarehouseItemsUpdatedPage"));
		_resourceMethodPairs.put(
			"query#warehouseItem",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class, "getWarehouseItem"));
		_resourceMethodPairs.put(
			"query#warehouseByExternalReferenceCodeWarehouseItems",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class,
				"getWarehouseByExternalReferenceCodeWarehouseItemsPage"));
		_resourceMethodPairs.put(
			"query#warehouseIdWarehouseItems",
			new ObjectValuePair<>(
				WarehouseItemResourceImpl.class,
				"getWarehouseIdWarehouseItemsPage"));
		_resourceMethodPairs.put(
			"query#warehouseByExternalReferenceCodeWarehouseOrderTypes",
			new ObjectValuePair<>(
				WarehouseOrderTypeResourceImpl.class,
				"getWarehouseByExternalReferenceCodeWarehouseOrderTypesPage"));
		_resourceMethodPairs.put(
			"query#warehouseIdWarehouseOrderTypes",
			new ObjectValuePair<>(
				WarehouseOrderTypeResourceImpl.class,
				"getWarehouseIdWarehouseOrderTypesPage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ReplenishmentItemResource>
		_replenishmentItemResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WarehouseResource>
		_warehouseResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WarehouseChannelResource>
		_warehouseChannelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WarehouseItemResource>
		_warehouseItemResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WarehouseOrderTypeResource>
		_warehouseOrderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderTypeResource>
		_orderTypeResourceComponentServiceObjects;

}