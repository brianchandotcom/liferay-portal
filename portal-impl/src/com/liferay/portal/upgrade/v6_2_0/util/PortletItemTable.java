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
public class PortletItemTable {

	public static final String TABLE_NAME = "PortletItem";

	public static final Object[][] TABLE_COLUMNS = {
		{"portletItemId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR},
		{"portletId", Types.VARCHAR},
		{"classNameId", Types.BIGINT}
	};

	public static final String TABLE_SQL_CREATE = "create table PortletItem (portletItemId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,portletId VARCHAR(200) null,classNameId LONG)";

	public static final String TABLE_SQL_DROP = "drop table PortletItem";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_96BDD537 on PortletItem (groupId, classNameId)",
		"create index IX_D699243F on PortletItem (groupId, name, portletId, classNameId)",
		"create index IX_2C61314E on PortletItem (groupId, portletId)",
		"create index IX_E922D6C0 on PortletItem (groupId, portletId, classNameId)",
		"create index IX_8E71167F on PortletItem (groupId, portletId, classNameId, name)",
		"create index IX_33B8CE8D on PortletItem (groupId, portletId, name)"
	};

}