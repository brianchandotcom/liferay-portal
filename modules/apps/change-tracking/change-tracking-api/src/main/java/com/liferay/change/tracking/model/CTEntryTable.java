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

package com.liferay.change.tracking.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the CTEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTEntryTable extends Table<CTEntryTable> {

	public static final CTEntryTable INSTANCE = new CTEntryTable();

	public final Column<CTEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<CTEntryTable, Long> ctEntryId = createColumn(
		"ctEntryId", Long.class, Types.BIGINT);
	public final Column<CTEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<CTEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<CTEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<CTEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<CTEntryTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT);
	public final Column<CTEntryTable, Long> modelClassNameId = createColumn(
		"modelClassNameId", Long.class, Types.BIGINT);
	public final Column<CTEntryTable, Long> modelClassPK = createColumn(
		"modelClassPK", Long.class, Types.BIGINT);
	public final Column<CTEntryTable, Long> modelMvccVersion = createColumn(
		"modelMvccVersion", Long.class, Types.BIGINT);
	public final Column<CTEntryTable, Integer> changeType = createColumn(
		"changeType", Integer.class, Types.INTEGER);

	private CTEntryTable() {
		super("CTEntry", CTEntryTable::new);
	}

}