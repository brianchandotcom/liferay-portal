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

package com.liferay.fragment.internal.upgrade.v2_5_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class FragmentEntryLinkTable {

	public static final String TABLE_NAME = "FragmentEntryLink";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"fragmentEntryLinkId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"originalFragmentEntryLinkId", Types.BIGINT},
		{"fragmentEntryId", Types.BIGINT},
		{"segmentsExperienceId", Types.BIGINT}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"plid", Types.BIGINT}, {"css", Types.CLOB},
		{"html", Types.CLOB}, {"js", Types.CLOB}, {"configuration", Types.CLOB},
		{"editableValues", Types.CLOB}, {"namespace", Types.VARCHAR},
		{"position", Types.INTEGER}, {"rendererKey", Types.VARCHAR},
		{"lastPropagationDate", Types.TIMESTAMP},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("fragmentEntryLinkId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("originalFragmentEntryLinkId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("fragmentEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("segmentsExperienceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);

TABLE_COLUMNS_MAP.put("css", Types.CLOB);

TABLE_COLUMNS_MAP.put("html", Types.CLOB);

TABLE_COLUMNS_MAP.put("js", Types.CLOB);

TABLE_COLUMNS_MAP.put("configuration", Types.CLOB);

TABLE_COLUMNS_MAP.put("editableValues", Types.CLOB);

TABLE_COLUMNS_MAP.put("namespace", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("position", Types.INTEGER);

TABLE_COLUMNS_MAP.put("rendererKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPropagationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table FragmentEntryLink (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,fragmentEntryLinkId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,originalFragmentEntryLinkId LONG,fragmentEntryId LONG,segmentsExperienceId LONG,classNameId LONG,classPK LONG,plid LONG,css TEXT null,html TEXT null,js TEXT null,configuration TEXT null,editableValues TEXT null,namespace VARCHAR(75) null,position INTEGER,rendererKey VARCHAR(200) null,lastPropagationDate DATE null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table FragmentEntryLink";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_F3A29B2B on FragmentEntryLink (fragmentEntryId)",
		"create index IX_2FB5437D on FragmentEntryLink (groupId, classNameId, classPK)",
		"create index IX_4A9E751A on FragmentEntryLink (groupId, fragmentEntryId, classNameId, classPK)",
		"create index IX_1E535B10 on FragmentEntryLink (groupId, fragmentEntryId, plid)",
		"create index IX_24214F73 on FragmentEntryLink (groupId, plid)",
		"create index IX_EB688B56 on FragmentEntryLink (groupId, segmentsExperienceId, classNameId, classPK)",
		"create index IX_F929694C on FragmentEntryLink (groupId, segmentsExperienceId, plid)",
		"create index IX_51698F4A on FragmentEntryLink (rendererKey[$COLUMN_LENGTH:200$])",
		"create index IX_9266C536 on FragmentEntryLink (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_AA2B2138 on FragmentEntryLink (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}