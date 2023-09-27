/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SiteNavigationMenu&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenu
 * @generated
 */
public class SiteNavigationMenuTable
	extends BaseTable<SiteNavigationMenuTable> {

	public static final SiteNavigationMenuTable INSTANCE =
		new SiteNavigationMenuTable();

	public final Column<SiteNavigationMenuTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SiteNavigationMenuTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SiteNavigationMenuTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SiteNavigationMenuTable, Long> siteNavigationMenuId =
		createColumn(
			"siteNavigationMenuId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SiteNavigationMenuTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SiteNavigationMenuTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SiteNavigationMenuTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SiteNavigationMenuTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SiteNavigationMenuTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SiteNavigationMenuTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SiteNavigationMenuTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SiteNavigationMenuTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SiteNavigationMenuTable, Boolean> auto = createColumn(
		"auto_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<SiteNavigationMenuTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private SiteNavigationMenuTable() {
		super("SiteNavigationMenu", SiteNavigationMenuTable::new);
	}

}