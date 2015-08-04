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

package com.liferay.dynamic.data.mapping.upgrade.v1_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DDMStructureTable {

	public static final String TABLE_NAME = "DDMStructure";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"structureId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"parentStructureId", Types.BIGINT},
		{"classNameId", Types.BIGINT},
		{"structureKey", Types.VARCHAR},
		{"version", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"definition", Types.CLOB},
		{"storageType", Types.VARCHAR},
		{"type_", Types.INTEGER}
	};

	public static final String TABLE_SQL_CREATE = "create table DDMStructure (uuid_ VARCHAR(75) null,structureId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentStructureId LONG,classNameId LONG,structureKey VARCHAR(75) null,version VARCHAR(75) null,name STRING null,description STRING null,definition TEXT null,storageType VARCHAR(75) null,type_ INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table DDMStructure";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_31817A62 on DDMStructure (classNameId)",
		"create index IX_4FBAC092 on DDMStructure (companyId, classNameId)",
		"create unique index IX_C8785130 on DDMStructure (groupId, classNameId, structureKey)",
		"create index IX_43395316 on DDMStructure (groupId, parentStructureId)",
		"create index IX_657899A8 on DDMStructure (parentStructureId)",
		"create index IX_20FDE04C on DDMStructure (structureKey)",
		"create index IX_F9FB8D60 on DDMStructure (uuid_, companyId)",
		"create unique index IX_85C7EBE2 on DDMStructure (uuid_, groupId)"
	};

}