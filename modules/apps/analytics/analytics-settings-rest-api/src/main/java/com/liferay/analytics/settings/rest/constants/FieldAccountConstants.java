/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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