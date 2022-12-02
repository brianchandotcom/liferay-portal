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
public class FieldPeopleConstants {

	public static final String[] FIELD_CONTACT_EXAMPLES = {
		"31st Oct 2008", "12345", "12345", "12346-A", "0", "12345",
		"9:00 AM - 5:00 PM", "johndoe", "Manager", "True", "12345", "12345",
		"johndoe", "johndoe", "12345", "@johndoe"
	};

	public static final String[] FIELD_CONTACT_NAMES = {
		"birthday", "classNameId", "classPK", "employeeNumber",
		"employeeStatusId", "facebookSn", "hoursOfOperation", "jabberSn",
		"jobClass", "male", "parentContactId", "prefixListTypeId", "skypeSn",
		"smsSn", "suffixListTypeId", "twitterSn"
	};

	public static final String[] FIELD_CONTACT_REQUIRED_NAMES = {"classPK"};

	public static final String[] FIELD_CONTACT_TYPES = {
		"Date", "Long", "Long", "String", "String", "String", "String",
		"String", "String", "Boolean", "Long", "Long", "String", "String",
		"Long", "String"
	};

	public static final String[] FIELD_USER_EXAMPLES = {
		"True", "lorem ipsum", "12345", "12345", "31st Oct 2008", "True",
		"test@liferay.com", "True", "key=value,...", "external12345", "12345",
		"John", "12345", "Hello John!", "Manager", "12345", "Doe", "12345",
		"12354,34567,...", "Michael", "31st Oct 2008", "12345", "12345",
		"johndoe", "0", "12345", "12345", "asd23-erwer34-..."
	};

	public static final String[] FIELD_USER_NAMES = {
		"agreedToTermsOfUse", "comments", "companyId", "contactId",
		"createDate", "defaultUser", "emailAddress", "emailAddressVerified",
		"expando", "externalReferenceCode", "facebookId", "firstName",
		"googleUserId", "greeting", "jobTitle", "languageId", "lastName",
		"ldapServerId", "membership", "middleName", "modifiedDate", "openId",
		"portraitId", "screenName", "status", "timeZoneId", "uuid"
	};

	public static final String[] FIELD_USER_REQUIRED_NAMES = {
		"contactId", "createDate", "emailAddress", "modifiedDate", "userId",
		"uuid"
	};

	public static final String[] FIELD_USER_TYPES = {
		"Boolean", "String", "Long", "Long", "Date", "Boolean", "String",
		"Boolean", "String", "String", "Long", "String", "String", "String",
		"String", "String", "String", "Long", "String", "String", "Date",
		"String", "Long", "String", "Integer", "Long", "Long", "String"
	};

}