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

import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryTable;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.qualifier.configuration.COREntryCommerceQualifierConfiguration;
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
	configurationPid = "com.liferay.commerce.qualifier.configuration.COREntryCommerceQualifierConfiguration",
	enabled = false, immediate = true,
	service = {CommerceQualifierMetadata.class, ModelListener.class}
)
public class CommerceOrderRuleCommerceQualifierMetadata
	extends BaseCommerceQualifierMetadata<COREntry> {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return COREntryCommerceQualifierConfiguration.class;
	}

	@Override
	public String getKey() {
		return "order-rule";
	}

	@Override
	public Column<?, String> getKeywordsColumn() {
		return COREntryTable.INSTANCE.name;
	}

	@Override
	public Class<COREntry> getModelClass() {
		return COREntry.class;
	}

	@Override
	public String getModelClassName() {
		return COREntry.class.getName();
	}

	@Override
	public ModelResourcePermission<COREntry> getModelResourcePermission() {
		return _commerceTermEntryModelResourcePermission;
	}

	@Override
	public PersistedModelLocalService getPersistedModelLocalService() {
		return _corEntryLocalService;
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return COREntryTable.INSTANCE.COREntryId;
	}

	@Override
	public Table getTable() {
		return COREntryTable.INSTANCE;
	}

	@Override
	protected OrderByExpression[] getAdditionalOrderByExpressions(
		Map<String, ?> targetAttributes) {

		return new OrderByExpression[] {
			COREntryTable.INSTANCE.COREntryId.descending()
		};
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.order.rule.model.COREntry)"
	)
	private ModelResourcePermission<COREntry>
		_commerceTermEntryModelResourcePermission;

	@Reference
	private COREntryLocalService _corEntryLocalService;

}