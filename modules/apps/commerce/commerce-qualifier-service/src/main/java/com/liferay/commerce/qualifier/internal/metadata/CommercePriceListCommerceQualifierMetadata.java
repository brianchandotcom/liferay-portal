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

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListTable;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.qualifier.configuration.CommercePriceListCommerceQualifierConfiguration;
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
	configurationPid = "com.liferay.commerce.qualifier.configuration.CommercePriceListCommerceQualifierConfiguration",
	enabled = false, immediate = true,
	service = {CommerceQualifierMetadata.class, ModelListener.class}
)
public class CommercePriceListCommerceQualifierMetadata
	extends BaseCommerceQualifierMetadata<CommercePriceList> {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return CommercePriceListCommerceQualifierConfiguration.class;
	}

	@Override
	public String getKey() {
		return "price-list";
	}

	@Override
	public Column<?, String> getKeywordsColumn() {
		return CommercePriceListTable.INSTANCE.name;
	}

	@Override
	public Class<CommercePriceList> getModelClass() {
		return CommercePriceList.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceList.class.getName();
	}

	@Override
	public ModelResourcePermission<CommercePriceList>
		getModelResourcePermission() {

		return _commercePriceListModelResourcePermission;
	}

	@Override
	public PersistedModelLocalService getPersistedModelLocalService() {
		return _commercePriceListLocalService;
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return CommercePriceListTable.INSTANCE.commercePriceListId;
	}

	@Override
	public Table getTable() {
		return CommercePriceListTable.INSTANCE;
	}

	@Override
	protected OrderByExpression[] getAdditionalOrderByExpressions(
		Map<String, ?> targetAttributes) {

		return new OrderByExpression[] {
			CommercePriceListTable.INSTANCE.catalogBasePriceList.ascending(),
			CommercePriceListTable.INSTANCE.priority.descending()
		};
	}

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceList)"
	)
	private ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission;

}