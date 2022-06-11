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

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceCurrencyTable;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.qualifier.configuration.CommerceCurrencyCommerceQualifierConfiguration;
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
	configurationPid = "com.liferay.commerce.qualifier.configuration.CommerceCurrencyCommerceQualifierConfiguration",
	enabled = false, immediate = true,
	service = {CommerceQualifierMetadata.class, ModelListener.class}
)
public class CommerceCurrencyCommerceQualifierMetadata
	extends BaseCommerceQualifierMetadata<CommerceCurrency> {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return CommerceCurrencyCommerceQualifierConfiguration.class;
	}

	@Override
	public String getKey() {
		return "currency";
	}

	@Override
	public Column<?, String> getKeywordsColumn() {
		return CommerceCurrencyTable.INSTANCE.name;
	}

	@Override
	public Class<CommerceCurrency> getModelClass() {
		return CommerceCurrency.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceCurrency.class.getName();
	}

	@Override
	public ModelResourcePermission<CommerceCurrency>
		getModelResourcePermission() {

		return null;
	}

	@Override
	public PersistedModelLocalService getPersistedModelLocalService() {
		return _commerceCurrencyLocalService;
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return CommerceCurrencyTable.INSTANCE.commerceCurrencyId;
	}

	@Override
	public Table getTable() {
		return CommerceCurrencyTable.INSTANCE;
	}

	@Override
	protected OrderByExpression[] getAdditionalOrderByExpressions(
		Map<String, ?> targetAttributes) {

		return new OrderByExpression[] {
			CommerceCurrencyTable.INSTANCE.priority.descending()
		};
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

}