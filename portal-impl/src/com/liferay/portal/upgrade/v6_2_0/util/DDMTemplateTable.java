/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DDMTemplateTable {

	public static final String TABLE_NAME = "DDMTemplate";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"templateId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"type_", Types.VARCHAR},
		{"mode_", Types.VARCHAR},
		{"language", Types.VARCHAR},
		{"script", Types.CLOB}
	};

	public static final String TABLE_SQL_CREATE = "create table DDMTemplate (uuid_ VARCHAR(75) null,templateId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,name STRING null,description STRING null,type_ VARCHAR(75) null,mode_ VARCHAR(75) null,language VARCHAR(75) null,script TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table DDMTemplate";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_B6356F93 on DDMTemplate (classNameId, classPK, type_)",
		"create index IX_2E1BAFD9 on DDMTemplate (classNameId, classPK, type_, mode_)",
		"create index IX_32F83D16 on DDMTemplate (classPK)",
		"create index IX_304767FF on DDMTemplate (classPK, type_)",
		"create index IX_6561A5ED on DDMTemplate (classPK, type_, mode_)",
		"create index IX_DB24DDDD on DDMTemplate (groupId)",
		"create index IX_824ADC72 on DDMTemplate (groupId, classNameId, classPK)",
		"create index IX_33BEF579 on DDMTemplate (language)",
		"create index IX_C9757A51 on DDMTemplate (structureId)",
		"create index IX_5BC0E264 on DDMTemplate (structureId, type_)",
		"create index IX_5B019FE8 on DDMTemplate (structureId, type_, mode_)",
		"create index IX_C4F283C8 on DDMTemplate (type_)",
		"create index IX_F2A243A7 on DDMTemplate (uuid_)",
		"create unique index IX_1AA75CE3 on DDMTemplate (uuid_, groupId)"
	};

}