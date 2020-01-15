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
 * The table class for the CTCollection.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTCollectionTable extends Table<CTCollectionTable> {

	public static final CTCollectionTable INSTANCE = new CTCollectionTable();

	public final Column<CTCollectionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<CTCollectionTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT);
	public final Column<CTCollectionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<CTCollectionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<CTCollectionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<CTCollectionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<CTCollectionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<CTCollectionTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<CTCollectionTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);
	public final Column<CTCollectionTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT);
	public final Column<CTCollectionTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP);

	private CTCollectionTable() {
		super("CTCollection", CTCollectionTable::new);
	}

}