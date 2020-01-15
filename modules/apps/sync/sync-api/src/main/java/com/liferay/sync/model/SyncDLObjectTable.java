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

package com.liferay.sync.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the SyncDLObject.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SyncDLObjectTable extends Table<SyncDLObjectTable> {

	public static final SyncDLObjectTable INSTANCE = new SyncDLObjectTable();

	public final Column<SyncDLObjectTable, Long> syncDLObjectId = createColumn(
		"syncDLObjectId", Long.class, Types.BIGINT);
	public final Column<SyncDLObjectTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SyncDLObjectTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<SyncDLObjectTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, Long> createTime = createColumn(
		"createTime", Long.class, Types.BIGINT);
	public final Column<SyncDLObjectTable, Long> modifiedTime = createColumn(
		"modifiedTime", Long.class, Types.BIGINT);
	public final Column<SyncDLObjectTable, Long> repositoryId = createColumn(
		"repositoryId", Long.class, Types.BIGINT);
	public final Column<SyncDLObjectTable, Long> parentFolderId = createColumn(
		"parentFolderId", Long.class, Types.BIGINT);
	public final Column<SyncDLObjectTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, String> extension = createColumn(
		"extension", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, String> mimeType = createColumn(
		"mimeType", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, String> changeLog = createColumn(
		"changeLog", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, Clob> extraSettings = createColumn(
		"extraSettings", Clob.class, Types.CLOB);
	public final Column<SyncDLObjectTable, String> version = createColumn(
		"version", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, Long> versionId = createColumn(
		"versionId", Long.class, Types.BIGINT);
	public final Column<SyncDLObjectTable, Long> size = createColumn(
		"size_", Long.class, Types.BIGINT);
	public final Column<SyncDLObjectTable, String> checksum = createColumn(
		"checksum", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, String> event = createColumn(
		"event", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, String> lanTokenKey = createColumn(
		"lanTokenKey", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, Date> lastPermissionChangeDate =
		createColumn("lastPermissionChangeDate", Date.class, Types.TIMESTAMP);
	public final Column<SyncDLObjectTable, Date> lockExpirationDate =
		createColumn("lockExpirationDate", Date.class, Types.TIMESTAMP);
	public final Column<SyncDLObjectTable, Long> lockUserId = createColumn(
		"lockUserId", Long.class, Types.BIGINT);
	public final Column<SyncDLObjectTable, String> lockUserName = createColumn(
		"lockUserName", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR);
	public final Column<SyncDLObjectTable, Long> typePK = createColumn(
		"typePK", Long.class, Types.BIGINT);
	public final Column<SyncDLObjectTable, String> typeUuid = createColumn(
		"typeUuid", String.class, Types.VARCHAR);

	private SyncDLObjectTable() {
		super("SyncDLObject", SyncDLObjectTable::new);
	}

}