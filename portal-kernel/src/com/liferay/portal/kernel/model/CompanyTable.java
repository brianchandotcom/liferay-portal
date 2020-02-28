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

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Company&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Company
 * @generated
 */
public class CompanyTable extends BaseTable<CompanyTable> {

	public static final CompanyTable INSTANCE = new CompanyTable();

	public final Column<CompanyTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CompanyTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CompanyTable, Long> accountId = createColumn(
		"accountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, String> webId = createColumn(
		"webId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, String> mx = createColumn(
		"mx", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, String> homeURL = createColumn(
		"homeURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, Long> logoId = createColumn(
		"logoId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, Boolean> system = createColumn(
		"system_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, Integer> maxUsers = createColumn(
		"maxUsers", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private CompanyTable() {
		super("Company", CompanyTable::new);
	}

}