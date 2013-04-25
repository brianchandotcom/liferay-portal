/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
public class LayoutTable {

	public static final String TABLE_NAME = "Layout";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"plid", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"privateLayout", Types.BOOLEAN},
		{"layoutId", Types.BIGINT},
		{"parentLayoutId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"title", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"keywords", Types.VARCHAR},
		{"robots", Types.VARCHAR},
		{"type_", Types.VARCHAR},
		{"typeSettings", Types.CLOB},
		{"hidden_", Types.BOOLEAN},
		{"friendlyURL", Types.VARCHAR},
		{"iconImage", Types.BOOLEAN},
		{"iconImageId", Types.BIGINT},
		{"themeId", Types.VARCHAR},
		{"colorSchemeId", Types.VARCHAR},
		{"wapThemeId", Types.VARCHAR},
		{"wapColorSchemeId", Types.VARCHAR},
		{"css", Types.CLOB},
		{"priority", Types.INTEGER},
		{"layoutPrototypeUuid", Types.VARCHAR},
		{"layoutPrototypeLinkEnabled", Types.BOOLEAN},
		{"sourcePrototypeLayoutUuid", Types.VARCHAR}
	};

	public static final String TABLE_SQL_CREATE = "create table Layout (uuid_ VARCHAR(75) null,plid LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,privateLayout BOOLEAN,layoutId LONG,parentLayoutId LONG,name STRING null,title STRING null,description STRING null,keywords STRING null,robots STRING null,type_ VARCHAR(75) null,typeSettings TEXT null,hidden_ BOOLEAN,friendlyURL VARCHAR(255) null,iconImage BOOLEAN,iconImageId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,wapThemeId VARCHAR(75) null,wapColorSchemeId VARCHAR(75) null,css TEXT null,priority INTEGER,layoutPrototypeUuid VARCHAR(75) null,layoutPrototypeLinkEnabled BOOLEAN,sourcePrototypeLayoutUuid VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table Layout";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_C7FBC998 on Layout (companyId)",
		"create index IX_C099D61A on Layout (groupId)",
		"create index IX_705F5AA3 on Layout (groupId, privateLayout)",
		"create unique index IX_BC2C4231 on Layout (groupId, privateLayout, friendlyURL)",
		"create unique index IX_7162C27C on Layout (groupId, privateLayout, layoutId)",
		"create index IX_6DE88B06 on Layout (groupId, privateLayout, parentLayoutId)",
		"create index IX_8CE8C0D9 on Layout (groupId, privateLayout, sourcePrototypeLayoutUuid)",
		"create index IX_1A1B61D2 on Layout (groupId, privateLayout, type_)",
		"create index IX_23922F7D on Layout (iconImageId)",
		"create index IX_B529BFD3 on Layout (layoutPrototypeUuid)",
		"create index IX_39A18ECC on Layout (sourcePrototypeLayoutUuid)",
		"create index IX_D0822724 on Layout (uuid_)",
		"create index IX_2CE4BE84 on Layout (uuid_, companyId)",
		"create unique index IX_E118C537 on Layout (uuid_, groupId, privateLayout)"
	};

}