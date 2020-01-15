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

package com.liferay.site.navigation.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the SiteNavigationMenu.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SiteNavigationMenuTable extends Table<SiteNavigationMenuTable> {

	public static final SiteNavigationMenuTable INSTANCE =
		new SiteNavigationMenuTable();

	public final Column<SiteNavigationMenuTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<SiteNavigationMenuTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<SiteNavigationMenuTable, Long> siteNavigationMenuId =
		createColumn("siteNavigationMenuId", Long.class, Types.BIGINT);
	public final Column<SiteNavigationMenuTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<SiteNavigationMenuTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SiteNavigationMenuTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<SiteNavigationMenuTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<SiteNavigationMenuTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<SiteNavigationMenuTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<SiteNavigationMenuTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<SiteNavigationMenuTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER);
	public final Column<SiteNavigationMenuTable, Boolean> auto = createColumn(
		"auto_", Boolean.class, Types.BOOLEAN);
	public final Column<SiteNavigationMenuTable, Date> lastPublishDate =
		createColumn("lastPublishDate", Date.class, Types.TIMESTAMP);

	private SiteNavigationMenuTable() {
		super("SiteNavigationMenu", SiteNavigationMenuTable::new);
	}

}