/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.constants;

/**
 * @author Riccardo Ferrari
 */
public class FieldPeopleConstants {

	public static final String[] FIELD_CONTACT_DEFAULTS = {
		"birthday", "classPK", "contactId", "createDate", "emailAddress",
		"firstName", "jobTitle", "lastName", "modifiedDate"
	};

	public static final String[] FIELD_CONTACT_EXAMPLES = {
		"31st Oct 2008", "12345", "12345", "12345", "12345", "31st Oct 2008",
		"johndoe@example.com", "12346-A", "0", "12345", "John",
		"9:00 AM - 5:00 PM", "johndoe", "Manager", "Manager", "Doe", "True",
		"Vincent", "31st Oct 2008", "12345", "12345", "johndoe", "johndoe",
		"12345", "@johndoe", "John User"
	};

	public static final String[] FIELD_CONTACT_NAMES = {
		"birthday", "classNameId", "classPK", "companyId", "contactId",
		"createDate", "emailAddress", "employeeNumber", "employeeStatusId",
		"facebookSn", "firstName", "hoursOfOperation", "jabberSn", "jobClass",
		"jobTitle", "lastName", "male", "middleName", "modifiedDate",
		"parentContactId", "prefixListTypeId", "skypeSn", "smsSn",
		"suffixListTypeId", "twitterSn", "userName"
	};

	public static final String[] FIELD_CONTACT_REQUIRED_NAMES = {
		"classPK", "contactId", "createDate", "emailAddress", "modifiedDate"
	};

	public static final String[] FIELD_CONTACT_TYPES = {
		"Date", "Long", "Long", "Long", "Long", "Date", "String", "String",
		"Long", "String", "String", "String", "String", "String", "String",
		"String", "Boolean", "String", "Date", "Long", "Long", "String",
		"String", "Long", "String", "String"
	};

	public static final String[] FIELD_USER_DEFAULTS = {
		"createDate", "emailAddress", "firstName", "jobTitle", "lastName",
		"modifiedDate", "timeZoneId", "userId", "uuid"
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
		"createDate", "emailAddress", "emailAddressVerified",
		"externalReferenceCode", "facebookId", "firstName", "googleUserId",
		"greeting", "jobTitle", "languageId", "lastName", "ldapServerId",
		"middleName", "modifiedDate", "openId", "portraitId", "screenName",
		"status", "timeZoneId", "userId", "uuid"
	};

	public static final String[] FIELD_USER_REQUIRED_NAMES = {
		"createDate", "emailAddress", "modifiedDate", "userId", "uuid"
	};

	public static final String[] FIELD_USER_TYPES = {
		"Boolean", "String", "Long", "Long", "Date", "String", "Boolean",
		"String", "Long", "String", "String", "String", "String", "String",
		"String", "Long", "String", "Date", "String", "Long", "String",
		"Integer", "Long", "Long", "String"
	};

}