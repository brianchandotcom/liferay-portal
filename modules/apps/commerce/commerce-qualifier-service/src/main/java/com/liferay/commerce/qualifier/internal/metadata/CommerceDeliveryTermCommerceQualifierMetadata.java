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

import com.liferay.commerce.qualifier.configuration.CommerceTermEntryCommerceQualifierConfiguration;
import com.liferay.commerce.qualifier.metadata.BaseCommerceQualifierMetadata;
import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadata;
import com.liferay.commerce.term.constants.CommerceTermEntryConstants;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.model.CommerceTermEntryTable;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
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
	configurationPid = "com.liferay.commerce.qualifier.configuration.CommerceTermEntryCommerceQualifierConfiguration",
	enabled = false, immediate = true,
	service = {CommerceQualifierMetadata.class, ModelListener.class}
)
public class CommerceDeliveryTermCommerceQualifierMetadata
	extends BaseCommerceQualifierMetadata<CommerceTermEntry> {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return CommerceTermEntryCommerceQualifierConfiguration.class;
	}

	@Override
	public Predicate getFilterPredicate() {
		return CommerceTermEntryTable.INSTANCE.type.eq(
			CommerceTermEntryConstants.TYPE_DELIVERY_TERMS);
	}

	@Override
	public String getKey() {
		return "delivery-term";
	}

	@Override
	public Column<?, String> getKeywordsColumn() {
		return CommerceTermEntryTable.INSTANCE.name;
	}

	@Override
	public Class<CommerceTermEntry> getModelClass() {
		return CommerceTermEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceTermEntry.class.getName();
	}

	@Override
	public ModelResourcePermission<CommerceTermEntry>
		getModelResourcePermission() {

		return _commerceTermEntryModelResourcePermission;
	}

	@Override
	public PersistedModelLocalService getPersistedModelLocalService() {
		return _commerceTermEntryLocalService;
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return CommerceTermEntryTable.INSTANCE.commerceTermEntryId;
	}

	@Override
	public Table getTable() {
		return CommerceTermEntryTable.INSTANCE;
	}

	@Override
	protected OrderByExpression[] getAdditionalOrderByExpressions(
		Map<String, ?> targetAttributes) {

		return new OrderByExpression[] {
			CommerceTermEntryTable.INSTANCE.commerceTermEntryId.descending()
		};
	}

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.term.model.CommerceTermEntry)"
	)
	private ModelResourcePermission<CommerceTermEntry>
		_commerceTermEntryModelResourcePermission;

}