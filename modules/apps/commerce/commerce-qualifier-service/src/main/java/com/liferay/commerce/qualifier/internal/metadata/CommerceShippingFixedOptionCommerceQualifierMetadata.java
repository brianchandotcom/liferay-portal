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

import com.liferay.commerce.qualifier.configuration.CommerceShippingFixedOptionCommerceQualifierConfiguration;
import com.liferay.commerce.qualifier.metadata.BaseCommerceQualifierMetadata;
import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadata;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionTable;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
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
	configurationPid = "com.liferay.commerce.qualifier.configuration.CommerceShippingFixedOptionCommerceQualifierConfiguration",
	enabled = false, immediate = true,
	service = {CommerceQualifierMetadata.class, ModelListener.class}
)
public class CommerceShippingFixedOptionCommerceQualifierMetadata
	extends BaseCommerceQualifierMetadata<CommerceShippingFixedOption> {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return CommerceShippingFixedOptionCommerceQualifierConfiguration.class;
	}

	@Override
	public String getKey() {
		return "shipping-fixed-option";
	}

	@Override
	public Column<?, String> getKeywordsColumn() {
		return CommerceShippingFixedOptionTable.INSTANCE.name;
	}

	@Override
	public Class<CommerceShippingFixedOption> getModelClass() {
		return CommerceShippingFixedOption.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceShippingFixedOption.class.getName();
	}

	@Override
	public ModelResourcePermission<CommerceShippingFixedOption>
		getModelResourcePermission() {

		return null;
	}

	@Override
	public PersistedModelLocalService getPersistedModelLocalService() {
		return _commerceShippingFixedOptionLocalService;
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return CommerceShippingFixedOptionTable.INSTANCE.
			commerceShippingFixedOptionId;
	}

	@Override
	public Table getTable() {
		return CommerceShippingFixedOptionTable.INSTANCE;
	}

	@Override
	protected OrderByExpression[] getAdditionalOrderByExpressions(
		Map<String, ?> targetAttributes) {

		return new OrderByExpression[] {
			CommerceShippingFixedOptionTable.INSTANCE.priority.descending()
		};
	}

	@Reference
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

}