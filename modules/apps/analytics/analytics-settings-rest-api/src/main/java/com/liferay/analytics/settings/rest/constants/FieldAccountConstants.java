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

	public static final String[] FIELD_ACCOUNT_DEFAULTS = {
		"accountEntryId", "companyId", "createDate", "modifiedDate",
		"defaultCPaymentMethodKey", "parentAccountEntryId", "description",
		"domains", "emailAddress", "logoId", "name", "restrictMembership",
		"taxExemptionCode", "taxIdNumber", "type_", "status"
	};

	public static final String[] FIELD_ACCOUNT_EXAMPLES = {
		"12345", "12345", "31st Oct 2008", "cash", "Gold Account",
		"www.liferay.com", "test@liferay.com", "12346-A", "12345",
		"31st Oct 2008", "Gold", "12345", "True", "0", "ee", "23456",
		"business", "asd-yrty"
	};

	public static final String[] FIELD_ACCOUNT_NAMES = {
		"accountEntryId", "companyId", "createDate", "defaultCPaymentMethodKey",
		"description", "domains", "emailAddress", "externalReferenceCode",
		"logoId", "modifiedDate", "name", "parentAccountEntryId",
		"restrictMembership", "status", "taxExemptionCode", "taxIdNumber",
		"type_", "uuid_"
	};

	public static final String[] FIELD_ACCOUNT_REQUIRED_NAMES = {
		"accountEntryId", "emailAddress", "name"
	};

	public static final String[] FIELD_ACCOUNT_TYPES = {
		"Long", "Long", "StringDate", "String", "String", "String", "String",
		"String", "Long", "StringDate", "String", "Long", "Boolean", "Integer",
		"String", "String", "String", "String"
	};

}