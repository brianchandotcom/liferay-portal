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

package com.liferay.portal.upgrade.v7_0_5.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ContactTable {

	public static final String TABLE_NAME = "Contact_";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"contactId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"accountId", Types.BIGINT},
		{"parentContactId", Types.BIGINT},
		{"emailAddress", Types.VARCHAR},
		{"firstName", Types.VARCHAR},
		{"middleName", Types.VARCHAR},
		{"lastName", Types.VARCHAR},
		{"prefixId", Types.BIGINT},
		{"suffixId", Types.BIGINT},
		{"male", Types.BOOLEAN},
		{"birthday", Types.TIMESTAMP},
		{"smsSn", Types.VARCHAR},
		{"facebookSn", Types.VARCHAR},
		{"jabberSn", Types.VARCHAR},
		{"skypeSn", Types.VARCHAR},
		{"twitterSn", Types.VARCHAR},
		{"employeeStatusId", Types.VARCHAR},
		{"employeeNumber", Types.VARCHAR},
		{"jobTitle", Types.VARCHAR},
		{"jobClass", Types.VARCHAR},
		{"hoursOfOperation", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("contactId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("accountId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentContactId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("emailAddress", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("firstName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("middleName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("prefixId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("suffixId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("male", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("birthday", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("smsSn", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("facebookSn", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("jabberSn", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("skypeSn", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("twitterSn", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("employeeStatusId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("employeeNumber", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("jobTitle", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("jobClass", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("hoursOfOperation", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table Contact_ (mvccVersion LONG default 0 not null,contactId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,accountId LONG,parentContactId LONG,emailAddress VARCHAR(254) null,firstName VARCHAR(75) null,middleName VARCHAR(75) null,lastName VARCHAR(75) null,prefixId LONG,suffixId LONG,male BOOLEAN,birthday DATE null,smsSn VARCHAR(75) null,facebookSn VARCHAR(75) null,jabberSn VARCHAR(75) null,skypeSn VARCHAR(75) null,twitterSn VARCHAR(75) null,employeeStatusId VARCHAR(75) null,employeeNumber VARCHAR(75) null,jobTitle VARCHAR(100) null,jobClass VARCHAR(75) null,hoursOfOperation VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table Contact_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_B8C28C53 on Contact_ (accountId)",
		"create index IX_791914FA on Contact_ (classNameId, classPK)",
		"create index IX_66D496A3 on Contact_ (companyId)"
	};

}