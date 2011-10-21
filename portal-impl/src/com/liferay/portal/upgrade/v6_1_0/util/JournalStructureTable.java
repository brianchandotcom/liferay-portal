/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class JournalStructureTable {

	public static final String TABLE_NAME = "JournalStructure";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"id_", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"structureId", Types.VARCHAR},
		{"parentStructureId", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"xsd", Types.CLOB}
	};

	public static final String TABLE_SQL_CREATE = "create table JournalStructure (uuid_ VARCHAR(75) null,id_ LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,structureId VARCHAR(75) null,parentStructureId VARCHAR(75) null,name STRING null,description STRING null,xsd TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table JournalStructure";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_B97F5608 on JournalStructure (groupId)",
		"create index IX_CA0BD48C on JournalStructure (groupId, parentStructureId)",
		"create unique index IX_AB6E9996 on JournalStructure (groupId, structureId)",
		"create index IX_8831E4FC on JournalStructure (structureId)",
		"create index IX_6702CA92 on JournalStructure (uuid_)",
		"create unique index IX_42E86E58 on JournalStructure (uuid_, groupId)"
	};

}