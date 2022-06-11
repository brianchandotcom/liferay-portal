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

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceOrderTypeTable;
import com.liferay.commerce.qualifier.configuration.CommerceOrderTypeCommerceQualifierConfiguration;
import com.liferay.commerce.qualifier.metadata.BaseCommerceQualifierMetadata;
import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadata;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
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
	configurationPid = "com.liferay.commerce.qualifier.configuration.CommerceOrderTypeCommerceQualifierConfiguration",
	enabled = false, immediate = true,
	service = {CommerceQualifierMetadata.class, ModelListener.class}
)
public class CommerceOrderTypeCommerceQualifierMetadata
	extends BaseCommerceQualifierMetadata<CommerceOrderType> {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return CommerceOrderTypeCommerceQualifierConfiguration.class;
	}

	@Override
	public String getKey() {
		return "order-type";
	}

	@Override
	public Column<?, String> getKeywordsColumn() {
		return CommerceOrderTypeTable.INSTANCE.name;
	}

	@Override
	public Class<CommerceOrderType> getModelClass() {
		return CommerceOrderType.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceOrderType.class.getName();
	}

	@Override
	public ModelResourcePermission<CommerceOrderType>
		getModelResourcePermission() {

		return _commerceOrderTypeModelResourcePermission;
	}

	@Override
	public PersistedModelLocalService getPersistedModelLocalService() {
		return _commerceOrderTypeLocalService;
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return CommerceOrderTypeTable.INSTANCE.commerceOrderTypeId;
	}

	@Override
	public Table getTable() {
		return CommerceOrderTypeTable.INSTANCE;
	}

	@Override
	protected OrderByExpression[] getAdditionalOrderByExpressions(
		Map<String, ?> targetAttributes) {

		return new OrderByExpression[] {
			CommerceOrderTypeTable.INSTANCE.commerceOrderTypeId.descending()
		};
	}

	@Reference
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrderType)"
	)
	private ModelResourcePermission<CommerceOrderType>
		_commerceOrderTypeModelResourcePermission;

}