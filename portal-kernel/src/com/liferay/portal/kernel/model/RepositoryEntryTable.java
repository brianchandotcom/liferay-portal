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
 * The table class for the &quot;RepositoryEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntry
 * @generated
 */
public class RepositoryEntryTable extends BaseTable<RepositoryEntryTable> {

	public static final RepositoryEntryTable INSTANCE =
		new RepositoryEntryTable();

	public final Column<RepositoryEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RepositoryEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RepositoryEntryTable, Long> repositoryEntryId =
		createColumn(
			"repositoryEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RepositoryEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RepositoryEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RepositoryEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RepositoryEntryTable, Long> repositoryId = createColumn(
		"repositoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryEntryTable, String> mappedId = createColumn(
		"mappedId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RepositoryEntryTable, Boolean> manualCheckInRequired =
		createColumn(
			"manualCheckInRequired", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<RepositoryEntryTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private RepositoryEntryTable() {
		super("RepositoryEntry", RepositoryEntryTable::new);
	}

}