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

import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.commerce.qualifier.configuration.AddressCommerceQualifierConfiguration;
import com.liferay.commerce.qualifier.metadata.BaseCommerceQualifierMetadata;
import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadata;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.AddressTable;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	configurationPid = "com.liferay.commerce.qualifier.configuration.AddressCommerceQualifierConfiguration",
	enabled = false, immediate = true,
	service = {CommerceQualifierMetadata.class, ModelListener.class}
)
public class CommerceBillingAddressCommerceQualifierMetadata
	extends BaseCommerceQualifierMetadata<Address> {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return AddressCommerceQualifierConfiguration.class;
	}

	@Override
	public Predicate getFilterPredicate() {
		ListType accountEntryAddressTypeBillingListType =
			_listTypeLocalService.getListType(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING,
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);

		ListType accountEntryAddressTypeBillingAndShippingListType =
			_listTypeLocalService.getListType(
				AccountListTypeConstants.
					ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING,
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);

		return AddressTable.INSTANCE.typeId.in(
			new Long[] {
				accountEntryAddressTypeBillingListType.getListTypeId(),
				accountEntryAddressTypeBillingAndShippingListType.
					getListTypeId()
			});
	}

	@Override
	public String getKey() {
		return "billing-address";
	}

	@Override
	public Column<?, String> getKeywordsColumn() {
		return AddressTable.INSTANCE.name;
	}

	@Override
	public Class<Address> getModelClass() {
		return Address.class;
	}

	@Override
	public String getModelClassName() {
		return Address.class.getName();
	}

	@Override
	public ModelResourcePermission<Address> getModelResourcePermission() {
		return null;
	}

	@Override
	public PersistedModelLocalService getPersistedModelLocalService() {
		return _addressLocalService;
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return AddressTable.INSTANCE.addressId;
	}

	@Override
	public Table getTable() {
		return AddressTable.INSTANCE;
	}

	@Override
	protected OrderByExpression[] getAdditionalOrderByExpressions(
		Map<String, ?> targetAttributes) {

		return new OrderByExpression[] {
			AddressTable.INSTANCE.addressId.descending()
		};
	}

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

}