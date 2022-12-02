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

package com.liferay.analytics.settings.rest.constants;

/**
 * @author Riccardo Ferrari
 */
public class FieldAccountConstants {

	public static final String[] FIELD_ACCOUNT_EXAMPLES = {
		"asd-yrty", "12346-A", "12345", "12345", "12345", "johndoe",
		"31st Oct 2008", "31st Oct 2008", "12345", "cash", "12345", "12345",
		"Gold Account", "www.liferay.com", "test@liferay.com", "12345", "Gold",
		"True", "ee", "23456", "business", "0", "12345", "admin",
		"31st Oct 2008"
	};

	public static final String[] FIELD_ACCOUNT_NAMES = {
		"uuid_", "externalReferenceCode", "accountEntryId", "companyId",
		"userId", "userName", "createDate", "modifiedDate",
		"defaultBillingAddressId", "defaultCPaymentMethodKey",
		"defaultShippingAddressId", "parentAccountEntryId", "description",
		"domains", "emailAddress", "logoId", "name", "restrictMembership",
		"taxExemptionCode", "taxIdNumber", "type_", "status", "statusByUserId",
		"statusByUserName", "statusDate"
	};

	public static final String[] FIELD_ACCOUNT_REQUIRED_NAMES = {
		"accountEntryId", "emailAddress", "name"
	};

	public static final String[] FIELD_ACCOUNT_TYPES = {
		"String", "String", "Long", "Long", "Long", "String", "StringDate",
		"StringDate", "Long", "String", "Long", "Long", "String", "String",
		"String", "Long", "String", "Boolean", "String", "String", "String",
		"Integer", "Long", "String", "StringDate"
	};

}