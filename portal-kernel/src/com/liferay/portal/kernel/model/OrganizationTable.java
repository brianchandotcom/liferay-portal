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

import java.util.Date;

/**
 * The table class for the &quot;Organization_&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Organization
 * @generated
 */
public class OrganizationTable extends BaseTable<OrganizationTable> {

	public static final OrganizationTable INSTANCE = new OrganizationTable();

	public final Column<OrganizationTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<OrganizationTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<OrganizationTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, Long> organizationId = createColumn(
		"organizationId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<OrganizationTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, Long> parentOrganizationId =
		createColumn(
			"parentOrganizationId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, Boolean> recursable = createColumn(
		"recursable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, Long> regionId = createColumn(
		"regionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, Long> countryId = createColumn(
		"countryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, Long> statusListTypeId =
		createColumn(
			"statusListTypeId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, String> comments = createColumn(
		"comments", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OrganizationTable, Long> logoId = createColumn(
		"logoId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private OrganizationTable() {
		super("Organization_", OrganizationTable::new);
	}

}