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

package com.liferay.commerce.qualifier.internal.metadata;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseTable;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.qualifier.configuration.CommerceInventoryWarehouseCommerceQualifierConfiguration;
import com.liferay.commerce.qualifier.metadata.BaseCommerceQualifierMetadata;
import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadata;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.PersistedModelLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	configurationPid = "com.liferay.commerce.qualifier.configuration.CommerceInventoryWarehouseCommerceQualifierConfiguration",
	enabled = false, immediate = true,
	service = {CommerceQualifierMetadata.class, ModelListener.class}
)
public class CommerceInventoryWarehouseCommerceQualifierMetadata
	extends BaseCommerceQualifierMetadata<CommerceInventoryWarehouse> {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return CommerceInventoryWarehouseCommerceQualifierConfiguration.class;
	}

	@Override
	public String getKey() {
		return "inventory-warehouse";
	}

	@Override
	public Column<?, String> getKeywordsColumn() {
		return CommerceInventoryWarehouseTable.INSTANCE.name;
	}

	@Override
	public Class<CommerceInventoryWarehouse> getModelClass() {
		return CommerceInventoryWarehouse.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceInventoryWarehouse.class.getName();
	}

	@Override
	public ModelResourcePermission<CommerceInventoryWarehouse>
		getModelResourcePermission() {

		return _commerceInventoryWarehouseModelResourcePermission;
	}

	@Override
	public PersistedModelLocalService getPersistedModelLocalService() {
		return _commerceInventoryWarehouseLocalService;
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return CommerceInventoryWarehouseTable.INSTANCE.
			commerceInventoryWarehouseId;
	}

	@Override
	public Table getTable() {
		return CommerceInventoryWarehouseTable.INSTANCE;
	}

	@Override
	protected OrderByExpression[] getAdditionalOrderByExpressions(
		Map<String, ?> targetAttributes) {

		return new OrderByExpression[] {
			CommerceInventoryWarehouseTable.INSTANCE.
				commerceInventoryWarehouseId.descending()
		};
	}

	@Reference
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.inventory.model.CommerceInventoryWarehouse)"
	)
	private ModelResourcePermission<CommerceInventoryWarehouse>
		_commerceInventoryWarehouseModelResourcePermission;

}