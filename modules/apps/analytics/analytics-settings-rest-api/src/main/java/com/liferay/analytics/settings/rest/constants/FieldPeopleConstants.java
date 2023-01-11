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

	public static final String[] FIELD_CONTACT_DEFAULTS = {
		"birthday", "classNameId", "classPK", "contactId", "createDate",
		"emailAddress", "employeeNumber", "employeeStatusId", "facebookSn",
		"hoursOfOperation", "jabberSn", "jobClass", "male", "modifiedDate",
		"parentContactId", "skypeSn", "smsSn", "twitterSn", "userName"
	};

	public static final String[] FIELD_CONTACT_EXAMPLES = {
		"31st Oct 2008", "12345", "12345", "12345", "31st Oct 2008",
		"johndoe@example.com", "12346-A", "0", "12345", "9:00 AM - 5:00 PM",
		"johndoe", "Manager", "True", "31st Oct 2008", "12345", "12345",
		"johndoe", "johndoe", "12345", "@johndoe", "John User"
	};

	public static final String[] FIELD_CONTACT_NAMES = {
		"birthday", "classNameId", "classPK", "contactId", "createDate",
		"emailAddress", "employeeNumber", "employeeStatusId", "facebookSn",
		"hoursOfOperation", "jabberSn", "jobClass", "male", "modifiedDate",
		"parentContactId", "prefixListTypeId", "skypeSn", "smsSn",
		"suffixListTypeId", "twitterSn", "userName"
	};

	public static final String[] FIELD_CONTACT_REQUIRED_NAMES = {
		"classNameId", "classPK", "contactId", "createDate", "emailAddress",
		"modifiedDate"
	};

	public static final String[] FIELD_CONTACT_TYPES = {
		"Date", "Long", "Long", "Long", "Date", "String", "String", "Long",
		"String", "String", "String", "String", "Boolean", "Date", "Long",
		"Long", "String", "String", "Long", "String", "String"
	};

	public static final String[] FIELD_USER_DEFAULTS = {
		"agreedToTermsOfUse", "companyId", "contactId", "createDate",
		"defaultUser", "emailAddress", "emailAddressVerified", "firstName",
		"jobTitle", "languageId", "lastName", "middleName", "modifiedDate",
		"status", "timeZoneId", "userId", "uuid"
	};

	public static final String[] FIELD_USER_EXAMPLES = {
		"True", "lorem ipsum", "12345", "12345", "31st Oct 2008", "True",
		"test@liferay.com", "True", "external12345", "12345", "John", "12345",
		"Hello John!", "Manager", "12345", "Doe", "12345", "Michael",
		"31st Oct 2008", "12345", "12345", "johndoe", "0", "12345", "12345",
		"asd23-erwer34-..."
	};

	public static final String[] FIELD_USER_NAMES = {
		"agreedToTermsOfUse", "comments", "companyId", "contactId",
		"createDate", "defaultUser", "emailAddress", "emailAddressVerified",
		"externalReferenceCode", "facebookId", "firstName", "googleUserId",
		"greeting", "jobTitle", "languageId", "lastName", "ldapServerId",
		"middleName", "modifiedDate", "openId", "portraitId", "screenName",
		"status", "timeZoneId", "userId", "uuid"
	};

	public static final String[] FIELD_USER_REQUIRED_NAMES = {
		"contactId", "createDate", "emailAddress", "modifiedDate", "userId",
		"uuid"
	};

	public static final String[] FIELD_USER_TYPES = {
		"Boolean", "String", "Long", "Long", "Date", "Boolean", "String",
		"Boolean", "String", "Long", "String", "String", "String", "String",
		"String", "String", "Long", "String", "Date", "String", "Long",
		"String", "Integer", "Long", "Long", "String"
	};

}